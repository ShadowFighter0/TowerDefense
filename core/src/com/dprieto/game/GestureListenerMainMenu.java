package com.dprieto.game;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GestureListenerMainMenu implements GestureDetector.GestureListener {

    MainMenu mainMenu;
    Vector2 point;

    public GestureListenerMainMenu(MainMenu mainMenu)
    {
        this.mainMenu = mainMenu;
        point = new Vector2();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        Vector3 pos = mainMenu.camera.orthographicCamera.unproject(new Vector3(x,y,0));

        point.x = pos.x;
        point.y = pos.y;

        boolean clicked = false;

        mainMenu.CheckOnClick(point);
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

}

