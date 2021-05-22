package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Level {

    CameraHelper cameraHelper;
    EnemyPooler enemyPooler;

    Texture map;
    TextureRegion coinImage;
    TextureRegion lifeImage;
    BitmapFont font;

    ArrayList<Vector2> path;
    ArrayList<Tower> towers;

    int money;
    int lives;
    boolean gamePaused;

    //Display https://github.com/libgdx/libgdx/wiki/Gdx-freetype


    public Level(Texture map, ArrayList<Vector2> path, ArrayList<Vector2> buildingPlaces, int initialMoney, int initialLives, ArrayList<Wave> waves)
    {
        //BuildRing
        BuildRing.getInstance().level = this;

        //Camera stuff
        cameraHelper = new CameraHelper(map.getWidth(),map.getHeight());

        font = new BitmapFont(Gdx.files.internal("Fonts/Font.fnt"));
        font.setColor(Color.BLACK);

        //LoadImages
        lifeImage = AssetManager.getInstance().getTexture("lifeIcon");
        coinImage  = AssetManager.getInstance().getTexture("coinIcon");

        this.map = map;
        this.gamePaused = false;
        this.money = initialMoney;
        this.path = path;
        this.lives = initialLives;

        //Create towers
        towers = new ArrayList<Tower>();

        for (int i = 0; i < buildingPlaces.size(); i++)
        {
            towers.add(new Tower(buildingPlaces.get(i),this));
        }
        //Create Enemies
        enemyPooler = new EnemyPooler(this,20,path, waves);
    }

    public void update(float delta)
    {
        //Update enemies
        enemyPooler.update(delta);

        //Update towers
        for (GameObject go : towers)
        {
            go.update(delta);
        }

        cameraHelper.update();
    }

    public void render(SpriteBatch batch)
    {
        //render map
        batch.draw(map,0,0);

        //Render towers
        Tower selectedOne = null;

        for (Tower go : towers)
        {
            if(go.selected)
            {
                selectedOne = go;
            }
            else
            {
                go.render(batch);
            }
        }

        //Render Shoots
        for (Tower tower : towers)
        {
            tower.renderShoot(batch);
        }

        //Render selected Tower on order to have the radius image visible over the rest towers
        if(selectedOne != null)
        {
            selectedOne.render(batch);
        }

        //Render enemies
        enemyPooler.render(batch);

        //Building Ring
        BuildRing.getInstance().render(batch);

        //GUI
        enemyPooler.waveTimer.render(batch);
        for(int i = 0 ; i < lives; i++)
        {

            batch.draw(lifeImage,
                    cameraHelper.position.x - (lifeImage.getRegionWidth()*cameraHelper.currentZoom) + (i * (lifeImage.getRegionWidth() * cameraHelper.currentZoom)),
                    cameraHelper.position.y + cameraHelper.currentHeight/2 - (lifeImage.getRegionHeight()*cameraHelper.currentZoom),
                    lifeImage.getRegionWidth() * cameraHelper.currentZoom,lifeImage.getRegionHeight() * cameraHelper.currentZoom);
        }
        //TODO scale with zoom
        batch.draw(coinImage,
                cameraHelper.position.x + (cameraHelper.currentWidth/2) - (100*cameraHelper.currentZoom) + ((coinImage.getRegionWidth()/2)*cameraHelper.currentZoom),
                cameraHelper.position.y + (cameraHelper.currentHeight/2) - (100*cameraHelper.currentZoom) + ((coinImage.getRegionHeight()/2) * cameraHelper.currentZoom),
                coinImage.getRegionWidth()*cameraHelper.currentZoom, coinImage.getRegionHeight()*cameraHelper.currentZoom);

        font.getData().setScale(cameraHelper.currentZoom);
        font.draw(batch,
                "" + money,
                cameraHelper.position.x + cameraHelper.currentWidth/2 - (100 * cameraHelper.currentZoom),
                cameraHelper.position.y + (cameraHelper.currentHeight/2));
    }
}
