// File: NestedLayoutExample.java
import javax.swing.*;
import java.awt.*;

public class NestedLayoutExample {
    public static void main(String[] args) {
        // Create a new JFrame container
        JFrame frame = new JFrame("Nested Layout Example");

        // Set the default close operation for the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create a title label and add it to the north region
        JLabel titleLabel = new JLabel("Login Form", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Create a form panel with GridLayout
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField(20);
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField(20);
        formPanel.add(passwordField);

        formPanel.add(new JLabel(""));
        JButton loginButton = new JButton("Login");
        formPanel.add(loginButton);

        // Add the form panel to the center of the main panel
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Create a footer panel with FlowLayout
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Cancel");
        footerPanel.add(cancelButton);

        // Add the footer panel to the south of the main panel
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Add the main panel to the frame's content pane
        frame.getContentPane().add(mainPanel);

        // Set the frame size
        frame.setSize(400, 250);

        // Make the frame visible
        frame.setVisible(true);
    }
}
