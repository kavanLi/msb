# 一、介绍

Spring Cloud LoadBalancer是Spring Cloud官方自己提供的客户端负载均衡器,抽象和实现，用来替代Ribbon（已经停更），

# 二、Ribbon和Loadbalance 对比

| **组件**            | **组件提供的负载策略**                                                                                                                                                                                          | **支持负载的客户端**     |
| ------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------ |
| Ribbon                    | 随机 RandomRule<br />轮询 RoundRobinRule    <br />重试 RetryRule<br />最低并发 BestAvailableRule<br />可用过滤 AvailabilityFilteringRule<br />响应时间加权重 ResponseTimeWeightedRule<br />区域权重 ZoneAvoidanceRule | Feign或openfeign、RestTemplate |
| Spring Cloud Loadbalancer | RandomLoadBalancer 随机（高版本有，此版本没有RoundRobinLoadBalancer 轮询（默认）                                                                                                                                      | Ribbon 所支持的、WebClient     |

LoadBalancer 的优势主要是，支持**响应式编程**的方式**异步访问**客户端，依赖 Spring Web Flux 实现客户端[负载均衡](https://so.csdn.net/so/search?q=负载均衡&spm=1001.2101.3001.7020)调用。

# 三、整合LoadBlance

> 注意如果是Hoxton之前的版本，默认负载均衡器为Ribbon，需要移除Ribbon引用和增加配置spring.cloud.loadbalancer.ribbon.enabled: false。

## 1、升级版本

| Spring Cloud Alibaba | Spring cloud            | Spring Boot   |
| -------------------- | ----------------------- | ------------- |
| 2.2.6.RELEASE        | Spring Cloud Hoxton.SR9 | 2.3.2.RELEASE |

## 2、移除ribbon依赖，增加loadBalance依赖

```pom
<!--nacos-服务注册发现-->
<dependency>
	<groupId>com.alibaba.cloud</groupId>
	<artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
	<exclusions>
		<!--将ribbon排除-->
		<exclusion>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
		</exclusion>
	</exclusions>
</dependency>


<!--添加loadbalanncer依赖, 添加spring-cloud的依赖-->
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-loadbalancer</artifactId>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```

# 四、自定定义负载均衡器

```java
package com.msb.order.loadbalance;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.reactive.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.reactive.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.reactive.Request;
import org.springframework.cloud.client.loadbalancer.reactive.Response;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Random;

public class CustomRandomLoadBalancerClient implements ReactorServiceInstanceLoadBalancer {
 
    // 服务列表
    private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;
 
    public CustomRandomLoadBalancerClient(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider) {
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
    }
 
    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider.getIfAvailable();
        return supplier.get().next().map(this::getInstanceResponse);
    }
 
    /**
     * 使用随机数获取服务
     * @param instances
     * @return
     */
    private Response<ServiceInstance> getInstanceResponse(
            List<ServiceInstance> instances) {
        System.out.println("进来了");
        if (instances.isEmpty()) {
            return new EmptyResponse();
        }
 
        System.out.println("进行随机选取服务");
        // 随机算法
        int size = instances.size();
        Random random = new Random();
        ServiceInstance instance = instances.get(random.nextInt(size));
 
        return new DefaultResponse(instance);
    }
}
```

```java
@EnableDiscoveryClient
@SpringBootApplication
// 设置全局负载均衡器
@LoadBalancerClients(defaultConfiguration = {CustomRandomLoadBalancerClient.class})
// 指定具体服务用某个负载均衡
//@LoadBalancerClient(name = "msb-stock",configuration = CustomRandomLoadBalancerClient.class)
//@LoadBalancerClients(
//        value = {
//                @LoadBalancerClient(value = "msb-stock",configuration = CustomRandomLoadBalancerClient.class)
//        },defaultConfiguration = LoadBalancerClientConfiguration.class
//)
public class OrderApplication {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class);
    }
}
```

# 五、重试机制

```yaml
spring:
  cloud:
    loadbalancer:
      #以下配置为LoadBalancerProperties 配置类
      clients:
        #default 表示去全局配置，如要针对某个服务，则填写毒地应的服务名称即可
        default:
          retry:
            enbled: true
            #是否有的的请求都重试，false表示只有GET请求才重试
            retryOnAllOperation: true
            #同一个实例的重试次数，不包括第一次调用：比如第填写3 ，实际会调用4次
            maxRetriesOnSameServiceInstance: 3
            #其他实例的重试次数，多节点情况下使用
            maxRetriesOnNextServiceInstance: 0
```

# 六、源码分析

## 1、猜测源码的实现

我们这里是给RestTemplate增加了@LoadBalanced就实现了负载均衡，我们学习Ribbon的时候是也是在RestTemplate上加了@LoadBalanced也实现了负载均衡，当时我们说RestTemplate上面有个扩展点ClientHttpRequestInterceptor， 我们Ribbon通过LoadBalancerInterceptor实现了这个扩展点，将msb-stock替换为 192.168.0.3:8003,如果所示：通过LoadBalancerClient 的实现类 RibbbonLoadBalancerClient 实现负载

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/e73cbc1106254db4896797653abd6ce6.png)

那我们现在我们想LoadBalancer是不是也是同样的功能呢？ 我们发现LoadBalancerClient 只有一个实现类是BlockingLoadBalancerClient

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/dd07f1e6ee324197a364598aeef2e432.png)

这是我们大概的猜想，他应该和我们的Ribbon的整体逻辑差不多

## 2、初始化过程

依旧是以前的逻辑找自动装配类，进入我们spring-cloud-starer-loadbalnecer:2.2.6.REALEAS  查找spring.factories，我们发现里面并没有对应spring.factories，这说明的这个starter只是起到jar管理的作用（查看的mybatis和SpringBoot整合的源码的话，会发现也是这样），所以我们进入pom中会发现应该是在spring-cloud-loadbalancer里面。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/a6e06b2badae4e939000a24c7a18e4f1.png)

我们分析这里自动配置类BlockingLoadBalancerClientAutoConfiguration和我们刚才分析的BlockingLoadBalancerClient前边名称一样，那这个应该是我们重点分析的自动配置类

进入BlockingLoadBalancerClientAutoConfiguration 你会发现这里和Ribbon中的配置相似，都是在LoadBalancerAutoConfiguration之前

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/65aa41f2ef7647f1a6ae2f728e0d8c81.png)

而LoadBalancerAutoConfiguration和我们将Ribbon中的配置是一样的，如下：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/a8afa54b72b44e6a9a8e18b09997f3e6.png)

在BlockingLoadBalancerClientAutoConfiguration中我们看到一个重要的类BlockingLoadBalancerClient，这个类在前面我们分析过，通过他我们进行的负载均衡，里面有个参数是LoadBalancerClientFactory，这个参数我们可以想起我们讲解Ribbon中的SpringClientFactory，那哪里创建的他呢？

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/40b64ab1673c488480ecd5992752e30e.png)

我们全文搜索会发先：在 LoadBalancerAutoConfiguration里面，这个类注意是在loadbalance包下和上面我们加载的LoadBalancerAutoConfiguration不是一个

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/46fa3168d0744724ae9ee8f9d406e3a2.png)

对应加载配置和顺序如下：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/0fa08e5c821745e99850775972544c15.png)

## 3、获取负载均衡器

RestTemplate发送请求一定经过LoadBalancerInterceptor，中的intercept方法，这里loadBalancer是BlockingLoadBalancerClient

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/d8d1950e676249f2b739111a6cb38fd6.png)

这里获取负载均衡器

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/4ef0eda832c64889a17fc1d14b0f9b23.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/0e6fb944111541b1b36df7b82aae8a0c.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/e3b833937b7e4d118cc8cddc9042e8c1.png)

这里就是从上面文中获取负载均衡器：RoundRobinLoadBalancer

## 4、获取实例

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/3bcb96106a5a4fb38847a7883773f6f2.png)

掉用RoundRobinLoadBalancer.choose方法

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/30eaebc837e540658d45b356d54b8f13.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/1c80ada03b684e0a8a48097afd89d7b0.png)

利用求余的方式选择一个实例，看到这我们发现实例列表已经获取到了，那什么时候获取到的呢？

## 5、获取服务实例列表

我们的服务列表是作为ServiceInstanceListSupplier的一个属性，那我们需要看在哪里创建的这个类：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/2a58a997a7424b8a950bd89f97ea360e.png)

在LoadBalancerClientConfiguration创建对应的类ServiceInstanceListSupplier

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/773745527d224d339565de5e1a9804d1.png)我们看他withDiscoveryClient方法：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/bb20aa3e0137473cba34190c3305b5ce.png)

在DiscoveryClientServiceInstanceListSupplier构造方法里面：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/aaeaaffab7b24428bd7ad35f6304647b.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/06ca699494d5481d90ab4d2864fe0dab.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/2be35f0ca623490f8824f19294611857.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/87137f7a9bcf47dfad8c951314634852.png)

## 6、进行调用

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/c014a7ff4618497b90f36723fafe59cd.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/23163b2cba384a928d48420ca73b0fbd.png)

这个request前面已经构建出来

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/d4dfaa8e538a46a3951bb540385b816d.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/11f13912da0948e2856750a6f9ca2401.png)

重构URL

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/097ac956245d479fa97917215c2e31c4.png)

在下面进行执行

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/b1378da8f5164278a8d1ed6216784017.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/8d3c320c0b9a4ef3afde1a5ddcfea4ef.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/bd361a9124c1496bb62469cb50f0d0cd.png)![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660801532066/2b42a34a62694c95b42d8ff510d7d147.png)
