package se.kth.id1212.server.net;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robin on 2017-11-16.
 */
public class Server {
    private static final int LINGER_TIME = 5000;
    private static final int TIMEOUT_HALF_HOUR = 1800000;
    private static final int PORT = 8080;

    /**
     * Starts the server
     * @param args There's no arguments
     */
    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }

    private void run() {
        try {
            ServerSocket listeningSocket = new ServerSocket(PORT);
            while (true) {
                Socket clientSocket = listeningSocket.accept();
                startClientHandler(clientSocket);
                System.out.println("Client connected");
            }
        } catch (IOException e) {
            System.err.println("Server failure.");
        }
    }

    private void startClientHandler(Socket clientSocket) throws SocketException {
        clientSocket.setSoLinger(true, LINGER_TIME);
        clientSocket.setSoTimeout(TIMEOUT_HALF_HOUR);
        ClientHandler handler = new ClientHandler(clientSocket);
        Thread handlerThread = new Thread(handler);
        handlerThread.setPriority(Thread.MAX_PRIORITY);
        handlerThread.start();
    }
}
