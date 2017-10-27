package com.example.hd.foodroute_v1;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;

public class DetalleRestaurante extends AppCompatActivity {

    private Button btnGeo, btnTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_restaurante);
        //flecha atras
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        this.btnGeo = (Button) findViewById(R.id.btnGeolocalizacion);
        this.btnTel = (Button) findViewById(R.id.btnTelfono);

        btnGeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapa = new Intent(DetalleRestaurante.this, MapaGeolocalizacion.class);
                startActivity(mapa);
            }
        });

        btnTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent numero = new Intent(Intent.ACTION_DIAL);
                numero.setData(Uri.parse("tel:77432015"));
                startActivity(numero);
             //   TelephonyManager tMgr = (TelephonyManager) mAppContext.getSystemService(Context.TELEPHONY_SERVICE);
               // String mPhoneNumber = tMgr.getLine1Number();
            }
        });





    }


    //flecha atras
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
