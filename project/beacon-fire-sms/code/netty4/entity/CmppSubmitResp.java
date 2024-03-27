package com.mashibing.smsgateway.netty4.entity;


import com.mashibing.smsgateway.netty4.utils.MsgUtils;
import org.apache.commons.lang3.ArrayUtils;


/**
 * CMPP响应信息
 */
public class CmppSubmitResp {

    private int sequenceId;

    private int result;

    private long msgId;

    public CmppSubmitResp(byte[] bytes) {
        this.sequenceId = MsgUtils.bytesToInt(ArrayUtils.subarray(bytes, 8, 12));
        this.msgId = MsgUtils.bytesToLong(ArrayUtils.subarray(bytes, 12, 20));
        this.msgId = Math.abs(this.msgId);
        this.result = bytes[20];
    }

    public int getResult() {
        return result;
    }

    public long getMsgId() {
        return msgId;
    }

    public int getSequenceId() {
        return sequenceId;
    }

}
