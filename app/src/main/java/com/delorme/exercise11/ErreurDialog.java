package com.delorme.exercise11;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ErreurDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.erreur_vitesse)
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                });
        return builder.create();
    }
}

