package com.example.androidintermediario;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PesquisaActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);

        String[] estados = {"Acre","Alagoas","Amapá","Amazonas","Bahia","Ceará","Distrito Federal","Espírito Santo","Goiás","Maranhão","Mato Grosso","Mato Grosso do Sul","Minas Gerais","Paraná","Paraíba","Pará","Pernambuco","Piauí","Rio de Janeiro","Rio Grande do Norte","Rio Grande do Sul","Rondonia","Roraima","Santa Catarina","Sergipe","São Paulo","Tocantins"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, estados);
        ListView listView = findViewById(R.id.listViewResultado);
        listView.setAdapter(adapter);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    public void handleIntent(Intent intent){
        if (Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);

            SearchRecentSuggestions searchRecentSuggestions;
            searchRecentSuggestions = new SearchRecentSuggestions(this, SugestaoPesquisaProvider.AUTHORITY, SugestaoPesquisaProvider.MODE);
            searchRecentSuggestions.saveRecentQuery(query, null);

            pesquisar(query);
        }
    }

    private void pesquisar(String query){
        adapter.getFilter().filter(query);
        adapter.notifyDataSetChanged();
    }
}
