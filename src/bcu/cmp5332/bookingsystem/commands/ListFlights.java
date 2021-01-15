package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.util.List;

/**
 * Command for listing flights from FlightBookingSystem. execute() prints all the customers to stdout
 * @see Command
 * @see Flight
 * @see FlightBookingSystem
 */
public class ListFlights implements Command {

    /**
     *
     * @param flightBookingSystem   The FlightBookingSystem which Flights will be retrieved from
     * @throws FlightBookingSystemException Thrown if anything goes wrong in the process.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        List<Flight> flights = flightBookingSystem.getFlights();
        for (Flight flight : flights) {
            System.out.println(flight.getDetailsShort());
        }
        System.out.println(flights.size() + " flight(s)");
    }
}
