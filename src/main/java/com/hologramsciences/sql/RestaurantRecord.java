package com.hologramsciences.sql;

public class RestaurantRecord {

    private long id;
    private String name;

    public RestaurantRecord(final long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "RestaurantRecord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
