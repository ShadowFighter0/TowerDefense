package com.dprieto.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {

    public String name;
    private TextureRegion[] sprites;
    private float frameDuration;
    private float currentFrameDuration = 0.0f;
    private int currentIndex = 0;
    private boolean paused = false;
    private boolean loop;
    private boolean ended;


    public Animation(String name, TextureRegion[] sprites,  float frameDuration, boolean loop)
    {
        this.name = name;
        this.sprites = sprites;
        this.frameDuration = frameDuration;
        this.loop = loop;
    }

    public Animation(Animation other)
    {
        name = other.name;
        sprites = other.sprites;
        frameDuration = other.frameDuration;
        loop = other.loop;
    }

    public void play()
    {
        paused = false;
        ended = false;
    }

    public void pause()
    {
        paused = true;
    }
    public void stop()
    {
        paused = true;
        currentIndex = 0;
        ended = true;

        currentFrameDuration = 0.0f;
    }

    public void update(float dt)
    {
        if (!paused)
        {
            currentFrameDuration += dt;

            if (currentFrameDuration >= frameDuration)
            {
                currentFrameDuration = 0;
                currentIndex++;

                if (currentIndex > sprites.length - 1)
                {
                    currentIndex = 0;
                    ended = true;
                    if (!loop)
                    {
                        paused = true;
                    }
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
    public boolean hasEnded()
    {
        return ended;
    }
}
