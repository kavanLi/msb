package com.msb.controller;

import com.msb.pojo.Player;
import com.msb.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@Controller
public class PlayerController  {

    @Autowired
    private PlayerService playerService;

    @RequestMapping("addPlayer")
    public String addPlayer(Player player){
        // 调用服务层方法,将数据保存进入数据库
        playerService.addPlayer(player);
        // 页面跳转至player信息展示页
        return "redirect:/showPlayer.jsp";
    }

    @RequestMapping("getAllPlayer")
    @ResponseBody
    public List<Player> getAllPlayer(){
        return playerService.getAllPlayer();
    }

}
