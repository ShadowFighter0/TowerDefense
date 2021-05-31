package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Vector;

public class EnemyPooler {

    //WaveTimer
    WaveTimer waveTimer;

    //Enemies
    ArrayList<Enemy> pool;
    ArrayList<Enemy> activeEnemies;

    //Waves
    ArrayList<Wave> waves;
    int currentWaveIndex;
    int currentOrderIndex;

    //boolean
    boolean mustChangeWave;

    //Timers
    float timeToNextWave;
    float currentTimeToNextWave;
    float timeToNextOrder;
    float currentTimeToNextOrder;

    //Shorter
    Wave currentWave;

    //Waypoints
    ArrayList<Vector2> path;

    //Current level
    Level currentLevel;

    public EnemyPooler(Level currentLevel, Vector2 dimensionMultiplier,  int poolSize, ArrayList<Vector2> path, ArrayList<Wave> waves)
    {
        //Variables
        this.currentLevel = currentLevel;
        this.path = path;
        this.waves = waves;
        currentWaveIndex = 0;
        currentOrderIndex = 0;
        mustChangeWave = true;
        timeToNextWave = Constants.getInstance().TIME_TO_START_INITIAL_WAVE;
        timeToNextOrder = 0;

        //Arrays
        pool = new ArrayList<Enemy>();
        activeEnemies = new ArrayList<Enemy>();

        //Fill pool
        for (int i = 0; i < poolSize; i++)
        {
            pool.add(new Enemy(path,dimensionMultiplier, currentLevel));
        }

        //Create WaveTimer
        waveTimer = new WaveTimer(this);
    }

    void waveControl(float delta)
    {
        if (mustChangeWave)
        {
            changeWave(delta);
        }
        else
        {
            inWave(delta);
        }
    }
    public void nextWave()
    {
        currentTimeToNextWave = timeToNextWave;
    }

    void changeWave(float delta)
    {
        if (currentTimeToNextWave >= timeToNextWave)
        {
            mustChangeWave = false;
            waveTimer.setActive(false);

            //New Wave
            currentWave = waves.get(currentWaveIndex);
            currentOrderIndex = 0;
            currentTimeToNextOrder = 0;
            timeToNextOrder = currentWave.enemies.get(0).secsUntilNextEnemy;

            currentWaveIndex++;

        }
        else
        {
            currentTimeToNextWave += delta;
        }
    }

    void inWave (float delta)
    {
        if (currentOrderIndex < currentWave.enemies.size())
        {
            if (currentTimeToNextOrder >= timeToNextOrder)
            {
               //New Order
                //Extract an enemy from the pool
                Enemy newEnemy = pool.get(0);
                activeEnemies.add(newEnemy);
                pool.remove(0);

                newEnemy.setEnemy(currentWave.enemies.get(currentOrderIndex).enemyType);
                timeToNextOrder = currentWave.enemies.get(currentOrderIndex).secsUntilNextEnemy;
                currentTimeToNextOrder = 0;

                currentOrderIndex++;
            }
            else
            {
                currentTimeToNextOrder += delta;
            }
        }
        else
        {
            if (currentWaveIndex < waves.size())
            {
                //New Wave
                waveTimer.setActive(true);
                mustChangeWave = true;
                timeToNextWave = currentWave.secsUntilNextWave;
                currentTimeToNextWave = 0;
            }
            else
            {
                if (activeEnemies.size() == 0)
                {
                    currentLevel.SetGameState(Level.GameState.Win);
                }
            }
        }
    }

    public void update(float delta)
    {
        waveControl(delta);

        for (int i = activeEnemies.size()-1 ; i >= 0; i--)
        {
            activeEnemies.get(i).update(delta);
        }

        waveTimer.update(delta);
    }

    public void render(SpriteBatch batch)
    {
        for (Enemy enemy : activeEnemies) {
            enemy.render(batch);
        }
    }

}
