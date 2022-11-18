package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.DatePickerDialog;
import android.widget.DatePicker;


public class AutorCrudFormularioActivity extends NewAppCompatActivity {

    Spinner spnGrado;
    ArrayAdapter<String> adaptador;
    ArrayList<String> grados = new ArrayList<String>();

    ServiceAutor serviceAutor;
    ServiceGrado serviceGrado;

    TextView txtTitulo;
    EditText txtNom, txtApe, txtFec, txtTel;
    Button btnEnviar, btnRegresar;

    String tipo;

    Autor obj = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor_crud_formulario);

        txtTitulo = findViewById(R.id.idCrudAutorFrmTitulo);
        txtNom = findViewById(R.id.idCrudAutorFrmNom);
        txtApe = findViewById(R.id.idCrudAutorFrmApe);
        txtFec = findViewById(R.id.idCrudAutorFrmFec);
        txtTel = findViewById(R.id.idCrudAutorFrmTel);
        btnEnviar = findViewById(R.id.idCrudAutorFrmBtnEnviar);
        btnRegresar = findViewById(R.id.idCrudAutorFrmBtnRegresar);

        adaptador = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, grados);
        spnGrado = findViewById(R.id.idCrudAutorFrmGra);
        spnGrado.setAdapter(adaptador);

        serviceAutor = ConnectionRest.getConnection().create(ServiceAutor.class);
        serviceGrado = ConnectionRest.getConnection().create(ServiceGrado.class);

        cargaGrado();

        Bundle extras = getIntent().getExtras();
        tipo = extras.getString("var_tipo");

        if (tipo.equals("REGISTRAR")){
            txtTitulo.setText("Mantenimiento Autor - REGISTRAR");
            btnEnviar.setText("REGISTRA");
        }else if (tipo.equals("ACTUALIZAR")){
            txtTitulo.setText("Mantenimiento Autor - ACTUALIZAR");
            btnEnviar.setText("ACTUALIZA");

            Autor objAutor = (Autor) extras.get("var_item");
            txtNom.setText(objAutor.getNombres());
            txtApe.setText(objAutor.getApellidos());
            txtFec.setText(objAutor.getFechaNacimiento());
            txtTel.setText(objAutor.getTelefono());
        }

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AutorCrudFormularioActivity.this, AutorCrudListaActivity.class);
                startActivity(intent);
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = txtNom.getText().toString();
                String ape = txtApe.getText().toString();
                String fnac = txtFec.getText().toString();
                String tel = txtTel.getText().toString();
                if (!nom.matches(ValidacionUtil.TEXTO)){
                    mensajeAlert("El nombre es de 2 a 20 caracteres");
                } else if (!ape.matches(ValidacionUtil.TEXTO)){
                    mensajeAlert("El nombre es de 2 a 20 caracteres");
                }else if (!fnac.matches(ValidacionUtil.FECHA)){
                    mensajeAlert("La fecha tiene formato yyyy-mm-dd");
                }else if (!tel.matches(ValidacionUtil.NUM)){
                    mensajeAlert("El numero tiene que tener 9 dígitos");
                }else{
                    String grado = spnGrado.getSelectedItem().toString();
                    String idGrado = grado.split(":")[0];

                    Grado objGra = new Grado();
                    objGra.setIdGrado(Integer.parseInt(idGrado.trim()));

                    Autor objAut = new Autor();
                    objAut.setNombres(nom);
                    objAut.setApellidos(ape);
                    objAut.setFechaNacimiento(fnac);
                    objAut.setTelefono(tel);
                    objAut.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    objAut.setEstado(1);
                    objAut.setGrado(objGra);

                    Bundle extras = getIntent().getExtras();
                    String tipo = extras.getString("var_tipo");

                    if(tipo.equals("REGISTRAR")){
                        registra(objAut);
                    }else if (tipo.equals("ACTUALIZAR")){
                        Autor obj = (Autor)  extras.get("var_item");
                        objAut.setIdAutor(obj.getIdAutor());
                        actualiza(objAut);
                    }
                }
            }
        });

        txtFec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog picker = new DatePickerDialog(AutorCrudFormularioActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                txtFec.setText(year+ "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
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
                        grados.add(obj.getIdGrado() +":"+ obj.getDescripcion());
                    }
                    Bundle extras = getIntent().getExtras();
                    String tipo = extras.getString("var_tipo");

                    if(tipo.equals("ACTUALIZAR")){

                        Autor obj= (Autor) extras.get("var_item");
                        String itemGrad = obj.getGrado().getIdGrado()+":"+ obj.getGrado().getDescripcion();

                        int posSeleccionada = -1;
                        for(int i=0; i< grados.size(); i++){
                            if (grados.get(i).equals(itemGrad)){
                                posSeleccionada = i;
                                break;
                            }
                        }
                        if (posSeleccionada != -1){
                            spnGrado.setSelection(posSeleccionada);
                        }
                    }
                    adaptador.notifyDataSetChanged();
                }else{
                    mensajeToastLong("Error al acceder al Servicio Rest >>> ");
                }
            }

            @Override
            public void onFailure(Call<List<Grado>> call, Throwable t) {
                mensajeToastLong("Error al acceder al Service Res>> " + t.getMessage());
            }
        });

    }

    public void actualiza(Autor obj){
        Call<Autor> call = serviceAutor.actualizaAutor(obj);
        call.enqueue(new Callback<Autor>() {
            @Override
            public void onResponse(Call<Autor> call, Response<Autor> response) {
                if (response.isSuccessful()){
                    Autor objSal = response.body();
                    mensajeAlert("Se actualizó el registro Autor : " +
                            "\nID >>>" + objSal.getIdAutor() +
                            "\nNombre >>> " + objSal.getNombres());
                }else{
                    mensajeToastLong("Error al acceder al Servicio Rest >>> " + response.message());
                }
            }
            @Override
            public void onFailure(Call<Autor> call, Throwable t) {
                mensajeToastLong("Error al acceder al Servicio Rest >>> " + t.getMessage());
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
                    mensajeToastLong("Error al acceder al Servicio Rest >>> " + response.message());
                }
            }
            @Override
            public void onFailure(Call<Autor> call, Throwable t) {
                mensajeToastLong("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }



}