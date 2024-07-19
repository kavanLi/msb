package com.msb.caffeine.caffeineDemo;

import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.github.benmanes.caffeine.cache.stats.StatsCounter;

public class MyStatsCounter implements StatsCounter {
    @Override
    public void recordHits( int count) {
        System.out.println("命中之后执行的操作");
    }
    @Override
    public void recordMisses( int count) {
    }
    @Override
    public void recordLoadSuccess( long loadTime) {
    }
    @Override
    public void recordLoadFailure( long loadTime) {
    }
    @Override
    public void recordEviction() {
    }
    @Override
    public CacheStats snapshot() {
        return null;
    }
}
