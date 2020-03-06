package Controller;
import Model.*;
public class Controller implements IController{

    @Override
    public User getUserDetails(String id) {
        return new User("0","eilon","0",0);
    }

    @Override
    public void updateInvoice(Invoice inv){

    }

    @Override
    public void deleteInvoice(int id){

    }

    @Override
    public void insertInvoice(String id, Invoice inv){

    }
}
