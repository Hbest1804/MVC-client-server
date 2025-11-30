package Controller;

import Model.ClientModel;
import Views.LoginViews;
import Views.AdminViews;
import Views.NVViews;
import Views.AccountViews;

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

    // Điều hướng sau khi đăng nhập
    public void navigateAfterLogin(String role, LoginViews loginView) {
        if (role.equalsIgnoreCase("admin")) {
            showAdmin();
        } else if (role.equalsIgnoreCase("nhanvien")) {
            showStaffDashboard();
        }
        loginView.dispose();
    }

    // Trang Admin
    private void showAdmin() {
        AdminViews adminView = new AdminViews(this);
        adminView.setVisible(true);
    }

    // Trang nhân viên
    private void showStaffDashboard() {
        NVViews nvView = new NVViews(this);
        nvView.setVisible(true);
    }

    // Mở trang quản lý tài khoản
    public void openAccountManager() {
        Model.Usertxt txtModel = new Model.Usertxt();
        AccountController accountController = new AccountController(txtModel);

        new AccountViews(accountController, this);
    }

    // Hàm quay lại trang quản trị
    public void backToAdmin() {
        showAdmin();
    }
}
