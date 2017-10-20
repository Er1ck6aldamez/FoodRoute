package com.example.hd.foodroute_v1;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Clases.AdaptadorSugerencias;
import Clases.Sugerencias;
import cz.msebera.android.httpclient.Header;

public class Resultados extends AppCompatActivity {

    public static String tipo_comida,presupuesto;
    public static int efectivo;
    public static String imgurl="https://foodroute.000webhostapp.com/img/";
    private ListView lstSugerencias;
    private ArrayList<Sugerencias> array;
    private AdaptadorSugerencias adaptador;
    private ProgressBar prgCircular;
    private String ruta,ruta2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        array=new ArrayList<>();
        adaptador=new AdaptadorSugerencias(Resultados.this,array);
        lstSugerencias = (ListView)findViewById(R.id.lstSugerencias);
        prgCircular = (ProgressBar)findViewById(R.id.prgCircular);
        ruta="https://foodroute.000webhostapp.com/proyecto/obtener_comidas.php?especialidad="+tipo_comida+" && presupuesto="+presupuesto;
        ruta2="https://foodroute.000webhostapp.com/proyecto/obtener_comidas_efectivo.php?especialidad="+tipo_comida+" && presupuesto="+presupuesto;

        CargarDatos();
        lstSugerencias.setAdapter(adaptador);

        //creacion de la fecha atras
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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
                                array.add(sugere);
                                adaptador.notifyDataSetChanged();
                            }
                        }else{
                            Sugerencias sugeren = new Sugerencias();
                            sugeren.setNombrePlatillo("Sin datos");
                            sugeren.setLugar("Sin datos");
                            sugeren.setPrecio("Sin datos");
                            sugeren.setImagen("Sin datos");
                            array.add(sugeren);
                            adaptador.notifyDataSetChanged();
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
                                    array.add(sugere);
                                    adaptador.notifyDataSetChanged();
                                }
                            }else{
                                Sugerencias sugeren = new Sugerencias();
                                sugeren.setNombrePlatillo("Sin datos");
                                sugeren.setLugar("Sin datos");
                                sugeren.setPrecio("Sin datos");
                                sugeren.setImagen("Sin datos");
                                array.add(sugeren);
                                adaptador.notifyDataSetChanged();
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

    //para terminar la activity (flecha atras)
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
