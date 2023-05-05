package com.iisi.patrol.webGuard.service;

import com.iisi.patrol.webGuard.service.sshService.ConnectionConfig;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

public class TestSSHService {


    /**
     * JSch Example Tutorial
     * Java SSH Connection Program
     */
    public static void main(String[] args) {
        String host="192.168.57.202";
        String user="tailinh";
        String password="IIsi@940450";
        String command1="du -b test1234.txt";
        ConnectionConfig conn = new ConnectionConfig(host, user, password, 22);

        String response = useSshCommand2(conn, command1);
        System.out.println(response);
        String fileName = response.split("\t")[1];
        String fileSize = response.split("\t")[0];
        System.out.println(fileName);
        System.out.println(fileSize);
        String filePath = "C:\\Users\\2106017\\comparison\\origin\\test1234.txt";
        System.out.println(new File(filePath).length());
    }


    public static String useSshCommand(ConnectionConfig connectionConfig, String command){
        StringBuilder sb = new StringBuilder();
        try{

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            Session session=jsch.getSession(connectionConfig.getUserName(), connectionConfig.getServerIp(), 22);
            session.setPassword(connectionConfig.getPassWord());
            session.setConfig(config);
            session.connect();
            System.out.println("Connected");

            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);
            channel.setInputStream(null);
            ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
            ((ChannelExec)channel).setErrStream(errorStream);

            InputStream in=channel.getInputStream();
            channel.connect();
            byte[] tmp=new byte[1024];
            while(true){
                while(in.available()>0){
                    int i=in.read(tmp, 0, 1024);
                    if(i<0)break;
                    sb.append(new String(tmp, 0, i));
                    System.out.print(new String(tmp, 0, i));
                }
                if(channel.isClosed()){
                    System.out.println("exit-status: "+channel.getExitStatus());
                    break;
                }
                try{Thread.sleep(1000);}catch(Exception ee){}
            }
            System.out.println(new String(errorStream.toByteArray()));
            channel.disconnect();
            session.disconnect();
            System.out.println("DONE");
        }catch(Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String useSshCommand2(ConnectionConfig connectionConfig, String command){
        Session session = null;
        ChannelExec channel = null;
        String responseString = null;
        try {
            session = new JSch().getSession(connectionConfig.getUserName(), connectionConfig.getServerIp(), connectionConfig.getPort());
            session.setPassword(connectionConfig.getPassWord());
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            channel.setOutputStream(responseStream);
            channel.connect();

            while (channel.isConnected()) {
                Thread.sleep(1000);
            }
            responseString = new String(responseStream.toByteArray());
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
        return responseString.trim();
    }


    public void checkFileSize(){

    }
}
