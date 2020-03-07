package View;

import Controller.*;
import Model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.List;

public class View implements IView {

    private JButton addInvoice, deleteInvoice, button;
    private JFrame frame;
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

        for (Invoice v : l) {
            this.centerPanel.add(new JButton(v.getAmount() + " " + v.getDescription()));
            this.centerPanel.add(new JLabel(""));
        }

        this.centerPanel.revalidate();
        this.centerPanel.repaint();
    }

    public void showAddInvoice(){
        this.button = new JButton("Connect");
        this.button.setSize(300, 200);
        
        this.centerPanel.add(this.button);
        frame.add(centerPanel, BorderLayout.CENTER);
        
        this.button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                this.frame.setVisible(false);
                c.insertInvoice(userID, amount, description, date);
                loadList(l);
            }
        });
    }

    public void showMenu() {
        this.frame.removeAll();

        JFrame menuFrame = new JFrame("Simple GUI application");
        // east pannel
        label = new JLabel(String.format("Welcome %s:", ((User) this.data).getName()));
        WestPanel = new JPanel();
        addInvoice = new JButton("Add invoice");
        deleteInvoice = new JButton("Delete invoice");
        addInvoice.setSize(300, 200);
        deleteInvoice.setSize(300, 200);
        WestPanel.setLayout(new GridLayout(12, 1));
        WestPanel.add(label);
        WestPanel.add(new JLabel(""));
        WestPanel.add(addInvoice);
        WestPanel.add(new JLabel(""));
        WestPanel.add(new JLabel(""));
        menuFrame.add(WestPanel, "West");

        // center pannel
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(30, 30));
        label1 = new JLabel(String.format("Budget:\t%s", ((User) this.data).getBudget()));
        label2 = new JLabel(String.format("Current:\t%s", ((User) this.data).getCurrent()));
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
        this.loadList(((User) this.data).getInvoices());
    }

    @Override
    public void initialize(){
      JTextField t1 , t2;
      
      frame = new JFrame("Simple GUI application");
      t1 = new JTextField("please enter id");
      t2 = new JTextField("please enter password");
      this.centerPanel = new JPanel();
      this.centerPanel.setLayout(new GridLayout(3,1));
      t1.setSize(300, 200);
      t2.setSize(300, 200);
      this.centerPanel.add(t1);
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
