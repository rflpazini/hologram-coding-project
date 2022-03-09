package com.hologramsciences;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class Restaurant {
    private final String name;
    private final Map<DayOfWeek, OpenHours> openHoursMap;

    public Restaurant(final String name, final Map<DayOfWeek, OpenHours> openHoursMap) {
        this.name = name;
        this.openHoursMap = openHoursMap;
    }

    public String getName() {
        return name;
    }

    public Map<DayOfWeek, OpenHours> getOpenHoursMap() {
        return openHoursMap;
    }

    public static class OpenHours {
        private final LocalTime startTime;
        private final LocalTime endTime;

        public OpenHours(final LocalTime startTime, final LocalTime endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public LocalTime getStartTime() {
            return startTime;
        }

        public LocalTime getEndTime() {
            return endTime;
        }

        public boolean spansMidnight() {
            if (startTime.equals(LocalTime.MIDNIGHT) || endTime.equals(LocalTime.MIDNIGHT)) {
                return false;
            } else {
                return ChronoUnit.HOURS.between(startTime, endTime) < 0;
            }
        }

        @Override
        public String toString() {
            return "OpenHours{" +
                    "startTime=" + startTime +
                    ", endTime=" + endTime +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", openHoursMap=" + openHoursMap +
                '}';
    }
}
