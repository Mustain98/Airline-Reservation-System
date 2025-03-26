package Utility;

public  class Message {
    public static void getAirlineLogo(){
        System.out.println("\n\t\t\t\t\t+++++++++++++ Welcome to BAV AirLines +++++++++++++\n\nTo Further Proceed, Please enter a value.");
    }
    public static void getDefaultAdmininfo(){
        System.out.println("\n***** Default Username && Password is root-root ***** Using Default Credentials will restrict you to just view the list of Passengers....\n");
    }
    public static void successfulCustomerLogin(String userName) {
        System.out.printf(
                "\n\n%-20sLogged in Successfully as \"%s\"..... For further Proceedings, enter a value from below....",
                "", userName);
    }
    public static void failedCustomerLogin() {
        System.out.printf(
                "\n%20sERROR!!! Unable to login Cannot find user with the entered credentials.... Try Creating New Credentials or get yourself register by pressing 4....\n",
                "");
    }
    public static void invalidOption() {
        System.out.print("ERROR!! Please enter value between 0 - 5. Enter the value again: ");
    }
}
