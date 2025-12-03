package Controller;

import Model.ClientModel;
import Views.LoginViews;
import Views.AdminViews;
import Views.NVViews;
import Views.AccountViews;

import javax.swing.*;

public class ClientController {

    private ClientModel model;
    private AdminViews adminView;
    private NVViews nvView;

    public ClientController(ClientModel model) {
        this.model = model;
        showLogin();
    }


    public void showLogin() {

        this.model = new ClientModel("localhost", 9999);
        LoginViews loginView = new LoginViews();
        new LoginController(loginView, model, this);
        loginView.setVisible(true);
    }


    public void navigateAfterLogin(String role, LoginViews loginView) {
        loginView.dispose(); // đóng login
        if (role.equalsIgnoreCase("admin")) showAdmin();
        else if (role.equalsIgnoreCase("nhanvien")) showStaffDashboard();
    }


    private void showAdmin() {
        if (adminView == null) {
            adminView = new AdminViews(this);
        }
        adminView.setVisible(true);
        if (nvView != null) nvView.setVisible(false); // ẩn NV nếu đang mở
    }


    private void showStaffDashboard() {
        if (nvView == null) {
            nvView = new NVViews(this);
        }
        nvView.setVisible(true);
        if (adminView != null) adminView.setVisible(false);
    }


    public void openAccountManager() {
        Model.Usertxt txtModel = new Model.Usertxt();
        AccountController accountController = new AccountController(txtModel);
        new AccountViews(accountController, this);
    }


    public void logout(JFrame currentView) {
        try {
            if (model != null) {
                model.send("LOGOUT");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (model != null) model.closeConnection();
            if (currentView != null) currentView.dispose();
            showLogin();
        }
    }


    public void backToAdmin() {
        if (nvView != null) nvView.setVisible(false);
        showAdmin();
    }
}
