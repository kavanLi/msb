package com.msb.redis.redisbase.basetypes;

import com.msb.redis.redisbase.advtypes.RedisGEO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class TestRedisGEO {

    @Autowired
    private RedisGEO redisGEO;

    @Test
    void testAddLocation(){
        System.out.println(redisGEO.addLocation("cities",116.28,39.55,"beijing"));
    }

    @Test
    void testAddLocations(){
        Map<String, GeoCoordinate> memberCoordinateMap = new HashMap<>();
        memberCoordinateMap.put("tianjin",new GeoCoordinate(117.12,39.08));
        memberCoordinateMap.put("shijiazhuang",new GeoCoordinate(114.29,38.02));
        memberCoordinateMap.put("tangshan",new GeoCoordinate(118.01,39.38));
        memberCoordinateMap.put("baoding",new GeoCoordinate(115.29,38.51));
        System.out.println(redisGEO.addLocations("cities",memberCoordinateMap));
    }

    @Test
    void testNearby(){
        List<GeoRadiusResponse> responses = redisGEO.nearbyMore("cities","beijing",150,
                true,true);
        for(GeoRadiusResponse city:responses){
            System.out.println(city.getMemberByString());
            System.out.println(city.getDistance());
            //System.out.println(city.getCoordinate());
            System.out.println("-------------------------");
        }
    }
}
