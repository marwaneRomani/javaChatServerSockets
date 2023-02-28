package org.est;

import org.est.chat.Message;
import org.est.chat.User;

import java.io.*;
import java.net.Socket;

public class Client2 {
    private User user;

    public Client2() {
        User client1 = new User();
        client1.setId("client 2");
        client1.setName("client 2");

        this.user = client1;
    }

    private void sendMessage(String m) {
        try {
            Socket socket = new Socket("localhost", 4002);
            // get output stream
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            // get input stream
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);


            // send messages
            objectOutputStream.writeObject(this.user);

            // received messages
            Message receivedMessage = (Message) objectInputStream.readObject();
            System.out.println(" received " + receivedMessage.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Client2().sendMessage("hello client 1 this is client 2.");
    }
}
