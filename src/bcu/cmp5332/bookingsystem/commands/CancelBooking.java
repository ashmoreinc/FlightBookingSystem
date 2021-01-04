package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class CancelBooking implements Command{
    int customerID, flightID;

    public CancelBooking(int customerID, int flightID) {
        this.customerID = customerID;
        this.flightID = flightID;
    }

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
    }
}
