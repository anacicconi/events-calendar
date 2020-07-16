package com.cicconi.events;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import java.util.ArrayList;

public class CategoryDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ArrayList<String> selectedItems = new ArrayList();

        String[] stringArray = getResources().getStringArray(R.array.categories);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
            .setTitle(R.string.dialog_category)
            .setMultiChoiceItems(R.array.categories, null, (dialog, which, isChecked) -> {
                if (isChecked) selectedItems.add(stringArray[which]);
                else selectedItems.remove(stringArray[which]);
            })
            .setPositiveButton(R.string.ok, (dialog, id) -> {
                MainActivity mainActivity = (MainActivity) getActivity();
                if(mainActivity != null) {
                    mainActivity.onEventCategoriesSet(selectedItems);
                }
            })
            .setNegativeButton(R.string.cancel, (dialog, id) -> dismiss());

        return builder.create();
    }
}