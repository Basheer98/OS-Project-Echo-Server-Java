import java.io.*;
import java.net.*;

public class EchoServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6007);
            System.out.println("Server is running and waiting for client connection...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client successfully connected.");

                // Create input and output streams
                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();

                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    // Read data from the client
                    String receivedData = new String(buffer, 0, bytesRead);
                    System.out.println("Received from client: " + receivedData);

                    // Check if the client wants to close the connection
                    if ("Done".equals(receivedData.trim())) {
                        System.out.println("Client requested to close the connection.");
                        break;
                    }

                    // Echo back to the client
                    outputStream.write(buffer, 0, bytesRead);
                }

                // Close the client socket
                clientSocket.close();
                System.out.println("Client disconnected.");
            }
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
}
