package com.msb.programming;

import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.Random;

public class ProgrammingDemo3 {
    static class Connection implements AutoCloseable {
        private final Random rnd = new Random();
        static Connection newConnection() {
            System.out.println("创建Connection对象");
            return new Connection();
        }
        public Iterable<String> getData() {
            if (rnd.nextInt(10) < 3) {
                throw new RuntimeException("通信异常");
            }
            return Arrays.asList("数据1", "数据2");
        }
        // close方法可以释放内部资源，并且应该始终被调用，即使在getData执行期间发生错误也是如此。
        @Override
        public void close() {
            System.out.println("关闭Connection连接");
        }
    }
    public static void main(String[] args) {
//        try (Connection connection = Connection.newConnection()) {
//            connection.getData().forEach(data -> System.out.println("接收的数据：" +
//                    data));
//        } catch (Exception e) {
//            System.err.println("错误信息：" + e);
//        }

        Flux.using(
                Connection::newConnection,
                connection -> Flux.fromIterable(connection.getData()),
                Connection::close
        ).subscribe(
                data -> System.out.println("onNext接收到数据：" + data),
                ex -> System.err.println("onError接收到的异常信息：" +ex),
                () -> System.out.println("处理完毕")
        );
    }
}
