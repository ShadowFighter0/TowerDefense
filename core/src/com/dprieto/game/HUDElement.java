package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class HUDElement {

    TextureRegion image;

    Vector2 position;
    Vector2 dimension;

    public HUDElement(String imageName, Vector2 position)
    {
        this.image = AssetManager.instance.getTexture(imageName);

        Gdx.app.debug("Texture", image.toString());
        this.position = position;

        dimension = new Vector2();
        dimension.x = image.getRegionWidth();
        dimension.y = image.getRegionHeight();
    }

    public void render (SpriteBatch batch)
    {
        batch.draw(image, position.x - dimension.x/2, position.y - dimension.y/2);
    }
}
