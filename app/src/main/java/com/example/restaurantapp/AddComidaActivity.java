package com.example.restaurantapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddComidaActivity extends AppCompatActivity {

    private EditText nameInput, priceInput, descInput, typeInput;
    private Button btnRegistrar;
    private RestaurantDBHelper dbHelper;
    private long restId = 1;
    private String defaultType = "comida";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_comida_view);

        dbHelper = new RestaurantDBHelper(this);

        nameInput = findViewById(R.id.addnewcomidanameinput);
        priceInput = findViewById(R.id.addnewcomidapriceinput);
        descInput = findViewById(R.id.addnewcomidadescripcioninput);
        typeInput = findViewById(R.id.addnewcomidatipoinput);
        btnRegistrar = findViewById(R.id.newrestaurantbutton);

        restId = getIntent().getLongExtra("REST_ID", 1);
        if (getIntent().hasExtra("TYPE")) {
            defaultType = getIntent().getStringExtra("TYPE");
        }

        typeInput.setText(defaultType);

        btnRegistrar.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            String price = priceInput.getText().toString().trim();
            String desc = descInput.getText().toString().trim();
            String type = typeInput.getText().toString().trim();

            if (name.isEmpty() || price.isEmpty()) {
                Toast.makeText(this, "Error: Por favor complete nombre y precio", Toast.LENGTH_SHORT).show();
                return;
            }

            long result = dbHelper.addFood(name, price, desc, type, restId);
            if (result > -1) {
                Toast.makeText(this, "Elemento registrado con éxito", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Error al registrar el elemento", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
