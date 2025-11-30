package Controller;

import Model.ServerModel;
import Model.User;
import Views.ServerViews;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

// Controller cho Server, xử lý logic đăng nhập từ client
public class ServerController {
    private ServerModel model;
    private ServerViews view;

    public ServerController(ServerModel model, ServerViews view) {
        this.model = model;
        this.view = view;
    }
// Xử lý đăng nhập từ client
    public void handleClient(Socket clientSocket) {
        String clientName = clientSocket.getInetAddress().toString() + ":" + clientSocket.getPort();

        try (ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream())) {


            String username = (String) ois.readObject();
            String password = (String) ois.readObject();


            User user = model.loginDatabase(username, password);
            if (user == null) {
                user = model.loginTxt(username, password);
            }

            if (user != null) {
                oos.writeObject("SUCCESS:" + user.getRole());
                oos.flush();
                view.showMessage(username + " đăng nhập thành công với tư cách: " + user.getRole());
            } else {
                oos.writeObject("FAIL");
                oos.flush();
                view.showMessage(username + " đăng nhập thất bại!");
            }

        } catch (Exception e) {
            view.showMessage("Lỗi client: " + clientName + " | " + e.getMessage());
        } finally {
            try {
                if (!clientSocket.isClosed()) {
                    clientSocket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}
