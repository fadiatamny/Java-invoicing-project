package View;

import Controller.*;
import Model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class View implements IView {

    
    private JFrame menuFrame;
    private IModel data;
    private IController c;
    private JTable table;

    public View() {
        this.c = new Controller();
    }

    private void loadList(List<Invoice> l) {
        Object[] row = new Object[4];
        DefaultTableModel model = new DefaultTableModel();
        Object[] columns = {"ID","Amount","Description","Date"};
        model.setColumnIdentifiers(columns);

        for (Invoice v : l) {
            row[0] = v.getID();
            row[1] = v.getAmount();
            row[2] = v.getDescription();
            row[3] = v.getDate();

            model.addRow(row);
        }

        this.table.setModel(model);
    }
    
    public void showMenu() {
        
        this.menuFrame = new JFrame("Simple GUI application");
        this.table = new JTable(); 

        c.getInvoices((User)this.data);
        this.loadList(((User) this.data).getInvoices());
        
        // Change A JTable Background Color, Font Size, Font Color, Row Height
        this.table.setBackground(Color.LIGHT_GRAY);
        this.table.setForeground(Color.black);
        Font font = new Font("",1,22);
        this.table.setFont(font);
        this.table.setRowHeight(30);
        
        // create JTextFields
        JTextField textId = new JTextField();
        JTextField textFname = new JTextField();
        
        // create labels
        JLabel current = new JLabel("Current:");
        current.setBounds(150, 220, 100, 25);  
        JLabel budget = new JLabel("Budget:");
        budget.setBounds(50,100, 100,30);  
        
        // create JButtons
        JButton btnAdd = new JButton("Add");
        JButton btnDelete = new JButton("Delete");
        JButton btnUpdate = new JButton("Update");     

        // set sizes to the textfields
        
        textId.setBounds(20, 220, 100, 25);
        textFname.setBounds(20, 250, 100, 25); 
        current.setBounds(150, 220, 100, 25);  
        btnAdd.setBounds(150, 220, 100, 25);
        btnUpdate.setBounds(150, 265, 100, 25);
        btnDelete.setBounds(150, 310, 100, 25);
        
        // create JScrollPane
        JScrollPane pane = new JScrollPane(this.table);
        pane.setBounds(0, 0, 880, 200);
        menuFrame.setLayout(null);
        menuFrame.add(pane);
        
        // add JTextFields to the jframe
        menuFrame.add(textId);
        menuFrame.add(textFname);
        
    
        // add JButtons to the jframe
        menuFrame.add(btnAdd);
        menuFrame.add(btnDelete);
        menuFrame.add(btnUpdate);

        // create an array of objects to set the row data
        Object[] row = new Object[2];
        
        
        btnAdd.addActionListener(new ActionListener(){ //button add row , add row to the this.table

            @Override
            public void actionPerformed(ActionEvent e) {
                row[0] = textId.getText();
                row[1] = textFname.getText();
                DefaultTableModel t = (DefaultTableModel)table.getModel();
                t.addRow(row);
            }
        });
        
        
        btnDelete.addActionListener(new ActionListener(){    // button remove row

            @Override
            public void actionPerformed(ActionEvent e) {
            
                // i = the index of the selected row
                int i = table.getSelectedRow();
                if(i >= 0){
                    // remove a row from jtable
                    DefaultTableModel t = (DefaultTableModel)table.getModel();
                    t.removeRow(i);
                }
                else{
                    System.out.println("Delete Error");
                }
            }
        });
        
            
        this.table.addMouseListener(new MouseAdapter(){ // get selected row data From this.table to textfields
        
        @Override
        public void mouseClicked(MouseEvent e){
            
            // i = the index of the selected row
            int i = table.getSelectedRow();
            textId.setText(table.getModel().getValueAt(i, 0).toString());
            textFname.setText(table.getModel().getValueAt(i, 1).toString());
            
        }
        });
                
        btnUpdate.addActionListener(new ActionListener(){   // button update row

            @Override
            public void actionPerformed(ActionEvent e) {
                
                // i = the index of the selected row
                int i = table.getSelectedRow();
                
                if(i >= 0) 
                {
                    table.getModel().setValueAt(textId.getText(), i, 0);
                    table.getModel().setValueAt(textFname.getText(), i, 1);
                }
                else{
                    System.out.println("Update Error");
                }
            }
        });
        
        menuFrame.setSize(890,400);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setVisible(true);
    }

    @Override
    public void initialize(){
        JFrame frame = new JFrame("Login");
        frame.setSize(350, 200);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(10,20,80,25);
        panel.add(userLabel);
        JTextField userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10,50,80,25);
        panel.add(passwordLabel);
        JTextField passwordText = new JTextField(20);
        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);
        JButton loginButton = new JButton("login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);
        loginButton.addActionListener(new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent e) {
              login(userText.getText(),passwordText.getText()); 
          }
      });
      frame.add(panel);
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
