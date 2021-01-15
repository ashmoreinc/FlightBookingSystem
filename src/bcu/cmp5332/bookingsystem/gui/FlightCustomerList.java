package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The pop up window which shows a list of Customers of a selected Flight in the FlightBookingSystem.
 * Extends JFrame
 * @author Cain Ashmore & Yasser Ibrahim
 * @see FlightBookingSystem
 * @see bcu.cmp5332.bookingsystem.model.Booking
 * @see Customer
 */
public class FlightCustomerList extends JFrame{

    private MainWindow mw;
    private FlightBookingSystem fbs;
    private Flight flight;

    /**
     * Constructs the window and initializes the GUI.
     * @param mw    The main window which the pop up was created from
     * @param fbs   The FlightBookingSystem which the data will be pulled from
     * @param flightID  The flightID of the flight whose customer list is to be viewed, used to get the Flight.
     */
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

    /**
     * Constructs the window and initializes the GUI, without having to fetch the Flight via it's ID
     * @param mw    The main window which the pop up was created from
     * @param fbs   The FlightBookingSystem which the data will be pulled from
     * @param flight The Flight which the customers are to be listed for.
     */
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
}
