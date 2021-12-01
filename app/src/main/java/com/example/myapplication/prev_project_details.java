package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class prev_project_details extends AppCompatActivity {

    private TextView name, cat, des, email, mobile, deadline, goal, min;
    private Button editDetails, removeProject;
    Project p;
    DatabaseReference reffs;
    StorageReference reffstorage;
    Uri uri;
    ImageView imageuri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prev_project_details);


        name = findViewById(R.id.tv_display_name);
        cat = findViewById(R.id.tv_display_cat);
        des = findViewById(R.id.tv_display_description);
        email = findViewById(R.id.tv_display_contactEmail);
        mobile = findViewById(R.id.tv_display_contactNo);
        deadline = findViewById(R.id.tv_display_deadline);
        goal = findViewById(R.id.tv_display_goal);
        min = findViewById(R.id.tv_display_MinDonation);
        editDetails = findViewById(R.id.btn_updatePage);
        removeProject = findViewById(R.id.btn_delete);
        imageuri = findViewById(R.id.img_project_image);

        getSupportActionBar().setTitle("Project Details");


        p = getIntent().getParcelableExtra("project");



        name.setText(String.valueOf(p.getName()));
        cat.setText(p.getCatergory());
        des.setText(p.getDescription());
        email.setText(p.getEmail());
        mobile.setText(p.getContactNo());
        deadline.setText(String.valueOf(p.getDate()));
        goal.setText("Rs."+String.valueOf(p.getDonationGoal()));
        min.setText("Rs."+String.valueOf(p.getMinDonation()));


        reffs = FirebaseDatabase.getInstance().getReference("projectImages");
        reffstorage = FirebaseStorage.getInstance().getReference("images/");

        Query query = reffs.orderByChild("pid").equalTo(p.getProjectID());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot1: snapshot.getChildren()){

                        ProjectImage pi = dataSnapshot1.getValue(ProjectImage.class);
                        uri = Uri.parse(pi.getUri());
                    }
                    if (uri == null){
                        Toast.makeText(prev_project_details.this, "uri is empty", Toast.LENGTH_SHORT).show();
                    } else {
                        //imgProject.setImageURI(uri);

//                        Picasso.get()
//                                .load(uri)
//                                .resize(50, 50)
//                                .centerCrop()
//                                .into(imgProject);
                        Picasso.get().load(uri).into(imageuri);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}