package Clases;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hd.foodroute_v1.Principal;
import com.example.hd.foodroute_v1.R;
import com.example.hd.foodroute_v1.TbInicio;
import com.github.snowdream.android.widget.SmartImage;
import com.github.snowdream.android.widget.SmartImageView;

import java.util.List;

/**
 * Created by hd on 19/10/2017.
 */

public class AdaptadorResultados extends ArrayAdapter<Resultados> {


    public AdaptadorResultados(@NonNull Context context, @NonNull List<Resultados> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Resultados res=getItem(position);


        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_resultado,parent,false);
        }
        if(res!=null){
            TextView lblNombre=(TextView) convertView.findViewById(R.id.txtNombrePlatillo);
            TextView lblLugar=(TextView) convertView.findViewById(R.id.txtLugar);
            TextView lblPrecio=(TextView) convertView.findViewById(R.id.txtPrecio);
            SmartImageView img=(SmartImageView) convertView.findViewById(R.id.imgResultado);

            if(lblNombre!=null&&lblLugar!=null&&lblPrecio!=null){
                lblNombre.setText(res.getNombrePlatillo());
                lblLugar.setText(res.getLugar());
                lblPrecio.setText("$"+res.getPrecio());
                Rect rect=new Rect(img.getLeft(),img.getTop(),img.getRight(),img.getBottom());
                img.setImageUrl(TbInicio.imgurl+res.getImagen(),rect);
            }
        }

        return convertView;
    }
}

