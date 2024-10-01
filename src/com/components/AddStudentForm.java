package com.components;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;

public class AddStudentForm{
    public void addStudentForm(DefaultTableModel model) {
        JFrame addFrame = new JFrame("Add Student");
        addFrame.setSize(400, 520);
        addFrame.setLayout(null);
        addFrame.setLocation(420, 100);
        addFrame.setResizable(false);

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

        String[] courses = { "B.Tech", "BCA", "M.Tech", "MCA" };
        JComboBox<String> cb1 = new JComboBox<>(courses); // Course dropdown

        JTextField tf7 = new JTextField(); // Semester
        JCheckBox feesPaidCheckBox = new JCheckBox("Paid"); //Fees Paid
        feesPaidCheckBox.setBackground(new Color(14, 19, 38, 255));
        feesPaidCheckBox.setForeground(Color.WHITE);
        JLabel imageLabel = new JLabel("  **NO IMAGE**"); // Image display
        imageLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,Color.RED,
                new Color(253, 186, 44)));

        JTextField[] textFields = {tf1, tf2, tf3, tf4, tf5, tf6, tf7 };
        for (JTextField textField : textFields) {
            textField.setFont(labelFont);
            textField.setForeground(Color.WHITE);
            textField.setBackground(new Color(2, 194, 255, 216));
            textField.setBorder(null);
        }


        JButton uploadButton = new JButton("Upload Image");
        uploadButton.setBackground(Color.RED);
        uploadButton.setForeground(Color.WHITE);
        uploadButton.setBorder(null);
        uploadButton.setFocusPainted(false);

        JButton addButton = new JButton("Add Student");
        addButton.setBackground(new Color(253, 186, 44));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setBorder(null);

        // Customize JComboBox appearance
        cb1.setBackground(new Color(2, 194, 255, 216));
        cb1.setForeground(Color.WHITE);


        l1.setBounds(60, 20, 100, 20);
        tf1.setBounds(170, 20, 150, 20);
        l2.setBounds(60, 50, 100, 20);
        tf2.setBounds(170, 50, 150, 20);
        l3.setBounds(60, 80, 100, 20);
        tf3.setBounds(170, 80, 150, 20);
        l4.setBounds(60, 110, 100, 20);
        tf4.setBounds(170, 110, 150, 20);
        l5.setBounds(60, 140, 100, 20);
        tf5.setBounds(170, 140, 150, 20);
        l6.setBounds(60, 170, 100, 20);
        cb1.setBounds(170, 170, 150, 20);
        l7.setBounds(60, 200, 100, 20);
        tf7.setBounds(170, 200, 150, 20);
        l8.setBounds(60, 230, 100, 20);
        tf6.setBounds(170, 230, 150, 20);
        l9.setBounds(60, 260, 100, 20);
        feesPaidCheckBox.setBounds(170, 260, 50, 20);
        l10.setBounds(60, 290, 100, 20);
        uploadButton.setBounds(170, 290, 150, 22);
        imageLabel.setBounds(145, 318, 100, 100);
        addButton.setBounds(130, 425, 150, 30);


        panel.add(l1); panel.add(tf1); panel.add(l2); panel.add(tf2);
        panel.add(l3); panel.add(tf3); panel.add(l4); panel.add(tf4);
        panel.add(l5); panel.add(tf5); panel.add(l6); panel.add(cb1);
        panel.add(l7); panel.add(tf7); panel.add(l8); panel.add(tf6);
        panel.add(l9); panel.add(feesPaidCheckBox); panel.add(l10);
        panel.add(uploadButton); panel.add(imageLabel); panel.add(addButton);

        final String[] imagePath = {null};

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(null);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    imagePath[0] = file.getAbsolutePath();
                    imageLabel.setIcon(new ImageIcon
                            (new ImageIcon(imagePath[0]).getImage().getScaledInstance
                                    (100, 100, java.awt.Image.SCALE_SMOOTH)));
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection con = DBConnection.getConnection();
                    assert con != null;
                    PreparedStatement ps = con.prepareStatement("INSERT INTO student(name, father_name, " +
                            "mother_name, email, phone, course, semester, grade, fees_paid, image_path) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
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

                    int row = ps.executeUpdate();
                    if (row > 0) {
                        JOptionPane.showMessageDialog(addFrame, "Student Added Successfully!");
                        addFrame.dispose();  // Close the edit window
                        // Refresh the students view
                        new RefreshStudentView().refreshStudentView(model); // Refresh the table
                    } else {
                        JOptionPane.showMessageDialog(addFrame, "Error Adding Student");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        addFrame.setContentPane(panel);
        addFrame.setVisible(true);
        addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
