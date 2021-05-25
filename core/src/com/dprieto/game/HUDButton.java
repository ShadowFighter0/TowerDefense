package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class HUDButton extends HUDElement{

    enum ButtonType {Play, Pause, MainMenu, Restart, Resume}
    ButtonType type;


    public HUDButton(String imageName, Vector2 position, Anchor anchor, ButtonType buttonType, Camera camera) {
        super(imageName, position, anchor, camera);

        this.type = buttonType;
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
   }

    public boolean checkClicked(Vector2 point)
    {
        if (point.x > currentPosition.x - dimension.x/2 && point.x < currentPosition.x + dimension.x/2
                && point.y > currentPosition.y - dimension.y/2 && point.y < currentPosition.y + dimension.y/2)
        {
            OnClicked();
            return true;
        }
        else
        {
            OnNotClicked();
        }
        return false;
    }

    public void OnClicked(){
        switch (type)
        {
            case Play:

                PlayButton();

                break;
            case Pause:

                PauseButton();

                break;
            case Resume:

                ResumeButton();

                break;
            case Restart:

                RestartButton();

                break;
            case MainMenu:
                MainMenuButton();

                break;
        }
    }

    public void OnNotClicked(){

    }

    void PlayButton ()
    {
        Gdx.app.debug("Button", "Play");
    }

    void PauseButton()
    {
        Gdx.app.debug("Button", "Pause");
    }

    void ResumeButton()
    {

    }

    void RestartButton()
    {

    }

    void MainMenuButton()
    {

    }
}