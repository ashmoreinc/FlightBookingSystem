package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    
    private int id;
    private String name;
    private String phone;
    private final List<Booking> bookings = new ArrayList<>();

    public Customer(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
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

    public List<Booking> getBookings() {
        return bookings;
    }
    
    public void addBooking(Booking booking) throws IllegalArgumentException{
        Customer bookingCustomer = booking.getCustomer();
        if(bookingCustomer.getId() != this.getId()){
            throw new IllegalArgumentException("booking customer must have the same id as the customer you are adding the booking too.");
        }

        // Check for existing bookings for this flight
        for(Booking existingBooking : this.bookings){
            if(existingBooking.getFlight().getId() == booking.getFlight().getId()){
                throw new IllegalArgumentException("This customer already has a booking for this flight.");
            }
        }

        this.bookings.add(booking);
    }

    public String getDetailsShort(){
        return "ID #" + this.id + " - " + this.name + " - " + this.phone + " - Bookings: " + bookings.size();
    }
}


