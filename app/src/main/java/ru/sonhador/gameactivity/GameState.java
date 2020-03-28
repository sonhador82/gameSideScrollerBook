package ru.sonhador.gameactivity;

import android.content.Context;
import android.content.SharedPreferences;

final class GameState {
    // static - переменные класса, а не инстанса
    // volatile - можно из снаружи и изнутри треда использовать
    private static volatile boolean mThreadRunning = false;
    private static volatile boolean mPaused = true;
    private static volatile boolean mGameOver = true;
    private static volatile boolean mDrawing = false;

    private GameStarter gameStarter;

    private int mScore;
    private int mHighScore;
    private int mNumShips;

    private SharedPreferences.Editor mEditor;

    GameState(GameStarter gs, Context context)
    {
        gameStarter = gs;

        // get curr highscreo
        SharedPreferences prefs;
        prefs = context.getSharedPreferences("HiScore", context.MODE_PRIVATE);
        mEditor = prefs.edit();
        mHighScore = prefs.getInt("his_score", 0);
    }

    private void endGame(){
        mGameOver = true;
        mPaused = true;
        if (mScore > mHighScore) {
            mHighScore = mScore;
            mEditor.putInt("hi_score", mHighScore);
            mEditor.commit();
        }
    }

    void startNewGame(){
        mScore = 0;
        mNumShips = 3;
        // Don't want to be drawing objects
        // while deSpawnReSpawn is
        // clearing them and spawning them again
        stopDrawing();
        gameStarter.deSpawnReSpawn();
        resume();

        // Now we can draw again
        startDrawing();
    }

    void loseLife(SoundEngine se){
        mNumShips--;
        se.playPlayerExplode();
        if (mNumShips == 0){
            pause();
            endGame();
        }
    }

    int getScore() {
        return mScore;
    }

    int getHighScore() {
        return mHighScore;
    }

    int getNumShips() {
        return mNumShips;
    }

    void increaseScore(){
        mScore++;
    }

    void pause(){
        mPaused = true;
    }

    void resume(){
        mGameOver = false;
        mPaused = false;
    }

    void stopEverethins(){
        mPaused = true;
        mGameOver = true;
        mThreadRunning = false;
    }

    boolean getThreadRunning() {
        return mThreadRunning;
    }

    void startThread(){
        mThreadRunning = true;
    }

    private void stopDrawing(){
        mDrawing = false;
    }

    private void startDrawing(){
        mDrawing = true;
    }

    boolean getDrawing(){
        return mDrawing;
    }

    boolean getPaused(){
        return mPaused;
    }

    boolean getGameOver(){
        return mGameOver;
    }
}
