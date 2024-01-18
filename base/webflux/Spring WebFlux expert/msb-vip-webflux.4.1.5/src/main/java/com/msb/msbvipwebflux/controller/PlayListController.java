package com.msb.msbvipwebflux.controller;

import com.msb.msbvipwebflux.bean.Song;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PlayListController {

    @RequestMapping("/play-list-view-ftl")
    public Mono<String> getPlayList(final Model model){
        List<Song> songs = new ArrayList<>();
        Song song = null;
        for(int i = 0 ;i < 15;i++){
            song = new Song(i,"张三" + i, "1001" + i, "专辑" + i);
            songs.add(song);
        }
        // 转化为响应式流
        final Flux<Song> playListStream = Flux.fromIterable(songs);
        // 渲染数据
        return playListStream
                .collectList()
                .doOnNext(list -> model.addAttribute("playList",list))
                .then(Mono.just("/freemarker/play-list-view"));
    }
}
