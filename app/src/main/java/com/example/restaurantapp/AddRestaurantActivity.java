package com.example.restaurantapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddRestaurantActivity extends AppCompatActivity {

    private EditText nameInput;
    private Button btnRegistrar;
    private RestaurantDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_restaurant_view);

        dbHelper = new RestaurantDBHelper(this);
        nameInput = findViewById(R.id.namenewrestaurantinput);
        btnRegistrar = findViewById(R.id.newrestaurantbutton);

        btnRegistrar.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Error: Por favor ingrese el nombre del restaurante", Toast.LENGTH_SHORT).show();
                return;
            }

            long result = dbHelper.addRestaurant(name);
            if (result > -1) {
                Toast.makeText(this, "Restaurante añadido con éxito", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Error al añadir el restaurante", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
