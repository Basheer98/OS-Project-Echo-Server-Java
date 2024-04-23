import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedEchoServer {
    private static final int PORT = 6007;
    private static final int THREAD_POOL_SIZE = 10;
    private static ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Multi-threaded Echo Server is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                executorService.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();

                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    String received = new String(buffer, 0, bytesRead);
                    System.out.println("Client " + clientSocket.getInetAddress() + ": " + received);

                    if ("Done".equalsIgnoreCase(received.trim())) {
                        outputStream.write("Done".getBytes());
                        break;
                    } else {
                        outputStream.write(received.getBytes());
                    }
                }

                clientSocket.close();
                System.out.println("Client Ended the Connection: " + clientSocket.getInetAddress());
            } catch (IOException e) {
                System.err.println("Error Connecting client: " + e.getMessage());
            }
        }
    }
}
