package Clases;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hd on 27/10/2017.
 */

public class DatosRestaurantes implements Parcelable {
    private String Nombre, Direccion, Especialidad, HoraApertura, HoraCierre, Efectivo, Imagen, ServicioDomicilio,Telefono;

    public static final Parcelable.Creator<DatosRestaurantes> CREATOR
            = new Parcelable.Creator<DatosRestaurantes>() {
        public DatosRestaurantes createFromParcel(Parcel in) {
            return new DatosRestaurantes(in);
        }

        public DatosRestaurantes[] newArray(int size) {
            return new DatosRestaurantes[size];
        }
    };

    public DatosRestaurantes(Parcel in){
        readFromParcel(in);
    }

    public DatosRestaurantes(){}

    public DatosRestaurantes(String nombre, String direccion, String especialidad, String horaApertura, String horaCierre, String efectivo, String imagen, String servicioDomicilio, String telefono) {
        Nombre = nombre;
        Direccion = direccion;
        Especialidad = especialidad;
        HoraApertura = horaApertura;
        HoraCierre = horaCierre;
        Efectivo = efectivo;
        Imagen = imagen;
        ServicioDomicilio = servicioDomicilio;
        Telefono = telefono;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getEspecialidad() {
        return Especialidad;
    }

    public void setEspecialidad(String especialidad) {
        Especialidad = especialidad;
    }

    public String getHoraApertura() {
        return HoraApertura;
    }

    public void setHoraApertura(String horaApertura) {
        HoraApertura = horaApertura;
    }

    public String getHoraCierre() {
        return HoraCierre;
    }

    public void setHoraCierre(String horaCierre) {
        HoraCierre = horaCierre;
    }

    public String getEfectivo() {
        return Efectivo;
    }

    public void setEfectivo(String efectivo) {
        Efectivo = efectivo;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public String getServicioDomicilio() {
        return ServicioDomicilio;
    }

    public void setServicioDomicilio(String servicioDomicilio) {
        ServicioDomicilio = servicioDomicilio;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Nombre);
        dest.writeString(Direccion);
        dest.writeString(Especialidad);
        dest.writeString(HoraApertura);
        dest.writeString(HoraCierre);
        dest.writeString(Efectivo);
        dest.writeString(Imagen);
        dest.writeString(ServicioDomicilio);
        dest.writeString(Telefono);

    }
    private void readFromParcel(Parcel in){
        Nombre=in.readString();
        Direccion=in.readString();
        Especialidad=in.readString();
        HoraApertura=in.readString();
        HoraCierre=in.readString();
        Efectivo=in.readString();
        Imagen=in.readString();
        ServicioDomicilio=in.readString();
        Telefono=in.readString();

    }
}
