package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class BuildRing extends GameObject{

    public static BuildRing instance;
    Level level;

    TextureRegion image;
    ArrayList<BuildOption> buildOptions;
    ArrayList<SellOptions> sellOptions;

    BitmapFont font;

    Tower selectedTower;
    boolean wantToSellTower;

    BuildRing()
    {
        font = new BitmapFont(Gdx.files.internal("Fonts/Font.fnt"));
        font.setColor(Color.WHITE);

        image = AssetManager.instance.getTexture("buildRing");

        buildOptions = new ArrayList<BuildOption>();
        buildOptions.add(new BuildOption(Constants.TowerType.bowTower,new Vector2(50,80)));
        buildOptions.add(new BuildOption(Constants.TowerType.barrackTower,new Vector2(90,-25)));
        buildOptions.add(new BuildOption(Constants.TowerType.crossbowTower,new Vector2(0,-90)));
        buildOptions.add(new BuildOption(Constants.TowerType.wizardTower,new Vector2(-90,-25)));
        buildOptions.add(new BuildOption(Constants.TowerType.bombTower,new Vector2(-50,80)));

        sellOptions = new ArrayList<SellOptions>();
        sellOptions.add(new SellOptions(Constants.SellButtonOptions.sellIcon, new Vector2(0,-90)));
        sellOptions.add(new SellOptions(Constants.SellButtonOptions.acceptIcon, new Vector2(90,-25)));
        sellOptions.add(new SellOptions(Constants.SellButtonOptions.cancelIcon, new Vector2(-90,-25)));

        wantToSellTower = false;
        setActive(false);
    }

    public static BuildRing getInstance()
    {
        if(instance == null)
        {
            instance = new BuildRing();
        }
        return instance;
    }

    //Set the Tower where we are going to build/sell/upgrade
    public void setTower(Tower tower)
    {
       selectedTower = tower;
       position = tower.position;
       for (BuildOption bp : buildOptions)
       {
           bp.position = position;
       }
       for (SellOptions sp: sellOptions)
       {
           sp.position = position;
       }
       setActive(true);
    }

    //Set selected tower to a certain build if we have money
    public void setConstruction(Constants.TowerType option)
    {
        level.money -= Constants.getInstance().towerStats.get(option).price;
        selectedTower.build(option);
        setActive(false);
    }

    //Set that we want to sell a tower
    public void wantToSellTower()
    {
        wantToSellTower = true;
    }

    //Accept sell the tower
    public void acceptSellTower()
    {
        //return 75% of the building price and set the tower to no tower
        selectedTower.currentLevel.money += (int)selectedTower.stats.price * 0.75;

        selectedTower.build(Constants.TowerType.sign);
        wantToSellTower = false;
        setActive(false);
    }

    //Cancel sell the tower
    public void cancelSellTower()
    {
        wantToSellTower = false;
        setActive(false);
    }

    public void turnOff()
    {
        setActive(false);

    }


    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {
        if (isActive() == true)
        {
            batch.draw(image,position.x - image.getRegionWidth()/2, position.y - image.getRegionWidth()/2);

            //Something build upgrade or sell Tower
            if(selectedTower.type != Constants.TowerType.sign)
            {
                if(wantToSellTower)
                {
                    for (SellOptions sp : sellOptions)
                    {
                        sp.render(batch);
                    }
                }
                else
                    {
                    sellOptions.get(0).render(batch);
                }
            }
            //no Tower built render construction buttons
            else
            {
                for (BuildOption bp : buildOptions)
                {
                    bp.render(batch);
                }
            }
        }
    }

    @Override
    public void OnClicked() {

    }

    @Override
    public void OnNotClicked() {

    }


}
