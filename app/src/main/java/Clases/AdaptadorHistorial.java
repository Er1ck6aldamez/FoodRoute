package Clases;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.hd.foodroute_v1.R;
import com.example.hd.foodroute_v1.TbHistorial;

import Modelo.Lugares;
import Modelo.LugaresVisitados;

/**
 * Created by Choche on 4/11/2017.
 */

public class AdaptadorHistorial extends CursorAdapter {


    public AdaptadorHistorial(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);

        return inflater.inflate(R.layout.item_historial,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView Nombre = (TextView) view.findViewById(R.id.txtNombreRest);
        TextView HoraA = (TextView) view.findViewById(R.id.txtHora);
        TextView HoraC = (TextView) view.findViewById(R.id.txtHoraCierre);
        TextView Correlativo = (TextView) view.findViewById(R.id.txtCorrelativo_);

        String nombre=cursor.getString(cursor.getColumnIndex(LugaresVisitados.NOTES.NOMBRE_COL));
        String horaA=cursor.getString(cursor.getColumnIndex(LugaresVisitados.NOTES.HORAAPE_COL));
        String horaC=cursor.getString(cursor.getColumnIndex(LugaresVisitados.NOTES.HORACIE_COL));

        Nombre.setText(nombre);
        HoraA.setText("Abre : "+horaA);
        HoraC.setText("Cierra : "+horaC);
        Correlativo.setText(String.valueOf("-"));

    }
}
