package fr.gramatiik.sensormaze.engines;

import android.util.Log;
import android.view.SurfaceView;
import android.view.SurfaceHolder;


import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import fr.gramatiik.sensormaze.models.Bloc;
import fr.gramatiik.sensormaze.models.Boule;

/**
 * Classe MazeView.
 *
 * Permet de gérér l'affichage des éléments du jeu SensorMaze
 *
 * @author Stanyslas Bres
 */
public class MazeView extends SurfaceView implements SurfaceHolder.Callback {
    Boule mBoule;
    final SurfaceHolder mSurfaceHolder;
    DrawingThread mThread;
    private int mBackColor = Color.CYAN;
    private List<Bloc> mBlocks = null;

    /**
     * Change la couleur de fond du labyrinthe
     * @param color Nouvelle couleur
     */
    public void setBGColor(int color) {
        mBackColor = color;
    }

    /**
     * Retourne la liste des Blocs composant la 'carte' du labyrinthe
     * @return
     */
    public List<Bloc> getBlocks() {
        return mBlocks;
    }

    /**
     * Change la liste des Blocs composant la 'carte' du labyrinthe
     * @param pBlocks
     */
    public void setBlocks(List<Bloc> pBlocks) {
        this.mBlocks = pBlocks;
    }

    /**
     * Retourne la boule utilisée pour l'affichage
     * @return
     */
    public Boule getBoule() {
        return mBoule;
    }

    /**
     * Change la boule utilisée pour l'affichage
     * @param pBoule
     */
    public void setBoule(Boule pBoule) {
        this.mBoule = pBoule;
    }

    Paint mPaint;

    /**
     * Constructeur du moteur graphique
     *
     * @param pContext Contexte de l'application
     */
    public MazeView(Context pContext) {
        super(pContext);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mThread = new DrawingThread();

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);

        mBoule = new Boule(20);
    }

    /**
     * On dessine le plateau de jeu lors de la mise à jour de l'affichage
     *
     * @param pCanvas Canvas utilisé pour l'affichage
     */
    @Override
    protected void onDraw(Canvas pCanvas) {
        // Dessiner le fond de l'écran en premier
        pCanvas.drawColor(mBackColor);
        if(mBlocks != null) {
            // Dessiner tous les blocs du labyrinthe
            for(Bloc b : mBlocks) {
                switch(b.getType()) {
                    case DEPART:
                        mPaint.setColor(Color.WHITE);
                        break;
                    case ARRIVEE:
                        mPaint.setColor(Color.RED);
                        break;
                    case TROU:
                        mPaint.setColor(Color.DKGRAY);
                        break;
                }
                pCanvas.drawRect(b.getRectangle(), mPaint);
            }
        }

        // Dessiner la boule
        if(mBoule != null) {
            mPaint.setColor(mBoule.getCouleur());
            pCanvas.drawCircle(mBoule.getX(), mBoule.getY(), mBoule.getRayon(), mPaint);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder pHolder, int pFormat, int pWidth, int pHeight) {}

    /**
     * A la création de la surface, on initialise la boule
     * @param pHolder
     */
    @Override
    public void surfaceCreated(SurfaceHolder pHolder) {
        mThread.keepDrawing = true;
        mThread.start();
        // Quand on crée la boule, on lui indique les coordonnées de l'écran
        if(mBoule != null ) {
            this.mBoule.setHeight(getHeight());
            this.mBoule.setWidth(getWidth());
        }
    }

    /**
     * On nettoie à la destruction de la surface
     *
     * @param pHolder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder pHolder) {
        mThread.keepDrawing = false;
        boolean retry = true;
        while (retry) {
            try {
                mThread.join();
                retry = false;
            } catch (InterruptedException e) {
                Log.e("MAZE_VIEW", e.getMessage());
            }
        }

    }

    /**
     * Class Thread utilisée pour le dessin
     */
    private class DrawingThread extends Thread {
        boolean keepDrawing = true;

        @Override
        public void run() {
            Canvas canvas;
            while (keepDrawing) {
                canvas = null;

                try {
                    canvas = mSurfaceHolder.lockCanvas();
                    synchronized (mSurfaceHolder) {
                        onDraw(canvas);
                    }
                } finally {
                    if (canvas != null)
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                }

                // Pour dessiner à 50 fps
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    Log.e("MAZE_VIEW", e.getMessage());
                }
            }
        }
    }
}

