package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.util.*;

public class FlightBookingSystem {
    
    private final LocalDate systemDate = LocalDate.parse("2020-11-11");
    
    private final Map<Integer, Customer> customers = new TreeMap<>();
    private final Map<Integer, Flight> flights = new TreeMap<>();

    public LocalDate getSystemDate() {
        return systemDate;
    }

    public List<Flight> getFlights() {
        List<Flight> out = new ArrayList<>(flights.values());
        return Collections.unmodifiableList(out);
    }

    public List<Flight> getAvailableFlights(){
        List<Flight> out = new ArrayList<>();
        for(Flight flight : flights.values()) {
            if(!flight.getDeleted()){
                out.add(flight);
            }
        }

        return out;
    }

    public List<Customer> getCustomers(){
        List<Customer> out = new ArrayList<>(customers.values());
        return Collections.unmodifiableList(out);
    }

    public List<Customer> getAvailableCustomers(){
        List<Customer> out = new ArrayList<>();
        for(Customer customer : customers.values()) {
            if(!customer.getDeleted()){
                out.add(customer);
            }
        }

        return out;
    }

    public Flight getFlightByID(int id) throws FlightBookingSystemException {
        if (!flights.containsKey(id)) {
            throw new FlightBookingSystemException("There is no flight with that ID.");
        }
        return flights.get(id);
    }

    public Flight getFlightByNumber(String flightNumber) throws FlightBookingSystemException {
        for(Flight flightEntry : flights.values()){
            if(flightEntry.getFlightNumber() == flightNumber){
                return flightEntry;
            }
        }

        throw new FlightBookingSystemException("There is no flight with that number.");
    }

    public Customer getCustomerByID(int id) throws FlightBookingSystemException {
        if(!this.customers.containsKey(id)) throw new FlightBookingSystemException("There is no customer with that ID.");

        return customers.get(id);
    }

    public void addFlight(Flight flight) throws FlightBookingSystemException {
        if (flights.containsKey(flight.getId())) {
            throw new IllegalArgumentException("Duplicate flight ID.");
        }
        for(Flight flightEntry : flights.values()){
            if(flightEntry.getFlightNumber() == flight.getFlightNumber()){
                throw new FlightBookingSystemException("Duplicate flight number.");
            }
        }

        for (Flight existing : flights.values()) {
            if (existing.getFlightNumber().equals(flight.getFlightNumber()) 
                && existing.getDepartureDate().isEqual(flight.getDepartureDate())) {
                throw new FlightBookingSystemException("There is a flight with same "
                        + "number and departure date in the system");
            }
        }
        flights.put(flight.getId(), flight);
    }

    public void addCustomer(Customer customer) throws FlightBookingSystemException {
        if(customers.containsKey(customer.getId())){
            throw new IllegalArgumentException("Duplicate customer ID.");
        }
        for (Customer existingCustomer : customers.values()){
            if(existingCustomer.getName().equals(customer.getName())
                && existingCustomer.getPhone().equals(customer.getPhone())) {
                throw new FlightBookingSystemException("There is already a customer with the same name"
                        + " and phone details in the system.");
            }
        }
        customers.put(customer.getId(), customer);
    }

    public void removeFlight (int flightID) throws FlightBookingSystemException{
        if(this.flights.containsKey(flightID)) {
            if(!this.flights.get(flightID).getDeleted()){
                this.flights.get(flightID).delete();
            } else {
                throw new FlightBookingSystemException("Flight ID#" + flightID + " is already deleted.");
            }
        } else {
            throw new FlightBookingSystemException("No flight with ID#" + flightID + " could be found for deletion");
        }
    }

    public void removeCustomer (int customerID) throws FlightBookingSystemException{
        if(this.customers.containsKey(customerID)) {
            if(!this.customers.get(customerID).getDeleted()){
                this.customers.get(customerID).delete();
            } else {
                throw new FlightBookingSystemException("Customer ID#" + customerID + " is already deleted.");
            }
        } else {
            throw new FlightBookingSystemException("No customer with ID#" + customerID + " could be found for deletion.");
        }
    }
}
