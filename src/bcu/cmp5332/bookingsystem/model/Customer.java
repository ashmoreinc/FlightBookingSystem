package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds the customer data in the FlightBookingSystem
 * @see FlightBookingSystem
 */
public class Customer {
    
    private int id;
    private String name;
    private String phone;
    private String email;
    private final List<Booking> bookings = new ArrayList<>();

    private boolean deleted;

    /**
     * Constructs all the data about a customer
     * @param id    The customer ID
     * @param name  The customer name
     * @param phone The customer Phone number
     * @param email The customer Email
     */
    public Customer(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;

        this.deleted = false;
    }

    /**
     * ID Getter
     * @return  int
     */
    public int getId() {
        return id;
    }

    /**
     * ID Setter
     * @param id    Customer ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * The name getter
     * @return  String
     */
    public String getName() {
        return name;
    }

    /**
     * The name setter
     * @param name  customer name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The Phone number setter
     * @return  String
     */
    public String getPhone() {
        return phone;
    }

    /**
     * The phone number setter
     * @param phone The phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Email getter
     * @return  String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Email setter
     * @param email The email to be set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * The bookings list getter
     * @return  List<Booking>
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Adds a booking to the bookings list
     * @param booking   The booking to be added
     * @throws IllegalArgumentException Thrown when the bookings attributes arent possible for this user
     */
    public void addBooking(Booking booking) throws IllegalArgumentException{
        Customer bookingCustomer = booking.getCustomer();
        if(bookingCustomer.getId() != this.getId()){
            throw new IllegalArgumentException("Booking customer must have the same id as the customer you are adding the booking too.");
        }

        // Check for existing bookings for this flight
        for(Booking existingBooking : this.bookings){
            if(existingBooking.getFlight().getId() == booking.getFlight().getId()){
                throw new IllegalArgumentException("This customer already has a booking for this flight.");
            }
        }

        this.bookings.add(booking);
    }

    /**
     * Removes a booking from this bookings list
     * @param flightID  The flightID for the booking
     * @throws IllegalArgumentException Thrown when a flight cannot be found with a given ID
     */
    public void cancelBooking(int flightID) throws IllegalArgumentException{
        // Check for existing bookings for this flight
        for(Booking existingBooking : this.bookings){
            if(existingBooking.getFlight().getId() == flightID){
                this.bookings.remove(existingBooking);
                return;
            }
        }

        throw new IllegalArgumentException("This customer does not have a booking for this flight.");
    }

    /**
     * Returns a short form string about the customer
     * @return  String
     */
    public String getDetailsShort(){
        return "ID #" + this.id + " - " + this.name + " - " + this.phone + " " + this.email + " - Bookings: " + bookings.size();
    }

    /**
     * Sets the deletion state to true
     */
    public void delete(){
        this.deleted = true;
    }

    /**
     * Sets the deletion state to false
     */
    public void reinstate(){
        this.deleted = false;
    }

    /**
     * Gets the deletion state
     * @return  Boolean
     */
    public boolean getDeleted(){
        return this.deleted;
    }
}


