package com.example.gutendex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String[] categories = {"fiction", "drama", "humor", "politics", "philosophy", "history", "adventure"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (String category : categories) {
            int resId = getResources().getIdentifier(category, "id", getPackageName());
            LinearLayout layout = findViewById(resId);
            setupCategoryClick(layout, category);
        }
    }

    private void setupCategoryClick(LinearLayout layout, final String category) {
        layout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FictionActivity.class);
            intent.putExtra("category", category);
            startActivity(intent);
        });
    }
}
