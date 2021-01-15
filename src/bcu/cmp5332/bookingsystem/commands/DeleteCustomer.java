package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.model.Customer;
import java.io.IOException;

/**
 * The command to delete a customer from the FlightBookingSystem. Extends Command.
 * @author Cain Ashmore and Yasser Ibrahim
 * @see Command
 * @see Customer
 * @see FlightBookingSystem
 */
public class DeleteCustomer implements Command{

    private final int customerID;

    /**
     * Contructs the command with the customer parameters needed.
     * @param customerID    The ID of the customer which should be deleted.
     */
    public DeleteCustomer(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Executes the deletion process for a customer
     * @param flightBookingSystem   The FlightBookingSystem to which the customer should be deleted from.
     * @throws FlightBookingSystemException Thrown if anything occurs which would stop the customer from being deleted.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        flightBookingSystem.removeCustomer(customerID);

        try {
            FlightBookingSystemData.store(flightBookingSystem);
            System.out.println("Stored");
        } catch (IOException ex) {
            throw new FlightBookingSystemException("Successfully executed. However data could not be saved at this time."
                    + "\nError: " + ex.getMessage());
        }
    }
}
