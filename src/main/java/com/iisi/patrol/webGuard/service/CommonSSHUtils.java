package com.iisi.patrol.webGuard.service;

import com.iisi.patrol.webGuard.service.sshService.ConnectionConfig;
import com.jcraft.jsch.*;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Properties;

@Component
public class CommonSSHUtils {

    public static String useSshCommand(ConnectionConfig connectionConfig, String command) throws Exception {

        Session session = null;
        ChannelExec channel = null;
        String responseString;
        String errorString = "";
        try {
            session = new JSch().getSession(connectionConfig.getUserName(), connectionConfig.getServerIp(), connectionConfig.getPort());
            session.setPassword(connectionConfig.getPassWord());
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            ((ChannelExec)channel).setErrStream(System.err);
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
            channel.setOutputStream(responseStream);
            channel.setErrStream(errorStream);
            channel.connect();

            while (channel.isConnected()) {
                Thread.sleep(100);
            }
            errorString = errorStream.toString();
            responseString = responseStream.toString();
        } finally {
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
        if(errorString.length()>0){
            return errorString.trim();
        }

        return responseString.trim();
    }

    public static String useScpCopyRemoteFile(ConnectionConfig connectionConfig,String from, String to,String fileName) throws JSchException, IOException {
        Session session = CommonSSHUtils.createSession(connectionConfig);
        copyRemoteToLocal(session,from,to,fileName);
        return "ok";
    }

    private static Session createSession(ConnectionConfig connectionConfig) {
        try {
            Session session = null;
            session = new JSch().getSession(connectionConfig.getUserName(),connectionConfig.getServerIp(), connectionConfig.getPort());
            session.setPassword(connectionConfig.getPassWord());
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            return session;
        } catch (JSchException e) {
            System.out.println(e);
            return null;
        }
    }

    private static void copyRemoteToLocal(Session session, String from, String to, String fileName) throws JSchException, IOException {
        from = from + File.separator + fileName;
        String prefix = null;

        if (new File(to).isDirectory()) {
            prefix = to + File.separator;
        }

        // exec 'scp -f rfile' remotely
        String command = "scp -f " + from;
        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(command);

        // get I/O streams for remote scp
        OutputStream out = channel.getOutputStream();
        InputStream in = channel.getInputStream();

        channel.connect();

        byte[] buf = new byte[8096];

        // send '\0'
        buf[0] = 0;
        out.write(buf, 0, 1);
        out.flush();

        while (true) {
            int c = checkAck(in);
            if (c != 'C') {
                break;
            }

            // read '0644 '
            //in.read(buf, 0, 5);下面的寫法比較不會有read的bug
            int bytes_read = 0;
            while (bytes_read < 5){
                bytes_read += in.read(buf, 0, 5 - bytes_read);
            }

            long filesize = 0L;
            while (true) {
                if (in.read(buf, 0, 1) < 0) {
                    // error
                    break;
                }
                if (buf[0] == ' ') break;
                filesize = filesize * 10L + (long) (buf[0] - '0');
            }

            String file = null;
            for (int i = 0; ; i++) {
                in.read(buf, i, 1);
                if (buf[i] == (byte) 0x0a) {
                    file = new String(buf, 0, i);
                    break;
                }
            }

            //System.out.println("file-size=" + filesize + ", file=" + file);

            // send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();

            // read a content of lfile
            FileOutputStream fos = new FileOutputStream(prefix == null ? to : prefix + file);
            int foo;
            while (true) {
                if (buf.length < filesize) foo = buf.length;
                else foo = (int) filesize;
                foo = in.read(buf, 0, foo);
                if (foo < 0) {
                    // error
                    break;
                }
                fos.write(buf, 0, foo);
                filesize -= foo;
                if (filesize == 0L) break;
            }

            if (checkAck(in) != 0) {
                System.exit(0);
            }

            // send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();

            try {
                if (fos != null) fos.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }

        channel.disconnect();
        session.disconnect();

    }

    public static int checkAck(InputStream in) throws IOException {
        int b = in.read();
        if (b == 0) return b;
        if (b == -1) return b;

        if (b == 1 || b == 2) {
            StringBuffer sb = new StringBuffer();
            int c;
            do {
                c = in.read();
                sb.append((char) c);
            }
            while (c != '\n');
            if (b == 1) { // error
                System.out.print(sb.toString());
            }
            if (b == 2) { // fatal error
                System.out.print(sb.toString());
            }
        }
        return b;
    }

}
