package com.dprieto.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.sun.scenario.effect.Offset;

import static com.dprieto.game.Constants.SellButtonOptions;

public class SellOptions extends GameObject{

    TextureRegion baseImage;
    TextureRegion optionImage;

    SellButtonOptions option;

    Vector2 offset;

    public SellOptions(SellButtonOptions type, Vector2 offset)
    {
        this.option = type;
        this.offset = offset;

        baseImage = AssetManager.getInstance().getTexture("saleContainer");
        optionImage = AssetManager.getInstance().getTexture(type.name());

        dimension.x = baseImage.getRegionWidth();
        dimension.y = baseImage.getRegionHeight();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(baseImage,position.x + offset.x - baseImage.getRegionWidth()/2, position.y + offset.y - baseImage.getRegionWidth()/2);
        batch.draw(optionImage,position.x + offset.x - optionImage.getRegionWidth()/2, position.y + offset.y - optionImage.getRegionWidth()/2);
    }

    @Override
    public boolean checkClicked(Vector2 point)
    {
        if (point.x > position.x + offset.x - dimension.x/2 && point.x  < position.x + offset.x + dimension.x/2
                && point.y > position.y + offset.y - dimension.y/2 && point.y  < position.y + offset.y + dimension.y/2)
        {
            OnClicked();
            return true;
        }
        return false;
    }

    @Override
    public void OnClicked() {
        switch (option)
        {
            case sellIcon:
                BuildRing.getInstance().wantToSellTower();
                break;
            case acceptIcon:
                BuildRing.getInstance().acceptSellTower();
                break;
            case cancelIcon:
                BuildRing.getInstance().cancelSellTower();
                break;
        }
    }

    @Override
    public void OnNotClicked() {

    }
}
