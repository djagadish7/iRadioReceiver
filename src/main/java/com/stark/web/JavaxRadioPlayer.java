package com.stark.web;

import javax.media.MediaLocator;
import javax.media.Format;
import javax.media.Manager;
import javax.media.Player;
import javax.media.PlugInManager;
import javax.media.format.AudioFormat;

public class JavaxRadioPlayer implements IRadioPlayer
{
    Player player = null;
    private boolean m_isPlaying = false;
    private Station m_currenStation = null;

    public JavaxRadioPlayer()
    {
        Format input1 = new AudioFormat(AudioFormat.MPEGLAYER3);
        Format input2 = new AudioFormat(AudioFormat.MPEG);
        Format input3 = new AudioFormat(AudioFormat.DOLBYAC3);
        Format output = new AudioFormat(AudioFormat.LINEAR);
        PlugInManager.addPlugIn(
                "com.sun.media.codec.audio.mp3.JavaDecoder",
                new Format[]{input1, input2, input3},
                new Format[]{output},
                PlugInManager.CODEC
        );
    }


    public void Play(Station station)
    {
        if(station == null)
            return;
        if(m_currenStation == station & m_isPlaying)
            return;
        m_currenStation = station;

        try
        {
            if(m_isPlaying)
            {
                player.stop();
                player.close();
            }
            System.out.println("Playing now..");
            MediaLocator mediaLocator = new MediaLocator(station.getLink());
            player = Manager.createPlayer(mediaLocator);
            player.start();
            m_isPlaying = true;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void Stop()
    {
        m_isPlaying = false;

        if(player != null)
            player.stop();
    }

    public void VolumeUp()
    {
        if(player == null)
            return;

        float currentLevel = player.getGainControl().getLevel();
        if( currentLevel < 0.9f)
            player.getGainControl().setLevel(currentLevel + 0.1f);
    }

    public void VolumeDown()
    {
        if(player == null)
            return;

        float currentLevel = player.getGainControl().getLevel();
        if( currentLevel > 0.0f)
            player.getGainControl().setLevel(currentLevel - 0.1f);
    }

    public void ToggleMute()
    {
        if(player == null)
            return;

        player.getGainControl().setMute(!player.getGainControl().getMute());
    }

    public boolean IsMute()
    {
        if(player == null)
            return true;

        return player.getGainControl().getMute();
    }

    public boolean isPlaying()
    {
        return m_isPlaying;
    }
}
