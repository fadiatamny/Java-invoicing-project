package View;

import Controller.*;
import Model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.util.Calendar;

public class View implements IView {

    private JFrame menuFrame;
    private IModel data;
    private final IController c;
    private JTable table;
    private JTextField textAmount;
    private JTextField textDescription;
    private JLabel id;

    public View() {
        this.c = new Controller();
    }

    /**
     * Function for showing the main menu of the programm
     */
    @Override
    public void mainMenu() {
        this.menuFrame = new JFrame("Invoice Manager");
        this.table = new JTable();

        this.loadList();

        // Change A JTable Background Color, Font Size, Font Color, Row Height
        this.table.setBackground(Color.LIGHT_GRAY);
        this.table.setForeground(Color.black);
        final Font font = new Font("", 1, 22);
        this.table.setFont(font);
        this.table.setRowHeight(30);

        // create JTextFields
        this.textAmount = new JTextField();
        this.textDescription = new JTextField();
        this.id = new JLabel("id:");

        // create labels
        final JLabel current = new JLabel("Current:");
        current.setBounds(150, 220, 100, 25);
        final JLabel budget = new JLabel("Budget:");
        budget.setBounds(50, 100, 100, 30);

        // create JButtons
        final JButton btnAdd = new JButton("Add");
        final JButton btnDelete = new JButton("Delete");
        final JButton btnUpdate = new JButton("Update");

        // set sizes to the textfields

        textAmount.setBounds(20, 220, 100, 25);
        textDescription.setBounds(20, 250, 100, 25);
        current.setBounds(150, 220, 100, 25);
        btnAdd.setBounds(150, 220, 100, 25);
        btnUpdate.setBounds(150, 265, 100, 25);
        btnDelete.setBounds(150, 310, 100, 25);

        // create JScrollPane
        final JScrollPane pane = new JScrollPane(this.table);
        pane.setBounds(0, 0, 880, 200);
        menuFrame.setLayout(null);
        menuFrame.add(pane);

        // add JTextFields to the jframe
        menuFrame.add(this.id);
        menuFrame.add(this.textAmount);
        menuFrame.add(this.textDescription);

        buttonActions(btnAdd, btnUpdate, btnDelete);

        // add JButtons to the jframe
        menuFrame.add(btnAdd);
        menuFrame.add(btnDelete);
        menuFrame.add(btnUpdate);

        menuFrame.setSize(890, 400);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setVisible(true);
    }

    /**
     * Function that clears all data from the table
     */
    private void cleanTable() {
        DefaultTableModel dm = (DefaultTableModel) this.table.getModel();
        dm.getDataVector().removeAllElements();
        this.table.revalidate();
    }

    /**
     * Function that handles showing the invoice list into the table
     */
    private void loadList() {
        this.cleanTable();
        c.getInvoices((User) this.data);
        final List<Invoice> l = ((User) this.data).getInvoices();
        final Object[] row = new Object[4];
        final DefaultTableModel model = new DefaultTableModel();
        final Object[] columns = { "ID", "Amount", "Description", "Date" };
        model.setColumnIdentifiers(columns);

        for (final Invoice v : l) {
            row[0] = v.getID();
            row[1] = v.getAmount();
            row[2] = v.getDescription();
            row[3] = v.getDate().split("T")[0];

            model.addRow(row);
        }

        this.table.setModel(model);
    }

    /**
     * Function that handles all button Action Events initilization
     * 
     * @param btnAdd    Button for adding a new invoice
     * @param btnUpdate Button for updating a new invoice
     * @param btnDelete Button for deleting a new invoice
     */
    private void buttonActions(final JButton btnAdd, final JButton btnUpdate, final JButton btnDelete) {
    
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        String s = String.format("%s-%s-%s", Calendar.getInstance().get(Calendar.YEAR),
                                Calendar.getInstance().get(Calendar.MONTH)+1,
                                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                        c.insertInvoice(((User) data).getID(), Double.parseDouble(textAmount.getText()),
                                textDescription.getText(), s);
                        loadList();
                    }
                }.start();
            }
        });

        btnDelete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        String s = String.format("%s-%s-%s", Calendar.getInstance().get(Calendar.YEAR),
                                Calendar.getInstance().get(Calendar.MONTH)+1,
                                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                        c.insertInvoice(((User) data).getID(), Double.parseDouble(textAmount.getText()),
                                textDescription.getText(), s);
                        loadList();
                    }
                }.start();
            }
        });

        this.table.addMouseListener(new MouseAdapter() { // get selected row data From this.table to textfields

            @Override
            public void mouseClicked(final MouseEvent e) {

                // i = the index of the selected row
                final int i = table.getSelectedRow();
                textAmount.setText(table.getModel().getValueAt(i, 0).toString());
                textDescription.setText(table.getModel().getValueAt(i, 1).toString());

            }
        });

        btnUpdate.addActionListener(new ActionListener() { // button update row

            @Override
            public void actionPerformed(final ActionEvent e) {

                // i = the index of the selected row
                final int i = table.getSelectedRow();

                if (i >= 0) {
                    table.getModel().setValueAt(textAmount.getText(), i, 0);
                    table.getModel().setValueAt(textDescription.getText(), i, 1);
                } else {
                    System.out.println("Update Error");
                }
            }
        });
    }

    /**
     * Draws the initial login menu
     */
    @Override
    public void loginMenu() {
        final JFrame frame = new JFrame("Invoice Manager - Login");
        frame.setSize(350, 200);
        final JPanel panel = new JPanel();
        panel.setLayout(null);
        final JLabel userLabel = new JLabel("User");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);
        final JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);
        final JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);
        final JTextField passwordText = new JTextField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);
        final JButton loginButton = new JButton("login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                login(userText.getText(), passwordText.getText());
            }
        });
        frame.add(panel);
        frame.setVisible(true);
    }

    /**
     * Function to verify login data
     * 
     * @param id
     * @param password
     */
    public void login(final String id, final String password) {
        this.data = this.c.getUserDetails(id, password);
        if (this.data != null) {
            mainMenu();
        }
    }

    public static void main(final String args[]) {
        final View simpleGUI = new View();
        simpleGUI.loginMenu();
    }

}
