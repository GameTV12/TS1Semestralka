package cz.cvut.fel.myselect;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Locale;

import io.paperdb.Paper;

/**
 * @author Kozhemiakin Viktor
 * Class for non-design add window for languages (3)
 */
public class SelectAppLangDialog extends DialogFragment {
    /**
     * @param savedInstanceState
     * @return
     */

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        final String [] listOfLanguages = getActivity().getResources().getStringArray(R.array.list_language);

        AlertDialog.Builder selectLang = new AlertDialog.Builder(getActivity());

        selectLang.setTitle(R.string.select_language);
        selectLang.setItems(listOfLanguages, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), getString(R.string.your_language), Toast.LENGTH_SHORT).show();
            }
        });
        return selectLang.create();
    }

}
