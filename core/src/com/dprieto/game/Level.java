package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Level {

    //Cameras
    Camera worldCamera;
    Camera guiCamera;

    //Pooler
    EnemyPooler enemyPooler;

    //GUI
    Texture map;
    ArrayList<HUDElement> guiImages;

    HUDText lifeText;
    HUDText coinText;
    HUDText roundText;

    ArrayList<HUDButton> buttonElements;
    BitmapFont font;

    //Array
    ArrayList<Vector2> path;
    ArrayList<Tower> towers;

    //Variable
    int money;
    int lives;
    boolean gamePaused;

    public Level(Texture map, ArrayList<Vector2> path, ArrayList<Vector2> buildingPlaces, int initialMoney, int initialLives, ArrayList<Wave> waves)
    {
        //BuildRing
        BuildRing.getInstance().level = this;

        //Camera stuff
        worldCamera = new Camera(map.getWidth(),map.getHeight());
        guiCamera = new Camera(map.getWidth(),map.getHeight());

        //Create GUI
        CreateGUI();

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

    public void CreateGUI()
    {
        font = new BitmapFont(Gdx.files.internal("Fonts/Font.fnt"));
        font.setColor(Color.BLACK);

        guiImages = new ArrayList<HUDElement>();
        guiImages.add(new HUDElement("barGUI", new Vector2(-260,-50),new Vector2(1.5f,1f),
                HUDElement.Anchor.UpperRight,guiCamera));


        lifeText = new HUDText("lifeIcon",new Vector2(-460,-50),
                HUDElement.Anchor.UpperRight, font, guiCamera);

        coinText = new HUDText("coinIcon",new Vector2(-330,-50),
                        HUDElement.Anchor.UpperRight, font, guiCamera);

        roundText = new HUDText("roundIcon",new Vector2(-200,-50),
                HUDElement.Anchor.UpperRight, font, guiCamera);


        buttonElements = new ArrayList<HUDButton>();

        buttonElements.add(new HUDButton("pauseButtonIcon",
                new Vector2(50,-50), HUDElement.Anchor.UpperLeft,
                HUDButton.ButtonType.Pause, guiCamera));

        buttonElements.add(new HUDButton("playButtonIcon",
                new Vector2(150,-50 ), HUDElement.Anchor.UpperLeft,
                HUDButton.ButtonType.Play, guiCamera));
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

        worldCamera.update();
        guiCamera.update();
    }

    public void render(SpriteBatch batch)
    {
        batch.setProjectionMatrix(worldCamera.camera.combined);

        batch.begin();

        renderWorld(batch);

        batch.end();


        batch.setProjectionMatrix(guiCamera.camera.combined);

        batch.begin();

        renderGUI(batch);

        batch.end();
    }


    void renderWorld (SpriteBatch batch)
    {
        //render map
        batch.draw(map,0,0);

        //Render towers
        Tower selectedOne = null;

        //Render Towers
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
            selectedOne.renderRange(batch);
            selectedOne.render(batch);
        }

        //Render enemies
        enemyPooler.render(batch);
        enemyPooler.waveTimer.render(batch);

        //Building Ring
        BuildRing.getInstance().render(batch);
    }

    void renderGUI(SpriteBatch batch)
    {

        for (HUDElement hudElement: guiImages)
        {
            hudElement.render(batch);
        }

        lifeText.setText(""+lives);
        coinText.setText("" + money);
        roundText.setText(enemyPooler.currentWaveIndex + " of " + enemyPooler.waves.size());

        lifeText.render(batch);
        coinText.render(batch);
        roundText.render(batch);

        for (HUDElement hudElement: buttonElements)
        {
            hudElement.render(batch);
        }
    }
}
