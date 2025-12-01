package Views;

import Controller.AccountController;
import Controller.ClientController;
import Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AccountViews extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnEdit, btnDelete, btnBack;
    private AccountController controller;
    private ClientController mainController;

    public AccountViews(AccountController controller, ClientController mainController) {
        this.controller = controller;
        this.mainController = mainController;

        initUI();
        loadTable();
        initEvents();

        setVisible(true);
    }

    private void initUI() {
        setTitle("Quản lý tài khoản");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel(
                new Object[]{"Username", "Password", "Vai trò", "Phone", "Mã NV"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnBack = new JButton("Quay lại");

        JPanel panelButtons = new JPanel();
        panelButtons.add(btnAdd);
        panelButtons.add(btnEdit);
        panelButtons.add(btnDelete);
        panelButtons.add(btnBack);

        add(scrollPane, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);
    }

    private void initEvents() {
        btnAdd.addActionListener(e -> {
            User user = inputUserData(null); // Nhập liệu mới
            if (user != null && controller.addAccount(user)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!\nMã NV: " + user.getEmployeeCode());
                loadTable();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }
        });

        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String username = (String) tableModel.getValueAt(row, 0);
                User user = inputUserData(username); // Nhập liệu sửa
                if (user != null && controller.editAccount(user)) {
                    JOptionPane.showMessageDialog(this, "Sửa thành công!");
                    loadTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Sửa thất bại!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản để sửa!");
            }
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String username = (String) tableModel.getValueAt(row, 0);
                if (controller.deleteAccount(username)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    loadTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản để xóa!");
            }
        });

        btnBack.addActionListener(e -> {
            mainController.backToAdmin();
            dispose();
        });
    }

    private User inputUserData(String username) {
        JTextField txtUsername = new JTextField();
        if (username != null) {
            txtUsername.setText(username);
            txtUsername.setEnabled(false);
        }

        JTextField txtPassword = new JTextField();
        JTextField txtPhone = new JTextField();

        String[] roles = {"admin", "nhanvien"};
        JComboBox<String> roleBox = new JComboBox<>(roles);

        Object[] inputs = {
                "Username:", txtUsername,
                "Password:", txtPassword,
                "Vai trò:", roleBox,
                "Phone:", txtPhone
        };

        int result = JOptionPane.showConfirmDialog(this, inputs,
                username == null ? "Thêm tài khoản" : "Sửa tài khoản",
                JOptionPane.OK_CANCEL_OPTION);

        if (result != JOptionPane.OK_OPTION) return null;

        String u = txtUsername.getText().trim();
        String p = txtPassword.getText().trim();
        String r = (String) roleBox.getSelectedItem();
        String ph = txtPhone.getText().trim();

        if (u.isEmpty() || p.isEmpty() || ph.isEmpty()) return null;

        String employeeCode = username == null ? controller.generateEmployeeCode() : null;

        return new User(u, p, r, ph, username == null ? employeeCode : tableModel.getValueAt(table.getSelectedRow(), 4).toString());
    }

    public void loadTable() {
        tableModel.setRowCount(0);
        List<User> users = controller.loadAccounts();
        for (User user : users) {
            tableModel.addRow(new Object[]{
                    user.getUsername(),
                    user.getPassword(),
                    user.getRole(),
                    user.getPhone(),
                    user.getEmployeeCode()
            });
        }
    }
}
