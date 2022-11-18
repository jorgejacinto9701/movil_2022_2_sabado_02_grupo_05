package com.cibertec.movil_modelo_proyecto_2022_2.service;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceAutor {

    @POST("autor")
    public Call<Autor> insertaAutor(@Body Autor objAutor);

    @GET("autor")
    public Call<List<Autor>> listaAutor() ;

    @GET("autor/porNombre/{nombre}")
    public Call<List<Autor>> listaAutorPorNombre(@Path("nombre") String nombre);

    @PUT("autor")
    public Call<Autor> actualizaAutor(@Body Autor obj);

}
