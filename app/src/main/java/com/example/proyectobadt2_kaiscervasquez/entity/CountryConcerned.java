package com.example.proyectobadt2_kaiscervasquez.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "CountryConcerned",
        foreignKeys = @ForeignKey(entity = Earthquake.class,
                parentColumns = "DateTime", childColumns = "DateTime", onDelete = ForeignKey.CASCADE))
public class CountryConcerned {
    @PrimaryKey
    private String Country;
    private String DateTime;

    public CountryConcerned(String country, String dateTime) {
        Country = country;
        DateTime = dateTime;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }
}
