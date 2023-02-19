package com.example.proyectobadt2_kaiscervasquez.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "CountryConcerned",
        foreignKeys = @ForeignKey(entity = Earthquake.class,
                parentColumns = "DateTime", childColumns = "DateTime", onDelete = ForeignKey.CASCADE),
        primaryKeys = {"DateTime", "Country"})
public class CountryConcerned {

    @NonNull
    private String DateTime;

    @NonNull
    private String Country;

    public CountryConcerned(String dateTime, String country) {
        this.DateTime = dateTime;
        this.Country = country;
    }

    public CountryConcerned() {
    }

    public String getDateTime() {

        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }
}
