package Model;

import java.io.*;
import java.net.Socket;

public class ClientModel {
    private String serverIP;
    private int serverPort;

    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public ClientModel(String serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    // Chỉ connect nếu chưa kết nối
    public void connect() throws Exception {
        if (socket == null || socket.isClosed()) {
            socket = new Socket(serverIP, serverPort);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        }
    }

    // Gửi username + password và nhận kết quả login
    public String login(String username, String password) {
        try {
            connect(); // giữ kết nối luôn mở

            oos.writeObject(username);
            oos.writeObject(password);
            oos.flush();

            return (String) ois.readObject();

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    // Gửi tin nhắn hoặc lệnh sau login
    public void send(String msg) throws IOException {
        oos.writeObject(msg);
        oos.flush();
    }

    // Nhận dữ liệu từ server sau login
    public Object receive() throws Exception {
        return ois.readObject();
    }

    // Đóng kết nối (khi tắt ứng dụng)
    public void close() {
        try {
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
