package Controller;

import Model.*;

public interface IController {
   public User getUserDetails(String id);
   public void updateInvoice(Invoice inv);
   public void deleteInvoice(int id);
   public void insertInvoice(String id, Invoice inv);
}
