package com.example.hd.foodroute_v1;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Buscar extends AppCompatActivity {

    private Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent res = new Intent(Buscar.this, Resultados.class);
                startActivity(res);
            }
        });



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

