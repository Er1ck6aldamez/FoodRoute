package Clases;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hd.foodroute_v1.DetalleRestaurante;
import com.example.hd.foodroute_v1.R;
import com.example.hd.foodroute_v1.TbInicio;
import com.example.hd.foodroute_v1.Ubicacion;
import com.github.snowdream.android.widget.SmartImageView;

import java.util.List;

import Modelo.Lugares;

/**
 * Created by Choche on 30/10/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    public static List<Sugerencias> lstSugerencias;
    public static boolean verdad=false;

    public static void Dialog(final int position,final View v) {
        AlertDialog.Builder Alerta = new AlertDialog.Builder(v.getContext());
        Alerta.setTitle("Confirmación").
                setMessage("¿Realizar pedido?").
                setIcon(R.drawable.warning).
                setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(v.getContext(),lstSugerencias.get(position).getNombrePlatillo(),Toast.LENGTH_SHORT).show();
                    }
                }).
                setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Toast.makeText(v.getContext(),lstSugerencias.get(position).getPrecio(),Toast.LENGTH_SHORT).show();
                    }
                });

        AlertDialog alertDialog = Alerta.create();
        alertDialog.show();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;
        int pos=0;
        // en este ejemplo cada elemento consta solo de un título
        public TextView plato,precio,lugar;
        public Button pedir;
        public SmartImageView imagen;
        public CardView tarjeta;

        public ViewHolder(View v) {
            super(v);
            context=v.getContext();
            plato = (TextView) v.findViewById(R.id.txtNombrePlatillo);
            precio = (TextView) v.findViewById(R.id.txtPrecio);
            pedir=(Button) v.findViewById(R.id.btnPedir);
           // lugar=(TextView) v.findViewById(R.id.txtLugar);
            imagen=(SmartImageView) v.findViewById(R.id.imgSugerencia);
            tarjeta=(CardView) v.findViewById(R.id.cardviewhorarios);
        }
        void setOnClickListeners(int posi){
            tarjeta.setOnClickListener(this);
            pedir.setOnClickListener(this);
            pos=posi;
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btnPedir:
                    //Toast.makeText(v.getContext(),lstSugerencias.get(pos).getNombrePlatillo(),Toast.LENGTH_SHORT).show();
                    Dialog(pos,v);
                    break;
                case R.id.cardviewhorarios:
                    verdad=false;
                    DetalleRestaurante.Datos=lstSugerencias.get(pos).getLugar();
                    if (!verdad) {
                        Ubicacion.la = lstSugerencias.get(pos).getLatitud();
                        Ubicacion.lo = lstSugerencias.get(pos).getLongitud();
                    }
                    Intent intr = new Intent(context, DetalleRestaurante.class);
                    context.startActivity(intr);
                    break;
            }
        }
    }

    public MyAdapter(List<Sugerencias> lstSu) {
        lstSugerencias = lstSu;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sugerencia, parent, false);

        ViewHolder vh=new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        final int pos=position;
        //holder.mTextView.setText(mDataSet[position]);
        holder.plato.setText(lstSugerencias.get(position).getNombrePlatillo());
        holder.precio.setText("$"+lstSugerencias.get(position).getPrecio());
        if(Double.parseDouble(lstSugerencias.get(position).getPrecio())>=1.25){
            holder.pedir.setVisibility(View.VISIBLE);
        }else{
            holder.pedir.setVisibility(View.INVISIBLE);
        }
        holder.pedir.setText("Pedir");
      //  holder.lugar.setText(lstSugerencias.get(position).getLugar());
        Rect rect=new Rect(holder.imagen.getLeft(),holder.imagen.getTop(),holder.imagen.getRight(),holder.imagen.getBottom());
        holder.imagen.setImageUrl(TbInicio.imgurl+lstSugerencias.get(position).getImagen(),rect);

        holder.setOnClickListeners(position);
    }

    @Override
    public int getItemCount() {
        return  lstSugerencias == null ? 0 : lstSugerencias.size();
    }
}
