package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class HUDButton extends HUDElement{

    enum ButtonType {Play, Pause,Restart, Resume, MainMenu, StartLevel,
                     InitialMenuStart, Quit, ArrowLeft, ArrowRight,
                     MusicVolumeUp, MusicVolumeDown, SoundVolumeUp, SoundVolumeDown}
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
        if (point.x > currentPosition.x - dimension.x / 2 && point.x < currentPosition.x + dimension.x / 2
                && point.y > currentPosition.y - dimension.y / 2 && point.y < currentPosition.y + dimension.y / 2)
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
            case InitialMenuStart:

                MainMenu.instance.mode = MainMenu.MainMenuMode.Map;

                break;

            case ArrowLeft:

                MainMenu.instance.currentLevel--;
                if (MainMenu.instance.currentLevel < 1)
                {
                    MainMenu.instance.currentLevel = LevelFactory.getInstance().numberOfLevels;
                }

                break;

            case ArrowRight:

                MainMenu.instance.currentLevel++;
                if (MainMenu.instance.currentLevel > LevelFactory.getInstance().numberOfLevels )
                {
                    MainMenu.instance.currentLevel = 1;
                }

                break;

            case StartLevel:

                MainMenu.instance.StartGame();

                break;

            case Play:
            case Resume:
                level.SetGameState(Level.GameState.Playing);
                level.winDefeatMenu.SetActive(false, WinDefeatMenu.ButtonMode.Pause);

                break;

            case Pause:
                level.SetGameState(Level.GameState.Pause);
                level.winDefeatMenu.SetActive(true, WinDefeatMenu.ButtonMode.Pause);

                break;

            case Restart:
                LevelsScreen.instance.game.ReloadLevel();

                break;
            case MainMenu:

                if (level != null)
                {
                    MainMenu.instance.mode = MainMenu.MainMenuMode.Map;
                    LevelsScreen.instance.game.setScene("MainMenu");
                }
                else
                {
                    MainMenu.instance.mode = MainMenu.MainMenuMode.Map;
                    MainMenu.instance.game.setScene("MainMenu");
                }

                break;

            case MusicVolumeUp:
                    //level.winDefeatMenu.
                break;

            case MusicVolumeDown:

                break;

            case SoundVolumeUp:

                break;

            case SoundVolumeDown:

                break;

            case Quit:
                Gdx.app.exit();
                break;
        }
    }

    public void OnNotClicked()
    {

    }

}