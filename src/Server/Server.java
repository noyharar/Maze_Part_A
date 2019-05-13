package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

//TODO: add Log instead of Sytem.out.prints.....
public class Server {
    private int port;
    private int listeningInterval;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
    private ThreadPoolExecutor myPoolOfThreads;

//    private static final Logger LOG = LogManager.getLogger(); //Log4j2

    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
        myPoolOfThreads = (ThreadPoolExecutor) Executors.newFixedThreadPool(Integer.parseInt(Configurations.getInstance().getProperty("ThreadPoolNum")));
    }

    public void start() {
        new Thread(() -> {
            runServer();
        }).start();
    }

    private void runServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningInterval);
//            LOG.info(String.format("Server starter at %s!", serverSocket));
//            LOG.info(String.format("Server's Strategy: %s", serverStrategy.getClass().getSimpleName()));
//            LOG.info("Server is waiting for clients...");
            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept(); // blocking call
//                    LOG.info(String.format("Client accepted: %s", clientSocket));
                    System.out.println(String.format("Server: Client accepted: %s", clientSocket));
                    myPoolOfThreads.submit(() -> {
                        try{
//                            Thread.sleep(1000);
                            System.out.println("Server: Currently running " + myPoolOfThreads.getActiveCount() + " Threads");
                            handleClient(clientSocket);
//                            return;
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
//                        LOG.info(String.format("Finished handle client: %s", clientSocket));
                    });
                }
                catch (SocketTimeoutException e) {
//                    LOG.debug("Socket Timeout - No clients pending!");

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            serverSocket.close();
            if(!myPoolOfThreads.isTerminated())
                myPoolOfThreads.shutdownNow();

        } catch (IOException e) {
//            LOG.error("IOException", e);
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            System.out.println(String.format("Server: Handling client with socket: %s", clientSocket.toString()));
//            LOG.info(String.format("Handling client with socket: %s", clientSocket.toString()));
            serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
            System.out.println(String.format("Server: Closed client socket: %s", clientSocket.toString()));
        } catch (IOException e) {
//            LOG.error("IOException", e);
        }
    }

    public void stop() {
//        LOG.info("Stopping server..");
        stop = true;
        myPoolOfThreads.shutdown();
    }
}
