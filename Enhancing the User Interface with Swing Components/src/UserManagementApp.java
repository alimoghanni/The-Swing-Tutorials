import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UserManagementApp extends JFrame {
    private DatabaseHelper dbHelper;
    private JTextField nameField, emailField;
    private JTable userTable;
    private DefaultTableModel tableModel;

    public UserManagementApp() {
        dbHelper = new DatabaseHelper();
        dbHelper.createTables();

        setTitle("User Management App");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        JButton addButton = new JButton("Add User");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);

        // Table Panel
        String[] columnNames = {"ID", "Name", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel);
        loadUserTable();
        add(new JScrollPane(userTable), BorderLayout.CENTER);

        setVisible(true);
    }

    private void addUser() {
        String name = nameField.getText();
        String email = emailField.getText();

        if (name.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name and Email fields cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dbHelper.insertUser(name, email);
        loadUserTable();
        nameField.setText("");
        emailField.setText("");
    }

    private void loadUserTable() {
        tableModel.setRowCount(0);
        String sql = "SELECT * FROM users";

        try (Connection conn = dbHelper.connect();
             Statement stmt = (conn != null) ? conn.createStatement() : null;
             ResultSet rs = (stmt != null) ? stmt.executeQuery(sql) : null) {

            if (rs != null) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    tableModel.addRow(new Object[]{id, name, email});
                }
            } else {
                System.out.println("ResultSet creation failed.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserManagementApp();
            }
        });
    }
}
