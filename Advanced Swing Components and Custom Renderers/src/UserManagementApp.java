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
        JPanel buttonsPanel = new JPanel(new FlowLayout());
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
                        "Invalid email format.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            dbHelper.insertUser(name, email);
            nameField.setText("");
            emailField.setText("");
            outputArea.append("User added: " + name + " (" + email + ")\n");
            refreshUserTree();
        }
    }

    private class ViewUsersListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            outputArea.setText("");
            dbHelper.selectAllUsers();
        }
    }

    private class AddOrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int userId;
            try {
                userId = Integer.parseInt(userIdField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(UserManagementApp.this,
                        "Invalid User ID format.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String productName = productNameField.getText().trim();
            double amount;
            try {
                amount = Double.parseDouble(amountField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(UserManagementApp.this,
                        "Invalid amount format.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            dbHelper.insertOrder(userId, productName, amount);
            userIdField.setText("");
            productNameField.setText("");
            amountField.setText("");
            outputArea.append("Order added: User ID " + userId + ", " + productName + ", $" + amount + "\n");
            refreshUserTree();
        }
    }

    private class ViewOrdersListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            outputArea.setText("");
            dbHelper.selectAllOrders();
        }
    }

    private void refreshUserTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Users");
        try (Connection conn = dbHelper.connect();
             Statement stmt = conn.createStatement()) {

            String sqlUsers = "SELECT * FROM users";
            ResultSet rsUsers = stmt.executeQuery(sqlUsers);

            while (rsUsers.next()) {
                DefaultMutableTreeNode userNode = new DefaultMutableTreeNode(rsUsers.getString("name"));
                int userId = rsUsers.getInt("id");

                String sqlOrders = "SELECT * FROM orders WHERE user_id = " + userId;
                ResultSet rsOrders = stmt.executeQuery(sqlOrders);

                while (rsOrders.next()) {
                    String orderDetails = rsOrders.getString("product_name") + " - $" + rsOrders.getDouble("amount");
                    DefaultMutableTreeNode orderNode = new DefaultMutableTreeNode(orderDetails);
                    userNode.add(orderNode);
                }

                root.add(userNode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        treeModel.setRoot(root);
        treeModel.reload();
    }

    private class UserTreeCellRenderer implements TreeCellRenderer {
        private JLabel label;

        public UserTreeCellRenderer() {
            label = new JLabel();
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
                                                      boolean leaf, int row, boolean hasFocus) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            Object nodeInfo = node.getUserObject();
            label.setText(nodeInfo.toString());

            if (leaf && node.getParent() != null) {
                label.setIcon(UIManager.getIcon("FileView.fileIcon"));
            } else {
                label.setIcon(UIManager.getIcon("FileView.directoryIcon"));
            }

            return label;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserManagementApp app = new UserManagementApp();
            app.setVisible(true);
        });
    }
}
