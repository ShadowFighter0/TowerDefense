package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class AssetManager {

    public static AssetManager instance;

    HashMap<String,Texture> maps;
    HashMap<String,TextureRegion> textures;
    HashMap<String, Animation> animations;
    HashMap<String, Sound> sounds;

    Music backgroundMusic;

    private AssetManager()
    {
        maps = new HashMap<String,Texture>();
        textures = new HashMap<String,TextureRegion>();
        animations = new HashMap<String, Animation>();
        sounds = new HashMap<String, Sound>();

        LoadMaps();
        LoadAnimations();
        LoadTextures();
        LoadAudio();
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
        //Load Maps
        maps.put("Map1", new Texture(Gdx.files.internal("Maps/Map_01.png")));
        maps.put("Map2", new Texture(Gdx.files.internal("Maps/MiniLevel2.jpg")));
        maps.put("Map3", new Texture(Gdx.files.internal("Maps/Map_03.png")));
        maps.put("Map4", new Texture(Gdx.files.internal("Maps/Map_04.png")));
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
        //Load Enemies
        LoadAnimationFromFile("Enemies/Bat.png","batEnemyAnimationWalking", 0.1f, true, 3,2,60,60);

        LoadAnimationFromFile("Enemies/Goblin.png","goblinEnemyAnimationAttacking", 0.1f,false, 6,1,95,95);
        LoadAnimationFromFile("Enemies/Goblin.png","goblinEnemyAnimationWalking"  , 0.1f,true, 6,1,95,95,0,95,0,0);
        LoadAnimationFromFile("Enemies/Goblin.png","goblinEnemyAnimationDying"    , 0.1f,false, 6,1,95,95,0,190,0,0);

        LoadAnimationFromFile("Enemies/Orc.png","orcEnemyAnimationAttacking",0.1f,false, 6,1,95,95);
        LoadAnimationFromFile("Enemies/Orc.png","orcEnemyAnimationWalking"  ,0.1f,true, 6,1,95,95,0,95,0,0);
        LoadAnimationFromFile("Enemies/Orc.png","orcEnemyAnimationDying"    ,0.1f,false, 6,1,95,95,0,190,0,0);

        LoadAnimationFromFile("Enemies/Shaman.png","shamanEnemyAnimationWalking",0.1f, true, 3,1,95,95);

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
        region = new TextureRegion(texture, 4,4,40,40);
        textures.put("lifeIcon",region);

        texture = new Texture(Gdx.files.internal("GUI/InGame/GameInterface.png"));
        region = new TextureRegion(texture,20,20,50,50);
        textures.put("pauseButtonIcon",region);
        region = new TextureRegion(texture,95,20,40,50);
        textures.put("playButtonIcon",region);
        region = new TextureRegion(texture,155,20,135,30);
        textures.put("pauseTextIcon",region);
        region = new TextureRegion(texture,310,15,335,75);
        textures.put("barGUI",region);
        region = new TextureRegion(texture,665,35,30,40);
        textures.put("coinIcon",region);
        region = new TextureRegion(texture,715,35,45,40);
        textures.put("roundIcon",region);

        //Load Menus

        //Menu Stage Completed
        texture = new Texture(Gdx.files.internal("GUI/Menu/StageCompleted.png"));
        region = new TextureRegion(texture,0,0,660,640);
        textures.put("StageCompletedBackground",region);

        region = new TextureRegion(texture,690,25,110,125);
        textures.put("StageCompletedStar1",region);
        region = new TextureRegion(texture,685,165,130,135);
        textures.put("StageCompletedStar2",region);
        region = new TextureRegion(texture,690,320,110,120);
        textures.put("StageCompletedStar3",region);

        //Buttons
        region = new TextureRegion(texture,0,650,250,250);
        textures.put("GotoMainMenuButton",region);
        region = new TextureRegion(texture,250,650,250,250);
        textures.put("ReloadLevelButton",region);
        region = new TextureRegion(texture,500,650,250,250);
        textures.put("ResumeButton",region);
        region = new TextureRegion(texture,0,900,930,370);
        textures.put("StageCompletedText",region);

        //Menu Stage Defeat
        texture = new Texture(Gdx.files.internal("GUI/Menu/StageDefeat.png"));
        region = new TextureRegion(texture,5,0,1060,640);
        textures.put("StageDefeatBackground",region);
        region = new TextureRegion(texture,0,700,580,180);
        textures.put("StageDefeatText",region);

        //Menu Options
        texture = new Texture(Gdx.files.internal("GUI/Menu/Options.png"));
        region = new TextureRegion(texture,0,40,670,630);
        textures.put("OptionsBackground",region);
        region = new TextureRegion(texture,690,190,120,90);
        textures.put("SoundImage",region);
        region = new TextureRegion(texture,690,295,85,95);
        textures.put("MusicImage",region);
        region = new TextureRegion(texture,720,410,50,50);
        textures.put("SelectorImage",region);

        //Load HealthBar
        texture = new Texture(Gdx.files.internal("Enemies/Healthbar.png"));
        region = new TextureRegion(texture, 20,4);
        textures.put("healthbar", region);

        //Load Paused
        texture = new Texture(Gdx.files.internal("GUI/Menu/Paused.png"));
        region = new TextureRegion(texture, 20,4);
        textures.put("PausedMenu", region);

        //Load MainMenu
        texture = new Texture(Gdx.files.internal("GUI/Menu/StartScreen.png"));
        region = new TextureRegion(texture, 15,10,1280,720);
        textures.put("MainMenuBackGround", region);
        region = new TextureRegion(texture, 1340,25,300,300);
        textures.put("StartButtonUp", region);
        region = new TextureRegion(texture, 1340,330,300,300);
        textures.put("ButtonShadow", region);
        region = new TextureRegion(texture,10,770, 890,292);
        textures.put("TitleText", region);
        region = new TextureRegion(texture,1180,755, 235,240);
        textures.put("QuitButton", region);

        //Load Choose Level
        texture = new Texture(Gdx.files.internal("GUI/Menu/ChooseLevel.png"));
        region = new TextureRegion(texture, 0,10,800,660);
        textures.put("ChooseMenuBackground", region);
        region = new TextureRegion(texture, 820,305,110,155);
        textures.put("ArrowLeft", region);
        region = new TextureRegion(texture, 955,305,110,155);
        textures.put("ArrowRight", region);
        region = new TextureRegion(texture, 820,190,65,95);
        textures.put("StarContainer", region);
        region = new TextureRegion(texture, 1145,210,35,30);
        textures.put("LevelName", region);

        //TODO Load Spells
    }

    void LoadAudio()
    {
        //Load Towers
        Sound audio = Gdx.audio.newSound(Gdx.files.internal("Audio/TowerAudio/barrackTowerSound.mp3"));
        sounds.put("barrackTowerSound", audio);
        audio = Gdx.audio.newSound(Gdx.files.internal("Audio/TowerAudio/bowTowerSound.mp3"));
        sounds.put("bowTowerSound", audio);
        audio = Gdx.audio.newSound(Gdx.files.internal("Audio/TowerAudio/crossbowTowerSound.mp3"));
        sounds.put("crossbowTowerSound", audio);
        audio = Gdx.audio.newSound(Gdx.files.internal("Audio/TowerAudio/wizardTowerSound.mp3"));
        sounds.put("wizardTowerSound", audio);

        //Load Menu Sounds
        audio = Gdx.audio.newSound(Gdx.files.internal("Audio/Others/Construct.mp3"));
        sounds.put("Construct", audio);
        audio = Gdx.audio.newSound(Gdx.files.internal("Audio/Others/Defeat.mp3"));
        sounds.put("Defeat", audio);
        audio = Gdx.audio.newSound(Gdx.files.internal("Audio/Others/Sell.mp3"));
        sounds.put("Sell", audio);
        audio = Gdx.audio.newSound(Gdx.files.internal("Audio/Others/Victory.mp3"));
        sounds.put("Victory", audio);
        audio = Gdx.audio.newSound(Gdx.files.internal("Audio/Others/WaveStart.mp3"));
        sounds.put("WaveStart", audio);

        //Load Guard Sounds
        audio = Gdx.audio.newSound(Gdx.files.internal("Audio/Guard/GuardAttack1.mp3"));
        sounds.put("GuardAttack1", audio);
        audio = Gdx.audio.newSound(Gdx.files.internal("Audio/Guard/GuardAttack2.mp3"));
        sounds.put("GuardAttack2", audio);

        //Load Enemy
        audio = Gdx.audio.newSound(Gdx.files.internal("Audio/Enemy/EnemyAttack.mp3"));
        sounds.put("EnemyAttack", audio);
        audio = Gdx.audio.newSound(Gdx.files.internal("Audio/Enemy/EnemyDie.mp3"));
        sounds.put("EnemyDie", audio);

        //BackGroundMusic
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Audio/Music/BackgroundMusic.mp3"));
    }
}
