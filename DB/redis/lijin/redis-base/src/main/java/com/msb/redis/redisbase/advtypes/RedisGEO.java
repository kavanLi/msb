package com.msb.redis.redisbase.advtypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;
import redis.clients.jedis.params.GeoRadiusParam;

import java.util.List;
import java.util.Map;

@Component
public class RedisGEO {

    public final static String RS_GEO_NS = "rg:";

    @Autowired
    private JedisPool jedisPool;

    /**
     *
     * @param key
     * @param longitude 经度
     * @param latitude 纬度
     * @param member 成员名
     * @return
     */
    public Long addLocation(String key, double longitude, double latitude, String member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.geoadd(RS_GEO_NS+key,longitude,latitude,member);
        } catch (Exception e) {
            return null;
        } finally {
            jedis.close();
        }
    }

    /**
     *
     * @param key
     * @param memberCoordinateMap 成员为key，经纬度为value的map
     * @return
     */
    public Long addLocations(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.geoadd(RS_GEO_NS+key,memberCoordinateMap);
        } catch (Exception e) {
            return null;
        } finally {
            jedis.close();
        }
    }

    public List<GeoRadiusResponse> nearbyMore(String key, String member, double radius,
                                              boolean withDist, boolean isASC) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            GeoRadiusParam geoRadiusParam = new GeoRadiusParam();
            if (withDist) geoRadiusParam.withDist();
            if(isASC) geoRadiusParam.sortAscending();
            else geoRadiusParam.sortDescending();
            return jedis.georadiusByMember(RS_GEO_NS+key, member, radius, GeoUnit.KM,geoRadiusParam);
        } catch (Exception e) {
            return null;
        } finally {
            jedis.close();
        }
    }
}
