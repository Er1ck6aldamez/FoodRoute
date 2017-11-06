package Modelo;

import java.util.UUID;

/**
 * Created by Choche on 4/11/2017.
 */

public class Lugares {
    private String id;
    private String Nombre;
    private String horaApertura;
    private String horaCierre;
    private Double longitud;
    private Double latitud;

    public Lugares() {
        this.id = UUID.randomUUID().toString();
    }

    public Lugares(String nombre, String horaApertura, String horaCierre, Double longitud, Double latitud) {
        this.id = UUID.randomUUID().toString();
        Nombre = nombre;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(String horaApertura) {
        this.horaApertura = horaApertura;
    }

    public String getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(String horaCierre) {
        this.horaCierre = horaCierre;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }
}
