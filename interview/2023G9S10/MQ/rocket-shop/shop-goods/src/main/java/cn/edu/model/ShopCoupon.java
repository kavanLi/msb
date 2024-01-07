package cn.edu.model;

import java.math.BigDecimal;
import java.util.Date;

public class ShopCoupon {
    private Long couponId;

    private BigDecimal couponPrice;

    private Integer isUsed;

    private Date usedTime;

    public ShopCoupon(Long couponId, BigDecimal couponPrice, Integer isUsed, Date usedTime) {
        this.couponId = couponId;
        this.couponPrice = couponPrice;
        this.isUsed = isUsed;
        this.usedTime = usedTime;
    }

    public ShopCoupon() {
        super();
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public BigDecimal getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(BigDecimal couponPrice) {
        this.couponPrice = couponPrice;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    public Date getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Date usedTime) {
        this.usedTime = usedTime;
    }
}