package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    DatabaseReference reff, reff2, reff3;
    long maxid;
    int pCount;
    ArrayList<String> catList;
    ArrayAdapter<String> arrayAdapter;
    private Uri imageUri;
    private ImageView projectImage;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private ProjectImage pI;
    private String imgUrl;

    EditText name;
    Spinner catergory;
    EditText email;
    EditText contactNo;
    EditText deadline;
    EditText donationGoal;
    EditText doantionMin;
    EditText Description;
    Button btnAddProject;
    Button addImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

//        EditText name = findViewById(R.id.et_projectName);
//        Spinner catergory = findViewById(R.id.sp_selectCat);
//        EditText email = findViewById(R.id.et_projectEmail);
//        EditText contactNo = findViewById(R.id.et_projectContactNo);
//        EditText deadline = findViewById(R.id.et_ProjectDonDeadline);
//        EditText donationGoal = findViewById(R.id.et_projectDonationGoal);
//        EditText doantionMin = findViewById(R.id.et_projectMinDonation);
//        EditText Description = findViewById(R.id.et_projectDescription);
//        Button btnAddProject = findViewById(R.id.btn_update_project);
//        projectImage = findViewById(R.id.projectImage);
//        Button addImage = findViewById(R.id.btn_add_project_image);

        name = findViewById(R.id.et_projectName);
        catergory = findViewById(R.id.sp_selectCat);
        email = findViewById(R.id.et_projectEmail);
        contactNo = findViewById(R.id.et_projectContactNo);
        deadline = findViewById(R.id.et_ProjectDonDeadline);
        donationGoal = findViewById(R.id.et_projectDonationGoal);
        doantionMin = findViewById(R.id.et_projectMinDonation);
        Description = findViewById(R.id.et_projectDescription);
        btnAddProject = findViewById(R.id.btn_update_project);
        projectImage = findViewById(R.id.projectImage);
        addImage = findViewById(R.id.btn_add_project_image);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        getSupportActionBar().setTitle("Add New Project");

        catList = new ArrayList<>();
        DatabaseReference r = FirebaseDatabase.getInstance().getReference();
        r.child("category").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    String name = dataSnapshot1.child("name").getValue(String.class);
                    catList.add(name);

                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, catList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                catergory.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        // getting the no of child nodes to generate the ID
//        reff = FirebaseDatabase.getInstance().getReference().child("project");
//        reff.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                if (dataSnapshot.exists()){
//                    maxid = (dataSnapshot.getChildrenCount());
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        //Adding a count value and retrieving Id from it.

        reff = FirebaseDatabase.getInstance().getReference().child("projectCount");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    pCount = (snapshot.getValue(Integer.class));
                    pCount = pCount + 1;
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        btnAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reff2 = FirebaseDatabase.getInstance().getReference().child("project");
                reff3 = FirebaseDatabase.getInstance().getReference().child("projectImages");


                if (TextUtils.isEmpty(name.getText().toString())) {

                    Toast.makeText(MainActivity.this, "Please enter a Project name", Toast.LENGTH_SHORT).show();

//                } else if(TextUtils.isEmpty(email.getText().toString())) {
                } else if(!validateEmail()) {

                    Toast.makeText(MainActivity.this, "Please enter a valid contact email address", Toast.LENGTH_SHORT).show();

//                } else if(TextUtils.isEmpty(contactNo.getText().toString())) {
//
//                    Toast.makeText(MainActivity.this, "Please enter a contact number", Toast.LENGTH_SHORT).show();

                } else if (!validatePhoneNumber()){
                    Toast.makeText(MainActivity.this, "Please enter a valid contact number", Toast.LENGTH_SHORT).show();
                    return;

                //} else if(TextUtils.isEmpty(deadline.getText().toString())) {
                } else if(!validateDeadline()) {

                    Toast.makeText(MainActivity.this, "Please enter a valid deadline", Toast.LENGTH_SHORT).show();

                } else if(TextUtils.isEmpty(donationGoal.getText().toString())) {

                    Toast.makeText(MainActivity.this, "Please enter a donation goal", Toast.LENGTH_SHORT).show();

                } else if(TextUtils.isEmpty(doantionMin.getText().toString())) {

                    Toast.makeText(MainActivity.this, "Please enter a minimum donation amount", Toast.LENGTH_SHORT).show();

                } else if(TextUtils.isEmpty(Description.getText().toString())) {

                    Toast.makeText(MainActivity.this, "Please enter a project description", Toast.LENGTH_SHORT).show();

                } else {

                    reff.setValue(pCount);

                    Project p = new Project();
                    pI = new ProjectImage();
                    int i;

                    p.setName(name.getText().toString().trim());
                    String cat = catergory.getSelectedItem().toString();
                    p.setCatergory(cat);
                    p.setContactNo(contactNo.getText().toString().trim());
                    p.setEmail(email.getText().toString().trim());
                    p.setDonationGoal(Double.parseDouble(donationGoal.getText().toString()));
                    p.setMinDonation(Double.parseDouble(doantionMin.getText().toString()));
                    p.setDate(deadline.getText().toString());
                    p.setDescription(Description.getText().toString().trim());
                    p.setProjectID(Integer.parseInt(String.valueOf(pCount)));
                    if (imageUri != null) {
                        pI.setUri(imgUrl);
                        pI.setPID(pCount);
                        reff3.child(String.valueOf(pCount)).setValue(pI);
                    }


                    reff2.child(String.valueOf(pCount)).setValue(p);


                    Toast.makeText(MainActivity.this, "Project added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), SearchProjects.class);
                    startActivity(intent);
                    finish();

                }

            }
        });


        //Add image part

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePictures();
            }
        });



    }

    private void choosePictures() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && data!=null && data.getData()!=null){
            imageUri = data.getData();
            projectImage.setImageURI(imageUri);
            uploadPicture();
        }else {
            Toast.makeText(this, "Something Went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadPicture() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image......");
        pd.show();


        final String randomKey = UUID.randomUUID().toString();

        StorageReference riversRef = storageReference.child(System.currentTimeMillis()+"."+ getFileExtension(imageUri));

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imgUrl = uri.toString();
                            }
                        });

                        Snackbar.make(findViewById(android.R.id.content), "Image uploaded", Snackbar.LENGTH_LONG).show();
                        pd.dismiss();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {

                        Toast.makeText(MainActivity.this, "Failed to upload Image", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                        pd.setMessage("Percentage:" + (int) progressPercent + "%");
                    }
                });

    }

    private String getFileExtension(Uri imgUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(imgUri));
    }


    //=============================validations=========================

    public boolean validatePhoneNumber(){
        String val = contactNo.getText().toString().trim();
        String checkspaces = "\\d{10}";
        if (val.isEmpty()) {
            contactNo.setError("Enter valid phone number");
            return false;
        } else if (!val.matches(checkspaces)) {
            contactNo.setError("Incorrect Format");
            return false;
        } else {
            contactNo.setError(null);
            return true;
        }
    }


    public boolean validateEmail() {
        String val = email.getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    public boolean validateDeadline() {
        String val = deadline.getText().toString().trim();

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

    //=============================validations=========================

    public boolean validatePhoneNumber(String date){
        String val = contactNo.getText().toString().trim();
        String checkspaces = "\\d{10}";
        if (val.isEmpty()) {
            return false;
        } else if (!val.matches(checkspaces)) {
            return false;
        } else {
            return true;
        }
    }


    public boolean validateEmail(String emailx) {

        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (emailx.isEmpty()) {
            return false;
        } else if (!emailx.matches(checkEmail)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean validateDeadline(String date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);

        try {

            dateFormat.parse(date.trim());

        } catch (ParseException e) {

            return false;
        }

        return true;


    }



    //=============================validations=========================

}