package fr.gramatiik.sensormaze;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * @author Stanyslas Bres <stanyslas.bres@gmail.com>
 * @version 1.0.0
 */

public class GameDialogFragment extends DialogFragment {

    public DialogInterface.OnClickListener mRestartCallback;

    public enum DialogType { VICTORY_DIALOG, DEFEAT_DIALOG };

    static public GameDialogFragment newInstance(DialogType type, DialogInterface.OnClickListener callback) {

        Bundle args = new Bundle();
        args.putSerializable("type", type);
        GameDialogFragment fragment = new GameDialogFragment();
        fragment.setArguments(args);
        fragment.mRestartCallback = callback;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        DialogType type = (DialogType) bundle.get("type");
        String title = "", message = "";

        if(type == null) throw new NullPointerException("Dialog type was not in bundle !");

        switch(type) {
            case VICTORY_DIALOG:
                title = "Victoire !";
                message = "Bravo, vous avez gagné.";
                break;
            case DEFEAT_DIALOG:
                title = "Défaite";
                message = "Dommage, vous avez perdu...";
                break;
        }

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton("Recommencer", this.mRestartCallback)
                .create();
    }

}
