package com.msb.service;

import com.msb.pojo.Player;

import java.util.List;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public interface PlayerService {
    int addPlayer(Player player);

    List<Player> getAllPlayer();
}
