package com.mashibing.chain.example01;

/**
 * @author spikeCong
 * @date 2022/10/14
 **/
public class Client {

    public static void main(String[] args) {
        Handler h1 = new HandlerA();
        Handler h2 = new HandlerB();
        Handler h3 = new HandlerC();

        //创建处理链
        h1.setSuccessor(h2);
        h2.setSuccessor(h3);

        RequestData requestData = new RequestData("请求数据: ABCD");
        //调用处理链头部的方法
        h1.handle(requestData);
    }
}
