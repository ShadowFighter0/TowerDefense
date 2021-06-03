package com.dprieto.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class MainMenu implements Screen {

    //Screen
    MainGame game;
    SpriteBatch batch;
    public static MainMenu instance;

    //Input
    InputManagerMainMenu desktopIM;
    GestureListenerMainMenu mobileIM;

    //GUI
    ArrayList<HUDElement> hudElementsMainMenu;
    ArrayList<HUDButton> hudButtonsMainMenu;

    HUDElement backGround;
    ArrayList<HUDButton> hudButtonsChooseLevel;
    Vector2 miniLevelPosition;
    Vector2 miniLevelDimension;

    //Variables
    int currentLevel = 1;

    BitmapFont font;

    Camera camera;

    enum MainMenuMode {InitialScreen, Map}
    MainMenuMode mode;

    public MainMenu(MainGame game)
    {
        this.game = game;
        instance = this;

        batch = new SpriteBatch();

        //Camera
        TextureRegion img = AssetManager.getInstance().textures.get("MainMenuBackGround");
        camera = new Camera(img.getRegionWidth(), img.getRegionHeight());

        //Inputs
        desktopIM = new InputManagerMainMenu(this);
        mobileIM = new GestureListenerMainMenu(this);

        if (Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS)
        {
            Gdx.input.setInputProcessor(new GestureDetector(mobileIM));
        }
        else if (Gdx.app.getType() == Application.ApplicationType.Desktop)
        {
            Gdx.input.setInputProcessor(desktopIM);

            //Cursor
            Pixmap pm = new Pixmap(Gdx.files.internal("GUI/InGame/Cursor.png"));
            Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm,0,0));
            pm.dispose();
        }

        CreateMainMenu();
        CreateChooseLevel();
    }

    public void SetControl()
    {
        if (Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS)
        {
            Gdx.input.setInputProcessor(new GestureDetector(mobileIM));
        }
        else if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            Gdx.input.setInputProcessor(desktopIM);
        }
    }

    void CreateMainMenu()
    {
        //Mode
        mode = MainMenuMode.InitialScreen;

        //Gui
        hudElementsMainMenu = new ArrayList<HUDElement>();
        hudButtonsMainMenu = new ArrayList<HUDButton>();

        //MainMenuBackground
        hudElementsMainMenu.add(new HUDElement ("MainMenuBackGround", new Vector2(0,0),
                HUDElement.Anchor.MiddleScreen, camera));
        hudElementsMainMenu.add(new HUDElement ("TitleText", new Vector2(0,200),
                HUDElement.Anchor.MiddleScreen, camera));

        //ShadowButton
        hudElementsMainMenu.add(new HUDElement("ButtonShadow", new Vector2(125,-180),
                new Vector2(0.7f,0.7f), HUDElement.Anchor.MiddleScreen, camera));
        hudElementsMainMenu.add(new HUDElement("ButtonShadow",     new Vector2(-125,-180),
                new Vector2(0.71f,0.71f), HUDElement.Anchor.MiddleScreen, camera));

        //Buttons
        hudButtonsMainMenu.add(new HUDButton ( "StartButtonUp", new Vector2(125,-180), new Vector2(0.6f,0.6f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.InitialMenuStart, null, camera));

        hudButtonsMainMenu.add(new HUDButton ("QuitButton",  new Vector2(-125,-180), new Vector2(0.7f,0.7f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.Quit, null, camera));
    }

    void CreateChooseLevel()
    {
        font = new BitmapFont(Gdx.files.internal("Fonts/Font.fnt"));
        font.setColor(Color.BLACK);


        hudButtonsChooseLevel = new ArrayList<HUDButton>();

        backGround = new HUDElement("ChooseMenuBackground", new Vector2(0,0),
                new Vector2(1,1), HUDElement.Anchor.MiddleScreen, camera);


        hudButtonsChooseLevel.add(new HUDButton ("QuitButton",  new Vector2(-250,-240), new Vector2(0.9f,0.9f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.Quit, null, camera));
        hudButtonsChooseLevel.add(new HUDButton ("ResumeButton",  new Vector2(250,-240), new Vector2(0.9f,0.9f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.StartLevel, null, camera));
        hudButtonsChooseLevel.add(new HUDButton ("ArrowLeft",  new Vector2(-350,70), new Vector2(0.9f,0.9f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.ArrowLeft, null, camera));
        hudButtonsChooseLevel.add(new HUDButton ("ArrowRight",  new Vector2(350,70), new Vector2(0.9f,0.9f),
                HUDElement.Anchor.MiddleScreen, HUDButton.ButtonType.ArrowRight, null, camera));

        miniLevelPosition = new Vector2(0,50);
        miniLevelDimension = new Vector2(550,275);
    }

    public void StartGame()
    {
        game.SetNextLevel(currentLevel);
    }

    void render()
    {
        if (mode == MainMenuMode.InitialScreen)
        {
            for (HUDElement hud : hudElementsMainMenu)
            {
                hud.render(batch);
            }

            for (HUDButton button : hudButtonsMainMenu)
            {
                button.render(batch);
            }
        }
        else
        {
            //Background
            hudElementsMainMenu.get(0).render(batch);

            backGround.render(batch);

            //Display miniLevel
            batch.draw(AssetManager.getInstance().getMap("Map" + currentLevel),
                    camera.position.x + miniLevelPosition.x - miniLevelDimension.x/2,
                    camera.position.y + miniLevelPosition.y - miniLevelDimension.y/2,
                    miniLevelDimension.x, miniLevelDimension.y);

            //Buttons
            for (HUDButton button : hudButtonsChooseLevel)
            {
                button.render(batch);
            }
        }
    }

    public boolean CheckOnClick(Vector2 point)
    {
        boolean clicked = false;
        boolean newClick = false;

        if (mode == MainMenuMode.InitialScreen)
        {
            for (HUDButton button : hudButtonsMainMenu)
            {
                newClick = button.checkClicked(point);

                if (newClick == false)
                {
                    clicked = newClick;
                }
            }
        }
        else
        {
            for (HUDButton button : hudButtonsChooseLevel)
            {
                newClick = button.checkClicked(point);

                if (newClick == false)
                {
                    clicked = newClick;
                }
            }

        }

        return false;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.orthographicCamera.combined);

        batch.begin();

        render();

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

        camera.FitResize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }




}
