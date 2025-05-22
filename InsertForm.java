package gui;
import javax.swing.*;
import java.awt.event.*;
import db.DBConnection;
import java.sql.*;

public class InsertForm extends JFrame {
    public InsertForm() {
        setTitle("Εισαγωγή Εκπαιδευτή");
        setSize(300, 250);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel idLbl = new JLabel("ID:");
        JLabel fnameLbl = new JLabel("Όνομα:");
        JLabel lnameLbl = new JLabel("Επώνυμο:");
        JTextField idTxt = new JTextField();
        JTextField fnameTxt = new JTextField();
        JTextField lnameTxt = new JTextField();
        JButton insertBtn = new JButton("Εισαγωγή");

        idLbl.setBounds(20, 20, 80, 25);
        idTxt.setBounds(100, 20, 150, 25);
        fnameLbl.setBounds(20, 60, 80, 25);
        fnameTxt.setBounds(100, 60, 150, 25);
        lnameLbl.setBounds(20, 100, 80, 25);
        lnameTxt.setBounds(100, 100, 150, 25);
        insertBtn.setBounds(100, 150, 100, 30);

        add(idLbl); add(idTxt);
        add(fnameLbl); add(fnameTxt);
        add(lnameLbl); add(lnameTxt);
        add(insertBtn);

        insertBtn.addActionListener(e -> {
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "INSERT INTO TEACHERS VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(idTxt.getText()));
                stmt.setString(2, fnameTxt.getText());
                stmt.setString(3, lnameTxt.getText());
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Επιτυχής εισαγωγή!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Σφάλμα: " + ex.getMessage());
            }
        });
    }
}
