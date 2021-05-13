package com.dprieto.game;

public class EnemyStats {

    int health;
    int speed;
    int damage;
    int money;
    float reloadTime;

    //TODO type of enemy (aereo/ground)
    String type;

    public EnemyStats (int health, int speed, int damage, int money, float reloadTime)
    {
        this.health = health;
        this.speed = speed;
        this.damage = damage;
        this.money = money;
        this.reloadTime = reloadTime;
        //this.type = type;
    }
}
