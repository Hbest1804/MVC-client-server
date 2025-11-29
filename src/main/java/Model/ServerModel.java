package Model;

import DAO.LoginDAO;
import java.util.Optional;

public class ServerModel {
    private LoginDAO dao;
    private Usertxt usertxt;

    public ServerModel() {
        dao = new LoginDAO();
        usertxt = new Usertxt();
    }


    public User loginDatabase(String username, String password) {
        return dao.login(username, password);
    }


    public User loginTxt(String username, String password) {
        return usertxt.readUsers().stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }


    public User login(String username, String password) {
        User user = loginDatabase(username, password);
        if(user != null) return user;
        return loginTxt(username, password);
    }
}
