package com.example.pedroantonio.videoclubandroid;

/**
 * Created by Pedro Antonio on 24/11/2016.
 */

public class PeliculasBuscadas {
    private String fechaSalida;
    private String duración;
    private String genero;
    private String titulo;
    private String anyo;
    private String portada;

    public PeliculasBuscadas(String duración, String anyo, String fechaSalida, String genero, String titulo, String portada) {
        this.duración = duración;
        this.anyo = anyo;
        this.fechaSalida = fechaSalida;
        this.genero = genero;
        this.titulo = titulo;
        this.portada = portada;
    }

    public String getAnyo() {
        return anyo;
    }

    public void setAnyo(String anyo) {
        this.anyo = anyo;
    }

    public String getDuración() {
        return duración;
    }

    public void setDuración(String duración) {
        this.duración = duración;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PeliculasBuscadas that = (PeliculasBuscadas) o;

        if (fechaSalida != null ? !fechaSalida.equals(that.fechaSalida) : that.fechaSalida != null)
            return false;
        if (duración != null ? !duración.equals(that.duración) : that.duración != null)
            return false;
        if (genero != null ? !genero.equals(that.genero) : that.genero != null) return false;
        if (titulo != null ? !titulo.equals(that.titulo) : that.titulo != null) return false;
        if (anyo != null ? !anyo.equals(that.anyo) : that.anyo != null) return false;
        return portada != null ? portada.equals(that.portada) : that.portada == null;
    }
}
