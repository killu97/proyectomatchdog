package com.example.ntpf;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class Chat {

    private String origen;
    private String destino;
    private String nombre;
    private String mensaje;

    public Chat() {
    }

    public Chat(String origen, String destino, String nombre, String ultimoMensaje) {
        this.origen = origen;
        this.destino = destino;
        this.nombre = nombre;
        this.mensaje = ultimoMensaje;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return origen.equals(chat.origen);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(origen);
    }

    @Override
    public String toString() {
        return "Chat{" +
                "origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", nombre='" + nombre + '\'' +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }
}
