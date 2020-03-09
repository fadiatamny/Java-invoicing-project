package Model;

public class Invoice implements IModel {
    private int _id;
    private double _amount;
    private String _description;
    private String _date;

    public Invoice(int _id, double _amount, String _description, String _date) {
        this._id = _id;
        this._amount = _amount;
        this._description = _description;
        this._date = _date;
    }

    public int getID() {
        return _id;
    }

    public double getAmount() {
        return _amount;
    }

    public String getDescription() {
        return _description;
    }

    public String getDate() {
        return _date;
    }

    public void setID(int id) {
        this._id = id;
    }

    public void setAmount(double amount) {
        this._amount = amount;
    }

    public void setDescription(String description) {
        this._description = description;
    }

    public void setDate(String date) {
        this._date = date;
    }

}
