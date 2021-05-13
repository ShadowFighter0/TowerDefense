package com.dprieto.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class TowerShoot extends GameObject{

    TextureRegion image;
    Enemy objective;
    int damage;
    float speed;

    Tower tower;

    public TowerShoot(Tower tower)
    {
        setActive(false);

        this.tower = tower;
    }

    public void setShoot(Vector2 position, Enemy objective, int damage, float speed, TextureRegion image )
    {
        this.position = position.cpy();
        this.objective = objective;
        this.damage = damage;
        this.speed = speed;
        this.image = image;

        dimension.x = image.getRegionWidth();
        dimension.y = image.getRegionHeight();

        setActive(true);
    }

    void returnToPool()
    {
        setActive(false);
        tower.shootsPool.add(this);
        tower.activeShoots.remove(this);
    }

    @Override
    public void update(float delta)
    {
        Vector2 movement = objective.position.cpy().sub(position.cpy());

        rotation = (float)(Math.atan2(
                        objective.position.cpy().y - position.cpy().y,
                        objective.position.cpy().x - position.cpy().x
                ) * 180f/Math.PI);


        translate(movement.nor().scl(speed * delta));


        if (position.dst(objective.position) < Constants.ENEMY_DISTANCE_THRESHOLD)
        {
            if (tower.type == Constants.TowerType.bombTower)
            {
                bombAttack();
            }
            else
            {
                objective.getDamage(damage);
            }

            returnToPool();
        }
    }

    private void bombAttack()
    {
        ArrayList<Enemy> enemies = tower.currentLevel.enemyPooler.activeEnemies;
        for (int i = enemies.size() - 1; i >= 0 ; i--)
        {
            if (enemies.get(i).position.dst(position) < tower.stats.bombDamageRadius)
            {
                enemies.get(i).getDamage(damage);
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {

        if(isActive())
        {
            batch.draw(image, position.x , position.y ,dimension.x/2, dimension.y/2, dimension.x, dimension.y,1,1,rotation);
        }
    }

    @Override
    public void OnClicked() {

    }

    @Override
    public void OnNotClicked() {

    }
}
