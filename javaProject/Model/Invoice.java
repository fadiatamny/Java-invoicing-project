package Model;

public class Invoice implements IModel {
    private int _id;
    private double _amount;
    private String _description;
    private String _date;

    
    /** 
     * @param _id
     * @param _amount
     * @param _description
     * @param _date
     * @return 
     */
    public Invoice(final int _id, final double _amount, final String _description, final String _date) {
        this._id = _id;
        this._amount = _amount;
        this._description = _description;
        this._date = _date;
    }

    
    /** 
     * @return int
     */
    public int getID() {
        return _id;
    }

    
    /** 
     * @return double
     */
    public double getAmount() {
        return _amount;
    }

    
    /** 
     * @return String
     */
    public String getDescription() {
        return _description;
    }

    
    /** 
     * @return String
     */
    public String getDate() {
        return _date;
    }

    
    /** 
     * @param id
     */
    public void setID(final int id) {
        this._id = id;
    }

    
    /** 
     * @param amount
     */
    public void setAmount(final double amount) {
        this._amount = amount;
    }

    
    /** 
     * @param description
     */
    public void setDescription(final String description) {
        this._description = description;
    }

    
    /** 
     * @param date
     */
    public void setDate(final String date) {
        this._date = date;
    }

}
