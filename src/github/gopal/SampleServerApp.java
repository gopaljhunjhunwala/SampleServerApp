package github.gopal;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: gopal
 */
public class SampleServerApp {

    private static final Integer SERVER_PORT = 8088;

    private static final Logger LOGGER = Logger.getLogger("SampleServer");

    private static final String CHARSET_NAME = "UTF-8";

    private static final List<String> COLORS = Arrays
        .asList("INDIANRED", "LIGHTCORAL", " DARKSALMON", "CRIMSON", " RED",
            " CORAL", " TOMATO", " ORANGE", " MOCCASIN", " LAVENDER", " ORCHID",
            " LIMEGREEN");

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            LOGGER.info("Connection Accepted");
            handleResponse(clientSocket);
        }
    }

    private static void handleResponse(Socket clientSocket) throws IOException {
        OutputStream outputStream = clientSocket.getOutputStream();
        long time = System.currentTimeMillis();
        int index = Math.toIntExact(time % 11);
        String color = COLORS.get(index);
        String responseDocumentStr =
            "<html>" + "<body style=\"background-color:" + color + ";\">"
                + "<h1> Example Server </h1>" + "<h3>Time is: " + time + "</h3>"
                + "</body>" + "</html>";
        byte[] responseDocument = responseDocumentStr.getBytes(CHARSET_NAME);

        String httpResponseHeaderStr =
            "HTTP/1.1 200 OK\r\n" + "Content-Type: text/html; charset=UTF-8\r\n"
                + "Content-Length: " + responseDocument.length + "\r\n\r\n";
        byte[] httpResponseHeader = httpResponseHeaderStr
            .getBytes(CHARSET_NAME);

        outputStream.write(httpResponseHeader);
        outputStream.write(responseDocument);

        outputStream.close();
        LOGGER.info("Connection Processed at : " + time + "with color: " + color);
    }
}
