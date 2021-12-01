package com.example.myapplication;

import android.net.Uri;

public class ProjectImage {

    private String uri;
    private int PID;

    public ProjectImage() {
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public ProjectImage(String uri, int PID) {
        this.uri = uri;
        this.PID = PID;
    }
}
