package com.example.restaurantapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditRestaurantActivity extends AppCompatActivity {

    private EditText nameInput;
    private Button btnUpdate, btnDelete;
    private RestaurantDBHelper dbHelper;
    private long restId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_restaurant_view);

        dbHelper = new RestaurantDBHelper(this);
        nameInput = findViewById(R.id.editrestaurantinput);
        btnUpdate = findViewById(R.id.udpaterestaurantbutton);
        btnDelete = findViewById(R.id.deleterestaurantbutton);

        restId = getIntent().getLongExtra("REST_ID", -1);
        String currentName = getIntent().getStringExtra("REST_NAME");

        if (currentName != null) {
            nameInput.setText(currentName);
        }

        btnUpdate.setOnClickListener(v -> {
            String newName = nameInput.getText().toString().trim();
            if (newName.isEmpty()) {
                Toast.makeText(this, "Error: El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
                return;
            }

            int rows = dbHelper.updateRestaurant(restId, newName);
            if (rows > 0) {
                Toast.makeText(this, "Restaurante actualizado con éxito", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar el restaurante", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(v -> {
            int rows = dbHelper.deleteRestaurant(restId);
            if (rows > 0) {
                Toast.makeText(this, "Restaurante eliminado con éxito", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Error al eliminar el restaurante", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
