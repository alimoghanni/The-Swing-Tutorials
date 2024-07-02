import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UserManagementApp extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JTextField userIdField;
    private JTextField productNameField;
    private JTextField amountField;
    private JTextField editUserIdField;
    private JTextField editOrderIdField;
    private JTextArea outputArea;
    private JTree userTree;
    private DefaultTreeModel treeModel;
    private DatabaseHelper dbHelper;

    public UserManagementApp() {
        dbHelper = new DatabaseHelper();
        dbHelper.createTables();

        setTitle("User Management App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel for Users
        JPanel userPanel = new JPanel(new GridLayout(3, 2));
        userPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        userPanel.add(nameField);
        userPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        userPanel.add(emailField);

        // Input Panel for Orders
        JPanel orderPanel = new JPanel(new GridLayout(3, 2));
        orderPanel.add(new JLabel("User ID:"));
        userIdField = new JTextField();
        orderPanel.add(userIdField);
        orderPanel.add(new JLabel("Product Name:"));
        productNameField = new JTextField();
        orderPanel.add(productNameField);
        orderPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        orderPanel.add(amountField);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 3));
        JButton addUserButton = new JButton("Add User");
        addUserButton.addActionListener(new AddUserListener());
        buttonsPanel.add(addUserButton);
        JButton viewUsersButton = new JButton("View Users");
        viewUsersButton.addActionListener(new ViewUsersListener());
        buttonsPanel.add(viewUsersButton);
        JButton addOrderButton = new JButton("Add Order");
        addOrderButton.addActionListener(new AddOrderListener());
        buttonsPanel.add(addOrderButton);
        JButton viewOrdersButton = new JButton("View Orders");
        viewOrdersButton.addActionListener(new ViewOrdersListener());
        buttonsPanel.add(viewOrdersButton);

        // New buttons for editing and deleting
        JButton editUserButton = new JButton("Edit User");
        editUserButton.addActionListener(new EditUserListener());
        buttonsPanel.add(editUserButton);
        editUserIdField = new JTextField();
        buttonsPanel.add(editUserIdField);

        JButton deleteUserButton = new JButton("Delete User");
        deleteUserButton.addActionListener(new DeleteUserListener());
        buttonsPanel.add(deleteUserButton);

        JButton editOrderButton = new JButton("Edit Order");
        editOrderButton.addActionListener(new EditOrderListener());
        buttonsPanel.add(editOrderButton);
        editOrderIdField = new JTextField();
        buttonsPanel.add(editOrderIdField);

        JButton deleteOrderButton = new JButton("Delete Order");
        deleteOrderButton.addActionListener(new DeleteOrderListener());
        buttonsPanel.add(deleteOrderButton);

        // Output Area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // JTree
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Users");
        treeModel = new DefaultTreeModel(root);
        userTree = new JTree(treeModel);
        userTree.setCellRenderer(new UserTreeCellRenderer());
        JScrollPane treeScrollPane = new JScrollPane(userTree);

        add(userPanel, BorderLayout.NORTH);
        add(orderPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.WEST);
        add(treeScrollPane, BorderLayout.EAST);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private class AddUserListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String email = emailField.getText();
            if (isValidEmail(email)) {
                dbHelper.insertUser(name, email);
                nameField.setText("");
                emailField.setText("");
                outputArea.setText("User added successfully!");
            } else {
                outputArea.setText("Invalid email address!");
            }
        }
    }

    private class AddOrderListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int userId = Integer.parseInt(userIdField.getText());
            String productName = productNameField.getText();
            double amount = Double.parseDouble(amountField.getText());
            dbHelper.insertOrder(userId, productName, amount);
            userIdField.setText("");
            productNameField.setText("");
            amountField.setText("");
            outputArea.setText("Order added successfully!");
        }
    }

    private class ViewUsersListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            outputArea.setText("");
            dbHelper.selectAllUsers();
        }
    }

    private class ViewOrdersListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            outputArea.setText("");
            dbHelper.selectAllOrders();
        }
    }

    private class EditUserListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Implementation for editing a user
        }
    }

    private class DeleteUserListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int userId = Integer.parseInt(editUserIdField.getText());
            dbHelper.deleteUser(userId);
            editUserIdField.setText("");
            outputArea.setText("User deleted successfully!");
        }
    }

    private class EditOrderListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Implementation for editing an order
        }
    }

    private class DeleteOrderListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int orderId = Integer.parseInt(editOrderIdField.getText());
            dbHelper.deleteOrder(orderId);
            editOrderIdField.setText("");
            outputArea.setText("Order deleted successfully!");
        }
    }

    private class UserTreeCellRenderer extends DefaultTreeCellRenderer {
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                      boolean selected, boolean expanded,
                                                      boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
            if (value instanceof DefaultMutableTreeNode) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                if (node.getUserObject() instanceof String) {
                    setText(node.getUserObject().toString());
                }
            }
            return this;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserManagementApp app = new UserManagementApp();
            app.setVisible(true);
        });
    }
}
