# 一、SpringCloud简介

## 1、SpringCloud是什么

- Spring Cloud是一系列框架的有序集合，这些框架为我们提供了分布式系统构建工具。

## 2、SpringCloud包含那些项目

| 项目           | 项目名称                                         |
| -------------- | ------------------------------------------------ |
| 服务注册于发现 | Alibaba Nacos、Netflix Eureka、Apache Zookper    |
| 分布式配置中心 | Alibaba Nacos、Spring Cloud Config               |
| 网关           | Spring Cloud Gateway、Netflix Zull               |
| 限流熔断器     | Alibaba Sentinel、Netflix Hystrix、 Resilience4j |
| 服务调用       | RestTemplate、Open Feign、Dubbo Spring Cloud     |
| 负载均衡       | Spring Cloud LoadBalancer、Netflix Ribbon        |
| 消息总线       | Spring Cloud Bus                                 |
| ...            | ....                                             |

## 3、SpringCloud版本选择

https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E

| Spring Cloud Alibaba Version | Spring Cloud Version       | Spring Boot Version |
| ---------------------------- | -------------------------- | ------------------- |
| 2.1.4.RELEASE                | Spring Cloud Greenwich.SR6 | 2.1.13.RELEASE      |

| Spring Cloud Alibaba Version | Sentinel Version | Nacos Version | Seata Version |
| ---------------------------- | ---------------- | ------------- | ------------: |
| 2.1.4.RELEASE                | 1.8.0            | 1.4.1         |         1.3.0 |

# 二、Nacos安装以及编译

## 1、下载源码

解压进入目录中进行maven编译

```pom
mvn clean install -DskipTests -Drat.skip=true -f pom.xml
```

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/a7aad72f3e894e8fbddee6cd9daa379d.png)

> 注意：编译的时候可能需要你自己指定jdk版本，可以修改maven配置文件conf/settings.xml
>
> ![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/f1fdb66f9a5e40e280ba734e496ff814.png)
>
> ```xml
> <profile>  
> <id>jdk-1.8</id>  
> <activation>  
>    <activeByDefault>true</activeByDefault>  
>    <jdk>1.8</jdk>  
> </activation>  
> <properties>  
>    <maven.compiler.source>1.8</maven.compiler.source>  
>    <maven.compiler.target>1.8</maven.compiler.target>  
>    <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>  
> </properties>  
> </profile> 
> ```

## 2、源码单机启动

- 将jdk版本都设置为jdk8
- 设置参数

```xml
 -Dnacos.standalone=true
```

## 3、单机启动服务

- 下载nacos服务

  https://github.com/alibaba/nacos/releases

  ![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/b5029ca5d167466bb61be38f98d0d6e6.png)
- 解压进入bin目录
- 执行命令

  ```shell
  startup.cmd -m standalone
  ```

## 5、修改startup.cmd

将MODE模式改为standalone，这样下次直接双击startup.cmd就可以了

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/50e8bb614a2944459922d92342423788.png)

# 三、Nacos服务领域模型

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/f5f315ce86494c35bc38bab9d0b52da1.png)

service->cluster-> instanc【之所以他会这样设置就是为了大的互联网公司，多集群垮机房提供了解决方案。但我们小公司一般都不需要这样。】

Namespace:实现环境隔离，默认值public

Group:不同的service可以组成一个Group，默认值Default-Group

Service:服务名称
Cluster:对指定的微服务虚拟划分，默认值Default

Instance:某个服务的具体实例

Nacos服务注册中心于发现的领域模型的最佳实践。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/0bbad64c99b04261bf9dc1933d863a10.jpg)

NameSpace: 是我们生产，开发和测试环境的隔离。测试访问测试，生产访问生产。

Group:比如说有一类服务。他们都是为了交易而服务的。比方说我们的订单，比方说我们的支付。这个在服务注册中心的场景中并不常用。

Service:下一个层级就是service，同一个group有多个service，再服务下面就是集群的概念：

Cluster:比方我们可以有北京的集群也可以有上海的集群。那这个集群的概念意义是什么？ 我们可以设想这样的一个场景，比方阿里只在杭州部署一个淘宝的集群，那我们北方的人民去访问，是不是就会相对较慢，一般都会再南方和北方都设置两个集群。当北方人民访问的时候一般我们都访问北京这个集群的服务，其他的服务都会在北京的集群里面互相调用，而不会垮cluster进行访问，这样在同一个数据中心访问都是比较快的。这就是他的意义。

Instance: 这个就是实例，并且是多实例的，这样防止单点问题，从而实现高可用，当北京集群坏了，他会可以访问上海的集群。当一个实例挂掉，另一个实例会替代，当一个集群挂掉，另一个集群会替代上。这样就保证了高可用。很多公司宣传5个9或者4个9的可用，那么全年停机的时长不会超过固定的时长。

# 四、Nacos的使用

## 1、 正常使用http客户端调用

我们下订单的时候的调用：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/cb1801af00ec4560ba525dd6f6d5a242.png)

1、讲解单独调用的图
2、然后运行一下这个demo
3、提出问题
4、进行改造

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/291eb48cdd134d17894d9d3c4b87334d.jpg)

## 2、引入nacos

改造msb-stock

1、父pom引入依赖

```pom
  <dependency>
      <groupId>com.alibaba.cloud</groupId>
      <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
  </dependency>
```

2、启动注解

```java
@EnableDiscoveryClient
@SpringBootApplication
public class StockApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class);
    }
}
```

3、增加配置

```yaml
spring:
  cloud:
    nacos:
      discovery:
        service: msb-stock
        server-addr: localhost:8848
```

改造msb-order

2、启动注解：

```java
@EnableDiscoveryClient
@SpringBootApplication
public class OrderApplication {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class);
    }
}
```

3、增加配置

```java
spring:
  cloud:
    nacos:
      discovery:
        service: msb-order
        server-addr: localhost:8848
```

4、改造代码：

```java
  @GetMapping("/order/create")
    public String createOrder(Integer productId,Integer userId){
        String result = restTemplate.getForObject("http://msb-stock/stock/reduce//" + productId, String.class);
        return "下单成功";
    }
```

5、现在我们访问msb-stock ，但是restTemplate并不知道怎样调用

```java
@GetMapping("/order/create")
public String createOrder(Integer productId,Integer userId){
    // 此时restTemplate并不能识别msb-stock所以他不能进行调用
    // RestTempLate调用需要一个负载均衡器 1、 获取msb-stock对应服务列表 2、选择一个去调用
    // RestTemplate扩展点clientHttpRequestInterceptor
    // 我们有个组件ribbon 实现了这个扩展点 LoadBalancerInterceptor
	// 他做的事情就是将msb-stock:替换为：localhost:11001
    String result = restTemplate.getForObject("http://msb-stock/stock/reduce//" + productId, String.class);
    return "下单成功";
}
```

分析一下LoadBalancerInterceptor的拦截器Interceptor

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/9be010bb61624c1e9bddee2f5c108fc9.png)

我们可以进行如下改造：

```java
@Autowired
LoadBalancerClient loadBalancerClient;

@Bean
public RestTemplate restTemplate(){
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setInterceptors(Collections.singletonList(new LoadBalancerInterceptor(loadBalancerClient)));
    return restTemplate;
}
```

当然我们也可以进行如下改造：直接加入注解@LoadBalance后面我们会讲

```java
@LoadBalanced
@Bean
public RestTemplate restTemplate(){
    return new RestTemplate();
}
```

# 五、Nacos注册中心的原理

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1678263581076/bc47487f858e4d8ab7bbc94a7cbea2ce.png)

首先服务在启动的时候会将数据注册到注册中心，这样就服务只要启动就可以对外提供服务了， 那么同时他也要维持一个心跳，心跳的意义就是某个服务挂掉，我肯定让注册中心知道，如果服务挂了还没有告诉注册中心，那么注册中心会引导客户端调用挂掉的服务体验就不好了，所以注册中心重要的概念就是和我们的服务维持一个心跳，来实时的监听我们服务的状态，当服务挂掉就会将其从服务列表中踢掉，接下来肯定还有服务的调用者，他在调用服务之前一定会到注册中心拉取服务列表，获取可用的服务，并且还有一个定时任务来定时拉取服务列表，以保持服务列表的最新状态，其次还有更新的功能，这个功能就是当服务挂掉的时候通过心跳他会发现这服务不健康了，他会更新本地的服务注册表，同时将所有订阅这个注册表的服务进行更新。

# 六、 Nacos服务注册源码解析

## 6.1  源码方式打包

客户端源码中增加打包方式，将源码打入包中

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-source-plugin</artifactId>
  <version>3.2.1</version>
  <configuration>
    <attach>true</attach>
  </configuration>
  <executions>
  <execution>
    <phase>compile</phase>
    <goals>
      <goal>jar</goal>
    </goals>
  </execution>
  </executions>
</plugin>
```

然后打包：

```pom
mvn install -DskipTests
```

## 6.2 入口

https://github.com/alibaba/nacos/tree/1.4.1

首先我们会把源码下载下来，我们会通过源码的方式进行启动， 你可以通过debug的方式进行运行来判断他的运行过程。

我们从源码的角度来分析一下：服务启动后他会上报到注册中心：

NacosNamingService 就是服务注册和发现相关的类，他就是在这里将当前启动的服务调用注册实例的方法，我们看一下这个方法干什么了？

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/d50dc9844687464d95cdc68451dcb9f9.png)

![image-20211221124947544](E:\BaiduNetdiskWorkspace\springcloud alibaba\img\image-20211221124947544.png)

他就是拼接了一些参数发送http请求，到达服务注册中心进行发现，请求的就是这个路径：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/eb0bf376cb184c4294eeacc2895754b1.png)

好这就是对应的路径，我们回到官方文档的指南当中

https://nacos.io/zh-cn/docs/open-api.html

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/8eb9a8c098c34a1a8700e5812c947ab9.png)

好，按照我们讲到这里就不用再往里面看了，我们可以点进去看一下，

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/3a9c664dca6249fdad16e57c95d97576.png)

他真正的调用给你是在这里。![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/aeaea225816148e4b0094774657f94ba.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/6495fc23825e4991b4569d5d8aa506f2.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/1d30ca336ffd4bf695587046900341fd.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/ef03bb99a7d14493ab8b895a54b30a54.png)

在这里进行调用

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/692a52b21e7c4aedbb81ad9b834e912c.png)

好哪有同学问你怎么知道断点就达到这里，那我们看一下怎样查看源码的启动的路径，我们看一下我们订单微服务的路径，我们要集成nacos的服务发现功能，我们要引入我们的discovery的包，他是一个starter，前面我们学过springboot我们知道任何starter里面一定有个spring.factories，作为一个入口

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/b2b550a01c3d4f588927a84aec973585.png)

这里面动态加载的类很多，NacosServiceRegistryAutoConfiguration 从这名字我们能发现他是一个nacos服务注册的自动配置类，

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/766e6fbdf2dd45e892e5879fd2fafdb1.png)

这里面实现了三个类，我们看一下这个类NacosAUtoServiceRegistration

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/dfd83a7b72ff4bd9959a600e33637b51.png)

自动注册类，我们可以看一下他的集成关系，是一个ApplicationListener  spring启动完成后都会发送一个消息，applicaitonListener就是通过监听这个消息然后进行执行的。所以我们知道下一步我们应该怎么看：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/e84b50c6ff81474aa62b6c7ee39138f9.png)

所以查看他的抽象类。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/078e0cef418f45b69f584395ba7c545e.png)

查看onApplicationEvent方法：这样在服务发送完就会发送这样一个消息，收到这个消息就会调用这个bind方法

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/6d633f2ec25849e1b7a25720059232ae.png)

这里有个if return 我们就直接跳过，这一定是分支代码，像这样的分支代码我们就不要看，第一 次要看主线，所以我们直接看这里的start方法，如果后面这里没有对应的代码逻辑我们可以进入这个分支来看。 好，像这样start, begin，init，register方法都是很重要的方法，我们一定要进去看

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/6aba1e2f2325434aa4d4e2feadf08264.png)

第一个if就不用看，第二个if需要看，因为后面就没有逻辑了你看这个register()，应该就是这个方法，因为你就是查看注册的流程。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/9fcfb56888994f96846ae55ad55db2ea.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/3317bbb769354b4883d8bd71b0b8f220.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/05ede1cd070d43e4ae3fbd80ff97f61a.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/fc5dffb9e76b4b22b65e28c28c035518.png)

这里需要你知道SpringBoot自动装配的基本知识，其次要知道Spring启动发现的 基本知识。

## 6.3 服务注册

Nacos高并发支撑异步任务与内存队列剖析

刚才是在服务提供者上面讲的内容，现在我们服务注册中心来看一下

请求的是instance实例：这就是一个springmvc的controller，所有我们可以全文搜索 Controller，我们是instance实例吧，所以我们这里InstanceController

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/8d8eafa9e9024db294791874300107b3.png)

那之前我们用的是post请求所以我们查看post方法，这里有delete,update...,他这里是什么风格？ restFul

我们发现里面没有对应的DefaultGroup，在服务注册和发现的情况下这个group是不经常用的。你用的话只是自己的规范和方便管理的。在服务注册和发现中源码中都没有用。，命名空间，服务名，然后将我们参数转化为实例。这就我们服务模型中的三层模型。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/7c7b02a094ab4ca99a20582918d886bc.png)

那我们看一下他的注册实例里面做了什么？ 这里面我们注意我们是注册instance，我们就围绕着他，进行分析，是不是就来到addInstance了

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/98db629be5804a5f9f2edba513339e89.png)

createEmptyService

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/4c70c069200f4f499b197e0c5d91d08d.png)

1、获取service 初次获取一定为空，我们可以进去分析一下

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/fc79c0d079ac4fc583b224b2163f571e.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/3bd69db4c7d2435f9ecae57175e96bd1.png)

这里就是注册表，我们前面说过nacos服务领域模型【可以参考图】，这个map就是对应的注册表

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/9d2f2a22c51b4a5e8acd08bd08fadbec.png)

这里面设置服务和初始化

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/89a9183d47614c64850771154d4daa7e.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/fbadeabc8d8d44c986b8d9109db63af2.png)

服务初始化：心跳

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/b7a54de97fef4d9499ff83df6b826ede.png)

看这里是个scheduleNamingHealth是一个定时任务，我们只需要看一下task任务就可以

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/c6a9d4adee4e4b0fabc8dabdae0a264b.png)

task任务我们需要看一下run方法：

在这里我们看是获取所有的实例【可以点进去看一下】

当前时间 -  上次心跳时间 间隔超过15秒 则将实例设置为非健康， 当超过30秒没有收到心跳就直接剔除

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/3e48f31c905542b09a5d451e8bf360f4.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/4352cc959e874fe582137215c5c4ac99.png)

好，我们回来，这里名字起的特别好，createEmtyService，是创建一个空服务，后面我们的实例，是不是还会注册到里面，

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/1a94705f8a7c47f08aa3fd695561a337.png)

> 我们可以看一下服务模型，和我们以前说过的一样
>
> 命名空间 ，cluster 集群概念
>
> ![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/23dd02ea3ed74a12a38e2f2e522b739b.png)
>
> ![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/22b3ec0947dd43ee87dfa8a3087dacd6.png)
>
> 集群中对应的实例。
>
> ![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/86dd62d4b603468d98909f99f67146e3.png)

我们看一下addinstance

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/daaecbf61a144c5faa484b2790058c21.png)

构建对应的key:

```
String key = KeyBuilder.buildInstanceListKey(namespaceId, serviceName, ephemeral);
```

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/7ed8da0b54394c28a8219dd16d429a40.png)

```java
//获取注册实例的IP端口列表
List<Instance> instanceList = addIpAddresses(service, ephemeral, ips);
```

我们进入简单的看一下，我们发现这个add,remove，这里就是新增和移除实例

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/3af412e0ab3e4ea38f5df2ab0fa5fbad.png)

我们主要出里这写注册进来的ips，那我们就点击ips，高亮来显示看看，然后他会进行循环instance，这里我们可以看到如果他是移除就从map里面移除出去，如果是新增就在instanceMap中新增一下，最后将期返回。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/a2d302ff5d8f45bba2fb3b40ce119bc2.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/c98a7751b38f413cbf76d104b360948e.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/bb2f7f5552094469a43879a638bda1e3.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/1404fa4f3c7d4b6a8c0e3dc57a682de8.png)

现类，我们可以猜测一下，或者debug进去，当然这个类，我们点击一下，在声明的地方

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/8ae3abea48284a578aee1b3f70d934b1.png)

它指定了名称：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/2fcbb14db2514b399fe3e7e954a39650.png)

好，我们全文搜索看一下：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/4941de40e2ee47a89578f90776adad6a.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/6c18b31fedef43b19d48cacbdec09876.png)

我们知道前面说过ephemeral是true所以选择第一个： 有疑问：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/916e422d74da4b67865e0a75566ce70c.png)

我们应该调用EphemeralConsistencyService对应的put方法。但是EphemeralConsistencyService只是一个接口，我们应该调用对应的实现实例。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/3a2ab51031c44c059e7642989f92ab37.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/29655d4a95bf493480828f307ee82179.png)

我们看一下他的onput方法。

现在放到队列中：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/452eef6161214feeaedc9715290cc77c.png)

这里面就是把核心的请求放到blockquene里面，也就是一个阻塞队列中

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/586c6bc6c35841dba61da2584af25141.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/b401449c63ca4856a880ad0dcf0992ff.png)

整个注册的过程就这么简单，一个请求过来，最终将服务注册的请求，放到我们的阻塞队列当中，放到则色队列之后，整个阻塞队列就返回了。那放到阻塞队列之后，哪里有访问这个阻塞队列。

大家注意这个Notifier是一个线程，老师交大家一个技巧：如果遇到一个线程就需要看他的run方法因为run方法才是他 真正执行代码的地方。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/bf0df76e1a6c4b42950c65c1b449adcc.png)

在这里进行死循环进行数据处理，不断的处理客户端注册的信息，丢进来就实现后面的异步注册信息，

这个线程会一直在转，一直运行，如果他挂了那说明整个服务就挂掉了，好，你看着里面的异常也吃掉了，所以一直会运行，如果没有数据他会阻塞，阻塞会让出cpu

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/97d50d70bad84ab7958d65d82afcdee3.png)

注册表结构的分析：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/feea975d82dc4a57995df4e8eb74ee20.png)

首先当他是个change的时候，我们就进入onChange，他是个服务所以我们进入他的service里面去看：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/83bbdb69ec94401589dce0505c943774.png)

这里面就不用看了，先看权重，权重大于多少的时候设置最大值，权重小于多少的时候设置一个最小值。然后就是核心的方法updateIP

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/956302ff7d3a4c76ba4ee0318f223cad.png)

那这个updateIPs是做什么呢？ 做的就是我遍历我所有要注册的实例，然后就放到我们的clusterMap里面

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/6218a53939e844b79420308ebb63a7cc.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/6c5e0158567c4054b6f54eb80c50f112.png)

那到这里大家可能就有疑问了，那什么时候启动这个线程，来实时监听我们消息的阻塞队列呢？ 教大家如何找一下这个方法，我们现在看着个类叫Notifier，因为他本身就是一个线程，他会丢到一个线程池中进行运行，我们看一下他究竟是在哪里实例化的，

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/97a7e339058b4bc1b3a8f869cd868cba.png)

我们看到这个方法：这个注解就是当你的spring的一个类进行初始化之后进行调用的，那我们看一下这个init方法到底是做了什么

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/884c188e221546feaf2b183fe7816d87.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/c2043dfe0bc04c9f92da8100d3728dab.png)

是一个Scheduled线程池：

![image-20211222222903941](E:\BaiduNetdiskWorkspace\springcloud alibaba\img\image-20211222222903941.png)

也就是在对象初始化的时候就进行启动一个线程池，去运行notifier对应的方法。这个run方法就是这样run的。启动后就会实时监听异步队列。这样写的好处，就是将写和处理完全隔离了。通过监听高性能的内存队列，来处理这个事情，他这样的好处，1、提高性能![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/5911461029c747659ceb5f5c034384d0.png)

## 6.4 发送心跳机制

### 1、 客户端

回到客户端查看，他进行心跳机制的处理

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/5b886eaf7aa8473cbbfc2936166c3229.png)

发送心跳后，在finally里面设置延迟5秒执行，这样达到的效果就是每隔5秒执行一次心跳

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/306fd489b65041e99ca69df23cd7fcca.png)

### 2、服务端

来到InstanceController类中的beat方法。里面通过参数获取对应的实例，如果没有对应的实例则需要注册，重点我们看他是怎样处理心跳的。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/752f8bb0a6aa4238a007f3c84937faff.png)

=![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/4690c9003c8541b5b30f678fa75822d7.png)

服务端怎样处理心跳的，他启动一个线程来处理我们的心跳。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/88931a952418489e8ded3723cff90b88.png)

我们查看ClientBeanProcessor的Run方法：

这里我们重点看一下他这里是循环所有的实例，然后设置对应的时间。这里于上面我们处理检查心跳信息的处理对应上了。（可以参考我们画的图）

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/15988eeb79664c6390b32f646f1e7798.png)

## 6.5、查询服务列表

### 1、客户端

#### 1.1 找出获取服务列表的请求方法

客户端有个请求**hostReactor.getServiceInfo**获取对应的所有服务实例，

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/b0bf3da2eccb4fbea691d3b4941e3a12.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/19e3bdd637cc48d7988542d30a38ed16.png)

这里启动一个定时任务来定时获取数据，后面们研究Updatetask()的时候我们仔细研究

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/3de27a0128ac4ebca7e586649f846ed2.png)

#### 1.2 分析堆栈信息

这说明我们spring容器刷新容器的时候来获取对应的服务列表

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/5546ae8685c24d749b5d18c79efa445d.png)

#### 1.3 获取服服务列表

从注册中心中获取对应的数据

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/4b9f2125b1fa43da8e1688b1b65c2373.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/709b73e05f1442a79bd49a7c2a374b04.png)

#### 1.4 定时任务获取对应的数据

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/dfc595a186bb4fba9bfdd802dd086a0f.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/e4b5953020104c9e9a583eb85e341b04.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/5128aae9e6a44f46ae786c5610960838.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/56e944b4e7a14a13a45a6149b87b8321.png)

### 2、服务端

发送请求后调用doSrvIpxt这个核心代码，前面的操作都是一些参数的解析。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/b50001c7623e457790c6afc5fc7a4742.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/026175f771134b9590a897091961139d.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/cb73679fc7fb4578bf58415566f9247a.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/0d7787d64fb84275b5061ee7127f9761.png)

返回的一些实例列表，ephemeralInstances 临时实例列表  persistentInstances持久化临时列表

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/f76e3a2e656048c7a7b89fce6131e261.png)

# 七、集群搭建

## 1、解压Nacos

解压Nacos，复制三份

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/164477700a5f4eedb5ae6d26c809f72a.png)

## 2、配置数据库

具体位置在nacos目录下的conf中，这里的操作和之前是一样的，我们可以直接打开这个文件然后拷贝到数据库中执行，当然也是要创建数据库使用数据库然后在复制脚本内容，执行即可。

```mysql
create database nacos_config;
use nacos_config;
```

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/75438064394a4a81a5b336b80658e149.png)

## 3、修改application.properties配置文件

修改每个服务数据库链接

```properties
spring.datasource.platform=mysql

db.num=1
db.url.0=jdbc:mysql://127.0.0.1:3306/nacos_config?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&serverTimezone=UTC
db.user=root
db.password=123456
```

修改每个服务端口

```
server.port=8848
```

## 4、修改集群配置文件

将cluster.conf.example 改为 cluster.conf，并添加对应服务集群Ip:port,

其他文件夹也要修改

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/76d3090e3651495cb40e18bfb62377fa.png)

## 5、集群启动

```shell
D:\toolsDev\springalibaba\nacos cluster\nacos-8848\bin\startup.cmd -m cluster
D:\toolsDev\springalibaba\nacos cluster\nacos-8868\bin\startup.cmd -m cluster
D:\toolsDev\springalibaba\nacos cluster\nacos-8888\bin\startup.cmd -m cluster
```

## 6、浏览器访问一下Nacos

## 7、安装Nginx

修改nginx.conf

```java
worker_processes  1;

events {
    worker_connections  1024;
}

stream {
      upstream nacos {
        server 192.168.1.11:8848;
        server 192.168.1.11:8868;
        server 192.168.1.11:8888;
      }


     server {
        listen  81;
        proxy_pass nacos;
     }
}
```

启动nginx

```shell
start nginx.exe
```

重新加载

```shell
nginx.exe -s reload
```

## 8、进行访问

http://localhost:8848/nacos

## 9、项目链接

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660380661024/625ca1aabd514674a0addb730f84bf62.png)
