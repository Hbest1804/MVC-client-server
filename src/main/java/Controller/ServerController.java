package Controller;

import Model.ServerModel;
import Model.User;
import Views.ServerViews;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerController {
    private ServerModel model;
    private ServerViews view;

    public ServerController(ServerModel model, ServerViews view) {
        this.model = model;
        this.view = view;
    }

    public void handleClient(Socket clientSocket) {

        String clientName = clientSocket.getInetAddress().toString() + ":" + clientSocket.getPort();
        view.showMessage("Client đã kết nối: " + clientName);

        try {
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());

            String username = (String) ois.readObject();
            String password = (String) ois.readObject();

            User user = model.loginDatabase(username, password);
            if (user == null) {
                user = model.loginTxt(username, password);
            }

            if (user != null) {
                oos.writeObject("SUCCESS:" + user.getRole());
                oos.flush();
                view.showMessage(username + " đăng nhập thành công!");
            } else {
                oos.writeObject("FAIL");
                oos.flush();
                view.showMessage(username + " đăng nhập thất bại!");
            }

        } catch (Exception e) {
            view.showMessage("Lỗi client: " + clientName);
        } finally {
            view.showMessage("Client đã thoát: " + clientName);
        }
    }
}
