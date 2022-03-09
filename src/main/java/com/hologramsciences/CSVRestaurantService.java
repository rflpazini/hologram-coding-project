package com.hologramsciences;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.*;

import com.hologramsciences.Restaurant.OpenHours;
import org.apache.commons.csv.CSVRecord;

import io.atlassian.fugue.Option;

import static java.util.stream.Collectors.*;

public class CSVRestaurantService {

    private final List<Restaurant> restaurantList;

    /**
     * TODO: Implement Me // DONE
     * <p>
     * From the CSVRecord which represents a single line from src/main/resources/rest_hours.csv
     * Write a parser to read the line and create an instance of the Restaurant class (Optionally, using the Option class)
     * <p>
     * Example Line:
     * <p>
     * "Burger Bar","Mon,Tue,Wed,Thu,Sun|11:00-22:00;Fri,Sat|11:00-0:00"
     * <p>
     * '|'   separates the list of applicable days from the hours span
     * ';'   separates groups of (list of applicable days, hours span)
     * <p>
     * So the above line would be parsed as:
     * <p>
     * Map<DayOfWeek, OpenHours> m = new HashMap<>();
     * m.put(MONDAY,    new OpenHours(LocalTime.of(11, 0), LocalTime.of(22, 0)));
     * m.put(TUESDAY,   new OpenHours(LocalTime.of(11, 0), LocalTime.of(22, 0)));
     * m.put(WEDNESDAY, new OpenHours(LocalTime.of(11, 0), LocalTime.of(22, 0)));
     * m.put(THURSDAY,  new OpenHours(LocalTime.of(11, 0), LocalTime.of(22, 0)));
     * m.put(SUNDAY,    new OpenHours(LocalTime.of(11, 0), LocalTime.of(22, 0)));
     * <p>
     * m.put(FRIDAY,    new OpenHours(LocalTime.of(11, 0), LocalTime.of(0, 0)));
     * m.put(SATURDAY,  new OpenHours(LocalTime.of(11, 0), LocalTime.of(0, 0)));
     * <p>
     * Option.some(new Restaurant("Burger Bar", m))
     * <p>
     * This method returns Option.some(parsedRestaurant),
     * IF the String name, and Map<DayOfWeek, OpenHours> openHours is found in the CSV,
     * - assume if both columns are in the CSV then they are both parsable.
     * AND if all values in openHours have !startTime.equals(endTime)
     * <p>
     * This method returns Option.none() when any of the OpenHours for a given restaurant have the same startTime and endDate
     * <p>
     * <p>
     * NOTE, the getDayOfWeek method should be helpful, and the LocalTime should be parsable by LocalDate.parse
     */
    public static Option<Restaurant> parse(final CSVRecord r) {
        if (r.size() != 2) {
            return Option.none();
        }

        Restaurant restaurant = new Restaurant(r.get(0), parseOpenHour(r.get(1)));
        boolean hasGoodTime = restaurant.getOpenHoursMap().values().stream()
                .noneMatch(openHours -> openHours.getStartTime().equals(openHours.getEndTime()));

        if (!hasGoodTime) {
            return Option.none();
        }

        return Option.some(restaurant);
    }

    /**
     * TODO: Implement me, This is a useful helper method // DONE
     */
    public static Map<DayOfWeek, OpenHours> parseOpenHour(final String openHours) {
        Map<String[], List<String>> daysWithCorrespondingHours = Arrays.stream(
                openHours.split("\\;")).map(s -> s.split("\\|")).collect(
                groupingBy(daysOfWeek -> daysOfWeek[0].split(","),
                        mapping(hours -> hours.length > 1 ? hours[1] : "", toList())));

        return populateDaysWithOpenHours(daysWithCorrespondingHours);
    }

    public CSVRestaurantService() throws IOException {
        this.restaurantList = ResourceLoader.parseOptionCSV("rest_hours.csv",
                CSVRestaurantService::parse);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantList;
    }

    /**
     * TODO: Implement me // DONE
     * <p>
     * A restaurant is considered open when the OpenHours for the dayOfWeek has:
     * <p>
     * startTime < localTime   && localTime < endTime
     * <p>
     * If the open hours are 16:00-20:00  Then
     * <p>
     * 15:59 open = false
     * 16:00 open = false
     * 16:01 open = true
     * 20:00 open = false
     * <p>
     * <p>
     * If the startTime endTime spans midnight, then consider an endTime up until 5:00 to be part of same DayOfWeek as the startTime
     * <p>
     * SATURDAY, OpenHours are: 20:00-04:00    SUNDAY, OpenHours are: 10:00-14:00
     * <p>
     * (SATURDAY, 03:00) => open = false
     * (SUNDAY, 03:00)   => open = true
     * (SUNDAY, 05:00)   => open = false
     */
    public List<Restaurant> getOpenRestaurants(final DayOfWeek dayOfWeek,
            final LocalTime localTime) {
        List<Restaurant> openRestaurants = new ArrayList<>();
        for (Restaurant restaurant : getAllRestaurants()) {
            restaurant.getOpenHoursMap().forEach((key, value) -> {
                if (!value.spansMidnight()) {
                    return;
                }

                if (validateNormalTime(value, localTime)) {
                    if (key.equals(dayOfWeek.minus(1))) {
                        openRestaurants.add(restaurant);
                    }
                } else if (key.equals(dayOfWeek)) {
                    openRestaurants.add(restaurant);
                }
            });
        }

        return openRestaurants;
    }

    public List<Restaurant> getOpenRestaurantsForLocalDateTime(final LocalDateTime localDateTime) {
        return getOpenRestaurants(localDateTime.getDayOfWeek(), localDateTime.toLocalTime());
    }

    public static Option<DayOfWeek> getDayOfWeek(final String s) {
        switch (s) {
            case "Mon":
                return Option.some(DayOfWeek.MONDAY);
            case "Tue":
                return Option.some(DayOfWeek.TUESDAY);
            case "Wed":
                return Option.some(DayOfWeek.WEDNESDAY);
            case "Thu":
                return Option.some(DayOfWeek.THURSDAY);
            case "Fri":
                return Option.some(DayOfWeek.FRIDAY);
            case "Sat":
                return Option.some(DayOfWeek.SATURDAY);
            case "Sun":
                return Option.some(DayOfWeek.SUNDAY);
            default:
                return Option.none();
        }
    }

    /**
     * NOTE: Useful for generating the data.sql file in src/main/resources/
     */
    public static void main(final String[] args) throws IOException {
        final CSVRestaurantService csvRestaurantService = new CSVRestaurantService();

        csvRestaurantService.getAllRestaurants().forEach(restaurant -> {

            final String name = restaurant.getName().replaceAll("'", "''");

            System.out.println("INSERT INTO restaurants (name) values ('" + name + "');");

            restaurant.getOpenHoursMap().entrySet().forEach(entry -> {
                final DayOfWeek dayOfWeek = entry.getKey();
                final LocalTime startTime = entry.getValue().getStartTime();
                final LocalTime endTime = entry.getValue().getEndTime();

                System.out.println(
                        "INSERT INTO open_hours (restaurant_id, day_of_week, start_time_minute_of_day, end_time_minute_of_day) select id, '"
                                + dayOfWeek.toString() + "', " + startTime.get(
                                ChronoField.MINUTE_OF_DAY) + ", " + endTime.get(
                                ChronoField.MINUTE_OF_DAY) + " from restaurants where name = '"
                                + name + "';");

            });
        });
    }

    private static Map<DayOfWeek, OpenHours> populateDaysWithOpenHours(
            Map<String[], List<String>> daysWithCorrespondingHours) {
        Map<DayOfWeek, OpenHours> weekOpenHours = new HashMap<>();
        daysWithCorrespondingHours.forEach(
                (days, hours) -> Arrays.stream(days).map(CSVRestaurantService::getDayOfWeek)
                        .forEach(dayOfWeek -> {
                            List<LocalTime> openingHours = splitHourAndMinute(hours);
                            weekOpenHours.put(dayOfWeek.get(),
                                    new OpenHours(openingHours.get(0), openingHours.get(1)));
                        }));

        return weekOpenHours;
    }

    private static List<LocalTime> splitHourAndMinute(List<String> hour) {
        String startEndHour = hour.get(0);

        String[] splitHours = startEndHour.split("-");
        List<LocalTime> openingHours = new ArrayList<>();
        for (String hours : splitHours) {
            openingHours.add(LocalTime.parse(hours));
        }

        return openingHours;
    }

    private boolean validateNormalTime(OpenHours openHours, LocalTime localTime) {
        LocalTime startTime = openHours.getStartTime();
        LocalTime endTime = openHours.getEndTime();

        return startTime.compareTo(localTime) >= 0 && localTime.compareTo(endTime) < 0;
    }
}
