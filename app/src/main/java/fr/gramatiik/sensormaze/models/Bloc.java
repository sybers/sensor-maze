package fr.gramatiik.sensormaze.models;

import android.graphics.RectF;

/**
 * Classe Bloc.
 *
 * Cette classe modélise un bloc à l'écran, les blocs peuvent être des trous, le départ ou l'arrivée.
 *
 * @author Stanyslas Bres
 */
public class Bloc {
    /**
     * Les différents types de Blocs disponibles.
     */
    public enum  Type { TROU, DEPART, ARRIVEE }

    private float mWidth;
    private float mHeight;

    private Type mType = null;
    private RectF mRectangle = null;

    /**
     * Retourne la largeur du bloc.
     *
     * @return Largeur du bloc
     */
    public float getWidth () {
        return mWidth;
    }

    /**
     * Retourne la hauteur du bloc.
     *
     * @return Hauteur du bloc
     */
    public float getHeight () {
        return mHeight;
    }

    /**
     * Retourne le type du bloc.
     *
     * @return Type du bloc
     */
    public Type getType() {
        return mType;
    }

    /**
     * Retourne le Rectangle associé au Bloc,
     * utilisé pour détecter les collisions.
     *
     * @return Rectangle associé au bloc
     */
    public RectF getRectangle() {
        return mRectangle;
    }

    /**
     * Constructeur de l'Object Bloc
     *
     * @param pType Type du bloc.
     * @param pX position X, par rapport à la grille.
     * @param pY position Y, par rapport à la grille.
     * @param width Largeur de l'élément.
     * @param height Hauteur de l'élément.
     */
    public Bloc(Type pType, int pX, int pY, float width, float height) {
        mWidth = width;
        mHeight = height;
        mType = pType;
        mRectangle = new RectF(pX * mWidth, pY * mHeight, (pX + 1) * mWidth, (pY + 1) * mHeight);
    }
}
