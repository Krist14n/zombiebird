package com.cocolab.zombiebird.zbhelpers;

import com.badlogic.gdx.InputProcessor;
import com.cocolab.zombiebird.gameobjects.Bird;
import com.cocolab.zombiebird.gameworld.GameWorld;

/**
 * Created by kristiantapiaaleman on 1/4/16.
 */
public class InputHandler implements InputProcessor {

    private Bird myBird;
    private GameWorld myWorld;

    public InputHandler(GameWorld myWorld) {

        // myBird now represents the gameWorld's bird.
        this.myWorld = myWorld;
        myBird = myWorld.getBird();

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (myWorld.isReady()) {
            myWorld.start();
        }

        myBird.onClick();

        if (myWorld.isGameOver()|| myWorld.isHighScore()) {
            // Reset all variables, go to GameState.READ
            myWorld.restart();
        }
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
