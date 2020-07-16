package com.cicconi.events;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

public class CategoryDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String[] stringArray = getResources().getStringArray(R.array.categories);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
            .setTitle(R.string.dialog_category)
            .setSingleChoiceItems(R.array.categories, 0, null)
            .setPositiveButton(R.string.ok, (dialog, id) -> {
                int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();

                MainActivity mainActivity = (MainActivity) getActivity();
                if(mainActivity != null) {
                    mainActivity.onCategorySet(stringArray[selectedPosition]);
                }
            })
            .setNegativeButton(R.string.cancel, (dialog, id) -> dismiss());

        return builder.create();
    }
}