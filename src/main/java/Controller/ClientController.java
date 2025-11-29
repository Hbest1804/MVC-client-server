package Controller;

import Model.ClientModel;
import Views.LoginViews;

public class ClientController {

    private ClientModel model;

    public ClientController(ClientModel model) {
        this.model = model;

        showLogin();
    }


    public void showLogin() {
        LoginViews loginView = new LoginViews();
        new LoginController(loginView, model, this);
        loginView.setVisible(true);
    }


    public void navigateAfterLogin(String role) {
        if (role.equalsIgnoreCase("admin")) {
            showAdminDashboard();
        } else if (role.equalsIgnoreCase("nhanvien")) {
            showStaffDashboard();
        } else {
            System.out.println("Chuyển sang trang với role: " + role);
        }
    }

    private void showAdminDashboard() {
        System.out.println("Mở giao diện Admin...");

    }

    private void showStaffDashboard() {
        System.out.println("Mở giao diện Nhân viên...");

    }
}
