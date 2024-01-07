package cn.edu;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**

 *类说明：springboot的启动类
 */

@SpringBootApplication
@MapperScan("cn.edu.dao")
public class ShopOrderApplication {
    //启动方法(相当于启动一个Tomcat,同时默认端口8080)
    public static void main(String[] args) {
        SpringApplication.run(ShopOrderApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}