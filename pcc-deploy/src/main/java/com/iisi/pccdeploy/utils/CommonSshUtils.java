package com.iisi.pccdeploy.utils;

import com.iisi.pccdeploy.service.ConnectionConfig;
import com.jcraft.jsch.*;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;

@Component
public class CommonSshUtils {

    public static String useSshCommand(ConnectionConfig connectionConfig, String command) throws JSchException {

        Session session = null;
        ChannelExec channel = null;
        String responseString = null;
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
        } catch (InterruptedException e) {
            e.printStackTrace();
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

    public boolean uploadFile(ConnectionConfig connectionConfig, String localFilePath, String remoteFilePath) {
        ChannelSftp channelSftp = createChannelSftp(connectionConfig);
        try {
            channelSftp.put(localFilePath, remoteFilePath);
            return true;
        } catch (SftpException ex) {
            ex.printStackTrace();
        } finally {
            disconnectChannelSftp(channelSftp);
        }

        return false;
    }

    public boolean testConnect(ConnectionConfig connectionConfig){
        Session session = null;
        try {
            session = new JSch().getSession(connectionConfig.getUserName(), connectionConfig.getServerIp(), connectionConfig.getPort());
            session.setPassword(connectionConfig.getPassWord());
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            return true;
        } catch (JSchException e) {
            System.out.println(e);
            return false;
        }finally {
            session.disconnect();
        }

    }


    private Session createSession(ConnectionConfig connectionConfig) {
        try {
            Session session = null;
            session = new JSch().getSession(connectionConfig.getUserName(), connectionConfig.getServerIp(), connectionConfig.getPort());
            session.setPassword(connectionConfig.getPassWord());
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            return session;
        } catch (JSchException e) {
            System.out.println(e);
            return null;
        }
    }


    private ChannelSftp createChannelSftp(ConnectionConfig connectionConfig) {
        try {
            JSch jSch = new JSch();
            Session session = jSch.getSession(connectionConfig.getUserName(), connectionConfig.getServerIp(), connectionConfig.getPort());
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(connectionConfig.getPassWord());
            session.connect(10000);
            Channel channel = session.openChannel("sftp");
            channel.connect(100000);
            return (ChannelSftp) channel;
        } catch (JSchException ex) {
            ex.printStackTrace();
        }

        return null;
    }



    private void disconnectChannelSftp(ChannelSftp channelSftp) {
        try {
            if (channelSftp == null)
                return;

            if (channelSftp.isConnected())
                channelSftp.disconnect();

            if (channelSftp.getSession() != null)
                channelSftp.getSession().disconnect();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
