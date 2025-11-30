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
    private ClientController mainController;   // <<< THÊM

    public AccountViews(AccountController controller, ClientController mainController) {
        this.controller = controller;
        this.mainController = mainController;

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

        loadTable();

        btnAdd.addActionListener(e -> {
            controller.addAccount();
            loadTable();
        });

        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String username = (String) tableModel.getValueAt(row, 0);
                controller.editAccount(username);
                loadTable();
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản để sửa!");
            }
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String username = (String) tableModel.getValueAt(row, 0);
                controller.deleteAccount(username);
                loadTable();
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản để xóa!");
            }
        });

        // NÚT QUAY LẠI — chính xác
        btnBack.addActionListener(e -> {
            mainController.backToAdmin();
            dispose();
        });

        setVisible(true);
    }

    private void loadTable() {
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
