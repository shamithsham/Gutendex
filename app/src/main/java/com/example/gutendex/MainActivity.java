package com.example.gutendex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    LinearLayout Fiction,Drama,Humor,Politics,Philosophy,History,Adventure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fiction=findViewById(R.id.fiction);
        Drama=findViewById(R.id.drama);
        Humor=findViewById(R.id.humor);
        Politics=findViewById(R.id.politics);
        Philosophy=findViewById(R.id.philosophy);
        History=findViewById(R.id.history);
        Adventure=findViewById(R.id.adventure);

        Fiction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FictionActivity.class);
                intent.putExtra("category", "fiction");
                startActivity(intent);
            }
        });

        Drama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FictionActivity.class);
                intent.putExtra("category", "drama");
                startActivity(intent);
            }
        });

        Humor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FictionActivity.class);
                intent.putExtra("category", "humor");
                startActivity(intent);
            }
        });
        Politics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FictionActivity.class);
                intent.putExtra("category", "politics");
                startActivity(intent);
            }
        });

        Philosophy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FictionActivity.class);
                intent.putExtra("category", "philosophy");
                startActivity(intent);
            }
        });

        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FictionActivity.class);
                intent.putExtra("category", "history");
                startActivity(intent);
            }
        });

        Adventure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FictionActivity.class);
                intent.putExtra("category", "adventure");
                startActivity(intent);
            }
        });


    }
}
