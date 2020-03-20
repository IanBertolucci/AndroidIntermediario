package com.example.androidintermediario;


import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class EditarLivroActivity extends AppCompatActivity {
    private static final int LOADER_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_livro);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent.hasExtra("id")){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID, intent.getExtras(), loaderCallbacks);
        }else {
            finish();
        }
    }

    public void alterar(){
        EditText txtId = (EditText) findViewById(R.id.editTextId);
        EditText txtTitulo = (EditText) findViewById(R.id.editTextTitulo);
        EditText txtAutor = (EditText) findViewById(R.id.editTextAutor);

        String titulo = txtTitulo.getText().toString();
        String id = txtId.getText().toString();
        String autor = txtAutor.getText().toString();

        ContentValues values = new ContentValues();

        values.put(LivrosDbHelper.C_TITULO, titulo);
        values.put(LivrosDbHelper.C_AUTOR, autor);

        getContentResolver().update(LivrosProvider.CONTENT_URI, values, LivrosDbHelper.C_ID+"=?", new String[]{id});

        startActivity(new Intent(this, MainActivity.class));
    }

    public void delete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_menu_delete)
                .setTitle("Confirmação!")
                .setMessage("Tem certeza que deseja excluir este registro?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText txtId = (EditText) findViewById(R.id.editTextId);
                        String id = txtId.getText().toString();
                        getContentResolver().delete(LivrosProvider.CONTENT_URI, LivrosDbHelper.C_ID+"=?", new String[]{id});

                        Toast.makeText(getApplicationContext(), "Livro excluido!", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getBaseContext(), MainActivity.class));
                    }
                })
                .setNegativeButton("Não", null);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_editar_livro, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_alter:
                alterar();
                return true;
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_delete:
                delete();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            if (id == LOADER_ID){
                String idLivro = "";
                idLivro = args.getString("id");
                Uri uri = Uri.withAppendedPath(LivrosProvider.CONTENT_URI, "id/"+idLivro);
                return new CursorLoader(getApplicationContext(), uri, null, null, null, null);
            }else
                return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if (loader.getId() == LOADER_ID){
                String titulo = "";
                String id = "";
                String autor = "";

                if (data.moveToNext()){
                    id = data.getString(0);
                    titulo = data.getString(1);
                    autor = data.getString(2);
                }

                EditText txtId = (EditText) findViewById(R.id.editTextId);
                EditText txtTitulo = (EditText) findViewById(R.id.editTextTitulo);
                EditText txtAutor = (EditText) findViewById(R.id.editTextAutor);

                txtId.setText(id);
                txtTitulo.setText(titulo);
                txtAutor.setText(autor);
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };
}
