package io.github.oliverschen.socket.samples;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer02 {

    public static void main(String[] args) throws Exception {
        ServerSocket socket = new ServerSocket(8182);
        while (true) {
            Socket accept = socket.accept();
            new Thread(()-> service(accept)).start();
        }
    }


    private static void service(Socket socket) {
        try {
            Thread.sleep(20);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body = "hello,nio";
            printWriter.println("Content-Length:" + body.getBytes().length);
            printWriter.println();
            printWriter.write(body);
            printWriter.close();
            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
