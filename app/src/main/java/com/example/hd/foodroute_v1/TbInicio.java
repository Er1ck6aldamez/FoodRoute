package com.example.hd.foodroute_v1;

/**
 * Created by hd on 25/9/2017.
 */
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Clases.AdaptadorSugerencias;
import Clases.MyAdapter;
import Clases.Sugerencias;
import cz.msebera.android.httpclient.Header;


public class TbInicio extends Fragment{
    //Declaracion de variables
    private RecyclerView lstSugerencias;
    public static String imgurl="https://foodroute.000webhostapp.com/img/";
    private static ArrayList<Sugerencias> array;
    private MyAdapter adaptador;
    private ProgressBar prgCircular;
    private TextView lblActualizado;
    private ImageView imagen_señal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tbinicio, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lstSugerencias = (RecyclerView)getView().findViewById(R.id.lstSugerencias);
        prgCircular = (ProgressBar)getView().findViewById(R.id.prgCircular);
        lblActualizado=(TextView)getView().findViewById(R.id.lblPrecios);
        lstSugerencias.setHasFixedSize(true);
        imagen_señal = (ImageView)getView().findViewById(R.id.img_señal);

        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(),getResources().getInteger(R.integer.colInicio));
        lstSugerencias.setLayoutManager(layoutManager);

        if(savedInstanceState==null) {
            array=new ArrayList<>();
            adaptador=new MyAdapter(array);

            CargarDatos();
        }else{

            array = (ArrayList<Sugerencias>) savedInstanceState.getSerializable("listSugerencias");
            adaptador=new MyAdapter(array);

            lstSugerencias.setVisibility(View.VISIBLE);
            prgCircular.setVisibility(View.INVISIBLE);
            lblActualizado.setVisibility(View.VISIBLE);
        }

        lstSugerencias.setAdapter(adaptador);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putParcelableArrayList("listSugerencias", array);

        super.onSaveInstanceState(outState);
    }

    private void CargarDatos() {

        AsyncHttpClient client=new AsyncHttpClient();
        client.get("https://foodroute.000webhostapp.com/proyecto/obtener_sugerencias.php", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    prgCircular.setVisibility(View.INVISIBLE);
                    lstSugerencias.setVisibility(View.VISIBLE);
                    lblActualizado.setVisibility(View.VISIBLE);
                    try {
                        JSONObject json=new JSONObject(new String(responseBody));
                        JSONArray jsonArray=json.getJSONArray("alumnos");
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
                    Toast.makeText(getActivity().getApplicationContext(),"Error al cargar lista",Toast.LENGTH_SHORT).show();
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
        AlertDialog.Builder Alerta = new AlertDialog.Builder(getContext());
        Alerta.setTitle("Fallo al cargar Sugerencias").
        setMessage("Volver a cargar datos").
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
                imagen_señal.setVisibility(View.VISIBLE);
            }
        });

        AlertDialog alertDialog = Alerta.create();
        alertDialog.show();
    }

}
