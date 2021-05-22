package com.dprieto.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class BuildOption extends GameObject{

    TextureRegion baseImage;
    TextureRegion priceImage;
    TextureRegion optionImage;
    TextureRegion lockImage;

    Constants.TowerType option;
    int price;

    Vector2 offset;

    public BuildOption(Constants.TowerType option, Vector2 offset)
    {
        this.option = option;
        this.offset = offset;

        price = Constants.getInstance().towerStats.get(option).price;


        baseImage = AssetManager.getInstance().getTexture("buildRingSelector");
        priceImage = AssetManager.getInstance().getTexture("priceRingSelector");
        lockImage = AssetManager.getInstance().getTexture("lockRingSelector");
        optionImage = AssetManager.getInstance().getTexture(option.name() + "GUI");

        dimension.x = baseImage.getRegionWidth();
        dimension.y = baseImage.getRegionHeight();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch)
    {
        batch.draw(baseImage,position.x + offset.x - baseImage.getRegionWidth()/2, position.y + offset.y - baseImage.getRegionHeight()/2);

        batch.draw(optionImage,position.x + offset.x - optionImage.getRegionWidth()/4, position.y + offset.y - optionImage.getRegionHeight()/4,optionImage.getRegionWidth()/2 ,optionImage.getRegionHeight()/2);

        batch.draw(priceImage,position.x + offset.x - priceImage.getRegionWidth()/2, position.y + offset.y - 2 * priceImage.getRegionHeight());

        if (BuildRing.getInstance().level.money >= price)
        {
            BuildRing.getInstance().font.getData().setScale(0.60f);
            BuildRing.getInstance().font.draw(batch,"" + price, position.x + offset.x - priceImage.getRegionWidth()/4, position.y + offset.y -  priceImage.getRegionHeight());
        }
        else
        {
            //TODO tint for display not enough money
            batch.draw(lockImage,position.x + offset.x - lockImage.getRegionWidth()/2, position.y + offset.y - 2 * lockImage.getRegionHeight());
        }

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

        if(BuildRing.getInstance().level.money >= price)
            BuildRing.getInstance().setConstruction(option);
    }

    @Override
    public void OnNotClicked() {

    }
}
