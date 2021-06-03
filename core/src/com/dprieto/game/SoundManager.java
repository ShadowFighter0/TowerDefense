package com.dprieto.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;

public class SoundManager {

    public static SoundManager instance;

    Music backgroundMusic;

    int currentSoundVolume = 10;
    int currentMusicVolume  = 10;

    SoundManager ()
    {
        backgroundMusic = AssetManager.getInstance().backgroundMusic;
    }

    public static SoundManager getInstance() {
        if (instance == null)
        {
            instance = new SoundManager();
        }
        return instance;
    }

    public void SetMusicVolume (int newVolume)
    {
        currentMusicVolume = newVolume;
        backgroundMusic.setVolume((float)currentMusicVolume/100);
    }

    public void PlaySound (String name)
    {
        Sound s = AssetManager.getInstance().sounds.get(name);
        s.play((float)currentSoundVolume/100);
    }

    public void PlayMusic ()
    {
        backgroundMusic.setVolume((float)currentMusicVolume/100);
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
        backgroundMusic.setLooping(true);
    }

    public void StopMusic()
    {
        backgroundMusic.stop();
    }
}
