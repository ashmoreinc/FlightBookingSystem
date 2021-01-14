
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.CommandParser;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Testing for the FlightBookingSystem.
 *
 * @see FlightBookingSystem
 * @see FlightBookingSystemData
 * @see Customer
 * @see Flight
 * @see Booking
 */
public class FlightBookingSystemTest {

    // Store variables for the output streams, so we can test the outputs
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    // Before testing, change the output streams to be directed through the ByteArrayOutput
    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    // Once testing has finished, reset System.Out
    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }



    @Test
    public void testAddCustomer () throws IOException, FlightBookingSystemException {
        FlightBookingSystem fbs = new FlightBookingSystem();
        Customer customer = new Customer(9999, "TEST", "12345678910", "test@test.com");


        List<Customer> before = fbs.getCustomers();

        fbs.addCustomer(customer);

        List<Customer> after = fbs.getCustomers();

        assertTrue(after.contains(customer));
        assertNotSame(before, after);
    }

    @Test
    public void testListCustomers() throws IOException, FlightBookingSystemException {
        FlightBookingSystem fbs = new FlightBookingSystem();
        Customer customer = new Customer(9999, "TEST", "12345678910", "test@test.com");

        // Add a single customer
        fbs.addCustomer(customer);


        Command command = CommandParser.parse("listcustomers");
        command.execute(fbs);

        // Test the output
        // Output expected:  ID #9999 - TEST - 12345678910 test@test.com - Bookings: 0\n1 customer(s)\n
        // Replace all line breaks as there could be a mixture of \n and \r from manual linebreaks (\n) and println linebreaks(\r)
        assertEquals(("ID #9999 - TEST - 12345678910 test@test.com - " +
                "Bookings: 0\n1 customer(s)\n").replaceAll("[\\r\\n ]", ""),
                outContent.toString().replaceAll("[\\r\\n ]", ""));
    }

    @Test
    public void testStorage() throws IOException, FlightBookingSystemException {
        FlightBookingSystem fbs = new FlightBookingSystem();
        Customer customer = new Customer(9999, "TEST", "12345678910", "test@test.com");
        Flight flight = new Flight(9999, "TEST1234", "TEST", "TEST", LocalDate.parse("2021-01-01"), 250, 20000);

        // Add a single customer and save that to storage
        fbs.addCustomer(customer);
        fbs.addFlight(flight);
        FlightBookingSystemData.store(fbs);

        Command command = CommandParser.parse("addbooking 9999 9999");
        command.execute(fbs);

        // Load the new data into a new flight booking system
        FlightBookingSystem newFbs = FlightBookingSystemData.load();

        // Check that 1 customer exists in this new system
        assertTrue(newFbs.getCustomers().size() == 1);
        assertTrue(newFbs.getFlights().size() == 1);
        assertTrue(flight.getPassengers().size() == 1);
        assertTrue(customer.getBookings().size() == 1);
    }

    @Test
    public void testBookingIssuing() throws IOException, FlightBookingSystemException {
        FlightBookingSystem fbs = new FlightBookingSystem();
        Customer customer = new Customer(9999, "TEST", "12345678910", "test@test.com");
        Flight flight = new Flight(9999, "TEST1234", "TEST", "TEST", LocalDate.parse("2021-01-01"), 250, 20000);

        fbs.addCustomer(customer);
        fbs.addFlight(flight);

        Command command = CommandParser.parse("addbooking 9999 9999");
        command.execute(fbs);

        assertTrue(flight.getPassengers().size() == 1);
        assertTrue(customer.getBookings().size() == 1);
    }

    @Test
    public void testBookingCancel() throws IOException, FlightBookingSystemException {
        FlightBookingSystem fbs = new FlightBookingSystem();
        Customer customer = new Customer(9999, "TEST", "12345678910", "test@test.com");
        Flight flight = new Flight(9999, "TEST1234", "TEST", "TEST", LocalDate.parse("2021-01-01"), 250, 20000);

        fbs.addCustomer(customer);
        fbs.addFlight(flight);

        Command command = CommandParser.parse("addbooking 9999 9999");
        command.execute(fbs);

        assertTrue(flight.getPassengers().size() == 1);
        assertTrue(customer.getBookings().size() == 1);

        Command command1 = CommandParser.parse("cancelbooking 9999 9999");
        command1.execute(fbs);

        assertTrue(flight.getPassengers().size() == 0);
        assertTrue(customer.getBookings().size() == 0);
    }
}

