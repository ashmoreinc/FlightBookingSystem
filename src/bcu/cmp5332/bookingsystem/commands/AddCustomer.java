package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.IOException;

/**
 * Command for adding a Customer to the FlightBookingSystem. Execute adds the customer supplied to the FlightBookingSystem. Extends Command
 * @author Cain Ashmore & Yasser Ibrahim
 * @see Command
 * @see Customer
 * @see FlightBookingSystem
 */
public class AddCustomer implements Command {

    private final String name;
    private final String phone;
    private final String email;

    /**
     *
     * @param name  The name of the customer
     * @param phone The phone number of the customer
     * @param email The email of the customer
     */
    public AddCustomer(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Executes the command action.
     * @param flightBookingSystem   The flight booking system the customer is to be added too.
     * @throws FlightBookingSystemException Throws the FlightBookingSystemException when anything occurs which will not allow the customer to be created.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        int maxId = 0;
        if(flightBookingSystem.getCustomers().size() > 0){
            int lastIndex = flightBookingSystem.getCustomers().size() - 1;
            maxId = flightBookingSystem.getCustomers().get(lastIndex).getId();
        }

        Customer customer = new Customer(++maxId, this.name, this.phone, this.email);
        flightBookingSystem.addCustomer(customer);
        System.out.println("Customer #" + customer.getId() + " added.");

        try {
            FlightBookingSystemData.store(flightBookingSystem);
        } catch (IOException ex) {
            throw new FlightBookingSystemException("Successfully executed. However data could not be saved at this time."
                    + "\nError: " + ex.getMessage());
        }
    }
}
