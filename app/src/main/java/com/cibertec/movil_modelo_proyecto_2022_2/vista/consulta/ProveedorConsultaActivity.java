package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.ProveedorAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Proveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceProveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProveedorConsultaActivity extends NewAppCompatActivity {

    EditText txtNombre;
    Button btnFiltrar;

    ListView lstConsultaProveedor;
    ArrayList<Proveedor> data = new ArrayList<Proveedor>();
    ProveedorAdapter adaptador;
    ServiceProveedor api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_consulta);

        lstConsultaProveedor = findViewById(R.id.idConsProvtListView);
        adaptador = new ProveedorAdapter(this, R.layout.activity_proveedor_consulta_item, data);
        lstConsultaProveedor.setAdapter(adaptador);
        txtNombre = findViewById(R.id.txtRegProvRS);

        api = ConnectionRest.getConnection().create(ServiceProveedor.class);

        btnFiltrar = findViewById(R.id.btnConsListarProv);
        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filro = txtNombre.getText().toString();
                consulta(filro);

            }
        });

    }


    public void consulta (String filtro) {
        Call<List<Proveedor>> call = api.listaPorRazonSocial(filtro);
        call.enqueue(new Callback<List<Proveedor>>() {
            @Override
            public void onResponse(Call<List<Proveedor>> call, Response<List<Proveedor>> response) {
                if (response.isSuccessful()){
                    List<Proveedor> lstSalida =response.body();
                    data.clear();
                    data.addAll(lstSalida);
                    adaptador.notifyDataSetChanged();


                }
            }

            @Override
            public void onFailure(Call<List<Proveedor>> call, Throwable t) {

            }
        });







    }





 }