package com.components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchTable {
    public void searchTable(DefaultTableModel model, String column, String searchText) {
        model.setRowCount(0);
        String columnName = switch (column) {
            case "Name" -> "name";
            case "Course" -> "course";
            case "Semester" -> "semester";
            case "Grade" -> "grade";
            case "Fees Paid" -> "fees_paid";
            default -> "";
        };


        try {
            Connection con = DBConnection.getConnection();
            assert con != null;
            PreparedStatement ps = con.prepareStatement("SELECT student_id, name, course, semester, grade, fees_paid FROM student WHERE " + columnName + " LIKE ?");
            ps.setString(1, "%" + searchText + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] student = {
                        rs.getInt("student_id"),
                        rs.getString("name"),
                        rs.getString("course"),
                        rs.getInt("semester"),
                        rs.getFloat("grade"),
                        rs.getBoolean("fees_paid") ? "Yes" : "No"
                };
                model.addRow(student);
            }

            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "No match found.");
                new RefreshStudentView().refreshStudentView(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error searching student data.");
        }
    }
}
