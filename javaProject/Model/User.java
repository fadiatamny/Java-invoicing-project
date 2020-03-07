package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User implements IModel {
    private String _id;
    private String _name;
    private String _password;
    private double _budget = 0;
    private List<Invoice> _invoices;


    public User(String id, String name, String password, double budget) {
        this._id = id;
        this._name = name;
        this._password = password;
        this._budget = budget;
        this._invoices = new ArrayList<>();
    }

    public String getName() {
        return this._name;
    }

    public String getID(){
        return this._id;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getPassword() {
        return this._password;
    }

    public void setPassword(String password) {
        this._password = password;
    }

    public double getBudget() {
        return this._budget;
    }

    public void setBudget(double budget) {
        this._budget = budget;
    }

    Invoice selectInvoice(int i){
        return this._invoices.get(i);
    }

    public void insertInvoice(Invoice v){
        this._invoices.add(v);
    }

    public List<Invoice> getInvoices() {
        return this._invoices;
    }

    public double getCurrent(){
        double sum = 0;

        for(Invoice v : this._invoices)
        {
            sum += v.getAmount();
        }
        return sum;
    }
}
