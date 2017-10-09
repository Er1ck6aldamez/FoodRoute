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
 * Created by Choche on 8/10/2017.
 */

public class AdaptadorSugerencias extends ArrayAdapter<Sugerencias> {

    public AdaptadorSugerencias(@NonNull Context context, @NonNull List<Sugerencias> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Sugerencias suge=getItem(position);

        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_sugerencia,parent,false);
        }
        if(suge!=null){
            TextView lblNombre=(TextView) convertView.findViewById(R.id.txtNombrePlatillo);
            TextView lblLugar=(TextView) convertView.findViewById(R.id.txtLugar);
            TextView lblPrecio=(TextView) convertView.findViewById(R.id.txtPrecio);
            SmartImageView img=(SmartImageView) convertView.findViewById(R.id.imgSugerencia);

            if(lblNombre!=null&&lblLugar!=null&&lblPrecio!=null){
                lblNombre.setText(suge.getNombrePlatillo());
                lblLugar.setText(suge.getLugar());
                lblPrecio.setText("$"+suge.getPrecio());
                Rect rect=new Rect(img.getLeft(),img.getTop(),img.getRight(),img.getBottom());
                img.setImageUrl(TbInicio.imgurl+suge.getImagen(),rect);
            }
        }

        return convertView;
    }
}
