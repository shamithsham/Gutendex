package com.example.gutendex;

import android.net.Uri;
import android.os.Bundle;
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
import java.util.List;

public class FictionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private BookAdapter adapter;
    private List<BookModel> bookList;
    private RequestQueue requestQueue;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiction);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        findViewById(R.id.back_button).setOnClickListener(v -> finish());

        requestQueue = Volley.newRequestQueue(this);

        bookList = new ArrayList<>();
        adapter = new BookAdapter(bookList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);

        category = getIntent().getStringExtra("category");

        fetchBooks(category, null);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchBooks(category, query);  // Fetch from API using search query
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    private void fetchBooks(String category, String query) {
        bookList.clear();
        adapter.notifyDataSetChanged();

        String url = "http://skunkworks.ignitesol.com:8000/books/?topic=" + category;
        if (query != null && !query.trim().isEmpty()) {
            url += "&search=" + Uri.encode(query);
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
                            BookModel book = new BookModel(title, authors, imageUrl, formats); // Pass full formats

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
