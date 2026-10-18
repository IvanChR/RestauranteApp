package com.example.restaurantapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditComidaActivity extends AppCompatActivity {

    private EditText nameInput, priceInput, descInput, typeInput;
    private Button btnUpdate, btnDelete;
    private RestaurantDBHelper dbHelper;
    private long foodId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_comida_view);

        dbHelper = new RestaurantDBHelper(this);

        nameInput = findViewById(R.id.addnewcomidanameinput);
        priceInput = findViewById(R.id.addnewcomidapriceinput);
        descInput = findViewById(R.id.addnewcomidadescripcioninput);
        typeInput = findViewById(R.id.addnewcomidatipoinput);

        btnUpdate = findViewById(R.id.updatecomidabutton);
        btnDelete = findViewById(R.id.deletecomidabutton);

        foodId = getIntent().getLongExtra("FOOD_ID", -1);
        String name = getIntent().getStringExtra("FOOD_NAME");
        String price = getIntent().getStringExtra("FOOD_PRICE");
        String desc = getIntent().getStringExtra("FOOD_DESC");
        String type = getIntent().getStringExtra("FOOD_TYPE");

        if (name != null) nameInput.setText(name);
        if (price != null) priceInput.setText(price);
        if (desc != null) descInput.setText(desc);
        if (type != null) typeInput.setText(type);

        btnUpdate.setOnClickListener(v -> {
            String newName = nameInput.getText().toString().trim();
            String newPrice = priceInput.getText().toString().trim();
            String newDesc = descInput.getText().toString().trim();
            String newType = typeInput.getText().toString().trim();

            if (newName.isEmpty() || newPrice.isEmpty()) {
                Toast.makeText(this, "Error: Nombre y precio no pueden estar vacíos", Toast.LENGTH_SHORT).show();
                return;
            }

            int rows = dbHelper.updateFood(foodId, newName, newPrice, newDesc, newType);
            if (rows > 0) {
                Toast.makeText(this, "Elemento actualizado con éxito", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar el elemento", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(v -> {
            int rows = dbHelper.deleteFood(foodId);
            if (rows > 0) {
                Toast.makeText(this, "Elemento eliminado con éxito", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Error al eliminar el elemento", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
