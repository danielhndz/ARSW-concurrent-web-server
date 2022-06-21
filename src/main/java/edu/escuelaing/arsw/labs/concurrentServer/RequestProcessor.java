package edu.escuelaing.arsw.labs.concurrentServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestProcessor implements Runnable {

    private final Socket clientSocket;

    public RequestProcessor(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            process();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void process() throws IOException {
        String inputLine;
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        while ((inputLine = in.readLine()) != null) {
            System.out.println("Received: " + inputLine);
            if (inputLine.startsWith("GET")) {
                String path = inputLine.replace("GET ", "").replace(" HTTP/1.1", "");
                if (path.endsWith(".png") || path.endsWith(".jpg")) {
                    FilesReader.img(path, clientSocket.getOutputStream());
                } else {
                    FilesReader.text(path, new PrintWriter(clientSocket.getOutputStream(), true));
                }
            }
            if (!in.ready()) {
                break;
            }
        }

        in.close();
        clientSocket.close();
    }

}
