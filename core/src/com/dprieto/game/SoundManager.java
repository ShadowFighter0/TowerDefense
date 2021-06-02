package com.dprieto.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;

public class SoundManager {

    public static SoundManager instance;

    ArrayList<Sound> activeSounds;

    Music backgroundMusic;

    float currentSoundVolume = 1;
    float currentMusicVolume  = 1;

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
            activeSounds.get(i).play(currentSoundVolume);
        }
    }

    public void SetSoundVolume (float newVolume)
    {
        currentSoundVolume = newVolume;
    }

    public void SetMusicVolume (float newVolume)
    {
        currentMusicVolume = newVolume;
        backgroundMusic.setVolume(currentMusicVolume);
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
        backgroundMusic.setVolume(currentMusicVolume);
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    }
}
