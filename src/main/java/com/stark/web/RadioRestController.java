package com.stark.web;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RadioRestController
{
   /* @RequestMapping({
            "/",
            "/index.html"
    })
    public String index() {
        return "forward:/index.html";
    }*/
    @GetMapping(path="/stations", produces = "application/json")
    public List<Station> getStations()
    {
        return StationsProvider.getStationsProvider().getStations();
    }

    @GetMapping(path="/station", produces = "application/json")
    public Station getCurrentStation()
    {
        return StationsProvider.getStationsProvider().getCurrentStation();
    }

    @PostMapping(path="/station")
    public void setStation(@RequestBody int stationIndex)
    {
        StationsProvider.getStationsProvider().SelectStation(stationIndex);
    }

    @PostMapping(path="/volume")
    public void setVolume(@RequestBody boolean isUp)
    {
        if(isUp)
            RadioPlayerFactory.getRadioPlayer(Application.m_MediaPlayerType).VolumeUp();
        else
            RadioPlayerFactory.getRadioPlayer(Application.m_MediaPlayerType).VolumeDown();
    }

    @PostMapping(path="/mute")
    public void toggleMute()
    {
        RadioPlayerFactory.getRadioPlayer(Application.m_MediaPlayerType).ToggleMute();
    }

    @GetMapping(path="/mute")
    public boolean getIsMute()
    {
        return RadioPlayerFactory.getRadioPlayer(Application.m_MediaPlayerType).IsMute();
    }

    @PostMapping(path="/play")
    public void play()
    {
        RadioPlayerFactory.getRadioPlayer(Application.m_MediaPlayerType).Play(StationsProvider.getStationsProvider().getCurrentStation());
    }

    @GetMapping(path="/play", produces = "application/json")
    public boolean isPlaying()
    {
        return RadioPlayerFactory.getRadioPlayer(Application.m_MediaPlayerType).isPlaying();
    }

    @PostMapping(path="/stop")
    public void stop()
    {
        RadioPlayerFactory.getRadioPlayer(Application.m_MediaPlayerType).Stop();
    }

    @PostMapping(path="/next", produces="application/json")
    public Station next()
    {
        Station station = StationsProvider.getStationsProvider().goToNextStation(false);
        RadioPlayerFactory.getRadioPlayer(Application.m_MediaPlayerType).Play(station);
        return station;
    }

    @PostMapping(path="/previous", produces="application/json")
    public Station previous()
    {
        Station station = StationsProvider.getStationsProvider().goToNextStation(true);
        RadioPlayerFactory.getRadioPlayer(Application.m_MediaPlayerType).Play(station);
        return station;
    }

}
