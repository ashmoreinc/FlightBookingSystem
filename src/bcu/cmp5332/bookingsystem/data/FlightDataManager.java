package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Implements DataManager.
 * Stores and loads the Flight data for the FlightBookingSystem between storage and memory
 * <p>Data is held in the format: ID::Flight Number::Origin::Destination::Departure Date::Seats::Base Price::Deletion State</p>
 * @see bcu.cmp5332.bookingsystem.data.DataManager
 * @see FlightBookingSystem
 * @see Flight
 */
public class FlightDataManager implements DataManager {
    
    private final String RESOURCE = "./resources/data/flights.txt";

    /**
     * Loads the customer data from storage into a FlightBookingSystem
     * @param fbs   The FlightBookingSystem which Flight data is to be loaded to.
     * @throws IOException  Thrown if the RESOURCE file cannot be accessed
     * @throws FlightBookingSystemException Thrown if there is an issue with loading the data into the FlightBookingSystem
     * @see FlightBookingSystem
     * @see Flight
     */
    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        try (Scanner sc = new Scanner(new File(RESOURCE))) {
            int line_idx = 1;

            int id, capacity, basePrice;
            String flightNumber, origin, destination;
            LocalDate departureDate;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);

                // Handle id parsing
                try {
                    id = Integer.parseInt(properties[0]);

                } catch (NumberFormatException ex) {
                    throw new FlightBookingSystemException("Unable to parse ID " + properties[0] + " on line " + line_idx
                        + "\nError: " + ex);
                }

                flightNumber = properties[1];
                origin = properties[2];
                destination = properties[3];
                departureDate = LocalDate.parse(properties[4]);

                // Handle capacity/available seats parsing
                try {
                    capacity = Integer.parseInt(properties[5]);
                }catch(NumberFormatException ex){
                    throw new FlightBookingSystemException("Unable to parse seat capacity " + properties[5] + " on line " + line_idx
                            + "\nError: " + ex);
                }

                // Handle base price parsing
                try {
                    basePrice = Integer.parseInt(properties[6]);
                }catch(NumberFormatException ex){
                    throw new FlightBookingSystemException("Unable to parse seat price " + properties[6] + " on line " + line_idx
                            + "\nError: " + ex);
                }

                boolean deleted = Boolean.parseBoolean(properties[7]);

                Flight flight = new Flight(id, flightNumber, origin, destination, departureDate, capacity, basePrice);
                if(deleted) {
                    flight.delete();
                }
                fbs.addFlight(flight);
                line_idx++;
            }
        }
    }

    /**
     * Stores Flight data from the FlightBookingSystem into a RESOURCE file.
     * @param fbs   The FlightBookingSystem which data is to be taken from.
     * @throws IOException  Thrown if the RESOURCE file cannot be accessed.
     * @see FlightBookingSystem
     */
    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Flight flight : fbs.getFlights()) {
                out.print(flight.getId() + SEPARATOR);
                out.print(flight.getFlightNumber() + SEPARATOR);
                out.print(flight.getOrigin() + SEPARATOR);
                out.print(flight.getDestination() + SEPARATOR);
                out.print(flight.getDepartureDate() + SEPARATOR);
                out.print(flight.getCapacity() + SEPARATOR);
                out.print(flight.getBasePrice() + SEPARATOR);
                out.print(flight.getDeleted() + SEPARATOR);
                out.println();
            }
        }
    }
}
