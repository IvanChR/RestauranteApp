package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private LinearLayout listContainer;
    private RestaurantDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new RestaurantDBHelper(this);
        listContainer = findViewById(R.id.restaurant_list_container);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupSearch();

        ImageButton btnPlus = findViewById(R.id.btn_plus);
        if (btnPlus != null) {
            btnPlus.setOnClickListener(v -> {
                Intent intent = new Intent(this, AddRestaurantActivity.class);
                startActivity(intent);
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRestaurants();
    }

    private void loadRestaurants() {
        if (listContainer == null) return;
        listContainer.removeAllViews();

        Cursor cursor = dbHelper.getAllRestaurants();
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(RestContract.RestEntry._ID);
            int nameIndex = cursor.getColumnIndex(RestContract.RestEntry.COLUMN_NAME);

            do {
                final long id = cursor.getLong(idIndex);
                final String name = cursor.getString(nameIndex);

                TextView tv = new TextView(this);
                tv.setText(name);
                tv.setTextSize(24);
                tv.setPadding(24, 24, 24, 24);

                tv.setOnClickListener(v -> abrirRestaurante(id, name));

                tv.setOnLongClickListener(v -> {
                    mostrarMenuContextual(v, id, name);
                    return true;
                });

                listContainer.addView(tv);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    private void mostrarMenuContextual(View anchor, long id, String name) {
        PopupMenu popup = new PopupMenu(this, anchor);
        popup.getMenu().add("Añadir restaurante");
        popup.getMenu().add("Editar restaurante");
        popup.getMenu().add("Eliminar");

        popup.setOnMenuItemClickListener(item -> {
            String title = item.getTitle().toString();
            if ("Añadir restaurante".equals(title)) {
                Intent intent = new Intent(this, AddRestaurantActivity.class);
                startActivity(intent);
            } else if ("Editar restaurante".equals(title)) {
                Intent intent = new Intent(this, EditRestaurantActivity.class);
                intent.putExtra("REST_ID", id);
                intent.putExtra("REST_NAME", name);
                startActivity(intent);
            } else if ("Eliminar".equals(title)) {
                int count = dbHelper.deleteRestaurant(id);
                if (count > 0) {
                    Toast.makeText(this, "Restaurante eliminado con éxito", Toast.LENGTH_SHORT).show();
                    loadRestaurants();
                } else {
                    Toast.makeText(this, "Error al eliminar el restaurante", Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        });

        popup.show();
    }

    private void abrirRestaurante(long id, String name) {
        Intent intent;
        if (id == 2 || (name != null && name.toLowerCase().contains("alitas"))) {
            intent = new Intent(this, RestauranteB.class);
        } else if (id == 3 || (name != null && name.toLowerCase().contains("sushi"))) {
            intent = new Intent(this, RestauranteC.class);
        } else {
            intent = new Intent(this, RestauranteA.class);
        }
        intent.putExtra("REST_ID", id);
        intent.putExtra("REST_NAME", name);
        startActivity(intent);
    }

    private void setupSearch() {
        ImageButton btnSearch = findViewById(R.id.btn_search);
        EditText searchEditText = findViewById(R.id.search_edit_text);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);

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
                    filtrarLista("");
                }
            });

            searchEditText.addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    filtrarLista(s.toString());
                }
                @Override public void afterTextChanged(Editable s) {}
            });
        }
    }

    private void filtrarLista(String query) {
        if (listContainer == null) return;
        String q = query == null ? "" : query.toLowerCase().trim();
        for (int i = 0; i < listContainer.getChildCount(); i++) {
            View child = listContainer.getChildAt(i);
            if (child instanceof TextView) {
                TextView tv = (TextView) child;
                boolean match = tv.getText().toString().toLowerCase().contains(q);
                tv.setVisibility(match ? View.VISIBLE : View.GONE);
            }
        }
    }
}
