package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{
    private ArrayList<Project> projectList;
    private RecyclerViewClickListner listner;
    private DatabaseReference reffs;
    Uri uri;

    public RecyclerViewAdapter(ArrayList<Project> list, RecyclerViewClickListner listner) {
        this.listner = listner;
        this.projectList = list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nametv;
        private TextView destv;
        private ImageView img;

        public MyViewHolder(final View view) {
            super(view);
            nametv = view.findViewById(R.id.tv_rv_name);
            destv = view.findViewById(R.id.tv_rv_des);
            img = view.findViewById(R.id.img_project);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listner.onClick(view, getBindingAdapterPosition());
        }
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        String name = projectList.get(position).getName();
        String des = projectList.get(position).getDescription();
//============================================
//        reffs = FirebaseDatabase.getInstance().getReference("projectImages");
//
//        Query query = reffs.orderByChild("pid").equalTo(projectList.get(position).getProjectID());
//
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//
//                if (snapshot.exists()) {
//                    for (DataSnapshot dataSnapshot1: snapshot.getChildren()) {
//                        ProjectImage pi = dataSnapshot1.getValue(ProjectImage.class);
//                        uri = Uri.parse(pi.getUri());
//                    }
//
//                    if (uri != null) {
//                        Picasso.get().load(uri).into(holder.img);
//                    }
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });

        //===============================================

        holder.nametv.setText(name);
        holder.destv.setText(des);

    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public interface RecyclerViewClickListner{
        void onClick(View v, int position);
    }
}
