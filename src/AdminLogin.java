import com.components.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Objects;

public class AdminLogin extends JFrame implements ActionListener {
    JLabel l1, l2;
    JTextField tf1;
    JPasswordField p1;
    JButton loginButton;
    JPanel panel;

    public AdminLogin() {
        // UI Components for Login form
        l1 = new JLabel("Username:");
        l2 = new JLabel("Password:");

        tf1 = new JTextField();
        p1 = new JPasswordField();

        loginButton = new JButton("Login");

        // Setting up the JFrame
        setTitle("Admin Login");
        setSize(400, 260);
        setLayout(new BorderLayout());
        setLocation(420, 200);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon(Objects.requireNonNull(getClass().getResource
                        ("admin login.png")));
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null);
        add(panel);

        // Set font color to white and customize fonts
        Font font = new Font("Arial", Font.BOLD, 14);
        l1.setFont(font);
        l2.setFont(font);
        l1.setForeground(Color.WHITE);
        l2.setForeground(Color.WHITE);

        tf1.setFont(font);
        tf1.setBorder(null);
        p1.setFont(font);
        p1.setBorder(null);
        loginButton.setFont(font);
        loginButton.setBorder(null);
        loginButton.setBackground(new Color(253, 186, 44)); // Button color
        loginButton.setForeground(Color.WHITE); // Button text color

        // Setting bounds
        l1.setBounds(80, 110, 80, 20);
        l2.setBounds(80, 140, 80, 20);
        tf1.setBounds(160, 110, 150, 20);
        p1.setBounds(160, 140, 150, 20);
        loginButton.setBounds(160, 180, 80, 30);


        panel.add(l1);
        panel.add(tf1);
        panel.add(l2);
        panel.add(p1);
        panel.add(loginButton);


        loginButton.addActionListener(this);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Login Button functionality
    public void actionPerformed(ActionEvent e) {
        String username = tf1.getText().trim();
        String password = new String(p1.getPassword());

        // Validate inputs
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM admin WHERE username = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful");
                new StudentManagement();  // Move to Student Management screen
                this.dispose();  // Close login window
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials",
                        "Login Failed", JOptionPane.WARNING_MESSAGE);
                tf1.setText("");
                p1.setText("");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminLogin::new);
    }
}