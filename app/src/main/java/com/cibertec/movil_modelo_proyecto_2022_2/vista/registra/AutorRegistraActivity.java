package com.cibertec.movil_modelo_proyecto_2022_2.vista.registra;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Grado;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceAutor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceGrado;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutorRegistraActivity extends NewAppCompatActivity {

    Button btnRegistrar;
    EditText txtNom, txtApe, txtFecNac, txtTel;
    Spinner spnGrado;
    ArrayAdapter<String> adaptador;
    ArrayList<String> grados = new ArrayList<String>();

    ServiceAutor serviceAutor;
    ServiceGrado serviceGrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor_registra);

        serviceGrado = ConnectionRest.getConnection().create(ServiceGrado.class);
        serviceAutor = ConnectionRest.getConnection().create(ServiceAutor.class);

        //Para el adapatador
        adaptador= new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, grados);
        spnGrado = findViewById(R.id.spnRegAutGra);
        spnGrado.setAdapter(adaptador);

        cargaGrado();

        txtNom = findViewById(R.id.txtRegAutNom);
        txtApe = findViewById(R.id.txtRegAutApe);
        txtFecNac = findViewById(R.id.txtRegAutFecNac);
        txtTel = findViewById(R.id.txtRegAutTel);
        btnRegistrar = findViewById(R.id.btnRegAutEnv);

       btnRegistrar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String nom = txtNom.getText().toString();
               String ape = txtApe.getText().toString();
               String fnac = txtFecNac.getText().toString();
               String tel = txtTel.getText().toString();
               if (!nom.matches(ValidacionUtil.TEXTO)){
                   mensajeToast("El nombre es de 2 a 20 caracteres");
               } else if (!ape.matches(ValidacionUtil.TEXTO)){
                   mensajeToast("El nombre es de 2 a 20 caracteres");
               }else if (!fnac.matches(ValidacionUtil.FECHA)){
                   mensajeToast("La fecha tiene formato yyyy-mm-dd");
               }else if (!tel.matches(ValidacionUtil.NUM)){
                   mensajeToast("El numero tiene que tener 11 dígitos");
               }else{
                   String grado = spnGrado.getSelectedItem().toString();
                   String idGrado = grado.split(":")[0];

                   Grado objGra = new Grado();
                   objGra.setIdGrado(Integer.parseInt(idGrado));

                   Autor objAut = new Autor();
                   objAut.setNombres(nom);
                   objAut.setApellidos(ape);
                   objAut.setFechaNacimiento(fnac);
                   objAut.setTelefono(tel);
                   objAut.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                   objAut.setEstado(1);
                   objAut.setGrado(objGra);

                   registra(objAut);
               }
           }
       });

    }

    public void registra(Autor obj){
        Call<Autor> call = serviceAutor.insertaAutor(obj);
        call.enqueue(new Callback<Autor>() {
            @Override
            public void onResponse(Call<Autor> call, Response<Autor> response) {
                if (response.isSuccessful()){
                    Autor objSal = response.body();
                    mensajeAlert("Se registró un Autor : " +
                            "\nID >>>" + objSal.getIdAutor() +
                            "\nNombre >>> " + objSal.getNombres());
                }else{
                    mensajeToast("Error al acceder al Servicio Rest >>> " + response.message());
                }
            }
            @Override
            public void onFailure(Call<Autor> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }


    public void cargaGrado(){
        Call<List<Grado>> call = serviceGrado.Todos();
        call.enqueue(new Callback<List<Grado>>() {
            @Override
            public void onResponse(Call<List<Grado>> call, Response<List<Grado>> response) {
                if (response.isSuccessful()){
                    List<Grado> lstGrados =  response.body();
                    for(Grado obj: lstGrados){
                        grados.add(obj.getIdGrado() +" : "+ obj.getDescripcion());
                    }
                    adaptador.notifyDataSetChanged();
                }else{
                    mensajeToast("Error al acceder al Servicio Rest >>> ");
                }
            }

            @Override
            public void onFailure(Call<List<Grado>> call, Throwable t) {
                mensajeToast("Error al acceder al Service Res>> " + t.getMessage());
            }
        });
    }


    public void mensajeToast(String mensaje){
        Toast toast1 =  Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG);
        toast1.show();
    }

    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }


}