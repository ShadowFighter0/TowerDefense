package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class CameraHelper {

    OrthographicCamera camera;

    Vector2 position;
    Vector2 target;
    boolean isGoingToTarget;

    float worldWidth;
    float worldHeight;

    float viewportWidth;
    float viewportHeight;

    float width;
    float height;

    float currentWidth;
    float currentHeight;

    //Zoom
    float currentZoom;
    float maxZoom = 1;
    float minZoom = 0.40f;

    CameraHelper(float worldWidth, float worldHeight)
    {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        viewportWidth = worldWidth;
        viewportHeight = worldHeight;

        width = (viewportHeight / Gdx.graphics.getHeight())*Gdx.graphics.getWidth();
        height = viewportHeight;

        currentWidth = width;
        currentHeight = height;

        camera = new OrthographicCamera(currentWidth, currentHeight);

        currentZoom = maxZoom;

        position = new Vector2(currentWidth/2,currentHeight/2);
        camera.position.set(position.x,position.y,0);

        camera.update();
    }

    public void update()
    {
        position.x = MathUtils.clamp(position.x, (currentWidth/2), worldWidth - (currentWidth/2));
        position.y = MathUtils.clamp(position.y, (currentHeight/2), worldHeight - (currentHeight/2));

        camera.position.set(position.x,position.y, 0);

        camera.update();
    }

    public void moveCamera(float xOffset, float yOffset)
    {
        position.x += xOffset;
        position.y += yOffset;

        update();

        camera.update();
    }

    public void changeZoom (float zoomOffset)
    {
        float newZoom = currentZoom + (zoomOffset * 0.1f);

        if ( maxZoom >= newZoom && newZoom > minZoom)
        {
            currentZoom = newZoom;

            currentWidth = width * currentZoom;
            currentHeight = height * currentZoom;

            camera.viewportWidth = currentWidth;
            camera.viewportHeight = currentHeight;
        }

        camera.update();
    }

    public void resize(int newWidth, int newHeight)
    {
        if(newHeight > newWidth) //if more height than width adjust width
        {
            width = (viewportHeight / newHeight)*newWidth;
            height = viewportHeight;
        }
        else //If more width than height adjust the height
        {
            height = (viewportWidth / newWidth) * newHeight;
            width = viewportWidth;
        }

        currentWidth = width;
        currentHeight = height;

        camera.viewportWidth = width;
        camera.viewportHeight = height;
    }
}
