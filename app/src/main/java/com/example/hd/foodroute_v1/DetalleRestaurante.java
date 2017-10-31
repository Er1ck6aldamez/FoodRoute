package com.example.hd.foodroute_v1;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.snowdream.android.widget.SmartImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Clases.DatosRestaurantes;
import Clases.Sugerencias;
import cz.msebera.android.httpclient.Header;

public class DetalleRestaurante extends AppCompatActivity {

    private Button btnGeo, btnTel;
    public static String Datos;
    private String ruta;
    private DatosRestaurantes comedor;
    public static String imgurl="https://foodroute.000webhostapp.com/img/";

    private SmartImageView logo;
    private TextView nombre, direccion, especialidad, horaApe, horaCie, formaPago, servDomicilio, telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detalle_restaurante);
        //flecha atras
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ruta="https://foodroute.000webhostapp.com/proyecto/obtener_restaurantes_por_nombre.php?nombre="+Datos;

        this.logo = (SmartImageView) findViewById(R.id.logoRestaurante);

        this.nombre = (TextView) findViewById(R.id.txtNombreRestaurante);
        this.direccion = (TextView) findViewById(R.id.txtDireccion);
        this.especialidad = (TextView) findViewById(R.id.txtEspecialidad);
        this.horaApe = (TextView) findViewById(R.id.txthoraApe);
        this.horaCie = (TextView) findViewById(R.id.txthoraCierre);
        this.servDomicilio = (TextView) findViewById(R.id.txtServicioDomicio);
        this.telefono = (TextView) findViewById(R.id.txtTelefono);
        this.formaPago = (TextView) findViewById(R.id.txtFormaPago);

        this.btnGeo = (Button) findViewById(R.id.btnGeolocalizacion);
        this.btnTel = (Button) findViewById(R.id.btnTelfono);

        comedor=new DatosRestaurantes();

        btnGeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapa = new Intent(DetalleRestaurante.this, Ubicacion.class);
                startActivity(mapa);
            }
        });

        btnTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent numero = new Intent(Intent.ACTION_DIAL);
                numero.setData(Uri.parse("tel:"+telefono.getText()+""));
                startActivity(numero);
            }
        });

        if(savedInstanceState==null){
            comedor=new DatosRestaurantes();
            CargarDatos();
        }else{
            comedor=(DatosRestaurantes) savedInstanceState.getParcelable("restaurante");
            nombre.setText(comedor.getNombre());
            direccion.setText(comedor.getDireccion());
            especialidad.setText(comedor.getEspecialidad());
            telefono.setText(comedor.getTelefono());
            horaApe.setText(comedor.getHoraApertura());
            horaCie.setText(comedor.getHoraCierre());
            servDomicilio.setText(comedor.getServicioDomicilio());
            formaPago.setText(comedor.getEfectivo());
            Rect rect=new Rect(logo.getLeft(),logo.getTop(),logo.getRight(),logo.getBottom());
            logo.setImageUrl(imgurl+comedor.getImagen(),rect);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putParcelable("restaurante", comedor);
        super.onSaveInstanceState(outState);
    }

    private void CargarDatos() {
        AsyncHttpClient client=new AsyncHttpClient();

        client.get(ruta, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){

                    try {
                        JSONObject json=new JSONObject(new String(responseBody));
                        String estado=json.getString("estado");
                        if(estado.equals("1")) {
                            JSONArray jsonArray=json.getJSONArray("restaurantes");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                nombre.setText(jsonArray.getJSONObject(i).getString("Nombre"));
                                comedor.setNombre(jsonArray.getJSONObject(i).getString("Nombre"));
                                direccion.setText(jsonArray.getJSONObject(i).getString("Direccion"));
                                comedor.setDireccion(jsonArray.getJSONObject(i).getString("Direccion"));
                                especialidad.setText(jsonArray.getJSONObject(i).getString("Especialidad"));
                                comedor.setEspecialidad(jsonArray.getJSONObject(i).getString("Especialidad"));
                                horaApe.setText(jsonArray.getJSONObject(i).getString("HoraApertura"));
                                comedor.setHoraApertura(jsonArray.getJSONObject(i).getString("HoraApertura"));
                                horaCie.setText(jsonArray.getJSONObject(i).getString("HoraCierre"));
                                comedor.setHoraCierre(jsonArray.getJSONObject(i).getString("HoraCierre"));
                                telefono.setText(jsonArray.getJSONObject(i).getString("Telefono"));
                                comedor.setTelefono(jsonArray.getJSONObject(i).getString("Telefono"));

                                if (jsonArray.getJSONObject(i).getString("ServicioDomicilio").equals("0")){
                                    servDomicilio.setText("No");
                                    comedor.setServicioDomicilio("No");
                                }else {
                                    servDomicilio.setText("Si");
                                    comedor.setServicioDomicilio("Si");
                                }

                                if (jsonArray.getJSONObject(i).getString("Tarjeta").equals("1") && jsonArray.getJSONObject(i).getString("Efectivo").equals("1")){
                                    formaPago.setText("Efectivo y Tarjeta");
                                    comedor.setEfectivo("Efectivo y Tarjeta");
                                }else {
                                    formaPago.setText("Efectivo");
                                    comedor.setEfectivo("Efectivo");
                                }

                                Rect rect=new Rect(logo.getLeft(),logo.getTop(),logo.getRight(),logo.getBottom());
                                logo.setImageUrl(imgurl+jsonArray.getJSONObject(i).getString("Imagen"),rect);
                                comedor.setImagen(jsonArray.getJSONObject(i).getString("Imagen"));

                            }
                        }else{
                            //  Dialog();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(DetalleRestaurante.this,"Error",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Toast.makeText(getActivity().getApplicationContext(),"Error al cargar lista",Toast.LENGTH_SHORT).show();
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
