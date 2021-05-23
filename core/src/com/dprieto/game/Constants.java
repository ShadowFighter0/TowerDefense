package com.dprieto.game;

import java.util.HashMap;

public class Constants {

    static Constants instance;

    public static final float ENEMY_DISTANCE_THRESHOLD = 5;
    public static final float TIME_TO_START_INITIAL_WAVE = 0;
    public static final int TOWER_POOL_SIZE = 10;

    public static final float ZOOM_MOBILE_SENSITIVITY = 0.001f;
    public static final float PAN_MOBILE_SENSITIVITY = 0.25f;

    public float timeScale = 1f;

    public enum TowerType {
        sign,
        barrackTower,barrackTower2,barrackTower3,
        bowTower, bowTower2, bowTower3,
        crossbowTower,crossbowTower2,crossbowTower3,
        wizardTower,wizardTower2,wizardTower3,
        bombTower,bombTower2,bombTower3}

    public enum EnemyType {batEnemy, goblinEnemy, orcEnemy, shamanEnemy}

    public enum SellButtonOptions {acceptIcon, cancelIcon, sellIcon}

    //TODO upgrade Guard
    //public enum GuardType {level1, level2, level3}

    //TODO shoot Type select minion
    //public enum ShootType {first, lowerLife, higherLife}

    public final HashMap<TowerType,TowerStats> towerStats;
    public final HashMap<EnemyType,EnemyStats> enemyStats;

    Constants()
    {
        //TowerStats
        towerStats = new HashMap<TowerType, TowerStats>();
        EnemyType[] bowEnemies = {EnemyType.batEnemy, EnemyType.goblinEnemy, EnemyType.orcEnemy, EnemyType.shamanEnemy};
        towerStats.put(TowerType.bowTower,new TowerStats(10,250,75,500,1,bowEnemies));

        EnemyType[] crossbowEnemies = {EnemyType.batEnemy, EnemyType.goblinEnemy, EnemyType.orcEnemy, EnemyType.shamanEnemy};
        towerStats.put(TowerType.crossbowTower,new TowerStats(60,200,100,350,3,crossbowEnemies));

        EnemyType[] wizardEnemies = {EnemyType.batEnemy, EnemyType.goblinEnemy, EnemyType.orcEnemy};
        towerStats.put(TowerType.wizardTower,new TowerStats(20,200,120,400,2,wizardEnemies));

        EnemyType[] bombEnemies = {EnemyType.goblinEnemy, EnemyType.orcEnemy, EnemyType.shamanEnemy};
        towerStats.put(TowerType.bombTower,new TowerStats(50,200,150,300,5,75, bombEnemies));

        EnemyType[] barrackEnemies = {EnemyType.goblinEnemy, EnemyType.orcEnemy};
        towerStats.put(TowerType.barrackTower,new TowerStats(20,200,75,50,10, barrackEnemies,50,75, 1 ));


        //EnemyStats
        enemyStats = new HashMap<EnemyType,EnemyStats>();
        enemyStats.put(EnemyType.batEnemy,new EnemyStats(50,50,10,30,0));
        enemyStats.put(EnemyType.orcEnemy,new EnemyStats(70,25,15,50,1));
        enemyStats.put(EnemyType.shamanEnemy,new EnemyStats(100,20,20,50,1));
        enemyStats.put(EnemyType.goblinEnemy,new EnemyStats(50,50,15,50,1));
    }

    public static Constants getInstance()
    {
        if (instance == null)
        {
            instance = new Constants();
        }
        return instance;
    }

}



