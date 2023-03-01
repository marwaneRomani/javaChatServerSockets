package org.est;

import org.est.chatElements.Message;
import org.est.chatElements.User;

import java.io.*;
import java.net.Socket;

public class Client1 {

    private User user;

    public Client1() {
        User client1 = new User();
        client1.setId("client 1");
        client1.setName("client 1");

        this.user = client1;
    }

    private void sendMessage(String m, User to ) {

        Message message = new Message();
        message.setId("message 1");
        message.setMessage(m);
        message.setSender(this.user);
        message.setReceiver(to);

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
            objectOutputStream.writeObject(message);

            // received messages

            Message receivedMessage = (Message) objectInputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        User client2 = new User();
        client2.setId("client 2");
        client2.setName("client 2");

        Client1 client1 = new Client1();

        client1.sendMessage("hello client 2 this is client 1.", client2);
    }
}