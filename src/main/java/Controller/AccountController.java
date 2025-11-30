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
import java.util.Random;

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
        String sql = "SELECT username, password, role, phone, employee_code FROM users";
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
                    String phone = rs.getString("phone");
                    String employeeCode = rs.getString("employee_code");

                    boolean exists = allUsers.stream()
                            .anyMatch(u -> u.getUsername().equals(username));
                    if (!exists) {
                        allUsers.add(new User(username, password, role, phone, employeeCode));
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allUsers;
    }

    // Thêm tài khoản mới
    public void addAccount() {
        JTextField txtUsername = new JTextField();
        JTextField txtPassword = new JTextField();
        JTextField txtPhone = new JTextField();
        String[] roles = {"admin", "nhanvien"};
        JComboBox<String> roleBox = new JComboBox<>(roles);

        Object[] inputs = {
                "Username:", txtUsername,
                "Password:", txtPassword,
                "Role:", roleBox,
                "Phone:", txtPhone
        };

        int result = JOptionPane.showConfirmDialog(null, inputs, "Thêm tài khoản", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) return;

        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String role = (String) roleBox.getSelectedItem();
        String phone = txtPhone.getText().trim();

        if (username.isEmpty() || password.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        // Tạo employeeCode ngẫu nhiên
        String employeeCode = generateEmployeeCode();

        User user = new User(username, password, role, phone, employeeCode);

        boolean success = true;

        // Thêm vào TXT
        if (!txtModel.addUser(user)) {
            success = false;
        }

        // Thêm vào MySQL
        String checkSql = "SELECT COUNT(*) FROM users WHERE username=?";
        String insertSql = "INSERT INTO users(username, password, role, phone, employee_code) VALUES(?,?,?,?,?)";

        try (Connection conn = db.getConnection()) {
            if (conn == null || conn.isClosed()) {
                success = false;
            } else {
                try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                    checkStmt.setString(1, username);
                    try (ResultSet rs = checkStmt.executeQuery()) {
                        if (rs.next() && rs.getInt(1) > 0) {
                            success = false; // Username đã tồn tại
                        } else {
                            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                                ps.setString(1, username);
                                ps.setString(2, password);
                                ps.setString(3, role);
                                ps.setString(4, phone);
                                ps.setString(5, employeeCode);

                                if (ps.executeUpdate() <= 0) success = false;
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }

        if (success) {
            JOptionPane.showMessageDialog(null, "Thêm thành công!\nMã nhân viên: " + employeeCode);
        } else {
            JOptionPane.showMessageDialog(null, "Thêm thất bại!");
        }
    }

    // Hàm tạo mã nhân viên ngẫu nhiên
    private String generateEmployeeCode() {
        Random random = new Random();
        String code;
        do {
            code = "NV" + (1000 + random.nextInt(9000)); // NVxxxx
        } while (checkEmployeeCodeExists(code)); // tránh trùng
        return code;
    }

    // Kiểm tra mã nhân viên đã tồn tại trong DB hay chưa
    private boolean checkEmployeeCodeExists(String code) {
        String sql = "SELECT COUNT(*) FROM users WHERE employee_code=?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa tài khoản
    public void deleteAccount(String username) {
        boolean success = true;

        if (!txtModel.deleteUser(username)) {
            success = false;
        }

        String deleteSql = "DELETE FROM users WHERE username=?";

        try (Connection conn = db.getConnection()) {
            if (conn == null || conn.isClosed()) {
                success = false;
            } else {
                try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
                    ps.setString(1, username);
                    if (ps.executeUpdate() <= 0) success = false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }

        if (success) {
            JOptionPane.showMessageDialog(null, "Xóa thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Xóa thất bại!");
        }
    }


    public void editAccount(String username) {


    }
}
