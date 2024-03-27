package com.mashibing.smsgateway.netty4.entity;

import com.mashibing.smsgateway.netty4.utils.Command;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;


public class CmppActiveTestResp extends CmppMessageHeader {
    public CmppActiveTestResp() {
        super(Command.CMPP_ACTIVE_TEST_RESP, Command.CMPP2_VERSION);
    }

    /**
     * 实现类必须自定义对象序列化
     *
     * @return
     */
    @Override
    public byte[] toByteArray() {
        ByteBuf buf = Unpooled.buffer(4 + 4 + 4 + 1);
        buf.writeInt(4 + 4 + 4 + 1);
        buf.writeInt(Command.CMPP_ACTIVE_TEST_RESP);
        buf.writeInt(0);
        buf.writeByte(0);
        return buf.array();
    }
}
