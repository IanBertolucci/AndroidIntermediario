package com.example.androidintermediario;

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

import java.util.List;

public class FotosAdapter extends ArrayAdapter<Fotos> {
    List<Fotos> dados;
    Context context;
    int resource;

    public FotosAdapter(@NonNull Context context, int resource, List<Fotos> dados) {
        super(context, resource, dados);

        this.dados = dados;
        this.context = context;
        this.resource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(resource, parent, false);
        }

        TextView id = (TextView) view.findViewById(R.id.textViewIdDb);
        ImageView imagem = (ImageView) view.findViewById(R.id.imageViewItemDb);

        Fotos fotos = dados.get(position);

        id.setText(String.valueOf(fotos.getId()));
        byte[] img = fotos.getImagem();

        if (img != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            imagem.setImageBitmap(bitmap);
        }

        return view;

    }
}
