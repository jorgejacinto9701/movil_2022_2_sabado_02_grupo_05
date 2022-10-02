package com.cibertec.movil_modelo_proyecto_2022_2.vista.registra;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Categoria;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Grado;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Libro;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceCategoria;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceLibro;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LibroRegistraActivity extends NewAppCompatActivity {

    EditText txtcodigo,txttitulo,txtaños, txtserie, txtestado;
    Spinner spnCategoria;
    ArrayAdapter<String> adapter;
    ArrayList<String> categoria = new ArrayList<String>();
    Button btnRegistrar;
    ServiceLibro serviceLibro;
    ServiceCategoria serviceCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro_registra);

        txttitulo = findViewById(R.id.txtTitulo);
        txtaños = findViewById(R.id.txtAños);
        txtserie = findViewById(R.id.txtSerie);
        spnCategoria = findViewById(R.id.spnCategoria);
        adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,categoria);
        spnCategoria.setAdapter(adapter);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        serviceLibro = ConnectionRest.getConnection().create(ServiceLibro.class);
        serviceCategoria = ConnectionRest.getConnection().create(ServiceCategoria.class);
        cargarDATA();
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String titulo = txttitulo.getText().toString();
                Integer años = Integer.valueOf(txtaños.getText().toString());
                String serie = txtserie.getText().toString();
                String categoria = spnCategoria.getSelectedItem().toString();

                String idCategoria = categoria.split(":")[0];

                Categoria objCategoria = new Categoria();
                objCategoria.setIdCategoria(Integer.parseInt(idCategoria.trim()));


                Libro obj = new Libro();
                obj.setTitulo(titulo);
                obj.setAnio(años);
                obj.setSerie(serie);
                obj.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                obj.setEstado(1);
                obj.setCategoria(objCategoria);

                RegistrarLibro(obj);
            }
        });

    }

    public void cargarDATA(){
        Call<List<Categoria>> call = serviceCategoria.listaTodos();
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful()){
                    mensajeAlert("Los datos se cargaron");
                    List<Categoria> lstCategoria = response.body();
                    for (Categoria cate : lstCategoria){
                    categoria.add(cate.getIdCategoria()+":"+ cate.getDescripcion());
                    }
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable e){
                mensajeAlert("problema " +e.getMessage());
            }
        });
    }

    public void RegistrarLibro(Libro obj){
        Call<Libro> call = serviceLibro.insertarLibro(obj);
        call.enqueue(new Callback<Libro>() {
            @Override
            public void onResponse(Call<Libro> call, Response<Libro> response) {
                if (response.isSuccessful()){
                    mensajeAlert("El libro Fue Registrado Exitosamente");
                    Libro objSalida = response.body();
                }
            }

            @Override
            public void onFailure(Call<Libro> call, Throwable t) {
                mensajeAlert("Error" + t.getMessage());
            }
        });
    }

}