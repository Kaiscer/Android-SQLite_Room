package com.example.proyectobadt2_kaiscervasquez.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.proyectobadt2_kaiscervasquez.entity.Earthquake;

import java.util.List;

@Dao
public interface EarthquakeDAO {

    @Query("SELECT * FROM EARTHQUAKE")
    public List<Earthquake> getAll();

    @Insert
    public long insert(Earthquake earthquake);

}
