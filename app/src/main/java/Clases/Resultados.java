package Clases;

/**
 * Created by hd on 19/10/2017.
 */

public class Resultados {
    String NombrePlatillo, Lugar, Precio, imagen;
    Double latitud, longitud;


    public Resultados() {

    }

    public Resultados(String nombrePlatillo, String lugar, String precio, String imgen) {
        NombrePlatillo = nombrePlatillo;
        Lugar = lugar;
        Precio = precio;
        imagen = imgen;
    }

    public Resultados(String nombrePlatillo, String lugar, String precio, Double latitud, Double longitud) {
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
}
