package fr.gramatiik.sensormaze.models;

import android.graphics.Color;
import android.graphics.RectF;

public class Boule {
    // Rayon de la boule
    private int mRayon = 30;
    public int getRayon() {
        return mRayon;
    }

    // Couleur de la boule
    private int mCouleur = Color.GREEN;
    public int getCouleur() {
        return mCouleur;
    }

    public void setCouleur(int couleur) {
        mCouleur = couleur;
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
        this.mX = pInitialRectangle.left + mRayon;
        this.mY = pInitialRectangle.top + mRayon;
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
        if(mX < mRayon) {
            mX = mRayon;
            // Rebondir, c'est changer la direction de la balle
            mSpeedY = -mSpeedY / REBOND;
        } else if(mX > mWidth - mRayon) {
            mX = mWidth - mRayon;
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
        if(mY < mRayon) {
            mY = mRayon;
            mSpeedX = -mSpeedX / REBOND;
        } else if(mY > mHeight - mRayon) {
            mY = mHeight - mRayon;
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

    public Boule(int rayon) {
        mRayon = rayon;
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
        mRectangle.set(mX - mRayon, mY - mRayon, mX + mRayon, mY + mRayon);

        return mRectangle;
    }

    // Remet la boule à sa position de départ
    public void reset() {
        mSpeedX = 0;
        mSpeedY = 0;
        this.mX = mInitialRectangle.left + mRayon;
        this.mY = mInitialRectangle.top + mRayon;
    }
}
