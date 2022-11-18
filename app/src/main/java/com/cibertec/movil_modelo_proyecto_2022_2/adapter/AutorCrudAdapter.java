package com.cibertec.movil_modelo_proyecto_2022_2.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;
import java.util.List;

public class AutorCrudAdapter extends ArrayAdapter<Autor> {

    private Context context;
    private List<Autor> lista;

    public AutorCrudAdapter(@NonNull Context context, int resource, @NonNull List<Autor> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_autor_crud_item, parent, false);

        Autor obj = lista.get(position);

        TextView txtNom = row.findViewById(R.id.idCrudAutrItemNom);
        txtNom.setText(String.valueOf(obj.getNombres()));

        TextView txtApe = row.findViewById(R.id.idCrudAutrItemApe);
        txtApe.setText(String.valueOf(obj.getApellidos()));

        return row;

    }
}
