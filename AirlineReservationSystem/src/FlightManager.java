import java.util.Iterator;
import java.util.List;

public class FlightManager {

    private final FlightScheduler flightScheduler;
    public List<Flight> flightList;

    public FlightManager() {
        this.flightScheduler = new FlightScheduler();
        this.flightList = flightScheduler.generateFlightSchedule();
    }

    public void deleteFlight(String flightNumber) {
        Iterator<Flight> iterator = flightList.iterator();
        while (iterator.hasNext()) {
            Flight flight = iterator.next();
            if (flight.getFlightNumber().equalsIgnoreCase(flightNumber)) {
                iterator.remove();
                break;
            }
        }
        displayFlightSchedule();
    }

    public void displayFlightSchedule() {
        System.out.println();
        System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+------------------------+\n");
        System.out.printf("| Num  | FLIGHT SCHEDULE\t\t\t   | FLIGHT NO | Available Seats  | \tFROM ====>>       | \t====>> TO\t   | \t    ARRIVAL TIME       | FLIGHT TIME |  GATE  |   DISTANCE(MILES/KMS)  |%n");
        System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+------------------------+\n");

        int i = 0;
        for (Flight flight : flightList) {
            i++;
            System.out.println(flight.toString(i));
            System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+------------------------+\n");
        }
    }
    public void displayMeasurementInstructions(){
        String symbols = "+---------------------------+";
        System.out.printf("\n\n %100s\n %100s", symbols, "| SOME IMPORTANT GUIDELINES |");
        System.out.printf("\n %100s\n", symbols);
        System.out.println("\n\t\t1. Distance between the destinations are based upon the Airports Coordinates(Latitudes && Longitudes) based in those cities\n");
        System.out.println("\t\t2. Actual Distance of the flight may vary from this approximation as Airlines may define their on Travel Policy that may restrict the planes to fly through specific regions...\n");
        System.out.println("\t\t3. Flight Time depends upon several factors such as Ground Speed(GS), AirCraft Design, Flight Altitude and Weather. Ground Speed for these calculations is 450 Knots...\n");
        System.out.println("\t\t4. Expect reaching your destination early or late from the Arrival Time. So, please keep a margin of ±1 hour...\n");
        System.out.println("\t\t5. The departure time is the moment that your plane pushes back from the gate, not the time it takes off. The arrival time is the moment that your plane pulls into the gate, not the time\n\t\t   it touches down on the runway...\n");
    }
}