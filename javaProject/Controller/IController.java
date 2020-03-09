package Controller;

import Model.*;

public interface IController {
   public User signUp(String id, String name, String password, double budget);

   public User getUserDetails(String id, String password);

   public void getInvoices(User s);

   public void insertInvoice(String userID, double amount, String description, String date);

   public void deleteInvoice(int id);
}
