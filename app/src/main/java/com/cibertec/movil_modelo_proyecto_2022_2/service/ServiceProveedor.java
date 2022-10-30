package com.cibertec.movil_modelo_proyecto_2022_2.service;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Proveedor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceProveedor {

    @GET("proveedor/porRazonSocial/{RS}")
    public Call<List<Proveedor>> listaPorRazonSocial(@Path("RS")String RazonSocial);



}
