package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.IOException;
/**
 * The command to delete a flight from the FlightBookingSystem. Extends Command.
 * @author Cain Ashmore & Yasser Ibrahim
 * @see Command
 * @see Flight
 * @see FlightBookingSystem
 */
public class DeleteFlight implements Command{
    private final int flightId;

    /**
     * Contructs the command with the flight parameters needed.
     * @param flightId    The ID of the flight which should be deleted.
     */
    public DeleteFlight (int flightId) {
        this.flightId = flightId;
    }

    /**
     * Executes the deletion process for a flight
     * @param flightBookingSystem   he FlightBookingSystem to which the flight should be deleted from.
     * @throws FlightBookingSystemException Thrown if anything occurs which would stop the flight from being deleted.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        Flight flight = flightBookingSystem.getFlightByID(flightId);

        flight.delete();

        try {
            FlightBookingSystemData.store(flightBookingSystem);
        } catch (IOException ex) {
            throw new FlightBookingSystemException("Successfully executed. However data could not be saved at this time."
                    + "\nError: " + ex.getMessage());
        }
    }
}
