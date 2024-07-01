// File: LayoutManagers.java
import javax.swing.*;
import java.awt.*;

public class LayoutManagers {
    public static void main(String[] args) {
        // Create a new JFrame container
        JFrame frame = new JFrame("Layout Managers Example");

        // Set the default close operation for the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the layout manager for the frame's content pane
        frame.setLayout(new BorderLayout());

        // Create a JPanel with FlowLayout
        JPanel flowPanel = new JPanel(new FlowLayout());
        flowPanel.add(new JLabel("FlowLayout:"));
        flowPanel.add(new JButton("Button 1"));
        flowPanel.add(new JButton("Button 2"));
        flowPanel.add(new JButton("Button 3"));

        // Create a JPanel with BorderLayout
        JPanel borderPanel = new JPanel(new BorderLayout());
        borderPanel.add(new JButton("North"), BorderLayout.NORTH);
        borderPanel.add(new JButton("South"), BorderLayout.SOUTH);
        borderPanel.add(new JButton("East"), BorderLayout.EAST);
        borderPanel.add(new JButton("West"), BorderLayout.WEST);
        borderPanel.add(new JButton("Center"), BorderLayout.CENTER);

        // Create a JPanel with GridLayout
        JPanel gridPanel = new JPanel(new GridLayout(2, 2));
        gridPanel.add(new JLabel("GridLayout:"));
        gridPanel.add(new JButton("Button 4"));
        gridPanel.add(new JButton("Button 5"));
        gridPanel.add(new JButton("Button 6"));

        // Create a JPanel with BoxLayout
        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));
        boxPanel.add(new JLabel("BoxLayout:"));
        boxPanel.add(new JButton("Button 7"));
        boxPanel.add(new JButton("Button 8"));

        // Add panels to the frame
        frame.add(flowPanel, BorderLayout.NORTH);
        frame.add(borderPanel, BorderLayout.CENTER);
        frame.add(gridPanel, BorderLayout.SOUTH);
        frame.add(boxPanel, BorderLayout.EAST);

        // Set the frame size
        frame.setSize(600, 400);

        // Make the frame visible
        frame.setVisible(true);
    }
}
