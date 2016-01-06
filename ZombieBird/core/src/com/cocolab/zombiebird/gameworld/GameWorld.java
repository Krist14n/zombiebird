package com.cocolab.zombiebird.gameworld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.cocolab.zombiebird.gameobjects.Bird;
import com.cocolab.zombiebird.gameobjects.ScrollHandler;
import com.cocolab.zombiebird.zbhelpers.AssetLoader;

/**
 * Created by kristiantapiaaleman on 1/4/16.
 */
public class GameWorld {

    private Bird bird;
    private ScrollHandler scroller;
    private boolean isAlive = true;
    private Rectangle ground;
    private int score = 0;

    private int midPointY;

    private GameState currentState;

    public enum GameState {

        READY, RUNNING, GAMEOVER, HIGHSCORE

    }


    public GameWorld(int midPointY) {
        currentState = GameState.READY;
        bird = new Bird(33, midPointY - 5, 17, 12);
        // The grass should start 66 pixels below the midPointY
        scroller = new ScrollHandler(this, midPointY + 66);
        ground = new Rectangle(0, midPointY + 66, 136, 11);

    }

    public void update(float delta) {

        switch (currentState) {
            case READY:
                updateReady(delta);
                break;

            case RUNNING:
                updateRunning(delta);
                break;
            default:
                break;
        }
    }

    private void updateReady(float delta) {
        // Do nothing for now
    }

    public void updateRunning(float delta) {

        // Add a delta cap so that if our game takes too long
        // to update, we will not break our collision detection.

        if (delta > .15f) {
            delta = .15f;
        }

        bird.update(delta);
        scroller.update(delta);

        if (scroller.collides(bird) && bird.isAlive()) {
            scroller.stop();
            bird.die();
            AssetLoader.dead.play();
        }

        if (Intersector.overlaps(bird.getBoundingCircle(), ground)) {
            // Clean up on game over
            scroller.stop();
            bird.die();
            bird.decelerate();
            currentState = GameState.GAMEOVER;

            if (score > AssetLoader.getHighScore()){
                AssetLoader.setHighScore(score);
                currentState = GameState.HIGHSCORE;
            }

        }

    }

    public Bird getBird() {
        return bird;

    }

    public ScrollHandler getScroller() {
        return scroller;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int increment) {
        score += increment;
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public void start() {
        currentState = GameState.RUNNING;
    }

    public void restart() {
        currentState = GameState.READY;
        score = 0;
        bird.onRestart(midPointY - 5);
        scroller.onRestart();
        currentState = GameState.READY;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }
}
