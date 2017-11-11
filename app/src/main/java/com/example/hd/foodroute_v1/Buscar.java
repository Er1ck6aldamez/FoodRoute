package com.example.hd.foodroute_v1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.hd.foodroute_v1.R.id.prgCircular;

public class Buscar extends AppCompatActivity {

    private Button btnBuscar;
    private EditText txtPresupuesto;
    private String tipo="";
    private RadioButton rbtnEfectivo, rbtnEfeTar;
    private String[] tipos_comida=new String[]{"Seleccionar","Comida a la carta","Comida a la vista","Comida a la parrilla","Comida mexicana","Comida rápida","Comida tradicional"};
    private Spinner cmbxTiposComidas;
    private TextView tipoComida;
    private ArrayAdapter<String> adaptador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        txtPresupuesto = (EditText) findViewById(R.id.txtpresupuesto);
        rbtnEfectivo=(RadioButton)findViewById(R.id.rbtnEfectivo);
        rbtnEfeTar=(RadioButton)findViewById(R.id.rbtnEfecivo_Tarjeta);
        cmbxTiposComidas=(Spinner)findViewById(R.id.spnrTipoComida);
        tipoComida = (TextView) findViewById(R.id.txt_tipoComida);

        this.adaptador=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,this.tipos_comida);
        this.cmbxTiposComidas.setAdapter(this.adaptador);

        cmbxTiposComidas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Resultados.tipo_comida=tipos_comida[position].toString();
                tipo=tipos_comida[position].toString();

                tipoComida.setError(null);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Validar()) {

                    if(rbtnEfectivo.isChecked()){
                        Resultados.efectivo=1;
                    }else{
                        Resultados.efectivo=0;
                    }

                    Resultados.presupuesto=txtPresupuesto.getText().toString();
                    Intent res = new Intent(Buscar.this, Resultados.class);
                    startActivityForResult(res,2);
                }
            }
        });



        rbtnEfectivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbtnEfectivo.setError(null);

            }
        });

        rbtnEfeTar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbtnEfectivo.setError(null);
            }
        });
        //creacion de la fecha atras
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public boolean Validar(){
        boolean estado=true;

            String presupuesto = txtPresupuesto.getText().toString().trim();
            if (TextUtils.isEmpty(presupuesto)) {
                txtPresupuesto.setError("Campo Obligatorio");
                txtPresupuesto.findFocus();

                estado = false;
             }

            if ((!rbtnEfectivo.isChecked() && !rbtnEfeTar.isChecked())) {
                rbtnEfectivo.setError("Seleccione una Forma de Pago");
                estado = false;
            }

            if (tipo == "" || tipo == "Seleccionar") {
                tipoComida.setError("Seleccione una Forma de Pago");
                estado = false;
            }
            return estado;
    }







    //para terminar la activity (flecha atras)
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==2){
            if (resultCode== Activity.RESULT_OK){
                if(data.hasExtra("home")){

                    Intent home2 = new Intent();
                    home2.putExtra("home",true);
                    setResult(Activity.RESULT_OK,home2);
                    finish();
                }

                if(data.hasExtra("especialidad")){

                    Intent espe = new Intent();
                    espe.putExtra("especialidad",true);
                    setResult(Activity.RESULT_OK,espe);
                    finish();
                }

                if(data.hasExtra("historial")){

                    Intent historial = new Intent();
                    historial.putExtra("historial",true);
                    setResult(Activity.RESULT_OK,historial);
                    finish();
                }
            }

        }

    }
}

