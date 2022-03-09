package com.hologramsciences.sql;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class OpenHourRecord {

    private final long id;
    private final long restaurantId;

    private final DayOfWeek dayOfWeek;

    private final LocalTime startTime;
    private final LocalTime endTime;

    public OpenHourRecord(final long id, final long restaurantId, final DayOfWeek dayOfWeek, final LocalTime startTime, final LocalTime endTime) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getId() {
        return id;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "OpenHourRecord{" +
                "id=" + id +
                ", restaurantId=" + restaurantId +
                ", dayOfWeek=" + dayOfWeek +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
