package com.example.hd.foodroute_v1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.BottomNavigationView;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Parser;

import java.io.Serializable;
import java.util.ArrayList;

import Clases.AdaptadorSugerencias;
import Clases.MyAdapter;
import Clases.Sugerencias;
import cz.msebera.android.httpclient.Header;

public class Resultados extends AppCompatActivity {

    public static String tipo_comida,presupuesto;
    public static int efectivo;
    public static String imgurl="https://foodroute.000webhostapp.com/img/";
    private RecyclerView lstSugerencias;
    private ArrayList<Sugerencias> array;
    private MyAdapter adaptador;
    private ProgressBar prgCircular;
    private String ruta,ruta2;

    private TextView lblActualizado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        lblActualizado=(TextView)findViewById(R.id.lblPrecio);
        lstSugerencias = (RecyclerView)findViewById(R.id.lstSugerencias);
        prgCircular = (ProgressBar)findViewById(R.id.prgCircular);
        lstSugerencias.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new GridLayoutManager(this,getResources().getInteger(R.integer.colInicio));
        lstSugerencias.setLayoutManager(layoutManager);
        ruta="https://foodroute.000webhostapp.com/proyecto/obtener_comidas.php?especialidad="+tipo_comida+" && presupuesto="+presupuesto;
        ruta2="https://foodroute.000webhostapp.com/proyecto/obtener_comidas_efectivo.php?especialidad="+tipo_comida+" && presupuesto="+presupuesto;

        if(savedInstanceState==null){
            array=new ArrayList<>();
            adaptador=new MyAdapter(array);
            CargarDatos();
        }else {
            array = (ArrayList<Sugerencias>) savedInstanceState.getSerializable("listResultados");
            adaptador=new MyAdapter(array);
            prgCircular.setVisibility(View.INVISIBLE);
            lblActualizado.setVisibility(View.VISIBLE);
            lstSugerencias.setVisibility(View.VISIBLE);
        }

        lstSugerencias.setAdapter(adaptador);

        //creacion de la fecha atras
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    Intent home = new Intent();
                    home.putExtra("home",true);
                    setResult(Activity.RESULT_OK,home);
                    finish();

                    return true;
                case R.id.navigation_dashboard:

                    Intent espe = new Intent();
                    espe.putExtra("especialidad",true);
                    setResult(Activity.RESULT_OK,espe);
                    finish();

                    return true;
                case R.id.navigation_notifications:

                    Intent hist = new Intent();
                    hist.putExtra("historial",true);
                    setResult(Activity.RESULT_OK,hist);
                    finish();

                    return true;
            }
            return false;
        }
    };






    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("listResultados", array);
        super.onSaveInstanceState(outState);
    }

    private void CargarDatos() {
        AsyncHttpClient client=new AsyncHttpClient();
        if(efectivo==0){
        client.get(ruta, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    prgCircular.setVisibility(View.INVISIBLE);
                    lstSugerencias.setVisibility(View.VISIBLE);
                    lblActualizado.setVisibility(View.VISIBLE);
                    try {
                        JSONObject json=new JSONObject(new String(responseBody));
                        String estado=json.getString("estado");
                        if(estado.equals("1")) {
                            JSONArray jsonArray=json.getJSONArray("comidas");
                            //sug=new Sugerencias[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Sugerencias sugere = new Sugerencias();
                                sugere.setNombrePlatillo(jsonArray.getJSONObject(i).getString("NombrePlatillo"));
                                sugere.setLugar(jsonArray.getJSONObject(i).getString("Nombre"));
                                sugere.setPrecio(jsonArray.getJSONObject(i).getString("PrecioTotal"));
                                sugere.setImagen(jsonArray.getJSONObject(i).getString("Foto"));
                                sugere.setLatitud(Double.parseDouble(jsonArray.getJSONObject(i).getString("latitud")));
                                sugere.setLongitud(Double.parseDouble(jsonArray.getJSONObject(i).getString("longitud")));
                                array.add(sugere);
                                adaptador.notifyDataSetChanged();
                            }
                        }else{
                            Dialog();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(Resultados.this,"Error",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Toast.makeText(getActivity().getApplicationContext(),"Error al cargar lista",Toast.LENGTH_SHORT).show();
            }
        });
        }else{
            client.get(ruta2, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if(statusCode==200){
                        prgCircular.setVisibility(View.INVISIBLE);
                        lstSugerencias.setVisibility(View.VISIBLE);
                        lblActualizado.setVisibility(View.VISIBLE);
                        try {
                            JSONObject json=new JSONObject(new String(responseBody));
                            String estado=json.getString("estado");
                            if(estado.equals("1")) {
                                JSONArray jsonArray=json.getJSONArray("comidas");
                                //sug=new Sugerencias[jsonArray.length()];
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Sugerencias sugere = new Sugerencias();
                                    sugere.setNombrePlatillo(jsonArray.getJSONObject(i).getString("NombrePlatillo"));
                                    sugere.setLugar(jsonArray.getJSONObject(i).getString("Nombre"));
                                    sugere.setPrecio(jsonArray.getJSONObject(i).getString("PrecioTotal"));
                                    sugere.setImagen(jsonArray.getJSONObject(i).getString("Foto"));
                                    sugere.setLatitud(Double.parseDouble(jsonArray.getJSONObject(i).getString("latitud")));
                                    sugere.setLongitud(Double.parseDouble(jsonArray.getJSONObject(i).getString("longitud")));
                                    array.add(sugere);
                                    adaptador.notifyDataSetChanged();
                                }
                            }else{
                                Dialog();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(Resultados.this,"Error",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    //Toast.makeText(getActivity().getApplicationContext(),"Error al cargar lista",Toast.LENGTH_SHORT).show();

                }
            });
        }
    }




    public void Dialog() {
        AlertDialog.Builder Alerta = new AlertDialog.Builder(this);
        Alerta.setTitle("Sin Resultados").
                setMessage("Realizar nueva Busqueda").
                setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       finish();
                    }
                }).
                setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Intent res = new Intent(Resultados.this, Principal.class);
                        startActivity(res);
                    }
                });

        AlertDialog alertDialog = Alerta.create();
        alertDialog.show();
    }

    //para terminar la activity (flecha atras)
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
