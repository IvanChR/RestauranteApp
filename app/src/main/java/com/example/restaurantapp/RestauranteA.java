package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class RestauranteA extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    TabItem tabComida;
    TabItem tabBebida;
    TabItem tabComplementos;
    ViewPagerAdapter viewPagerAdapter;
    private long restId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurante_a);

        restId = getIntent().getLongExtra("REST_ID", 1);
        String name = getIntent().getStringExtra("REST_NAME");
        if (name != null) {
            TextView titleTv = findViewById(R.id.toolbar_title);
            if (titleTv != null) {
                titleTv.setText(name);
            }
        }

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

        ImageButton btnPlus = findViewById(R.id.btn_plus);
        if (btnPlus != null) {
            btnPlus.setOnClickListener(v -> {
                int pos = viewPager != null ? viewPager.getCurrentItem() : 0;
                String type;
                if (pos == 1) {
                    type = "bebida";
                } else if (pos == 2) {
                    type = "complemento";
                } else {
                    type = "comida";
                }

                Intent intent = new Intent(this, AddComidaActivity.class);
                intent.putExtra("REST_ID", restId);
                intent.putExtra("TYPE", type);
                startActivity(intent);
            });
        }

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
