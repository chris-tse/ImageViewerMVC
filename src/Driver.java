import controller.Controller;
import model.Model;
import view.View;

public class Driver {

    public static void main(String[] args) {
        Model model = new Model();

        Controller c = new Controller(model);

        View v = new View(c, model);

        v.setVisible(true);
    }
}
