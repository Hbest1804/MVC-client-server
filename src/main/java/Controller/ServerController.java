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

        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ois = new ObjectInputStream(clientSocket.getInputStream());

            // ===== 1) NHẬN LOGIN =====
            String username = (String) ois.readObject();
            String password = (String) ois.readObject();

            User user = model.loginDatabase(username, password);
            if (user == null) user = model.loginTxt(username, password);

            if (user == null) {
                oos.writeObject("FAIL");
                oos.flush();
                view.showMessage(username + " đăng nhập thất bại!");
                return;
            }

            oos.writeObject("SUCCESS:" + user.getRole());
            oos.flush();
            view.showMessage(username + " đăng nhập thành công (" + user.getRole() + ")");


            while (true) {
                Object requestObj = ois.readObject();
                if (requestObj == null) continue;

                String command = requestObj.toString().trim();

                if (command.equalsIgnoreCase("LOGOUT")) {
                    view.showMessage(username + " đã đăng xuất.");
                    break;
                } else {
                    view.showMessage("Nhận từ " + username + ": " + command);
                    oos.writeObject("Server đã nhận: " + command);
                    oos.flush();
                }



            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
