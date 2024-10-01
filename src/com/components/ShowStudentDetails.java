package com.components;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class ShowStudentDetails {
    public void showStudentDetails(int studentId, DefaultTableModel model) {

        JFrame detailsFrame = new JFrame("Student Details");
        detailsFrame.setSize(400, 520);
        detailsFrame.setLayout(null);
        detailsFrame.setLocation(420, 100);
        detailsFrame.setResizable(false);

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
        Font labelFont = new Font("Arial", Font.BOLD, 14);
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

        // Labels for displaying details (set to non-editable)
        JLabel tf1 = new JLabel();
        JLabel tf2 = new JLabel();
        JLabel tf3 = new JLabel();
        JLabel tf4 = new JLabel();
        JLabel tf5 = new JLabel();
        JLabel tf6 = new JLabel();
        JLabel tf7 = new JLabel();
        JLabel tf8 = new JLabel();
        JLabel tf9 = new JLabel("Fees Paid");
        JLabel imageLabel = new JLabel();

        // Applying font and color to labels
        JLabel[] labels = {l1, l2, l3, l4, l5, l6, l7, l8, l9};
        for (JLabel label : labels) {
            label.setFont(labelFont);
            label.setForeground(labelColor);
        }

        JLabel[] textFields = {tf1, tf2, tf3, tf4, tf5, tf6, tf7,tf8,tf9 };
        for (JLabel textField : textFields) {
            textField.setFont(labelFont);
            textField.setForeground(labelColor);
        }


        final String[] imagePath = {null};
        // Fetch existing student data from the database
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
                tf6.setText(rs.getString("course"));
                tf7.setText(String.valueOf(rs.getInt("semester")));
                tf8.setText(String.valueOf(rs.getFloat("grade")));
                int paid = rs.getInt("fees_paid");
                tf9.setText((paid == 1) ? "Yes" : "No");
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

        l1.setBounds(80, 130, 100, 20);tf1.setBounds(190, 130, 150, 20);
        l2.setBounds(80, 160, 100, 20);tf2.setBounds(190, 160, 150, 20);
        l3.setBounds(80, 190, 100, 20);tf3.setBounds(190, 190, 150, 20);
        l4.setBounds(80, 220, 100, 20);tf4.setBounds(190, 220, 180, 20);
        l5.setBounds(80, 250, 100, 20);tf5.setBounds(190, 250, 150, 20);
        l6.setBounds(80, 280, 100, 20);tf6.setBounds(190, 280, 150, 20);
        l7.setBounds(80, 310, 100, 20);tf7.setBounds(190, 310, 150, 20);
        l8.setBounds(80, 340, 100, 20);tf8.setBounds(190, 340, 150, 20);
        l9.setBounds(80, 370, 100, 20);tf9.setBounds(190, 370, 150, 20);
        imageLabel.setBounds(145, 20, 102, 102);
        imageLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,Color.RED,
                new Color(253, 186, 44)));

        JButton editButton = new JButton("Edit");
        editButton.setBounds(80, 420, 100, 30);
        editButton.setBackground(new Color(253, 186, 44));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.setBorder(null);


        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(200, 420, 100, 30);
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBorder(null);
        deleteButton.setFocusPainted(false);


        panel.add(l1); panel.add(tf1);
        panel.add(l2); panel.add(tf2);
        panel.add(l3); panel.add(tf3);
        panel.add(l4); panel.add(tf4);
        panel.add(l5); panel.add(tf5);
        panel.add(l6); panel.add(tf6);
        panel.add(l7); panel.add(tf7);
        panel.add(l8); panel.add(tf8);
        panel.add(l9); panel.add(tf9);
        panel.add(editButton); panel.add(imageLabel);
        panel.add(deleteButton);


        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new EditStudent().editStudent(studentId, model);// Edit the selected student
                detailsFrame.dispose();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DeleteStudent().deleteStudent(studentId, detailsFrame, model);// Edit the selected student
            }
        });


        detailsFrame.setContentPane(panel);
        detailsFrame.setVisible(true);
    }


}
