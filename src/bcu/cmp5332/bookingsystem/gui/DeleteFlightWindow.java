package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.commands.DeleteCustomer;
import bcu.cmp5332.bookingsystem.commands.DeleteFlight;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class DeleteFlightWindow extends JFrame implements ActionListener {
    private MainWindow mw;
    private FlightBookingSystem fbs;

    private JTable table;

    private JButton cancelBtn = new JButton("Cancel");

    public DeleteFlightWindow (MainWindow mw, FlightBookingSystem fbs) {
        this.mw = mw;
        this.fbs = fbs;
        initialize();
    }

    public void initialize (){
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

        List<Flight> flightList = fbs.getFlights();
        // headers for the table
        String[] columns = new String[]{"ID", "Flight Number", "Origin", "Destination", "Date"};

        Object[][] data = new Object[flightList.size()][5];
        for(int i=0; i < flightList.size(); i++){
            Flight flight = flightList.get(i);

            data[i][0] = flight.getId();
            data[i][1] = flight.getFlightNumber();
            data[i][2] = flight.getOrigin();
            data[i][3] = flight.getDestination();
            data[i][4] = flight.getDepartureDate().toString();
        }

        JTable table = new JTable(data, columns);
        bottomPanel.add(new JScrollPane(table));

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JTable target = (JTable)e.getSource();
                int row = target.getSelectedRow();

                int flightID = Integer.parseInt(target.getValueAt(row, 0).toString());

                deleteFlight(flightID);
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

    private void deleteFlight(int flightId){
        Flight flight;
        try {
            flight = fbs.getFlightByID(flightId);

            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete "
                            + flight.getId() + ": #" + flight.getFlightNumber() + " || " + flight.getOrigin()
                            + " -> " + flight.getDestination(), "Confirm",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.NO_OPTION) {
                System.out.println("Deletion Cancelled");
                JOptionPane.showMessageDialog(this, "Delete cancelled");
            } else if (response == JOptionPane.YES_OPTION) {
                System.out.println("Deletion confirmed.");

                Command command = new DeleteFlight(flightId);
                command.execute(fbs);
                JOptionPane.showMessageDialog(this, "Delete complete");
            } else if (response == JOptionPane.CLOSED_OPTION) {
                System.out.println("Window closed");
                JOptionPane.showMessageDialog(this, "Delete cancelled");
            }
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, "Error: " +
                    ex.getMessage(), "Could not delete flight.", JOptionPane.ERROR_MESSAGE);
            this.setVisible(false);
        }

        // Refresh the view with the customer list
        mw.displayFlights();
    }
}
