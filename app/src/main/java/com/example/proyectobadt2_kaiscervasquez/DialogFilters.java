package com.example.proyectobadt2_kaiscervasquez;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.proyectobadt2_kaiscervasquez.dao.CountryConcernedDAO;
import com.example.proyectobadt2_kaiscervasquez.dao.db.DataSource;
import com.example.proyectobadt2_kaiscervasquez.dao.db.EarthquakesDB;
import com.example.proyectobadt2_kaiscervasquez.entity.CountryConcerned;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DialogFilters extends DialogFragment {

    OnFiltersListener onListener;

    Spinner spnMonth,spnYear,spnCountry;

    DataSource dataSource;
    EarthquakesDB earthquakesDB;
    CountryConcernedDAO countryConcernedDAO;
    ArrayList<String> lisCountry;
    ArrayList<String> listYear;
    String month;
    String year ;
    String country;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_filters, null);
        spnMonth = view.findViewById(R.id.spn_filterMonth);
        spnYear = view.findViewById(R.id.spn_filterYear);
        spnCountry = view.findViewById(R.id.spn_filterCountry);
        dataSource = new DataSource();
        earthquakesDB = EarthquakesDB.getDatabase(getContext());
        countryConcernedDAO = earthquakesDB.countryConcernedDAO();
        configSPN();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        builder.setTitle(R.string.filters)
                .setPositiveButton(R.string.btn_accept,null)
                .setNegativeButton(R.string.btn_cancel, null);


        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        dialog.setOnShowListener(dialogInterface ->{
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(btn ->{
                dialog.dismiss();
            });
        });

        dialog.setOnShowListener(dialogInterface ->{
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(btn ->{
                selectedFilter(dialog, btn);
            });
        });


        return dialog;
    }

    private void configSPN() {
        lisCountry = (ArrayList<String>) countryConcernedDAO.getCountry();
        lisCountry.add(0,"----");
        ArrayAdapter<String> adapterSpn = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, lisCountry);
        adapterSpn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCountry.setAdapter(adapterSpn);

        listYear = (ArrayList<String>) countryConcernedDAO.getAllYear();
        Pattern pattern = Pattern.compile("\\d{4}");
       ArrayList<String> newFormat = new ArrayList<>();
        for (String year : listYear){
            Matcher matcher = pattern.matcher(year);
            if (matcher.find()){
                year = matcher.group();
                newFormat.add(year);
                newFormat.sort(String::compareTo);
            }
        }
        newFormat.add(0,"----");
        ArrayAdapter<String> adapterSpnYear = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, newFormat);
        adapterSpnYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnYear.setAdapter(adapterSpnYear);
    }

    private void selectedFilter(AlertDialog dialog, View btn) {
        if (spnMonth.getSelectedItemPosition() != 0){
            month = spnMonth.getSelectedItem().toString();
            onListener.onFiltersMonth(month);
            dialog.dismiss();
            if (spnMonth.getSelectedItemPosition() != 0 && spnYear.getSelectedItemPosition() != 0 &&
                    spnCountry.getSelectedItemPosition() == 0){
                month = spnMonth.getSelectedItem().toString();
                year = spnYear.getSelectedItem().toString();
                onListener.onFilterMonthYear(month,year);
                dialog.dismiss();
            }else if (spnMonth.getSelectedItemPosition() != 0 &&
                    spnYear.getSelectedItemPosition() == 0 &&
                    spnCountry.getSelectedItemPosition() != 0){
                month = spnMonth.getSelectedItem().toString();
                country = spnCountry.getSelectedItem().toString();
                onListener.onFilterMonthCountry(month,country);
                dialog.dismiss();
            } else if (spnMonth.getSelectedItemPosition() != 0 &&
                    spnYear.getSelectedItemPosition() != 0 &&
                    spnCountry.getSelectedItemPosition() != 0){
                month = spnMonth.getSelectedItem().toString();
                year = spnYear.getSelectedItem().toString();
                country = spnCountry.getSelectedItem().toString();
                onListener.onFilterMonthYearCountry(month,year,country);
                dialog.dismiss();
            }

        } else if (spnYear.getSelectedItemPosition() != 0) {
            year = spnYear.getSelectedItem().toString();
            onListener.onFiltersYear(year);
            dialog.dismiss();
            if (spnYear.getSelectedItemPosition() != 0 && spnCountry.getSelectedItemPosition() != 0){
                year = spnYear.getSelectedItem().toString();
                country = spnCountry.getSelectedItem().toString();
                onListener.onFilterYearCountry(year,country);
                dialog.dismiss();
            }

        } else if (spnCountry.getSelectedItemPosition() != 0) {
            country = spnCountry.getSelectedItem().toString();
            onListener.onFiltersCountry(country);
            dialog.dismiss();
        }  else {
            Snackbar.make(btn, R.string.errorFilter, Snackbar.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFiltersListener){
            onListener = (OnFiltersListener) context;
        }else {
            throw new RuntimeException(context.toString() + getString(R.string.errorOnAttach));
        }
    }

    public void onDetach() {
        if (onListener != null){
            onListener = null;
        }
        super.onDetach();
    }
}
