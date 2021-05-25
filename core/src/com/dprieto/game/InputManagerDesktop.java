package com.dprieto.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class InputManagerDesktop implements InputProcessor {

    private Level level;

    Vector2 point;
    boolean isDragging;
    Vector2 screenPoint;

    public InputManagerDesktop(Level level)
    {
        this.level = level;
        point = new Vector2();
        screenPoint = new Vector2();
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        Vector3 pos = level.guiCamera.camera.unproject(new Vector3(screenX,screenY,0));

        screenPoint.x = screenX;
        screenPoint.y = screenY;

        point.x = pos.x;
        point.y = pos.y;

        if (!GuiCamera())
        {
            pos = level.worldCamera.camera.unproject(new Vector3(screenX,screenY,0));

            point.x = pos.x;
            point.y = pos.y;

            WorldCamera();
        }

        return false;
    }
    private boolean GuiCamera()
    {
        boolean clicked = false;

        for (HUDButton button : level.buttonElements ) {

            clicked = button.checkClicked(point);
        }

        return  clicked;
    }

    private void WorldCamera() {

        boolean clicked = false;


        //Check WaveTimer
        if (level.enemyPooler.waveTimer.isActive())
        {
            clicked = level.enemyPooler.waveTimer.checkClicked(point);
        }
        if (!clicked)
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
                BuildRing.getInstance().turnOff();
            }
        }
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
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if(isDragging)
        {
            isDragging = false;
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(isDragging)
        {
            float cameraMovementX = screenX - screenPoint.x;
            float cameraMovementY = screenY - screenPoint.y;

            level.worldCamera.moveCamera(- cameraMovementX, cameraMovementY);
            screenPoint.x = screenX;
            screenPoint.y = screenY;
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {

        level.worldCamera.changeZoom(amountY);

        return false;
    }
}
