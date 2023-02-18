package com.example.proyectobadt2_kaiscervasquez.dao.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.proyectobadt2_kaiscervasquez.dao.CountryConcernedDAO;
import com.example.proyectobadt2_kaiscervasquez.dao.EarthquakeDAO;
import com.example.proyectobadt2_kaiscervasquez.entity.CountryConcerned;
import com.example.proyectobadt2_kaiscervasquez.entity.Earthquake;

@Database(entities = {Earthquake.class, CountryConcerned.class}, version = 1)
public abstract class EarthquakesDB extends RoomDatabase {

    public abstract EarthquakeDAO earthquakeDAO();
    public abstract CountryConcernedDAO countryConcernedDAO();

    private static EarthquakesDB earthquakesDB;

    public static EarthquakesDB getDatabase(Context context){
        if (earthquakesDB == null){
            earthquakesDB = Room.databaseBuilder(
                    context.getApplicationContext(),
                    EarthquakesDB.class, "earthquakesDB").allowMainThreadQueries().build();
        }
        return earthquakesDB;
    }
}
