package gui;
import javax.swing.*;
import java.awt.event.*;

public class MainForm extends JFrame {
    public MainForm() {
        setTitle("Αρχική Φόρμα");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton manageBtn = new JButton("Διαχείριση Εκπαιδευτών");
        manageBtn.addActionListener(e -> {
            new ManagementForm().setVisible(true);
            this.dispose();
        });

        add(manageBtn);
    }
}
