
import com.components.ViewStudents;

import javax.swing.*;
import java.awt.event.*;


public class StudentManagement extends JFrame implements ActionListener {


    public StudentManagement() {
        new ViewStudents().viewStudents();
    }


//    public static void main(String[] args) {
//        new StudentManagement();
//    }




    public void actionPerformed(ActionEvent e) {}
}