// File: AddComponents.java
import javax.swing.*;

public class AddComponents {
    public static void main(String[] args) {
        // Create a new JFrame container
        JFrame frame = new JFrame("Adding Components Example");

        // Set the default close operation for the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a new JPanel
        JPanel panel = new JPanel();

        // Create a JLabel and add it to the panel
        JLabel label = new JLabel("Enter your name:");
        panel.add(label);

        // Create a JTextField and add it to the panel
        JTextField textField = new JTextField(20);
        panel.add(textField);

        // Create a JButton and add it to the panel
        JButton button = new JButton("Submit");
        panel.add(button);

        // Add the panel to the frame's content pane
        frame.getContentPane().add(panel);

        // Set the frame size
        frame.setSize(400, 300);

        // Make the frame visible
        frame.setVisible(true);
    }
}
