package fr.gramatiik.sensormaze.models;

import android.graphics.RectF;

public class Bloc {
    public enum  Type { TROU, DEPART, ARRIVEE }

    private float mWidth;
    public float getWidth () {
        return mWidth;
    }

    private float mHeight;
    public float getHeight () {
        return mHeight;
    }

    private Type mType = null;
    private RectF mRectangle = null;

    public Type getType() {
        return mType;
    }

    public RectF getRectangle() {
        return mRectangle;
    }

    public Bloc(Type pType, int pX, int pY, float width, float height) {
        mWidth = width;
        mHeight = height;
        mType = pType;
        mRectangle = new RectF(pX * mWidth, pY * mHeight, (pX + 1) * mWidth, (pY + 1) * mHeight);
    }
}
