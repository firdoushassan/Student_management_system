package com.components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class RefreshStudentView {

    public void refreshStudentView(DefaultTableModel model) {
        model.setRowCount(0);           // Clear existing rows

        // Fetch updated data from the MySQL database
        try {
            Connection con = DBConnection.getConnection();
            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT student_id, name, course, semester, grade, fees_paid FROM student");

            // Loop through the result set and add rows to the model
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("student_id"),
                        rs.getString("name"),
                        rs.getString("course"),
                        rs.getInt("semester"),
                        rs.getString("grade"),
                        rs.getBoolean("fees_paid") ? "Yes" : "No"
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading student data.");
        }
    }

}
