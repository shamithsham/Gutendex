package com.example.gutendex;

import org.json.JSONObject;
import java.util.List;


public class BookModel {
    private String title;
    private List<String> authors;
    private String imageUrl;

    private JSONObject formats;

    public BookModel(String title, List<String> authors, String imageUrl,JSONObject formats) {
        this.title = title;
        this.authors = authors;
        this.imageUrl = imageUrl;
        this.formats = formats;
    }

    public String getTitle() {
        return title;
    }
    public List<String> getAuthors() {
        return authors;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public JSONObject getFormats() {
        return formats;
    }
}
