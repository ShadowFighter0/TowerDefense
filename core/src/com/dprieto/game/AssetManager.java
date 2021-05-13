package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class AssetManager {

    public static AssetManager instance;

    HashMap<String,Texture> maps;
    HashMap<String,TextureRegion> textures;

    private AssetManager()
    {
        maps = new HashMap<String,Texture>();
        textures = new HashMap<String,TextureRegion>();
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

    public Texture getMap (String mapName)
    {
        return maps.get(mapName);
    }

    public TextureRegion getTexture(String textureName)
    {
        return textures.get(textureName);
    }

    void LoadTextures()
    {
        TextureRegion region;

        //Load Maps
        maps.put("Map1",new Texture(Gdx.files.internal("Maps/Map_01.png")));
        maps.put("Map2",new Texture(Gdx.files.internal("Maps/Map_02.png")));
        maps.put("Map2",new Texture(Gdx.files.internal("Maps/Map_03.png")));


        //Load BuildingPlace
        Texture texture = new Texture(Gdx.files.internal("Towers/BuildingPlace.png"));
        region = new TextureRegion(texture, 0, 0, 135, 90);
        textures.put("sign",region);
        region = new TextureRegion(texture, 0, 90, 135, 90);
        textures.put("towerField",region);

        //Load Towers
        texture = new Texture(Gdx.files.internal("Towers/Towers.png"));
        region = new TextureRegion(texture, 15, 30, 90, 100);
        textures.put("barrackTower",region);
        region = new TextureRegion(texture, 15, 170, 90, 100);
        textures.put("bowTower",region);
        region = new TextureRegion(texture, 10, 310, 90, 100);
        textures.put("crossbowTower",region);
        region = new TextureRegion(texture, 20, 450, 90, 120);
        textures.put("wizardTower",region);
        region = new TextureRegion(texture, 15, 610, 100, 105);
        textures.put("bombTower",region);

        //Load Tower GUI
        region = new TextureRegion(texture, 130, 60, 35, 25);
        textures.put("barrackTowerGUI",region);
        region = new TextureRegion(texture, 125, 200, 35, 40);
        textures.put("bowTowerGUI",region);
        region = new TextureRegion(texture, 120, 340, 45, 35);
        textures.put("crossbowTowerGUI",region);
        region = new TextureRegion(texture, 125, 500, 40, 40);
        textures.put("wizardTowerGUI",region);
        region = new TextureRegion(texture, 125, 640, 50, 40);
        textures.put("bombTowerGUI",region);

        //Load Bullets
        texture = new Texture(Gdx.files.internal("Towers/Bullets.png"));
        region = new TextureRegion(texture,40,300,30,30);
        textures.put("wizardTowerShoot",region);
        region = new TextureRegion(texture,135,310,35,15);
        textures.put("bowTowerShoot",region);
        region = new TextureRegion(texture,205,305,50,20);
        textures.put("crossbowTowerShoot",region);
        region = new TextureRegion(texture,135,230,30,30);
        textures.put("bombTowerShoot",region);

        //Load AttackRange
        texture = new Texture(Gdx.files.internal("Towers/Attack_Range.png"));
        region = new TextureRegion(texture,0,0,200,200);
        textures.put("attackRange",region);

        //Load Building Ring
        texture = new Texture(Gdx.files.internal("GUI/Building_ring.png"));
        region = new TextureRegion(texture, 110, 10, 75, 75);
        textures.put("buildRingSelector",region);
        region = new TextureRegion(texture,25,135,55,35);
        textures.put("priceRingSelector",region);
        region = new TextureRegion(texture,125,130,40,45);
        textures.put("lockRingSelector",region);
        region = new TextureRegion(texture, 210, 10, 205, 205);
        textures.put("buildRing",region);

        //Load Sale Button
        texture = new Texture(Gdx.files.internal("Towers/Sale_Button.png"));
        region = new TextureRegion(texture,14,14,75,75);
        textures.put("saleContainer",region);
        region = new TextureRegion(texture,105,30,50,50);
        textures.put("cancelIcon",region);
        region = new TextureRegion(texture,170,30,50,50);
        textures.put("acceptIcon",region);
        region = new TextureRegion(texture,160,120,30,35);
        textures.put("sellIcon",region);

        //Load Timer
        texture = new Texture(Gdx.files.internal("GUI/Timer.png"));
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

        //Load Enemies
        texture = new Texture(Gdx.files.internal("Enemies/Bat.png"));
        region = new TextureRegion(texture, 0, 0, 60, 60);
        textures.put("batEnemy",region);
        texture = new Texture(Gdx.files.internal("Enemies/Goblin.png"));
        region = new TextureRegion(texture, 25, 25, 50, 50);
        textures.put("goblinEnemy",region);
        texture = new Texture(Gdx.files.internal("Enemies/Orc.png"));
        region = new TextureRegion(texture, 25, 25, 50, 50);
        textures.put("orcEnemy",region);
        texture = new Texture(Gdx.files.internal("Enemies/Shaman.png"));
        region = new TextureRegion(texture, 0, 0, 90, 100);
        textures.put("shamanEnemy",region);

        //Load Guardians
        texture = new Texture(Gdx.files.internal("Towers/Guards.png"));
        region = new TextureRegion(texture, 15,15,75,60);
        textures.put("guard", region);

        //Load GUI
        texture = new Texture(Gdx.files.internal("GUI/Life.png"));
        region = new TextureRegion(texture, 0,0,44,44);
        textures.put("lifeIcon",region);
        texture = new Texture(Gdx.files.internal("GUI/GameInterface.png"));
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




    }
}
