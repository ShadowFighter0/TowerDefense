package com.dprieto.game;

import com.badlogic.gdx.math.Vector2;

public class WinDefeatMenu {

    Vector2 position;

    HUDElement winBackGround;
    HUDElement looseBackGround;

    HUDElement winText;
    HUDElement looseText;

    HUDButton gotoMainMenu;
    HUDButton restartLevel;
    HUDButton resumeLevel;

    boolean mode;

    public WinDefeatMenu (Vector2 position)
    {
        this.position = position;

    }
}
