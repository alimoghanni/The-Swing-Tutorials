import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UserManagementApp extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JTextArea outputArea;
    private DatabaseHelper dbHelper;

    public UserManagementApp() {
        dbHelper = new DatabaseHelper();

        setTitle("User Management App");
        setSize(500, 400);
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

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add User");
        addButton.addActionListener(new AddUserListener());
        buttonsPanel.add(addButton);
        JButton viewButton = new JButton("View Users");
        viewButton.addActionListener(new ViewUsersListener());
        buttonsPanel.add(viewButton);
        JButton updateButton = new JButton("Update User");
        updateButton.addActionListener(new UpdateUserListener());
        buttonsPanel.add(updateButton);
        JButton deleteButton = new JButton("Delete User");
        deleteButton.addActionListener(new DeleteUserListener());
        buttonsPanel.add(deleteButton);

        // Output Area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private boolean isValidEmail(String email) {
        // Basic email validation regex
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private class AddUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();

            if (name.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(UserManagementApp.this,
                        "Please enter both name and email.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(UserManagementApp.this,
                        "Invalid email format",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            dbHelper.insertUser(name, email);
            nameField.setText("");
            emailField.setText("");
            outputArea.append("User added: " + name + " (" + email + ")\n");
        }
    }

    private class ViewUsersListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            outputArea.setText("");
            dbHelper.selectAllUsers();
        }
    }

    private class UpdateUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();

            if (name.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(UserManagementApp.this,
                        "Please enter both name and email.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(UserManagementApp.this,
                        "Invalid email format",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            dbHelper.updateUser(name, email);
            nameField.setText("");
            emailField.setText("");
            outputArea.append("User updated: " + name + " (" + email + ")\n");
        }
    }

    private class DeleteUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(UserManagementApp.this,
                        "Please enter the name of the user to delete.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            dbHelper.deleteUser(name);
            nameField.setText("");
            emailField.setText("");
            outputArea.append("User deleted: " + name + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatabaseHelper dbHelper = new DatabaseHelper();
            dbHelper.createTable(); // Ensure table is created on application startup
            UserManagementApp app = new UserManagementApp();
            app.setVisible(true);
        });
    }
}
