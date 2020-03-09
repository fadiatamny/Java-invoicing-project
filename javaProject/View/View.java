package View;

import Controller.*;
import Model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.util.Calendar;
import java.util.logging.*;

public class View implements IView {

    private JFrame menuFrame;
    private IModel data;
    private final IController c;
    private JTable table;
    private JTextField textAmount;
    private JTextField textDescription;
    private JLabel idHolder, current;
    private Logger logger;

    
    /** 
     * @return 
     */
    public View() {
        this.c = new Controller();
    }

    /**
     * Function for showing the main menu of the programm
     */
    @Override
    public void mainMenu()
    {

        logger = Logger.getLogger("");
        this.menuFrame = new JFrame("Invoice Manager");
        this.table = new JTable();

        // Change A JTable Background Color, Font Size, Font Color, Row Height
        this.table.setBackground(Color.LIGHT_GRAY);
        this.table.setForeground(Color.black);
        final Font font = new Font("", 1, 22);
        this.table.setFont(font);
        this.table.setRowHeight(30);

        // create JTextFields
        final JLabel idLabel = new JLabel("id:");
        idHolder = new JLabel("Choose A Row Please!");
        final JLabel amountLabel = new JLabel("Amount:");
        final JLabel descriptionLabel = new JLabel("Description:");

        this.textAmount = new JTextField();
        this.textDescription = new JTextField();

        // create labels

        this.current = new JLabel("Current: " + (((User) this.data).getBudget() - ((User) this.data).getCurrent()));
        this.current.setBounds(650, 200, 100, 25);

        final JLabel budget = new JLabel("Budget: " + ((User) this.data).getBudget());
        budget.setBounds(750, 200, 100, 25);

        final JLabel userNameLabel = new JLabel("Welcome " + ((User) this.data).getName());
        userNameLabel.setBounds(10, 200, 100, 25);

        this.loadList();

        // create JButtons
        final JButton btnAdd = new JButton("Add");
        final JButton btnDelete = new JButton("Delete");
        final JButton btnUpdate = new JButton("Clear");

        // set sizes to the textfields
        idLabel.setBounds(305, 200, 100, 25);
        idHolder.setBounds(385, 200, 100, 25);
        amountLabel.setBounds(305, 240, 100, 25);
        textAmount.setBounds(385, 240, 100, 25);
        descriptionLabel.setBounds(305, 280, 100, 25);
        textDescription.setBounds(385, 280, 100, 25);
        btnAdd.setBounds(255, 320, 100, 25);
        btnUpdate.setBounds(375, 320, 100, 25);
        btnDelete.setBounds(495, 320, 100, 25);

        // create JScrollPane
        final JScrollPane pane = new JScrollPane(this.table);
        pane.setBounds(0, 0, 880, 200);
        menuFrame.setLayout(null);
        menuFrame.add(pane);

        // add JTextFields to the jframe
        menuFrame.add(idLabel);
        menuFrame.add(amountLabel);
        menuFrame.add(descriptionLabel);

        menuFrame.add(this.idHolder);
        menuFrame.add(this.textAmount);
        menuFrame.add(this.textDescription);

        buttonActions(btnAdd, btnUpdate, btnDelete);

        this.table.addMouseListener(new MouseAdapter() { // get selected row data From this.table to textfields
            @Override
            public void mouseClicked(final MouseEvent e) {
                final int i = table.getSelectedRow();
                idHolder.setBounds(420, 200, 100, 25);
                idHolder.setText(table.getModel().getValueAt(i, 0).toString());
                textAmount.setText(table.getModel().getValueAt(i, 1).toString());
                textDescription.setText(table.getModel().getValueAt(i, 2).toString());
            }
        });

        // add JButtons to the jframe
        menuFrame.add(btnAdd);
        menuFrame.add(btnDelete);
        menuFrame.add(btnUpdate);

        menuFrame.add(this.current);
        menuFrame.add(budget);
        menuFrame.add(userNameLabel);

        menuFrame.setSize(890, 400);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setVisible(true);
        logger.info("Login of " + ((User) this.data).getName() + " Succeeded. " + " \n" + " His current expend is: " + (((User) this.data).getBudget() - ((User) this.data).getCurrent())+ "$" + 
        "\n " + "His budget is: " + ((User) this.data).getBudget()+"$");
    }

    /**
     * Function that clears all data from the table
     */
    private void cleanTable() {
        final DefaultTableModel dm = (DefaultTableModel) this.table.getModel();
        dm.getDataVector().removeAllElements();
        this.table.revalidate();
    }

    private void cleanSelection() {
        idHolder.setBounds(385, 200, 100, 25);
        idHolder.setText("Choose A Row Please!");
        textAmount.setText("");
        textDescription.setText("");
    }

    /**
     * Function that handles showing the invoice list into the table
     */
    private void loadList() {
        this.cleanTable();
        c.getInvoices((User) this.data);
        this.current.setText("Budget: " + (((User) this.data).getBudget() - ((User) this.data).getCurrent()));
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
     * @param btnClear  Button for updating a new invoice
     * @param btnDelete Button for deleting a new invoice
     */
    private void buttonActions(final JButton btnAdd, final JButton btnClear, final JButton btnDelete) {
        logger = Logger.getLogger("");
        btnAdd.addActionListener(e -> {
            if (!textAmount.getText().equals("") && !textDescription.getText().equals("")) {
                try {
                    Double.parseDouble(textAmount.getText());
                    new Thread() {
                        @Override
                        public void run() {
                            final String s = String.format("%s-%s-%s", Calendar.getInstance().get(Calendar.YEAR),
                                    Calendar.getInstance().get(Calendar.MONTH) + 1,
                                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                            c.insertInvoice(((User) data).getID(), Double.parseDouble(textAmount.getText()),
                                    textDescription.getText(), s);
                            cleanSelection();
                            loadList();
                            logger.info("add invoice Succeeded. " + " \n" + " His current expend is: " + (((User) data).getBudget() - ((User) data).getCurrent())+ "$" + 
                            "\n " + "His budget is: " + ((User) data).getBudget()+"$");
                            
                        }
                    }.start();
                } catch (NumberFormatException ex) {
                    System.err.println(ex);
                }
            }
            else
            {
                logger.info("Add row faild"); 
            }
        });

        btnDelete.addActionListener(e -> {
            if (!idHolder.getText().equals("Choose A Row Please!"))
                new Thread() {
                    @Override
                    public void run() {
                        final int i = table.getSelectedRow();
                        c.deleteInvoice(Integer.parseInt(idHolder.getText()));
                        cleanSelection();
                        loadList();
                        logger.info("delete invoice Succeeded. " + " \n" + " His current expend is: " + ((((User) data).getBudget() - ((User) data).getCurrent()))+ "$" + 
                            "\n " + "His budget is: " + ((User) data).getBudget()+"$");
                    }
                }.start();
        });
        btnClear.addActionListener(e -> cleanSelection());
    }

    /**
     * Draws the initial login menu
     */
    @Override
    public void loginMenu() {
        logger = Logger.getLogger("");
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
        final JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(100, 80, 100, 25);
        panel.add(signUpButton);
        loginButton.addActionListener(e -> {
            if (!userText.getText().equals("") && !passwordText.getText().equals("")) {
                frame.setVisible(false);
                login(userText.getText(), passwordText.getText());
            }
            else{
                logger.info("Login faild"); 
            }
        });
        signUpButton.addActionListener(e -> signUpWindow());
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

    /**
     * Function to draw the signup window
     */
    @Override
    public void signUpWindow() {
        logger = Logger.getLogger("");
        final JFrame frame = new JFrame("Invoice Manager - Sign Up");
        frame.setSize(300, 220);
        final JPanel panel = new JPanel();
        panel.setLayout(null);
        final JLabel userLabel = new JLabel("User: ");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);
        final JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        final JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);
        final JTextField passwordText = new JTextField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        final JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setBounds(10, 80, 80, 25);
        panel.add(nameLabel);
        final JTextField nameText = new JTextField(20);
        nameText.setBounds(100, 80, 165, 25);
        panel.add(nameText);

        final JLabel budgetLabel = new JLabel("Budget: ");
        budgetLabel.setBounds(10, 110, 80, 25);
        panel.add(budgetLabel);
        final JTextField budgetText = new JTextField(20);
        budgetText.setBounds(100, 110, 165, 25);
        panel.add(budgetText);

        final JButton submitButton = new JButton("submit");
        submitButton.setBounds(10, 150, 100, 25);
        panel.add(submitButton);
        submitButton.addActionListener(e -> {
            if (!userText.getText().equals("") && !passwordText.getText().equals("") && !nameText.getText().equals("")
                    && !budgetText.getText().equals("")) {
                try {
                    Double.parseDouble(budgetText.getText());
                    new Thread() {
                        @Override
                        public void run() {
                            data = c.signUp(userText.getText(), nameText.getText(), passwordText.getText(),
                                    Double.parseDouble(budgetText.getText()));
                            frame.setVisible(false);
                            mainMenu();
                        }
                    }.start();
                } catch (NumberFormatException ex) {
                    System.err.println(ex);
                }
            }
            else{
                logger.info("Sign Up faild");
            }
        });
        frame.add(panel);
        frame.setVisible(true);
    }

    
    /** 
     * @param args[]
     */
    public static void main(final String args[]) {
        final View simpleGUI = new View();
        simpleGUI.loginMenu();
    }

}
