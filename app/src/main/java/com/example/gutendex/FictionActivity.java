package com.example.gutendex;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FictionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private Spinner languageSpinner;
    private BookAdapter adapter;
    private List<BookModel> bookList;
    private RequestQueue requestQueue;
    private String category;
    private String selectedLanguage = "en"; // Default English

    private final Map<String, String> languageMap = new LinkedHashMap<String, String>() {{
        put("English", "en");
        put("French", "fr");
        put("German", "de");
        put("Spanish", "es");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiction);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        languageSpinner = findViewById(R.id.languageSpinner);
        findViewById(R.id.back_button).setOnClickListener(v -> finish());

        category = getIntent().getStringExtra("category");
        ((TextView) findViewById(R.id.title)).setText(category.toUpperCase());

        bookList = new ArrayList<>();
        adapter = new BookAdapter(bookList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);

        setupLanguageSpinner();
        fetchBooks(category, null, selectedLanguage);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchBooks(category, query, selectedLanguage);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void setupLanguageSpinner() {
        List<String> languageNames = new ArrayList<>(languageMap.keySet());

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                languageNames
        );
        languageSpinner.setAdapter(spinnerAdapter);

        languageSpinner.setSelection(languageNames.indexOf("English")); // Default

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLanguage = languageMap.get(languageNames.get(position));
                fetchBooks(category, searchView.getQuery().toString(), selectedLanguage);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void fetchBooks(String category, String query, String language) {
        bookList.clear();
        adapter.notifyDataSetChanged();

        String url = "http://skunkworks.ignitesol.com:8000/books/?topic=" + category
                + "&languages=" + language;

        if (query != null && !query.trim().isEmpty()) {
            url += "&search=" + Uri.encode(query.trim());
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");

                        for (int i = 0; i < results.length(); i++) {
                            JSONObject bookObj = results.getJSONObject(i);
                            String title = bookObj.getString("title");

                            JSONArray authorsArray = bookObj.getJSONArray("authors");
                            List<String> authors = new ArrayList<>();
                            for (int j = 0; j < authorsArray.length(); j++) {
                                JSONObject authorObj = authorsArray.getJSONObject(j);
                                authors.add(authorObj.getString("name"));
                            }

                            JSONObject formats = bookObj.getJSONObject("formats");
                            String imageUrl = formats.optString("image/jpeg", "");

                            BookModel book = new BookModel(title, authors, imageUrl, formats);
                            bookList.add(book);
                        }

                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Failed to load books", Toast.LENGTH_SHORT).show();
                });

        requestQueue.add(request);
    }
}
