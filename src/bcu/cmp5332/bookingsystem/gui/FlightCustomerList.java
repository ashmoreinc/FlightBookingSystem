package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FlightCustomerList extends JFrame implements ActionListener {

    private MainWindow mw;
    private FlightBookingSystem fbs;
    private Flight flight;

    public FlightCustomerList (MainWindow mw, FlightBookingSystem fbs, int flightID){
        this.mw = mw;
        this.fbs = fbs;

        try {
            this.flight = fbs.getFlightByID(flightID);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(mw, "Error: " +
                    ex.getMessage(), "Could retrieve flight data.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        initialize();
    }

    public FlightCustomerList (MainWindow mw, FlightBookingSystem fbs, Flight flight){
        this.mw = mw;
        this.fbs = fbs;

        this.flight = flight;

        initialize();
    }

    private void initialize(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored){}

        setTitle("Flight passengers");
        setSize(500, 340);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 1));

        // Page header
        JLabel header = new JLabel("Customers for flight #" + flight.getFlightNumber());
        header.setFont(new Font("Verdana", Font.PLAIN, 18));
        topPanel.add(header);

        // Customer list
        List<Customer> passengers = this.flight.getPassengers();
        // headers for the table
        String[] columns = new String[]{"ID", "Name", "Phone", "Email"};

        Object[][] data = new Object[passengers.size()][4];
        for(int i=0; i < passengers.size(); i++){
            Customer customer = passengers.get(i);

            data[i][0] = customer.getId();
            data[i][1] = customer.getName();
            data[i][2] = customer.getPhone();
            data[i][3] = customer.getEmail();
        }

        JTable table = new JTable(data, columns);

        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        this.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
        setLocationRelativeTo(mw);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
