package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Guard extends GameObject {

    //References
    Tower tower;

    //State
    enum state {idle, walking, attacking, dead}
    state currentState;

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

    //Animations
    Animation attackingAnimation;
    Animation walkingAnimation;
    Animation dyingAnimation;

    Animation currentAnimation;

    //Images
    TextureRegion healthbar;
    TextureRegion idleImage;

    public Guard (Tower tower){

        setActive(false);

        this.tower = tower;

        attackingAnimation = new Animation(AssetManager.instance.getAnimation("guardAnimationAttacking"));
        walkingAnimation = new Animation(AssetManager.instance.getAnimation("guardAnimationWalking"));
        dyingAnimation = new Animation(AssetManager.instance.getAnimation("guardAnimationDying"));

        this.healthbar = AssetManager.getInstance().getTexture("healthbar");
        this.idleImage = attackingAnimation.getSprite(0);

        setDimension(idleImage);
    }

    //Called when Tower spawn a guard
    public void spawn(){

        maxHealth = tower.stats.barrackSoldiersHealth;
        currentHealth = maxHealth;

        position = tower.position.cpy();

        damage = tower.stats.damage;
        speed = tower.stats.shootSpeed;
        reloadTime = tower.stats.barrackSoldierReloadTime;
        currentReloadTime = reloadTime * 0.75f;
        radius = tower.stats.barrackSoldierRadius;

        currentAnimation = walkingAnimation;

        currentState = state.walking;
        enemyTarget = null;

        setActive(true);
    }

    //Set patrol position with offset
    public void setGuardPosition(Vector2 newPosition) {

        int offsetX = MathUtils.random(-1, 1);
        int offsetY = MathUtils.random(-1, 1);

        Vector2 offset = new Vector2(dimension.x/2,dimension.y/2).scl(offsetX,offsetY);

        patrolPosition = newPosition.cpy().add(offset);
    }

    //return true if player is dead
    public boolean getDamage(int damage)
    {
        currentHealth -= damage;

        if (currentHealth <= 0)
        {
            currentState = state.dead;
            ChangeAnimation(dyingAnimation);
            return true;
        }
        return false;
    }

    @Override
    public void update(float delta) {

        if (isActive()) {

            switch (currentState) {

                //In patrol position, no near enemy
                case idle:

                    //Check For Enemies
                    getEnemy();
                    break;

                //Walking to patrol position
                case walking:

                    if (patrolPosition.dst(position) >= Constants.ENEMY_DISTANCE_THRESHOLD)
                    {
                        //Move to patrol
                        Vector2 movement = patrolPosition.cpy().sub(this.position);
                        translate(movement.nor().scl(delta * speed));

                        //Check For Enemies
                        getEnemy();
                    }
                    else
                    {
                        currentState = state.idle;
                    }

                    break;

                    //Walk and attack to an enemy
                    case attacking:
                        //If we have an enemy
                        if (enemyTarget != null && enemyTarget.isActive())
                        {
                            //Moving to Enemy
                            if (patrolPosition.dst(enemyTarget.position) < radius)
                            {
                                Vector2 movement = enemyTarget.position.cpy().sub(this.position);
                                translate(movement.nor().scl(delta * speed));

                                //If is in range
                                if (position.dst(enemyTarget.position) < Constants.ENEMY_DISTANCE_THRESHOLD)
                                {
                                    //Attack available
                                    if (currentReloadTime >= reloadTime)
                                    {
                                        currentReloadTime = 0;
                                        ChangeAnimation(attackingAnimation);

                                        if (enemyTarget.getDamage(damage))
                                        {
                                            enemyTarget = null;

                                            currentState = state.walking;
                                        }

                                    }
                                    //Attack in reload
                                    else
                                    {
                                        currentReloadTime += delta;
                                    }
                                }
                            }
                        }
                        else
                        {
                            //Enemy Dead
                            enemyTarget.guard = null;
                            enemyTarget = null;

                            //Change State
                            currentState = state.walking;
                            ChangeAnimation(walkingAnimation);
                        }
                        break;

                    case dead:
                        if (currentAnimation.hasEnded()) {
                            setActive(false);
                        }

                        break;
            }
            currentAnimation.update(delta);
        }
    }

    //Search for near enemy
    void getEnemy(){

        float distance = 1000;
        float newDistance;

        enemyTarget = null;

        //Search for nearest enemy
        for (int i = tower.currentLevel.enemyPooler.activeEnemies.size()-1 ; i >= 0; i--)
        {
            newDistance = position.cpy().dst(tower.currentLevel.enemyPooler.activeEnemies.get(i).position.cpy());

            if (newDistance < radius && newDistance < distance)
            {
                //If the selected enemy has not guard assigned
                if (tower.currentLevel.enemyPooler.activeEnemies.get(i).guard == null)
                {
                    enemyTarget = tower.currentLevel.enemyPooler.activeEnemies.get(i);
                    distance = newDistance;
                }
            }
        }

        //Set enemy
        if (enemyTarget != null)
        {
            enemyTarget.setGuard(this);

            currentState = state.attacking;
            ChangeAnimation(walkingAnimation);
        }
    }

    //If given animation is different of actual, change to animation
    private void ChangeAnimation (Animation newAnimation)
    {
        if (currentAnimation.name != newAnimation.name)
        {
            currentAnimation.stop();
            currentAnimation = newAnimation;
            currentAnimation.play();
        }
    }

    @Override
    public void render(SpriteBatch batch) {

        if (isActive())
        {
            if (currentState == state.idle)
            {
                batch.draw(idleImage ,position.x - dimension.x/2,position.y - dimension.y/2);
            }
            else
            {
                batch.draw(currentAnimation.getCurrentSprite(),position.x - dimension.x/2,position.y - dimension.y/2);
            }

            //Display Life
            batch.draw(healthbar, position.x - healthbar.getRegionWidth()/2, position.y + dimension.y/2);

            batch.setColor(1,0,0,1);
            batch.draw(healthbar, position.x - healthbar.getRegionWidth()/2, position.y + dimension.y/2,
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
