package com.example.proyectobadt2_kaiscervasquez;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    ArrayList<Earthquake> list = new ArrayList<>();
    boolean result = true;


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
        lisEarthQK = (ArrayList<Earthquake>) earthquakeDAO.getAllEarthquake();
        configRV(lisEarthQK);
        rvEvents.setVisibility(View.INVISIBLE);
        earthquakesDB = EarthquakesDB.getDatabase(this);
        earthquakeDAO = earthquakesDB.earthquakeDAO();




    }
    private void configRV(ArrayList<Earthquake> listEarthQK) {
        llm = new LinearLayoutManager(this);
        rvEvents.setLayoutManager(llm);
        adapter = new EventsAdapter(listEarthQK);
        rvEvents.setAdapter(adapter);
        rvEvents.setHasFixedSize(true);

    }

    private void checkDataBase(ArrayList<Earthquake> lisEarthQK) {
        lisEarthQK = (ArrayList<Earthquake>) earthquakeDAO.getAllEarthquake();
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
        if (lisEarthQK.isEmpty() || listCcd.isEmpty()){
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
        DialogFilters dialogFilters = new DialogFilters();
        dialogFilters.setCancelable(false);
        dialogFilters.show(getSupportFragmentManager(), "DialogFilters");
        configRV(list);
        tvFilter.setText("----");
        hideRecycleView(View.INVISIBLE);
    }

    private void hideRecycleView(int invisible) {
        tvTitle.setVisibility(invisible);
        rvEvents.setVisibility(invisible);
    }

    private void showResults() {
       if (result && tvFilter.getText().toString().equals("----")){
             rvEvents.setVisibility(View.VISIBLE);
             tvTitle.setVisibility(View.VISIBLE);
            lisEarthQK = (ArrayList<Earthquake>) earthquakeDAO.getAllEarthquake();
            configRV(lisEarthQK);
       }else if (result || !tvFilter.getText().toString().equals("----")){
           rvEvents.setVisibility(View.VISIBLE);
           tvTitle.setVisibility(View.VISIBLE);

       }else {
           hideRecycleView(View.INVISIBLE);
       }

    }


    @Override
    public void onFiltersMonth(String month) {
        tvFilter.setText(month);
        ArrayList<Earthquake> listFilterMont = new ArrayList<>();
        listFilterMont = (ArrayList<Earthquake>) earthquakeDAO.getEarthquakeByMonth("%"+month+"%");
       if (!listFilterMont.isEmpty()){
            configRV(listFilterMont);
           hideRecycleView(View.INVISIBLE);
       }else{
           messageNoData();
           hideRecycleView(View.INVISIBLE);
           result = false;
       }
    }

    private void messageNoData() {
        configRV(list);
        Toast.makeText(this, R.string.not_data_found, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFiltersYear(String year) {
        tvFilter.setText(year);
        ArrayList<Earthquake> listFilterYear = new ArrayList<>();
        listFilterYear = (ArrayList<Earthquake>) earthquakeDAO.getEarthquakeByYear("%"+year+"%");
        configRV(listFilterYear);
        hideRecycleView(View.INVISIBLE);
    }

    @Override
    public void onFiltersCountry(String country) {
        tvFilter.setText(country);
        ArrayList<Earthquake> listFilterCountry = new ArrayList<>();
        listFilterCountry = (ArrayList<Earthquake>) earthquakeDAO.getEarthquakeByCountry("%"+country+"%");
        configRV(listFilterCountry);
        hideRecycleView(View.INVISIBLE);

    }


    @Override
    public void onFilterMonthYear(String month, String year) {
        tvFilter.setText(month + " - " + year);
        ArrayList<Earthquake> listFilterMontYear = new ArrayList<>();
        listFilterMontYear = (ArrayList<Earthquake>)
                earthquakeDAO.getEarthquakeByMonthAndYear("%"+month+"%", "%"+year+"%" );
        if (!listFilterMontYear.isEmpty()){
            configRV(listFilterMontYear);
            hideRecycleView(View.INVISIBLE);
        }else {
            hideRecycleView(View.INVISIBLE);
            messageNoData();
            result = false;
        }
    }


    @Override
    public void onFilterMonthCountry(String month, String country) {
        tvFilter.setText(month +" - "+ country);
        ArrayList<Earthquake> listFilterMontCountry = new ArrayList<>();
        listFilterMontCountry = (ArrayList<Earthquake>)
                earthquakeDAO.getEarthquakeByMonthAndCountry("%"+month+"%", "%"+country+"%" );
        if (!listFilterMontCountry.isEmpty()){
            configRV(listFilterMontCountry);
            hideRecycleView(View.INVISIBLE);
        }else {
            messageNoData();
            result = true;
        }

    }


    @Override
    public void onFilterYearCountry(String year, String country) {
        tvFilter.setText(year +" - "+ country);
        ArrayList<Earthquake> listFilterYearCountry = new ArrayList<>();
        listFilterYearCountry = (ArrayList<Earthquake>)
                earthquakeDAO.getEarthquakeByYearAndCountry("%"+year+"%", "%"+country+"%" );
        if (!listFilterYearCountry.isEmpty()){
            configRV(listFilterYearCountry);
            hideRecycleView(View.INVISIBLE);
        }else {
            messageNoData();
            result = false;
        }

    }


    @Override
    public void onFilterMonthYearCountry(String month, String year, String country) {
        tvFilter.setText(month +" - "+ year +" - "+ country);
        ArrayList<Earthquake> listFilterMontYearCountry = new ArrayList<>();
        listFilterMontYearCountry = (ArrayList<Earthquake>)
                earthquakeDAO.getEarthquakeByMonthYearCountry("%"+month+"%", "%"+year+"%", "%"+country+"%" );
        if (!listFilterMontYearCountry.isEmpty()){
            configRV(listFilterMontYearCountry);
            hideRecycleView(View.INVISIBLE);
        }else {
            messageNoData();
            result = false;
        }

    }
}