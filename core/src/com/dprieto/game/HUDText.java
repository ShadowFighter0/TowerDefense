package com.dprieto.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class HUDText extends HUDElement{

    BitmapFont font;
    String text;

    public HUDText(String imageName, Vector2 position, BitmapFont font) {
        super(imageName, position);
        this.font = font;
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        font.draw(batch,
                "" + text,
                position.x - dimension.x/2 ,
                position.y - dimension.y/2);
    }

    public void setText(String text)
    {
        this.text = text;
    }
}