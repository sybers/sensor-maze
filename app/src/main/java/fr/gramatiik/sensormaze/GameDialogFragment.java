package fr.gramatiik.sensormaze;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Classe GameDialogFragement.
 *
 * Cette classe se charge d'afficher les dialogues de victoire ou de défaite du jeu.
 *
 * @author Stanyslas Bres.
 */
public class GameDialogFragment extends DialogFragment {

    /**
     * Le callback à appeler lors de l'appuie du bouton "Recommencer".
     */
    public DialogInterface.OnClickListener mRestartCallback;

    /**
     * Les différents types de dialogues disponibles.
     */
    enum DialogType { VICTORY_DIALOG, DEFEAT_DIALOG }

    /**
     * Le type de dialogue sélectionné.
     */
    private DialogType mDialogType;

    /**
     * Crée une nouvelle instance du Fragemnt.
     *
     * @param type Type de dialogue à générer.
     * @param callback Callback pour le bouton "Recommencer"
     *
     * @return Nouvelle instance du Fragment.
     */
    static public GameDialogFragment newInstance(DialogType type, DialogInterface.OnClickListener callback) {
        GameDialogFragment fragment = new GameDialogFragment();
        fragment.setType(type);
        fragment.mRestartCallback = callback;
        return fragment;
    }

    /**
     * Lors de la création du dialogue, on affiche les données en fonction du type.
     *
     * @param bundle Bundle du Fragemtn.
     *
     * @return Instance du Dialog créé.
     */
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

    /**
     * Change le type de dialogue à afficher.
     *
     * @param type Nouveau type de dialogue.
     */
    private void setType(DialogType type) {
        this.mDialogType = type;
    }

}
