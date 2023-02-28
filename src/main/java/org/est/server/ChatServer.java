package org.est.server;

import org.est.chat.Message;
import org.est.chat.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

public class ChatServer extends Thread {

    private Map<User, Conversation > connectedUsers = new HashMap<>();
    private List<Message> pendindMessages = new ArrayList<>();

    public static void main(String[] args) {
        new ChatServer().start();
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(4002);

            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                new Conversation(socket).start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private class Conversation extends Thread {
        protected User user;
        protected Socket socket;

        private Conversation(Socket socket) {
                this.socket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream inputStream = socket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

                OutputStream outputStream = socket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

                this.user = (User) objectInputStream.readObject();

                connectedUsers.put(user,this);

                List<Message> pendingMessagesList = pendindMessages.stream().filter(message -> message.getReceiver().equals(user)).collect(Collectors.toList());

                for (Message pendingMessage : pendingMessagesList) {
                    objectOutputStream.writeObject(pendingMessage);
                }

                while (!socket.isClosed()) {
                    Message message = (Message) objectInputStream.readObject();
                    User receiver = message.getReceiver();

                    System.out.println(message.getMessage());

                    Conversation conversation = connectedUsers.get(receiver);

                    if (conversation == null) {
                        System.out.println("destination is not available pushing the message to the pending list");
                        pendindMessages.add(message);
                    }
                    else {
                        ObjectOutputStream directionOutputStream = new ObjectOutputStream(conversation.socket.getOutputStream());
                        directionOutputStream.writeObject(message);
                    }
                }

                connectedUsers.remove(this,user);

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }
}






