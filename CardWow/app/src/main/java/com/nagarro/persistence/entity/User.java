package com.nagarro.persistence.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "user")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @ColumnInfo(name = "LinkedIn")
    private String linkedlink;

    @ColumnInfo(name = "latitude")
    private double lat;

    @ColumnInfo(name = "longitude")
    private double lon;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLinkedlink() {
        return linkedlink;
    }

    public void setLinkedlink(String link){
        this.linkedlink= link;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLink() {
        return linkedlink;
    }
    public void setLink(String linkedlink) {
        this.linkedlink = linkedlink;
    }

    public double getLat(){
        return lat;
    }
    public void setLat(double lat ){
        this.lat= lat;
    }
    public double getLon(){
        return lon;
    }
    public void setLon(double lon){
        this.lon= lon;
    }


}
