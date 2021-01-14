package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    
    private int id;
    private String name;
    private String phone;
    private String email;
    private final List<Booking> bookings = new ArrayList<>();

    private boolean deleted;

    public Customer(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;

        this.deleted = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Booking> getBookings() {
        return bookings;
    }
    
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

    public String getDetailsShort(){
        return "ID #" + this.id + " - " + this.name + " - " + this.phone + " " + this.email + " - Bookings: " + bookings.size();
    }

    public void delete(){
        this.deleted = true;
    }

    public void reinstate(){
        this.deleted = false;
    }

    public boolean getDeleted(){
        return this.deleted;
    }
}


