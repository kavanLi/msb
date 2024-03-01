package com.example.demo.designpattern.builder;

public interface TerrainBuilder {
    TerrainBuilder buildWall();
    TerrainBuilder buildFort();
    TerrainBuilder buildMine();
    Terrain build();
}
