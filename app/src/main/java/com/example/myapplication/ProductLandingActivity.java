package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProductLandingActivity extends AppCompatActivity {

    Button search, create, cat, prevProjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_landing);

        search = findViewById(R.id.btn_Projects);
        create = findViewById(R.id.btn_add_new_project);
        cat = findViewById(R.id.btn_categories);
        prevProjects = findViewById(R.id.btn_prev_projects);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SearchProjects.class);
                startActivity(i);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CategoryActivity.class);
                startActivity(i);
            }
        });

        prevProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), prev_projects.class);
                startActivity(i);
            }
        });


    }
}