/*
 * This file is generated by jOOQ.
 */
package com.hologramsciences.jooq.tables.records;


import com.hologramsciences.jooq.tables.MenuItems;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MenuItemsRecord extends UpdatableRecordImpl<MenuItemsRecord> implements Record3<Long, Long, String> {

    private static final long serialVersionUID = 1030017586;

    /**
     * Setter for <code>PUBLIC.MENU_ITEMS.ID</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>PUBLIC.MENU_ITEMS.ID</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>PUBLIC.MENU_ITEMS.RESTAURANT_ID</code>.
     */
    public void setRestaurantId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>PUBLIC.MENU_ITEMS.RESTAURANT_ID</code>.
     */
    public Long getRestaurantId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>PUBLIC.MENU_ITEMS.NAME</code>.
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>PUBLIC.MENU_ITEMS.NAME</code>.
     */
    public String getName() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Long, Long, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Long, Long, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return MenuItems.MENU_ITEMS.ID;
    }

    @Override
    public Field<Long> field2() {
        return MenuItems.MENU_ITEMS.RESTAURANT_ID;
    }

    @Override
    public Field<String> field3() {
        return MenuItems.MENU_ITEMS.NAME;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public Long component2() {
        return getRestaurantId();
    }

    @Override
    public String component3() {
        return getName();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public Long value2() {
        return getRestaurantId();
    }

    @Override
    public String value3() {
        return getName();
    }

    @Override
    public MenuItemsRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public MenuItemsRecord value2(Long value) {
        setRestaurantId(value);
        return this;
    }

    @Override
    public MenuItemsRecord value3(String value) {
        setName(value);
        return this;
    }

    @Override
    public MenuItemsRecord values(Long value1, Long value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached MenuItemsRecord
     */
    public MenuItemsRecord() {
        super(MenuItems.MENU_ITEMS);
    }

    /**
     * Create a detached, initialised MenuItemsRecord
     */
    public MenuItemsRecord(Long id, Long restaurantId, String name) {
        super(MenuItems.MENU_ITEMS);

        set(0, id);
        set(1, restaurantId);
        set(2, name);
    }
}
