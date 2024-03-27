package com.example.demo.designpattern.bridge;

public class WarmGift extends Gift {

    public WarmGift(GiftImpl impl) {
        this.impl = impl;
    }

}
