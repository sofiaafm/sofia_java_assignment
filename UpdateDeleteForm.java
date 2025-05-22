package gui;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import db.DBConnection;

public class UpdateDeleteForm extends JFrame {

    private JTextField idTxt, fnameTxt, lnameTxt, searchTxt;
    private JButton searchBtn, updateBtn, deleteBtn;

    public UpdateDeleteForm() {
        setTitle("Ενημέρωση / Διαγραφή Εκπαιδευτή");
        setSize(400, 350);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel searchLbl = new JLabel("Αναζήτηση Επώνυμου:");
        searchLbl.setBounds(20, 20, 150, 25);
        add(searchLbl);

        searchTxt = new JTextField();
        searchTxt.setBounds(170, 20, 150, 25);
        add(searchTxt);

        searchBtn = new JButton("Αναζήτηση");
        searchBtn.setBounds(130, 60, 120, 30);
        add(searchBtn);

        JLabel idLbl = new JLabel("ID:");
        idLbl.setBounds(20, 110, 80, 25);
        add(idLbl);

        idTxt = new JTextField();
        idTxt.setBounds(100, 110, 200, 25);
        idTxt.setEnabled(false); // Δεν επιτρέπεται αλλαγή ID
        add(idTxt);

        JLabel fnameLbl = new JLabel("Όνομα:");
        fnameLbl.setBounds(20, 150, 80, 25);
        add(fnameLbl);

        fnameTxt = new JTextField();
        fnameTxt.setBounds(100, 150, 200, 25);
        add(fnameTxt);

        JLabel lnameLbl = new JLabel("Επώνυμο:");
        lnameLbl.setBounds(20, 190, 80, 25);
        add(lnameLbl);

        lnameTxt = new JTextField();
        lnameTxt.setBounds(100, 190, 200, 25);
        add(lnameTxt);

        updateBtn = new JButton("Ενημέρωση");
        updateBtn.setBounds(60, 240, 120, 30);
        updateBtn.setEnabled(false);
        add(updateBtn);

        deleteBtn = new JButton("Διαγραφή");
        deleteBtn.setBounds(200, 240, 120, 30);
        deleteBtn.setEnabled(false);
        add(deleteBtn);

        // Λειτουργία Αναζήτησης
        searchBtn.addActionListener(e -> searchTeacher());

        // Ενημέρωση
        updateBtn.addActionListener(e -> updateTeacher());

        // Διαγραφή
        deleteBtn.addActionListener(e -> deleteTeacher());
    }

    private void searchTeacher() {
        String lname = searchTxt.getText();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM TEACHERS WHERE TEACHER_LNAME = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, lname);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idTxt.setText(String.valueOf(rs.getInt("TEACHER_ID")));
                fnameTxt.setText(rs.getString("TEACHER_FNAME"));
                lnameTxt.setText(rs.getString("TEACHER_LNAME"));

                updateBtn.setEnabled(true);
                deleteBtn.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Δεν βρέθηκε εκπαιδευτής.");
                updateBtn.setEnabled(false);
                deleteBtn.setEnabled(false);
                clearFields();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την αναζήτηση: " + ex.getMessage());
        }
    }

    private void updateTeacher() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE TEACHERS SET TEACHER_FNAME = ?, TEACHER_LNAME = ? WHERE TEACHER_ID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fnameTxt.getText());
            stmt.setString(2, lnameTxt.getText());
            stmt.setInt(3, Integer.parseInt(idTxt.getText()));

            int updated = stmt.executeUpdate();
            if (updated > 0) {
                JOptionPane.showMessageDialog(this, "Επιτυχής ενημέρωση.");
            } else {
                JOptionPane.showMessageDialog(this, "Η ενημέρωση απέτυχε.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Σφάλμα ενημέρωσης: " + ex.getMessage());
        }
    }

    private void deleteTeacher() {
        int confirm = JOptionPane.showConfirmDialog(this, "Είστε σίγουρος ότι θέλετε να διαγράψετε;", "Επιβεβαίωση", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "DELETE FROM TEACHERS WHERE TEACHER_ID = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(idTxt.getText()));

                int deleted = stmt.executeUpdate();
                if (deleted > 0) {
                    JOptionPane.showMessageDialog(this, "Ο εκπαιδευτής διαγράφηκε.");
                    clearFields();
                    updateBtn.setEnabled(false);
                    deleteBtn.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Η διαγραφή απέτυχε.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Σφάλμα διαγραφής: " + ex.getMessage());
            }
        }
    }

    private void clearFields() {
        idTxt.setText("");
        fnameTxt.setText("");
        lnameTxt.setText("");
    }
}
