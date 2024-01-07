package com.msb.service.impl;

import com.msb.mapper.PlayerMapper;
import com.msb.pojo.Player;
import com.msb.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private PlayerMapper playerMapper;

    @Override
    public int addPlayer(Player player) {
        return playerMapper.addPlayer(player);
    }

    @Override
    public List<Player> getAllPlayer() {
        return playerMapper.getAllPlayer();
    }
}
