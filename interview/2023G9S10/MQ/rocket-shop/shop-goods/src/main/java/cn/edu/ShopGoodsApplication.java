package cn.edu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**

 *类说明：springboot的启动类
 */
@SpringBootApplication
@MapperScan(basePackages = "cn.edu.dao")
public class ShopGoodsApplication {
    //启动方法(相当于启动一个Tomcat,端口8081)
    public static void main(String[] args) {
        SpringApplication.run(ShopGoodsApplication.class, args);
    }
}
