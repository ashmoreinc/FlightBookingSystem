package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Command for adding a Flight to the FlightBookingSystem. Extends Command.
 * @author Cain Ashmore & Yasser Ibrahim
 * @see Command
 * @see Flight
 * @see FlightBookingSystem
 */
public class AddFlight implements Command {

    private final String flightNumber;
    private final String origin;
    private final String destination;
    private final LocalDate departureDate;
    private final int capacity, basePrice;

    /**
     * Constructs the command with the Flight information
     * @param flightNumber  The number for the flight to be created
     * @param origin        The original location of the flight
     * @param destination   The destination location of the flight
     * @param departureDate The date the flight will depart
     * @param capacity      The total seat capacity of the flight
     * @param basePrice     The base price of the flight. Used to judge how much a seat should cost based on other factors such as date, rebooking etc.
     */
    public AddFlight(String flightNumber, String origin, String destination, LocalDate departureDate, int capacity, int basePrice) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.capacity = capacity;
        this.basePrice = basePrice;
    }

    /**
     * Executes the command action.
     * @param flightBookingSystem   The FlightBookingSystem which the flight will be added too.
     * @throws FlightBookingSystemException Throws the FlightBookingSystemException when anything occurs which will not allow the flight to be created.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        int maxId = 0;
        if (flightBookingSystem.getFlights().size() > 0) {
            int lastIndex = flightBookingSystem.getFlights().size() - 1;
            maxId = flightBookingSystem.getFlights().get(lastIndex).getId();
        }
        
        Flight flight = new Flight(++maxId, flightNumber, origin, destination, departureDate, capacity, basePrice);
        flightBookingSystem.addFlight(flight);
        System.out.println("Flight #" + flight.getId() + " added.");

        try {
            FlightBookingSystemData.store(flightBookingSystem);
        } catch (IOException ex) {
            throw new FlightBookingSystemException("Successfully executed. However data could not be saved at this time."
                    + "\nError: " + ex.getMessage());
        }
    }
}
