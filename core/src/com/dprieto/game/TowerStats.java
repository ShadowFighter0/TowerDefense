package com.dprieto.game;

public class TowerStats {

    public int damage;
    public int radius;
    public int price;
    public int reloadTime; //secs
    public float shootSpeed;

    public int bombDamageRadius;

    public int barrackSoldiersHealth;
    public int barrackSoldierRadius;
    public int barrackSoldierReloadTime;

    //TODO type of enemy(aereo/ground)
    public String type;

    public TowerStats (int damage, int radius, int price, float shootSpeed, int shootTime)
    {
        this.damage = damage;
        this.radius = radius;
        this.price = price;
        this.reloadTime = shootTime;
        this.shootSpeed = shootSpeed;
        bombDamageRadius = 0;

        barrackSoldiersHealth = 0;
        barrackSoldierRadius = 0;
        barrackSoldierReloadTime = 0;
    }

    public TowerStats (int damage, int radius, int price, float shootSpeed, int shootTime, int bombDamageRadius)
    {
        this.damage = damage;
        this.radius = radius;
        this.price = price;
        this.reloadTime = shootTime;
        this.shootSpeed = shootSpeed;

        this.bombDamageRadius = bombDamageRadius;
    }

    public TowerStats (int damage, int radius, int price, float shootSpeed, int shootTime, int barrackSoldiersHealth, int barrackSoldierRadius, int barrackSoldierReloadTime) {

        this.damage = damage;
        this.radius = radius;
        this.price = price;
        this.reloadTime = shootTime;
        this.shootSpeed = shootSpeed;

        this.barrackSoldiersHealth = barrackSoldiersHealth;
        this.barrackSoldierRadius = barrackSoldierRadius;
        this.barrackSoldierReloadTime = barrackSoldierReloadTime;
    }

}
