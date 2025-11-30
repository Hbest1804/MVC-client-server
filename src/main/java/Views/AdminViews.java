package Views;

import Controller.ClientController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminViews extends JFrame {
    private JButton btnQuanLyTaiKhoan;

    public AdminViews(ClientController mainController) {
        setTitle("Trang quản trị");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        btnQuanLyTaiKhoan = new JButton("Quản lý tài khoản");

        btnQuanLyTaiKhoan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.openAccountManager();
            }
        });

        add(btnQuanLyTaiKhoan);
    }
}
