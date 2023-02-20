package com.example.proyectobadt2_kaiscervasquez;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;

public class DialogFilters extends DialogFragment {

    OnFiltersListener onListener;

    Spinner spnMonth,spnYear,spnCountry;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_filters, null);
        spnMonth = view.findViewById(R.id.spn_filterMonth);
        spnYear = view.findViewById(R.id.spn_filterYear);
        spnCountry = view.findViewById(R.id.spn_filterCountry);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        builder.setTitle(R.string.filters)
                .setPositiveButton(R.string.btn_accept,null)
                .setNegativeButton(R.string.btn_cancel, (dialog, which) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        dialog.setOnShowListener(dialogInterface ->{
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(btn ->{
                selectedFilter(dialog, btn);
            });
        });


        return dialog;
    }

    private void selectedFilter(AlertDialog dialog, View btn) {
        if (spnMonth.getSelectedItemPosition() != 0){
            onListener.onFiltersMonth(spnMonth.getSelectedItem().toString());
            dialog.dismiss();
        }else if (spnYear.getSelectedItemPosition() != 0){
            onListener.onFiltersYear(spnYear.getSelectedItem().toString());
            dialog.dismiss();
        }else if (spnCountry.getSelectedItemPosition() != 0){
            onListener.onFiltersCountry(spnCountry.getSelectedItem().toString());
            dialog.dismiss();
        }else if (spnMonth.getSelectedItemPosition() != 0
                && spnYear.getSelectedItemPosition() != 0){
            onListener.onFilterMonthYear(spnMonth.getSelectedItem().toString(),
                    spnYear.getSelectedItem().toString());
            dialog.dismiss();
        } else if (spnMonth.getSelectedItemPosition() != 0
                && spnCountry.getSelectedItemPosition() != 0) {
            onListener.onFilterMonthCountry(spnMonth.getSelectedItem().toString(),
                    spnCountry.getSelectedItem().toString());
            dialog.dismiss();
        } else if (spnYear.getSelectedItemPosition() != 0
                && spnCountry.getSelectedItemPosition() != 0) {
            onListener.onFilterYearCountry(spnYear.getSelectedItem().toString(),
                    spnCountry.getSelectedItem().toString());
            dialog.dismiss();
        } else if (spnMonth.getSelectedItemPosition() != 0
                && spnYear.getSelectedItemPosition() != 0
                && spnCountry.getSelectedItemPosition() != 0 ) {
            onListener.onFilterMonthYearCountry(spnMonth.getSelectedItem().toString(),
                    spnYear.getSelectedItem().toString(),
                    spnCountry.getSelectedItem().toString());
            dialog.dismiss();
        }else {
            Snackbar.make(btn, R.string.errorFilter, Snackbar.LENGTH_LONG).show();
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
