package com.jdb.util;

import com.google.common.base.Preconditions;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by qimwang on 9/25/16.
 */
public class TimeServer {
    public static void main(String[] args) throws IOException{
        int port = 8080;
        if (null != args && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {

            }
        }

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("The server is started in port: " + port);
            Socket socket = null;
            while (true) {
                socket = serverSocket.accept();
                System.out.println(socket.getInetAddress().getHostName());
                new Thread(new TimeServerHandler(socket)).start();
            }
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
                serverSocket = null;
            }
        }
    }
}
