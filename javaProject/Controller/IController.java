package Controller;

import java.sql.Date;

import Model.*;

public interface IController {
   public User getUserDetails(String id, String password);
   public void getInvoices(User s);
   public void insertInvoice(String userID, double amount, String description, String date);
}
