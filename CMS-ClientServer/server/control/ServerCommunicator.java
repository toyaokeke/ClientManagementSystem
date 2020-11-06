package server.control;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.model.ServerDatabase;

public class ServerCommunicator {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutputStream socketOut;
    private ObjectInputStream socketIn;
    private ExecutorService pool;
    private int port;
    private ServerDatabase serverDB;

    /**
     * Default constructor of server that is initialized with port and server
     * database
     * 
     * @param port     port number
     * @param serverDB database
     */
    public ServerCommunicator(int port, ServerDatabase serverDB) {
        try {
            this.port = port;
            serverSocket = new ServerSocket(this.port);
            pool = Executors.newCachedThreadPool();
            this.serverDB = serverDB;
            System.out.println("Server is running.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This accepts connection from clients to the server and creates a runnable
     * instance of their connection to the server
     */
    public void acceptConnections() {
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                System.out.println("A client has connected to the Client Management System.");
                openStreams();
                ServerInstance si = new ServerInstance(socketIn, socketOut, serverDB);
                runServer(si);
            } catch (IOException e) {
                System.err.println("Unable to establish connection...");
                e.printStackTrace();
            }
        }
    }

    /**
     * runs server instance in the threadpool
     * 
     * @param server server instance
     */
    public void runServer(ServerInstance server) {
        pool.execute(server);
    }

    /**
     * Opens IO streams of the client to the server
     */
    private void openStreams() {
        try {
            socketOut = new ObjectOutputStream(clientSocket.getOutputStream());
            socketIn = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            System.err.println("Unable to open IO streams...");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServerDatabase sdb = new ServerDatabase();
        sdb.createTableFromFile("clients.txt");
        ServerCommunicator sc = new ServerCommunicator(9999, sdb);
        sc.acceptConnections();
    }
}
