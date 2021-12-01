package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class prev_projects extends AppCompatActivity {

    private ArrayList<Project> projectList;
    private RecyclerView recyclerView;
    //EditText search;
    RecyclerViewAdapter adapter;
    private RecyclerViewAdapter.RecyclerViewClickListner listner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prev_projects);

        recyclerView = findViewById(R.id.recyclerView_prevProjects);
        projectList = new ArrayList<>();

        getSupportActionBar().setTitle("Previous Projects");

        setProjects();
        setOnClickListner();
        adapter = new RecyclerViewAdapter(projectList, listner);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        //search = findViewById(R.id.et_search);

//        search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                FilterSearchText();
//                Toast.makeText(prev_projects.this, "TextChange Works", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

    }

//    private void FilterSearchText() {
//
//        String str = String.valueOf(search.getText());
//        Query query = FirebaseDatabase.getInstance().getReference("prevProjects").orderByChild("name").startAt(str).endAt(str+"\uf8ff");
//
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                projectList.clear();
//
//                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
//
//
//                    Project project = dataSnapshot1.getValue(Project.class);
//                    projectList.add(project);
//
//                }
//                adapter.notifyDataSetChanged();
//
//
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }

    private void setOnClickListner() {
        listner = new RecyclerViewAdapter.RecyclerViewClickListner() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), prev_project_details.class);
                intent.putExtra("project", projectList.get(position));
                startActivity(intent);
            }
        };
    }


//    private void setAdapter() {
//
//       RecyclerViewAdapter adapter = new RecyclerViewAdapter(projectList, th);
//       RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//       recyclerView.setLayoutManager(layoutManager);
//       recyclerView.setItemAnimator(new DefaultItemAnimator());
//       recyclerView.setAdapter(adapter);
//
//    }

    private void setProjects() {

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("prevProjects");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                projectList.clear();

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                    Project project = dataSnapshot1.getValue(Project.class);
                    projectList.add(project);

                }
                adapter.notifyDataSetChanged();




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}