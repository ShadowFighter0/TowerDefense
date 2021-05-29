package com.dprieto.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class HUDButton extends HUDElement{

    enum ButtonType {Play, Pause, MainMenu, Restart, Resume, Sound, Music,
                     InitialMenuStart, Quit,  }
    ButtonType type;
    Level level;

    public HUDButton (String imageName, Vector2 position, Anchor anchor, ButtonType buttonType, Level level, Camera camera) {
        super(imageName, position, anchor, camera);

        this.type = buttonType;
        this.level = level;
    }

    public HUDButton (String imageName, Vector2 position, Vector2 dimensionModifier, Anchor anchor, ButtonType buttonType, Level level, Camera camera) {
        super(imageName, position, dimensionModifier, anchor, camera);

        this.type = buttonType;
        this.level = level;
    }

    @Override
    public void render (SpriteBatch batch) {
        super.render(batch);
   }

    public boolean checkClicked (Vector2 point)
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
            case Music:
                SetMusicVolume();
                break;

            case Sound:
                SetSoundVolume();
                break;
        }
    }

    public void OnNotClicked(){

    }

    void PlayButton ()
    {
        level.SetGameState(Level.GameState.Playing);
        level.winDefeatMenu.SetActive(false, WinDefeatMenu.ButtonMode.Pause);
    }

    void PauseButton()
    {
        level.SetGameState(Level.GameState.Pause);
        level.winDefeatMenu.SetActive(true, WinDefeatMenu.ButtonMode.Pause);
    }

    void ResumeButton(){
        level.SetGameState(Level.GameState.Playing);
        level.winDefeatMenu.SetActive(false, WinDefeatMenu.ButtonMode.Pause);
    }

    void RestartButton(){

    }

    void MainMenuButton() {

    }

    void SetMusicVolume(){}

    void SetSoundVolume(){}
}