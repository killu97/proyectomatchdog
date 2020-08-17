package com.example.ntpf;

public class mensajes {

    private String nombre;
    private String mensaje;
    private String destino;
    private String origen;
    private String destinoOr;
    private String origenOr;

    public mensajes() {
    }

    public mensajes(String nombre, String mensaje) {
        this.nombre = nombre;
        this.mensaje = mensaje;
    }

    public mensajes(String nombre, String mensaje, String destino, String origen) {
        this.nombre = nombre;
        this.mensaje = mensaje;
        this.destino = destino;
        this.origen = origen;
    }

    public mensajes(String nombre, String mensaje, String destino, String origen, String destinoOr, String origenOr) {
        this.nombre = nombre;
        this.mensaje = mensaje;
        this.destino = destino;
        this.origen = origen;
        this.destinoOr = destinoOr;
        this.origenOr = origenOr;
    }

    public String getDestinoOr() {
        return destinoOr;
    }

    public void setDestinoOr(String destinoOr) {
        this.destinoOr = destinoOr;
    }

    public String getOrigenOr() {
        return origenOr;
    }

    public void setOrigenOr(String origenOr) {
        this.origenOr = origenOr;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "mensajes{" +
                "nombre='" + nombre + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", destino='" + destino + '\'' +
                ", origen='" + origen + '\'' +
                ", destinoOr='" + destinoOr + '\'' +
                ", origenOr='" + origenOr + '\'' +
                '}';
    }
}
