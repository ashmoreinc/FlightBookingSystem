package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Implements DataManager
 * Stores and loads the Customer data for the FlightBookingSystem between storage and memory
 * <p>Data held in the format: ID::Name::Phone Number::Email::Deletion Status</p>
 * @see DataManager
 * @see Customer
 * @see FlightBookingSystem
 */
public class CustomerDataManager implements DataManager {

    private final String RESOURCE = "./resources/data/customers.txt";

    /**
     * Loads the customer data from storage into a FlightBookingSystem
     * @param fbs   The FlightBookingSystem which Customer data is to be loaded to.
     * @throws IOException  Thrown if the RESOURCE file cannot be accessed
     * @throws FlightBookingSystemException Thrown if there is an issue with loading the data into the FlightBookingSystem
     * @see FlightBookingSystem
     * @see Customer
     */
    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        try (Scanner sc = new Scanner(new File(RESOURCE))){
            int line_idx = 1;
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);

                try {
                    int id = Integer.parseInt(properties[0]);
                    String name = properties[1];
                    String phone = properties[2];
                    String email = properties[3];
                    boolean deleted = Boolean.parseBoolean(properties[4]);

                    Customer customer = new Customer(id, name, phone, email);
                    if (deleted) {
                        customer.delete();
                    }
                    fbs.addCustomer(customer);
                } catch (NumberFormatException ex) {
                    throw new FlightBookingSystemException("Unable to parse ID " + properties[0] + " on line " + line_idx
                            + "\nError: " + ex.getMessage());
                }
                line_idx++;
            }
        }
    }

    /**
     * Stores Customer data from the FlightBookingSystem into a RESOURCE file.
     * @param fbs   The FlightBookingSystem which data is to be taken from.
     * @throws IOException  Thrown if the RESOURCE file cannot be accessed.
     * @see FlightBookingSystem
     */
    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for(Customer customer : fbs.getCustomers()){
                out.print(customer.getId() + SEPARATOR);
                out.print(customer.getName() + SEPARATOR);
                out.print(customer.getPhone() + SEPARATOR);
                out.print(customer.getEmail() + SEPARATOR);
                out.print(customer.getDeleted() + SEPARATOR);
                out.println();
            }
        }
    }
}
