package com.dprieto.game;

public class TowerStats {

    public int damage;
    public int radius;
    public int price;
    public int reloadTime; //secs
    public float shootSpeed;

    public Constants.EnemyType[] enemyTypes;

    public int bombDamageRadius;

    public int barrackSoldiersHealth;
    public int barrackSoldierRadius;
    public int barrackSoldierReloadTime;

    //Normal Towers
    public TowerStats (int damage, int radius, int price, float shootSpeed, int shootTime, Constants.EnemyType[] types)
    {
        this.damage = damage;
        this.radius = radius;
        this.price = price;
        this.reloadTime = shootTime;
        this.shootSpeed = shootSpeed;
        this.enemyTypes = types;


        bombDamageRadius = 0;

        barrackSoldiersHealth = 0;
        barrackSoldierRadius = 0;
        barrackSoldierReloadTime = 0;
    }

    //Bomb Tower
    public TowerStats (int damage, int radius, int price, float shootSpeed, int shootTime, int bombDamageRadius, Constants.EnemyType[] types)
    {
        this.damage = damage;
        this.radius = radius;
        this.price = price;
        this.reloadTime = shootTime;
        this.shootSpeed = shootSpeed;
        this.enemyTypes = types;

        this.bombDamageRadius = bombDamageRadius;
    }

    //Barrack Tower
    public TowerStats (int damage, int radius, int price, float shootSpeed, int shootTime,Constants.EnemyType[] types,
                       int barrackSoldiersHealth, int barrackSoldierRadius, int barrackSoldierReloadTime) {

        this.damage = damage;
        this.radius = radius;
        this.price = price;
        this.reloadTime = shootTime;
        this.shootSpeed = shootSpeed;
        this.enemyTypes = types;

        this.barrackSoldiersHealth = barrackSoldiersHealth;
        this.barrackSoldierRadius = barrackSoldierRadius;
        this.barrackSoldierReloadTime = barrackSoldierReloadTime;
    }

}
