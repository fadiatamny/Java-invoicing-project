package Model;

import java.util.ArrayList;
import java.util.List;

public class User implements IModel {
    private final String _id;
    private String _name;
    private String _password;
    private double _budget = 0;
    private final List<Invoice> _invoices;

    
    /** 
     * @param id
     * @param name
     * @param password
     * @param budget
     * @return 
     */
    public User(final String id, final String name, final String password, final double budget) {
        this._id = id;
        this._name = name;
        this._password = password;
        this._budget = budget;
        this._invoices = new ArrayList<>();
    }

    
    /** 
     * @return String
     */
    public String getName() {
        return this._name;
    }

    
    /** 
     * @return String
     */
    public String getID() {
        return this._id;
    }

    
    /** 
     * @param name
     */
    public void setName(final String name) {
        this._name = name;
    }

    
    /** 
     * @return String
     */
    public String getPassword() {
        return this._password;
    }

    
    /** 
     * @param password
     */
    public void setPassword(final String password) {
        this._password = password;
    }

    
    /** 
     * @return double
     */
    public double getBudget() {
        return this._budget;
    }

    
    /** 
     * @param budget
     */
    public void setBudget(final double budget) {
        this._budget = budget;
    }

    public void dumpList() {
        this._invoices.clear();
    }

    
    /** 
     * @param v
     */
    public void insertInvoice(final Invoice v) {
        this._invoices.add(v);
    }

    
    /** 
     * @return List<Invoice>
     */
    public List<Invoice> getInvoices() {
        return this._invoices;
    }

    
    /** 
     * @return double
     */
    public double getCurrent() {
        double sum = 0;

        for (final Invoice v : this._invoices) {
            sum += v.getAmount();
        }
        return sum;
    }
}
