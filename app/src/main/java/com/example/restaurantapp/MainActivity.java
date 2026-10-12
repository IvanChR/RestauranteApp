package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);

        String items[] = new String[4];

        items[0] = "Restaurante A";
        items[1] = "Restaurante B";
        items[2] = "Restaurante C";
        items[3] = "Restaurante D";

  /*      for(int i = 0; i<items.length; i++){
            items[i] = "item " + (i + 1);
        }
*/
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);

        listView.setAdapter(adapter);

    }

    public void onGoToRestauranteA(View view){
        Intent intent = new Intent(this, RestauranteA.class);
        startActivity(intent);
    }


}