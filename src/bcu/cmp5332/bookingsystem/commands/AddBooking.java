package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.time.LocalDate;

public class AddBooking implements Command {
    int customerID, flightID;

    public AddBooking(int customerID, int flightID) {
        this.customerID = customerID;
        this.flightID = flightID;
    }

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        Customer customer;
        Flight flight;

        customer = flightBookingSystem.getCustomerByID(this.customerID);
        flight = flightBookingSystem.getFlightByID(flightID);

        Booking booking = new Booking(customer, flight, LocalDate.now());

        try {
            customer.addBooking(booking);
            System.out.println("Booking added for " + customer.getName() + "(" + customerID + ") for flight #" + flight.getId());
        } catch (IllegalArgumentException ex) {
            throw new FlightBookingSystemException(ex.getMessage());
        }
    }
}
