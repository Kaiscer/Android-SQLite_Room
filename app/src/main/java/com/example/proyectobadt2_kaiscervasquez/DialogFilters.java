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

    Spinner spn;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_filters, null);
        spn = view.findViewById(R.id.spn_filter);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        builder.setTitle(R.string.filters)
                .setPositiveButton(R.string.btn_accept,null)
                .setNegativeButton(R.string.btn_cancel, (dialog, which) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        dialog.setOnShowListener(dialogInterface ->{
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(btn ->{
                if (spn.getSelectedItem().toString().equals("")){
                    Snackbar.make(btn,R.string.errorFilter, Snackbar.LENGTH_LONG).show();
                }else{
                    onListener.onFiltersSelected(spn.getSelectedItem().toString());
                    dialog.dismiss();
                }
            });
        });


        return dialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFiltersListener){
            onListener = (OnFiltersListener) context;
        }else {
            throw new RuntimeException(context.toString() + getString(R.string.errorFilters));
        }
    }

    public void onDetach() {
        if (onListener != null){
            onListener = null;
        }
        super.onDetach();
    }
}
