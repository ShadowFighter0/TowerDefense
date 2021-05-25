package com.dprieto.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class WaveTimer extends GameObject{
    //Images
    TextureRegion baseTimer;
    TextureRegion clock1Image;
    TextureRegion sandImage;
    TextureRegion clock2Image;
    TextureRegion clock3Image;

    //EnemyPooler
    EnemyPooler enemyPooler;

    //Camera
    Camera camera;

    //Margin
    float scale = 0;

    public WaveTimer(EnemyPooler enemyPooler)
    {
        baseTimer = AssetManager.getInstance().getTexture("timerHolder");
        clock1Image = AssetManager.getInstance().getTexture("clock1");
        sandImage = AssetManager.getInstance().getTexture("clock2");
        clock2Image = AssetManager.getInstance().getTexture("clock3");
        clock3Image = AssetManager.getInstance().getTexture("clock4");

        dimension.x = baseTimer.getRegionWidth();
        dimension.y = baseTimer.getRegionHeight();

        this.enemyPooler = enemyPooler;

        camera = enemyPooler.currentLevel.worldCamera;
        setActive(false);
    }

    @Override
    public void update(float delta)
    {
        if(isActive())
        {
            position.x = enemyPooler.path.get(0).cpy().x;
            position.y = enemyPooler.path.get(0).cpy().y;

            position.x = MathUtils.clamp(position.x,
                    camera.position.x - camera.currentWidth / 2 + baseTimer.getRegionWidth() * camera.currentZoom / 2,
                    camera.position.x + camera.currentWidth / 2 - baseTimer.getRegionWidth() * camera.currentZoom / 2);

            position.y = MathUtils.clamp(position.y,
                    camera.position.y - camera.currentHeight / 2 + baseTimer.getRegionHeight() * camera.currentZoom / 2,
                    camera.position.y + camera.currentHeight / 2 - baseTimer.getRegionHeight()  * camera.currentZoom / 2);

            scale = enemyPooler.currentTimeToNextWave / enemyPooler.timeToNextWave;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if(isActive())
        {
            float zoom = camera.currentZoom;
            //TODO scale with zoom
            batch.draw(baseTimer,
                    position.x - ((baseTimer.getRegionWidth() * zoom)/2),
                    position.y - ((baseTimer.getRegionHeight() * zoom)/2),
                    baseTimer.getRegionWidth() * zoom,
                    baseTimer.getRegionHeight() * zoom);

            batch.draw(clock1Image,
                    position.x - ((clock1Image.getRegionWidth() * zoom)/2),
                    position.y - ((clock1Image.getRegionHeight() * zoom)/2),
                    clock1Image.getRegionWidth() * zoom,
                    clock1Image.getRegionHeight() * zoom);

            batch.draw(sandImage,
                    position.x - ((sandImage.getRegionWidth() * zoom)/2),
                    position.y - ((sandImage.getRegionHeight() * zoom)/2),
                    sandImage.getRegionWidth() * zoom,
                    sandImage.getRegionHeight() * scale * zoom);

            batch.draw(clock2Image,
                    position.x - ((clock2Image.getRegionWidth() * zoom)/2),
                    position.y - ((clock2Image.getRegionHeight() * zoom)/2),
                    clock2Image.getRegionWidth() * zoom,
                    clock2Image.getRegionHeight() * zoom);

            batch.draw(clock3Image,
                    position.x - ((clock3Image.getRegionWidth() * zoom)/2),
                    position.y - ((clock3Image.getRegionHeight() * zoom)/2),
                    clock3Image.getRegionWidth() * zoom,
                    clock3Image.getRegionHeight() * zoom);
        }
    }

    @Override
    public void OnClicked() {
        enemyPooler.nextWave();
    }

    @Override
    public void OnNotClicked() {

    }
}
