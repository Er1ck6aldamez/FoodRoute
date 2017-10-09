package Clases;

/**
 * Created by Choche on 8/10/2017.
 */

public class Sugerencias {
    String NombrePlatillo,Lugar,Precio,imagen;
    Double latitud,longitud;

    public Sugerencias(){

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
}
