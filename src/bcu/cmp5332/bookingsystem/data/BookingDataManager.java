package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Implements DataManger.
 * Stores and loads the Bookings for FlightBookingSystem between storage and memory.
 * Data held in format: CustomerID::FlightID::LocalDate
 * @author Cain Ashmore
 * @see bcu.cmp5332.bookingsystem.data.DataManager
 * @see FlightBookingSystem
 * @see Booking
 * @see Customer
 * @see Flight
 */
public class BookingDataManager implements DataManager {
    
    public final String RESOURCE = "./resources/data/bookings.txt";

    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        try (Scanner sc = new Scanner(new File(RESOURCE))){
            int line_idx = 1;


            Customer customer;
            Flight flight;
            LocalDate date;
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);


                // Parse customer details
                try {
                    int custID = Integer.parseInt(properties[0]);

                    customer = fbs.getCustomerByID(custID);
                } catch (NumberFormatException ex){
                    throw new FlightBookingSystemException("Unable to parse customer ID " + properties[0] + " on line " + line_idx
                            + "\nError: " + ex);
                } catch (FlightBookingSystemException ex) {
                    throw new FlightBookingSystemException("Could not retrieve customer data for ID " + properties[1]
                            + "\nReason: " + ex.getMessage());
                }

                // Parse flight details
                try {
                    int flightID = Integer.parseInt(properties[1]);

                    flight = fbs.getFlightByID(flightID);
                } catch (NumberFormatException ex){
                    throw new FlightBookingSystemException("Unable to parse flight ID " + properties[1] + " on line " + line_idx
                            + "\nError: " + ex);
                } catch (FlightBookingSystemException ex) {
                    throw new FlightBookingSystemException("Could not retrieve flight data for ID " + properties[1]
                            + "\nReason: " + ex.getMessage());
                }

                // Parse date
                try{
                    date = LocalDate.parse(properties[2]);
                } catch (DateTimeParseException ex) {
                    throw new FlightBookingSystemException("Unable to parse date " + properties[2] + " on line " + line_idx
                            + "\nError: " + ex.getMessage());
                }

                // Add data
                Booking booking  = new Booking(customer, flight, date);
                customer.addBooking(booking);
                flight.addPassenger(customer);

                line_idx++;
            }
        }
    }

    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for(Customer customer : fbs.getCustomers()){
                for(Booking booking : customer.getBookings()){
                    out.print(booking.getCustomer().getId() + SEPARATOR);
                    out.print(booking.getFlight().getId() + SEPARATOR);
                    out.print(booking.getBookingDate().toString());
                    out.println();
                }
            }
        }
    }
    
}
