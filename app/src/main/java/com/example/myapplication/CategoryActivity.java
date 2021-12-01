package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    ArrayList<Category> catList;
    ListView catListView;
    EditText newCat;
    private RecyclerView recyclerView;
    Button addCat;
    RecyclerViewAdapter2_ForCat adapter;
    private RecyclerViewAdapter2_ForCat.RecyclerViewClickListner listner;
    private DatabaseReference reff1, reff2;
    private long maxid;
    int cCount, position1;
    AlertDialog.Builder builder;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);
        recyclerView = findViewById(R.id.listView_cat);
        catList = new ArrayList<>();
        addCat = findViewById(R.id.btn_AddCat);
        newCat = findViewById(R.id.et_AddNewCat);
        getSupportActionBar().setTitle("Categories");


        setProjects();
        setOnClickListner();
        adapter = new RecyclerViewAdapter2_ForCat(catList, listner);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        //Getting cCount value to obtain ID


        reff2 = FirebaseDatabase.getInstance().getReference().child("catCount");
        reff2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    cCount = (snapshot.getValue(Integer.class));
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });




        addCat.requestFocus();

        addCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reff1 = FirebaseDatabase.getInstance().getReference("category");


                if (TextUtils.isEmpty(newCat.getText().toString())) {
                    Toast.makeText(CategoryActivity.this, "Please enter a Category Name to Continue", Toast.LENGTH_SHORT).show();
                } else {
                    cCount = cCount +1;
                    reff2.setValue(cCount);

                    Category c = new Category();
                    c.setName(String.valueOf(newCat.getText()));
                    c.setCatID((int) cCount);

                    reff1.child(String.valueOf(cCount)).setValue(c);

                    Toast.makeText(CategoryActivity.this, "Category added", Toast.LENGTH_SHORT).show();
                }



            }
        });

        builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete category ?");
        builder.setMessage("Deleted category cannot be undone");

        builder.setPositiveButton("Delete Category", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                DatabaseReference reffc = FirebaseDatabase.getInstance().getReference("category").child(String.valueOf(catList.get(position1).getCatID()));
                reffc.removeValue();

                Toast.makeText(CategoryActivity.this, "Item Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });



    }

    private void setOnClickListner() {
        listner = new RecyclerViewAdapter2_ForCat.RecyclerViewClickListner() {
            @Override
            public void onClick(View v, int position) {
                position1 = position;
                builder.show();
            }
        };
    }


    private void setProjects() {

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("category");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                catList.clear();

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                    Category c = dataSnapshot1.getValue(Category.class);
                    catList.add(c);

                }
                adapter.notifyDataSetChanged();




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}