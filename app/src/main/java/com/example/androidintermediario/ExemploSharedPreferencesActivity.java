package com.example.androidintermediario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class ExemploSharedPreferencesActivity extends AppCompatActivity {
    private TextView lblStatus;
    private EditText txtNome, txtIdade;
    private RadioButton rbMasculino, rbFeminino;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exemplo_shared_preferences);

        lblStatus = (TextView) findViewById(R.id.textViewStatus);
        txtNome = (EditText) findViewById(R.id.editTextNome);
        txtIdade = (EditText) findViewById(R.id.editTextIdade);
        rbFeminino = (RadioButton) findViewById(R.id.radioFeminino);
        rbMasculino = (RadioButton) findViewById(R.id.radioMasculino);

        findViewById(R.id.btnSalvar).setOnClickListener(clickListener);

        readPreferences();
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

        if (sexo.equals("Masculino")){
            rbMasculino.setChecked(true);
        } else rbFeminino.setChecked(true);
    }
}
