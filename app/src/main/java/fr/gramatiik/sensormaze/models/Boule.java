package fr.gramatiik.sensormaze.models;

import android.graphics.Color;
import android.graphics.RectF;

public class Boule {
    // Rayon de la boule
    public static final int RAYON = 50;

    // Couleur de la boule
    private int mCouleur = Color.GREEN;
    public int getCouleur() {
        return mCouleur;
    }

    // Vitesse maximale autorisée pour la boule
    private static final float MAX_SPEED = 20.0f;

    // Permet à la boule d'accélérer moins vite
    private static final float COMPENSATEUR = 8.0f;

    // Utilisé pour compenser les rebonds
    private static final float REBOND = 1.75f;

    // Le rectangle qui correspond à la position de départ de la boule
    private RectF mInitialRectangle = null;

    // A partir du rectangle initial on détermine la position de la boule
    public void setInitialRectangle(RectF pInitialRectangle) {
        this.mInitialRectangle = pInitialRectangle;
        this.mX = pInitialRectangle.left + RAYON;
        this.mY = pInitialRectangle.top + RAYON;
    }

    // Le rectangle de collision
    private RectF mRectangle = null;

    // Coordonnées en x
    private float mX;
    public float getX() {
        return mX;
    }
    public void setPosX(float pPosX) {
        mX = pPosX;

        // Si la boule sort du cadre, on rebondit
        if(mX < RAYON) {
            mX = RAYON;
            // Rebondir, c'est changer la direction de la balle
            mSpeedY = -mSpeedY / REBOND;
        } else if(mX > mWidth - RAYON) {
            mX = mWidth - RAYON;
            mSpeedY = -mSpeedY / REBOND;
        }
    }

    // Coordonnées en y
    private float mY;
    public float getY() {
        return mY;
    }

    public void setPosY(float pPosY) {
        mY = pPosY;
        if(mY < RAYON) {
            mY = RAYON;
            mSpeedX = -mSpeedX / REBOND;
        } else if(mY > mHeight - RAYON) {
            mY = mHeight - RAYON;
            mSpeedX = -mSpeedX / REBOND;
        }
    }

    // Vitesse sur l'axe x
    private float mSpeedX = 0;
    // Utilisé quand on rebondit sur les murs horizontaux
    public void changeXSpeed() {
        mSpeedX = -mSpeedX;
    }

    // Vitesse sur l'axe y
    private float mSpeedY = 0;
    // Utilisé quand on rebondit sur les murs verticaux
    public void changeYSpeed() {
        mSpeedY = -mSpeedY;
    }

    // Taille de l'écran en hauteur
    private int mHeight = -1;
    public void setHeight(int pHeight) {
        this.mHeight = pHeight;
    }

    // Taille de l'écran en largeur
    private int mWidth = -1;
    public void setWidth(int pWidth) {
        this.mWidth = pWidth;
    }

    public Boule() {
        mRectangle = new RectF();
    }

    // Mettre à jour les coordonnées de la boule
    public RectF putXAndY(float pX, float pY) {
        mSpeedX += pX / COMPENSATEUR;
        if(mSpeedX > MAX_SPEED)
            mSpeedX = MAX_SPEED;
        if(mSpeedX < -MAX_SPEED)
            mSpeedX = -MAX_SPEED;

        mSpeedY += pY / COMPENSATEUR;
        if(mSpeedY > MAX_SPEED)
            mSpeedY = MAX_SPEED;
        if(mSpeedY < -MAX_SPEED)
            mSpeedY = -MAX_SPEED;

        setPosX(mX + mSpeedY);
        setPosY(mY + mSpeedX);

        // Met à jour les coordonnées du rectangle de collision
        mRectangle.set(mX - RAYON, mY - RAYON, mX + RAYON, mY + RAYON);

        return mRectangle;
    }

    // Remet la boule à sa position de départ
    public void reset() {
        mSpeedX = 0;
        mSpeedY = 0;
        this.mX = mInitialRectangle.left + RAYON;
        this.mY = mInitialRectangle.top + RAYON;
    }
}
