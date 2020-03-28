package ru.sonhador.gameactivity;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

class GameEngine extends SurfaceView implements Runnable {
    private Thread mThread = null;
    private long mFPS;

    private SoundEngine mSoungEngine;

    public GameEngine(Context context, Point size) {
        super(context);

        mSoungEngine = new SoundEngine(context);
    }

    @Override
    public void run() {
        long frameStartTime = System.currentTimeMillis();
        // Update all the game objects here
        // in a new way
        // Draw all the game objects here
        // in a new way»
        long timeThisFrame = System.currentTimeMillis() - frameStartTime;
        if (timeThisFrame >= 1) {
            final int MILLIS_IN_SECOND = 1000;
            mFPS = MILLIS_IN_SECOND / timeThisFrame;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Handle the player's input here
        // But in a new way»
        return true;
    }

    public void startThread() {
        mThread = new Thread(this);
        mThread.start();
    }

    public void stopThread() {
        try {
            mThread.join();
        }
        catch (InterruptedException e)
        {
            Log.e("Exception","stop-Thread()" + e.getMessage());
        }
    }
}
