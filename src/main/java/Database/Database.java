package Database;

import java.sql.*;

public class Database {
    private Connection connection;

    public Database() {
        try {
            String url = "jdbc:mysql://localhost:3306/ze?useSSL=false&serverTimezone=UTC";
            String user = "root";
            String password = "18004huyhio";

            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Kết nối MySQL thành công!");


            String check = "SELECT * FROM users WHERE username='admin'";
            PreparedStatement ps = connection.prepareStatement(check);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                String insert = "INSERT INTO users(username,password,role) VALUES('admin','123','admin')";
                ps = connection.prepareStatement(insert);
                ps.executeUpdate();
                System.out.println("Đã tạo user admin mặc định");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Kết nối MySQL thất bại!");
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
