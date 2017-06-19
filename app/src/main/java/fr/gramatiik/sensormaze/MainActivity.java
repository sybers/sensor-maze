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

public class MainActivity extends AppCompatActivity {
    // Le moteur graphique du jeu
    private MazeView mView = null;
    // Le moteur physique du jeu
    private MazeEngine mEngine = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mView = new MazeView(this);
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

    @Override
    protected void onResume() {
        super.onResume();
        mEngine.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        super.onStop();
        mEngine.stop();
    }

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

