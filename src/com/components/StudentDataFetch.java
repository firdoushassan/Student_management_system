package com.components;

import java.sql.*;

public class StudentDataFetch {
    public String[] studentDataFetch(int studentId) {
        String[] studentData = new String[11];

        try {
            Connection con = DBConnection.getConnection();
            assert con != null;
            PreparedStatement ps = con.prepareStatement("SELECT * FROM student WHERE student_id = ?");
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                studentData[0] = String.valueOf(rs.getInt("student_id"));
                studentData[1] = rs.getString("name");
                studentData[2] = rs.getString("father_name");
                studentData[3] = rs.getString("mother_name");
                studentData[4] = rs.getString("email");
                studentData[5] = rs.getString("phone");
                studentData[6] = rs.getString("course");
                studentData[7] = rs.getString("semester");
                studentData[8] = rs.getString("grade");
                int feesPaid = rs.getInt("fees_paid");
                studentData[9] = (feesPaid == 1) ? "Yes" : "No";
                studentData[10] = rs.getString("image_path");
            }
            rs.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentData;
    }
}
