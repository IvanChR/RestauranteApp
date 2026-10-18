package com.example.restaurantapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabComplementos extends Fragment implements SearchableFragment {

    private TextView tvA, tvB, tvC;

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

        tvA = view.findViewById(R.id.textViewComplementoA);
        tvB = view.findViewById(R.id.textViewComplementoB);
        tvC = view.findViewById(R.id.textViewComplementoC);

        int strA, strB, strC;

        if (getActivity() instanceof RestauranteB) {
            strA = R.string.restaurante_b_complemento_a;
            strB = R.string.restaurante_b_complemento_b;
            strC = R.string.restaurante_b_complemento_c;
        } else if (getActivity() instanceof RestauranteC) {
            strA = R.string.restaurante_c_complemento_a;
            strB = R.string.restaurante_c_complemento_b;
            strC = R.string.restaurante_d_complemento_c;
        } else {
            strA = R.string.restaurante_a_complemento_a;
            strB = R.string.restaurante_a_complemento_b;
            strC = R.string.restaurante_a_complemento_c;
        }

        if (tvA != null) {
            tvA.setText(getString(strA));
            tvA.setOnClickListener(v -> abrirDetalle("a", getString(strA)));
        }

        if (tvB != null) {
            tvB.setText(getString(strB));
            tvB.setOnClickListener(v -> abrirDetalle("b", getString(strB)));
        }

        if (tvC != null) {
            tvC.setText(getString(strC));
            tvC.setOnClickListener(v -> abrirDetalle("c", getString(strC)));
        }

        return view;

    }

    @Override
    public void filtrar(String query) {
        String q = query == null ? "" : query.toLowerCase().trim();
        if (tvA != null) tvA.setVisibility(tvA.getText().toString().toLowerCase().contains(q) ? View.VISIBLE : View.GONE);
        if (tvB != null) tvB.setVisibility(tvB.getText().toString().toLowerCase().contains(q) ? View.VISIBLE : View.GONE);
        if (tvC != null) tvC.setVisibility(tvC.getText().toString().toLowerCase().contains(q) ? View.VISIBLE : View.GONE);
    }

    private void abrirDetalle(String opcion, String titulo) {
        int layoutId;

        if (getActivity() instanceof RestauranteB) {
            switch (opcion) {
                case "b":
                    layoutId = R.layout.restaurante_b_comida_b;
                    break;
                case "c":
                    layoutId = R.layout.restaurante_b_comida_c;
                    break;
                default:
                    layoutId = R.layout.restaurante_b_comida_a;
                    break;
            }
        } else if (getActivity() instanceof RestauranteC) {
            switch (opcion) {
                case "b":
                    layoutId = R.layout.restaurante_c_comida_b;
                    break;
                case "c":
                    layoutId = R.layout.restaurante_c_comida_c;
                    break;
                default:
                    layoutId = R.layout.restaurante_c_comida_a;
                    break;
            }
        } else {
            switch (opcion) {
                case "b":
                    layoutId = R.layout.restaurante_a_comida_b;
                    break;
                case "c":
                    layoutId = R.layout.restaurante_a_comida_c;
                    break;
                default:
                    layoutId = R.layout.restaurante_a_comida_a;
                    break;
            }
        }

        Intent intent = new Intent(requireContext(), ComidaDetalle.class);
        intent.putExtra("LAYOUT_ID", layoutId);
        intent.putExtra("TITULO", titulo);
        startActivity(intent);
    }
}
