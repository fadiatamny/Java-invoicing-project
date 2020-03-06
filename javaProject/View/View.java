package View;
import Controller.*;
import Model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Constructor;

import java.util.List;

public class View implements IView
{

    private JButton btONE, btTWO, btTHREE ,btFour , btFive , btSix;
    private JFrame frame;
    private JPanel WestPanel , centerPanel , northPanel;
    private JLabel label, label1 , label2;
    private JScrollBar s;
    private IModel data;
    private IController c;

    public View (){
        this.c = new Controller();
    }

    private void loadList(List<Invoice> l){

        this.centerPanel.removeAll();

        this.centerPanel.add(new JLabel(""));
        this.centerPanel.add(new JLabel(""));
        this.centerPanel.add(new JLabel(""));
        this.centerPanel.add(new JLabel(""));
        this.centerPanel.add(new JLabel(""));
        this.centerPanel.add(new JLabel(""));
        this.centerPanel.add(new JLabel(""));

        for(Invoice v : l){
            this.centerPanel.add(new JButton(v.getAmount()+ " " + v.getDescription()));
            this.centerPanel.add(new JLabel(""));
        }

        this.centerPanel.revalidate();
        this.centerPanel.repaint();
    }

    @Override
    public void initialize(String id)
    {  
        System.out.println(id);
        this.data = this.c.getUserDetails(id);

        // frame
        frame =  new JFrame("Simple GUI application");
        // east pannel
        label = new JLabel("Welcome Eilon:");
        WestPanel = new JPanel();
        btONE = new JButton("Add invoice");
        btTWO = new JButton("Delete invoice");
        btTHREE = new JButton("Set new limit");
        btONE.setSize(300,200);
        btTWO.setSize(300,200);
        btTHREE.setSize(300,200);
        WestPanel.setLayout(new GridLayout(12, 1));
        WestPanel.add(label);
        WestPanel.add(new JLabel(""));
        WestPanel.add(btONE);
        WestPanel.add(new JLabel(""));
        WestPanel.add(new JLabel(""));
        WestPanel.add(btTWO);
        WestPanel.add(new JLabel(""));
        WestPanel.add(new JLabel(""));
        WestPanel.add(btTHREE);
        frame.add(WestPanel,"West");

        // center pannel
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(30, 30));
        label1 = new JLabel(String.format("Budget:\t%s", ((User)this.data).getBudget()));
        label2 = new JLabel(String.format("Current:\t%s", ((User)this.data).getCurrent()));
        frame.add(centerPanel,BorderLayout.CENTER);
        //west scroll bar
        s=new JScrollBar();
        s.setBounds(100,100, 50,100);
        frame.add(s,"East");

        //north pannel
        northPanel = new JPanel();
        northPanel.add(label1);
        northPanel.add(label2);
        frame.add(northPanel,"North");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);
        frame.setVisible(true);
        this.loadList(((User)this.data).getInvoices());

        //update;
        // label1.setText("new text");
        // label1.paintImmediately(label1.getVisibleRect());
    }

    @Override
    public void update(User obj, ActionEvent actionEvent) {
        // Object source = actionEvent.getSource();
        // if(source == btONE) // if add invoice pressed
        // { centerPanel.add(btFour);
        //     centerPanel.add(new JLabel(""));
        // }
    }

    @Override
    public void viewDetails(User obj) {

    }

    @Override
    public void updateBudget(double budget) {

    }

    public static void main(String args[])
    {
        View simpleGUI = new View();
        simpleGUI.initialize("THISISAFUCKINGSTRING1");
    }

}
