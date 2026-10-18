package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class RestauranteC extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    TabItem tabComida;
    TabItem tabBebida;
    TabItem tabComplementos;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurante_c);

        View btnBack = findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> onBackPressed());
        }

        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewPager);

        tabComida = findViewById(R.id.tabComida);
        tabBebida = findViewById(R.id.tabBebidas);
        tabComplementos = findViewById(R.id.tabComplementos);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        setupSearch();
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
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof SearchableFragment) {
                ((SearchableFragment) fragment).filtrar(query);
            }
        }
    }
}
