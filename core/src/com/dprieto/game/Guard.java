package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Guard extends GameObject {

    Tower tower;

    //Stats
    int maxHealth;
    int currentHealth;
    int damage;
    float speed;
    int radius;

    float reloadTime;
    float currentReloadTime;

    //Enemy
    Vector2 patrolPosition;
    Enemy enemyTarget;

    //Image
    TextureRegion guardImage;
    TextureRegion healthbar;

    public Guard (Tower tower){

        setActive(false);
        this.tower = tower;
        this.guardImage = AssetManager.getInstance().getTexture("guard");
        this.healthbar = AssetManager.getInstance().getTexture("healthbar");
    }

    public void init(){

        maxHealth = tower.stats.barrackSoldiersHealth;
        currentHealth = maxHealth;
        damage = tower.stats.damage;
        speed = tower.stats.shootSpeed;

        radius = tower.stats.barrackSoldierRadius;

        reloadTime = tower.stats.barrackSoldierReloadTime;
        currentReloadTime = reloadTime * 0.75f;
        enemyTarget = null;
    }

    public void spawn(){
        maxHealth = tower.stats.barrackSoldiersHealth;
        position = tower.position.cpy();
        currentHealth = maxHealth;
        currentReloadTime = reloadTime * 0.75f;
        setActive(true);
        enemyTarget = null;
    }

    public void setGuardPosition(Vector2 newPosition) {

        int offsetX = MathUtils.random(-2, 2);
        int offsetY = MathUtils.random(-2, 2);

        Vector2 offset = new Vector2(guardImage.getRegionWidth()/2,guardImage.getRegionHeight()/2).scl(offsetX,offsetY);

        patrolPosition = newPosition.cpy().add(offset);
    }

    public boolean getDamage(int damage)
    {
        currentHealth -= damage;

        if (currentHealth <= 0)
        {
            setActive(false);

            return true;
        }
        return false;
    }

    @Override
    public void update(float delta) {
        if(isActive())
        {
            if (enemyTarget != null && enemyTarget.isActive())
            {
                if (patrolPosition.dst(enemyTarget.position) < radius)
                {
                    Vector2 movement =  enemyTarget.position.cpy().sub(this.position);
                    translate(movement.nor().scl(delta * speed));

                    if(position.dst(enemyTarget.position) < Constants.ENEMY_DISTANCE_THRESHOLD)
                    {
                        if (currentReloadTime >= reloadTime)
                        {
                            enemyTarget.getDamage(damage);
                            currentReloadTime = 0;
                        }
                        else
                        {
                            currentReloadTime += delta;
                        }
                    }
                }
                else
                {
                    enemyTarget.guard = null;
                    enemyTarget = null;
                }
            }
            else
            {
                if (patrolPosition.dst(position) >= Constants.ENEMY_DISTANCE_THRESHOLD)
                {
                    Vector2 movement =  patrolPosition.cpy().sub(this.position);
                    translate(movement.nor().scl(delta * speed));
                }

                getEnemy();
            }
        }
    }

    //Search for near enemy
    void getEnemy(){

        float distance = 1000;
        float newDistance;

        enemyTarget = null;

        for (int i = tower.currentLevel.enemyPooler.activeEnemies.size()-1 ; i >= 0; i--)
        {
            newDistance = position.cpy().dst(tower.currentLevel.enemyPooler.activeEnemies.get(i).position.cpy());

            if (newDistance < radius && newDistance < distance)
            {
                if (tower.currentLevel.enemyPooler.activeEnemies.get(i).guard == null)
                {
                    enemyTarget = tower.currentLevel.enemyPooler.activeEnemies.get(i);
                    distance = newDistance;
                }
            }
        }

        if (enemyTarget != null)
        {
            enemyTarget.setGuard(this);
        }
    }

    @Override
    public void render(SpriteBatch batch) {

        if (isActive())
        {
            batch.draw(guardImage, position.x - guardImage.getRegionWidth()/2, position.y - guardImage.getRegionHeight()/2);

            //Display Life
            batch.draw(healthbar, position.x - healthbar.getRegionWidth()/2, position.y + guardImage.getRegionHeight()/2);

            batch.setColor(1,0,0,1);
            batch.draw(healthbar, position.x - healthbar.getRegionWidth()/2, position.y + guardImage.getRegionHeight()/2,
                    healthbar.getRegionWidth() * ((float)currentHealth/ (float)maxHealth), healthbar.getRegionHeight());
            batch.setColor(1,1,1, 1);
        }
    }

    @Override
    public void OnClicked() {

    }

    @Override
    public void OnNotClicked() {

    }
}
