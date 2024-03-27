package com.example.demo.designpattern.bridge;

public class WildGift extends Gift {

    public WildGift(GiftImpl impl) {
        this.impl = impl;
    }
}
