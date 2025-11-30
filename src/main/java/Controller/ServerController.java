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

            // ===== 2) GIỮ KẾT NỐI THEO ROLE =====
            while (true) {
                Object requestObj = ois.readObject();
                if (requestObj == null) continue;

                String command = requestObj.toString().trim();

                if (command.equals("LOGOUT")) {
                    view.showMessage(username + " đã đăng xuất!");
                    oos.writeObject("SERVER_LOGOUT_OK");
                    oos.flush();
                    break;
                }

                // ===== Xử lý theo role =====
                if (user.getRole().equalsIgnoreCase("admin")) {
                    switch (command) {
                        case "QUAN_LY_TAI_KHOAN":
                            view.showMessage(username + " đang quản lý tài khoản");
                            oos.writeObject("SERVER_OK_ACCOUNT");
                            break;
                        case "QUAN_LY_SAN_PHAM":
                            view.showMessage(username + " đang quản lý sản phẩm");
                            oos.writeObject("SERVER_OK_PRODUCT");
                            break;
                        case "QUAN_LY_DON_HANG":
                            view.showMessage(username + " đang quản lý đơn hàng");
                            oos.writeObject("SERVER_OK_ORDER");
                            break;
                        default:
                            oos.writeObject("SERVER_UNKNOWN_CMD");
                    }
                } else if (user.getRole().equalsIgnoreCase("nhanvien")) {
                    switch (command) {
                        case "BAN_HANG":
                            view.showMessage(username + " đang bán hàng...");
                            oos.writeObject("SERVER_OK_BAN_HANG");
                            break;
                        case "XEM_SAN_PHAM":
                            view.showMessage(username + " đang xem sản phẩm");
                            oos.writeObject("SERVER_OK_XEM_SP");
                            break;
                        default:
                            oos.writeObject("SERVER_UNKNOWN_CMD");
                    }
                } else {
                    oos.writeObject("SERVER_UNKNOWN_ROLE");
                }

                oos.flush();
            }

        } catch (Exception e) {
            view.showMessage("Lỗi với client " + clientName + ": " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                view.showMessage("Đã đóng kết nối với " + clientName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
