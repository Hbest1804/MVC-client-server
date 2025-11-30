package Views;

import Controller.ClientController;

import javax.swing.*;
import java.awt.*;

public class AdminViews extends JFrame {
    private JButton btnQuanLyTaiKhoan;
    private JButton btnQuanLySanPham;
    private JButton btnQuanLyDonHang;
    private JButton btnBackLogin; // nút quay lại

    public AdminViews(ClientController mainController) {
        setTitle("Admin Dashboard");
        setSize(500, 350); // tăng chiều cao để thêm nút
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel chính dùng BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Tiêu đề JLabel
        JLabel titleLabel = new JLabel("Trang Quản Trị Bán Hàng", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel cho các nút tile
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 20, 20));

        btnQuanLyTaiKhoan = new JButton("Quản lý tài khoản");
        btnQuanLyTaiKhoan.setPreferredSize(new Dimension(120, 120));
        btnQuanLyTaiKhoan.addActionListener(e -> mainController.openAccountManager());
        buttonPanel.add(btnQuanLyTaiKhoan);

        btnQuanLySanPham = new JButton("Quản lý sản phẩm");
        btnQuanLySanPham.setPreferredSize(new Dimension(120, 120));
        buttonPanel.add(btnQuanLySanPham);

        btnQuanLyDonHang = new JButton("Quản lý đơn hàng");
        btnQuanLyDonHang.setPreferredSize(new Dimension(120, 120));
        buttonPanel.add(btnQuanLyDonHang);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Panel dưới cho nút quay lại
        JPanel bottomPanel = new JPanel();
        btnBackLogin = new JButton("Đăng xuất");
        btnBackLogin.addActionListener(e -> {

            this.dispose();

            mainController.showLogin();
        });
        bottomPanel.add(btnBackLogin);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}
