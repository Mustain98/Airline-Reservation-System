import java.util.List;
import java.util.Scanner;

public class AdminDashboard {
    private String username;
    private Customer c1;
    private Flight f1;
    private List<Customer> customersCollection;
    private FlightReservation bookingAndReserving;
    private static final Scanner scanner = new Scanner(System.in);

    public AdminDashboard(String user, Customer c1, Flight f1, List<Customer> customerCollection, FlightReservation bookingAndReserving) {
        this.username = user;
        this.c1 = c1;
        this.f1 = f1;
        this.customersCollection = customerCollection;
        this.bookingAndReserving = bookingAndReserving;
    }

    public void showOptions() {
        System.out.printf("\n\n%-60s+++++++++ 2nd Layer Menu +++++++++%50sLogged in as \"%s\"\n", "",
                "", username);
        System.out.printf("%-30s (a) Enter 1 to add new Passenger....\n", "");
        System.out.printf("%-30s (b) Enter 2 to search a Passenger....\n", "");
        System.out.printf("%-30s (c) Enter 3 to update the Data of the Passenger....\n", "");
        System.out.printf("%-30s (d) Enter 4 to delete a Passenger....\n", "");
        System.out.printf("%-30s (e) Enter 5 to Display all Passengers....\n", "");
        System.out.printf("%-30s (f) Enter 6 to Display all flights registered by a Passenger...\n",
                "");
        System.out.printf("%-30s (g) Enter 7 to Display all registered Passengers in a Flight....\n",
                "");
        System.out.printf("%-30s (h) Enter 8 to Delete a Flight....\n", "");
        System.out.printf("%-30s (i) Enter 0 to Go back to the Main Menu/Logout....\n", "");
        System.out.print("Enter the desired Choice :   ");
    }

    public int executeOptions(int option) {
        switch (option) {
            case 1 -> addPassenger();
            case 2 -> searchPassenger();
            case 3 -> updatePassenger();
            case 4 -> deletePassenger();
            case 5 -> c1.displayCustomersData(false);
            case 6 -> displayFlightsByUser();
            case 7 -> displayPassengersByFlight();
            case 8 -> deleteFlight();
            case 0 -> System.out.println("Thanks for using BAV Airlines Ticketing System!");
            default -> {
                System.out.println("Invalid Choice...Looks like you're Robot...Entering values randomly...You've Have to login again...");
                option=0;
            }
        }
        return option;
    }

    private void addPassenger() {
        c1.addNewCustomer();
    }

    private void searchPassenger() {
        c1.displayCustomersData(false);
        System.out.print("Enter Customer ID: ");
        String customerID = scanner.nextLine();
        c1.searchUser(customerID);
    }

    private void updatePassenger() {
        c1.displayCustomersData(false);
        System.out.print("Enter Customer ID to update: ");
        String customerID = scanner.nextLine();
        if (!customersCollection.isEmpty()) {
            c1.editUserInfo(customerID);
        } else {
            System.out.println("No customer with ID " + customerID + " found!");
        }
    }

    private void deletePassenger() {
        c1.displayCustomersData(false);
        System.out.print("Enter Customer ID to delete: ");
        String customerID = scanner.nextLine();
        if (!customersCollection.isEmpty()) {
            c1.deleteUser(customerID);
        } else {
            System.out.println("No customer with ID " + customerID + " found!");
        }
    }

    private void displayFlightsByUser() {
        c1.displayCustomersData(false);
        System.out.print("Enter User ID to display their flights: ");
        String id = scanner.nextLine();
        bookingAndReserving.displayFlightsRegisteredByOneUser(id);
    }

    private void displayPassengersByFlight() {
        System.out.print("Display passengers for all flights? (Y/N): ");
        char choice = scanner.nextLine().charAt(0);
        if (choice == 'Y' || choice == 'y') {
            bookingAndReserving.displayRegisteredUsersForAllFlight();
        } else if (choice == 'N' || choice == 'n') {
            f1.displayFlightSchedule();
            System.out.print("Enter Flight Number: ");
            String flightNum = scanner.nextLine();
            bookingAndReserving.displayRegisteredUsersForASpecificFlight(flightNum);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private void deleteFlight() {
        f1.displayFlightSchedule();
        System.out.print("Enter Flight Number to delete: ");
        String flightNum = scanner.nextLine();
        f1.deleteFlight(flightNum);
    }
}
