import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 6007);
            System.out.println("Connected to server.");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();

            String message;
            while (true) {
                System.out.print("Enter message: ");
                message = reader.readLine();

                // Send message to server
                outputStream.write(message.getBytes());
                outputStream.flush();

                // Check if client wants to close the connection
                if ("Done".equals(message.trim())) {
                    break;
                }

                // Receive echo from server
                byte[] buffer = new byte[1024];
                int bytesRead = inputStream.read(buffer);
                String receivedData = new String(buffer, 0, bytesRead);
                System.out.println("Server echoed: " + receivedData);
            }

            // Close the socket
            socket.close();
            System.out.println("Connection closed.");
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
}
