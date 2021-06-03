package com.dprieto.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;

public class SoundManager {

    public static SoundManager instance;

    ArrayList<Sound> activeSounds;

    Music backgroundMusic;

    int currentSoundVolume = 10;
    int currentMusicVolume  = 10;

    SoundManager ()
    {

    }

    public static SoundManager getInstance() {
        if (instance == null)
        {
            instance = new SoundManager();
        }
        return instance;
    }

    public void StopAllSounds ()
    {
        for (int i = 0; i < activeSounds.size(); i++)
        {
            activeSounds.get(i).stop();
        }
    }

    public void ResumeAllSounds ()
    {
        for (int i = 0; i < activeSounds.size(); i++)
        {
            activeSounds.get(i).play((float)currentSoundVolume/10);
        }
    }

    public void SetSoundVolume (int newVolume)
    {
        currentSoundVolume = newVolume;
    }

    public void SetMusicVolume (int newVolume)
    {
        currentMusicVolume = newVolume;
        backgroundMusic.setVolume((float)currentMusicVolume/10);
    }

    public void PlaySound (String name)
    {
        Sound s = AssetManager.getInstance().sounds.get(name);
        s.play(currentSoundVolume);
        activeSounds.add(s);
    }

    public void PlayMusic ()
    {
        backgroundMusic = AssetManager.getInstance().backgroundMusic;
        backgroundMusic.setVolume((float)currentMusicVolume/10);
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    }
}
