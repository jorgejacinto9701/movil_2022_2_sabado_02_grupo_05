package com.cibertec.movil_modelo_proyecto_2022_2.entity;

public class Libro {

    private int Codigo;
    private String Titulo;
    private int Años;
    private String Serie;
    private String fechaRegistro;
    private int estado;
    private String Categoria;

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public int getestado() {
        return estado;
    }

    public void setestado(int estado) {
        estado = estado;
    }

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int Codigo) {
        this.Codigo = Codigo;
    }

    public String getSerie() {
        return Serie;
    }

    public void setSerie(String serie) {
        Serie = serie;
    }

    private String Titutlo;

    public String getTitutlo() {
        return Titutlo;
    }

    public void setTitutlo(String titutlo) {
        Titutlo = titutlo;
    }

    public int getAños() {
        return Años;
    }

    public void setAños(int años) {
        Años = años;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }



}
