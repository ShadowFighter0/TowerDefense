package com.dprieto.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class HUDText extends HUDElement{

    BitmapFont font;
    String text;

    public HUDText(String imageName, Vector2 position, Anchor anchor, BitmapFont font, Camera camera) {
        super(imageName, position, anchor, camera);
        this.font = font;
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        font.draw(batch,
                "" + text,
                currentPosition.x -  dimension.x/2 ,
                currentPosition.y - dimension.y/2);
    }

    public void setText(String text)
    {
        this.text = text;
    }
}