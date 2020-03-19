package com.example.androidintermediario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExemploSharedPreferencesActivity extends AppCompatActivity {
    private TextView lblStatus;
    private EditText txtNome, txtIdade;
    private RadioButton rbMasculino, rbFeminino;
    DbHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exemplo_shared_preferences);

        lblStatus = (TextView) findViewById(R.id.textViewStatus);
        txtNome = (EditText) findViewById(R.id.editTextNome);
        txtIdade = (EditText) findViewById(R.id.editTextIdade);
        rbFeminino = (RadioButton) findViewById(R.id.radioFeminino);
        rbMasculino = (RadioButton) findViewById(R.id.radioMasculino);

        findViewById(R.id.btnSalvar).setOnClickListener(clickListenerDb);

//      readPreferences();
//      readFileInterno();
//      readFileExterno();
    }
    private View.OnClickListener clickListenerDb = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String nome = txtNome.getText().toString();
            String idade = txtIdade.getText().toString();
            String sexo = "";

            if (rbMasculino.isChecked()){
                sexo = "Masculino";
            } else if (rbFeminino.isChecked()){
                sexo = "Feminino";
            }

            salvarDb(nome, sexo, idade);
            lblStatus.setText("Status: preferências salvas no banco de dados.");
        }
    };

    private void salvarDb(String nome, String sexo, String idade) {
        try {
            dbHelper = new DbHelper(this);
            db = dbHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(dbHelper.C_NOME, nome);
            contentValues.put(dbHelper.C_SEXO, sexo);
            contentValues.put(dbHelper.C_IDADE, idade);

            try {
                db.insertOrThrow(dbHelper.TABLE, null, contentValues);
            } finally {
                db.close();
            }
        } catch (SQLException e) {
            Log.e("errorCustom123: ", e.getMessage());
        }
    }

    private View.OnClickListener clickListenerExterno = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String nome = txtNome.getText().toString();
            String idade = txtIdade.getText().toString();
            String sexo = "";

            if (rbMasculino.isChecked()){
                sexo = "Masculino";
            } else if (rbFeminino.isChecked()){
                sexo = "Feminino";
            }

            salvarExterno(nome, sexo, idade);
            lblStatus.setText("Status: preferências salvas internamente.");
        }
    };

    private void salvarExterno(String nome, String sexo, String idade){
        String dados = "";
        dados += "nome="+nome;
        dados += "\n";
        dados += "idade="+idade;
        dados += "\n";
        dados += "sexo="+sexo;
        dados += "\n";

        try {
            String estado = Environment.getExternalStorageState();
            if (estado.equals(Environment.MEDIA_MOUNTED)){
                File file = new File(getExternalFilesDir(null), "/dados.txt");
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(dados.getBytes());
                fos.close();
            }
        } catch (IOException e) {
            Log.e("errorCustom123: ", e.getMessage());
        }
    }

    private View.OnClickListener clickListenerInterno = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String nome = txtNome.getText().toString();
            String idade = txtIdade.getText().toString();
            String sexo = "";

            if (rbMasculino.isChecked()){
                sexo = "Masculino";
            } else if (rbFeminino.isChecked()){
                sexo = "Feminino";
            }

            salvarInterno(nome, sexo, idade);
            lblStatus.setText("Status: preferências salvas internamente.");
        }
    };

    private void salvarInterno(String nome, String sexo, String idade){
        String dados = "";
        dados += "nome="+nome;
        dados += "\n";
        dados += "idade="+idade;
        dados += "\n";
        dados += "sexo="+sexo;
        dados += "\n";

        try {
            FileOutputStream fos = openFileOutput("dados.txt", MODE_PRIVATE);
            fos.write(dados.getBytes());
            fos.close();
        } catch (IOException e) {
            Log.e("errorCustom123: ", e.getMessage());
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String nome = txtNome.getText().toString();
            String idade = txtIdade.getText().toString();
            String sexo = "";

            if (rbMasculino.isChecked()){
                sexo = "Masculino";
            } else if (rbFeminino.isChecked()){
                sexo = "Feminino";
            }

            SharedPreferences filePreferences = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = filePreferences.edit();
            editor.putString("nome", nome);
            editor.putString("sexo", sexo);
            editor.putString("idade", idade);
            editor.commit();

            lblStatus.setText("Status: preferências salvas com sucesso.");
        }
    };

    private void readPreferences(){
        SharedPreferences filePreferences = getPreferences(MODE_PRIVATE);

        String nome = filePreferences.getString("nome", "");
        String idade = filePreferences.getString("idade", "");
        String sexo = filePreferences.getString("sexo", "Masculino");

        txtNome.setText(nome);
        txtIdade.setText(idade);

        if (sexo.contains("Masculino")){
            rbMasculino.setChecked(true);
        } else rbFeminino.setChecked(true);
    }

    private void readFileInterno(){
        String nome = "";
        String idade = "";
        String sexo = "Masculino";

        try {
            File dir = getFilesDir();
            File file = new File(dir+"/dados.txt");

            if (file.exists()){
                FileInputStream fis = openFileInput("dados.txt");
                byte[] buffer = new byte[(int) file.length()];

                while (fis.read(buffer) != -1){
                    String texto = new String(buffer);

                    if (texto.contains("nome")){
                        int index = texto.indexOf("=");
                        int indexFinal = texto.indexOf("\n");
                        nome = texto.substring(index+1, indexFinal);
                        texto = texto.substring(indexFinal+1);
                    }

                    if (texto.contains("idade")){
                        int index = texto.indexOf("=");
                        int indexFinal = texto.indexOf("\n");
                        idade = texto.substring(index+1, indexFinal);
                        texto = texto.substring(indexFinal+1);
                    }

                    if (texto.contains("sexo")){
                        int index = texto.indexOf("=");
                        sexo = texto.substring(index+1);
                    }
                }
            }
        } catch (IOException e) {
            Log.e("errorCustom123: ", e.getMessage());
        }

        txtNome.setText(nome);
        txtIdade.setText(idade);

        if (sexo.contains("Masculino")){
            rbMasculino.setChecked(true);
        } else rbFeminino.setChecked(true);
    }


    private void readFileExterno() {
        String nome = "";
        String idade = "";
        String sexo = "Masculino";

        try {
            String estado = Environment.getExternalStorageState();
            if (estado.equals(Environment.MEDIA_MOUNTED)) {
                File dir = getExternalFilesDir(null);
                File file = new File(dir + "/dados.txt");

                if (file.exists()) {
                    FileInputStream fis = new FileInputStream(file);
                    byte[] buffer = new byte[(int) file.length()];

                    while (fis.read(buffer) != -1) {
                        String texto = new String(buffer);

                        if (texto.contains("nome")) {
                            int index = texto.indexOf("=");
                            int indexFinal = texto.indexOf("\n");
                            nome = texto.substring(index + 1, indexFinal);
                            texto = texto.substring(indexFinal + 1);
                        }

                        if (texto.contains("idade")) {
                            int index = texto.indexOf("=");
                            int indexFinal = texto.indexOf("\n");
                            idade = texto.substring(index + 1, indexFinal);
                            texto = texto.substring(indexFinal + 1);
                        }

                        if (texto.contains("sexo")) {
                            int index = texto.indexOf("=");
                            sexo = texto.substring(index + 1);
                        }
                    }
                }
            }
        } catch (IOException e) {
            Log.e("errorCustom123: ", e.getMessage());
        }

        txtNome.setText(nome);
        txtIdade.setText(idade);

        if (sexo.contains("Masculino")){
            rbMasculino.setChecked(true);
        } else rbFeminino.setChecked(true);
    }
}
