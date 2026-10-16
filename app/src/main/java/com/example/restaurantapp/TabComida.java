package com.example.restaurantapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabComida extends Fragment {

    public TabComida() {
        // Required empty public constructor
    }

    public static TabComida newInstance(String param1, String param2) {
        TabComida fragment = new TabComida();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_comida, container, false);

        TextView textoComidaA = view.findViewById(R.id.textViewComidaA);
        TextView textoComidaB = view.findViewById(R.id.textViewComidaB);
        TextView textoComidaC = view.findViewById(R.id.textViewComidaC);

        if (textoComidaA != null) {
            textoComidaA.setOnClickListener(v -> abrirDetalle(R.layout.comida_a));
        }

        if (textoComidaB != null) {
            textoComidaB.setOnClickListener(v -> abrirDetalle(R.layout.comida_b));
        }

        if (textoComidaC != null) {
            textoComidaC.setOnClickListener(v -> abrirDetalle(R.layout.comida_c));
        }

        return view;
    }

    private void abrirDetalle(int layoutId) {
        Intent intent = new Intent(requireContext(), ComidaDetalle.class);
        intent.putExtra("LAYOUT_ID", layoutId);
        startActivity(intent);
    }
}
