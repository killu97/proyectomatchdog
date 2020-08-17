package com.example.ntpf.entidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class mascotas {
    private String canNom;
    private String Raza;
    private String Sexo;
    private String canEdad;
    private String dato;
    private Bitmap imagencan;
    private String user_id;



    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;

        try {
            byte[] bytecode= Base64.decode(dato,Base64.DEFAULT);
            this.imagencan= BitmapFactory.decodeByteArray(bytecode,0,bytecode.length);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public Bitmap getImagencan() {
        return imagencan;
    }

    public void setImagencan(Bitmap imagencan) {
        this.imagencan = imagencan;
    }

    public String getCanNom() {
        return canNom;
    }

    public void setCanNom(String canNom) {
        this.canNom = canNom;
    }

    public String getRaza() {
        return Raza;
    }

    public void setRaza(String raza) {
        Raza = raza;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String sexo) {
        Sexo = sexo;
    }

    public String getCanEdad() {
        return canEdad;
    }

    public void setCanEdad(String canEdad) {
        this.canEdad = canEdad;
    }
}
