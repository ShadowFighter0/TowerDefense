package com.dprieto.game;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GestureListener implements GestureDetector.GestureListener {

    private Level level;
    Vector2 point;
    boolean isDragging;
    Vector2 screenPoint;

    public GestureListener (Level level)
    {
        this.level = level;
        point = new Vector2();
        screenPoint = new Vector2();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        Vector3 pos = level.worldCamera.camera.unproject(new Vector3(x,y,0));

        screenPoint.x = x;
        screenPoint.y = y;

        point.x = pos.x;
        point.y = pos.y;

        boolean clicked = false;

        //Check WaveTimer
        if(level.enemyPooler.waveTimer.isActive())
        {
            clicked = level.enemyPooler.waveTimer.checkClicked(point);
        }
        if(!clicked)
        {
            //Check Options
            if (BuildRing.getInstance().isActive())
            {
                clicked = checkBuildingRing(clicked);
            }
            else
            {
                clicked = checkTowers(clicked);
            }

            //NO tower or build has been touched
            if(!clicked) {
                isDragging = true;

                BuildRing.getInstance().setActive(false);
            }
        }
        return false;
    }

    private boolean checkTowers(boolean clicked) {
        boolean newClick;
        //Check towers
        for(GameObject go : level.towers)
        {
            newClick = go.checkClicked(point);

            if(!clicked)
            {
                clicked = newClick;
            }
        }
        return clicked;
    }

    private boolean checkBuildingRing(boolean clicked) {
        boolean newClick;
        //Check all construction buttons
        if(BuildRing.getInstance().selectedTower.type == Constants.TowerType.sign)
        {
            for (BuildOption bp : BuildRing.getInstance().buildOptions)
            {
                newClick = bp.checkClicked(point);

                if(!clicked)
                {
                    clicked = newClick;
                }
            }
        }
        //Check sell button
        else
        {
            //If we have selected the sell button check accept and cancel
            if(BuildRing.getInstance().wantToSellTower)
            {
                for (SellOptions sp : BuildRing.getInstance().sellOptions)
                {
                    newClick = sp.checkClicked(point);

                    if(!clicked)
                    {
                        clicked = newClick;
                    }
                }
            }
            //else we only check the sell button
            else
            {
                clicked = BuildRing.getInstance().sellOptions.get(0).checkClicked(point);
            }
        }
        return clicked;
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

        float cameraMovementX = x - screenPoint.x;
        float cameraMovementY = y - screenPoint.y;

        level.worldCamera.moveCamera(- cameraMovementX * Constants.PAN_MOBILE_SENSITIVITY, cameraMovementY * Constants.PAN_MOBILE_SENSITIVITY);
        screenPoint.x = x;
        screenPoint.y = y;
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {

        float changeDistance = initialDistance - distance;
        changeDistance *= Constants.ZOOM_MOBILE_SENSITIVITY * level.worldCamera.currentZoom;
        level.worldCamera.changeZoom(changeDistance);
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
