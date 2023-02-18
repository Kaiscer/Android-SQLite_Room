package com.example.proyectobadt2_kaiscervasquez.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "Earthquake",indices = {@Index(value = {"DeviceTime"}, unique = true)})    //Unique index
public class Earthquake {
    @PrimaryKey
    @ColumnInfo(name = "DateTime")
    private String DateTime;

    @ColumnInfo(name = "DeviceName")
    private String DeviceName;

    @ColumnInfo(name = "Scale")
    private double Scale;

    @ColumnInfo(name = "Coordinates")
    private double Coordinates;

    @ColumnInfo(name = "Location")
    private String Location;

    @ColumnInfo(name = "Dead")
     private int Dead;

    public Earthquake(String DateTime, String DeviceName, double Scale, double Coordinates, String Location, int Dead) {
        this.DateTime = DateTime;
        this.DeviceName = DeviceName;
        if (Scale < 0){
            this.Scale = 0;
        } else if (Scale > 10){
            this.Scale = 10;
        } else {
            this.Scale = Scale;
        }
        this.Coordinates = Coordinates;
        this.Location = Location;
        this.Dead = Dead;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getDeviceName() {
        return DeviceName;
    }

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }

    public double getScale() {
        return Scale;
    }

    public void setScale(double scale) {
        Scale = scale;
    }

    public double getCoordinates() {
        return Coordinates;
    }

    public void setCoordinates(double coordinates) {
        Coordinates = coordinates;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public int getDead() {
        return Dead;
    }

    public void setDead(int dead) {
        Dead = dead;
    }
}
