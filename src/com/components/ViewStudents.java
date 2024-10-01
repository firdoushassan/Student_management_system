package com.components;

import javax.swing.*;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ViewStudents {
    public void viewStudents() {

        JFrame viewFrame = new JFrame("View Students");
        viewFrame.setSize(900, 500);
        viewFrame.setLocationRelativeTo(null); // Center the window
        viewFrame.setLayout(new BorderLayout());

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon(Objects.requireNonNull(getClass().getResource
                        ("images\\viewbg.png")));
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null);
        viewFrame.add(panel);

        // Table and its model
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        model.addColumn("Student ID");
        model.addColumn("Name");
        model.addColumn("Course");
        model.addColumn("Semester");
        model.addColumn("Grade");
        model.addColumn("Fees Paid");

        //Table Design
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(center);
        table.getColumnModel().getColumn(3).setCellRenderer(center);
        table.getColumnModel().getColumn(4).setCellRenderer(center);
        table.getColumnModel().getColumn(5).setCellRenderer(center);


        table.setBackground(Color.DARK_GRAY);
        table.setForeground(Color.WHITE);
        table.setGridColor(Color.WHITE);
        table.setSelectionBackground(new Color(253, 186, 44));
        table.setSelectionForeground(Color.DARK_GRAY);
        table.setRowHeight(20);
        table.setShowHorizontalLines(false);

        table.getTableHeader().setBackground(Color.BLACK);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(30,30));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.BLACK));

        new RefreshStudentView().refreshStudentView(model);

        // Adding the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(18, 70, 850, 330);
        scrollPane.setBackground(Color.BLACK);
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setPreferredSize(new Dimension(12, 0));
        verticalScrollBar.setUI(new BasicScrollBarUI(){
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(253, 186, 44);
                this.trackColor = Color.BLACK;
            }
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }
            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }
            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });

        panel.add(scrollPane);


        // Search components
        JLabel searchLabel = new JLabel("Search By:");
        searchLabel.setBounds(20, 30, 100, 20);
        searchLabel.setForeground(Color.WHITE); // Set text color to white

        String[] columns = { "Select", "Name", "Course", "Semester", "Grade", "Fees Paid" }; // Columns to search on
        JComboBox<String> columnComboBox = new JComboBox<>(columns);
        columnComboBox.setBounds(100, 30, 150, 20);
        columnComboBox.setBackground(Color.DARK_GRAY);
        columnComboBox.setForeground(Color.WHITE);
        columnComboBox.setBorder(BorderFactory.createLineBorder(new Color(253, 186, 44)));

        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(270, 30, 150, 20);
        searchTextField.setBackground(Color.BLACK);
        searchTextField.setBorder(BorderFactory.createLineBorder(new Color(253, 186, 44)));
        searchTextField.setForeground(Color.WHITE);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(425, 25, 80, 25);
        customizeButton(searchButton); // Customize button

        Icon refresh = new ImageIcon(Objects.requireNonNull(getClass().getResource("refresh.png")));
        JButton refreshButton = new JButton(refresh);
        refreshButton.setOpaque(false);
        refreshButton.setBorder(null);
        refreshButton.setContentAreaFilled(false);
        refreshButton.setBorderPainted(false);
        refreshButton.setBounds(510, 27, 20, 20);

        JButton exportButton = new JButton("Export");
        exportButton.setBounds(780, 25, 80, 25);
        customizeButton(exportButton); // Customize button

        panel.add(searchLabel);
        panel.add(columnComboBox);
        panel.add(searchTextField);
        panel.add(searchButton);
        panel.add(refreshButton);
        panel.add(exportButton);

        // Add buttons
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton viewButton = new JButton("View");
        JButton addButton = new JButton("Add");

        customizeButton(editButton);
        customizeButton(deleteButton);
        customizeButton(viewButton);
        customizeButton(addButton);

        JPanel buttonPanel = new JPanel();
        GridLayout layout = new GridLayout(1,4);
        layout.setHgap(10);
        buttonPanel.setLayout(layout);
        buttonPanel.setBackground(new Color(72, 128, 220));
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(addButton);
        buttonPanel.setBounds(250, 422, 400, 20);
        panel.add(buttonPanel);

        // Search button functionality
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedColumn = Objects.requireNonNull(columnComboBox.getSelectedItem()).toString();
                String searchText = searchTextField.getText().trim();

                if (!searchText.isEmpty()) {
                    new SearchTable().searchTable(model, selectedColumn, searchText);
                    searchTextField.setText("");
                } else {
                    JOptionPane.showMessageDialog(viewFrame, "Please enter a search term.");
                }
            }
        });

        // Refresh button functionality
        refreshButton.addActionListener((new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchTextField.setText("");
                new RefreshStudentView().refreshStudentView(model);
            }
        }));

        // Edit button functionality
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int studentId = (int) model.getValueAt(selectedRow, 0);
                    new EditStudent().editStudent(studentId, model);  // Call the method to edit the student
                } else {
                    JOptionPane.showMessageDialog(viewFrame, "Please select a student to edit.");
                }
            }
        });

        // Delete button functionality
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int studentId = (int) model.getValueAt(selectedRow, 0);
                    new DeleteStudent().deleteStudent(studentId, viewFrame, model);  // Call the method to delete the student
                    new ViewStudents().viewStudents();
                } else {
                    JOptionPane.showMessageDialog(viewFrame, "Please select a student to delete.");
                }
            }
        });

        // View button functionality
        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int studentId = (int) model.getValueAt(selectedRow, 0);
                    new ShowStudentDetails().showStudentDetails(studentId, model);  // Call to show student details
                } else {
                    JOptionPane.showMessageDialog(viewFrame, "Please select a student to view.");
                }
            }
        });

        // Add button functionality
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddStudentForm().addStudentForm(model);  // Call to show add student form
            }
        });

        // Export button functionality
        exportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a file to save");

                // Set file filter for CSV
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV files", "csv"));

                int userSelection = fileChooser.showSaveDialog(viewFrame);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    java.io.File fileToSave = fileChooser.getSelectedFile();
                    String filePath = fileToSave.getAbsolutePath();
                    if (!filePath.endsWith(".csv")) {
                        filePath += ".csv";  // Ensure it has a .csv extension
                    }

                    try (java.io.PrintWriter writer = new java.io.PrintWriter(filePath)) {

                        writer.println("ID,Name,Father Name,Mother Name,Email,Phone,Course,Semester,Grade," +
                                "Fees Paid,Image Path");

                        StudentDataFetch studentDataFetch = new StudentDataFetch();

                        // Fetch complete data for the displayed rows
                        for (int i = 0; i < model.getRowCount(); i++) {
                            int studentId = (int) model.getValueAt(i, 0);

                            // Fetch full student data (including hidden fields) from the database
                            String[] studentData = studentDataFetch.studentDataFetch(studentId);

                            // Write the full data row into the CSV file
                            writer.println(String.join(",", studentData));
                        }

                        JOptionPane.showMessageDialog(viewFrame, "Data exported successfully!");
                    } catch (java.io.IOException ex) {
                        JOptionPane.showMessageDialog(viewFrame, "Error saving file: " + ex.getMessage());
                    }
                }
            }
        });

        viewFrame.setVisible(true);
        viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void customizeButton(JButton button) {
        button.setBackground(new Color(253, 186, 44)); // Button color
        button.setForeground(Color.WHITE); // Button text color
        button.setFocusPainted(false); // Remove focus painting
        button.setBorder(null);
    }
}
