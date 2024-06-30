// File: FrameAndPanel.java
import javax.swing.*;

public class FrameAndPanel {
    public static void main(String[] args) {
        // Create a new JFrame container
        JFrame frame = new JFrame("JFrame and JPanel Example");

        // Set the default close operation for the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a new JPanel
        JPanel panel = new JPanel();

        // Create a JLabel and add it to the panel
        JLabel label = new JLabel("This is a JLabel");
        panel.add(label);

        // Add the panel to the frame's content pane
        frame.getContentPane().add(panel);

        // Set the frame size
        frame.setSize(400, 300);

        // Make the frame visible
        frame.setVisible(true);
    }
}
