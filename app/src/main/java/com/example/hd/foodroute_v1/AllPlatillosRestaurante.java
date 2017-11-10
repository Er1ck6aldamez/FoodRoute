package com.example.hd.foodroute_v1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Clases.MyAdapter;
import Clases.Sugerencias;
import cz.msebera.android.httpclient.Header;

public class AllPlatillosRestaurante extends AppCompatActivity {

    //Declaracion de variables
    private RecyclerView lstTodosPlatillos;
    public static String imgurl="https://foodroute.000webhostapp.com/img/";
    private static ArrayList<Sugerencias> array;
    private MyAdapter adaptador;
    private ProgressBar prgCircular;
    private TextView lblActualizado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_platillos_restaurante);

        lblActualizado=(TextView)findViewById(R.id.lblPrecio);
        lstTodosPlatillos = (RecyclerView)findViewById(R.id.lstTodosPlatillos);
        prgCircular = (ProgressBar)findViewById(R.id.prgCircular);
        lstTodosPlatillos.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new GridLayoutManager(AllPlatillosRestaurante.this,2);
        lstTodosPlatillos.setLayoutManager(layoutManager);

        if(savedInstanceState==null) {
            array=new ArrayList<>();
            adaptador=new MyAdapter(array);

            CargarDatos();
        }else{

            array = (ArrayList<Sugerencias>) savedInstanceState.getSerializable("lstToPlatillosG");
            adaptador=new MyAdapter(array);

            lstTodosPlatillos.setVisibility(View.VISIBLE);
            prgCircular.setVisibility(View.INVISIBLE);
            lblActualizado.setVisibility(View.VISIBLE);
        }

        lstTodosPlatillos.setAdapter(adaptador);

        //creacion de la fecha atras
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putParcelableArrayList("lstToPlatillosG", array);

        super.onSaveInstanceState(outState);
    }

    private void CargarDatos() {

        AsyncHttpClient client=new AsyncHttpClient();
        client.get("https://foodroute.000webhostapp.com/proyecto/obtener_comidas_restaurantes.php?nombre="+DetalleRestaurante.nombreRes, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    prgCircular.setVisibility(View.INVISIBLE);
                    lstTodosPlatillos.setVisibility(View.VISIBLE);
                    lblActualizado.setVisibility(View.VISIBLE);
                    try {
                        JSONObject json=new JSONObject(new String(responseBody));
                        JSONArray jsonArray=json.getJSONArray("comidas");
                        //sug=new Sugerencias[jsonArray.length()];
                        for(int i=0;i<jsonArray.length();i++){
                            Sugerencias sugere=new Sugerencias();
                            sugere.setNombrePlatillo(jsonArray.getJSONObject(i).getString("NombrePlatillo"));
                            sugere.setLugar(jsonArray.getJSONObject(i).getString("Nombre"));
                            sugere.setPrecio(jsonArray.getJSONObject(i).getString("PrecioTotal"));
                            sugere.setImagen(jsonArray.getJSONObject(i).getString("Foto"));
                            sugere.setLatitud(Double.parseDouble(jsonArray.getJSONObject(i).getString("latitud")));
                            sugere.setLongitud(Double.parseDouble(jsonArray.getJSONObject(i).getString("longitud")));
                            array.add(sugere);
                            adaptador.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(AllPlatillosRestaurante.this,"Error al cargar lista",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Toast.makeText(getActivity().getApplicationContext(),"Error al cargar lista",Toast.LENGTH_SHORT).show();
                Dialog();

            }
        });
    }




    public void Dialog() {
        AlertDialog.Builder Alerta = new AlertDialog.Builder(AllPlatillosRestaurante.this);
        Alerta.setTitle("Fallo al cargar los Platillos").
                setMessage("Volever a cargar datos").
                setIcon(R.drawable.warning).
                setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CargarDatos();
                    }
                }).
                setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        prgCircular.setVisibility(View.INVISIBLE);
    //                    imagen_señal.setVisibility(View.VISIBLE);
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

