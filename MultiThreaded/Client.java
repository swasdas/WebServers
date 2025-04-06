
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public Runnable getRunnable() throws UnknownHostException, IOException {
        return new Runnable() {
            @Override
            public void run() {
                int port = 8010;
                try {
                    InetAddress address = InetAddress.getByName("127.0.0.1");
//                    System.out.println("Connecting to: " + address + " on port 8010");
                    Socket socket = new Socket(address, port);
//                    System.out.println("Connected to server!");
                    try ( // resources passed in try block will auto-close after execution
                            PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);
                            BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    ) {
                        toSocket.println("Hello from Client " + socket.getLocalSocketAddress());
                        String line = fromSocket.readLine();
                        System.out.println("Response from Server: " + line);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // The socket will be closed automatically when leaving the try-with-resources block
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
    }

    public static void main(String[] args){
        Client client = new Client();
        for(int i=0; i < 30; i++){
            try{
                Thread thread = new Thread(client.getRunnable());
                thread.start();
            }catch(Exception ex){
                return;
            }
        }
    }
}
