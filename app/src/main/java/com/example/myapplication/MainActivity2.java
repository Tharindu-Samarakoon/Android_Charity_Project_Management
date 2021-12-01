package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    private TextView name, cat, des, email, mobile, deadline, goal, min;
    private Button editDetails, removeProject;
    private ImageView imgProject;

    EditText name1;
    Spinner catergory;
    EditText email1;
    EditText contactNo1;
    EditText deadline1;
    EditText donationGoal1;
    EditText doantionMin1;
    EditText Description1;
    Button btnAddProject1;

    Project p;
    Project pr = new Project();
    long PID;
    DatabaseReference reff, reffs;
    StorageReference reffstorage;
    AlertDialog.Builder builder;
    ArrayList<String> catList;

    Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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
        imgProject = findViewById(R.id.img_project_image);

        getSupportActionBar().setTitle("Project Details");


                p = getIntent().getParcelableExtra("project");


        name.setText(String.valueOf(p.getName()));
        cat.setText(p.getCatergory());
        des.setText(p.getDescription());
        email.setText(p.getEmail());
        mobile.setText(p.getContactNo());
        deadline.setText(String.valueOf(p.getDate()));
        goal.setText(String.valueOf(p.getDonationGoal()));
        min.setText(String.valueOf(p.getMinDonation()));

        //retrieving image from url

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
                        Toast.makeText(MainActivity2.this, "uri is empty", Toast.LENGTH_SHORT).show();
                    } else {
                        //imgProject.setImageURI(uri);

//                        Picasso.get()
//                                .load(uri)
//                                .resize(50, 50)
//                                .centerCrop()
//                                .into(imgProject);
                        Picasso.get().load(uri).into(imgProject);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });




        //=====================

        PID = p.getProjectID();

        //Update project

        DialogPlus dialogPlus = DialogPlus.newDialog(this).setGravity(Gravity.CENTER).setMargin(50,0,50,0)
                .setContentHolder(new ViewHolder(R.layout.update_project)).setExpanded(false).create();
        p = getIntent().getParcelableExtra("project");

        View holderView = (LinearLayout) dialogPlus.getHolderView();

        name1 = holderView.findViewById(R.id.et_projectName);
        catergory = holderView.findViewById(R.id.sp_selectCat);
        email1 = holderView.findViewById(R.id.et_projectEmail);
        contactNo1 = holderView.findViewById(R.id.et_projectContactNo);
        deadline1 = holderView.findViewById(R.id.et_ProjectDonDeadline);
        donationGoal1 = holderView.findViewById(R.id.et_projectDonationGoal);
        doantionMin1 = holderView.findViewById(R.id.et_projectMinDonation);
        Description1 = holderView.findViewById(R.id.et_projectDescription);
        btnAddProject1 = holderView.findViewById(R.id.btn_update_project);

        catList = new ArrayList<>();
        DatabaseReference r = FirebaseDatabase.getInstance().getReference();
        r.child("category").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    String name = dataSnapshot1.child("name").getValue(String.class);
                    catList.add(name);

                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<> ( dialogPlus.getHolderView().getContext() , android.R.layout.simple_spinner_item, catList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                catergory.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnAddProject1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> map = new HashMap<>();

                //==============================================


                if (TextUtils.isEmpty(name1.getText().toString())) {

                    Toast.makeText(MainActivity2.this, "Please enter a Project name", Toast.LENGTH_SHORT).show();

//                } else if(TextUtils.isEmpty(email.getText().toString())) {
                } else if(!validateEmail()) {

                    Toast.makeText(MainActivity2.this, "Please enter a valid contact email address", Toast.LENGTH_SHORT).show();

//                } else if(TextUtils.isEmpty(contactNo.getText().toString())) {
//
//                    Toast.makeText(MainActivity.this, "Please enter a contact number", Toast.LENGTH_SHORT).show();

                } else if (!validatePhoneNumber()){
                    Toast.makeText(MainActivity2.this, "Please enter a valid contact number", Toast.LENGTH_SHORT).show();
                    return;

                    //} else if(TextUtils.isEmpty(deadline.getText().toString())) {
                } else if(!validateDeadline()) {

                    Toast.makeText(MainActivity2.this, "Please enter a valid deadline", Toast.LENGTH_SHORT).show();

                } else if(TextUtils.isEmpty(donationGoal1.getText().toString())) {

                    Toast.makeText(MainActivity2.this, "Please enter a donation goal", Toast.LENGTH_SHORT).show();

                } else if(TextUtils.isEmpty(doantionMin1.getText().toString())) {

                    Toast.makeText(MainActivity2.this, "Please enter a minimum donation amount", Toast.LENGTH_SHORT).show();

                } else if(TextUtils.isEmpty(Description1.getText().toString())) {

                    Toast.makeText(MainActivity2.this, "Please enter a project description", Toast.LENGTH_SHORT).show();

                } else {

                    //==============================================


                    String cat1 = catergory.getSelectedItem().toString();

                    map.put("contactNo", contactNo1.getText().toString());
                    map.put("date", deadline1.getText().toString());
                    map.put("catergory", cat1);
                    map.put("description", Description1.getText().toString());
                    map.put("donationGoal", Double.parseDouble(donationGoal1.getText().toString()));
                    map.put("email", email1.getText().toString());
                    map.put("minDonation", Double.parseDouble(doantionMin1.getText().toString()));
                    map.put("name", name1.getText().toString());


                    pr.setName(name1.getText().toString().trim());
                    pr.setContactNo(contactNo1.getText().toString().trim());
                    pr.setCatergory(cat1);
                    pr.setEmail(email1.getText().toString().trim());
                    pr.setDonationGoal(Double.parseDouble(donationGoal1.getText().toString()));
                    pr.setMinDonation(Double.parseDouble(doantionMin1.getText().toString()));
                    pr.setDate(deadline1.getText().toString());
                    pr.setDescription(Description1.getText().toString().trim());
                    pr.setProjectID(p.getProjectID());


                    FirebaseDatabase.getInstance().getReference("project").child(String.valueOf(p.getProjectID())).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {


                            name.setText(String.valueOf(pr.getName()));
                            cat.setText(pr.getCatergory());
                            des.setText(pr.getDescription());
                            email.setText(pr.getEmail());
                            mobile.setText(pr.getContactNo());
                            deadline.setText(String.valueOf(pr.getDate()));
                            goal.setText(String.valueOf(pr.getDonationGoal()));
                            min.setText(String.valueOf(pr.getMinDonation()));

                            p = pr;

                            dialogPlus.dismiss();
                            Toast.makeText(MainActivity2.this, "Details updated", Toast.LENGTH_SHORT).show();

                        }
                    });
                }


//
//
//                reff.child(String.valueOf(PID)).setValue(p);
//
//                Toast.makeText(MainActivity2.this, "Project added", Toast.LENGTH_SHORT).show();
//
//
////                DatabaseReference reff = FirebaseDatabase.getInstance().getReference("project");
////                reff.chi

            }
        });

        name1.setText(p.getName());
        email1.setText(p.getEmail());
        contactNo1.setText(p.getContactNo());
        deadline1.setText(p.getDate());
        donationGoal1.setText(String.valueOf(p.getDonationGoal()));
        doantionMin1.setText(String.valueOf(p.getMinDonation()));
        Description1.setText(p.getDescription());

        Button btn_update = holderView.findViewById(R.id.btn_update_project);

        editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //AlertDialog.Builder builder = new AlertDialog.Builder(this);

                dialogPlus.show();



            }
        });

        builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure?");
        builder.setMessage("Once a project is removed it cannot be undone");

        builder.setPositiveButton("Remove Project", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                deleteProject(p);
                Toast.makeText(MainActivity2.this, "Project Removed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), SearchProjects.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        removeProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder.show();

            }
        });

        }

    private void deleteProject(Project project) {

        DatabaseReference r1 = FirebaseDatabase.getInstance().getReference("project").child(String.valueOf(project.getProjectID()));
        DatabaseReference r2 = FirebaseDatabase.getInstance().getReference().child("prevProjects");

        r1.removeValue();

        r2.child(String.valueOf(project.getProjectID())).setValue(project);


    }

    //=============================validations=========================

    private boolean validatePhoneNumber(){
        String val = contactNo1.getText().toString().trim();
        String checkspaces = "\\d{10}";
        if (val.isEmpty()) {
            contactNo1.setError("Enter valid phone number");
            return false;
        } else if (!val.matches(checkspaces)) {
            contactNo1.setError("Incorrect Format");
            return false;
        } else {
            contactNo1.setError(null);
            return true;
        }
    }


    private boolean validateEmail() {
        String val = email1.getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email1.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email1.setError("Invalid Email");
            return false;
        } else {
            email1.setError(null);
            return true;
        }
    }

    private boolean validateDeadline() {
        String val = deadline1.getText().toString().trim();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);

        try {

            dateFormat.parse(val.trim());

        } catch (ParseException e) {

            return false;
        }

        return true;


    }



    //=============================validations=========================


}