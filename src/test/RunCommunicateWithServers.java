//package test;
//
//import IO.MyDecompressorInputStream;
//import Server.*;
//import Client.*;
//import algorithms.mazeGenerators.Maze;
//import algorithms.mazeGenerators.MyMazeGenerator;
//import algorithms.search.AState;
//import algorithms.search.Solution;
//
//import java.io.*;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.util.ArrayList;
//import java.util.Properties;
//import Server.Configurations;
//
//public class RunCommunicateWithServers {
////    public static Maze myMaze = new MyMazeGenerator().generate(50, 50);
//    private static final int NumOfClientThreads = 10;
//    public static void main(String[] args) {
//        int generatePortNum,generateInterval, solverPortNum, solverInterval;
//        generatePortNum = Integer.parseInt(Configurations.getInstance().getProperty("GeneratorTypePort"));
//        generateInterval = Integer.parseInt(Configurations.getInstance().getProperty("GeneratorTypeListeningInterval"));
//        solverPortNum = Integer.parseInt(Configurations.getInstance().getProperty("SolverTypePort"));
//        solverInterval = Integer.parseInt(Configurations.getInstance().getProperty("SolverTypeListeningInterval"));
//
//        //Initializing servers
////        Server mazeGeneratingServer = new Server(generatePortNum, generateInterval, new ServerStrategyGenerateMaze());
//        Server solveSearchProblemServer = new Server(solverPortNum, solverInterval, new ServerStrategySolveSearchProblem());
//        //Server stringReverserServer = new Server(5402, 1000, new ServerStrategyStringReverser());
//
//        //Starting  servers
//        solveSearchProblemServer.start();
////        mazeGeneratingServer.start();
//        //stringReverserServer.start();
//
//        //Communicating with servers
//        Thread[] mazeGeneratingThreads = new Thread[NumOfClientThreads];
//        Thread[] mazeSolvingThreads = new Thread[NumOfClientThreads];
//
//        for (int i = 0; i < mazeGeneratingThreads.length; i++) {
////            mazeGeneratingThreads[i] = new ThreadMazeGenerating(i);
//            if(i != 5)
//                mazeSolvingThreads[i] = new ThreadSolveSearchProblem(i);
//            else
//            {
//                try
//                {
//                    File sol = new File(System.getProperty("java.io.tmpdir") + "\\solverStrategySolutions\\0");
//                    if(sol.exists())
//                    {
//                        ObjectInputStream readSol = new ObjectInputStream(new FileInputStream(sol));
//
//
//                        Object maze = readSol.readObject();
//                        if (maze instanceof byte[])
//                        {
//                            InputStream decomp = new MyDecompressorInputStream(new ByteArrayInputStream((byte[])maze));
//                            int byteRead = 0,counter = 0, height = 0,width = 0;
//                            while (counter < 4)
//                            {
//                                byteRead = decomp.read();
//                                if(byteRead == 255)
//                                    counter++;
//                            }
//
//                            while(counter < 6)
//                            {
//                                byteRead = decomp.read();
//                                if(byteRead == 255)
//                                {
//                                    counter++;
//                                }
//                                else
//                                {
//                                    if(counter == 4)
//                                    {
//                                        height += byteRead;
//                                    }
//                                    else if (counter == 5)
//                                    {
//                                        width += byteRead;
//                                    }
//                                }
//                            }
//
//                            decomp = new MyDecompressorInputStream(new ByteArrayInputStream((byte[])maze));
//                            byte[] mazeArray = new byte[(height*width)+54];
//                            int x = decomp.read(mazeArray);
//                            Maze openedMaze = new Maze(mazeArray);
//                            mazeSolvingThreads[i] = new ThreadSolveSearchProblem(i,openedMaze);
//                        }
//                        readSol.close();
//                    }
//                    else
//                    {
//                        mazeSolvingThreads[i] = new ThreadSolveSearchProblem(i);
//                    }
//                }
//                catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        }
//        //Threads start
//        for (int i = 0; i < mazeGeneratingThreads.length; i++) {
////            System.out.println("\nMazeGenerating | Thread index: "+i+" Thread Id: "+ Thread.currentThread().getId());
////            mazeGeneratingThreads[i].start();
//            mazeSolvingThreads[i].start();
//        }
//        //Wait for all the threads to join
//        for (int i = 0; i < mazeGeneratingThreads.length; i++) {
//            try{
////                mazeGeneratingThreads[i].join();
//                mazeSolvingThreads[i].join();
////                System.out.println("Main: Thread" + i + " is finished");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
////        CommunicateWithServer_MazeGenerating();
////        CommunicateWithServer_SolveSearchProblem();
////        CommunicateWithServer_SolveSearchProblem();
//        //CommunicateWithServer_StringReverser();
//
//        //Stopping all servers
////        mazeGeneratingServer.stop();
////        System.out.println("Main: Calling to stop server");
//        solveSearchProblemServer.stop();
//        //stringReverserServer.stop();
//    }
//
//    private static class ThreadMazeGenerating extends Thread {
//        private int threadId;
//
//        public ThreadMazeGenerating(int threadId) {
//            this.threadId = threadId;
//        }
//
//        public int getThreadId() {
//            return threadId;
//        }
//
//        public void run(){
//            CommunicateWithServer_MazeGenerating(50,50);
//        }
//    }
//
//
//    private static class ThreadSolveSearchProblem extends Thread {
//        private int threadId;
//        private Maze maze;
//
//        public ThreadSolveSearchProblem(int threadId, Maze maze) {
//            this.threadId = threadId;
//            this.maze = maze;
//        }
//
//        public ThreadSolveSearchProblem(int threadId) {
//            this.threadId = threadId;
//            MyMazeGenerator mazeGenerator = new MyMazeGenerator();
//            this.maze = mazeGenerator.generate(300,300);
//        }
//
//        public void run(){
//            CommunicateWithServer_SolveSearchProblem(maze);
//        }
//    }
//
//
///*
//    private static void CommunicateWithServer_MazeGenerating() {
//        try {
//            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
//                @Override
//                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
//                    try {
//                        System.out.println("Client: Client trying to connect to Server");
//                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
//                        System.out.println("Client: OutputStream to Server is set");
//                        MyDecompressorInputStream decompressorInputStream = new MyDecompressorInputStream(inFromServer);
//                        ObjectInputStream fromServer = new ObjectInputStream(decompressorInputStream);
//                        //Here it will DIe
//
//                        System.out.println("Client: OutputStream from Server is set");
//                        System.out.println("Client: Client Connected to Server");
//                        toServer.flush();
//                        int row = 1000;
//                        int col = 1000;
//                        int[] mazeDimensions = new int[]{row, col};
//                        System.out.println("Client: Sending Dimensions");
//                        toServer.writeObject(mazeDimensions); //send maze dimensions to server
//                        toServer.flush();
//                        System.out.println("Client: Dimensions sent");
//                        Object compMaze = fromServer.readObject();
//                        byte[] byteTest = new byte[row*col+12];
////                        Object compMaze = fromServer.read(byteTest);
//                        byte[] compressedMaze = byteTest; //read generated maze (compressed with MyCompressor) from server
//                        for (byte b :
//                                compressedMaze) {
//                            System.out.print((int) b + " ");
//                        }
//                        System.out.println();
//                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
//                        byte[] decompressedMaze = new byte[byteTest.length]; //allocating byte[] for the decompressed maze -
//                        is.read(decompressedMaze); //Fill decompressedMaze with bytes
//                        for (byte b :
//                                decompressedMaze) {
//                            System.out.print((int)b + " ");
//                        }
//                        System.out.println();
//                        Maze maze = new Maze(decompressedMaze);
////                        maze.print();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
////            client.start();
//            client.communicateWithServer();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//*/
//
//    private static void CommunicateWithServer_MazeGenerating(int row, int col) {
//        try {
//            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
//                @Override
//                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
//                    try {
//                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
//                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
//                        toServer.flush();
//                        int[] mazeDimensions = new int[]{row, col};
//                        toServer.writeObject(mazeDimensions); //send maze dimensions to server
//                        toServer.flush();
//                        byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
//                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
//                        byte[] decompressedMaze = new byte[row*col+12 /*CHANGE SIZE ACCORDING TO YOU MAZE SIZE*/]; //allocating byte[] for the decompressed maze -
//                        is.read(decompressedMaze); //Fill decompressedMaze with bytes
//                        Maze maze = new Maze(decompressedMaze);
////                        maze.print();
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
//
//
///*
//    private static void CommunicateWithServer_SolveSearchProblem() {
//        try {
//            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
//                @Override
//                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
//                    try {
//                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
//                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
//                        toServer.flush();
//                        MyMazeGenerator mg = new MyMazeGenerator();
//                        Maze maze = mg.generate(1000, 1000);
//                        maze.print();
//                        toServer.writeObject(maze); //send maze to server
//                        toServer.flush();
//                        Solution mazeSolution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
//
//                        //Print Maze Solution retrieved from the server
//                        System.out.println(String.format("Solution steps: %s", mazeSolution));
//                        ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
//                        for (int i = 0; i < mazeSolutionSteps.size(); i++) {
//                            System.out.println(String.format("%s. %s", i, mazeSolutionSteps.get(i).toString()));
//                        }
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
//*/
//private static void CommunicateWithServer_SolveSearchProblem(Maze myMaze) {
//    try {
//        Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
//            @Override
//            public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
//                try {
//                    ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
//                    ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
//                    toServer.flush();
////                    myMaze.print();
//                    toServer.writeObject(myMaze); //send maze to server
//                    toServer.flush();
//                    Solution mazeSolution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
//
//                    //Print Maze Solution retrieved from the server
//                    //System.out.println(String.format("Solution steps: %s", mazeSolution));
////                    ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
////                    for (int i = 0; i < mazeSolutionSteps.size(); i++) {
////                        System.out.println(String.format("%s. %s", i, mazeSolutionSteps.get(i).toString()));
////                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        client.communicateWithServer();
////        System.out.println("Test: client Finished Communication");
//    } catch (UnknownHostException e) {
//        e.printStackTrace();
//    }
//}
////    private static void CommunicateWithServer_StringReverser() {
////        try {
////            Client client = new Client(InetAddress.getLocalHost(), 5402, new IClientStrategy() {
////                @Override
////                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
////                    try {
////                        BufferedReader fromServer = new BufferedReader(new InputStreamReader(inFromServer));
////                        PrintWriter toServer = new PrintWriter(outToServer);
////
////                        String message = "Client Message";
////                        String serverResponse;
////                        toServer.write(message + "\n");
////                        toServer.flush();
////                        serverResponse = fromServer.readLine();
////                        System.out.println(String.format("Server response: %s", serverResponse));
////                        toServer.flush();
////                        fromServer.close();
////                        toServer.close();
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
////                }
////            });
////            client.communicateWithServer();
////        } catch (UnknownHostException e) {
////            e.printStackTrace();
////        }
////    }
//}
//

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

/**
 * Created by Aviadjo on 3/27/2017.
 */
public class RunCommunicateWithServers {
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
        CommunicateWithServer_MazeGenerating();
        CommunicateWithServer_SolveSearchProblem();
        //CommunicateWithServer_StringReverser();

        //Stopping all servers
        mazeGeneratingServer.stop();
        solveSearchProblemServer.stop();
        //stringReverserServer.stop();
    }

    private static void CommunicateWithServer_MazeGenerating() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{50, 50};
                        toServer.writeObject(mazeDimensions); //send maze dimensions to server
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[(50*50) + 54 /*CHANGE SIZE ACCORDING TO YOU MAZE SIZE*/]; //allocating byte[] for the decompressed maze -
                        is.read(decompressedMaze); //Fill decompressedMaze with bytes
                        Maze maze = new Maze(decompressedMaze);
                        maze.print();
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
                        Maze maze = mg.generate(50, 50);
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

    private static void CommunicateWithServer_StringReverser() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5402, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        BufferedReader fromServer = new BufferedReader(new InputStreamReader(inFromServer));
                        PrintWriter toServer = new PrintWriter(outToServer);

                        String message = "Client Message";
                        String serverResponse;
                        toServer.write(message + "\n");
                        toServer.flush();
                        serverResponse = fromServer.readLine();
                        System.out.println(String.format("Server response: %s", serverResponse));
                        toServer.flush();
                        fromServer.close();
                        toServer.close();
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
}