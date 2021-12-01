package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Project implements Parcelable {

    private int projectID;
    private String name;
    private String email;
    private String contactNo;
    private String catergory;
    private double minDonation;
    private String description;
    private String date;
    private double donationGoal;

    public Project(){};

    protected Project(Parcel in) {
        projectID = in.readInt();
        name = in.readString();
        email = in.readString();
        contactNo = in.readString();
        catergory = in.readString();
        minDonation = in.readDouble();
        description = in.readString();
        date = in.readString();
        donationGoal = in.readDouble();
    }

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }


    public int getProjectID() {
        return projectID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getCatergory() {
        return catergory;
    }

    public double getDonationGoal() {
        return donationGoal;
    }

    public double getMinDonation() {
        return minDonation;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public void setCatergory(String catergory) {
        this.catergory = catergory;
    }

    public void setDonationGoal(double donationGoal) {
        this.donationGoal = donationGoal;
    }

    public void setMinDonation(double minDonation) {
        this.minDonation = minDonation;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(projectID);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(contactNo);
        parcel.writeString(catergory);
        parcel.writeDouble(minDonation);
        parcel.writeString(description);
        parcel.writeString(date);
        parcel.writeDouble(donationGoal);
    }
}
