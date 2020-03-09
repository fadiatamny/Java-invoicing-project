package Model;

import java.util.ArrayList;
import java.util.List;

public class User implements IModel {
    private final String _id;
    private String _name;
    private String _password;
    private double _budget = 0;
    private final List<Invoice> _invoices;

    public User(final String id, final String name, final String password, final double budget) {
        this._id = id;
        this._name = name;
        this._password = password;
        this._budget = budget;
        this._invoices = new ArrayList<>();
    }

    public String getName() {
        return this._name;
    }

    public String getID() {
        return this._id;
    }

    public void setName(final String name) {
        this._name = name;
    }

    public String getPassword() {
        return this._password;
    }

    public void setPassword(final String password) {
        this._password = password;
    }

    public double getBudget() {
        return this._budget;
    }

    public void setBudget(final double budget) {
        this._budget = budget;
    }

    public void dumpList() {
        this._invoices.clear();
    }

    public void insertInvoice(final Invoice v) {
        this._invoices.add(v);
    }

    public List<Invoice> getInvoices() {
        return this._invoices;
    }

    public double getCurrent() {
        double sum = 0;

        for (final Invoice v : this._invoices) {
            sum += v.getAmount();
        }
        return sum;
    }
}
