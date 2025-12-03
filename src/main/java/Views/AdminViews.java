package Views;

import Controller.ClientController;

import javax.swing.*;
import java.awt.*;

public class AdminViews extends JFrame {

    private JButton btnQuanLyTaiKhoan;
    private JButton btnQuanLySanPham;
    private JButton btnQuanLyDonHang;
    private JButton btnBackLogin;

    public AdminViews(ClientController mainController) {

        setTitle("Admin Dashboard");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // ===================== SIDEBAR (Gradient + đẹp) ======================
        JPanel sidebar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(33, 150, 243),
                        0, getHeight(), new Color(30, 136, 229));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sidebar.setPreferredSize(new Dimension(220, getHeight()));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel adminTitle = new JLabel("<html><center><br><font size=5 color='white'>ADMIN</font></center></html>");
        adminTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(adminTitle);
        sidebar.add(Box.createVerticalStrut(40));

        // Nút logout
        btnBackLogin = new JButton("🚪 Đăng xuất");
        styleSidebarButton(btnBackLogin);
        btnBackLogin.addActionListener(e -> {
            mainController.logout(this);  // truyền this để đóng view
        });
        sidebar.add(btnBackLogin);
        sidebar.add(Box.createVerticalGlue());

        add(sidebar, BorderLayout.WEST);

        // ===================== CONTENT ======================
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(new Color(245, 245, 245));

        JLabel header = new JLabel("Trang Quản Trị Bán Hàng", SwingConstants.LEFT);
        header.setFont(new Font("Segoe UI", Font.BOLD, 30));
        header.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        content.add(header, BorderLayout.NORTH);

        JPanel cardPanel = new JPanel(new GridLayout(1, 3, 30, 30));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        cardPanel.setOpaque(false);

        btnQuanLyTaiKhoan = createCard("👤", "Quản Lý\nTài Khoản");
        btnQuanLyTaiKhoan.addActionListener(e -> mainController.openAccountManager());

        btnQuanLySanPham = createCard("📦", "Quản Lý\nSản Phẩm");
        btnQuanLyDonHang = createCard("🧾", "Quản Lý\nĐơn Hàng");

        cardPanel.add(btnQuanLyTaiKhoan);
        cardPanel.add(btnQuanLySanPham);
        cardPanel.add(btnQuanLyDonHang);

        content.add(cardPanel, BorderLayout.CENTER);

        add(content, BorderLayout.CENTER);
    }

    // ===================== CARD UI ======================
    private JButton createCard(String icon, String text) {
        JButton btn = new JButton("<html><center>" + icon + "<br><br>" + text.replace("\n", "<br>") + "</center></html>");
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(230, 230, 250));
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.WHITE);
            }
        });

        return btn;
    }

    // ===================== STYLE SIDEBAR BUTTON ======================
    private void styleSidebarButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(33, 150, 243));
        btn.setFocusPainted(false);
        btn.setMaximumSize(new Dimension(180, 50));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(30, 136, 229));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(33, 150, 243));
            }
        });
    }
}
