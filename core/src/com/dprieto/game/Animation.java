package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import sun.security.util.Debug;

public class Animation {

  private TextureRegion[] sprites;
  private float frameDuration = 0.1f;
  private float currentFrameDuration = 0.0f;
  private int currentIndex = 0;
  private boolean paused = false;

  public Animation(Constants.EnemyType type, String state)
  {
    Gdx.app.debug("anim", type.name() + "Animation"+state);
    sprites = AssetManager.getInstance().getAnimation(type.name() + "Animation" + state);
  }

  public void play()
  {

  }

  public void pause()
  {

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
