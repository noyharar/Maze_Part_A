package test;

import IO.MyDecompressorInputStream;
import Server.*;
import Client.*;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.AState;
import algorithms.search.Solution;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class RunCommunicateWithServers {
//    public static Maze myMaze = new MyMazeGenerator().generate(50, 50);
    private static final int NumOfClientThreads = 1000;
    public static void main(String[] args) {

        //Initializing servers
        Server mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        Server solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        //Server stringReverserServer = new Server(5402, 1000, new ServerStrategyStringReverser());

        //Starting  servers
        solveSearchProblemServer.start();
        mazeGeneratingServer.start();
        //stringReverserServer.start();

        //Communicating with servers
        Thread[] mazeGeneratingThreads = new Thread[NumOfClientThreads];
        Thread[] mazeSolvingThreads = new Thread[NumOfClientThreads];

        for (int i = 0; i < mazeGeneratingThreads.length; i++) {
            mazeGeneratingThreads[i] = new ThreadMazeGenerating(i);
            mazeSolvingThreads[i] = new ThreadSolveSearchProblem(i);
        }
        //Threads start
        for (int i = 0; i < mazeGeneratingThreads.length; i++) {
//            System.out.println("\nMazeGenerating | Thread index: "+i+" Thread Id: "+ Thread.currentThread().getId());
            mazeGeneratingThreads[i].start();
            mazeSolvingThreads[i].start();
        }
        //Wait for all the threads to join
        for (int i = 0; i < mazeGeneratingThreads.length; i++) {
            try{
                mazeGeneratingThreads[i].join();
                mazeSolvingThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        CommunicateWithServer_MazeGenerating();
//        CommunicateWithServer_SolveSearchProblem();
//        CommunicateWithServer_SolveSearchProblem();
        //CommunicateWithServer_StringReverser();

        //Stopping all servers
        mazeGeneratingServer.stop();
        solveSearchProblemServer.stop();
        //stringReverserServer.stop();
    }

    private static class ThreadMazeGenerating extends Thread {
        private int threadId;

        public ThreadMazeGenerating(int threadId) {
            this.threadId = threadId;
        }

        public int getThreadId() {
            return threadId;
        }

        public void run(){
            CommunicateWithServer_MazeGenerating(50,50);
        }
    }


    private static class ThreadSolveSearchProblem extends Thread {
        private int threadId;
        private Maze maze;

        public ThreadSolveSearchProblem(int threadId, Maze maze) {
            this.threadId = threadId;
            this.maze = maze;
        }

        public ThreadSolveSearchProblem(int threadId) {
            this.threadId = threadId;
            MyMazeGenerator mazeGenerator = new MyMazeGenerator();
            this.maze = mazeGenerator.generate(50,50);
        }

        public void run(){
            CommunicateWithServer_SolveSearchProblem(maze);
        }
    }


/*
    private static void CommunicateWithServer_MazeGenerating() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        System.out.println("Client: Client trying to connect to Server");
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        System.out.println("Client: OutputStream to Server is set");
                        MyDecompressorInputStream decompressorInputStream = new MyDecompressorInputStream(inFromServer);
                        ObjectInputStream fromServer = new ObjectInputStream(decompressorInputStream);
                        //Here it will DIe

                        System.out.println("Client: OutputStream from Server is set");
                        System.out.println("Client: Client Connected to Server");
                        toServer.flush();
                        int row = 1000;
                        int col = 1000;
                        int[] mazeDimensions = new int[]{row, col};
                        System.out.println("Client: Sending Dimensions");
                        toServer.writeObject(mazeDimensions); //send maze dimensions to server
                        toServer.flush();
                        System.out.println("Client: Dimensions sent");
                        Object compMaze = fromServer.readObject();
                        byte[] byteTest = new byte[row*col+12];
//                        Object compMaze = fromServer.read(byteTest);
                        byte[] compressedMaze = byteTest; //read generated maze (compressed with MyCompressor) from server
                        for (byte b :
                                compressedMaze) {
                            System.out.print((int) b + " ");
                        }
                        System.out.println();
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[byteTest.length]; //allocating byte[] for the decompressed maze -
                        is.read(decompressedMaze); //Fill decompressedMaze with bytes
                        for (byte b :
                                decompressedMaze) {
                            System.out.print((int)b + " ");
                        }
                        System.out.println();
                        Maze maze = new Maze(decompressedMaze);
//                        maze.print();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
//            client.start();
            client.communicateWithServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    private static void CommunicateWithServer_MazeGenerating(int row, int col) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{row, col};
                        toServer.writeObject(mazeDimensions); //send maze dimensions to server
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[row*col+12 /*CHANGE SIZE ACCORDING TO YOU MAZE SIZE*/]; //allocating byte[] for the decompressed maze -
                        is.read(decompressedMaze); //Fill decompressedMaze with bytes
                        Maze maze = new Maze(decompressedMaze);
//                        maze.print();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


/*
    private static void CommunicateWithServer_SolveSearchProblem() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        MyMazeGenerator mg = new MyMazeGenerator();
                        Maze maze = mg.generate(1000, 1000);
                        maze.print();
                        toServer.writeObject(maze); //send maze to server
                        toServer.flush();
                        Solution mazeSolution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server

                        //Print Maze Solution retrieved from the server
                        System.out.println(String.format("Solution steps: %s", mazeSolution));
                        ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
                        for (int i = 0; i < mazeSolutionSteps.size(); i++) {
                            System.out.println(String.format("%s. %s", i, mazeSolutionSteps.get(i).toString()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
*/
private static void CommunicateWithServer_SolveSearchProblem(Maze myMaze) {
    try {
        Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
            @Override
            public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                try {
                    ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                    ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                    toServer.flush();
                    myMaze.print();
                    toServer.writeObject(myMaze); //send maze to server
                    toServer.flush();
                    Solution mazeSolution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server

                    //Print Maze Solution retrieved from the server
                    System.out.println(String.format("Solution steps: %s", mazeSolution));
                    ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
                    for (int i = 0; i < mazeSolutionSteps.size(); i++) {
                        System.out.println(String.format("%s. %s", i, mazeSolutionSteps.get(i).toString()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        client.communicateWithServer();
    } catch (UnknownHostException e) {
        e.printStackTrace();
    }
}
//    private static void CommunicateWithServer_StringReverser() {
//        try {
//            Client client = new Client(InetAddress.getLocalHost(), 5402, new IClientStrategy() {
//                @Override
//                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
//                    try {
//                        BufferedReader fromServer = new BufferedReader(new InputStreamReader(inFromServer));
//                        PrintWriter toServer = new PrintWriter(outToServer);
//
//                        String message = "Client Message";
//                        String serverResponse;
//                        toServer.write(message + "\n");
//                        toServer.flush();
//                        serverResponse = fromServer.readLine();
//                        System.out.println(String.format("Server response: %s", serverResponse));
//                        toServer.flush();
//                        fromServer.close();
//                        toServer.close();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            client.communicateWithServer();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//    }
}

