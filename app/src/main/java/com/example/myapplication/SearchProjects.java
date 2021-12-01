package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchProjects extends AppCompatActivity {
    private ArrayList<Project> projectList;
    private RecyclerView recyclerView;
    EditText search;
    RecyclerViewAdapter adapter;
    private RecyclerViewAdapter.RecyclerViewClickListner listner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_projects);
        recyclerView = findViewById(R.id.RecyclerView_DisplayProjects);
        projectList = new ArrayList<>();

        getSupportActionBar().setTitle("Heart of Hope");

        setProjects();
        setOnClickListner();
        adapter = new RecyclerViewAdapter(projectList, listner);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        search = findViewById(R.id.et_search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                FilterSearchText();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void FilterSearchText() {

        String str = String.valueOf(search.getText());
        Query query = FirebaseDatabase.getInstance().getReference("project").orderByChild("name").startAt(str).endAt(str+"\uf8ff");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
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

    private void setOnClickListner() {
        listner = new RecyclerViewAdapter.RecyclerViewClickListner() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                intent.putExtra("project", projectList.get(position));
                startActivity(intent);
            }
        };
    }


//    private void setAdapter() {
//
//       RecyclerViewAdapter adapter = new RecyclerViewAdapter(projectList);
//       RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//       recyclerView.setLayoutManager(layoutManager);
//       recyclerView.setItemAnimator(new DefaultItemAnimator());
//       recyclerView.setAdapter(adapter);
//
//    }

    private void setProjects() {

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("project");
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