package SingleThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public void run() throws IOException {
        int port = 8010;
        InetAddress address = InetAddress.getByName("localhost");
        Socket socket = new Socket(address, port);
        PrintWriter toServer = new PrintWriter(socket.getOutputStream());
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        toServer.println("Hello from the client");
        String line = fromServer.readLine();
        System.out.println("Response from server is "+ line);
        toServer.close();
        fromServer.close();
        socket.close();
    }

    public static void main(String[] args){
        try{
            Client client = new Client();
            client.run();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}