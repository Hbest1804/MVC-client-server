package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Usertxt {
    private String file_name = "src/main/java/users.txt";

    public List<User> readUsers() {
        List<User> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file_name))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                // username,password,role,phone,employeeCode
                if (parts.length == 5) {
                    list.add(new User(parts[0], parts[1], parts[2], parts[3], parts[4]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    private void writeUsers(List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file_name))) {
            for (User u : users) {
                bw.write(
                        u.getUsername() + "," +
                                u.getPassword() + "," +
                                u.getRole() + "," +
                                u.getPhone() + "," +
                                u.getEmployeeCode()
                );
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean addUser(User user) {
        List<User> users = readUsers();
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) return false; 
        }
        users.add(user);
        writeUsers(users);
        return true;
    }

    public boolean deleteUser(String username) {
        List<User> users = readUsers();
        users.removeIf(u -> u.getUsername().equals(username));
        writeUsers(users);
        return true;
    }
    public boolean updateUser(User updatedUser) {
        List<User> users = readUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(updatedUser.getUsername())) {
                users.set(i, updatedUser);
                writeUsers(users);
                return true;
            }
        }
        return false;
    }


}
