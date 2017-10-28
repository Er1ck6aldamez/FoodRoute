package Clases;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Choche on 8/10/2017.
 */

public class Sugerencias implements Parcelable{
    private String NombrePlatillo,Lugar,Precio,imagen;
    private Double latitud,longitud;

    public static final Parcelable.Creator<Sugerencias> CREATOR
            = new Parcelable.Creator<Sugerencias>() {
        public Sugerencias createFromParcel(Parcel in) {
            return new Sugerencias(in);
        }

        public Sugerencias[] newArray(int size) {
            return new Sugerencias[size];
        }
    };

    public Sugerencias(){

    }
    public Sugerencias(Parcel in){
        readFromParcel(in);
    }

    public Sugerencias(String nombrePlatillo, String lugar, String precio,String imgen) {
        NombrePlatillo = nombrePlatillo;
        Lugar = lugar;
        Precio = precio;
        imagen=imgen;
    }

    public Sugerencias(String nombrePlatillo, String lugar, String precio, Double latitud, Double longitud) {
        NombrePlatillo = nombrePlatillo;
        Lugar = lugar;
        Precio = precio;
        this.latitud = latitud;
        this.longitud = longitud;
    }



    public String getNombrePlatillo() {
        return NombrePlatillo;
    }

    public void setNombrePlatillo(String nombrePlatillo) {
        NombrePlatillo = nombrePlatillo;
    }

    public String getLugar() {
        return Lugar;
    }

    public void setLugar(String lugar) {
        Lugar = lugar;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(NombrePlatillo);
        dest.writeString(Lugar);
        dest.writeString(Precio);
        dest.writeString(imagen);
        dest.writeDouble(latitud);
        dest.writeDouble(longitud);


    }
    private void readFromParcel(Parcel in){
        this.NombrePlatillo=in.readString();
        Lugar=in.readString();
        Precio=in.readString();
        imagen=in.readString();
        latitud=in.readDouble();
        longitud=in.readDouble();

    }
}
