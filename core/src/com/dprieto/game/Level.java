package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Level {

    //Cameras
    Camera worldCamera;
    Camera guiCamera;

    //Pooler
    EnemyPooler enemyPooler;

    //Map
    Texture textureMap;
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer renderer;
    //Gui

    ArrayList<HUDElement> guiImages;
    WinDefeatMenu winDefeatMenu;
    HUDText lifeText;
    HUDText coinText;
    HUDText roundText;

    ArrayList<HUDButton> buttonElements;
    BitmapFont font;

    //Array
    ArrayList<Vector2> path;
    ArrayList<Tower> towers;

    //Variable
    enum GameState {Playing, Pause, Win, Loose}
    GameState gamestate;

    int money;
    int lives;

    public Level(Texture map, ArrayList<Vector2> path, ArrayList<Vector2> buildingPlaces,
                 int initialMoney, int initialLives, ArrayList<Wave> waves)
    {
        //BuildRing
        BuildRing.getInstance().level = this;

        //Camera stuff
        worldCamera = new Camera(map.getWidth(),map.getHeight());
        guiCamera = new Camera(map.getWidth(),map.getHeight());

        //Create GUI
        CreateGUI();
        winDefeatMenu = new WinDefeatMenu(this, guiCamera);

        //Variables
        this.textureMap = map;
        this.money = initialMoney;
        this.path = path;
        this.lives = initialLives;

        //Create towers
        towers = new ArrayList<Tower>();

        for (int i = 0; i < buildingPlaces.size(); i++)
        {
            towers.add(new Tower(buildingPlaces.get(i), this));
        }

        //Create Enemies
        enemyPooler = new EnemyPooler(this, new Vector2(1,1),20,path, waves);

        //GameState
        gamestate = GameState.Playing;
    }

    public Level(TiledMap map, ArrayList<Vector2> path, ArrayList<Vector2> buildingPlaces,
                 int initialMoney, int initialLives, ArrayList<Wave> waves)
    {
        //BuildRing
        BuildRing.getInstance().level = this;

        //Map
        this.tiledMap = map;

        //Camera stuff


        int mapWidth = map.getProperties().get("width", Integer.class);
        int mapHeight = map.getProperties().get("height", Integer.class);
        int tilePixelWidth = map.getProperties().get("tilewidth", Integer.class);
        int tilePixelHeight = map.getProperties().get("tileheight", Integer.class);

        int w = mapWidth * tilePixelWidth;
        int h = mapHeight * tilePixelHeight;

        worldCamera = new Camera(w, h);
        guiCamera = new Camera(w, h);

        renderer = new OrthogonalTiledMapRenderer(tiledMap, Constants.TILED_UNIT_SCALE);

        //Create GUI
        CreateGUI();
        winDefeatMenu = new WinDefeatMenu(this, guiCamera);

        //Variables

        this.money = initialMoney;
        this.path = path;
        this.lives = initialLives;

        //Create towers
        towers = new ArrayList<Tower>();

        for (int i = 0; i < buildingPlaces.size(); i++)
        {
            towers.add(new Tower(buildingPlaces.get(i), this));
        }

        //Create Enemies
        enemyPooler = new EnemyPooler(this, new Vector2(Constants.TILED_UNIT_SCALE,Constants.TILED_UNIT_SCALE), 20,path, waves);

        //GameState
        gamestate = GameState.Playing;
    }

    void CreateGUI()
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
                HUDButton.ButtonType.Pause, this, guiCamera));

        buttonElements.add(new HUDButton("playButtonIcon",
                new Vector2(150,-50 ), HUDElement.Anchor.UpperLeft,
                HUDButton.ButtonType.Play, this, guiCamera));
    }


    public void SetGameState(GameState newMode) {gamestate = newMode;}

    public void update(float delta)
    {
        if (gamestate == GameState.Playing)
        {
            if (lives == 0)
            {
                gamestate = GameState.Loose;
                winDefeatMenu.SetActive(true, WinDefeatMenu.ButtonMode.Loose);
            }
            else
            {
                //Update enemies
                enemyPooler.update(delta);

                //Update towers
                for (GameObject go : towers)
                {
                    go.update(delta);
                }
            }
        }
        else if (gamestate == GameState.Win)
        {
            winDefeatMenu.SetActive(true, WinDefeatMenu.ButtonMode.Win);
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
        winDefeatMenu.render(batch);

        batch.end();
    }


    void renderWorld (SpriteBatch batch)
    {
        //render map
        if (textureMap != null)
        {
            batch.draw(textureMap,0,0);
        }
        else
        {
            renderer.setView(worldCamera.camera);
            renderer.render();
        }

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

        lifeText.setText("" + lives);
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
