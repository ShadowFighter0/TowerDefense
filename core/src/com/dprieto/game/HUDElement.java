package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

public class HUDElement {

    enum Anchor {UpperLeft, UpperRight}

    Anchor myAnchor;
    Vector2 anchorPos;

    String name;
    TextureRegion image;

    Vector2 offsetPosition;
    Vector2 currentPosition;
    Vector2 dimension;



    Camera camera;
    boolean isActive;

    public HUDElement(String imageName, Vector2 position, Anchor anchor, Camera camera)
    {
        this.name = imageName;
        this.image = AssetManager.instance.getTexture(imageName);

        this.offsetPosition = position;
        this.currentPosition = position.cpy();
        this.myAnchor = anchor;

        anchorPos = new Vector2();

        dimension = new Vector2();
        dimension.x = image.getRegionWidth();
        dimension.y = image.getRegionHeight();
        
        this.camera = camera;
    }
    public HUDElement(String imageName, Vector2 position, Vector2 dimensionMultiplier, Anchor anchor, Camera camera)
    {
        this.name = imageName;
        this.image = AssetManager.instance.getTexture(imageName);

        this.offsetPosition = position;
        this.currentPosition = position.cpy();
        this.myAnchor = anchor;

        anchorPos = new Vector2();

        dimension = new Vector2();
        dimension.x = image.getRegionWidth() * dimensionMultiplier.x;
        dimension.y = image.getRegionHeight() * dimensionMultiplier.y;

        this.camera = camera;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void render (SpriteBatch batch)
    {
        switch (myAnchor)
        {
            case UpperLeft:
                anchorPos.x = camera.position.x - camera.currentWidth/2;
                anchorPos.y = camera.position.y + camera.currentHeight/2;
                break;

            case UpperRight:
                anchorPos.x = camera.position.x + camera.currentWidth/2;
                anchorPos.y = camera.position.y + camera.currentHeight/2;
                break;
        }
        currentPosition.x = anchorPos.x + offsetPosition.x;
        currentPosition.y = anchorPos.y + offsetPosition.y;

        batch.draw(image, anchorPos.x + (offsetPosition.x - dimension.x/2), anchorPos.y + (offsetPosition.y - dimension.y/2), dimension.x, dimension.y);
    }
}
