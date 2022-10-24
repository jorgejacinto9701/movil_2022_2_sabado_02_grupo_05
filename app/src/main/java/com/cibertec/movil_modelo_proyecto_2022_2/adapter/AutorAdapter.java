package com.cibertec.movil_modelo_proyecto_2022_2.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class AutorAdapter extends ArrayAdapter<Autor>  {

    private Context context;
    private List<Autor> lista;

    public AutorAdapter(@NonNull Context context, int resource, @NonNull List<Autor> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_autor_consulta_item, parent, false);

        Autor obj = lista.get(position);

        TextView txtNom = row.findViewById(R.id.idAutorItemNom);
        txtNom.setText(String.valueOf(obj.getNombres()));

        TextView txtApe = row.findViewById(R.id.idAutorItemApe);
        txtApe.setText(String.valueOf(obj.getApellidos()));

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String ruta ;
                    if (obj.getIdAutor() == 176){
                        ruta = "https://i.postimg.cc/nzbH3BgT/imagen1.jpg";
                    }else if (obj.getIdAutor() == 2){
                        ruta = "https://i.postimg.cc/RZYCnjTk/imagen2.jpg";
                    }else if (obj.getIdAutor() == 3){
                        ruta = "https://i.postimg.cc/Hknsnpb0/imagen3.jpg";
                    }else if (obj.getIdAutor() == 177){
                        ruta = "https://i.postimg.cc/pdnWQqGp/imagen4.jpg";
                    }else{
                        ruta = "https://i.postimg.cc/qvk5PmNP/no-disponible.png";
                    }

                    URL rutaImagen  = new URL(ruta);
                    InputStream is = new BufferedInputStream(rutaImagen.openStream());
                    Bitmap b = BitmapFactory.decodeStream(is);
                    ImageView vista = row.findViewById(R.id.idAutorItemImagen);
                    vista.setImageBitmap(b);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        return row;

    }
}
