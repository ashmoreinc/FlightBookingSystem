package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.commands.DeleteCustomer;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class DeleteCustomerWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private FlightBookingSystem fbs;

    private JTable table;

    private JButton cancelBtn = new JButton("Cancel");

    public DeleteCustomerWindow (MainWindow mw, FlightBookingSystem fbs){
        this.mw = mw;
        this.fbs = fbs;
        initialize();
    }


    private void initialize () {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored){}

        setTitle("Delete customer");
        setSize(350, 220);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4, 2));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(cancelBtn);

        cancelBtn.addActionListener(this);

        List<Customer> customerList = fbs.getCustomers();
        // headers for the table
        String[] columns = new String[]{"ID", "Name", "Phone", "Email"};

        Object[][] data = new Object[customerList.size()][4];
        for(int i=0; i < customerList.size(); i++){
            Customer customer = customerList.get(i);

            data[i][0] = customer.getId();
            data[i][1] = customer.getName();
            data[i][2] = customer.getPhone();
            data[i][3] = customer.getEmail();
        }

        JTable table = new JTable(data, columns);
        bottomPanel.add(new JScrollPane(table));

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JTable target = (JTable)e.getSource();
                int row = target.getSelectedRow();

                int custNo = Integer.parseInt(target.getValueAt(row, 0).toString());

                deleteCustomer(custNo);
            }
        });

        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        this.getContentPane().add(table, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }

    private void deleteCustomer(int customerID){
        Customer customer;
        try {
            customer = fbs.getCustomerByID(customerID);

            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete "
                            + customer.getId() + ": " + customer.getName(), "Confirm",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.NO_OPTION) {
                System.out.println("Deletion Cancelled");
                JOptionPane.showMessageDialog(this, "Delete cancelled");
            } else if (response == JOptionPane.YES_OPTION) {
                System.out.println("Deletion confirmed.");

                Command command = new DeleteCustomer(customerID);
                command.execute(fbs);
                JOptionPane.showMessageDialog(this, "Delete complete");
            } else if (response == JOptionPane.CLOSED_OPTION) {
                System.out.println("Window closed");
                JOptionPane.showMessageDialog(this, "Delete cancelled");
            }

        } catch(FlightBookingSystemException ex){
            JOptionPane.showMessageDialog(this, "Error: " +
                    ex.getMessage(), "Could not delete user.", JOptionPane.ERROR_MESSAGE);
            this.setVisible(false);
        }

        // Refresh the view with the customer list
        mw.displayCustomers();
    }

}
