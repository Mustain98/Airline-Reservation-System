import java.util.Scanner;

public class CustomerDashboard {
    public void DisplayOption(String userName) {
        System.out.printf("\n\n%-60s+++++++++ 3rd Layer Menu +++++++++%50sLogged in as \"%s\"\n", "",
                "", userName);
        System.out.printf("%-40s (a) Enter 1 to Book a flight....\n", "");
        System.out.printf("%-40s (b) Enter 2 to update your Data....\n", "");
        System.out.printf("%-40s (c) Enter 3 to delete your account....\n", "");
        System.out.printf("%-40s (d) Enter 4 to Display Flight Schedule....\n", "");
        System.out.printf("%-40s (e) Enter 5 to Cancel a Flight....\n", "");
        System.out.printf("%-40s (f) Enter 6 to Display all flights registered by \"%s\"....\n", "",
                userName);
        System.out.printf("%-40s (g) Enter 0 to Go back to the Main Menu/Logout....\n", "");
        System.out.print("Enter the desired Choice :   ");
    }
    public int getDesiredChoice(int desiredChoice, FlightManager f1, FlightReservation bookingAndReserving, String[] result, Customer c1, String userName) {
        Scanner read = new Scanner(System.in);
        if (desiredChoice == 1) {
            f1.displayFlightSchedule();
            bookTicket(bookingAndReserving, result, read);
        } else if (desiredChoice == 2) {
            c1.editUserInfo(result[1]);
        } else if (desiredChoice == 3) {
             deleteAccount(result, c1, userName, read);
             desiredChoice=0;
        } else if (desiredChoice == 4) {
            f1.displayFlightSchedule();
            f1.displayMeasurementInstructions();
        } else if (desiredChoice == 5) {
            bookingAndReserving.cancelFlight(result[1]);
        } else if (desiredChoice == 6) {
            bookingAndReserving.displayFlightsRegisteredByOneUser(result[1]);
        } else {
            desiredChoice = invalidChoice(desiredChoice);
        }
        return desiredChoice;
    }

    private static int invalidChoice(int desiredChoice) {
        if (desiredChoice != 0) {
            System.out.println(
                    "Invalid Choice...Looks like you're Robot...Entering values randomly...You've Have to login again...");
        }
        desiredChoice = 0;
        return desiredChoice;
    }

    private  void deleteAccount( String[] result, Customer c1, String userName, Scanner read) {
        System.out.print(
                "Are you sure to delete your account...It's an irreversible action...Enter Y/y to confirm...");
        char confirmationChar = read.nextLine().charAt(0);
        if (confirmationChar == 'Y' || confirmationChar == 'y') {
            c1.deleteUser(result[1]);
            System.out.printf("User %s's account deleted Successfully...!!!", userName);
        } else {
            System.out.println("Action has been cancelled...");
        }
    }

    private  void bookTicket(FlightReservation bookingAndReserving, String[] result, Scanner read) {
        System.out.print("\nEnter the desired flight number to book :\t ");
        String flightToBeBooked = read.nextLine();
        System.out.print("Enter the Number of tickets for " + flightToBeBooked + " flight :   ");
        int numOfTickets = read.nextInt();
        while (numOfTickets > 10) {
            System.out.print(
                    "ERROR!! You can't book more than 10 tickets at a time for single flight....Enter number of tickets again : ");
            numOfTickets = read.nextInt();
        }
        bookingAndReserving.bookFlight(flightToBeBooked, numOfTickets, result[1]);
    }


}
