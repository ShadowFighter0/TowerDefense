package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Camera {

    OrthographicCamera orthographicCamera;

    Vector2 position;

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

    Camera(float viewportWidth, float viewportHeight)
    {
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;

        width = (viewportHeight / Gdx.graphics.getHeight()) * Gdx.graphics.getWidth();
        height = viewportHeight;

        currentWidth = width;
        currentHeight = height;

        orthographicCamera = new OrthographicCamera(viewportWidth, viewportHeight);

        currentZoom = maxZoom;

        position = new Vector2(viewportWidth/2,viewportHeight/2);
        orthographicCamera.position.set(position.x,position.y,0);

        orthographicCamera.update();
    }

    public void update()
    {
        position.x = MathUtils.clamp(position.x, (currentWidth/2), viewportWidth - (currentWidth/2));
        position.y = MathUtils.clamp(position.y, (currentHeight/2), viewportHeight - (currentHeight/2));

        orthographicCamera.position.set(position.x,position.y, 0);

        orthographicCamera.update();
    }

    public void moveCamera(float xOffset, float yOffset)
    {
        position.x += xOffset;
        position.y += yOffset;

        update();

        orthographicCamera.update();
    }

    public void changeZoom (float zoomOffset)
    {
        float newZoom = currentZoom + (zoomOffset * 0.1f);

        if ( maxZoom >= newZoom && newZoom > minZoom)
        {
            currentZoom = newZoom;

            currentWidth = width * currentZoom;
            currentHeight = height * currentZoom;

            orthographicCamera.viewportWidth = currentWidth;
            orthographicCamera.viewportHeight = currentHeight;
        }

        orthographicCamera.update();
    }

    public void ExpandResize(int newWidth, int newHeight)
    {
        if (newWidth < newHeight) //if more height than width adjust width
        {
            width = (viewportHeight / newHeight) * newWidth;
            height = viewportHeight;

            if (width > viewportWidth)
            {
                height = (viewportWidth / newWidth) * newHeight;
                width = viewportWidth;
            }
        }
        else //else more width than height adjust the height
        {
            height = (viewportWidth / newWidth) * newHeight;
            width = viewportWidth;

            if (height > viewportHeight)
            {
                width = (viewportHeight / newHeight) * newWidth;
                height = viewportHeight;
            }
        }

        currentWidth = width;
        currentHeight = height;

        orthographicCamera.viewportWidth = width;
        orthographicCamera.viewportHeight = height;

        orthographicCamera.update();
    }

    public void FitResize(int newWidth, int newHeight)
    {
        if (newWidth - (viewportWidth - viewportHeight) > newHeight)
        {
            height = viewportHeight;
            width = height * ((float)newWidth / (float)newHeight);
        }
        else
        {
            width = viewportWidth;
            height = width * ((float)newHeight / (float)newWidth);
        }

        orthographicCamera.viewportWidth = width;
        orthographicCamera.viewportHeight = height;
        orthographicCamera.update();
    }
}
