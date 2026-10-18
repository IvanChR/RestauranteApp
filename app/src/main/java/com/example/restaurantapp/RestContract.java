package com.example.restaurantapp;

import android.provider.BaseColumns;

public class RestContract {
    private RestContract() {
    }

    public static class RestEntry implements BaseColumns {
        public static final String TABLE_NAME = "restaurant";
        public static final String COLUMN_NAME = "nombre";

        public static final String SQL_CREATE_REST_ENTRY = "" +
                "CREATE TABLE " + RestEntry.TABLE_NAME +
                " (" + RestEntry._ID + " INTEGER PRIMARY KEY," +
                RestEntry.COLUMN_NAME + " TEXT)";
    }

    public static class FoodEntry implements BaseColumns {
        public static final String TABLE_NAME = "food";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_REST_ID = "rest_id";

        public static final String SQL_CREATE_FOOD_ENTRY = "" +
                "CREATE TABLE " + FoodEntry.TABLE_NAME + " (" +
                FoodEntry._ID + " INTEGER PRIMARY KEY," +
                FoodEntry.COLUMN_NAME + " TEXT," +
                FoodEntry.COLUMN_PRICE + " TEXT," +
                FoodEntry.COLUMN_TYPE + " TEXT," +
                FoodEntry.COLUMN_DESCRIPTION + " TEXT," +
                FoodEntry.COLUMN_REST_ID + " INTEGER," +
                "FOREIGN KEY(" + FoodEntry.COLUMN_REST_ID + ") REFERENCES " +
                RestEntry.TABLE_NAME + "(" + RestEntry._ID + "))";
    }

}
