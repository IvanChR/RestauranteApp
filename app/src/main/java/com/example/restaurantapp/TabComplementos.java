package com.example.restaurantapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class TabComplementos extends Fragment implements SearchableFragment {

    private LinearLayout listContainer;
    private RestaurantDBHelper dbHelper;
    private long restId = 1;

    public TabComplementos() {
        // Required empty public constructor
    }

    public static TabComplementos newInstance(String param1, String param2) {
        return new TabComplementos();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_complementos, container, false);
        listContainer = view.findViewById(R.id.tab_list_container);
        dbHelper = new RestaurantDBHelper(requireContext());

        if (getActivity() != null && getActivity().getIntent() != null) {
            restId = getActivity().getIntent().getLongExtra("REST_ID", 1);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadItems();
    }

    private void loadItems() {
        if (listContainer == null || dbHelper == null) return;
        listContainer.removeAllViews();

        Cursor cursor = dbHelper.getFoodsByRestAndType(restId, "complemento");
        if (cursor != null && cursor.moveToFirst()) {
            int idCol = cursor.getColumnIndex(RestContract.FoodEntry._ID);
            int nameCol = cursor.getColumnIndex(RestContract.FoodEntry.COLUMN_NAME);
            int priceCol = cursor.getColumnIndex(RestContract.FoodEntry.COLUMN_PRICE);
            int descCol = cursor.getColumnIndex(RestContract.FoodEntry.COLUMN_DESCRIPTION);
            int typeCol = cursor.getColumnIndex(RestContract.FoodEntry.COLUMN_TYPE);

            do {
                final long foodId = cursor.getLong(idCol);
                final String name = cursor.getString(nameCol);
                final String price = cursor.getString(priceCol);
                final String desc = cursor.getString(descCol);
                final String type = cursor.getString(typeCol);

                TextView tv = new TextView(requireContext());
                tv.setText(name);
                tv.setTextSize(24);
                tv.setPadding(24, 24, 24, 24);

                tv.setOnClickListener(v -> abrirDetalle(foodId, name));

                tv.setOnLongClickListener(v -> {
                    mostrarMenuContextual(v, foodId, name, price, desc, type);
                    return true;
                });

                listContainer.addView(tv);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    private void mostrarMenuContextual(View anchor, long foodId, String name, String price, String desc, String type) {
        PopupMenu popup = new PopupMenu(requireContext(), anchor);
        popup.getMenu().add("Añadir complemento");
        popup.getMenu().add("Editar complemento");
        popup.getMenu().add("Eliminar");

        popup.setOnMenuItemClickListener(item -> {
            String title = item.getTitle() != null ? item.getTitle().toString() : "";
            if ("Añadir complemento".equals(title)) {
                Intent intent = new Intent(requireContext(), AddComidaActivity.class);
                intent.putExtra("REST_ID", restId);
                intent.putExtra("TYPE", "complemento");
                startActivity(intent);
            } else if ("Editar complemento".equals(title)) {
                Intent intent = new Intent(requireContext(), EditComidaActivity.class);
                intent.putExtra("FOOD_ID", foodId);
                intent.putExtra("FOOD_NAME", name);
                intent.putExtra("FOOD_PRICE", price);
                intent.putExtra("FOOD_DESC", desc);
                intent.putExtra("FOOD_TYPE", type);
                startActivity(intent);
            } else if ("Eliminar".equals(title)) {
                int count = dbHelper.deleteFood(foodId);
                if (count > 0) {
                    Toast.makeText(requireContext(), "Complemento eliminado con éxito", Toast.LENGTH_SHORT).show();
                    loadItems();
                } else {
                    Toast.makeText(requireContext(), "Error al eliminar complemento", Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        });

        popup.show();
    }

    @Override
    public void filtrar(String query) {
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

    private void abrirDetalle(long foodId, String titulo) {
        int layoutId;
        if (getActivity() instanceof RestauranteB) {
            layoutId = R.layout.restaurante_b_comida_a;
        } else if (getActivity() instanceof RestauranteC) {
            layoutId = R.layout.restaurante_c_comida_a;
        } else {
            layoutId = R.layout.restaurante_a_comida_a;
        }

        Intent intent = new Intent(requireContext(), ComidaDetalle.class);
        intent.putExtra("LAYOUT_ID", layoutId);
        intent.putExtra("FOOD_ID", foodId);
        intent.putExtra("TITULO", titulo);
        startActivity(intent);
    }
}
