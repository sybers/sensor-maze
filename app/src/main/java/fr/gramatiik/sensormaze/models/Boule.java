package fr.gramatiik.sensormaze.models;

import android.graphics.Color;
import android.graphics.RectF;

public class Boule {

    /**
     * Vitesse maximale autorisée pour la boule.
     */
    private static final float MAX_SPEED = 20.0f;

    /**
     * Permet à la boule d'accélérer moins vite.
     */
    private static final float COMPENSATEUR = 8.0f;

    /**
     * Utilisé pour compenser les rebonds.
     */
    private static final float REBOND = 1.75f;

    /**
     * Rayon de la boule.
     */
    private int mRayon = 30;

    /**
     * Couleur par défaut de la boule.
     */
    private int mCouleur = Color.GREEN;

    /**
     * Le rectangle qui correspond à la position de départ de la boule,
     * Utilisé pour remettre la boule à sa position de départ.
     */
    private RectF mInitialRectangle = null;

    /**
     * Le rectangle de collision de la boule.
     */
    private RectF mRectangle = null;

    /**
     * Coordonnées en X.
     */
    private float mX;

    /**
     * Coordonnées en Y.
     */
    private float mY;

    /**
     * Vitesse sur l'axe X.
     */
    private float mSpeedX = 0;

    /**
     * Vitesse sur l'axe y
     */
    private float mSpeedY = 0;

    /**
     * Taille de l'écran en Hauteur.
     */
    private int mHeight = -1;

    /**
     * Taille de l'écran en largeur.
     */
    private int mWidth = -1;

    /**
     * Retourne le rayon de la boule.
     * @return Rayon de la boule.
     */
    public int getRayon() {
        return mRayon;
    }

    /**
     * Retourne la couleur de la boule.
     * @return Couleur de la boule.
     */
    public int getCouleur() {
        return mCouleur;
    }

    /**
     * Change la couleur de la boule.
     * @param couleur Nouvelle couleur.
     */
    public void setCouleur(int couleur) {
        mCouleur = couleur;
    }

    /**
     * A partir du rectangle initial on détermine la position de la boule.
     *
     * @param pInitialRectangle Rectangle initial.
     */
    public void setInitialRectangle(RectF pInitialRectangle) {
        this.mInitialRectangle = pInitialRectangle;
        this.mX = pInitialRectangle.left + mRayon;
        this.mY = pInitialRectangle.top + mRayon;
    }

    /**
     * Retourne la position X de la boule.
     *
     * @return Position X.
     */
    public float getX() {
        return mX;
    }

    /**
     * Change la position X de la boule.
     *
     * @param pPosX nouvelle position X.
     */
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

    /**
     * Retourne la position X de la boule.
     *
     * @return Position X.
     */
    public float getY() {
        return mY;
    }

    /**
     * Change la position Y de la boule.
     *
     * @param pPosY nouvelle position Y.
     */
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

    /**
     * Utilisé quand on rebondit sur les murs horizontaux
     */
    public void changeXSpeed() {
        mSpeedX = -mSpeedX;
    }

    /**
     * Utilisé quand on rebondit sur les murs verticaux
     */
    public void changeYSpeed() {
        mSpeedY = -mSpeedY;
    }

    /**
     * Change la hauteur d'écran associé à la boule.
     *
     * @param pHeight nouvelle hauteur d'écran.
     */
    public void setHeight(int pHeight) {
        this.mHeight = pHeight;
    }

    /**
     * Change la largeur d'écran associé à la boule.
     *
     * @param pWidth nouvelle largeur d'écran.
     */
    public void setWidth(int pWidth) {
        this.mWidth = pWidth;
    }

    /**
     * Constructeur de l'objet Boule.
     *
     * @param rayon rayon de la boule
     */
    public Boule(int rayon) {
        mRayon = rayon;
        mRectangle = new RectF();
    }

    /**
     * Permet de mettre à jour les coordonnées de la boule
     * @param pX position X de la boule
     * @param pY position Y de la boule
     * @return le nouveau rectangle de collision
     */
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

    /**
     * Premet de remettre la boule dans sa position de départ
     */
    public void reset() {
        mSpeedX = 0;
        mSpeedY = 0;
        this.mX = mInitialRectangle.left + mRayon;
        this.mY = mInitialRectangle.top + mRayon;
    }
}
