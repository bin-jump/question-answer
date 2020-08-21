package com.zhang.ddd.infrastructure.storage;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.PrintWriter;

public class FtpClient implements AutoCloseable {

    private String server;

    private int port;

    private String user;

    private String password;

    private FTPClient client;

    public static FtpClient newInstance(String server, int port, String user, String password) throws IOException {
        FtpClient ftp = new FtpClient(server, port, user, password);
        ftp.open();
        return ftp;
    }

    private FtpClient(String server, int port, String user, String password) {
        this.server = server;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public FTPClient getClient() {
        return this.client;
    }

    public void open() throws IOException {
        client = new FTPClient();
        //client.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

        client.connect(server, port);
        int reply = client.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            client.disconnect();
            throw new IOException("Failed to connect the FTP Server");
        }
        if (!client.login(user, password)){
            throw new IllegalStateException("Failed not login to the FTP server.");
        }
    }


    @Override
    public void close() throws IOException {
        if (client.isConnected()) {
            client.disconnect();
        }
    }
}
