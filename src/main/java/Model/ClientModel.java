package Model;

import java.io.*;
import java.net.Socket;

public class ClientModel {
    private String serverIP;
    private int serverPort;

    public ClientModel(String serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    public String login(String username, String password) {
        try (Socket socket = new Socket(serverIP, serverPort);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {


            oos.writeObject(username);
            oos.writeObject(password);
            oos.flush();


            return (String) ois.readObject();

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
}
