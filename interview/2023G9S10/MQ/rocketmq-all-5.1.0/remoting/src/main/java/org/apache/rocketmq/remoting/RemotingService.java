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
//远程通讯服务接口
public interface RemotingService {
    void start();//开始远程通信服务，启动监听器，等待客户端接入；

    void shutdown();//关闭远程通信服务，停止监听器；

    void registerRPCHook(RPCHook rpcHook);//向远程通信服务注册一个 RPCHook，用于在远程调用的前后添加拦截逻辑，比如权限检查、日志打印等；

    /**
     * Remove all rpc hooks.
     */
    void clearRPCHook();//从远程通信服务中移除所有 RPCHook。
}
