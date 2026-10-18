package com.example.restaurantapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ComidaDetalle extends AppCompatActivity {

    private RestaurantDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int layoutId = getIntent().getIntExtra("LAYOUT_ID", R.layout.restaurante_a_comida_a);
        setContentView(layoutId);

        dbHelper = new RestaurantDBHelper(this);

        View btnBack = findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> onBackPressed());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDetails();
    }

    private void loadDetails() {
        long foodId = getIntent().getLongExtra("FOOD_ID", -1);
        String fallbackTitle = getIntent().getStringExtra("TITULO");

        TextView txtNombre = findViewById(R.id.nombreComida);
        TextView txtPrecio = findViewById(R.id.precioComida);
        TextView txtDesc = findViewById(R.id.descripcionComida);
        TextView txtToolbarTitle = findViewById(R.id.toolbar_title);

        if (foodId > -1) {
            Cursor cursor = dbHelper.getFoodById(foodId);
            if (cursor != null && cursor.moveToFirst()) {
                int nameCol = cursor.getColumnIndex(RestContract.FoodEntry.COLUMN_NAME);
                int priceCol = cursor.getColumnIndex(RestContract.FoodEntry.COLUMN_PRICE);
                int descCol = cursor.getColumnIndex(RestContract.FoodEntry.COLUMN_DESCRIPTION);

                String name = cursor.getString(nameCol);
                String price = cursor.getString(priceCol);
                String desc = cursor.getString(descCol);

                if (txtNombre != null && name != null) txtNombre.setText(name);
                if (txtPrecio != null && price != null) txtPrecio.setText(price);
                if (txtDesc != null && desc != null && !desc.isEmpty()) txtDesc.setText(desc);
                if (txtToolbarTitle != null && name != null) txtToolbarTitle.setText(name);

                cursor.close();
                return;
            }
        }

        if (fallbackTitle != null) {
            if (txtNombre != null) txtNombre.setText(fallbackTitle);
            if (txtToolbarTitle != null) txtToolbarTitle.setText(fallbackTitle);
        }
    }
}
