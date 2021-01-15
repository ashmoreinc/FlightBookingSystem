package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddCustomer;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The pop up window which shows a form to add a Customer to the FlightBookingSystem.
 * Extends JFrame
 * Implements ActionListener
 * @author Cain Ashmore & Yasser Ibrahim
 * @see FlightBookingSystem
 * @see bcu.cmp5332.bookingsystem.model.Customer
 */
public class AddCustomerWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField customerNameText = new JTextField();
    private JTextField customerPhoneText = new JTextField();
    private JTextField customerEmailText = new JTextField();

    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");

    /**
     * stores the main window and runs the initialize method.
     * @param mw    The MainWindow which the pop up was created from
     * @see MainWindow
     */
    public AddCustomerWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    private void initialize(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored){

        }

        setTitle("Add a New Customer");
        setSize(350, 220);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4, 2));
        topPanel.add(new JLabel("Customer Name: "));
        topPanel.add(customerNameText);
        topPanel.add(new JLabel("Customer Phone: "));
        topPanel.add(customerPhoneText);
        topPanel.add(new JLabel("Customer Email: "));
        topPanel.add(customerEmailText);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(addBtn);
        bottomPanel.add(cancelBtn);

        addBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);
    }

    /**
     * The event listener which GUI elements can point to, to handle events.
     * @param ae    The ActionEvent that triggered this function
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            addCust();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }

    private void addCust(){
        try {
            String customerName = customerNameText.getText();
            String customerPhone = customerPhoneText.getText();
            String customerEmail = customerEmailText.getText();

            // create and execute the AddCustomer Command

            Command addCustomer = new AddCustomer(customerName, customerPhone, customerEmail);
            addCustomer.execute(mw.getFlightBookingSystem());
            // Refresh the view with the customer list
            mw.displayCustomers();
            // close the AddCustomerWindow
            this.setVisible(false);
        } catch (FlightBookingSystemException ex){
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
