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
package org.apache.rocketmq.example.transaction;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionListenerImpl implements TransactionListener {
    //执行本地事务
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        //todo 执行本地事务 update A...
        System.out.println("update A ... where transactionId:"+msg.getTransactionId() +":"+df.format(new Date()));
        //System.out.println("commit");
        //todo 情况1：本地事务成功
        //return LocalTransactionState.COMMIT_MESSAGE;
        //todo 情况2：本地事务失败
        //System.out.println("rollback");
        //return LocalTransactionState.ROLLBACK_MESSAGE;
        //todo 情况3：业务复杂，还处于中间过程或者依赖其他操作的返回结果，就是unknow
        System.out.println("业务比较长，还没有处理完，不知道是成功还是失败！");
        return LocalTransactionState.UNKNOW;
    }
    //事务回查  默认是60s，一分钟检查一次
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        //打印每次回查的时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println("checkLocalTransaction:"+df.format(new Date()));// new Date()为获取当前系统时间
        //todo 情况3.1：业务回查成功！
        System.out.println("业务回查：执行本地事务成功，确认消息");
        return LocalTransactionState.COMMIT_MESSAGE;
        //todo 情况3.2：业务回查回滚！
       // System.out.println("业务回查：执行本地事务失败，删除消息");
       // return LocalTransactionState.ROLLBACK_MESSAGE;
        //todo 情况3.3：业务回查还是UNKNOW！
         //System.out.println("业务比较长，还没有处理完，不知道是成功还是失败！");
         //return LocalTransactionState.UNKNOW;
    }
}
