package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

/**
 * Main window for the GUI application for managing a FlightBookingSystem.
 * Extends JFrame
 * Implements ActionListener
 * @see FlightBookingSystem
 * @see JFrame
 * @see ActionListener
 */
public class MainWindow extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu adminMenu;
    private JMenu flightsMenu;
    private JMenu bookingsMenu;
    private JMenu customersMenu;

    private JMenuItem adminExit;

    private JMenuItem flightsView;
    private JMenuItem flightsAdd;
    private JMenuItem flightsDel;
    
    private JMenuItem bookingsIssue;
    private JMenuItem bookingsUpdate;
    private JMenuItem bookingsCancel;

    private JMenuItem custView;
    private JMenuItem custAdd;
    private JMenuItem custDel;

    private FlightBookingSystem fbs;

    /**
     * Initialises the Interface
     * @param fbs   The FlightBookingSystem to manage
     */
    public MainWindow(FlightBookingSystem fbs) {

        initialize();
        this.fbs = fbs;
    }

    /**
     * FlightBookingSystem getter
     * @return  FlightBookingSystem
     */
    public FlightBookingSystem getFlightBookingSystem() {
        return fbs;
    }

    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        setTitle("Flight Booking Management System");

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //adding adminMenu menu and menu items
        adminMenu = new JMenu("Admin");
        menuBar.add(adminMenu);

        adminExit = new JMenuItem("Exit");
        adminMenu.add(adminExit);
        adminExit.addActionListener(this);

        // adding Flights menu and menu items
        flightsMenu = new JMenu("Flights");
        menuBar.add(flightsMenu);

        flightsView = new JMenuItem("View");
        flightsAdd = new JMenuItem("Add");
        flightsDel = new JMenuItem("Delete");
        flightsMenu.add(flightsView);
        flightsMenu.add(flightsAdd);
        flightsMenu.add(flightsDel);
        // adding action listener for Flights menu items
        for (int i = 0; i < flightsMenu.getItemCount(); i++) {
            flightsMenu.getItem(i).addActionListener(this);
        }
        
        // adding Bookings menu and menu items
        bookingsMenu = new JMenu("Bookings");
        menuBar.add(bookingsMenu);
        bookingsIssue = new JMenuItem("Issue");
        bookingsUpdate = new JMenuItem("Update");
        bookingsCancel = new JMenuItem("Cancel");
        bookingsMenu.add(bookingsIssue);
        bookingsMenu.add(bookingsUpdate);
        bookingsMenu.add(bookingsCancel);
        // adding action listener for Bookings menu items
        for (int i = 0; i < bookingsMenu.getItemCount(); i++) {
            bookingsMenu.getItem(i).addActionListener(this);
        }

        // adding Customers menu and menu items
        customersMenu = new JMenu("Customers");
        menuBar.add(customersMenu);

        custView = new JMenuItem("View");
        custAdd = new JMenuItem("Add");
        custDel = new JMenuItem("Delete");

        customersMenu.add(custView);
        customersMenu.add(custAdd);
        customersMenu.add(custDel);
        // adding action listener for Customers menu items
        custView.addActionListener(this);
        custAdd.addActionListener(this);
        custDel.addActionListener(this);

        setSize(800, 500);

        setVisible(true);
        setAutoRequestFocus(true);
        toFront();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
/* Uncomment the following line to not terminate the console app when the window is closed */
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);        

    }

    /**
     * The Main function which allows this to run as a standalone applciation
     * @param args  Arguments to be passed on application start up
     * @throws IOException  Thrown if the storage files cannot be read
     * @throws FlightBookingSystemException Thrown if theres an issue setting up the loaded data
     */
    public static void main(String[] args) throws IOException, FlightBookingSystemException {
        FlightBookingSystem fbs = FlightBookingSystemData.load();
        new MainWindow(fbs);
    }

    /**
     * Event listener for handling events.
     * @param ae    ActionEvent which holds the vent information
     */
    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == adminExit) {
            try {
                FlightBookingSystemData.store(fbs);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
            }
            System.exit(0);
        } else if (ae.getSource() == flightsView) {
            displayFlights();
            
        } else if (ae.getSource() == flightsAdd) {
            new AddFlightWindow(this);
            
        } else if (ae.getSource() == flightsDel) {
            new DeleteFlightWindow(this, fbs);
            
        } else if (ae.getSource() == bookingsIssue) {
            
            
        } else if (ae.getSource() == bookingsCancel) {
            
            
        } else if (ae.getSource() == custView) {
            displayCustomers();
            
        } else if (ae.getSource() == custAdd) {
            new AddCustomerWindow(this);
            
        } else if (ae.getSource() == custDel) {
            new DeleteCustomerWindow(this, fbs);
            
        }
    }

    /**
     * Displays the flights onto the Main application window
     */
    public void displayFlights() {
        List<Flight> flightsList = fbs.getAvailableFlights();
        // headers for the table
        String[] columns = new String[]{"Flight No", "Origin", "Destination", "Departure Date", "Seat Count", "Seats Reserved", "Seat Price (£)"};

        Object[][] data = new Object[flightsList.size()][7];
        for (int i = 0; i < flightsList.size(); i++) {
            Flight flight = flightsList.get(i);

            data[i][0] = flight.getFlightNumber();
            data[i][1] = flight.getOrigin();
            data[i][2] = flight.getDestination();
            data[i][3] = flight.getDepartureDate();
            data[i][4] = flight.getCapacity();
            data[i][5] = flight.getPassengers().size();
            data[i][6] = Double.parseDouble(String.valueOf(flight.getBasePrice())) / 100;
        }

        JTable table = new JTable(data, columns);

        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();

        // A lil hacky way to access this rather than the MouseAdapter
        MainWindow parent = this;

        // Mouse listener
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JTable target = (JTable)e.getSource();
                int row = target.getSelectedRow();
                int col = target.getSelectedColumn();

                // Trigger a pop up with the customers when the reserved seats has been clicked.
                if(col == 5) {
                    try {
                        Flight flight = fbs.getFlightByNumber(target.getValueAt(row, 0).toString());
                        new FlightCustomerList(parent, fbs, flight);
                    } catch (FlightBookingSystemException ex) {
                        JOptionPane.showMessageDialog(parent, "Error: " +
                                ex.getMessage(), "Could retrieve flight data.", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    /**
     * Displays the customers onto the Main application window
     */
    public void displayCustomers() {
        List<Customer> customers = fbs.getAvailableCustomers();
        // table headers
        String[] columns = new String[]{"ID", "Name", "Phone", "Email", "Bookings"};

        Object[][] data = new Object[customers.size()][5];
        for(int i=0; i < customers.size(); i++){
            Customer customer = customers.get(i);
            data[i][0] = customer.getId();
            data[i][1] = customer.getName();
            data[i][2] = customer.getPhone();
            data[i][3] = customer.getEmail();
            data[i][4] = customer.getBookings().size();
        }

        JTable table = new JTable(data, columns);
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();

        // A lil hacky way to access this rather than the MouseAdapter
        MainWindow parent = this;

        // Mouse listener
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JTable target = (JTable)e.getSource();
                int row = target.getSelectedRow();
                int col = target.getSelectedColumn();

                // Trigger a pop up with the customers when the reserved seats has been clicked.
                if(col == 4) {
                    try {
                        Customer customer = fbs.getCustomerByID(Integer.parseInt(target.getValueAt(row, 0).toString()));
                        new CustomerBookingList(parent, fbs, customer);
                    } catch (FlightBookingSystemException ex) {
                        JOptionPane.showMessageDialog(parent, "Error: " +
                                ex.getMessage(), "Could retrieve customer data.", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
}
