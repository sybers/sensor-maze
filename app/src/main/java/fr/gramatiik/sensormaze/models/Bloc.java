package fr.gramatiik.sensormaze.models;

import android.graphics.RectF;

public class Bloc {
    public enum  Type { TROU, DEPART, ARRIVEE };

    private float SIZE = Boule.RAYON * 2;

    private Type mType = null;
    private RectF mRectangle = null;

    public Type getType() {
        return mType;
    }

    public RectF getRectangle() {
        return mRectangle;
    }

    public Bloc(Type pType, int pX, int pY) {
        this.mType = pType;
        this.mRectangle = new RectF(pX * SIZE, pY * SIZE, (pX + 1) * SIZE, (pY + 1) * SIZE);
    }
}
