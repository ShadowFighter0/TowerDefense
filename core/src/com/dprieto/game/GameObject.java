package com.dprieto.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {

    protected Vector2 position;
    protected Vector2 dimension;
    protected float rotation;

    private boolean isActive;

    public abstract void update(float delta);
    public abstract void render(SpriteBatch batch);

    public GameObject()
    {
        this(0,0,1f,1f);
    }

    public GameObject(float x, float y,float width, float height)
    {
        position = new Vector2(x,y);
        dimension = new Vector2(width, height);
    }

    public void translate (float x,float y) {position.x += x; position.y += y;}
    public void translate (Vector2 other) {position.x += other.x; position.y += other.y;}

    public void setActive(boolean isActive) {this.isActive = isActive;}
    public boolean isActive() {return isActive;}
    public Vector2 position(){return position;}
    public void setDimension(TextureRegion image) {
        this.dimension.x = image.getRegionWidth();
        this.dimension.y = image.getRegionHeight();
    }
    public void setDimension(Vector2 dimension) {
        this.dimension = dimension;
    }

    public Vector2 getDimension(){return dimension;}

    public boolean checkClicked(Vector2 point)
    {
        if (point.x > position.x - dimension.x/2 && point.x < position.x + dimension.x/2
        && point.y > position.y - dimension.y/2 && point.y < position.y + dimension.y/2)
        {
            OnClicked();
            return true;
        }
        else
        {
            OnNotClicked();
        }
            return false;
    }

    public abstract void OnClicked();
    public abstract void OnNotClicked();

}
