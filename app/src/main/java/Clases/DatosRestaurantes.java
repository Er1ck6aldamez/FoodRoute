package Clases;

/**
 * Created by hd on 27/10/2017.
 */

public class DatosRestaurantes {
    private String Nombre, Direccion, Especialidad, HoraApertura, HoraCierre,Tarjeta, Efectivo, Imagen, ServicioDomicilio,Telefono;

    public DatosRestaurantes(String nombre, String direccion, String especialidad, String horaApertura, String horaCierre, String tarjeta, String efectivo, String imagen, String servicioDomicilio, String telefono) {
        Nombre = nombre;
        Direccion = direccion;
        Especialidad = especialidad;
        HoraApertura = horaApertura;
        HoraCierre = horaCierre;
        Tarjeta = tarjeta;
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

    public String getTarjeta() {
        return Tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        Tarjeta = tarjeta;
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
}
