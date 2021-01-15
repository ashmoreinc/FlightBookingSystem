package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Command used to add a booking to a flight for a customer. Extends Command.
 * @author Cain Ashmore and Yasser Ibrahim
 * @see Command
 * @see Flight
 * @see Customer
 * @see FlightBookingSystem
 */
public class AddBooking implements Command {
    int customerID, flightID;

    /**
     * Constructs the booking
     * @param customerID    The ID of the customer which a booking is to be made for
     * @param flightID      The ID of the flight which a booking is to be made for
     * @see Customer
     * @see Flight
     */
    public AddBooking(int customerID, int flightID) {
        this.customerID = customerID;
        this.flightID = flightID;
    }

    /**
     * Executes the command action.
     * @param flightBookingSystem   The FlightBookingSystem which these changes are to be made too
     * @throws FlightBookingSystemException Throws the FlightBookingSystemException when anything occurs which will not allow the booking to be made.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        Customer customer;
        Flight flight;

        customer = flightBookingSystem.getCustomerByID(this.customerID);
        flight = flightBookingSystem.getFlightByID(flightID);

        Booking booking = new Booking(customer, flight, LocalDate.now());

        if(flight.getPassengers().size() >= flight.getCapacity()) {
            throw new FlightBookingSystemException("Flight is already at capacity. Could not and any more.");
        }

        try {
            customer.addBooking(booking);
            flight.addPassenger(customer);
            System.out.println("Booking added for " + customer.getName() + "(" + customerID + ") for flight #" + flight.getId());
        } catch (IllegalArgumentException ex) {
            throw new FlightBookingSystemException(ex.getMessage());
        }

        try {
            FlightBookingSystemData.store(flightBookingSystem);
        } catch (IOException ex) {
            throw new FlightBookingSystemException("Successfully executed. However data could not be saved at this time."
                    + "\nError: " + ex.getMessage());
        }
    }
}
