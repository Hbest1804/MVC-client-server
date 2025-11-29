package Server;

import Controller.ServerController;
import Model.ServerModel;
import Views.ServerViews;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        ServerModel model = new ServerModel();
        ServerViews view = new ServerViews();
        ServerController controller = new ServerController(model, view);

        try (ServerSocket serverSocket = new ServerSocket(9999)) {
            view.showMessage("Server đang chạy trên cổng 9999...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                view.showMessage("Kết nối mới từ: " + clientSocket.getInetAddress().toString() + ":" + clientSocket.getPort());


                new Thread(() -> controller.handleClient(clientSocket)).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
