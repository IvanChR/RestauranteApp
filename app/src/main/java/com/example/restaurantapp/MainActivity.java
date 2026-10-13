package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

            getMenuInflater().inflate(R.menu.menu, menu);
            return true;
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
        Intent intent = new Intent(this, RestauranteA.class);
        startActivity(intent);
    }
    public void onGoToRestauranteD(View view){
        Intent intent = new Intent(this, RestauranteB.class);
        startActivity(intent);
    }


}