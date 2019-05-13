package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server extends Thread {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
//    private static final Logger LOG = LogManager.getLogger(); //Log4j2

    public Server(int port, int listeningIntervalMS, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.serverStrategy = serverStrategy;
    }

    public void start()
    {
        (new Thread(() -> {
            this.startServer();
        })).start();
    }

    public void startServer() {
        try {

            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            System.out.println(String.format("Server starter at %s!", serverSocket));
//            LOG.info(String.format("Server starter at %s!", serverSocket));
            System.out.println(String.format("Server's Strategy: %s", serverStrategy.getClass().getSimpleName()));
//            LOG.info(String.format("Server's Strategy: %s", serverStrategy.getClass().getSimpleName()));
            System.out.println("Server is waiting for clients...");
//            LOG.info("Server is waiting for clients...");
            System.out.println("Server is waiting for clients...");
            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept(); // Accepts client

                    System.out.println(String.format("Client excepted: %s", clientSocket));
//                    LOG.info(String.format("Client excepted: %s", clientSocket));
                    try {
                        serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
                        clientSocket.getInputStream().close();
                        clientSocket.getOutputStream().close();
                        clientSocket.close();
                    } catch (IOException e) {
//                        LOG.error("IOException - Error handing client!", e);
                    }
                } catch (SocketTimeoutException e) {
//                    LOG.debug("Socket Timeout - No clients are waiting!");
                }

            }
            serverSocket.close();
        } catch (IOException e) {
//            LOG.error("IOException:", e);
        }
        catch (Exception e){}
    }

    public void stopServer() {
//        LOG.info("Stopping server..");
        stop = true;
    }
}
