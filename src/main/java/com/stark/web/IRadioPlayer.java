package com.stark.web;

public interface IRadioPlayer {
    public void Play(Station station);
    public void Stop();
    public void VolumeUp();
    public void VolumeDown();
    public void ToggleMute();
    public boolean IsMute();
    public boolean isPlaying();
}
