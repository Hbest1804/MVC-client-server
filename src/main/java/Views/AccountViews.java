package Views;

import Controller.AccountController;
import Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AccountViews extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnEdit, btnDelete;
    private AccountController controller;

    public AccountViews(AccountController controller) {
        this.controller = controller;

        setTitle("Quản lý tài khoản");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table model
        tableModel = new DefaultTableModel(new Object[]{"Username", "Password", "Role"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // không cho sửa trực tiếp
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);


        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");

        JPanel panelButtons = new JPanel();
        panelButtons.add(btnAdd);
        panelButtons.add(btnEdit);
        panelButtons.add(btnDelete);

        add(scrollPane, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);

        // Load dữ liệu ban đầu
        loadTable();

        // Sự kiện nút
        btnAdd.addActionListener(e -> {
            controller.addAccount();
            loadTable(); // tải lại dữ liệu sau khi thêm
        });

//        btnEdit.addActionListener(e -> {
//            int selectedRow = table.getSelectedRow();
//            if (selectedRow >= 0) {
//                String username = (String) tableModel.getValueAt(selectedRow, 0);
//                controller.editAccount(username); // bạn cần viết hàm editAccount trong controller
//                loadTable();
//            } else {
//                JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản để sửa!");
//            }
//        });

        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String username = (String) tableModel.getValueAt(selectedRow, 0);
//                controller.deleteAccount(username); // bạn cần viết hàm deleteAccount trong controller
                loadTable();
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản để xóa!");
            }
        });

        setVisible(true);
    }


    private void loadTable() {
        tableModel.setRowCount(0); // xóa dữ liệu cũ
        List<User> users = controller.loadAccounts();
        for (User user : users) {
            tableModel.addRow(new Object[]{user.getUsername(), user.getPassword(), user.getRole()});
        }
    }
}
