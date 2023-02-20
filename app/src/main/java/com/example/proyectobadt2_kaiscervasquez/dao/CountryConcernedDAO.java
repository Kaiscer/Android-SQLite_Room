package com.example.proyectobadt2_kaiscervasquez.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.proyectobadt2_kaiscervasquez.entity.CountryConcerned;

@Dao
public interface CountryConcernedDAO {

    @Insert
    void insert(CountryConcerned countryConcerned);
}
