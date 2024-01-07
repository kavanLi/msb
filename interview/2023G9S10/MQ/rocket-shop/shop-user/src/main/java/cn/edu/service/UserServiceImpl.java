package cn.edu.service;

import cn.edu.dao.ShopCouponMapper;
import cn.edu.dao.shopCouponUniqueMapper;
import cn.edu.model.ShopCoupon;
import cn.edu.model.shopCouponUnique;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 *@author King老师
 *
 *类说明：用户相关的服务
 */
@Service
@Transactional
public class UserServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private ShopCouponMapper shopCouponMapper;


    @Autowired
    private shopCouponUniqueMapper shopCouponUniqueMapper;

    //使用优惠券
    public synchronized int useCoupon(long orderid, long couponId) {
        try {
            //去重表--确保幂等性
            shopCouponUnique shopCouponUnique  = new shopCouponUnique();
            shopCouponUnique.setOrderId(orderid);
            shopCouponUniqueMapper.insert(shopCouponUnique);
        }catch (Exception e) {
            logger.error("重复的修改库存信息：[" + orderid + "]");
            // e.printStackTrace();
            return 1;
        }
        //模拟业务卡顿  20ms
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ShopCoupon shopCoupon = shopCouponMapper.selectByPrimaryKey(couponId);
        shopCoupon.setIsUsed(1);
        shopCoupon.setUsedTime(new Date());
        if (shopCouponMapper.updateByPrimaryKey(shopCoupon) >= 0) {
            //logger.info("使用优惠券成功：[" + orderid + "]");
            return 1;
        } else {
            logger.error("使用优惠券失败：[" + orderid + "]");
            return -1;
        }
    }

    //回退优惠券
    public synchronized int CanceluseCoupon(long orderid, long couponId) {
        try {
            //去重表中有，才能证明是插入了，所以要回退
            shopCouponUnique shopCouponUnique  = new shopCouponUnique();
            shopCouponUnique.setOrderId(orderid);
            shopCouponUniqueMapper.insert(shopCouponUnique);
        }catch (Exception e) {
            ShopCoupon shopCoupon = shopCouponMapper.selectByPrimaryKey(couponId);
            shopCoupon.setIsUsed(0);
            shopCoupon.setUsedTime(null);
            if (shopCouponMapper.updateByPrimaryKey(shopCoupon) >= 0) {
                //logger.info("使用优惠券成功：[" + orderid + "]");
                return 1;
            } else {
                logger.error("使用优惠券失败：[" + orderid + "]");
                return -1;
            }
        }
        return 1;
    }
}
