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

    private DialogType mDialogType;

    static public GameDialogFragment newInstance(DialogType type, DialogInterface.OnClickListener callback) {
        GameDialogFragment fragment = new GameDialogFragment();
        fragment.setType(type);
        fragment.mRestartCallback = callback;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        String title = "", message = "";

        if(mDialogType == null) throw new NullPointerException("Dialog type was not set!");

        switch(mDialogType) {
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

    private void setType(DialogType type) {
        this.mDialogType = type;
    }

}
