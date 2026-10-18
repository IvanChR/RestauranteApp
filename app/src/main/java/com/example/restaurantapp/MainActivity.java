package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Intent;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupSearch();
    }

    private void setupSearch() {
        ImageButton btnSearch = findViewById(R.id.btn_search);
        EditText searchEditText = findViewById(R.id.search_edit_text);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);

        TextView tvRestA = findViewById(R.id.restaurantea);
        TextView tvRestB = findViewById(R.id.restauranteb);
        TextView tvRestC = findViewById(R.id.restaurantec);

        if (btnSearch != null && searchEditText != null) {
            btnSearch.setOnClickListener(v -> {
                if (searchEditText.getVisibility() == View.GONE) {
                    if (toolbarTitle != null) toolbarTitle.setVisibility(View.GONE);
                    searchEditText.setVisibility(View.VISIBLE);
                    searchEditText.requestFocus();
                } else {
                    searchEditText.setText("");
                    searchEditText.setVisibility(View.GONE);
                    if (toolbarTitle != null) toolbarTitle.setVisibility(View.VISIBLE);
                    if (tvRestA != null) tvRestA.setVisibility(View.VISIBLE);
                    if (tvRestB != null) tvRestB.setVisibility(View.VISIBLE);
                    if (tvRestC != null) tvRestC.setVisibility(View.VISIBLE);
                }
            });

            searchEditText.addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String query = s.toString().toLowerCase().trim();
                    if (tvRestA != null) {
                        tvRestA.setVisibility(tvRestA.getText().toString().toLowerCase().contains(query) ? View.VISIBLE : View.GONE);
                    }
                    if (tvRestB != null) {
                        tvRestB.setVisibility(tvRestB.getText().toString().toLowerCase().contains(query) ? View.VISIBLE : View.GONE);
                    }
                    if (tvRestC != null) {
                        tvRestC.setVisibility(tvRestC.getText().toString().toLowerCase().contains(query) ? View.VISIBLE : View.GONE);
                    }
                }
                @Override public void afterTextChanged(Editable s) {}
            });
        }
    }

    public void onGoToRestauranteA(View view){
        Intent intent = new Intent(this, RestauranteA.class);
        startActivity(intent);
    }

    public void onGoToRestauranteB(View view){
        Intent intent = new Intent(this, RestauranteB.class);
        startActivity(intent);
    }

    public void onGoToRestauranteC(View view){
        Intent intent = new Intent(this, RestauranteC.class);
        startActivity(intent);
    }
}
