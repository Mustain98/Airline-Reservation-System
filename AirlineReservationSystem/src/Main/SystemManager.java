package Main;

import Admin.AdminManager;
import Customer.CustomerManager;
import Flight.FlightManager;
import Flight.FlightReservation;

import java.util.Scanner;
import static Utility.Message.*;

public class SystemManager {

    static int countNumOfUsers = 1;

    public static void main(String[] args) {
        FlightManager f1 = FlightManager.getInstance();
        FlightReservation bookingAndReserving = new FlightReservation();
        CustomerManager c1 = CustomerManager.getInstance();
        AdminManager am1 = new AdminManager();
        Scanner read = new Scanner(System.in);
        int desiredOption;

        getAirlineLogo();
        getDefaultAdmininfo();
        displayMainMenu();

        desiredOption = read.nextInt();
        while (desiredOption < 0 || desiredOption > 5) {
            invalidOption();
            desiredOption = read.nextInt();
        }

        do {
            if (desiredOption == 1) {
                handleAdminLogin(c1, f1, bookingAndReserving, am1);
            } else if (desiredOption == 2) {
                handleAdminRegister(am1);
            } else if (desiredOption == 3) {
                handleCustomerLogin(read, f1, bookingAndReserving, c1);
            } else if (desiredOption == 4) {
                c1.addNewCustomer();
            } else if (desiredOption == 5) {
                displayManuals();
            }

            displayMainMenu();
            desiredOption = read.nextInt();
            while (desiredOption < 0 || desiredOption > 5) {
                invalidOption();
                desiredOption = read.nextInt();
            }
        } while (desiredOption != 0);
    }

    private static void handleAdminLogin (CustomerManager c1 , FlightManager f1, FlightReservation bookingAndReserving, AdminManager am1) {
        Scanner read=new Scanner(System.in);
        int desiredOption;
        System.out.print("\nEnter the UserName to login to the Management System :     ");
        String username = read.nextLine();
        System.out.print("Enter the Password to login to the Management System :    ");
        String password = read.nextLine();
        System.out.println();

        /* Checking the RolesAndPermissions...... */
        if (am1.isPrivilegedUserOrNot(username, password)==-1) {
            failedCustomerLogin();
        } else if (am1.isPrivilegedUserOrNot(username, password)==0) {
            System.out.println(
                    "You've standard/default privileges to access the data... You can just view customers data..."
                            + "Can't perform any actions on them....");
            c1.displayCustomersData(true);
        } else {
            System.out.printf(
                    "%-20sLogged in Successfully as \"%s\"..... For further Proceedings, enter a value from below....",
                    "", username);
            do {
                AdminDashboard adminDashboard=new AdminDashboard(username,c1,f1,bookingAndReserving);
                adminDashboard.showOptions();
                desiredOption = read.nextInt();
                desiredOption=adminDashboard.executeOptions(desiredOption);
            } while (desiredOption != 0);

        }
    }
    private static void handleAdminRegister(AdminManager am1){
        Scanner read=new Scanner(System.in);
        System.out.print("\nEnter the UserName to Register :    ");
        String username = read.nextLine();
        System.out.print("Enter the Password to Register :     ");
        String password = read.nextLine();
        while (am1.isPrivilegedUserOrNot(username, password) != -1) {
            System.out.print("ERROR!!! Admin with same UserName already exist. Enter new UserName:   ");
            username = read.nextLine();
            System.out.print("Enter the Password Again:   ");
            password = read.nextLine();
        }

        am1.addNewAdmin(username, password);
        countNumOfUsers++;
    }
    private static void handleCustomerLogin(Scanner read, FlightManager f1, FlightReservation bookingAndReserving, CustomerManager c1) {
        System.out.print("\n\nEnter the Email to Login: ");
        String userName = read.next();
        System.out.print("Enter the Password: ");
        String password = read.next();
        String[] result = c1.isPassengerRegistered(userName, password).split("-");

        if (Integer.parseInt(result[0]) == 1) {
            int desiredChoice;
            successfulCustomerLogin(userName);
            do {
                CustomerDashboard customerDashboard = new CustomerDashboard();
                customerDashboard.DisplayOption(userName);
                desiredChoice = read.nextInt();
                desiredChoice = customerDashboard.getDesiredChoice(desiredChoice, f1, bookingAndReserving, result, c1, userName);
            } while (desiredChoice != 0);
        } else {
            failedCustomerLogin();
        }
    }

    static void displayMainMenu() {
        System.out.println("\n\n\t\t(a) Press 0 to Exit.");
        System.out.println("\t\t(b) Press 1 to Login as admin.");
        System.out.println("\t\t(c) Press 2 to Register as admin.");
        System.out.println("\t\t(d) Press 3 to Login as Passenger.");
        System.out.println("\t\t(e) Press 4 to Register as Passenger.");
        System.out.println("\t\t(f) Press 5 to Display the User Manual.");
        System.out.print("\t\tEnter the desired option: ");
    }

    static void displayManuals() {
        Scanner read = new Scanner(System.in);
        System.out.printf("%n%n%50s %s Welcome to BAV Airlines User Manual %s", "", "+++++++++++++++++", "+++++++++++++++++");
        System.out.println("\n\n\t\t(a) Press 1 to display Admin Manual.");
        System.out.println("\t\t(b) Press 2 to display User Manual.");
        System.out.print("\nEnter the desired option: ");
        int choice = read.nextInt();
        while (choice < 1 || choice > 2) {
            System.out.print("ERROR!!! Invalid entry...Please enter a value either 1 or 2....Enter again: ");
            choice = read.nextInt();
        }
        switch (choice) {
            case 1:
                adminManual();
                break;
            case 2:
                userManual();
                break;
        }
    }

    private static void adminManual() {
        System.out.println("\n\n(1) Admin have the access to all users data...Admin can delete, update, add and can perform search for any customer...\n");
        System.out.println("(2) In order to access the admin module, you've to get yourself register by pressing 2, when the main menu gets displayed...\n");
        System.out.println("(3) Provide the required details i.e., name, email, id...Once you've registered yourself, press 1 to login as an admin... \n");
        System.out.println("(4) Once you've logged in, 2nd layer menu will be displayed on the screen...From here on, you can select from variety of options...\n");
        System.out.println("(5) Pressing \"1\" will add a new Passenger, provide the program with required details to add the passenger...\n");
        System.out.println("(6) Pressing \"2\" will search for any passenger, given the admin(you) provides the ID from the table printing above....  \n");
        System.out.println("(7) Pressing \"3\" will let you update any passengers data given the user ID provided to program...\n");
        System.out.println("(8) Pressing \"4\" will let you delete any passenger given its ID provided...\n");
        System.out.println("(9) Pressing \"5\" will let you display all registered passenger...\n");
    }

    private static void userManual() {
        System.out.println("\n\n(1) Local user has the access to its data only...He/She won't be able to change/update other users data...\n");
        System.out.println("(2) In order to access local users benefits, you've to get yourself register by pressing 4, when the main menu gets displayed...\n");
        System.out.println("(3) Provide the details asked by the program to add you to the users list...Once you've registered yourself, press \"3\" to login as a passenger...\n");
        System.out.println("(4) Once you've logged in, 3rd layer menu will be displayed...From here on, you embarked on the journey to fly with us...\n");
        System.out.println("(5) Pressing \"1\" will display available/scheduled list of flights...\n");
        System.out.println("(7) Pressing \"2\" will let you update your own data...\n");
        System.out.println("(8) Pressing \"3\" will delete your account...\n");
    }
}
