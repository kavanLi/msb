/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.msb.rocket.transaction;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionListenerImpl implements TransactionListener {
    //执行本地事务（）
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        //todo 执行本地事务 update A...
        System.out.println("begin   本地事务  ,A服务器的TransactionId：" + msg.getTransactionId());
        System.out.println("update A ...(A账户减100块) :"+df.format(new Date()));
        System.out.println("commit "+df.format(new Date()));
        //以上的代码都是李老师的模拟--在数据库中对 A账户减100块。
        //return LocalTransactionState.ROLLBACK_MESSAGE; //如果上述代码发生异常或者执行不成功，认定失败了，
        // return LocalTransactionState.COMMIT_MESSAGE; //这里本地事务执行成功了
        return LocalTransactionState.UNKNOW;//这里本地事务需要等待其他的（等待人脸识别的结果）
    }
    //事务回查  默认是60s（不同的版本时间不同），一分钟检查一次
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        //打印每次回查的时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println("A服务器本地事务  ,TransactionId：" + msg.getTransactionId());
        System.out.println("commit "+df.format(new Date()));
        //这里检测到本地事务已经成功提交（检测 update A ...(A账户减100块)  这段SQL有没有执行成功）
        //todo 情况3.1：业务回查成功！
        System.out.println("事务回查失败：执行本地事务成功，确认消息");
       // return LocalTransactionState.COMMIT_MESSAGE;
         return LocalTransactionState.UNKNOW;//这里本地事务需要等待其他的（比如密码支付或延时之类的）
    }
}
