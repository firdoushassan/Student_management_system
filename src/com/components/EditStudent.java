package com.components;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class EditStudent {

    public void editStudent(int studentId, DefaultTableModel model) {

        JFrame editFrame = new JFrame("Edit Student");
        editFrame.setSize(400, 520);
        editFrame.setLayout(null);
        editFrame.setLocation(420, 100);
        editFrame.setResizable(false);


        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon otherbg = new ImageIcon(Objects.requireNonNull(getClass().getResource
                        ("images\\otherbg.png")));
                g.drawImage(otherbg.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        // Custom Font and Color
        Font labelFont = new Font("Arial", Font.BOLD, 12);
        Color labelColor = Color.WHITE;

        // Labels and text fields for editing
        JLabel l1 = new JLabel("Student Name:");
        JLabel l2 = new JLabel("Father's Name:");
        JLabel l3 = new JLabel("Mother's Name:");
        JLabel l4 = new JLabel("Email:");
        JLabel l5 = new JLabel("Phone:");
        JLabel l6 = new JLabel("Course:");
        JLabel l7 = new JLabel("Semester:");
        JLabel l8 = new JLabel("Grade:");
        JLabel l9 = new JLabel("Fees Paid:");
        JLabel l10 = new JLabel("Student Image:");

        // Applying font and color to labels
        JLabel[] labels = {l1, l2, l3, l4, l5, l6, l7, l8, l9, l10};
        for (JLabel label : labels) {
            label.setFont(labelFont);
            label.setForeground(labelColor);
        }

        JTextField tf1 = new JTextField(); // Name
        JTextField tf2 = new JTextField(); // Father's Name
        JTextField tf3 = new JTextField(); // Mother's Name
        JTextField tf4 = new JTextField(); // Email
        JTextField tf5 = new JTextField(); // Phone
        JTextField tf6 = new JTextField(); // Grade

        JComboBox<String> cb1 = new JComboBox<>(new String[] {"B.Tech", "BCA", "M.Tech", "MCA"});  // Course dropdown
        JTextField tf7 = new JTextField(); // Semester
        JCheckBox feesPaidCheckBox = new JCheckBox("Paid");
        feesPaidCheckBox.setBackground(new Color(80, 120, 162, 255));
        feesPaidCheckBox.setForeground(Color.WHITE);
        JLabel imageLabel = new JLabel();

        JTextField[] textFields = {tf1, tf2, tf3, tf4, tf5, tf6, tf7 };
        for (JTextField textField : textFields) {
            textField.setFont(labelFont);
            textField.setForeground(Color.WHITE);
            textField.setBackground(new Color(2, 194, 255, 216));
            textField.setBorder(null);
        }

        JButton changeButton = new JButton("Change Image");
        changeButton.setBackground(Color.RED);
        changeButton.setForeground(Color.WHITE);
        changeButton.setBorder(null);
        changeButton.setFocusPainted(false);

        JButton saveButton = new JButton("Save Changes");
        saveButton.setBackground(new Color(253, 186, 44));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setBorder(null);


        // Set the bounds and align items
        l1.setBounds(60, 120, 100, 20);
        tf1.setBounds(170, 120, 150, 20);
        l2.setBounds(60, 150, 100, 20);
        tf2.setBounds(170, 150, 150, 20);
        l3.setBounds(60, 180, 100, 20);
        tf3.setBounds(170, 180, 150, 20);
        l4.setBounds(60, 210, 100, 20);
        tf4.setBounds(170, 210, 150, 20);
        l5.setBounds(60, 240, 100, 20);
        tf5.setBounds(170, 240, 150, 20);
        l6.setBounds(60, 270, 100, 20);
        cb1.setBounds(170, 270, 150, 20);
        l7.setBounds(60, 300, 100, 20);
        tf7.setBounds(170, 300, 150, 20);
        l8.setBounds(60, 330, 100, 20);
        tf6.setBounds(170, 330, 150, 20);
        l9.setBounds(60, 360, 100, 20);
        feesPaidCheckBox.setBounds(170, 360, 50, 20);
        l10.setBounds(60, 390, 100, 20);
        changeButton.setBounds(170, 390, 150, 22);
        imageLabel.setBounds(145, 10, 100, 100);
        imageLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,Color.RED,
                new Color(253, 186, 44)));

        saveButton.setBounds(130, 425, 150, 30);


        // Customize JComboBox appearance
        cb1.setBackground(new Color(2, 194, 255, 216));
        cb1.setForeground(Color.WHITE);


        // Add components to the frame
        panel.add(l1); panel.add(tf1);
        panel.add(l2); panel.add(tf2);
        panel.add(l3); panel.add(tf3);
        panel.add(l4); panel.add(tf4);
        panel.add(l5); panel.add(tf5);
        panel.add(l6); panel.add(cb1);
        panel.add(l7); panel.add(tf7);
        panel.add(l8); panel.add(tf6);
        panel.add(l9); panel.add(feesPaidCheckBox);
        panel.add(l10); panel.add(changeButton);
        panel.add(imageLabel); panel.add(saveButton);


        final String[] imagePath = {null};

        // Fetch existing student data from database
        try {
            Connection con = DBConnection.getConnection();
            assert con != null;
            PreparedStatement ps = con.prepareStatement("SELECT * FROM student WHERE student_id = ?");
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tf1.setText(rs.getString("name"));
                tf2.setText(rs.getString("father_name"));
                tf3.setText(rs.getString("mother_name"));
                tf4.setText(rs.getString("email"));
                tf5.setText(rs.getString("phone"));
                cb1.setSelectedItem(rs.getString("course"));
                tf7.setText(String.valueOf(rs.getInt("semester")));
                tf6.setText(rs.getString("grade"));
                feesPaidCheckBox.setSelected(rs.getBoolean("fees_paid"));
                imagePath[0] = rs.getString("image_path");
                if (imagePath[0] != null && !imagePath[0].isEmpty()) {
                    imageLabel.setIcon(new ImageIcon(new ImageIcon(imagePath[0])
                            .getImage().getScaledInstance
                                    (100, 100, java.awt.Image.SCALE_SMOOTH)));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Change Button functionality
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(null);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    imagePath[0] = file.getAbsolutePath();
                    imageLabel.setIcon(new ImageIcon(new ImageIcon(imagePath[0]).getImage().
                            getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH)));
                }
            }
        });

        // Save changes to the database
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection con = DBConnection.getConnection();
                    assert con != null;
                    PreparedStatement ps = con.prepareStatement("UPDATE student SET name=?, father_name=?, " +
                            "mother_name=?, email=?, phone=?, course=?, semester=?, grade=?, " +
                            "fees_paid=?, image_path=? WHERE student_id=?");
                    ps.setString(1, tf1.getText());
                    ps.setString(2, tf2.getText());
                    ps.setString(3, tf3.getText());
                    ps.setString(4, tf4.getText());
                    ps.setString(5, tf5.getText());
                    ps.setString(6, Objects.requireNonNull(cb1.getSelectedItem()).toString());
                    ps.setInt(7, Integer.parseInt(tf7.getText()));
                    ps.setString(8, tf6.getText());
                    ps.setBoolean(9, feesPaidCheckBox.isSelected());
                    ps.setString(10, imagePath[0]);
                    ps.setInt(11, studentId);

                    int row = ps.executeUpdate();
                    if (row > 0) {
                        JOptionPane.showMessageDialog(editFrame, "Student updated successfully!");
                        editFrame.dispose();  // Close the edit window
                        // Refresh the students view
                        new RefreshStudentView().refreshStudentView(model); // Refresh the table
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        editFrame.setContentPane(panel);
        editFrame.setVisible(true);
    }


}
