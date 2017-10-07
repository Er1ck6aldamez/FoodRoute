package com.example.hd.foodroute_v1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Choche on 30/9/2017.
 */

public class SplashActivity extends AppCompatActivity{
    // 1 segundo tarda en abrir
    private final int DURACION_SPLASH = 1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Tenemos una plantilla llamada splash.xml donde mostraremos la información que queramos (logotipo, etc.)
        //setContentView(R.drawable.splash);

        new Handler().postDelayed(new Runnable(){
            public void run(){
                // Cuando pasen los 3 segundos, pasamos a la actividad principal de la aplicación
                Intent intent = new Intent(SplashActivity.this, Principal.class);
                startActivity(intent);
                finish();
            };
        }, DURACION_SPLASH);

    }
}
