package Utility;

import Customer.Customer;
import Flight.Flight;

import java.util.List;

public interface DisplayClass {

    void displayRegisteredUsersForAllFlight();

    void displayRegisteredUsersForASpecificFlight(String flightNum);

    void displayHeaderForUsers(Flight flight, List<Customer> c);

    void displayFlightsRegisteredByOneUser(String userID);

}
