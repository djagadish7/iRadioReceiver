package com.stark.web;

import java.util.ArrayList;
import java.util.List;

public class RadioPlayerFactory
{
    enum MEDIA_PLAYER
    {
        MPG123,
        JAVAX
    };

    private static List<IRadioPlayer> m_radioPlayers = new ArrayList<>();

    public static IRadioPlayer getRadioPlayer(MEDIA_PLAYER media_player)
    {
        if (media_player == MEDIA_PLAYER.JAVAX) {
            for (IRadioPlayer player : m_radioPlayers) {
                if (player instanceof JavaxRadioPlayer)
                    return player;
            }
            IRadioPlayer player = (IRadioPlayer) new JavaxRadioPlayer();
            m_radioPlayers.add(player);
            return player;
        }
        if (media_player == MEDIA_PLAYER.MPG123)
        {
            for (IRadioPlayer player : m_radioPlayers)
            {
                if (player instanceof MPG123RadioPlayer)
                    return player;
            }
            IRadioPlayer player = (IRadioPlayer) new MPG123RadioPlayer();
            m_radioPlayers.add(player);
            return player;
        }
        return null;
    }
}