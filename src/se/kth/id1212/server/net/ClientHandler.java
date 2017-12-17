package se.kth.id1212.server.net;

import se.kth.id1212.common.Constants;
import se.kth.id1212.common.MessageType;
import se.kth.id1212.server.controller.Controller;

import java.io.*;
import java.net.Socket;
import java.util.StringJoiner;


/**
 * Created by Robin on 2017-11-16.
 */
public class ClientHandler implements Runnable{

    private final Socket clientSocket;

    private final Controller controller = new Controller();

    private boolean connected;

    private BufferedReader fromClient;
    private PrintWriter toClient;

    ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        connected = true;
    }

    @Override
    public void run() {
        try {
            boolean autoFlush = true;
            fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            toClient = new PrintWriter(clientSocket.getOutputStream(), autoFlush);
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
        sendMessages(controller.getRules());
        while (connected) {
            try {

                Message message = new Message(fromClient.readLine());
                switch (message.getMessageType()) {
                    case START:
                        sendMessages(controller.startGame());
                        break;
                    case GUESS:
                        sendMessages(controller.guess(message.getMessageBody()));
                        break;
                    case DISCONNECT:
                        disconnectClient();
                        break;
                    default:
                        throw new Exception("Received corrupt message: " + message.toString());
                }
            } catch (Exception e) {
                disconnectClient();
            }
        }
    }

    private void sendMessages(String[] messages) {
        for (String m:messages) {
            sendMessage(m);
        }
    }

    private void sendMessage(String message) {
        StringJoiner joiner = new StringJoiner(Constants.MESSAGE_DELIMITER);
        joiner.add(MessageType.GAME.toString());
        joiner.add(message);
        toClient.println(joiner.toString());
    }

    private void disconnectClient() {
        try {
            clientSocket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        connected = false;
    }
}
