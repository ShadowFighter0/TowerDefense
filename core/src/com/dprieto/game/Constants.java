package com.dprieto.game;

import java.util.HashMap;

public class Constants {

    static Constants instance;

    public static final float ENEMY_DISTANCE_THRESHOLD = 2;
    public static final float TIME_TO_START_INITIAL_WAVE = 0;
    public static final int TOWER_POOL_SIZE = 10;

    public static final float ZOOM_MOBILE_SENSITIVITY = 0.001f;
    public static final float PAN_MOBILE_SENSITIVITY = 0.25f;

    public enum TowerType {sign,
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
    //public final HashMap<GuardType,GuardStats> guardStats;

    Constants()
    {
        towerStats = new HashMap<TowerType, TowerStats>();
        towerStats.put(TowerType.barrackTower,new TowerStats(30,200,75,50,10,50,75, 1));

        towerStats.put(TowerType.bowTower,new TowerStats(10,250,75,300,1));
        towerStats.put(TowerType.crossbowTower,new TowerStats(60,200,100,200,3));
        towerStats.put(TowerType.wizardTower,new TowerStats(20,200,120,250,2));

        towerStats.put(TowerType.bombTower,new TowerStats(50,200,150,300,5,75));


        enemyStats = new HashMap<EnemyType,EnemyStats>();
        enemyStats.put(EnemyType.batEnemy,new EnemyStats(50,50,10,30,5));
        enemyStats.put(EnemyType.orcEnemy,new EnemyStats(70,25,15,50,5));
        enemyStats.put(EnemyType.shamanEnemy,new EnemyStats(100,20,20,50,5));
        enemyStats.put(EnemyType.goblinEnemy,new EnemyStats(50,50,15,50,5));

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



