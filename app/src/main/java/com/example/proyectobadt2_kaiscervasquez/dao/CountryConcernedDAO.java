package com.example.proyectobadt2_kaiscervasquez.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.proyectobadt2_kaiscervasquez.entity.CountryConcerned;

import java.util.List;

@Dao
public interface CountryConcernedDAO {

    @Insert
    public void insert(CountryConcerned countryConcerned);


    @Query("SELECT DISTINCT Country FROM CountryConcerned ORDER BY Country")
    public List<String> getCountry();

    @Query("SELECT DISTINCT DateTime FROM CountryConcerned ORDER BY DateTime DESC")
    public List<String> getAllYear();



}

