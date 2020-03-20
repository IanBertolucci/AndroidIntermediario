package com.example.androidintermediario;

import android.content.SearchRecentSuggestionsProvider;

public class SugestaoPesquisaProvider extends SearchRecentSuggestionsProvider {

    public static final String AUTHORITY = "com.example.androidintermediario";
    public static final int MODE = DATABASE_MODE_QUERIES;

    public SugestaoPesquisaProvider(){
        setupSuggestions(AUTHORITY, MODE);
    }
}
