package com.example.proyectobadt2_kaiscervasquez.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "EARTHQUAKE",indices = {@Index(value = {"DeviceName"}, unique = true)})    //Unique index
public class Earthquake {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "DateTime")
    private String DateTime;

    @ColumnInfo(name = "Scale")
    private double Scale;

    @ColumnInfo(name = "DeviceName")
    private String DeviceName;

    @ColumnInfo(name = "Location")
    private String Location;

    @ColumnInfo(name = "Coordinates")
    private String Coordinates;

    @ColumnInfo(name = "Dead")
     private String Dead;

    public Earthquake() {

    }

    public Earthquake(String dateTime, double scale, String deviceName, String location, String coordinates, String dead) {
        this.DateTime = dateTime;
        if (scale < 0) {
            this.Scale = 0;
        } else if (scale > 10) {
            this.Scale = 10;
        } else {
            this.Scale = scale;
        }
        this.DeviceName = deviceName;
        this.Location = location;
        this.Coordinates = coordinates;
        this.Dead = dead;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public double getScale() {
        return Scale;
    }

    public void setScale(double scale) {
        Scale = scale;
    }

    public String getDeviceName() {
        return DeviceName;
    }

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getCoordinates() {
        return Coordinates;
    }

    public void setCoordinates(String coordinates) {
        Coordinates = coordinates;
    }

    public String getDead() {
        return Dead;
    }

    public void setDead(String dead) {
        Dead = dead;
    }
}
