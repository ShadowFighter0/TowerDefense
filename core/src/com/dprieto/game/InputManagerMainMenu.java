package com.dprieto.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class InputManagerMainMenu implements InputProcessor {

    private MainMenu mainMenu;

    Vector2 point;

    public InputManagerMainMenu(MainMenu menu)
    {
        this.mainMenu = menu;

        point = new Vector2();
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector3 pos = mainMenu.camera.orthographicCamera.unproject(new Vector3(screenX,screenY,0));

        point.x = pos.x;
        point.y = pos.y;

        mainMenu.CheckOnClick(point);

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
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
