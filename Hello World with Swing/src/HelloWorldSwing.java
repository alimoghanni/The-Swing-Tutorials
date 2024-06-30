// File: HelloWorldSwing.java
import javax.swing.*;

public class HelloWorldSwing {
    public static void main(String[] args) {
        // Create a new JFrame container
        JFrame frame = new JFrame("Hello World Swing");

        // Set the default close operation for the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add a JLabel to the frame
        JLabel label = new JLabel("Hello, World!");
        frame.getContentPane().add(label);

        // Set the frame size
        frame.setSize(300, 200);

        // Make the frame visible
        frame.setVisible(true);
    }
}
