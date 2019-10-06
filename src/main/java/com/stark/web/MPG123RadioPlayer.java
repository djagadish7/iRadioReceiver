package com.stark.web;

import java.io.IOException;

public class MPG123RadioPlayer implements IRadioPlayer
{
    private Station m_currenStation = null;
    private Thread m_thread = null;
    private Process m_process = null;
    private int MAX_VOL = 95;
    private int MIN_VOL = 10;
    private int CUR_VOL = 90;

    public void MPG123RadioPlayer()
    {
        ChangeVol(90);
    }


    @Override
    public void Play(Station station)
    {
        System.out.println("In Play");
        if(m_thread != null && m_currenStation == station)
            return;

        if(m_thread != null)
            Stop();

        m_thread = new Thread(() -> {
            try
            {
                System.out.println("Initiating Play thread");
                //Runtime.getRuntime().exec("mpg123 " + station.getLink()).waitFor();
                //Runtime.getRuntime().exec("mpg123 http://149.56.234.136:9928/;?listening-from-radio-garden=1556322114235").waitFor();
                ProcessBuilder processBuilder = new ProcessBuilder("/usr/bin/mpg123", station.getLink());
                processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
                (m_process = processBuilder.start()).waitFor();
                System.out.println("After start of the process");
            }
            catch (InterruptedException e)
            {
                System.out.println("Thread interrupted");
                e.printStackTrace();
            }
            catch (IOException e)
            {
                System.out.println("I/O exception - " + e.getMessage());
                e.printStackTrace();
            }
        });
        m_thread.start();
        m_currenStation = station;
    }

    @Override
    public void Stop()
    {
        System.out.println("Being stopped");
        if(m_thread != null)
        {
            m_thread.interrupt();
            m_process.destroy();
            m_process = null;
            m_thread = null;
            m_currenStation = null;
            System.out.println("Stopped");
        }
    }

    @Override
    public void VolumeUp()
    {
        if(CUR_VOL >= MAX_VOL)
            return;
        CUR_VOL = CUR_VOL + 5;
        ChangeVol(CUR_VOL);
    }

    @Override
    public void VolumeDown()
    {
        if(CUR_VOL <= MIN_VOL)
            return;
        CUR_VOL = CUR_VOL - 5;
        ChangeVol(CUR_VOL);
    }

    private void ChangeVol(int percentage)
    {
        try
        {
            ProcessBuilder processBuilder = new ProcessBuilder("/usr/bin/amixer", "set", "\'PCM\'", percentage + "%");
            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
            (processBuilder.start()).waitFor();
        }
        catch (InterruptedException e)
        {
            System.out.println("Thread interrupted");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            System.out.println("I/O exception - " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void ToggleMute()
    {

    }

    @Override
    public boolean IsMute()
    {
        return false;
    }

    @Override
    public boolean isPlaying()
    {
        return false;
    }
}
