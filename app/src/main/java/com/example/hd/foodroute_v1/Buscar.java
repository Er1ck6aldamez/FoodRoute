package com.example.hd.foodroute_v1;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class Buscar extends AppCompatActivity {

    private Button btnBuscar;
    private EditText txtPresupuesto;
    private String tipo="";
    private RadioButton rbtnEfectivo, rbtnEfeTar;
    private String[] tipos_comida=new String[]{"Seleccionar","Comida a la carta","Comida a la vista","Comida a la parrilla","Comida mexicana","Comida rápida","Comida tradicional"};
    private Spinner cmbxTiposComidas;
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

        this.adaptador=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,this.tipos_comida);
        this.cmbxTiposComidas.setAdapter(this.adaptador);

        cmbxTiposComidas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Resultados.tipo_comida=tipos_comida[position].toString();
                tipo=tipos_comida[position].toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtPresupuesto.getText().length()>0 && (rbtnEfectivo.isChecked()||rbtnEfeTar.isChecked()) &&(tipo!="" && tipo!="Seleccionar")) {
                    if(rbtnEfectivo.isChecked()){
                        Resultados.efectivo=1;
                    }else{
                        Resultados.efectivo=0;
                    }

                    Resultados.presupuesto=txtPresupuesto.getText().toString();
                    Intent res = new Intent(Buscar.this, Resultados.class);
                    startActivity(res);
                }else{
                    Toast.makeText(Buscar.this,"Campos vacíos",Toast.LENGTH_SHORT).show();
                }
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

