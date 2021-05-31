package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class LevelFactory {

    static LevelFactory instance;
    TowerDefense master;

    LevelFactory()
    {

    }

    public static LevelFactory getInstance()
    {
        if(instance == null)
        {
            instance = new LevelFactory();
        }
        return instance;
    }

    public void setMasterClass(TowerDefense master)
    {
        this.master = master;
    }

    public Level getLevel(int index)
    {
        ArrayList<Vector2> path = new ArrayList<Vector2>();
        ArrayList<Vector2> buildingPlaces = new ArrayList<Vector2>();
        ArrayList<Wave> levelWaves = new ArrayList<Wave>();
        ArrayList<WaveOrder> wave = new ArrayList<WaveOrder>();

        switch (index)
        {
            case 1:
                path.add(new Vector2(-100,470));
                path.add(new Vector2(150,470));
                path.add(new Vector2(210,540));
                path.add(new Vector2(240,660));
                path.add(new Vector2(260,690));
                path.add(new Vector2(310,710));
                path.add(new Vector2(400,710));
                path.add(new Vector2(440,690));
                path.add(new Vector2(470,660));
                path.add(new Vector2(490,530));
                path.add(new Vector2(500,390));
                path.add(new Vector2(520,330));
                path.add(new Vector2(600,310));
                path.add(new Vector2(700,310));
                path.add(new Vector2(800,340));
                path.add(new Vector2(830,430));
                path.add(new Vector2(850,490));
                path.add(new Vector2(880,510));
                path.add(new Vector2(900,530));
                path.add(new Vector2(1400,530));


                buildingPlaces.add(new Vector2(350,470));
                buildingPlaces.add(new Vector2(350,600));
                buildingPlaces.add(new Vector2(650,420));
                buildingPlaces.add(new Vector2(560,170));
                buildingPlaces.add(new Vector2(720,170));
                buildingPlaces.add(new Vector2(950,390));
                buildingPlaces.add(new Vector2(930,660));
                buildingPlaces.add(new Vector2(1160,660));

                //List with all waves
                //Wave1
                wave = new ArrayList<WaveOrder>();
                wave.add(new WaveOrder(Constants.EnemyType.orcEnemy,1));
                wave.add(new WaveOrder(Constants.EnemyType.orcEnemy,1));
                levelWaves.add(new Wave(wave,20));

                //Wave 2
                wave = new ArrayList<WaveOrder>();
                wave.add(new WaveOrder(Constants.EnemyType.batEnemy,1));
                wave.add(new WaveOrder(Constants.EnemyType.batEnemy,1));
                wave.add(new WaveOrder(Constants.EnemyType.batEnemy,1));
                wave.add(new WaveOrder(Constants.EnemyType.batEnemy,1));
                wave.add(new WaveOrder(Constants.EnemyType.batEnemy,1));
                wave.add(new WaveOrder(Constants.EnemyType.batEnemy,1));
                wave.add(new WaveOrder(Constants.EnemyType.batEnemy,1));
                wave.add(new WaveOrder(Constants.EnemyType.batEnemy,1));
                levelWaves.add(new Wave(wave,20));

                //Wave3
                wave = new ArrayList<WaveOrder>();
                wave.add(new WaveOrder(Constants.EnemyType.batEnemy,1));
                wave.add(new WaveOrder(Constants.EnemyType.batEnemy,1));
                levelWaves.add(new Wave(wave,20));

                ////Wave4
                wave = new ArrayList<WaveOrder>();
                wave.add(new WaveOrder(Constants.EnemyType.goblinEnemy,1));
                wave.add(new WaveOrder(Constants.EnemyType.goblinEnemy,1));
                levelWaves.add(new Wave(wave,20));

                //Wave5
                wave = new ArrayList<WaveOrder>();
                wave.add(new WaveOrder(Constants.EnemyType.shamanEnemy,1));
                wave.add(new WaveOrder(Constants.EnemyType.shamanEnemy,1));
                levelWaves.add(new Wave(wave,20));


                return new Level(
                        AssetManager.getInstance().getMap("Map"+index),
                        path,
                        buildingPlaces,
                        500,
                        3,
                        levelWaves
                );


            case 2:

                TiledMap map = new TmxMapLoader().load("Maps/Level2.tmx");

                PolylineMapObject obj = (PolylineMapObject)map.getLayers().get("Waypoint").getObjects().get(0);

                float [] vertices = obj.getPolyline().getTransformedVertices();

                for(int i = 0 ; i < vertices.length; i+=2)
                {
                    path.add(new Vector2(vertices[i] * Constants.TILED_UNIT_SCALE, vertices[i+1] * Constants.TILED_UNIT_SCALE));
                }


                MapObjects towers = map.getLayers().get("TowerPlaces").getObjects();

                for (int i = 0; i < towers.getCount(); i++)
                {
                    Vector2 position = new Vector2(towers.get(i).getProperties().get("x", Float.class) * Constants.TILED_UNIT_SCALE,
                                                   towers.get(i).getProperties().get("y", Float.class) * Constants.TILED_UNIT_SCALE);
                    
                    buildingPlaces.add(position);
                }

                //List with all waves
                //Wave1
                wave = new ArrayList<WaveOrder>();
                wave.add(new WaveOrder(Constants.EnemyType.orcEnemy,1));
                wave.add(new WaveOrder(Constants.EnemyType.orcEnemy,1));
                levelWaves.add(new Wave(wave,20));

                //Wave 2
                wave = new ArrayList<WaveOrder>();
                wave.add(new WaveOrder(Constants.EnemyType.batEnemy,1));
                wave.add(new WaveOrder(Constants.EnemyType.batEnemy,1));
                wave.add(new WaveOrder(Constants.EnemyType.batEnemy,1));
                wave.add(new WaveOrder(Constants.EnemyType.batEnemy,1));
                wave.add(new WaveOrder(Constants.EnemyType.batEnemy,1));
                wave.add(new WaveOrder(Constants.EnemyType.batEnemy,1));
                wave.add(new WaveOrder(Constants.EnemyType.batEnemy,1));
                wave.add(new WaveOrder(Constants.EnemyType.batEnemy,1));
                levelWaves.add(new Wave(wave,20));

                //Wave3
                wave = new ArrayList<WaveOrder>();
                wave.add(new WaveOrder(Constants.EnemyType.batEnemy,1));
                wave.add(new WaveOrder(Constants.EnemyType.batEnemy,1));
                levelWaves.add(new Wave(wave,20));

                ////Wave4
                wave = new ArrayList<WaveOrder>();
                wave.add(new WaveOrder(Constants.EnemyType.goblinEnemy,1));
                wave.add(new WaveOrder(Constants.EnemyType.goblinEnemy,1));
                levelWaves.add(new Wave(wave,20));

                //Wave5
                wave = new ArrayList<WaveOrder>();
                wave.add(new WaveOrder(Constants.EnemyType.shamanEnemy,1));
                wave.add(new WaveOrder(Constants.EnemyType.shamanEnemy,1));
                levelWaves.add(new Wave(wave,20));


                return new Level(
                        map,
                        path,
                        buildingPlaces,
                        500,
                        3,
                        levelWaves
                );
        }
        return null;
    }


}
