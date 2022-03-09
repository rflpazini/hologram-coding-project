package com.hologramsciences;


import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.BeforeClass;
import org.junit.Test;

import com.hologramsciences.jooq.tables.records.RestaurantsRecord;

import static junit.framework.TestCase.assertTrue;

public class JooqRestaurantServiceTest {

    private static final SQLRestaurantService sqlRestaurantService = new SQLRestaurantService();
    private static final JooqRestaurantService jooqRestaurantService = new JooqRestaurantService();

    @BeforeClass
    public static void initDB() throws Exception {
        sqlRestaurantService.initializeDatabase();
    }

    @Test
    public void getOpenLate() throws Exception {
        final List<RestaurantsRecord> restaurantRecords = jooqRestaurantService.getOpenRestaurants(
                DayOfWeek.SATURDAY, LocalTime.MIDNIGHT);
        final Set<String> names = restaurantRecords.stream().map(RestaurantsRecord::getName)
                .collect(Collectors.toSet());

        final Set<String> expectedNames = Arrays.asList(
                "The Cheesecake Factory", "Sudachi", "Thai Stick Restaurant", "Sabella & La Torre",
                "Marrakech Moroccan Restaurant", "Tim's Nighttime Banana Stand"
        ).stream().collect(Collectors.toSet());

        assertTrue(names.equals(expectedNames));
    }

    @Test
    public void getOpenReallyLate() throws Exception {
        final List<RestaurantsRecord> restaurantRecords = jooqRestaurantService.getOpenRestaurants(
                DayOfWeek.SATURDAY, LocalTime.of(2, 0));

        final Set<String> names = restaurantRecords.stream().map(RestaurantsRecord::getName)
                .collect(Collectors.toSet());
        final Set<String> expectedNames = Arrays.asList("Tim's Nighttime Banana Stand").stream()
                .collect(Collectors.toSet());
        assertTrue(names.equals(expectedNames));
    }

    @Test
    public void getRestaurantWithThreeOrMoreMenuItems() throws Exception {
        final List<RestaurantsRecord> restaurantRecords = jooqRestaurantService.getRestaurantsWithMenuOfSizeGreaterThanOrEqualTo(
                3);
        final Set<String> names = restaurantRecords.stream().map(RestaurantsRecord::getName)
                .collect(Collectors.toSet());
        final Set<String> expectedNames = Arrays.asList("The Cheesecake Factory",
                "Shanghai China Restaurant").stream().collect(Collectors.toSet());
        assertTrue(names.equals(expectedNames));
    }

}
