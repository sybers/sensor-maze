package fr.gramatiik.sensormaze.engines;

import fr.gramatiik.sensormaze.MainActivity;
import fr.gramatiik.sensormaze.models.Bloc;
import fr.gramatiik.sensormaze.models.Boule;
import android.app.Service;
import android.graphics.Color;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MazeEngine {
    private Boule mBoule = null;
    public Boule getBoule() {
        return mBoule;
    }

    public void setBoule(Boule pBoule) {
        this.mBoule = pBoule;
    }

    // Le labyrinthe
    private List<Bloc> mBlocks = null;

    private MainActivity mActivity = null;

    private SensorManager mManager = null;
    private Sensor mAccelerometre = null;
    private Sensor mMagneticSensor = null;
    private Sensor mLightSensor = null;

    SensorEventListener mAccelerometerSensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent pEvent) {
            float x = pEvent.values[0];
            float y = pEvent.values[1];

            if(mBoule != null) {
                // On met à jour les coordonnées de la boule
                RectF hitBox = mBoule.putXAndY(x, y);

                // Pour tous les blocs du labyrinthe
                for(Bloc block : mBlocks) {
                    // On crée un nouveau rectangle pour ne pas modifier celui du bloc
                    RectF inter = new RectF(block.getRectangle());
                    if(inter.intersect(hitBox)) {
                        // On agit différement en fonction du type de bloc
                        switch(block.getType()) {
                            case TROU:
                                mActivity.showDefeatDialog();
                                break;

                            case DEPART:
                                break;

                            case ARRIVEE:
                                mActivity.showVictoryDialog();
                                break;
                        }
                        break;
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor pSensor, int pAccuracy) {}
    };

    SensorEventListener mMagneticSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(mBoule != null) {
                // on calcule la couleur en fonction du magnétisme
                float maxRange = event.sensor.getMaximumRange();

                int r = (int)sensorRangeToColorComponent(event.values[0], 50);
                int g = (int)sensorRangeToColorComponent(event.values[1], 50);
                int b = (int)sensorRangeToColorComponent(event.values[2], 50);

                int c = Color.rgb(r, g, b);
                // on applique la couleur à la boule
                mBoule.setCouleur(c);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    };

    SensorEventListener mLightSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    };

    private float sensorRangeToColorComponent(float value, float maxValue) {
        if(value > maxValue) value = maxValue;
        return (Math.abs(value)*255)/maxValue;
    }

    public MazeEngine(MainActivity pView) {
        mActivity = pView;
        mManager = (SensorManager) mActivity.getBaseContext().getSystemService(Service.SENSOR_SERVICE);
        mAccelerometre = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagneticSensor = mManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mLightSensor = mManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    // Remet à zéro l'emplacement de la boule
    public void reset() {
        mBoule.reset();
    }

    // Arrête le capteur
    public void stop() {
        mManager.unregisterListener(mAccelerometerSensorEventListener, mAccelerometre);
        mManager.unregisterListener(mMagneticSensorEventListener, mMagneticSensor);
        mManager.unregisterListener(mLightSensorEventListener, mLightSensor);
    }

    // Redémarre le capteur
    public void resume() {
        mManager.registerListener(mAccelerometerSensorEventListener, mAccelerometre, SensorManager.SENSOR_DELAY_GAME);
        mManager.registerListener(mMagneticSensorEventListener, mMagneticSensor, SensorManager.SENSOR_DELAY_GAME);
        mManager.registerListener(mLightSensorEventListener, mLightSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    // Construit le labyrinthe
    public List<Bloc> buildLabyrinthe() {
        mBlocks = new ArrayList<Bloc>();
        mBlocks.add(new Bloc(Bloc.Type.TROU, 0, 0));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 0, 1));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 0, 2));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 0, 3));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 0, 4));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 0, 5));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 0, 6));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 0, 7));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 0, 8));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 0, 9));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 0, 10));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 0, 11));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 0, 12));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 0, 13));

        mBlocks.add(new Bloc(Bloc.Type.TROU, 1, 0));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 1, 13));

        mBlocks.add(new Bloc(Bloc.Type.TROU, 2, 0));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 2, 13));

        mBlocks.add(new Bloc(Bloc.Type.TROU, 3, 0));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 3, 13));

        mBlocks.add(new Bloc(Bloc.Type.TROU, 4, 0));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 4, 1));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 4, 2));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 4, 3));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 4, 4));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 4, 5));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 4, 6));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 4, 7));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 4, 8));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 4, 9));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 4, 10));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 4, 13));

        mBlocks.add(new Bloc(Bloc.Type.TROU, 5, 0));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 5, 13));

        mBlocks.add(new Bloc(Bloc.Type.TROU, 6, 0));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 6, 13));

        mBlocks.add(new Bloc(Bloc.Type.TROU, 7, 0));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 7, 1));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 7, 2));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 7, 5));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 7, 6));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 7, 9));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 7, 10));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 7, 11));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 7, 12));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 7, 13));

        mBlocks.add(new Bloc(Bloc.Type.TROU, 8, 0));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 8, 5));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 8, 9));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 8, 13));

        mBlocks.add(new Bloc(Bloc.Type.TROU, 9, 0));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 9, 5));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 9, 9));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 9, 13));

        mBlocks.add(new Bloc(Bloc.Type.TROU, 10, 0));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 10, 5));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 10, 9));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 10, 13));

        mBlocks.add(new Bloc(Bloc.Type.TROU, 11, 0));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 11, 5));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 11, 9));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 11, 13));

        mBlocks.add(new Bloc(Bloc.Type.TROU, 12, 0));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 12, 1));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 12, 2));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 12, 3));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 12, 4));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 12, 5));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 12, 9));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 12, 8));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 12, 13));

        mBlocks.add(new Bloc(Bloc.Type.TROU, 13, 0));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 13, 8));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 13, 13));

        mBlocks.add(new Bloc(Bloc.Type.TROU, 14, 0));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 14, 8));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 14, 13));

        mBlocks.add(new Bloc(Bloc.Type.TROU, 15, 0));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 15, 8));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 15, 13));

        mBlocks.add(new Bloc(Bloc.Type.TROU, 16, 0));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 16, 4));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 16, 5));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 16, 6));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 16, 7));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 16, 8));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 16, 9));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 16, 13));

        mBlocks.add(new Bloc(Bloc.Type.TROU, 17, 0));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 17, 13));

        mBlocks.add(new Bloc(Bloc.Type.TROU, 18, 0));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 18, 13));

        mBlocks.add(new Bloc(Bloc.Type.TROU, 19, 0));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 19, 1));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 19, 2));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 19, 3));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 19, 4));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 19, 5));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 19, 6));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 19, 7));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 19, 8));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 19, 9));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 19, 10));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 19, 11));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 19, 12));
        mBlocks.add(new Bloc(Bloc.Type.TROU, 19, 13));

        Bloc b = new Bloc(Bloc.Type.DEPART, 2, 2);
        mBoule.setInitialRectangle(new RectF(b.getRectangle()));
        mBlocks.add(b);

        mBlocks.add(new Bloc(Bloc.Type.ARRIVEE, 8, 11));

        return mBlocks;
    }

}
