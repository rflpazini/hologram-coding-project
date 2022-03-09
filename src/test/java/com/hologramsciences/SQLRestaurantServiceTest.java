package com.hologramsciences;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.BeforeClass;
import org.junit.Test;

import com.hologramsciences.sql.RestaurantRecord;

import static junit.framework.TestCase.assertTrue;

public class SQLRestaurantServiceTest {

    private static SQLRestaurantService sqlRestaurantService = new SQLRestaurantService();

    @BeforeClass
    public static void initDB() throws Exception {
        sqlRestaurantService.initializeDatabase();
    }

    @Test
    public void getAllRestaurantsIsNotEmpty() throws Exception {
        final List<RestaurantRecord> restaurantRecordList = sqlRestaurantService.getAllRestaurantRecordsWithIds(
                Arrays.asList(28L, 29L));
        assertTrue(restaurantRecordList.size() > 0);
    }

    @Test
    public void getOpenLate() throws Exception {
        final List<RestaurantRecord> restaurantRecords = sqlRestaurantService.getOpenRestaurants(
                DayOfWeek.SATURDAY, LocalTime.MIDNIGHT);
        final Set<String> names = restaurantRecords.stream().map(RestaurantRecord::getName)
                .collect(Collectors.toSet());

        final Set<String> expectedNames = Arrays.asList(
                "The Cheesecake Factory", "Sudachi", "Thai Stick Restaurant", "Sabella & La Torre",
                "Marrakech Moroccan Restaurant", "Tim's Nighttime Banana Stand"
        ).stream().collect(Collectors.toSet());

        assertTrue(names.equals(expectedNames));
    }

    @Test
    public void getOpenReallyLate() throws Exception {
        final List<RestaurantRecord> restaurantRecords = sqlRestaurantService.getOpenRestaurants(
                DayOfWeek.SATURDAY, LocalTime.of(2, 0));

        final Set<String> names = restaurantRecords.stream().map(RestaurantRecord::getName)
                .collect(Collectors.toSet());
        final Set<String> expectedNames = Arrays.asList("Tim's Nighttime Banana Stand").stream()
                .collect(Collectors.toSet());
        assertTrue(names.equals(expectedNames));
    }

    @Test
    public void getRestaurantWithThreeOrMoreMenuItems() throws Exception {
        final List<RestaurantRecord> restaurantRecords = sqlRestaurantService.getRestaurantsWithMenuOfSizeGreaterThanOrEqualTo(
                3);
        final Set<String> names = restaurantRecords.stream().map(RestaurantRecord::getName)
                .collect(Collectors.toSet());
        final Set<String> expectedNames = Arrays.asList("The Cheesecake Factory",
                "Shanghai China Restaurant").stream().collect(Collectors.toSet());
        assertTrue(names.equals(expectedNames));
    }
}
