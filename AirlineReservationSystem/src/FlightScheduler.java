import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FlightScheduler {

    private static int nextFlightDay = 0;
    private final List<Flight> flightList = new ArrayList<>();

    public List<Flight> generateFlightSchedule() {
        int numOfFlights = 15; // Number of flights to generate
        RandomGenerator r1 = new RandomGenerator();
        for (int i = 0; i < numOfFlights; i++) {
            String[][] chosenDestinations = r1.randomDestinations();
            String[] distanceBetweenTheCities = calculateDistance(
                    Double.parseDouble(chosenDestinations[0][1]),
                    Double.parseDouble(chosenDestinations[0][2]),
                    Double.parseDouble(chosenDestinations[1][1]),
                    Double.parseDouble(chosenDestinations[1][2])
            );
            String flightSchedule = createNewFlightsAndTime();
            String flightNumber = r1.randomFlightNumbGen(2, 1).toUpperCase();
            int numOfSeatsInTheFlight = r1.randomNumOfSeats();
            String gate = r1.randomFlightNumbGen(1, 30);
            flightList.add(new Flight(flightSchedule, flightNumber, numOfSeatsInTheFlight, chosenDestinations, distanceBetweenTheCities, gate.toUpperCase()));
        }
        return flightList;
    }

    public String createNewFlightsAndTime() {
        Calendar calendar = Calendar.getInstance();
        nextFlightDay += Math.random() * 7;
        calendar.add(Calendar.DATE, nextFlightDay);
        calendar.add(Calendar.HOUR, nextFlightDay);
        calendar.set(Calendar.MINUTE, ((calendar.get(Calendar.MINUTE) * 3) - (int) (Math.random() * 45)));
        Date dateObj = calendar.getTime();
        LocalDateTime date = Instant.ofEpochMilli(dateObj.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        date = getNearestHourQuarter(date);
        return date.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy, HH:mm a "));
    }

    public LocalDateTime getNearestHourQuarter(LocalDateTime datetime) {
        int minutes = datetime.getMinute();
        int mod = minutes % 15;
        if (mod < 8) {
            datetime = datetime.minusMinutes(mod);
        } else {
            datetime = datetime.plusMinutes(15 - mod);
        }
        return datetime.truncatedTo(ChronoUnit.MINUTES);
    }

    public String[] calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double distance = Math.sin(degreeToRadian(lat1)) * Math.sin(degreeToRadian(lat2)) + Math.cos(degreeToRadian(lat1)) * Math.cos(degreeToRadian(lat2)) * Math.cos(degreeToRadian(theta));
        distance = Math.acos(distance);
        distance = radianToDegree(distance);
        distance = distance * 60 * 1.1515;
        String[] distanceString = new String[3];
        distanceString[0] = String.format("%.2f", distance * 0.8684);
        distanceString[1] = String.format("%.2f", distance * 1.609344);
        distanceString[2] = Double.toString(Math.round(distance * 100.0) / 100.0);
        return distanceString;
    }

    private double degreeToRadian(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double radianToDegree(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}