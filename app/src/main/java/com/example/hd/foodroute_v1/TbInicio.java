package com.example.hd.foodroute_v1;

/**
 * Created by hd on 25/9/2017.
 */
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class TbInicio extends Fragment{
    //Declaracion de variables
    private ListView lstSugerencias;
    public static String imgurl="https://foodroute.000webhostapp.com/img/";
    private ArrayList<Sugerencias> array;
    private AdaptadorSugerencias adaptador;
    private ProgressBar prgCircular;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tbinicio, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        array=new ArrayList<>();
        adaptador=new AdaptadorSugerencias(getActivity().getApplicationContext(),array);
        lstSugerencias = (ListView)getView().findViewById(R.id.lstSugerencias);
        prgCircular = (ProgressBar)getView().findViewById(R.id.prgCircular);

        CargarDatos();
        lstSugerencias.setAdapter(adaptador);
    }

    private void CargarDatos() {

        AsyncHttpClient client=new AsyncHttpClient();
        client.get("https://foodroute.000webhostapp.com/proyecto/obtener_sugerencias.php", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    prgCircular.setVisibility(View.INVISIBLE);
                    lstSugerencias.setVisibility(View.VISIBLE);
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
        setMessage("Volever a cargar datos").
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
            }
        });

        AlertDialog alertDialog = Alerta.create();
        alertDialog.show();
    }

}
