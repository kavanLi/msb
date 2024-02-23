package com.bobo.mp.controller;

import java.time.Duration;
import java.util.Date;

import com.bobo.mp.domain.pojo.DynaAmsOrgprovisions;
import com.bobo.mp.domain.pojo.User;
import com.bobo.mp.repository.ReactiveItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * https://www.baeldung.com/spring-webflux
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class WebFluxController {

    @Autowired
    private ReactiveItemRepository reactiveItemRepository;

    @GetMapping("/{id}")
    public Mono <DynaAmsOrgprovisions> getEmployeeById(@PathVariable String id) {
        DynaAmsOrgprovisions dynaAmsOrgprovisions = DynaAmsOrgprovisions.builder()
                .id(123L)
                .accountNo("sdfsdf")
                .createDatetime(new Date())
                .build();
        return Mono.just(dynaAmsOrgprovisions);
    }

    @GetMapping("/user1")
    public Mono <String> user1() {
        return Mono.just("Hello, WebFlux !");
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, WebFlux !";
    }

    @GetMapping("/user")
    public Mono<User> getUser() {
        User user = new User();
        user.setUserName("犬小哈");
        user.setEmail("欢迎关注我的公众号: 小哈学Java");
        return Mono.just(user);
    }

    /**
     * User 对象是通过 Mono 对象包装的，你可能会问，为啥不直接返回呢？
     * 在 WebFlux 中，Mono 是非阻塞的写法，只有这样，你才能发挥 WebFlux 非阻塞 + 异步的特性。
     *
     * 补充：在 WebFlux 中，除了 Mono 外，还有一个 Flux，这哥俩均能充当响应式编程中发布者的角色，不同的是：
     *
     * Mono：返回 0 或 1 个元素，即单个对象。
     * Flux：返回 N 个元素，即 List 列表对象。
     * @return
     */
    @GetMapping
    public Flux <DynaAmsOrgprovisions> getAllEmployees() {
        return reactiveItemRepository.findAll();
    }

    @PostMapping("/update")
    public Mono<DynaAmsOrgprovisions> updateEmployee(@RequestBody DynaAmsOrgprovisions employee) {
        return reactiveItemRepository.save(employee);
    }

    /**
     * 在这个例子中：
     *
     * @return
     * @Controller 表示这是一个控制器类。
     * @RequestMapping("/api") 表示这个控制器处理 /api 下的所有请求。
     * @GetMapping("/numbers") 表示处理 GET 请求，并映射到 /api/numbers。
     * produces = MediaType.APPLICATION_STREAM_JSON_VALUE 表示这个接口产生的数据是一个流，并且是 JSON 格式的。
     * 然后，在 getNumbers 方法中，我们使用 Reactor 的 Flux 类创建了一个无限的流，每秒生成一个递增的数字。这个流会被自动转换为 JSON 格式，并以流的方式传输给客户端。
     *
     * 这只是一个简单的示例，WebFlux 还提供了许多其他功能，例如处理请求和响应、使用过滤器、异常处理等等。你可以根据项目的需要，进一步深入学习和应用 WebFlux。
     */
    @GetMapping(value = "/numbers", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux <Integer> getNumbers() {
        // 创建一个无限流，每秒生成一个递增的数字
        Flux <Integer> map = Flux.interval(Duration.ofSeconds(1))
                .map(Long::intValue).take(10);
        log.info(String.valueOf(map));
        return map;
    }


}
