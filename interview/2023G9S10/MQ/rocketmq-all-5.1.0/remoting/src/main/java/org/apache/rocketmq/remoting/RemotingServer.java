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
package org.apache.rocketmq.remoting;

import io.netty.channel.Channel;
import java.util.concurrent.ExecutorService;
import org.apache.rocketmq.common.Pair;
import org.apache.rocketmq.remoting.exception.RemotingSendRequestException;
import org.apache.rocketmq.remoting.exception.RemotingTimeoutException;
import org.apache.rocketmq.remoting.exception.RemotingTooMuchRequestException;
import org.apache.rocketmq.remoting.netty.NettyRequestProcessor;
import org.apache.rocketmq.remoting.protocol.RemotingCommand;
//继承了 RemotingService 接口,对服务器端的通信服务进行了进一步定义
public interface RemotingServer extends RemotingService {
    //注册一个处理指定请求码的请求处理器，以及一个用于执行处理器的线程池；
    void registerProcessor(final int requestCode, final NettyRequestProcessor processor,
        final ExecutorService executor);
    //注册默认请求处理器，当请求码没有对应的请求处理器时，使用默认处理器处理请求；
    void registerDefaultProcessor(final NettyRequestProcessor processor, final ExecutorService executor);
    //获取当前服务器监听的端口号
    int localListenPort();
    //获取指定请求码的请求处理器及其对应的线程池
    Pair<NettyRequestProcessor, ExecutorService> getProcessorPair(final int requestCode);
    //获取默认请求处理器及其对应的线程池
    Pair<NettyRequestProcessor, ExecutorService> getDefaultProcessorPair();
    //创建一个新的 RemotingServer 实例，并指定监听的端口号
    RemotingServer newRemotingServer(int port);
    //关闭指定端口的 RemotingServer 实例
    void removeRemotingServer(int port);
    //同步方式发送请求，并等待响应，直到超时或收到响应为止
    RemotingCommand invokeSync(final Channel channel, final RemotingCommand request,
        final long timeoutMillis) throws InterruptedException, RemotingSendRequestException,
        RemotingTimeoutException;
    //异步方式发送请求，发送成功后立即返回，请求处理完成后，通过回调函数通知结果
    void invokeAsync(final Channel channel, final RemotingCommand request, final long timeoutMillis,
        final InvokeCallback invokeCallback) throws InterruptedException,
        RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException;
    //单向发送请求，不等待响应，发送成功后立即返回
    void invokeOneway(final Channel channel, final RemotingCommand request, final long timeoutMillis)
        throws InterruptedException, RemotingTooMuchRequestException, RemotingTimeoutException,
        RemotingSendRequestException;

}
