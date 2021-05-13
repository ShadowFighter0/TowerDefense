package com.dprieto.game;

import java.util.ArrayList;

public class Wave {

    ArrayList<WaveOrder> enemies;
    float secsUntilNextWave;

    public Wave (ArrayList<WaveOrder> enemies, float secsUntilNextWave)
    {
        this.enemies = enemies;
        this.secsUntilNextWave = secsUntilNextWave;
    }
}
