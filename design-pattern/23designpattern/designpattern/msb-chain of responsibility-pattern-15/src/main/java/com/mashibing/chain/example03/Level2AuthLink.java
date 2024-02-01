package com.mashibing.chain.example03;

import com.mashibing.chain.example02.AuthInfo;
import com.mashibing.chain.example02.AuthService;

import java.text.ParseException;
import java.util.Date;

/**
 * @author spikeCong
 * @date 2022/10/16
 **/
public class Level2AuthLink extends AuthLink {

    private Date beginDate = sdf.parse("2022-11-11 00:00:00");
    private Date endDate = sdf.parse("2022-11-31 00:00:00");

    public Level2AuthLink(String levelUserId, String levelUserName) throws ParseException {
        super(levelUserId, levelUserName);
    }

    @Override
    public AuthInfo doAuth(String uId, String orderId, Date authDate) {

        Date date = AuthService.queryAuthInfo(levelUserId, orderId);

        if(null == date){
            return new AuthInfo("0001","单号: "+
                    orderId," 状态: 待二级审核人审批", levelUserName);
        }

        AuthLink next = super.getNext();

        if(next == null){
            return new AuthInfo("0000","单号: "+
                    orderId," 状态: 二级审批完成", "审批人: " + levelUserName);
        }

        if(authDate.before(beginDate) || authDate.after(endDate)){
            return new AuthInfo("0000","单号: "+
                    orderId," 状态: 二级审批完成", "审批人: " + levelUserName);
        }

        return next.doAuth(uId,orderId,authDate);
    }
}
