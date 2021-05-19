package com.dprieto.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Enemy extends GameObject{

    //Animations
    Animation attackingAnimation;
    Animation walkingAnimation;
    Animation dyingAnimation;

    Animation currentAnimation;

    //Image
    TextureRegion healthbar;

    //Variables
    Constants.EnemyType type;
    Constants.EnemyState currentState;
    EnemyStats stats;
    int currentHealth;
    float currentReloadTime;

    ArrayList<Vector2> waypoints;
    int nextPoint;

    //Level
    Level currentLevel;
    Guard guard;


    public Enemy(ArrayList<Vector2> waypoints, Level level) {

        setActive(false);
        this.waypoints = waypoints;
        this.position = waypoints.get(0).cpy();

        healthbar = AssetManager.getInstance().getTexture("healthbar");

        this.currentLevel = level;
    }

    void moveToNextPoint(float delta) {

        Vector2 movement =  waypoints.get(nextPoint).cpy().sub(this.position);
        translate(movement.nor().scl(delta * stats.speed));
    }

    void moveToGuard(float delta) {

        Vector2 movement =  guard.position.cpy().sub(this.position);
        translate(movement.nor().scl(delta * stats.speed));
    }

    void checkNextPoint() {

        if (position.dst(waypoints.get(nextPoint)) <= Constants.getInstance().ENEMY_DISTANCE_THRESHOLD)
        {
            if(nextPoint == waypoints.size()-1)
            {
                currentLevel.lives--;
                returnToPool();
            }
            else
            {
                nextPoint++;
            }
        }
    }

    void returnToPool() {

        setActive(false);
        currentLevel.enemyPooler.pool.add(this);
        currentLevel.enemyPooler.activeEnemies.remove(this);
    }

    public void setEnemy (Constants.EnemyType type) {

        if (this.type != type)
        {
            this.type = type;
            stats = Constants.getInstance().enemyStats.get(type);

            attackingAnimation = new Animation(type,"Attacking");
            walkingAnimation = new Animation(type,"Walking");
            dyingAnimation = new Animation(type,"Dying");
            setDimension(walkingAnimation.getSprite(0));
        }

        currentHealth = stats.health;

        position = waypoints.get(0).cpy();
        nextPoint = 1;
        guard = null;

        currentState = Constants.EnemyState.walking;
        currentAnimation = walkingAnimation;
        setActive(true);
    }

    public void getDamage(int damage) {

        currentHealth -= damage;

        if(currentHealth <= 0)
        {
            currentLevel.money += stats.money;
            returnToPool();
        }
    }

    public void setGuard(Guard guard)
    {
        this.guard = guard;
    }

    private void getWaypoint() {

        float distance = 1000;

        for( int i = 0; i < waypoints.size(); i++)
        {
            float newDistance = position.dst(waypoints.get(i));
            if (newDistance < distance)
            {
                nextPoint = i;
                distance = newDistance;
            }
        }
    }

    private void attackGuard(float delta) {
        if (position.dst(guard.position) <= Constants.ENEMY_DISTANCE_THRESHOLD)
        {
            currentState = Constants.EnemyState.attacking;
            if (currentReloadTime >= stats.reloadTime)
            {

                currentReloadTime = 0;

                if (guard.getDamage(stats.damage))
                {
                    guard = null;

                    getWaypoint();
                }
            }
            else
            {
                currentReloadTime += delta;
            }
        }
    }

    @Override
    public void update(float delta){

        if(isActive())
        {
            if(guard == null)
            {
                checkNextPoint();
                moveToNextPoint(delta);
            }
            else
            {
                moveToGuard(delta);
                attackGuard(delta);
            }

        currentAnimation.update(delta);
        }
    }

    @Override
    public void render(SpriteBatch batch) {

        if(isActive())
        {
            batch.draw(currentAnimation.getCurrentSprite(),position.x - dimension.x/2,position.y - dimension.y/2);

            //DisplayLife
            batch.draw(healthbar, position.x - healthbar.getRegionWidth()/2, position.y + dimension.y/2);

            batch.setColor(1,0,0,1);
            batch.draw(healthbar, position.x - healthbar.getRegionWidth()/2, position.y + dimension.y/2,
                    healthbar.getRegionWidth() * ((float)currentHealth/(float)stats.health), healthbar.getRegionHeight());
            batch.setColor(1,1,1,1);
        }
    }

    @Override
    public void OnClicked() {

    }

    @Override
    public void OnNotClicked() {

    }

}
