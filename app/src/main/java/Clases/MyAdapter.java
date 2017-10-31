package Clases;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hd.foodroute_v1.DetalleRestaurante;
import com.example.hd.foodroute_v1.R;
import com.example.hd.foodroute_v1.TbInicio;
import com.github.snowdream.android.widget.SmartImageView;

import java.util.List;

/**
 * Created by Choche on 30/10/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private static List<Sugerencias> lstSugerencias;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;
        int pos=0;
        // en este ejemplo cada elemento consta solo de un título
        public TextView plato,precio,lugar;
        public SmartImageView imagen;
        public CardView tarjeta;

        public ViewHolder(View v) {
            super(v);
            context=v.getContext();
            plato = (TextView) v.findViewById(R.id.txtNombrePlatillo);
            precio = (TextView) v.findViewById(R.id.txtPrecio);
            lugar=(TextView) v.findViewById(R.id.txtLugar);
            imagen=(SmartImageView) v.findViewById(R.id.imgSugerencia);
            tarjeta=(CardView) v.findViewById(R.id.cardviewhorarios);


        }
        void setOnClickListeners(int posi){
            tarjeta.setOnClickListener(this);
            pos=posi;
        }

        @Override
        public void onClick(View v) {
            DetalleRestaurante.Datos=lstSugerencias.get(pos).getLugar();
            Intent intr = new Intent(context, DetalleRestaurante.class);
            context.startActivity(intr);
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
        holder.lugar.setText(lstSugerencias.get(position).getLugar());
        Rect rect=new Rect(holder.imagen.getLeft(),holder.imagen.getTop(),holder.imagen.getRight(),holder.imagen.getBottom());
        holder.imagen.setImageUrl(TbInicio.imgurl+lstSugerencias.get(position).getImagen(),rect);

        holder.setOnClickListeners(position);
    }

    @Override
    public int getItemCount() {
        return lstSugerencias.size();
    }
}
