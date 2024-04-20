import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 6007);
            System.out.println("Attemping to connect to host 127.0.0.1 on port 6007.");

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream outputStream = socket.getOutputStream();

            String input;
            while ((input = inputReader.readLine()) != null) {
                outputStream.write((input + "\n").getBytes());

                String echo = socketReader.readLine();
                System.out.println("echo: " + echo);

                if ("Done".equalsIgnoreCase(input.trim())) {
                    break;
                }
            }

            socket.close();
        } catch (Exception e) {
            // Catch any exceptions to avoid exception message output on console
        }
    }
}
