package com.components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteStudent{
    public void deleteStudent(int studentId, JFrame parentFrame, DefaultTableModel model) {
        // Ask for confirmation before deleting
        int response = JOptionPane.showConfirmDialog(parentFrame, "Are you sure you want to delete " +
                "this student?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            try {
                Connection con = DBConnection.getConnection();
                assert con != null;
                PreparedStatement ps = con.prepareStatement("DELETE FROM student WHERE student_id = ?");
                ps.setInt(1, studentId);

                int row = ps.executeUpdate();
                if (row > 0) {
                    JOptionPane.showMessageDialog(parentFrame, "Student deleted successfully!");
                    new RefreshStudentView().refreshStudentView(model);
                    parentFrame.dispose();  // Close the view window and refresh it
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }else{
            new RefreshStudentView().refreshStudentView(model);
            parentFrame.dispose();
        }
    }


}
