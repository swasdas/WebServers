import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {
    public Consumer<Socket> getConsumer() {
        return (clientSocket) -> {
            try (PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(), true)) {
                toSocket.println("Hello from server " + clientSocket.getInetAddress());
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    clientSocket.close(); // Close socket after processing
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    public static void main(String[] args) {
        int port = 8010;
        Server server = new Server();

        try {
            ServerSocket serverSocket = new ServerSocket(port); // to accept client request
            serverSocket.setSoTimeout(20000);
            System.out.println("Server is listening on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept(); // to communicate with client after accepting request above
                Thread thread = new Thread(() -> server.getConsumer().accept(clientSocket)); // New thread for each client
                thread.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
