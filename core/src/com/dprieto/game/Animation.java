package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {

    private TextureRegion[] sprites;
    private float frameDuration = 0.1f;
    private float currentFrameDuration = 0.0f;
    private int currentIndex = 0;
    private boolean paused = false;

    public Animation(Constants.EnemyType type, String state)
    {
        Gdx.app.debug("", type.name() + "Animation" + state);
        sprites = AssetManager.getInstance().getAnimation(type.name() + "Animation" + state);
        frameDuration = Constants.getInstance().animations.get(type.name() + "Animation" + state);
    }
    public Animation(String name, String state)
    {
        Gdx.app.debug("", name + "Animation" + state);
        sprites = AssetManager.getInstance().getAnimation(name + "Animation" + state);
        frameDuration = Constants.getInstance().animations.get(name + "Animation" + state);
    }

    public void play()
    {
        paused = false;
    }

    public void pause()
    {
        paused = true;
    }

    public void update(float dt)
    {
        if(!paused)
        {
            currentFrameDuration += dt;

            if(currentFrameDuration >= frameDuration)
            {
                currentFrameDuration = 0;
                currentIndex++;

                if(currentIndex > sprites.length - 1)
                {
                    currentIndex = 0;
                }
            }
        }
    }

    public TextureRegion getSprite(int index)
    {
        return sprites[index];
    }

    public TextureRegion getCurrentSprite()
    {
        return sprites[currentIndex];
    }
}
