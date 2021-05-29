package com.dprieto.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class MainMenu {

    public static MainMenu instance;

    HUDElement backgroundTexture;
    HUDElement titleText;

    HUDElement startButtonShadow;
    HUDButton startButton;
    HUDButton quitButton;

    Camera camera;

    TowerDefense masterClient;

    enum MainMenuMode {InitialScreen, Map, Upgrade}
    MainMenuMode mode;

    public static MainMenu getInstance() {
        if (instance == null)
        {
            instance = new MainMenu();
        }
        return instance;
    }

    MainMenu()
    {
        mode = MainMenuMode.InitialScreen;

        backgroundTexture = new HUDElement ("MainMenuBackGround", new Vector2(),
                new Vector2(), HUDElement.Anchor.MiddleScreen, camera);
        titleText =  new HUDElement ("TitleText",                 new Vector2(),
                new Vector2(), HUDElement.Anchor.MiddleScreen, camera);


        startButtonShadow = new HUDElement("StartButtonDown",      new Vector2(),
                new Vector2(), HUDElement.Anchor.MiddleScreen, camera);


        startButton = new HUDButton ( "StartButtonUp" ,     new Vector2(),
                new Vector2(), HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.InitialMenuStart,
                null, camera);
        startButton = new HUDButton ( "MainMenuBackGround", new Vector2(),
                new Vector2(), HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.InitialMenuStart,
                null, camera);
        quitButton  = new HUDButton ("MainMenuBackGround",  new Vector2(),
                new Vector2(), HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.InitialMenuStart,
                null, camera);
    }

    public void setMasterClass( TowerDefense master)
    {
        masterClient = master;
    }

    public boolean CheckOnClick()
    {
        return false;
    }




}
