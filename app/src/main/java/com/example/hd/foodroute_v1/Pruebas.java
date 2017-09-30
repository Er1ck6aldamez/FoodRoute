package com.example.hd.foodroute_v1;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Pruebas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pruebas);

        //creacion de la fecha atras
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    //para terminar la activity (flecha atras)
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
