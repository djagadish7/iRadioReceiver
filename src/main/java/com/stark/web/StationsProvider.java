package com.stark.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class StationsProvider
{
    private static StationsProvider m_stationProvider = null;
    private List<Station> m_stations = null;
    private Station m_currentStation = null;
    private int m_currentStationIndex = 0;

    public static StationsProvider getStationsProvider()
    {
        if(m_stationProvider == null)
        {
            ClassLoader classLoader = new Application().getClass().getClassLoader();
            String file = classLoader.getResource("stations.db").getFile();
            m_stationProvider = new StationsProvider(file);
        }

        return m_stationProvider;
    }

    public StationsProvider(String filePath)
    {
        m_stations = new ArrayList<Station>();
        File file = new File(filePath);
        String content = null;
        try
        {
            content = new String(Files.readAllBytes(file.toPath()));
            String[] lines = content.split("\n");
            for (String line: lines)
            {
                String[] name_link = line.split("#");
                m_stations.add(new Station(name_link[0], name_link[1]));
            }
            if(m_stations.size() > 0)
                m_currentStation = m_stations.get(0);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public boolean SelectStation(int index)
    {
        if(index > m_stations.size() && index > 0)
        {
            return false;
        }
        m_currentStationIndex = index;
        m_currentStation = m_stations.get(index - 1);
        return true;
    }

    public Station getCurrentStation()
    {
        return m_currentStation;
    }

    public Station goToNextStation(boolean reverse)
    {
        int current_index = m_stations.indexOf(m_currentStation) + 1;
        if((reverse && m_currentStationIndex == 0) || (!reverse && (m_currentStationIndex == m_stations.size() -1)))
        {
            return m_currentStation;
        }
        m_currentStationIndex = reverse? m_currentStationIndex - 1 : m_currentStationIndex + 1;
        m_currentStation = m_stations.get(m_currentStationIndex);
        return m_currentStation;
    }

    public List<Station> getStations()
    {
        return m_stations;
    }
}
