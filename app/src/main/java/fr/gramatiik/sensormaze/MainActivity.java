package fr.gramatiik.sensormaze;

import android.graphics.Point;
import android.os.Bundle;
import java.util.List;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;

import fr.gramatiik.sensormaze.engines.MazeEngine;
import fr.gramatiik.sensormaze.engines.MazeView;
import fr.gramatiik.sensormaze.models.Bloc;
import fr.gramatiik.sensormaze.models.Boule;

/**
 * Classe MainActivity.
 *
 * Instancie le jeu lors du démarrage de l'application.
 *
 * @author Stanyslas Bres.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Le moteur physique du jeu
     */
    private MazeEngine mEngine = null;

    /**
     * Lors de la création de l'activité, on ititialise
     * les moteurs de jeux et toutes les dépendances.
     *
     * @param savedInstanceState État sauvegardé de l'application
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MazeView mView = new MazeView(this);
        setContentView(mView);

        mEngine = new MazeEngine(this, mView);

        Boule b = new Boule(20);
        mView.setBoule(b);
        mEngine.setBoule(b);

        Point screenSizes = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSizes);

        List<Bloc> mList = mEngine.buildLabyrinthe(screenSizes);
        mView.setBlocks(mList);
    }

    /**
     * Pour reprendre le jeu.
     */
    @Override
    protected void onResume() {
        super.onResume();
        mEngine.resume();
    }

    /**
     * Pour mettre le jeu en pause.
     */
    @Override
    protected void onPause() {
        super.onPause();
        super.onStop();
        mEngine.stop();
    }

    /**
     * Pour afficher le dialogue de victoire,
     * permet de relancer le heu si l'on clique sur "Recommencer".
     */
    public void showVictoryDialog() {
        mEngine.stop();
        GameDialogFragment.newInstance(GameDialogFragment.DialogType.VICTORY_DIALOG, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // L'utilisateur peut recommencer s'il le veut
                mEngine.reset();
                mEngine.resume();
            }
        }).show(getFragmentManager(), "GameDialog");
    }

    /**
     * Pour afficher le dialogue de défaite,
     * permet de relancer le heu si l'on clique sur "Recommencer".
     */
    public void showDefeatDialog() {
        mEngine.stop();
        GameDialogFragment.newInstance(GameDialogFragment.DialogType.DEFEAT_DIALOG, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // L'utilisateur peut recommencer s'il le veut
                mEngine.reset();
                mEngine.resume();
            }
        }).show(getFragmentManager(), "GameDialog");
    }

}

