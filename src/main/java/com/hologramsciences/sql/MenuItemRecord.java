package com.hologramsciences.sql;

public class MenuItemRecord {
    private final long id;
    private final long restaurantId;
    private final String name;

    public MenuItemRecord(final long id, final long restaurantId, final String name) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "MenuItemRecord{" +
                "id=" + id +
                ", restaurantId=" + restaurantId +
                ", name='" + name + '\'' +
                '}';
    }
}
