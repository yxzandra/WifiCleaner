package com.hostienda.wificleaner.classes;

/**
 * Created by Desarrollo on 19/10/2016.
 */
public class Redes {
    String nombre;
    String seguridad;
    String estado;
    double potencia;
    String velocidad;
    String frecuencia;
    int tipoRed;
    boolean guardada;

    //tipo de red 0= conectada 1=cerca y 2= guardada


    public Redes(String nombre, String seguridad, String estado, double potencia,  String velocidad, String frecuencia, int tipoRed, boolean guardada) {
        this.estado = estado;
        this.frecuencia = frecuencia;
        this.guardada = guardada;
        this.nombre = nombre;
        this.potencia = potencia;
        this.seguridad = seguridad;
        this.tipoRed = tipoRed;
        this.velocidad = velocidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPotencia() {
        return potencia;
    }

    public void setPotencia(double potencia) {
        this.potencia = potencia;
    }

    public String getSeguridad() {
        return seguridad;
    }

    public void setSeguridad(String seguridad) {
        this.seguridad = seguridad;
    }

    public int getTipoRed() {
        return tipoRed;
    }

    public void setTipoRed(int tipoRed) {
        this.tipoRed = tipoRed;
    }

    public String getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(String velocidad) {
        this.velocidad = velocidad;
    }

    public boolean isGuardada() {
        return guardada;
    }

    public void setGuardada(boolean guardada) {
        this.guardada = guardada;
    }

    @Override
    public String toString() {
        return "Redes{" +
                "estado='" + estado + '\'' +
                ", nombre='" + nombre + '\'' +
                ", seguridad='" + seguridad + '\'' +
                ", potencia=" + potencia +
                ", velocidad='" + velocidad + '\'' +
                ", frecuencia='" + frecuencia + '\'' +
                ", tipoRed=" + tipoRed +
                ", guardada=" + guardada +
                '}';
    }
}
