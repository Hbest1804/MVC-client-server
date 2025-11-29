package Views;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class ServerViews extends JFrame {
    private JTextArea txtLog;

    public ServerViews() {
        setTitle("Server Log");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        getContentPane().add(panel);


        txtLog = new JTextArea();
        txtLog.setEditable(false);
        txtLog.setLineWrap(true);
        txtLog.setWrapStyleWord(true);

        DefaultCaret caret = (DefaultCaret) txtLog.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        panel.add(new JScrollPane(txtLog), BorderLayout.CENTER);

        setVisible(true);
    }

    public void showMessage(String message) {
        txtLog.append(message + "\n");
    }

    // Hai hàm này giữ lại để tránh lỗi khi ServerController gọi
    public void addClient(String clientName) {
        // Không làm gì nữa
    }

    public void removeClient(String clientName) {
        // Không làm gì nữa
    }
}
