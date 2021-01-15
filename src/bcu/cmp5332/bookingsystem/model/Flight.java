package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Holds the Flight data in the FlightBookingSystem
 * @see FlightBookingSystem
 */
public class Flight {
    
    private int id;
    private String flightNumber;
    private String origin;
    private String destination;
    private LocalDate departureDate;

    private int capacity, basePrice;

    private boolean deleted;

    private final Set<Customer> passengers;

    /**
     *
     * @param id    The flights ID
     * @param flightNumber  The flights Number
     * @param origin    The Origin of the flight
     * @param destination   The destination of the flight
     * @param departureDate The date of the departure
     * @param capacity  The seating capacity
     * @param basePrice The base price of the seats
     */
    public Flight(int id, String flightNumber, String origin, String destination, LocalDate departureDate, int capacity, int basePrice) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;

        this.capacity = capacity;
        this.basePrice = basePrice;

        this.deleted = false;

        passengers = new HashSet<>();
    }

    /**
     * The ID getter
     * @return  int
     */
    public int getId() {
        return id;
    }

    /**
     * The ID setter
     * @param id    The ID to be set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * The flight number setter
     * @return  String
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * The flight number setter
     * @param flightNumber  The flight number to be set
     */
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    /**
     * The origin getter
     * @return  String
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * The origin setter
     * @param origin    The origin
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * The destination getter
     * @return  String
     */
    public String getDestination() {
        return destination;
    }

    /**
     * The destination setter
     * @param destination   The destination to be set
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * The departure date getter
     * @return  LocalDate
     */
    public LocalDate getDepartureDate() {
        return departureDate;
    }

    /**
     * The departure date setter
     * @param departureDate The departure date to be set
     */
    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    /**
     * The getter for the list of passengers
     * @return  List<Customer>
     */
    public List<Customer> getPassengers() {
        return new ArrayList<>(passengers);
    }

    /**
     * Capacity getter
     * @return  int
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Capacity setter
     * @param capacity  The capacity to be set
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Base price getter
     * @return  int
     */
    public int getBasePrice() {
        return basePrice;
    }

    /**
     * The base prices setter
     * @param basePrice The base price to be set
     */
    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    /**
     * The short for of the details
     * @return  String
     */
    public String getDetailsShort() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return "Flight #" + id + " - " + flightNumber + " - " + origin + " to " 
                + destination + " on " + departureDate.format(dtf);
    }

    /**
     * The long form of the details
     * @return  String
     */
    public String getDetailsLong() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return "Flight #" + id + " - " + flightNumber + " - " + origin + " to "
                + destination + " on " + departureDate.format(dtf) + " - " + passengers.size() + "/" + capacity
                + " Seats taken, with a base cost of £" + (double)basePrice / 100;
    }

    /**
     * Adds a Customer to the passenger list
     * @param passenger The customer to be added
     */
    public void addPassenger(Customer passenger) {
        for (Customer customer : this.passengers) {
            if(customer.getId() == passenger.getId()) {
                throw new IllegalArgumentException("There is already a passenger with the same ID on this flight.");
            }
        }

        this.passengers.add(passenger);
    }

    /**
     * Removes a customer from the passenger list
     * @param passenger The customer to be removed
     */
    public void removePassenger(Customer passenger){
        for (Customer customer : this.passengers) {
            if(customer.getId() == passenger.getId()) {
                this.passengers.remove(customer);
                return;
            }
        }

        throw new IllegalArgumentException("This passenger was not on this flight.");
    }

    /**
     * Changes the deletion state to true
     */
    public void delete(){
        this.deleted = true;
    }

    /**
     * Changes the deletion state to false
     */
    public void reinstate(){
        this.deleted = false;
    }

    /**
     * Getter for the deleted attribute
     * @return  boolean
     */
    public boolean getDeleted(){
        return this.deleted;
    }
}
