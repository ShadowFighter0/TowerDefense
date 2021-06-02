package com.dprieto.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

public class MainGame extends com.badlogic.gdx.Game {

    int selectedLevel;

    MainMenu mainMenu;
    LevelsScreen levels;

    @Override
    public void create() {

        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        mainMenu = new MainMenu(this);
        levels = new LevelsScreen(this);

        setScreen(mainMenu);
    }

    public void setScene(String screenName)
    {
        if (screenName == "MainMenu")
        {
            setScreen(mainMenu);
            mainMenu.SetControl();
        }
        else if(screenName == "Level")
        {
            setScreen(levels);
        }
    }

    public void SetNextLevel(int level)
    {
        selectedLevel = level;
        levels.SetLevel(selectedLevel);

        setScreen(levels);
    }

    public void ReloadLevel()
    {
        levels.SetLevel(selectedLevel);

        setScreen(levels);
    }
}
