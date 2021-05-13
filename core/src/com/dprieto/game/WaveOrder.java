package com.dprieto.game;

public class WaveOrder {

    Constants.EnemyType enemyType;
    float secsUntilNextEnemy;

    public WaveOrder(Constants.EnemyType type, float secsUntilNextEnemy)
    {
        this.enemyType = type;
        this.secsUntilNextEnemy = secsUntilNextEnemy;
    }
}
