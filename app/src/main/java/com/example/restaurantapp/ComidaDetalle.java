package com.example.restaurantapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ComidaDetalle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int layoutId = getIntent().getIntExtra("LAYOUT_ID", R.layout.restaurante_a_comida_a);
        setContentView(layoutId);

        String titulo = getIntent().getStringExtra("TITULO");
        if (titulo != null) {
            TextView txtNombre = findViewById(R.id.nombreComida);
            if (txtNombre != null) {
                txtNombre.setText(titulo);
            }

            TextView txtToolbarTitle = findViewById(R.id.toolbar_title);
            if (txtToolbarTitle != null) {
                txtToolbarTitle.setText(titulo);
            }
        }

        View btnBack = findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> onBackPressed());
        }
    }
}
