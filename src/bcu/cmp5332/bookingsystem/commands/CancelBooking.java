package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.IOException;

/**
 * The command for cancelling a booking in a FlightBookingSystem. Extends command.m
 * @see Command
 * @see Booking
 * @see FlightBookingSystem
 */
public class CancelBooking implements Command{
    int customerID, flightID;

    /**
     * Constructs the Command with the booking information
     * @param customerID    The customer ID of the Customer in the Booking.
     * @param flightID      The flight ID of the Flight in the Booking.
     */
    public CancelBooking(int customerID, int flightID) {
        this.customerID = customerID;
        this.flightID = flightID;
    }

    /**
     * Executes the command action.
     * @param flightBookingSystem   The FlightBookingSystem which the changes will be made on.
     * @throws FlightBookingSystemException Thrown if anything occurs which would not allow the action to occur.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        Customer customer;
        Flight flight;

        customer = flightBookingSystem.getCustomerByID(this.customerID);
        flight = flightBookingSystem.getFlightByID(this.flightID);


        try {
            customer.cancelBooking(flightID);
            System.out.println("Booking cancelled.");
        } catch (IllegalArgumentException ex){
            throw new FlightBookingSystemException(ex.getMessage());
        }


        try {
            flight.removePassenger(customer);
            System.out.println("Customer removed from Flight.");
        } catch (IllegalArgumentException ex){
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
