package com.cibertec.movil_modelo_proyecto_2022_2.entity;

import java.io.Serializable;

public class Proveedor implements Serializable {
    private int idProveedor;
    private String RS;
    private String Empresa;


    public int getidProveedor() {
        return idProveedor;
    }

    public void setIidProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getEmpresa() {
        return Empresa;
    }

    public void setEmpresa(String Empresa) {
        this.Empresa = Empresa;
    }

    public String getRS() {
        return RS;
    }

    public void setRS(String RS) {
        this.RS = RS;
    }


}
