// File: View.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View {
    private JFrame frame;
    private JTextField number1Field;
    private JTextField number2Field;
    private JButton addButton;
    private JLabel resultLabel;

    public View() {
        frame = new JFrame("MVC Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,2));

        panel.add(new JLabel("Number 1:"));
        number1Field = new JTextField();
        panel.add(number1Field);

        panel.add(new JLabel("Number 2:"));
        number2Field = new JTextField();
        panel.add(number2Field);

        addButton = new JButton("Add");
        panel.add(addButton);

        panel.add(new JLabel("Result:"));
        resultLabel = new JLabel("");
        panel.add(resultLabel);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public int getNumber1() {
        return Integer.parseInt(number1Field.getText());
    }

    public int getNumber2() {
        return Integer.parseInt(number2Field.getText());
    }

    public void setResult(int result) {
        resultLabel.setText(String.valueOf(result));
    }

    public void addAdditionListener (ActionListener listener) {
        addButton.addActionListener(listener);
    }
}
