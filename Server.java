import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6007);
            System.out.println("Waiting for connection.....");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection successful");

                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();

                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    String received = new String(buffer, 0, bytesRead);
                    System.out.println("Client: " + received);

                    if ("Done".equalsIgnoreCase(received.trim())) {
                        outputStream.write("Done".getBytes());
                        break;
                    } else {
                        outputStream.write(received.getBytes());
                    }
                }

                clientSocket.close();
                System.out.println("Waiting for connection.....");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
