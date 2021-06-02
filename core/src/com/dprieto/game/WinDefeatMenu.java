package com.dprieto.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class WinDefeatMenu {

    enum ButtonMode {Win, Loose, Pause}
    ButtonMode mode;

    boolean isActive;
    Vector2 position;

    Level level;

    //Win
    HUDElement winBackGround;
    HUDElement winText;
    HUDElement star1;
    HUDElement star2;
    HUDElement star3;

    //Loose
    HUDElement looseBackGround;
    HUDElement looseText;

    //Pause
    HUDElement pauseBackGround;
    HUDElement soundImage;
    HUDElement musicImage;
    HUDElement soundSelectorButton;
    HUDElement musicSelectorButton;

    HUDButton musicVolumeUp;
    HUDButton musicVolumeDown;
    HUDButton soundVolumeUp;
    HUDButton soundVolumeDown;

    Vector2 soundEmpty;
    Vector2 soundFull;
    Vector2 musicEmpty;
    Vector2 musicFull;

    //Button
    HUDButton gotoMainMenuButton;
    HUDButton restartLevelButton;
    HUDButton resumeLevelButton;

    public WinDefeatMenu (Level level, Camera camera)
    {
        mode = ButtonMode.Pause;

        isActive = false;
        this.level = level;

        this.position = camera.position;

        // Background
        winBackGround = new HUDElement("StageCompletedBackground", new Vector2(0,0),
                new Vector2(0.75f,0.75f), HUDElement.Anchor.MiddleScreen, camera);

        looseBackGround = new HUDElement("StageDefeatBackground" , new Vector2(0,0),
                new Vector2(0.75f,0.75f), HUDElement.Anchor.MiddleScreen, camera);

        pauseBackGround = new HUDElement("OptionsBackground"     , new Vector2(0,0),
                new Vector2(0.75f,0.75f), HUDElement.Anchor.MiddleScreen, camera);

        // Text
        winText = new HUDElement("StageCompletedText", new Vector2(0,150),
                new Vector2(0.75f,0.75f), HUDElement.Anchor.MiddleScreen, camera);

        looseText = new HUDElement("StageDefeatText" , new Vector2(0,100),
                new Vector2(0.75f,0.75f), HUDElement.Anchor.MiddleScreen, camera);

        // Images
        soundImage = new HUDElement("SoundImage", new Vector2(-95,125),
                new Vector2(0.75f,0.75f),HUDElement.Anchor.MiddleScreen, camera);

        musicImage = new HUDElement("MusicImage", new Vector2(-106,30),
                new Vector2(0.75f,0.75f),HUDElement.Anchor.MiddleScreen, camera);

        star1 = new HUDElement("StageCompletedStar1", new Vector2(-125,0),HUDElement.Anchor.MiddleScreen, camera);
        star2 = new HUDElement("StageCompletedStar2", new Vector2(0,0),   HUDElement.Anchor.MiddleScreen, camera);
        star3 = new HUDElement("StageCompletedStar3", new Vector2(125,0), HUDElement.Anchor.MiddleScreen, camera);

        // Sound
        soundSelectorButton = new HUDElement("SelectorImage", new Vector2(150,120),
                new Vector2(0.8f,0.8f), HUDElement.Anchor.MiddleScreen, camera);

        musicSelectorButton = new HUDElement("SelectorImage", new Vector2(150,20),
                new Vector2(0.8f,0.8f), HUDElement.Anchor.MiddleScreen ,  camera);

        soundFull = new Vector2(150,120);
        soundEmpty = new Vector2(-40, 120);
        musicFull = new Vector2(150,120);
        musicEmpty = new Vector2(-40, 120);

        musicVolumeDown = new HUDButton ("ArrowLeft",  new Vector2(-30,-20), new Vector2(0.25f,0.25f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.ArrowLeft, null, camera);
        musicVolumeUp = new HUDButton ("ArrowRight",  new Vector2(150,-20), new Vector2(0.25f,0.25f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.ArrowRight, null, camera);
        soundVolumeDown = new HUDButton ("ArrowLeft",  new Vector2(-30,80), new Vector2(0.25f,0.25f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.ArrowLeft, null, camera);
        soundVolumeUp = new HUDButton ("ArrowRight",  new Vector2(150,80), new Vector2(0.25f,0.25f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.ArrowRight, null, camera);


        //Buttons
        gotoMainMenuButton = new HUDButton("GotoMainMenuButton", new Vector2(-200,-150),
                new Vector2(0.8f,0.8f), HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.MainMenu ,level,  camera);

        restartLevelButton = new HUDButton("ReloadLevelButton" , new Vector2(-5,-175),
                new Vector2(0.8f,0.8f), HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.Restart  ,level, camera);

        resumeLevelButton = new HUDButton("ResumeButton"       , new Vector2(200,-150),
                new Vector2(0.8f,0.8f), HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.Resume   ,level, camera);

        isActive = false;
    }

    public void SetActive(boolean active, ButtonMode newMode) {this.mode = newMode; isActive = active;}

    void renderPause (SpriteBatch batch)
    {
        //BackGround
        pauseBackGround.render(batch);

        //Sound
        soundImage.render(batch);
        musicImage.render(batch);
        soundSelectorButton.render(batch);
        musicSelectorButton.render(batch);

        musicVolumeUp.render(batch);
        musicVolumeDown.render(batch);
        soundVolumeUp.render(batch);
        soundVolumeDown.render(batch);

        //Buttons menu
        gotoMainMenuButton.SetPosition(-200,-150);
        gotoMainMenuButton.render(batch);
        resumeLevelButton.render(batch);

        restartLevelButton.SetPosition(-5,-175);
        restartLevelButton.render(batch);
    }

    void renderWin (SpriteBatch batch)
    {
        //Background
        winBackGround.render(batch);

        //Text
        winText.render(batch);

        //Stars
        star1.render(batch);
        star2.render(batch);
        star3.render(batch);

        //MenuButton
        gotoMainMenuButton.SetPosition(0,-175);
        gotoMainMenuButton.render(batch);
    }

    void renderLoose (SpriteBatch batch)
    {
        //Background
        looseBackGround.render(batch);

        //Text
        looseText.render(batch);

        //MenuButton
        gotoMainMenuButton.SetPosition(-130,-175);
        gotoMainMenuButton.render(batch);
        restartLevelButton.SetPosition(100,-175);
        restartLevelButton.render(batch);
    }

    public void render (SpriteBatch batch)
    {
        if (!isActive)  return;

        switch (mode)
        {
            case Pause:

                renderPause(batch);

                break;
            case Win:

                renderWin(batch);

                break;
            case Loose:

                renderLoose(batch);

                break;
        }
    }

    public boolean CheckClicks(Vector2 point)
    {
        if (!isActive) return false;


        boolean clicked = false;
        boolean newClick = false;

        switch (mode)
        {
            case Pause:

                //newClick = soundSelectorButton.checkClicked(point);
                if (!clicked)
                {
                    clicked = newClick;
                }

                //newClick = musicSelectorButton.checkClicked(point);
                if (!clicked)
                {
                    clicked = newClick;
                }

                newClick = gotoMainMenuButton.checkClicked(point);
                if (!clicked)
                {
                    clicked = newClick;
                }

                newClick = resumeLevelButton.checkClicked(point);
                if (!clicked)
                {
                    clicked = newClick;
                }

                newClick = restartLevelButton.checkClicked(point);
                if (!clicked)
                {
                    clicked = newClick;
                }
                break;

            case Win:

                newClick = gotoMainMenuButton.checkClicked(point);
                if (!clicked)
                {
                    clicked = newClick;
                }

                break;
            case Loose:

                newClick = gotoMainMenuButton.checkClicked(point);
                if (!clicked)
                {
                    clicked = newClick;
                }
                newClick = restartLevelButton.checkClicked(point);
                if (!clicked)
                {
                    clicked = newClick;
                }
                break;
        }

        return clicked;
    }
}
