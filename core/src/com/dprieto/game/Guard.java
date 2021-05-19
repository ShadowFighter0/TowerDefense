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

    //Animations
    Animation attackingAnimation;
    Animation walkingAnimation;
    Animation dyingAnimation;

    Animation currentAnimation;

    //Image
    TextureRegion healthbar;

    public Guard (Tower tower){

        setActive(false);
        this.tower = tower;

        attackingAnimation = new Animation(AssetManager.instance.getAnimation("guardAnimationAttacking"));
        walkingAnimation = new Animation(AssetManager.instance.getAnimation("guardAnimationWalking"));
        dyingAnimation = new Animation(AssetManager.instance.getAnimation("guardAnimationDying"));

        setDimension(walkingAnimation.getSprite(0));

        this.healthbar = AssetManager.getInstance().getTexture("healthbar");
    }

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

        setActive(true);
        enemyTarget = null;
    }

    public void setGuardPosition(Vector2 newPosition) {

        int offsetX = MathUtils.random(-2, 2);
        int offsetY = MathUtils.random(-2, 2);

        Vector2 offset = new Vector2(dimension.x/2,dimension.y/2).scl(offsetX,offsetY);

        patrolPosition = newPosition.cpy().add(offset);
    }

    public boolean getDamage(int damage)
    {
        currentHealth -= damage;

        if (currentHealth <= 0)
        {
            currentAnimation.stop();
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
                            currentAnimation = attackingAnimation;
                            currentAnimation.play();

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
                    currentAnimation = walkingAnimation;
                }

                getEnemy();
            }

            currentAnimation.update(delta);
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

    private void ChangeAnimation(Animation newAnimation)
    {
        currentAnimation.stop();
        currentAnimation = newAnimation;
        newAnimation.play();
    }

    @Override
    public void render(SpriteBatch batch) {

        if (isActive())
        {
            batch.draw(currentAnimation.getCurrentSprite(),position.x - dimension.x/2,position.y - dimension.y/2);

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
