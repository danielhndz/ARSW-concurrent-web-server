package edu.escuelaing.arsw.labs.concurrentServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private static final int MAX_THREADS = 8;
    private static final int PORT = 35000;
    private static boolean run = true;

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(getPort());
            System.out.println("Server socket created ...");
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + getPort() + ".");
            System.exit(1);
        }

        while (run) {
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Client socket accepted ...");
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            threadPool.execute(new RequestProcessor(clientSocket));
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Close server failed.");
            System.exit(1);
        }
    }

    public static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return PORT;
    }
}
