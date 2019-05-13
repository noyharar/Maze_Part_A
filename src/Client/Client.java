package Client;

import java.net.InetAddress;
import java.net.Socket;


public class Client {
    private InetAddress serverIP;
    private int serverPort;
    private IClientStrategy clientStrategy;

    public Client(InetAddress IP, int port, IClientStrategy clientStrategy) {
        this.serverIP = IP;
        this.serverPort = port;
        this.clientStrategy = clientStrategy;
    }

    public void start(){
        try {
            Socket theServer = new Socket(serverIP, serverPort);
            System.out.println(String.format("Client: Client is connected to server: %s on port: %s",serverIP.toString(),serverPort));
            clientStrategy.clientStrategy(theServer.getInputStream(),theServer.getOutputStream());
            theServer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void communicateWithServer() {
        this.start();
    }
}
