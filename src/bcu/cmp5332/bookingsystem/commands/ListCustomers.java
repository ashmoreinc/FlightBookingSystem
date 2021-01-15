package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.util.List;

/** Command for listing customers from FlightBookingSystem. execute() prints all the customers to stdout
 * @author Cain Ashmore and Yasser Ibrahim
 * @see Command
 * @see Customer
 * @see FlightBookingSystem
 */
public class ListCustomers implements Command{
    /**
     * Executes the command by retrieving all the customers and outputting them to STDOUT
     * @param flightBookingSystem   The FlightBookingSystem that Customers will be pulled from
     * @throws FlightBookingSystemException Thrown if anything goes wrong in the process.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        List<Customer> customers = flightBookingSystem.getCustomers();
        for(Customer customer : customers){
            System.out.println(customer.getDetailsShort());
        }
        System.out.println(customers.size() + " customer(s)");
    }
}
