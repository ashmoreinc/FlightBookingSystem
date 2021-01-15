package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CustomerBookingList  extends JFrame implements ActionListener {

    private MainWindow mw;
    private FlightBookingSystem fbs;
    private Customer customer;

    public CustomerBookingList (MainWindow mw, FlightBookingSystem fbs, int customerID){
        this.mw = mw;
        this.fbs = fbs;

        try {
            this.customer = fbs.getCustomerByID(customerID);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(mw, "Error: " +
                    ex.getMessage(), "Could retrieve customer data.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        initialize();
    }

    public CustomerBookingList(MainWindow mw, FlightBookingSystem fbs, Customer customer){
        this.mw = mw;
        this.fbs = fbs;

        this.customer = customer;

        initialize();
    }

    private void initialize(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored){}

        setTitle("Customer Bookings");
        setSize(500, 340);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 1));

        // Page header
        JLabel header = new JLabel("Bookings for customer #" + customer.getId() + " - " + customer.getName());
        header.setFont(new Font("Verdana", Font.PLAIN, 18));
        topPanel.add(header);

        // Customer list
        List<Booking> bookings = this.customer.getBookings();
        // headers for the table
        String[] columns = new String[]{"Flight Number", "Origin", "Destination", "Departure Date", "Booking Date"};

        Object[][] data = new Object[bookings.size()][5];
        for(int i=0; i < bookings.size(); i++){
            Booking booking = bookings.get(i);

            data[i][0] = booking.getFlight().getFlightNumber();
            data[i][1] = booking.getFlight().getOrigin();
            data[i][2] = booking.getFlight().getDestination();
            data[i][3] = booking.getFlight().getDepartureDate().toString();
            data[i][4] = booking.getBookingDate().toString();
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
