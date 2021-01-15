package bcu.cmp5332.bookingsystem.model;

import java.time.LocalDate;

/**
 * Holds data about a Customers booking for a Flight
 * @see Customer
 * @see Flight
 * @see LocalDate
 */
public class Booking {
    
    private Customer customer;
    private Flight flight;
    private LocalDate bookingDate;

    /**
     * Constructs all the data for the booking
     * @param customer  The Customer of the Booking
     * @param flight    The Flight of the Booking
     * @param bookingDate   The date of the Booking
     */
    public Booking(Customer customer, Flight flight, LocalDate bookingDate) {
        this.customer = customer;
        this.flight = flight;
        this.bookingDate = bookingDate;
    }

    /**
     * Customer Getter
     * @return  Customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Customer Setter
     * @param customer  The Customer to be set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Flight Getter
     * @return  Flight
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * Flight setter
     * @param flight    The Flight to be set
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    /**
     * The booking date getter
     * @return  LocalDate
     */
    public LocalDate getBookingDate() {
        return bookingDate;
    }

    /**
     * The booking date setter
     * @param bookingDate   The LocalDate to be set
     */
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }
}
