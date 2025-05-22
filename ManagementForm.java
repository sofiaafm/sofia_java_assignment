package gui;
import javax.swing.*;

public class ManagementForm extends JFrame {
    public ManagementForm() {
        setTitle("Διαχείριση Εκπαιδευτών");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JButton insertBtn = new JButton("Εισαγωγή Εκπαιδευτή");
        insertBtn.setBounds(100, 30, 200, 30);
        insertBtn.addActionListener(e -> new InsertForm().setVisible(true));
        add(insertBtn);

        JButton updateDeleteBtn = new JButton("Ενημέρωση/Διαγραφή Εκπαιδευτή");
        updateDeleteBtn.setBounds(100, 80, 200, 30);
        updateDeleteBtn.addActionListener(e -> new UpdateDeleteForm().setVisible(true));
        add(updateDeleteBtn);
    }
}
