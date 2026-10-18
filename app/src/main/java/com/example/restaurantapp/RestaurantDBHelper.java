package com.example.restaurantapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class RestaurantDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "restaurantes.db";

    public RestaurantDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RestContract.RestEntry.SQL_CREATE_REST_ENTRY);
        db.execSQL(RestContract.FoodEntry.SQL_CREATE_FOOD_ENTRY);

        seedInitialData(db);
    }

    private void seedInitialData(SQLiteDatabase db) {
        long id1 = insertRest(db, "Tacos Pepe");
        long id2 = insertRest(db, "Alitas | Wings Army");
        long id3 = insertRest(db, "Sushi Tachidito");

        // Tacos Pepe
        insertFood(db, "Tacos de Pastor", "$99.00", "Deliciosos tacos de pastor con piña y cilantro.", "comida", id1);
        insertFood(db, "Tacos de chorizo", "$99.00", "Tacos de chorizo bien doradito.", "comida", id1);
        insertFood(db, "Tacos de bistec", "$99.00", "Tacos de bistec jugoso a la plancha.", "comida", id1);

        insertFood(db, "Coca Cola 500ml", "$25.00", "Refresco de cola helado.", "bebida", id1);
        insertFood(db, "Agua de horchata", "$20.00", "Agua fresca de horchata natural.", "bebida", id1);
        insertFood(db, "Jarritos (diferentes sabores)", "$22.00", "Jarritos sabor mandarina, tamarindo y limón.", "bebida", id1);

        insertFood(db, "Cebolla guisada", "$15.00", "Cebollitas cambray asadas.", "complemento", id1);
        insertFood(db, "Pure de papa", "$30.00", "Cremoso puré de papa con mantequilla.", "complemento", id1);
        insertFood(db, "Guacamole", "$35.00", "Guacamole fresco con totopos.", "complemento", id1);

        // Alitas | Wings Army
        insertFood(db, "200gr de Alitas", "$120.00", "Alitas bañadas en tu salsa favorita.", "comida", id2);
        insertFood(db, "400gr de Alitas", "$220.00", "Porción grande de alitas crujientes.", "comida", id2);
        insertFood(db, "Costillas de 200gr", "$180.00", "Costillas BBQ horneadas lentamente.", "comida", id2);

        insertFood(db, "Cerveza Clara", "$45.00", "Cerveza bien fría.", "bebida", id2);
        insertFood(db, "Cubeta con 10 cervezas", "$350.00", "Cubeta con hielos y 10 cervezas.", "bebida", id2);
        insertFood(db, "Coca lata 355ml", "$25.00", "Refresco en lata.", "bebida", id2);

        insertFood(db, "Aros de cebolla", "$60.00", "Aros empanizados y crujientes.", "complemento", id2);
        insertFood(db, "Dedos de queso", "$75.00", "Queso mozzarella empanizado.", "complemento", id2);
        insertFood(db, "Boneless", "$110.00", "Trozos de pechuga empanizados.", "complemento", id2);

        // Sushi Tachidito
        insertFood(db, "Sushi de filadelfia", "$130.00", "Roll de salmón con queso crema.", "comida", id3);
        insertFood(db, "Yakimeshi", "$110.00", "Arroz frito estilo japonés.", "comida", id3);
        insertFood(db, "Sushi especial", "$150.00", "Roll especial empanizado con aguacate.", "comida", id3);

        insertFood(db, "Calpico", "$40.00", "Bebida japonesa refrescante.", "bebida", id3);
        insertFood(db, "Coca 355ml", "$25.00", "Refresco de cola.", "bebida", id3);
        insertFood(db, "Sake", "$120.00", "Bebida tradicional de arroz.", "bebida", id3);

        insertFood(db, "Takoyaki", "$85.00", "Bolas de masa con pulpo.", "complemento", id3);
        insertFood(db, "Bolas de arroz", "$50.00", "Onigiri relleno de atún o salmón.", "complemento", id3);
        insertFood(db, "Arroz frito", "$65.00", "Porción de arroz frito con verduras.", "complemento", id3);
    }

    private long insertRest(SQLiteDatabase db, String name) {
        ContentValues cv = new ContentValues();
        cv.put(RestContract.RestEntry.COLUMN_NAME, name);
        return db.insert(RestContract.RestEntry.TABLE_NAME, null, cv);
    }

    private long insertFood(SQLiteDatabase db, String name, String price, String desc, String type, long restId) {
        ContentValues cv = new ContentValues();
        cv.put(RestContract.FoodEntry.COLUMN_NAME, name);
        cv.put(RestContract.FoodEntry.COLUMN_PRICE, price);
        cv.put(RestContract.FoodEntry.COLUMN_DESCRIPTION, desc);
        cv.put(RestContract.FoodEntry.COLUMN_TYPE, type);
        cv.put(RestContract.FoodEntry.COLUMN_REST_ID, restId);
        return db.insert(RestContract.FoodEntry.TABLE_NAME, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RestContract.FoodEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RestContract.RestEntry.TABLE_NAME);
        onCreate(db);
    }

    public long addRestaurant(String name) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RestContract.RestEntry.COLUMN_NAME, name);
        return db.insert(RestContract.RestEntry.TABLE_NAME, null, cv);
    }

    public int updateRestaurant(long id, String newName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RestContract.RestEntry.COLUMN_NAME, newName);
        return db.update(RestContract.RestEntry.TABLE_NAME, cv, RestContract.RestEntry._ID + "=?", new String[]{String.valueOf(id)});
    }

    public int deleteRestaurant(long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(RestContract.FoodEntry.TABLE_NAME, RestContract.FoodEntry.COLUMN_REST_ID + "=?", new String[]{String.valueOf(id)});
        return db.delete(RestContract.RestEntry.TABLE_NAME, RestContract.RestEntry._ID + "=?", new String[]{String.valueOf(id)});
    }

    public Cursor getAllRestaurants() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(RestContract.RestEntry.TABLE_NAME, null, null, null, null, null, RestContract.RestEntry._ID + " ASC");
    }

    public long addFood(String name, String price, String desc, String type, long restId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RestContract.FoodEntry.COLUMN_NAME, name);
        cv.put(RestContract.FoodEntry.COLUMN_PRICE, price);
        cv.put(RestContract.FoodEntry.COLUMN_DESCRIPTION, desc);
        cv.put(RestContract.FoodEntry.COLUMN_TYPE, type);
        cv.put(RestContract.FoodEntry.COLUMN_REST_ID, restId);
        return db.insert(RestContract.FoodEntry.TABLE_NAME, null, cv);
    }

    public int updateFood(long id, String name, String price, String desc, String type) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RestContract.FoodEntry.COLUMN_NAME, name);
        cv.put(RestContract.FoodEntry.COLUMN_PRICE, price);
        cv.put(RestContract.FoodEntry.COLUMN_DESCRIPTION, desc);
        cv.put(RestContract.FoodEntry.COLUMN_TYPE, type);
        return db.update(RestContract.FoodEntry.TABLE_NAME, cv, RestContract.FoodEntry._ID + "=?", new String[]{String.valueOf(id)});
    }

    public int deleteFood(long id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(RestContract.FoodEntry.TABLE_NAME, RestContract.FoodEntry._ID + "=?", new String[]{String.valueOf(id)});
    }

    public Cursor getFoodsByRestAndType(long restId, String type) {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(RestContract.FoodEntry.TABLE_NAME, null,
                RestContract.FoodEntry.COLUMN_REST_ID + "=? AND " + RestContract.FoodEntry.COLUMN_TYPE + "=?",
                new String[]{String.valueOf(restId), type}, null, null, RestContract.FoodEntry._ID + " ASC");
    }

    public Cursor getFoodById(long id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(RestContract.FoodEntry.TABLE_NAME, null,
                RestContract.FoodEntry._ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);
    }
}
