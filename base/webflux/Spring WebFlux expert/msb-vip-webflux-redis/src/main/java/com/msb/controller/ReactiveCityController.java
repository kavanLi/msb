package com.msb.controller;

import com.msb.bean.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/city")
public class ReactiveCityController {

    @Autowired
    private ReactiveRedisTemplate reactiveRedisTemplate;

    @GetMapping(value = "/{id}")
    public Mono<City> findCityById(@PathVariable("id") Long id){
        String key = "city_" + id;
        ReactiveValueOperations<String,City> operations = reactiveRedisTemplate.opsForValue();
        Mono<City> city = operations.get(key);
        return city;
    }
    @PostMapping
    public Mono<City> saveCity(@RequestBody City city){
        return reactiveRedisTemplate.opsForValue().getAndSet("city_" + city.getId(),city);
    }

    @DeleteMapping("/{id}")
    public Mono<Long> deleteCity(@PathVariable("id") Long id){
        String key = "city_" + id;
        return reactiveRedisTemplate.delete(key);
    }










}
