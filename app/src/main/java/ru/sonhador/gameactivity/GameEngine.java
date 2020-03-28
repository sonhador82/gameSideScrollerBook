package ru.sonhador.gameactivity;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

class GameEngine extends SurfaceView implements Runnable, GameStarter {
    private Thread mThread = null;
    private long mFPS;

    private GameState mGameState;
    private SoundEngine mSoungEngine;
    HUD mHUD;
    Renderer mRenderer;

    public GameEngine(Context context, Point size) {
        super(context);
        mGameState = new GameState(this, context);
        mSoungEngine = new SoundEngine(context);
        mHUD = new HUD(size);
        mRenderer = new Renderer(this);
    }

    @Override
    public void run() {
        while (mGameState.getThreadRunning()){
            long frameStartTime = System.currentTimeMillis();
            if(!mGameState.getPaused()) {
                // update all game objects here in a new way
            }
            // draw all game objects here in a new way
            mRenderer.draw(mGameState, mHUD);

            long timeThisFrame = System.currentTimeMillis() - frameStartTime;
            if (timeThisFrame >= 1) {
                final int MILLIS_IN_SECOND = 1000;
                mFPS = MILLIS_IN_SECOND / timeThisFrame;
            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Handle the player's input here
        // But in a new way»

        mSoungEngine.playShoot();
        return true;
    }

    public void startThread() {
        mGameState.startThread();
        mThread = new Thread(this);
        mThread.start();
    }

    public void stopThread() {
        mGameState.stopEverethins();
        try {
            mThread.join();
        }
        catch (InterruptedException e)
        {
            Log.e("Exception","stop-Thread()" + e.getMessage());
        }
    }

    @Override
    public void deSpawnReSpawn() {
        // Eventually this will despawn
        // and then respawn all the game objects»
    }
}
