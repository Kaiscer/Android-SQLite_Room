package com.example.proyectobadt2_kaiscervasquez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyectobadt2_kaiscervasquez.dao.CountryConcernedDAO;
import com.example.proyectobadt2_kaiscervasquez.dao.EarthquakeDAO;
import com.example.proyectobadt2_kaiscervasquez.dao.db.DataSource;
import com.example.proyectobadt2_kaiscervasquez.dao.db.EarthquakesDB;
import com.example.proyectobadt2_kaiscervasquez.entity.CountryConcerned;
import com.example.proyectobadt2_kaiscervasquez.entity.Earthquake;
import com.example.proyectobadt2_kaiscervasquez.recycleutil.EventsAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnFiltersListener {

    Button btsSelectFilter, btnSee;
    TextView tvFilter;
    DataSource dataSource;
    TextView tvTitle;

    EarthquakeDAO earthquakeDAO;
    CountryConcernedDAO concernedDAO;
    EarthquakesDB earthquakesDB;
    ArrayList<Earthquake> lisEarthQK;
    RecyclerView rvEvents;
    RecyclerView.LayoutManager llm;
    EventsAdapter adapter;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvFilter = findViewById(R.id.tv_filter);
        tvTitle = findViewById(R.id.tv_title);
        // De esta manera el tvTitle no se muestra pero si ocupa espacio
        tvTitle.setVisibility(View.INVISIBLE);
        btsSelectFilter = findViewById(R.id.btn_selectFilters);
        btsSelectFilter.setOnClickListener(this);
        btnSee = findViewById(R.id.btn_see);
        btnSee.setOnClickListener(this);
        earthquakesDB = EarthquakesDB.getDatabase(this);
        earthquakeDAO = earthquakesDB.earthquakeDAO();
        concernedDAO = earthquakesDB.countryConcernedDAO();
        dataSource = new DataSource();
        checkDataBase(lisEarthQK);
        rvEvents = findViewById(R.id.rv_events);
        earthquakesDB = EarthquakesDB.getDatabase(this);
        earthquakeDAO = earthquakesDB.earthquakeDAO();
        lisEarthQK = (ArrayList<Earthquake>) earthquakeDAO.getAll();
        configRV(lisEarthQK);
        rvEvents.setVisibility(View.INVISIBLE);


    }
    private void configRV(ArrayList<Earthquake> lisEarthQK) {
        llm = new LinearLayoutManager(this);
        rvEvents.setLayoutManager(llm);
        adapter = new EventsAdapter(lisEarthQK);
        rvEvents.setAdapter(adapter);
        rvEvents.setHasFixedSize(true);

    }

    private void checkDataBase(ArrayList<Earthquake> lisEarthQK) {
        lisEarthQK = (ArrayList<Earthquake>) earthquakeDAO.getAll();
        ArrayList<CountryConcerned> listCcd = new ArrayList<>();
        if (lisEarthQK.isEmpty()){
            uploadList(lisEarthQK, listCcd);
        }
    }

    private void uploadList(ArrayList<Earthquake> lisEarthQK, ArrayList<CountryConcerned> listCcd) {
        lisEarthQK = dataSource.getListEarthQK();
        listCcd = dataSource.getListCountryCcd();
        for (Earthquake e: lisEarthQK) {
            earthquakeDAO.insert(e);
        }
        for (CountryConcerned c: listCcd) {
            concernedDAO.insert(c);
        }
        if (lisEarthQK.isEmpty() && listCcd.isEmpty()){
            Snackbar.make(findViewById(android.R.id.content),
                    R.string.error_upload, Snackbar.LENGTH_LONG).show();
            return;
        }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_selectFilters:
                showDialogFilters();
                break;
            case R.id.btn_see:
                showResults();
                break;
        }
    }

    private void showDialogFilters() {
        rvEvents.setVisibility(View.INVISIBLE);
        DialogFilters dialogFilters = new DialogFilters();
        dialogFilters.show(getSupportFragmentManager(), "DialogFilters");
    }

    private void showResults() {
        if (tvFilter.getText().toString().isEmpty())
        tvTitle.setVisibility(View.VISIBLE);
        rvEvents.setVisibility(View.VISIBLE);
    }


    @Override
    public void onFiltersMonth(String filterMonth) {

        tvFilter.setText(filterMonth);
    }

    @Override
    public void onFiltersYear(String filterYear) {
        tvFilter.setText(filterYear);
    }

    @Override
    public void onFiltersCountry(String filterCountry) {
        tvFilter.setText(filterCountry);
    }


    @Override
    public void onFilterMonthYear(@NonNull String filterMonth, String filterYear) {
        result = filterMonth.concat(" - ").concat(filterYear);
        tvFilter.setText(result);
    }


    @Override
    public void onFilterMonthCountry(@NonNull String filterMonth, String filterCountry) {
            result = filterMonth.concat(" - ").concat(filterCountry);
            tvFilter.setText(result);
    }


    @Override
    public void onFilterYearCountry(@NonNull String filterYear, String filterCountry) {

        result = filterYear.concat(" - ").concat(filterCountry);
        tvFilter.setText(result);

    }


    @Override
    public void onFilterMonthYearCountry(@NonNull String filterMonth, String filterYear, String filterCountry) {
        result = filterMonth.concat(" - ").concat(filterYear).concat(" - ").concat(filterCountry);
        tvFilter.setText(result);
    }
}