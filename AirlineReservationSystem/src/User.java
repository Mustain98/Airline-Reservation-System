/*
 * This class is intended to be the main class for this Project. All necessary methods are getting calls from this class.
 *
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class User {

    // ************************************************************ Fields
    // ************************************************************

    /*
     * 2D Array to store admin credentials. Default credentials are stored on [0][0]
     * index. Max num of admins can be 10....
     */
    private static final String defaultAdminUserName="root";
    private static final String defaultAdminPass="root";
    private static List<Customer> customersCollection = new ArrayList<>();
    static HashMap<String,String>adminUserNameAndPassword=new HashMap<>();
    static int countNumOfUsers = 1;
    // ************************************************************
    // Behaviours/Methods
    // ************************************************************

    public static void main(String[] args) {

        RolesAndPermissions r1 = new RolesAndPermissions();
        Flight f1 = new Flight();
        FlightReservation bookingAndReserving = new FlightReservation();
        Customer c1 = new Customer();
        f1.flightScheduler();
        Scanner read = new Scanner(System.in);
        adminUserNameAndPassword.put(defaultAdminUserName, defaultAdminPass);

        System.out.println(
                "\n\t\t\t\t\t+++++++++++++ Welcome to BAV AirLines +++++++++++++\n\nTo Further Proceed, Please enter a value.");
        System.out.println(
                "\n***** Default Username && Password is root-root ***** Using Default Credentials will restrict you to just view the list of Passengers....\n");
        displayMainMenu();
        int desiredOption = read.nextInt();
        while (desiredOption < 0 || desiredOption > 8) {
            System.out.print("ERROR!! Please enter value between 0 - 4. Enter the value again :\t");
            desiredOption = read.nextInt();
        }

        do {
            Scanner read1 = new Scanner(System.in);
            if (desiredOption == 1) {
                handleAdminLogin(c1,f1,r1,bookingAndReserving);
            }
            else if (desiredOption == 2) {
                handleAdminResister(r1);
            }
            else if (desiredOption == 3) {
                handleCustomerLogin(read1, r1, read, f1, bookingAndReserving, c1);
            }
            else if (desiredOption == 4) {
                c1.addNewCustomer();
            }
            else if (desiredOption == 5) {
                manualInstructions();
            }

            displayMainMenu();
            desiredOption = read1.nextInt();
            while (desiredOption < 0 || desiredOption > 8) {
                System.out.print("ERROR!! Please enter value between 0 - 4. Enter the value again :\t");
                desiredOption = read1.nextInt();
            }
        } while (desiredOption != 0);

    }

    private static void handleCustomerLogin(Scanner read1, RolesAndPermissions r1, Scanner read, Flight f1, FlightReservation bookingAndReserving, Customer c1) {
        System.out.print("\n\nEnter the Email to Login : \t");
        String userName = read1.nextLine();
        System.out.print("Enter the Password : \t");
        String password = read1.nextLine();
        String[] result = r1.isPassengerRegistered(userName, password).split("-");

        if (Integer.parseInt(result[0]) == 1) {
            int desiredChoice;
            System.out.printf(
                    "\n\n%-20sLogged in Successfully as \"%s\"..... For further Proceedings, enter a value from below....",
                    "", userName);
            do {
                CustomerDashboard customerDashboard = new CustomerDashboard();
                customerDashboard.DisplayOption(userName);
                desiredChoice = read.nextInt();
                desiredChoice = customerDashboard.getDesiredChoice(desiredChoice, f1, bookingAndReserving, result, c1, userName);
            } while (desiredChoice != 0);

        } else {
            System.out.printf(
                    "\n%20sERROR!!! Unable to login Cannot find user with the entered credentials.... Try Creating New Credentials or get yourself register by pressing 4....\n",
                    "");
        }
    }

         static void displayMainMenu() {
        System.out.println("\n\n\t\t(a) Press 0 to Exit.");
        System.out.println("\t\t(b) Press 1 to Login as admin.");
        System.out.println("\t\t(c) Press 2 to Register as admin.");
        System.out.println("\t\t(d) Press 3 to Login as Passenger.");
        System.out.println("\t\t(e) Press 4 to Register as Passenger.");
        System.out.println("\t\t(f) Press 5 to Display the User Manual.");
        System.out.print("\t\tEnter the desired option:    ");
    }

         static void manualInstructions() {
        Scanner read = new Scanner(System.in);
        System.out.printf("%n%n%50s %s Welcome to BAV Airlines User Manual %s", "", "+++++++++++++++++",
                "+++++++++++++++++");
        System.out.println("\n\n\t\t(a) Press 1 to display Admin Manual.");
        System.out.println("\t\t(b) Press 2 to display User Manual.");
        System.out.print("\nEnter the desired option :    ");
        int choice = read.nextInt();
        while (choice < 1 || choice > 2) {
            System.out.print("ERROR!!! Invalid entry...Please enter a value either 1 or 2....Enter again....");
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
         private static void handleAdminLogin (Customer c1 ,Flight f1,RolesAndPermissions r1,FlightReservation bookingAndReserving) {
        Scanner read=new Scanner(System.in);
        int desiredOption;
        System.out.print("\nEnter the UserName to login to the Management System :     ");
        String username = read.nextLine();
        System.out.print("Enter the Password to login to the Management System :    ");
        String password = read.nextLine();
        System.out.println();

        /* Checking the RolesAndPermissions...... */
        if (r1.isPrivilegedUserOrNot(username, password)==-1) {
            System.out.printf(
                    "\n%20sERROR!!! Unable to login Cannot find user with the entered credentials.... Try Creating New Credentials or get yourself register by pressing 4....\n",
                    "");
        } else if (r1.isPrivilegedUserOrNot(username, password)==0) {
            System.out.println(
                    "You've standard/default privileges to access the data... You can just view customers data..."
                            + "Can't perform any actions on them....");
            c1.displayCustomersData(true);
        } else {
            System.out.printf(
                    "%-20sLogged in Successfully as \"%s\"..... For further Proceedings, enter a value from below....",
                    "", username);

            /*
             * Going to Display the CRUD operations to be performed by the privileged
             * user.....Which includes Creating, Updating
             * Reading(Searching) and deleting a customer....
             */
            do {
                AdminDashboard adminDashboard=new AdminDashboard(username,c1,f1,customersCollection,bookingAndReserving);
                adminDashboard.showOptions();
                desiredOption = read.nextInt();
                desiredOption=adminDashboard.executeOptions(desiredOption);
            } while (desiredOption != 0);

        }
    }
         private static void handleAdminResister(RolesAndPermissions r1){
        /*
         * If desiredOption is 2, then call the registration method to register a
         * user......
         */
        Scanner read=new Scanner(System.in);
        System.out.print("\nEnter the UserName to Register :    ");
        String username = read.nextLine();
        System.out.print("Enter the Password to Register :     ");
        String password = read.nextLine();
        while (r1.isPrivilegedUserOrNot(username, password) != -1) {
            System.out.print("ERROR!!! Admin with same UserName already exist. Enter new UserName:   ");
            username = read.nextLine();
            System.out.print("Enter the Password Again:   ");
            password = read.nextLine();
        }

        /* Setting the credentials entered by the user..... */
        adminUserNameAndPassword.put(username, password);

        /* Incrementing the numOfUsers */
        countNumOfUsers++;
    }

        // ************************************************************ Setters &
        // Getters ************************************************************

        public static List<Customer> getCustomersCollection(){
            return customersCollection;
        }
        private static void adminManual () {
            System.out.println(
                    "\n\n(1) Admin have the access to all users data...Admin can delete, update, add and can perform search for any customer...\n");
            System.out.println(
                    "(2) In order to access the admin module, you've to get yourself register by pressing 2, when the main menu gets displayed...\n");
            System.out.println(
                    "(3) Provide the required details i.e., name, email, id...Once you've registered yourself, press 1 to login as an admin... \n");
            System.out.println(
                    "(4) Once you've logged in, 2nd layer menu will be displayed on the screen...From here on, you can select from variety of options...\n");
            System.out.println(
                    "(5) Pressing \"1\" will add a new Passenger, provide the program with required details to add the passenger...\n");
            System.out.println(
                    "(6) Pressing \"2\" will search for any passenger, given the admin(you) provides the ID from the table printing above....  \n");
            System.out.println(
                    "(7) Pressing \"3\" will let you update any passengers data given the user ID provided to program...\n");
            System.out.println("(8) Pressing \"4\" will let you delete any passenger given its ID provided...\n");
            System.out.println("(9) Pressing \"5\" will let you display all registered passenger...\n");
            System.out.println(
                    "(10) Pressing \"6\" will let you display all registered passengers...After selecting, program will ask, if you want to display passengers for all flights(Y/y) or a specific flight(N/n)\n");
            System.out.println(
                    "(11) Pressing \"7\" will let you delete any flight given its flight number provided...\n");
            System.out.println(
                    "(11) Pressing \"0\" will make you logged out of the program...You can login again any time you want during the program execution....\n");

        }
        private static void userManual() {
            System.out.println("\n\n(1) Local user has the access to its data only...He/She won't be able to change/update other users data...\n");
            System.out.println("(2) In order to access local users benefits, you've to get yourself register by pressing 4, when the main menu gets displayed...\n");
            System.out.println("(3) Provide the details asked by the program to add you to the users list...Once you've registered yourself, press \"3\" to login as a passenger...\n");
            System.out.println("(4) Once you've logged in, 3rd layer menu will be displayed...From here on, you embarked on the journey to fly with us...\n");
            System.out.println("(5) Pressing \"1\" will display available/scheduled list of flights...To get yourself booked for a flight, enter the flight number and number of tickets for the flight...Max num of tickets at a time is 10 ...\n");
            System.out.println("(7) Pressing \"2\" will let you update your own data...You won't be able to update other's data... \n");
            System.out.println("(8) Pressing \"3\" will delete your account... \n");
            System.out.println("(9) Pressing \"4\" will display randomly designed flight schedule for this runtime...\n");
            System.out.println("(10) Pressing \"5\" will let you cancel any flight registered by you...\n");
            System.out.println("(11) Pressing \"6\" will display all flights registered by you...\n");
            System.out.println("(12) Pressing \"0\" will make you logout of the program...You can login back at anytime with your credentials...for this particular run-time... \n");
        }
    }
