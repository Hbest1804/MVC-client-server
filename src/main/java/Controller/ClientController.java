package Controller;

import Model.ClientModel;
import Views.LoginViews;
import Views.AdminViews;

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

    // Điều hướng sau khi đăng nhập dựa trên vai trò
    public void navigateAfterLogin(String role) {
        if (role.equalsIgnoreCase("admin")) {
            showAdmin();
        } else if (role.equalsIgnoreCase("nhanvien")) {
            showStaffDashboard();
        } else {
            System.out.println("Chuyển sang trang với role: " + role);
        }
    }
    // Mở giao diện Admin
    private void showAdmin() {
        System.out.println("Mở giao diện Admin...");
        AdminViews adminView = new AdminViews(this);
        adminView.setVisible(true);
    }

    private void showStaffDashboard() {
        System.out.println("Mở giao diện Nhân viên...");
    }

    // Mở giao diện quản lý tài khoản
    public void openAccountManager() {
        Model.Usertxt txtModel = new Model.Usertxt();
        Controller.AccountController accountController = new Controller.AccountController(txtModel);
        new Views.AccountViews(accountController);
    }
}
