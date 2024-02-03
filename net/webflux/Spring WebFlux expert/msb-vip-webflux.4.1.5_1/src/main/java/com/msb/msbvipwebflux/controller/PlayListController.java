package com.msb.msbvipwebflux.controller;

import com.msb.msbvipwebflux.bean.Song;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PlayListController {
    @RequestMapping("/play-list-view-thy")
    public String getPlayList(Model model){
       List<Song> songs = new ArrayList<>();
       Song song = null;
       for(int i = 0;i <1000;i++){
           song = new Song(i,"张三" + i,"1001" + i,"专辑" + (i % 3));
           songs.add(song);
       }

        Flux<Song> playListStream = Flux.fromIterable(songs).delayElements(Duration.ofMillis(500));
       model.addAttribute("playList",new ReactiveDataDriverContextVariable(playListStream,1,1));
       return "play-list-view";
    }
}
