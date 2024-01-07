package com.msb.mapper;

import com.msb.pojo.Player;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public interface PlayerMapper {
    public int addPlayer(Player player);

    @Select("select * from player")
    List<Player> getAllPlayer();
}
