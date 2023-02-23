package com.example.proyectobadt2_kaiscervasquez.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.proyectobadt2_kaiscervasquez.entity.Earthquake;

import java.util.List;

@Dao
public interface EarthquakeDAO {



    @Query("SELECT * FROM EARTHQUAKE ORDER BY Scale DESC")
    public List<Earthquake> getAllEarthquake();

    @Insert
    public long insert(Earthquake earthquake);

   @Query("SELECT * FROM EARTHQUAKE WHERE DateTime LIKE :month ORDER BY Scale DESC")
   public List<Earthquake> getEarthquakeByMonth(String month);

   @Query("SELECT * FROM EARTHQUAKE WHERE DateTime LIKE :year ORDER BY Scale DESC")
    public List<Earthquake> getEarthquakeByYear(String year);

   @Query("SELECT * FROM EARTHQUAKE AS ERQK INNER JOIN CountryConcerned AS CC " +
           "ON ERQK.DateTime = CC.DateTime WHERE CC.Country LIKE :country ORDER BY ERQK.Scale DESC")
    public List<Earthquake> getEarthquakeByCountry(String country);

   //month - year
    @Query("SELECT * FROM EARTHQUAKE WHERE DateTime LIKE :month AND DateTime LIKE :year")
    public List<Earthquake> getEarthquakeByMonthAndYear(String month,String year);

    //month - country
    @Query("SELECT * FROM EARTHQUAKE AS ERQK INNER JOIN CountryConcerned AS CC " +
            "ON ERQK.DateTime = CC.DateTime WHERE CC.DateTime LIKE :month AND CC.Country LIKE :country ")
    public List<Earthquake> getEarthquakeByMonthAndCountry(String month,String country);

    //year - country
    @Query("SELECT * FROM EARTHQUAKE AS ERQK INNER JOIN CountryConcerned AS CC " +
            "ON ERQK.DateTime = CC.DateTime WHERE CC.DateTime LIKE :year AND CC.Country LIKE :country ")
    public List<Earthquake> getEarthquakeByYearAndCountry(String year,String country);

    //month - year - country
    @Query("SELECT * FROM EARTHQUAKE AS ERQK INNER JOIN CountryConcerned AS CC " +
            "ON ERQK.DateTime = CC.DateTime WHERE CC.DateTime LIKE :month AND CC.DateTime LIKE :year AND CC.Country LIKE :country ")
    public List<Earthquake> getEarthquakeByMonthYearCountry(String month,String year,String country);
}
