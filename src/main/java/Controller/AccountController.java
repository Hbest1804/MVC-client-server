package Controller;

import Model.User;
import Model.Usertxt;
import Database.Database;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountController {
    private Usertxt txtModel;
    private Database db;

    public AccountController(Usertxt txtModel) {
        this.txtModel = txtModel;
        this.db = new Database();
    }

    // Load tất cả tài khoản từ TXT + DB
    public List<User> loadAccounts() {
        List<User> allUsers = new ArrayList<>();

        // Load từ TXT
        List<User> txtUsers = txtModel.readUsers();
        allUsers.addAll(txtUsers);

        // Load từ MySQL
        String sql = "SELECT username, password, role FROM users";
        try (Connection conn = db.getConnection()) {
            if (conn == null || conn.isClosed()) {
                JOptionPane.showMessageDialog(null, "Không thể kết nối DB!");
                return allUsers;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String role = rs.getString("role");

                    // Kiểm tra nếu đã có trong danh sách TXT thì không thêm trùng
                    boolean exists = allUsers.stream()
                            .anyMatch(u -> u.getUsername().equals(username));
                    if (!exists) {
                        allUsers.add(new User(username, password, role));
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allUsers;
    }


    public void addAccount() {
        JTextField txtUsername = new JTextField();
        JTextField txtPassword = new JTextField();
        String[] roles = {"admin", "nhanvien"};
        JComboBox<String> roleBox = new JComboBox<>(roles);

        Object[] inputs = {
                "Username:", txtUsername,
                "Password:", txtPassword,
                "Role:", roleBox
        };

        int result = JOptionPane.showConfirmDialog(null, inputs, "Thêm tài khoản", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) return;

        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String role = (String) roleBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        User user = new User(username, password, role);


        boolean txtOk = txtModel.addUser(user);

        boolean dbOk = false;
        String checkSql = "SELECT COUNT(*) FROM users WHERE username=?";
        String insertSql = "INSERT INTO users(username, password, role) VALUES(?,?,?)";

        try (Connection conn = db.getConnection()) {
            if (conn == null || conn.isClosed()) {
                JOptionPane.showMessageDialog(null, "Không thể kết nối DB!");
            } else {

                try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                    checkStmt.setString(1, username);
                    try (ResultSet rs = checkStmt.executeQuery()) {
                        if (rs.next() && rs.getInt(1) > 0) {
                            JOptionPane.showMessageDialog(null, "Username đã tồn tại trong DB!");
                        } else {
                            // Thêm mới
                            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                                ps.setString(1, username);
                                ps.setString(2, password);
                                ps.setString(3, role);
                                dbOk = ps.executeUpdate() > 0;
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (txtOk && dbOk) {
            JOptionPane.showMessageDialog(null, "Thêm tài khoản thành công vào cả TXT và DB!");
        } else if (txtOk) {
            JOptionPane.showMessageDialog(null, "Thêm vào TXT thành công, DB thất bại!");
        } else if (dbOk) {
            JOptionPane.showMessageDialog(null, "Thêm vào DB thành công, TXT thất bại!");
        } else {
            JOptionPane.showMessageDialog(null, "Thêm thất bại!");
        }
    }
}
