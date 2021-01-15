package bcu.cmp5332.bookingsystem.main;

import bcu.cmp5332.bookingsystem.commands.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Parses Command Strings and returns a valid Command for execution or an Exception of failure
 * @see Command
 * @see IOException
 * @see FlightBookingSystemException
 */
public class CommandParser {

	/**
	 * The main command parser
	 * @param line	The command string which will be parsed into a command
	 * @return	Command
	 * @throws IOException
	 * @throws FlightBookingSystemException Thrown on Failure to parse a valid command or if there is an issue with the command
	 */
	public static Command parse(String line) throws IOException, FlightBookingSystemException {
		try {
			String[] parts = line.split(" ", 3);
			String cmd = parts[0];

			if (cmd.equals("addflight")) {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("Flight Number: ");
				String flightNumber = br.readLine();
				System.out.print("Origin: ");
				String origin = br.readLine();
				System.out.print("Destination: ");
				String destination = br.readLine();

				System.out.print("Seats available: ");
				int capacity = Integer.parseInt(br.readLine());

				System.out.print("Seat price (pence): ");
				int basePrice = Integer.parseInt(br.readLine());

				LocalDate departureDate = parseDateWithAttempts(br);
				return new AddFlight(flightNumber, origin, destination, departureDate, capacity, basePrice);
			} else if (cmd.equals("addcustomer")) {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Name: ");
				String name = br.readLine();
				System.out.println("Phone: ");
				String phone = br.readLine();
				System.out.println("Email: ");
				String email = br.readLine();

				return new AddCustomer(name, phone, email);
			} else if (cmd.equals("loadgui")) {
				return new LoadGUI();
			} else if (parts.length == 1) {
				if (line.equals("listflights")) {
					return new ListFlights();
				} else if (line.equals("listcustomers")) {
					return new ListCustomers();
				} else if (line.equals("help")) {
					return new Help();
				}
			} else if (parts.length == 2) {
				int id = Integer.parseInt(parts[1]);

				if (cmd.equals("showflight")) {

				} else if (cmd.equals("showcustomer")) {

				}
			} else if (parts.length == 3) {
				int arg1Int = Integer.parseInt(parts[1]); // Renamed from patronID as the first argument is not always the patron.
				int arg2Int = Integer.parseInt(parts[2]);

				if (cmd.equals("addbooking")) {
					// Add booking args: arg1 = customerID. arg2 = flightID
					return new AddBooking(arg1Int, arg2Int);
				} else if (cmd.equals("editbooking")) {

				} else if (cmd.equals("cancelbooking")) {
					// Cancel booking args: arg1 = customerID. arg2 = flightID
					return new CancelBooking(arg1Int, arg2Int);
				}
			}
		} catch (NumberFormatException ex) {

		}

		throw new FlightBookingSystemException("Invalid command.");
	}

	private static LocalDate parseDateWithAttempts(BufferedReader br, int attempts) throws IOException, FlightBookingSystemException {
		if (attempts < 1) {
			throw new IllegalArgumentException("Number of attempts should be higher that 0");
		}
		while (attempts > 0) {
			attempts--;
			System.out.print("Departure Date (\"YYYY-MM-DD\" format): ");
			try {
				LocalDate departureDate = LocalDate.parse(br.readLine());
				return departureDate;
			} catch (DateTimeParseException dtpe) {
				System.out.println("Date must be in YYYY-MM-DD format. " + attempts + " attempts remaining...");
			}
		}

		throw new FlightBookingSystemException("Incorrect departure date provided. Cannot create flight.");
	}

	private static LocalDate parseDateWithAttempts(BufferedReader br) throws IOException, FlightBookingSystemException {
		return parseDateWithAttempts(br, 3);
	}
}
