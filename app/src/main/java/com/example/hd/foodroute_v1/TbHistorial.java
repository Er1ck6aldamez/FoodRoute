package com.example.hd.foodroute_v1;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import Clases.AdaptadorHistorial;
import Modelo.HistorialDBHelper;
import Modelo.Lugares;

/**
 * Created by hd on 25/9/2017.
 */

public class TbHistorial extends Fragment{

    private ListView lstHistorial;
    private AdaptadorHistorial adaptadorHistorial;
    private HistorialDBHelper historialDBHelper;
    private ImageView fondo;
    private Button btnBorrar;

    public TbHistorial(){

    }
    public static TbHistorial newInstance(){
        return new TbHistorial();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tbhistorial, container, false);

        lstHistorial=(ListView)rootView.findViewById(R.id.lstHistorial);
        fondo=(ImageView)rootView.findViewById(R.id.fondoHistorial);
        adaptadorHistorial=new AdaptadorHistorial(getActivity(),null);
        btnBorrar=(Button)rootView.findViewById(R.id.btnBorrar);

        lstHistorial.setAdapter(adaptadorHistorial);

        historialDBHelper=new HistorialDBHelper(getActivity());

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historialDBHelper.deleteAll();
                loadLawyers();
            }
        });

        // Carga de datos
        loadLawyers();

        return rootView;
    }

    private void loadLawyers() {
        // Cargar datos...
        new LawyersLoadTask().execute();
    }

    private class LawyersLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return historialDBHelper.getAllNotes();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                fondo.setVisibility(View.INVISIBLE);
                adaptadorHistorial.swapCursor(cursor);
            } else {
                // Mostrar empty state
                adaptadorHistorial.swapCursor(cursor);
                fondo.setVisibility(View.VISIBLE);
                btnBorrar.setVisibility(View.INVISIBLE);
            }
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}

