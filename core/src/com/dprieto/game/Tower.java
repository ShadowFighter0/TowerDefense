package com.dprieto.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Tower extends GameObject{

    //Images
    TextureRegion baseTexture;
    TextureRegion upTexture;

    TextureRegion rangeImage;
    TextureRegion shootImage;

    //Variables
    Constants.TowerType type;
    TowerStats stats;
    float currentReloadTime;

    //Pools
    ArrayList<TowerShoot> shootsPool;
    ArrayList<TowerShoot> activeShoots;

    ArrayList<Guard> guards;
    Vector2 guardsPosition;

    boolean selected;

    Enemy selectedEnemy;

    Level currentLevel;

    public Tower(Vector2 position, Level level) {

        this.position = position;
        selected = false;
        currentLevel = level;

        type = Constants.TowerType.sign;

        baseTexture = AssetManager.getInstance().getTexture(type.name() + "Base");
        upTexture = AssetManager.getInstance().getTexture(type.name() + "Up");

        rangeImage = AssetManager.getInstance().getTexture("attackRange");

        setDimension(baseTexture);

        //Create shoots
        shootsPool = new ArrayList<TowerShoot>();
        activeShoots = new ArrayList<TowerShoot>();

        for (int i = 0; i < Constants.TOWER_POOL_SIZE; i++)
        {
            shootsPool.add(new TowerShoot(this));
        }

        //Create Guards
        guards = new ArrayList<Guard>();
        guardsPosition = null;

        for (int i = 0; i < 3; i++)
        {
            guards.add(new Guard(this));
        }
    }

    //BuildTower
    public void build(Constants.TowerType option) {

        //Set new type
        type = option;

        //Get Textures
        baseTexture = AssetManager.getInstance().getTexture(type.name()+"Base");
        upTexture = AssetManager.getInstance().getTexture(type.name()+"Up");

        if (option != Constants.TowerType.sign)
            shootImage = AssetManager.getInstance().getTexture(type.name() + "Shoot");

        setDimension(baseTexture);

        //Get new Stats
        stats  = Constants.getInstance().towerStats.get(option);

        if (option != Constants.TowerType.barrackTower)
        {
            for(Guard guard : guards)
            {
                guard.setActive(false);
            }
        }
    }

    @Override
    public void update(float delta) {

        switch (type)
        {
            case sign:
                break;

            case barrackTower:
                barrackTowerUpdate(delta);
                break;

            default:
                shootTowerUpdate(delta);
                break;
        }
    }

    //region Barrack
    //Barrack Update
    private void barrackTowerUpdate(float delta) {

        //If there is no guard position assign one
        if (guardsPosition == null)
        {
            //A waypoint
            //TODO player can change the point
            getDefaultGuardPosition();
        }
        else
        {
            boolean someoneDead = false;

            //call guards update
            for (Guard guard : guards)
            {
                if (guard.isActive())
                {
                    guard.update(delta);
                }
                else
                {
                    someoneDead = true;
                }
            }

            // Spawn dead guards
            if (someoneDead)
            {
                if (currentReloadTime >= stats.reloadTime)
                {
                    for (Guard guard : guards)
                    {
                        if (!guard.isActive())
                        {
                            guard.spawn();
                        }
                    }
                    currentReloadTime = 0;
                }
                else
                {
                    currentReloadTime += delta;
                }
            }
        }
    }

    //Set nearest Waypoint as patrol position
    private void getDefaultGuardPosition() {

        float distance = 1000;

        for( int i = 0; i < currentLevel.path.size(); i++)
        {
            float newDistance = position.dst(currentLevel.path.get(i));
            if (newDistance < distance)
            {
                guardsPosition = currentLevel.path.get(i);
                distance = newDistance;
            }
        }
        setGuardsPosition();
    }

    //Set guards the patrol position
    private void setGuardsPosition() {

        for (Guard guard : guards)
        {
            guard.setGuardPosition(guardsPosition);
        }
    }
    //endregion

    //region ShootTowers
    //shoot tower Update
    private void shootTowerUpdate(float delta){

        if (selectedEnemy != null && selectedEnemy.isActive())
        {
            if (currentReloadTime >= stats.reloadTime)
            {
                shoot();
                currentReloadTime = 0;
            }
            else
            {
                currentReloadTime += delta;
            }
        }
        else
        {
            getEnemy();
        }

        for (int i = activeShoots.size()-1; i >= 0; i--)
        {
            activeShoots.get(i).update(delta);
        }
    }

    void shoot() {
        if (position.cpy().dst(selectedEnemy.position.cpy()) > stats.radius)
        {
            selectedEnemy = null;
            return;
        }
        else
        {
            TowerShoot newShoot = shootsPool.get(0);
            activeShoots.add(newShoot);
            shootsPool.remove(0);

            newShoot.setShoot(this.position, selectedEnemy, stats.damage, stats.shootSpeed, shootImage);
        }
    }

    void getEnemy() {

        for (int i = currentLevel.enemyPooler.activeEnemies.size()-1; i >= 0; i--)
        {
            if (position.cpy().dst(currentLevel.enemyPooler.activeEnemies.get(i).position.cpy()) < stats.radius)
            {
                selectedEnemy = currentLevel.enemyPooler.activeEnemies.get(i);
                return;
            }
        }

        selectedEnemy = null;
    }

    //endregion

    //Render towers
    @Override
    public void render(SpriteBatch batch) {

        if( type == Constants.TowerType.sign)
        {
            batch.draw( baseTexture  ,position.x - dimension.x/2,position.y - dimension.y/2);       //Draw Ground
        }
        else
        {
            batch.draw( baseTexture  ,position.x - dimension.x/2,position.y - dimension.y/5);       //Draw Tower Base
        }

        batch.draw( upTexture    ,position.x - dimension.x/2,position.y - dimension.y/5);       //Draw Shooter
    }

    //Render tower range
    public void renderRange (SpriteBatch batch)
    {
        batch.draw(rangeImage,position.x - stats.radius,position.y - stats.radius, stats.radius*2 ,stats.radius*2);
    }

    //render shoots or guards
    public void renderShoot(SpriteBatch batch) {

        if(type == Constants.TowerType.barrackTower)
        {
            for(Guard guard : guards)
            {
                guard.render(batch);
            }
        }
        else
        {
            for(TowerShoot shoot : activeShoots)
            {
                shoot.render(batch);
            }
        }
    }

    public void OnClicked() {
        selected = true;
        //Show menu
        BuildRing.getInstance().setTower(this);
    }

    public void OnNotClicked() {
        selected = false;

    }
}
