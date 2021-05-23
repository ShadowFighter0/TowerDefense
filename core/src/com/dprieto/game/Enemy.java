package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Enemy extends GameObject{

    //References
    Level currentLevel;
    Guard guard;

    //State
    enum State {walking, attacking, dead};
    State currentState;

    //Stats
    Constants.EnemyType type;
    EnemyStats stats;
    int currentHealth;
    float currentReloadTime;

    //Variables
    ArrayList<Vector2> waypoints;
    int nextPoint;

    //Animations
    Animation attackingAnimation;
    Animation walkingAnimation;
    Animation dyingAnimation;

    Animation currentAnimation;

    //Image
    TextureRegion healthbar;

    public Enemy(ArrayList<Vector2> waypoints, Level level) {

        setActive(false);

        this.currentLevel = level;

        this.waypoints = waypoints;
        this.position = waypoints.get(0).cpy();

        healthbar = AssetManager.getInstance().getTexture("healthbar");
    }

    //Set Enemy Type and get all the data needed
    public void setEnemy (Constants.EnemyType type) {

        if (this.type != type)
        {
            this.type = type;
            stats = Constants.getInstance().enemyStats.get(type);

            walkingAnimation = new Animation(AssetManager.instance.getAnimation(type.name() +"AnimationWalking"));
            if (type != Constants.EnemyType.batEnemy && type != Constants.EnemyType.shamanEnemy)
            {
                attackingAnimation = new Animation(AssetManager.instance.getAnimation(type.name() +"AnimationAttacking"));
                dyingAnimation = new Animation(AssetManager.instance.getAnimation(type.name() +"AnimationDying"));
            }

            setDimension(walkingAnimation.getSprite(0));
        }

        currentHealth = stats.health;
        currentReloadTime = stats.reloadTime;

        position = waypoints.get(0).cpy();
        nextPoint = 1;
        guard = null;

        currentState = State.walking;
        currentAnimation = walkingAnimation;
        setActive(true);
    }

    // Move to next waypoint
    void moveToNextPoint(float delta) {

        Vector2 movement =  waypoints.get(nextPoint).cpy().sub(this.position);
        translate(movement.nor().scl(delta * stats.speed));

        if (position.dst(waypoints.get(nextPoint)) <= Constants.getInstance().ENEMY_DISTANCE_THRESHOLD)
        {
            if (nextPoint == waypoints.size()-1)
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

    //return this enemy to the pool
    void returnToPool() {

        currentLevel.money += stats.money;
        setActive(false);
        currentLevel.enemyPooler.pool.add(this);
        currentLevel.enemyPooler.activeEnemies.remove(this);
    }

    //return true if enemy is dead
    public boolean getDamage(int damage) {

        currentHealth -= damage;

        if (currentHealth <= 0 && currentState != State.dead)
        {
            if (type == Constants.EnemyType.batEnemy || type == Constants.EnemyType.shamanEnemy)
            {
                returnToPool();

                return true;
            }
            currentState = State.dead;
            ChangeAnimation(dyingAnimation);

            return true;
        }
        return false;
    }

    //If a guard has detected this enemy as objective set that guard as objective
    public void setGuard(Guard guard){

        this.guard = guard;
        currentState = State.attacking;
    }

    //Get closest waypoint
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

    //If guard is near, attack him
    private void attackGuard(float delta) {

        if (guard != null && guard.isActive())
        {
            //Move to enemy
            Vector2 movement = guard.position.cpy().sub(this.position);
            translate(movement.nor().scl(delta * stats.speed));

            //If guard is near
            if (position.dst(guard.position) <= Constants.ENEMY_DISTANCE_THRESHOLD)
            {
                //and attack is available attack
                if (currentReloadTime >= stats.reloadTime)
                {
                    currentReloadTime = 0;

                    ChangeAnimation(attackingAnimation);

                    if (guard.getDamage(stats.damage))
                    {
                        guard = null;
                        //Set reloaded in order to change animation when both enter combat
                        currentReloadTime = stats.reloadTime;
                        
                        getWaypoint();
                    }
                }
                // reload attack
                else
                {
                    currentReloadTime += delta;
                }
            }

        }
        else
        {
            guard = null;
            getWaypoint();

            //Set state
            currentState = State.walking;
            ChangeAnimation(walkingAnimation);
        }
    }

    @Override
    public void update(float delta){

        if(isActive())
        {
            switch (currentState)
            {
                //Move to next waypoint
                case walking:
                    moveToNextPoint(delta);
                    break;

                //Move to guard and attack him
                case attacking:

                    attackGuard(delta);

                    break;

                case dead:
                    if (currentAnimation.hasEnded()) {
                        returnToPool();
                    }

                break;
            }

            currentAnimation.update(delta);
        }
    }

    //If given animation is different of actual, change to animation
    private void ChangeAnimation (Animation newAnimation) {

        currentAnimation.stop();
        currentAnimation = newAnimation;
        newAnimation.play();
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
