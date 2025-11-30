package Views;

import Controller.ClientController;

import javax.swing.*;
import java.awt.*;

public class NVViews extends JFrame {

    private JButton btnBanHang;
    private JButton btnQuanLyDonHang;
    private JButton btnXemSanPham;
    private JButton btnDangXuat;

    public NVViews(ClientController mainController) {
        setTitle("Trang Nhân Viên Bán Hàng");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));

        JLabel title = new JLabel("Hệ thống bán hàng", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title);

        btnBanHang = new JButton("Bán Hàng");
        btnQuanLyDonHang = new JButton("Quản Lý Đơn Hàng");
        btnXemSanPham = new JButton("Xem Sản Phẩm");
        btnDangXuat = new JButton("Đăng Xuất");

        add(btnBanHang);
        add(btnQuanLyDonHang);
        add(btnXemSanPham);
        add(btnDangXuat);
    }

    public JButton getBtnBanHang() { return btnBanHang; }
    public JButton getBtnQuanLyDonHang() { return btnQuanLyDonHang; }
    public JButton getBtnXemSanPham() { return btnXemSanPham; }
    public JButton getBtnDangXuat() { return btnDangXuat; }
}
