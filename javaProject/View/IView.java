package View;
import Model.*;

import java.awt.event.ActionEvent;

public interface IView {
    public void initialize(String id);
    public void update(User obj, ActionEvent actionEvent);
    public void viewDetails(User obj);
    public void updateBudget(double budget);
}
