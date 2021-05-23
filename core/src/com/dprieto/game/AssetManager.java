package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class AssetManager {

    public static AssetManager instance;

    HashMap<String,Texture> maps;
    HashMap<String,TextureRegion> textures;
    HashMap<String, Animation> animations;

    private AssetManager()
    {
        maps = new HashMap<String,Texture>();
        textures = new HashMap<String,TextureRegion>();
        animations = new HashMap<String, Animation>();

        LoadMaps();
        LoadAnimations();
        LoadTextures();
    }

    public static AssetManager getInstance()
    {
        if(instance == null)
        {
            instance = new AssetManager();
        }
        return instance;
    }

    //Get
    public Texture getMap (String mapName)
    {
        return maps.get(mapName);
    }
    public TextureRegion getTexture(String textureName)
    {
        return textures.get(textureName);
    }
    public Animation getAnimation(String animationName)
    {
        return animations.get(animationName);
    }

    //Load Assets
    void LoadMaps()
    {
        //Load Maps TODO from tiled
        maps.put("Map1",new Texture(Gdx.files.internal("Maps/Map_01.png")));
        maps.put("Map2",new Texture(Gdx.files.internal("Maps/Map_02.png")));
        maps.put("Map2",new Texture(Gdx.files.internal("Maps/Map_03.png")));
    }

    void LoadAnimationFromFile(String filePath, String animationName, float frameDuration, boolean loop, int numColumns, int numRows, int xSize, int ySize)
    {
        Texture texture;
        TextureRegion region;

        TextureRegion[] sprites = new TextureRegion[numRows * numColumns];

        texture = new Texture(Gdx.files.internal(filePath));

        int currentRow = 0;
        int currentColumn = 0;
        for (int i = 0; i < sprites.length; i++)
        {
            currentColumn %= numColumns;
            currentRow %= numRows;

            region = new TextureRegion(texture, currentColumn * xSize, currentRow * ySize, xSize, ySize);
            sprites[i] = region;

            currentColumn++;
            currentRow += currentColumn >= numColumns ? 1 : 0;
        }

        Animation anim = new Animation(animationName, sprites, frameDuration, loop);
        animations.put(animationName, anim);
    }

    void LoadAnimationFromFile(String filePath, String animationName, float frameDuration, boolean loop, int numColumns, int numRows, int xSize, int ySize,
                               int initialXOffset, int initialYOffset,  int xOffset, int yOffset)
    {
        Texture texture;
        TextureRegion region;

        TextureRegion[] sprites = new TextureRegion[numRows * numColumns];

        texture = new Texture(Gdx.files.internal(filePath));

        int currentRow = 0;
        int currentColumn = 0;
        for (int i = 0; i < sprites.length; i++)
        {
            currentColumn %= numColumns;
            currentRow %= numRows;

            region = new TextureRegion(texture,
                    initialXOffset + (currentColumn * (xSize + xOffset)) , initialYOffset + (currentRow * (ySize + yOffset)),
                    xSize, ySize);
            sprites[i] = region;

            currentColumn++;
            currentRow += currentColumn >= numColumns ? 1 : 0;
        }

        Animation anim = new Animation(animationName, sprites, frameDuration, loop);
        animations.put(animationName, anim);
    }

    void LoadAnimations()
    {
        TextureRegion region;
        Texture texture;

        //Load Enemies
        LoadAnimationFromFile("Enemies/Bat.png","batEnemyAnimationWalking", 0.1f, true, 3,2,60,60);

        LoadAnimationFromFile("Enemies/Goblin.png","goblinEnemyAnimationAttacking", 0.1f,false, 6,1,95,95);
        LoadAnimationFromFile("Enemies/Goblin.png","goblinEnemyAnimationWalking"  , 0.1f,true, 6,1,95,95,0,95,0,0);
        LoadAnimationFromFile("Enemies/Goblin.png","goblinEnemyAnimationDying"    , 0.1f,false, 6,1,95,95,0,190,0,0);

        LoadAnimationFromFile("Enemies/Orc.png","orcEnemyAnimationAttacking",0.1f,false, 6,1,95,95);
        LoadAnimationFromFile("Enemies/Orc.png","orcEnemyAnimationWalking"  ,0.1f,true, 6,1,95,95,0,95,0,0);
        LoadAnimationFromFile("Enemies/Orc.png","orcEnemyAnimationDying"    ,0.1f,false, 6,1,95,95,0,190,0,0);

        LoadAnimationFromFile("Enemies/Shaman.png","shamanEnemyAnimationWalking",0.1f, true, 6,1,95,95);

        //Load Guardians
        LoadAnimationFromFile("Towers/Shoots/Guards.png","guardAnimationAttacking",0.1f, false, 6,1,95,95);
        LoadAnimationFromFile("Towers/Shoots/Guards.png","guardAnimationWalking"  ,0.1f, true, 6,1,95,95,0,95,0,0);
        LoadAnimationFromFile("Towers/Shoots/Guards.png","guardAnimationDying"    ,0.11f, false, 6,1,95,95,0,190,0,0);
    }

    void LoadTextures()
    {
        TextureRegion region;

        //Load BuildingPlace
        Texture texture = new Texture(Gdx.files.internal("Towers/Tower/BuildingPlace.png"));
        region = new TextureRegion(texture, 0, 0, 135, 90);
        textures.put("signUp",region);
        region = new TextureRegion(texture, 0, 90, 135, 90);
        textures.put("signBase",region);

        //Load Towers GUI
        texture = new Texture(Gdx.files.internal("GUI/BuildingRing/TowersGUI.png"));
        region = new TextureRegion(texture, 15, 30, 85, 90);
        textures.put("barrackTowerGUI",region);
        region = new TextureRegion(texture, 20, 170, 75, 100);
        textures.put("bowTowerGUI",region);
        region = new TextureRegion(texture, 10, 310, 90, 100);
        textures.put("crossbowTowerGUI",region);
        region = new TextureRegion(texture, 15, 460, 85, 105);
        textures.put("wizardTowerGUI",region);
        region = new TextureRegion(texture, 10, 610, 105, 105);
        textures.put("bombTowerGUI",region);


        //Load Tower Base
        texture = new Texture(Gdx.files.internal("Towers/Tower/TowersAnimation.png"));

        region = new TextureRegion(texture, 0, 0, 144, 144);
        textures.put("barrackTowerBase",region);
        region = new TextureRegion(texture, 0, 144, 144, 144);
        textures.put("bowTowerBase",region);
        region = new TextureRegion(texture, 0, 288, 144, 144);
        textures.put("crossbowTowerBase",region);
        region = new TextureRegion(texture, 0, 432, 144, 144);
        textures.put("wizardTowerBase",region);
        region = new TextureRegion(texture, 0, 576, 144, 144);
        textures.put("bombTowerBase",region);

        //Load Tower Shooter
        region = new TextureRegion(texture, 144, 0, 144, 144);
        textures.put("barrackTowerUp",region);
        region = new TextureRegion(texture, 144, 144, 144, 144);
        textures.put("bowTowerUp",region);
        region = new TextureRegion(texture, 144, 288, 144, 144);
        textures.put("crossbowTowerUp",region);
        region = new TextureRegion(texture, 144, 432, 144, 144);
        textures.put("wizardTowerUp",region);
        region = new TextureRegion(texture, 144, 576, 144, 144);
        textures.put("bombTowerUp",region);

        //Load Bullets
        texture = new Texture(Gdx.files.internal("Towers/Shoots/Bullets.png"));
        region = new TextureRegion(texture,40,300,30,30);
        textures.put("wizardTowerShoot",region);
        region = new TextureRegion(texture,135,310,35,15);
        textures.put("bowTowerShoot",region);
        region = new TextureRegion(texture,205,305,50,20);
        textures.put("crossbowTowerShoot",region);
        region = new TextureRegion(texture,135,230,30,30);
        textures.put("bombTowerShoot",region);

        //Load AttackRange
        texture = new Texture(Gdx.files.internal("Towers/Tower/Attack_Range.png"));
        region = new TextureRegion(texture,0,0,200,200);
        textures.put("attackRange",region);

        //Load Building Ring
        texture = new Texture(Gdx.files.internal("GUI/BuildingRing/Building_ring.png"));
        region = new TextureRegion(texture, 110, 10, 75, 75);
        textures.put("buildRingSelector",region);
        region = new TextureRegion(texture,25,135,55,35);
        textures.put("priceRingSelector",region);
        region = new TextureRegion(texture,125,130,40,45);
        textures.put("lockRingSelector",region);
        region = new TextureRegion(texture, 210, 10, 205, 205);
        textures.put("buildRing",region);

        //Load Sale Button
        texture = new Texture(Gdx.files.internal("GUI/BuildingRing/SaleButton.png"));
        region = new TextureRegion(texture,14,14,75,75);
        textures.put("saleContainer",region);
        region = new TextureRegion(texture,105,30,50,50);
        textures.put("cancelIcon",region);
        region = new TextureRegion(texture,170,30,50,50);
        textures.put("acceptIcon",region);
        region = new TextureRegion(texture,160,120,30,35);
        textures.put("sellIcon",region);

        //Load Timer
        texture = new Texture(Gdx.files.internal("GUI/InGame/Timer.png"));
        region = new TextureRegion(texture,95,120,125,120);
        textures.put("timerHolder",region);
        region = new TextureRegion(texture,5,20,65,80);
        textures.put("clock1", region);
        region = new TextureRegion(texture,115,35,30,50);
        textures.put("clock2", region);
        region = new TextureRegion(texture,190,30,35,60);
        textures.put("clock3",region);
        region = new TextureRegion(texture,250,20,65,80);
        textures.put("clock4", region);

        //Load GUI
        texture = new Texture(Gdx.files.internal("GUI/InGame/Life.png"));
        region = new TextureRegion(texture, 0,0,44,44);
        textures.put("lifeIcon",region);

        texture = new Texture(Gdx.files.internal("GUI/InGame/GameInterface.png"));
        region = new TextureRegion(texture,20,20,50,50);
        textures.put("pauseIcon",region);
        region = new TextureRegion(texture,20,20,50,50);
        textures.put("pauseIcon",region);
        region = new TextureRegion(texture,95,20,40,50);
        textures.put("playIcon",region);
        region = new TextureRegion(texture,155,20,140,30);
        textures.put("pauseTextIcon",region);
        region = new TextureRegion(texture,665,35,30,35);
        textures.put("coinIcon",region);

        //Load HealthBar
        texture = new Texture(Gdx.files.internal("Enemies/Healthbar.png"));
        region = new TextureRegion(texture, 20,4);
        textures.put("healthbar", region);

        //Load Spells

    }
}
