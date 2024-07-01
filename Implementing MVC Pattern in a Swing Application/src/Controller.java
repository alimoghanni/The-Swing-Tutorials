// File: Controller.java
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        this.view.addAdditionListener(new AdditionListener());
    }

    class AdditionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int number1 = view.getNumber1();
            int number2 = view.getNumber2();

            model.setNumber1(number1);
            model.setNumber2(number2);

            int sum = model.getSum();

            view.setResult(sum);
        }
    }
}
