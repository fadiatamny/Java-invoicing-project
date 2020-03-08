package View;

import Controller.*;
import Model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class View implements IView {

    private JButton addInvoice, deleteInvoice, button;
    private JFrame frame;
    private JFrame menuFrame;
    private JPanel WestPanel, centerPanel, northPanel;
    private JLabel label, label1, label2;
    private JScrollBar s;
    private IModel data;
    private IController c;

    public View() {
        this.c = new Controller();
    }

    private void loadList(List<Invoice> l) {

        this.centerPanel.removeAll();

        this.centerPanel.add(new JLabel(""));
        this.centerPanel.add(new JLabel(""));
        this.centerPanel.add(new JLabel(""));
        this.centerPanel.add(new JLabel(""));
        this.centerPanel.add(new JLabel(""));
        this.centerPanel.add(new JLabel(""));
        this.centerPanel.add(new JLabel(""));
        System.out.println(l.size());

        for (Invoice v : l) {
            JButton b = new JButton(v.getAmount() + " " + v.getDescription());
            b.addActionListener(new ActionListener()
            {
                public Invoice in = v;
                public void actionPerformed(ActionEvent e) {
                    showInvoiceDetails(in);
                }
            });

            this.centerPanel.add(b);
            this.centerPanel.add(new JLabel(""));
        }
        System.out.println("here");


        this.centerPanel.revalidate();
        this.centerPanel.repaint();
    }

    private void showInvoiceDetails(Invoice i){
        //show a menu with delete or add that can allow us to add delete or update options.
    }

    public void showAddInvoice(){
  
        JFrame newFrame = new JFrame();
        JPanel newPanel = new JPanel();
        JButton btn = new JButton("add");
        User s = (User)this.data;
        JTextField t1 , t2;
        t1 = new JTextField("please enter description of invoice:");
        t2 = new JTextField("please enter amount of invoice:");
        newPanel.add(t1);
        newPanel.add(t2);
        newPanel.add(btn);
        newFrame.add(newPanel, BorderLayout.CENTER);
        newFrame.setVisible(true);
        btn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                c.insertInvoice(s.getID(), Double.parseDouble(t2.getText()), t1.getText(),new Date(Calendar.getInstance().getTimeInMillis()));
                newFrame.setVisible(false);
            }
        });
       
    }
    
    public void showMenu() {
      //  this.frame.removeAll();

        this.menuFrame = new JFrame("Simple GUI application");
        // east pannel
        label = new JLabel(String.format("Welcome %s:", ((User) this.data).getName()));
        //label = new JLabel("welcome Eilon");
        WestPanel = new JPanel();
        addInvoice = new JButton("Add invoice");
        deleteInvoice = new JButton("Delete invoice");
        addInvoice.setSize(300, 200);
        deleteInvoice.setSize(300, 200);
        WestPanel.setLayout(new GridLayout(12, 1));
        WestPanel.add(label);
        WestPanel.add(new JLabel(""));
        WestPanel.add(addInvoice);
        WestPanel.add(deleteInvoice);
        WestPanel.add(new JLabel(""));
        WestPanel.add(new JLabel(""));
        menuFrame.add(WestPanel, "West");
        this.addInvoice.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               showAddInvoice();
            }
        });


        // center pannel
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(30, 30));
      //  label1 = new JLabel(String.format("Budget:\t%s", ((User) this.data).getBudget()));
        label1 = new JLabel("budjet: ");
       // label2 = new JLabel(String.format("Current:\t%s", ((User) this.data).getCurrent()));
        label2 = new JLabel("Current: ");
        menuFrame.add(centerPanel, BorderLayout.CENTER);
        // west scroll bar
        s = new JScrollBar();
        s.setBounds(100, 100, 50, 100);
        menuFrame.add(s, "East");

        // north pannel
        northPanel = new JPanel();
        northPanel.add(label1);
        northPanel.add(label2);
        menuFrame.add(northPanel, "North");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(400, 400);
        menuFrame.setVisible(true);
        c.getInvoices((User)this.data);
        this.loadList(((User) this.data).getInvoices());
    }

    @Override
    public void initialize(){
      JTextField t1 , t2;
      
      frame = new JFrame("Simple GUI application");
      t1 = new JTextField();
      t2 = new JTextField();
      this.centerPanel = new JPanel();
      this.centerPanel.setLayout(new GridLayout(5,1));
      t1.setSize(300, 200);
      t2.setSize(300, 200);
      JLabel lb1 = new JLabel("please enter id:");
      JLabel lb2 = new JLabel("please enter password:");
      this.centerPanel.add(lb1);
      this.centerPanel.add(t1);
      this.centerPanel.add(lb2);
      this.centerPanel.add(t2);
      this.button = new JButton("Connect");
      this.button.setSize(300, 200);
      this.centerPanel.add(this.button);
      frame.add(centerPanel, BorderLayout.CENTER);
      
      this.button.addActionListener(new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent e) {
              login(t1.getText(),t2.getText());
          }
      });
      frame.setSize(400, 400);
      frame.setVisible(true);
    }

    public void login(String id, String password){
        this.data = this.c.getUserDetails(id,password);
        if (this.data != null) {
            showMenu();
        }
    }

    @Override
    public void update(User obj, ActionEvent actionEvent) {
        // Object source = actionEvent.getSource();
        // if(source == btONE) // if add invoice pressed
        // { centerPanel.add(btFour);
        // centerPanel.add(new JLabel(""));
        // }
    }

    @Override
    public void viewDetails(User obj) {

    }

    @Override
    public void updateBudget(double budget) {

    }

    public static void main(String args[]) {
        View simpleGUI = new View();
        simpleGUI.initialize();
    }

}
