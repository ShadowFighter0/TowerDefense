package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Tower extends GameObject{

    //Images
    TextureRegion baseImage;
    TextureRegion topImage;
    TextureRegion rangeImage;
    TextureRegion shootImage;
    ShapeRenderer shape;

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

        shape = new ShapeRenderer();

        baseImage = AssetManager.getInstance().getTexture("towerField");
        topImage = AssetManager.getInstance().getTexture(type.name());
        rangeImage = AssetManager.getInstance().getTexture("attackRange");

        dimension.x = baseImage.getRegionWidth();
        dimension.y = baseImage.getRegionHeight();

        shootsPool = new ArrayList<TowerShoot>();
        activeShoots = new ArrayList<TowerShoot>();

        guards = new ArrayList<Guard>();

        guardsPosition = null;

        for (int i = 0; i < Constants.TOWER_POOL_SIZE; i++)
        {
            shootsPool.add(new TowerShoot(this));
        }

        for (int i = 0; i < 3; i++)
        {
            guards.add(new Guard(this));
        }
    }

        //BuildTower
    public void build(Constants.TowerType option) {

        type = option;
        topImage = AssetManager.getInstance().getTexture(type.name());
        shootImage = AssetManager.getInstance().getTexture(type.name() + "Shoot");
        stats  = Constants.getInstance().towerStats.get(option);

        if(option == Constants.TowerType.barrackTower)
        {
            for(Guard guard : guards)
            {
                guard.init();
            }
        }
        else
        {
            for(Guard guard : guards)
            {
                guard.setActive(false);
            }
        }
    }


    @Override
    public void update(float delta) {

        if (type != Constants.TowerType.sign)
        {
            if (type == Constants.TowerType.barrackTower)
            {
                barrackTowerUpdate(delta);
            }
            else
            {
                shootTowerUpdate(delta);
            }
        }
    }


    //GUARDTOWER
    private void barrackTowerUpdate(float delta) {

        if (guardsPosition == null)
        {
            //A waypoint
            //TODO player can change the point
            getDefaultGuardPosition();
        }
        else
        {
            boolean someoneDead = false;

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

            if (someoneDead)
            {
                if(currentReloadTime >= stats.reloadTime)
                {
                    for (Guard guard : guards)
                    {
                        if (!guard.isActive())
                        {
                            guard.spawn();
                        }
                    }
                    currentReloadTime = 0;
                    someoneDead = false;
                }
                else
                {
                    currentReloadTime += delta;
                }
            }
        }
    }

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

    private void setGuardsPosition() {

        for (Guard guard : guards)
        {
            guard.setGuardPosition(guardsPosition);
        }
    }


    //SHOOT TOWERS
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


    @Override
    public void render(SpriteBatch batch) {

        if (type == Constants.TowerType.sign)
        {
            batch.draw(baseImage,position.x - dimension.x/2,position.y - dimension.y/2);
        }

        if (selected && type != Constants.TowerType.sign)
        {
            //TODO shapeRenderer for radius
            batch.draw(rangeImage,position.x - stats.radius,position.y - stats.radius, stats.radius*2 ,stats.radius*2);
        }

        batch.draw(topImage,position.x - topImage.getRegionWidth()/2,position.y - topImage.getRegionHeight()/5 );

        if(type == Constants.TowerType.barrackTower)
        {
            for(Guard guard : guards)
            {
                guard.render(batch);
            }
        }
        else
        {
            for(TowerShoot ts : activeShoots)
            {
                ts.render(batch);
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
