package com.example.androidintermediario;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ThreadActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnExibir;
    private ImageView imageView;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        btnExibir = (Button) findViewById(R.id.btnExibirImagem);
        btnExibir.setOnClickListener(this);

        imageView = (ImageView) findViewById(R.id.imageViewLogo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnExibirImagem:
                Runnable processo = new Runnable() {
                    @Override
                    public void run() {
                        String url = "https://pbs.twimg.com/media/ETfDdehWoAAuc4T?format=jpg&name=4096x4096";
                        try {
                            final Bitmap imagem = exibirImagem(url);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    imageView.setImageBitmap(imagem);
                                }
                            });
                        } catch (IOException e) {
                            Log.i("erro", e.getMessage());
                        }
                    }
                };
                Thread tarefa = new Thread(processo);
                tarefa.start();
                break;
        }
    }

    protected Bitmap exibirImagem(String url) throws IOException {
        Bitmap imagem = null;
        try {
            URL src = new URL(url);
            InputStream inputStream = (InputStream) src.getContent();
            imagem = BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return imagem;
    }
}
