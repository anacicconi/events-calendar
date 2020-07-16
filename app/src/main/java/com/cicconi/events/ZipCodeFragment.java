package com.cicconi.events;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import java.util.ArrayList;

public class ZipCodeFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ArrayList selectedItems = new ArrayList();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
            .setMessage(R.string.dialog_zip_code)
            .setMultiChoiceItems(R.array.zip_codes, null, (dialog, which, isChecked) -> {
                if (isChecked) {
                    selectedItems.add(which);
                } else if (selectedItems.contains(which)) {
                    selectedItems.remove(Integer.valueOf(which));
                }
            })
            .setPositiveButton(R.string.ok, (dialog, id) -> {
                MainActivity mainActivity = (MainActivity) getActivity();
                if(mainActivity != null) {
                    mainActivity.onZipCodeSet(selectedItems);
                }
            })
            .setNegativeButton(R.string.cancel, (dialog, id) -> dismiss());

        return builder.create();
    }
}