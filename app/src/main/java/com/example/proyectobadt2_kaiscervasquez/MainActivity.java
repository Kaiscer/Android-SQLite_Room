package com.example.proyectobadt2_kaiscervasquez;

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
    CountryConcernedDAO ccDAO;
    EarthquakesDB earthquakesDB;
    ArrayList<Earthquake> lisEarthQK;
    RecyclerView rvEvents;
    RecyclerView.LayoutManager llm;
    EventsAdapter adapter;

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
        ccDAO = earthquakesDB.countryConcernedDAO();
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
        if (lisEarthQK.isEmpty()){
            uploadList(lisEarthQK);
        }
    }

    private void uploadList(ArrayList<Earthquake> lisEarthQK) {
        lisEarthQK = dataSource.getListEarthQK();
        for (Earthquake e: lisEarthQK) {
            earthquakeDAO.insert(e);
        }
        if (lisEarthQK.isEmpty()){
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
        rvEvents.setVisibility(View.GONE);
        DialogFilters dialogFilters = new DialogFilters();
        dialogFilters.show(getSupportFragmentManager(), "DialogFilters");
    }

    private void showResults() {
        tvTitle.setVisibility(View.VISIBLE);
        rvEvents.setVisibility(View.VISIBLE);
    }


    @Override
    public void onFiltersSelected(String filter) {
        tvFilter.setText(filter);
    }
}