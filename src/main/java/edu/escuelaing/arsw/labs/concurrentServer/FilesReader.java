package edu.escuelaing.arsw.labs.concurrentServer;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Path;

import javax.imageio.ImageIO;

public class FilesReader {

    private static final String RESOURCES_DIR = "src/main/res";
    private static final String NOT_FOUND_PAGE = "/404.html";
    private static final String HEADER = "HTTP/1.1 200 OK\r\n";
    private static final String CONTENT_TYPE_PNG = "Content-Type: image/png\r\n\r\n";
    private static final String CONTENT_TYPE_JPG = "Content-Type: image/jpg\r\n\r\n";
    private static final String CONTENT_TYPE_HTML = "Content-Type: text/html\r\n\r\n";
    private static final String CONTENT_TYPE_CSS = "Content-Type: text/css\r\n\r\n";
    private static final String CONTENT_TYPE_JS = "Content-Type: application/javascript\r\n\r\n";

    public static void img(String path, OutputStream clientOutputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(
                    ImageIO.read(Path.of("", (RESOURCES_DIR + path)
                            .replace("/", System.getProperty("file.separator")))
                            .toFile()),
                    "png",
                    outputStream);
            DataOutputStream dataOutputStream = new DataOutputStream(clientOutputStream);
            if (path.endsWith(".png")) {
                dataOutputStream.writeBytes(HEADER + CONTENT_TYPE_PNG);
            } else if (path.endsWith(".jpg")) {
                dataOutputStream.writeBytes(HEADER + CONTENT_TYPE_JPG);
            }
            dataOutputStream.write(outputStream.toByteArray());
            clientOutputStream.close();
        } catch (IOException e) {
            text(NOT_FOUND_PAGE, new PrintWriter(clientOutputStream));
        }
    }

    public static void text(String path, PrintWriter clientPrintWriter) {
        String line, response = "";
        if (path.isEmpty() || path.equals("/")) {
            response = defaultContent("This is the default content");
            clientPrintWriter.write(response);
        } else {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(
                        Path.of("", (RESOURCES_DIR + path)
                                .replace("/", System.getProperty("file.separator")))
                                .toFile()));
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
            } catch (FileNotFoundException e) {
                text(NOT_FOUND_PAGE, clientPrintWriter);
            } catch (IOException e) {
                text(NOT_FOUND_PAGE, clientPrintWriter);
            }

            if (path.endsWith(".css")) {
                clientPrintWriter.println(HEADER + CONTENT_TYPE_CSS + response);
            } else if (path.endsWith(".js")) {
                clientPrintWriter.println(HEADER + CONTENT_TYPE_JS + response);
            } else if (path.endsWith(".html")) {
                clientPrintWriter.println(HEADER + CONTENT_TYPE_HTML + response);
            }
        }

        clientPrintWriter.close();
    }

    private static String defaultContent(String inputLine) {
        System.out.println("default content");
        return HEADER + CONTENT_TYPE_HTML
                + "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<title>WITHOUT MEDIA</title>\n"
                + "</head>"
                + "<body>"
                + "<h2>My Web Site</h2>"
                + "<div> Request : " + inputLine + "</div>"
                + "</body>"
                + "</html>";
    }
}
