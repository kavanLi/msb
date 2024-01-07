## 1、 使用Feign实现远程HTTP调用

### 1.1、常见HTTP客户端

- **HttpClient**

  HttpClient 是 Apache Jakarta Common 下的子项目，用来提供高效的、最新的、功能丰富的支持 Http 协 议的客户端编程工具包，并且它支持 HTTP 协议最新版本和建议。HttpClient 相比传统 JDK 自带的 URLConnection，提升了易用性和灵活性，使客户端发送 HTTP 请求变得容易，提高了开发的效率。
- **Okhttp**

  一个处理网络请求的开源项目，是安卓端最火的轻量级框架，由 Square 公司贡献，用于替代 HttpUrlConnection 和 Apache HttpClient。OkHttp 拥有简洁的 API、高效的性能，并支持多种协议 （HTTP/2 和 SPDY）。
- **HttpURLConnection**

  HttpURLConnection 是 Java 的标准类，它继承自 URLConnection，可用于向指定网站发送 GET 请求、 POST 请求。HttpURLConnection 使用比较复杂，不像 HttpClient 那样容易使用。
- **RestTemplate**

  RestTemplate 是 Spring 提供的用于访问 Rest 服务的客户端，RestTemplate 提供了多种便捷访问远程 HTTP 服务的方法，能够大大提高客户端的编写效率。

### 1.2、什么是Fegin

Feign是Netflix开源的声明式HTTP客户端

### 1.3、优点

Feign可以做到使用 HTTP 请求远程服务时就像调用本地方法一样的体验，开发者完全感知不到这是远程方 ，更感知不到这是个 HTTP 请求。

【Fegin和OpenFeign的区别：Spring Cloud openfeign对Feign进行了 增强，使其支持Spring MVC注解，另外还整合了Ribbon和Eureka，从而使得Feign的使用更加方便 】

### 1.4、重构以前的代码：

三板斧：

1. 引入依赖
2. 写启动注解
3. 增加配置

   ```
   暂时没有配置
   ```
4. 进行改造

增加一个feign，他也是基于ribbon进行操作的，所以以前我们学的ribbon在这里也适用

我们在这里看一下我们启动了两个微服务，他们都发起了调用，也就feign后台实现了负载均衡。我们可以看一下他的继承关系，我们发现fegin里面有依赖了ribbon，所以他除了增强了我们springmvc注解，同时也整合我们的ribbon

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/c6aeaf281ae5445c9f973ef7f6e66284.png)

## 2、Feign的组成

| 接口               | 作用                                                         | 默认值                                                       |
| ------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| Feign.Builder      | Feign的入口                                                  | Feign.Builder                                                |
| Client             | Feign底层用什么去请求                                        | 和Ribbon配合时：LoadBalancerFeignClient&#x3c;br />不和Ribbon配合时：Feign.Client.Default |
| Contract           | 契约，注解支持                                               | SpringMVC Contract                                           |
| Encoder            | 编码器，将相应消息体转成对象                                 | SpringEncoder                                                |
| Decoder            | 解码器，用于将独享转换成HTTP请求消息体                       | ResponseEntityDecoder                                        |
| Logger             | 日志管理器                                                   | Slf4jLogger                                                  |
| RequestInterceptor | 用于为每个请求添加通用逻辑（拦截器，例子：比如想给每个请求都带上heard） | 无                                                           |

Feign.Client.Default 利用的是我们默认的HttpURLConnection，他是没有连接池的。也没有资源管理这个概念，性能不是很好，

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/ecf6801bc7934b6bb9c3e8e1b6cc8ba1.png)

## 3、细粒度配置自定义

### 3.1、代码方式-指定日志级别

feign默认是不打印任何日志的，但是我们希望打印一些日志信息。比如调用的时间。

Feign设置方式就不同的，他有四个级别如下：

| 级别          | 打印日志内容                                  |
| ------------- | --------------------------------------------- |
| NONE（默认值) | 不记录任何日志                                |
| BASIC         | 仅记录请求方法、URL、响应状态代码以及执行时间 |
| HEADERS       | 记录BASIC级别的基础上，记录请求和响应的header |
| FULL          | 记录请求和响应的header、body和元数据          |

#### 1.1 指定springboot日志

Feign日志是基于Spring boot的日志所以先设置SpringBoot日志：

```properties
logging:
  level:
    com.msb: debug
```

#### 1.2 创建配置类

BASIC使用于生产环境 FULL适用于开发环境

创建配置类UserFeignConfiguration ，注意这里并没有增加@Configuration

更改请求feign的配置文件。

#### 1.3 将配置类引入

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/c39b3a69133946489fb9a0cdf1eecbae.png)

### 3.2、属性方式-指定日志级别

feign.client.config.&#x3c;feignName>.loggerLevel:full

```properties
logging:
  level:
    com.msb: debug
feign:
  client:
    config:
      msb-stock:
        loggerLevel: full
```

## 4、全局配置自定义

### 1、代码配置

- 方式一:让父子上下文ComponentScan重叠（强烈不建议使用）

  ```java
  @Configuration
  public class StockFeignConfiguration {
      /**
       * 日志级别
       * 通过源码可以看到日志等级有 4 种，分别是：
       * NONE：不输出日志。
       * BASIC：只输出请求方法的 URL 和响应的状态码以及接口执行的时间。
       * HEADERS：将 BASIC 信息和请求头信息输出。
       * FULL：输出完整的请求信息。
       */
      @Bean
      public Logger.Level level(){
          return Logger.Level.FULL;
      }
  }
  ```
- 方式二【唯一正确的途径】:
  EnableFeignClients(defaultConfiguration=xxx.class)

  ![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/52b0da9fc29b47ecaaaf7d3255187810.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/fcc457261143470e856cd1dda7043ab7.png)

### 2、属性配置

```yaml
logging:
  level:
    com.msb: debug
feign:
  client:
    config:
      default:
        loggerLevel: full
```

## 5、 支持的配置项

### 5.1、契约配置

Spring Cloud 在 Feign 的基础上做了扩展，可以让 Feign 支持 Spring MVC 的注解来调用。原生的 Feign 是不支持 Spring MVC 注解的，如果你想在 Spring Cloud 中使用原生的注解方式来定义客户端也是 可以的，通过配置契约来改变这个配置，Spring Cloud 中默认的是 SpringMvcContract。

#### 1.1代码方式

```java
    /**
     * 修改契约配置，这里仅仅支持Feign原生注解
     * 这里是一个扩展点，如果我们想支持其他的注解，可以更改Contract的实现类。
     * @return
     */
    @Bean
    public Contract feignContract(){
        return new Contract.Default();
    }
```

注意：这里修改了契约配置之后，我们就只能用Fegin的原生注解

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/97e5093b1f8647e5a63a61fe6596a44d.png)

#### 1.2 属性方式

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/f467eb9e5c31421b854cfb0a27ae9d35.png)

### 5.2、编解码

Feign 中提供了自定义的编码解码器设置，同时也提供了多种编码器的实现，比如 Gson、Jaxb、Jackson。 我们可以用不同的编码解码器来处理数据的传输。。

扩展点：Encoder & Decoder

默认我们使用：SpringEncoder和SpringDecoder

```java
package feign.codec;
public interface Encoder {
  void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException;
}
```

```java
package feign.codec;
public interface Decoder {
  Object decode(Response response, Type type) throws IOException, DecodeException, FeignException;
}
```

#### 2.1 代码方式

```java
@Bean
public Decoder decoder(){
    return new CustomDecoder();
}
@Bean
public Encoder encoder(){
    return new CustomEncoder();
}
```

#### 2.2 属性方式

```yaml
feign:
  client:
    config:
      #想要调用的微服务的名称
      msb-user:
        encoder: com.xxx.CustomDecoder
        decoder: com.xxx..CustomEncoder
```

### 5.3、拦截器

通常我们调用的接口都是有权限控制的，很多时候可能认证的值是通过参数去传递的，还有就是通过请求头 去传递认证信息，比如 Basic 认证方式。

#### 3.1 扩展点：

```java
package feign;

public interface RequestInterceptor {
  void apply(RequestTemplate template);
}
```

#### 3.2 使用场景

1. 统一添加 header 信息；
2. 对 body 中的信息做修改或替换；

#### 3.3 自定义逻辑

### 5.4、Client 设置

Feign 中默认使用 JDK 原生的 URLConnection 发送 HTTP 请求，我们可以集成别的组件来替换掉 URLConnection，比如 Apache HttpClient，OkHttp。

#### 4.1 扩展点

Feign发起调用真正执行逻辑：**feign.Client#execute （扩展点）**

```java
public interface Client {
  Response execute(Request request, Options options) throws IOException;
 }
```

#### 4.2 配置Apache HttpClient

1. 引入依赖

   ```pom
   <dependency>
       <groupId>io.github.openfeign</groupId>
       <artifactId>feign-httpclient</artifactId>
   </dependency>
   ```
2. 修改yml配置
3. 源码分析 FeignAutoConfiguration

   ![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/6a853f1760f244cfa2a1bdac4478ad0a.png)

此时默认增加一个ApacheHttpCient实现类

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/b222b81a4c71455687bc0f877b7a50ca.png)

#### 4.3 设置OkHttp

1. 引入依赖
2. 增加配置

)

3、源码分析 FeignAutoConfiguration

### 5.5、超时配置

通过 Options 可以配置连接超时时间和读取超时时间，Options 的第一个参数是连接的超时时间（ms）， 默认值是 10s；第二个是请求处理的超时时间（ms），默认值是 60s。

Request.Options

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/83960fc994ec4720a1e3bcf983ec0709.png)

#### 5.1 代码配置

msb-stock改造

## 6、推荐配置方式

- 尽量使用属性配置，属性方式实现不了的情况下再考虑用代码配置
- 在同一个微服务内尽量保持单一性，比如统一使用属性配置，不要两种方式混用，增加定位代码的复杂性



# 7、源码分析

## 1、 源码推演

我们在想为什么我们调用接口StockFeignClient就能调用对应的服务呢？ StockFeignClient接口代码如下：

```java
@FeignClient(name = "msb-stock")//,configuration = StockFeignConfiguration.class)
public interface StockFeignClient {
    /**
     * http://msb-stock/stock/reduce/{productId}
     * @param productId
     * @return
     */
    @GetMapping("/stock/reduce/{productId}")
    String reduce(@PathVariable Integer productId);
}
```

StockFeignClient接⼝打个@FeignClient注解，它是怎么通过接⼝上的信息、找到接⼝的实现类的呢？我们看一下StockFeignClient发现⾥⾯就是⼀些SpringMVC相关的注解信息，⽐如接⼝类和⽅法上的@RequestMapping中、标注了具体访问时的路径以及请求⽅法（GET、PUT、POST、DELETE）是怎样的、@PathVariable标注了应该在请求路径上带上什么变量名、@RequestBody表示POST请求要带上的请求参数。 还有这个@FeignClient中name属性，这些信息一定是构建一个url， 好再@ReqeustMapping中我们知道我们的路径是/stock/reduce/{productId} 并且是一个get请求，

```java
    @GetMapping("/stock/reduce/{productId}")
    String reduce(@PathVariable Integer productId);
```

那对应StockFeignClient上的注解@FeignClient注解，可以得到目标服务，也就是本次调用的服务msb-stock

```java
@FeignClient(name = "msb-stock",configuration = StockFeignConfiguration.class)
```

最终根据这些注解信息得到的请求URL信息为：/msb-stock//stock/reduce/12。

⽽⼜因为在SpringCloud体系内，发送⼀次请求都是通过HTTP协议来的，最终我们加上协议后，请求URL为: http://msb-stock//stock/reduce/45465。

分析到这⾥，我们再看下现在还缺什么：http://msb-stock/stock/reduce/45465，这个请求URL中⽬前唯⼀的疑点就在msb-stock上了，总不能就这么尴尬的写个msb-stock来发送请求吧，没有实际的ip和port怕是直接发起请求就报错了，所以肯定是需要将msb-stock解析成具体的ip和port，这样的URL才算是⼀个完整的URL、才能实际的发送有效请求出去。

并且我们是和nacos进行整合的，那么我们需要从nacos中获取所有服务对应的ip和port ，但是我们如果有多个实例那我们是不是需要利用负载均衡器来获取一个我们需要的服务，当然我们feign也整合了ribbon，所以我们底层可以使用ribbon进行负载均衡。

## 2、源码入口

源码分析的两种思路，一个是@EnableXXX 作为入口，另一个就是springboot的自动装配 xxxAutoConfiguration

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/b1bd2aa837b240ecbbfb304f57cf5ee7.png)

进⼊到EnableFeignClients注解类中，会发现有个@Import注解，这个注解前面我们经常看到，这里导⼊了⼀个⽐较特别的类：FeignClietnsRegistrar，简单翻译下就是Feign的客户端注册器，注册器？这可能是吧@FeignClient注解标记的那些接口类，进行解析然后注入的

```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({FeignClientsRegistrar.class})
public @interface EnableFeignClients {
    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    Class<?>[] defaultConfiguration() default {};

    Class<?>[] clients() default {};
}
```

## 3、扫描@FeignClient标注的类

因为他实现了ImportBeanDefinitionRegistrar 所以我们来看他的registerBeanDefinitions方法

```java
public void registerBeanDefinitions(AnnotationMetadata metadata,
      BeanDefinitionRegistry registry) {
    // 解析默认的配置类EnableFeignClients
   registerDefaultConfiguration(metadata, registry);
    // 注册用@FeignClient标注的接口  
   registerFeignClients(metadata, registry);
}
```

### 3.1 整体思路分析

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/9e082574dfb842a58bb78eb7454e320d.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/57175331462a4250a2c2d31be8d19614.png)

### 3.2 获取扫描器

registerFeignClients⽅法⼀进去我们可以看到getScanner⽅法、很明显它就是获取⼀个扫描器，在getScanner⽅法中，发现它new了⼀个ClassPathScanningCandidateComponentProvider类型的对象，⾥⾯有个⽅法isCandidateComponent，来判断是否是我们需要的

```java
protected ClassPathScanningCandidateComponentProvider getScanner() {
   return new ClassPathScanningCandidateComponentProvider(false, this.environment) {
      @Override
      protected boolean isCandidateComponent(
            AnnotatedBeanDefinition beanDefinition) {
         boolean isCandidate = false;
         // 确定基础类是否独立，即它是一个顶级类或嵌套类（静态内部类）可以独立于封闭类构造。
         if (beanDefinition.getMetadata().isIndependent()) {
             // 判断是否是注解
            if (!beanDefinition.getMetadata().isAnnotation()) {
               isCandidate = true;
            }
         }
         return isCandidate;
      }
   };
}
```

### 3.3 获取扫描包

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/8e355b5285994073a7513df4d6ca3e6e.png)

### 3.4 获取标注@FeignClient的接口并注入容器

**获取标注@FeignClient的接口**

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/6d52e32cdc674bbca18d5f11e625079b.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/7966f8dc401146399bc87eed88585638.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/1797917bd19946bb8e40e2c766f2d3e0.png)

这里的扫描我们能想起mybatis的扫描。

注入容器

这里设计的类是FeignClientFactoryBean 他是一个FactoryBean 我们获取对象是调用getObject

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/574f9876fe8c4870b65f6a1bfb176845.png)

总结：

1.以启动类上的@EnableFeignClients为⼊⼝，扫描启动类所在包路径以及该包下所有⼦包中的所有的类

2.从扫描到的类中、筛选出所有打了@FeignClient注解的类

3.解析@FeignClient注解中的属性，创建⼀个BeanDefinition并设置各种属性值，再注⼊到Spring容器中

## 4、FeignClientFactoryBean创建动态代理

由于FeignClientFactoryBean是tFactoryBean所有获取对象是从getObject中

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/d7b082d8ae8c484da2551e773d646cc9.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/0e6b76cd510f418786f276e49c5f75af.png)

### 4.1 获取组件属性

从容器中获取对应的FeignContext，

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/0389928f1c484de7bc159614d3c5b804.png)

我们进入feign（contex）

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/dd39132b75ad407f8bacd6db76a820c4.png)

这些方法都是调用的get方法，只是类型不同而已

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/2be9dace655e4d00b2bbe0b675e9261c.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/7bd14ac03eaa4c2a9fc6f2f11e4ed05b.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/0ee65c14a4e54c28b8525348a72285d2.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/92b5853b0ea74540996df8fbff3b5e5c.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/0ae421eb3ec94f2db7916ad73b5b77de.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/be5c94d3d5fc48e68d590db237776022.png)

在getObject⽅法中，⾸先从applicationContext中、也就是从Spring容器中获取了⼀个FeignContext组件，应该是Feign存放⼯具类的⼀个上下⽂组件，然后从FeignContext中获取到了FeignLoggerFactory组件，⼀路追进去发现，原来在底层也是维护了⼀个Spring容器的缓存Map&#x3c;String, AnnotationConfigApplicationContext>。Feign在执⾏时涉及到⼀系列的组件，所以Feign⼲脆为每个服务创建了⼀个Spring容器类ApplicationContext，⽤来存放每个服务各⾃所需要的组件，每个服务的这些⼯具类、组件都是互不影，所以我们看到它在底层是通过⼀个Map来缓存的，key为服务名，value为服务所对应的的spring容器ApplicationContext。

接着我们想不管是FeignContext、FeignLoggerFactory还是Encoder、Decoder、Contract，他们都是接⼝，那具体的实现类是什么呢？

因为它们都是直接从Spring的容器中取出来的，这就意味着在某个地⽅事先注⼊到了Spring容器中了，所以我们还得从⼀些地⽅、如配置类⾥⾯看下。

在SpringCloud系列组件中，设计者都⽐较喜欢把⼀些组件的初始化都放在⼀些XxxConfiguration中，然后需要使⽤到的时候通过容器直接取，但是对于我们分析⽽⾔、还是有点麻烦的，毕竟还得去找。

⾸先因为我们当前是在FeignClientFactoryBean中，所以⾸先当然是从它对应路径下开始寻找，看下有没有类似Configuration相关的类，如下图所示：

**FeignAutoConfiguration**

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/c67ffab2df264efca11c6f94e4b9f9d0.png)

**FeignClientsConfiguration**

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/b3b5f214e60a44a38f46e4c3c01faec0.png)

上面是对应get内容

下面对应configureFeign

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/5b4ecf95999946e28b50b55a0ced24d8.png)

我们看方法会发现里面就是先获取java类配置，然后再获取属性配置，属性配置会把代码配置给覆盖掉，所以我们需要属性配置优先级高于代码

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/8973f694ec2942788930d9ba3e14b1da.png)

### 4.2 创建动态代理对象

因为@FeignClient默认属性是name,所以url为空，所以第一个if条件成立；而注解中name属性值是msb-stock，所以url最后为http://msb-stock然后进⼊loadBalance⽅法中，从⽅法名称上来看、从这⾥开始要和负载均衡有点关系了。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/2cd0898bf4cf4ae38ee437ad20177104.png)

获取的Client是LoadBalanceFeignClient，对应的Targeter是HystrixTargeter

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/8d1d06b2071c4aaa87a7cd15cf80c791.png)

创建实例

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/6a2e14477e3d4f47b50ebc5108143881.png)

创建动态代理的类。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/94d1e39bcbb244d7910160286aab98ad.png)

在newInstance⽅法中，⼀开始先调⽤targetToHandlersByName获取了Map&#x3c;String, MethodHandler>，因为我们这⾥是动态代理、⽽且代理的是接⼝的⽅法，所以我们基本可以猜测到这⾥是想要为每个⽅法创建⼀个⽅法的处理器，也就是MethodHandler，但是具体怎么创建呢？

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/2961f5ca82f841ee9cb7a1fdb0460dab.png)

我们进入factory.create查看是怎样创建MethodHandler

这里重点就是我们会把我们对应的接口封装为&#x3c;font color="red">SynchronousMethodHandler&#x3c;/font>，后面调用的时候我们会进入这个方法

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/021fb4650257433791e369c4589d757c.png)

理解了前⾯的内容之后，现在就⽐较好理解了，创建动态代理、怎么代理呢，就是遍历了下⽅法，把之前的Map&#x3c;String, MethodHandler>、本来以⽅法名称为key的形式、换成了以Method为key的methodToHandler的Map&#x3c;Method, MethodHandler>。

⽅便通过Method就能从Map获取到对应的Handler，然后创建了InvocationHandler，通过JDK的API 创建⼀个JDK的动态代理并返回。

好，我们查看创建jdk动态代理InvocationHandler

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/3a03342c121c4c5b893509d8f0db7611.png)

=![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/b0aaacdef39445cea58aebeac3d7b583.png)

到这⾥我们基本上可以知道：⽐如当服务B的getById⽅法被调⽤时、会调⽤服务A的getById⽅法，⽽服务A这⾥因为是通过Feign声明式调⽤的，肯定会先通过服务A的Feign动态代理FeignInvocationHandler，从动态代理中的Map&#x3c;Method, MethodHandler>中、找到具体某个⽅法的SynchronousMethodHandler，然后执⾏该SynchronousMethodHandler中的invoke⽅法来处理⽅法的逻辑。

并且根据我们最早的猜测，SynchronousMethodHandler中的逻辑⽆⾮就是将前⾯从@FeignClient、接⼝上的@RequestMapping、@PathVariable、@RequestParams等注解中解析到的信息，拼凑成⼀个完整的HTTP请求，然后发起请求并接对应的响应信息。

### 4.3 进行调用

jdk动态代理的调用，会调用FeignInvocationHandler.invoke

从缓存中通过Method获取对应的MethodHandler （**SynchronousMethodHandler**）

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/5ac3cfaa12404935bc88acff0ba7ff49.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/3d69f3f5b2f04b9f8ec58b8cc09fa38e.png)

然后调用**SynchronousMethodHandler**

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/946cda67e8264024a42a8e8cb0d94b6a.png)

这里通过client进行执行，这里面应该有负载均衡和调用

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/fa33b055fcd24478852f0720eb4e7c92.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/2f3dd1949e87448d87e36a42f5af98e4.png)

#### 4.3.1 获取负载均衡器

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/ebc664daacd24de0a7590d3d195b42d6.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/28888fec67024fd7a6eba0bf2c6d0183.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/0c7e5bf852b647e0b4bd893fa79750de.png)

#### 4.3.2通过负载均衡器获取服务

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/211a93ab24964acfb2f7a26a46f416aa.png)

```
LoadBalancerCommand从名称可以推测这是一个负载均衡器，调用submit里面会调用selectServer方法
```

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/71075ee7c3a2412c8c8ee4955243a4fd.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/69b27d0544e4450eaf3d35cce64a78a4.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/12829217959d4761b461f2447066e575.png)

获取负载均衡器**ZoneAwareLoadBalancer**，然后根据路由规则进行调用这里就是ribbon的内容了

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/7b8e98f4c1364500ad3819c4d425506c.png)

#### 4.3.3 重构URL

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/d59cb7b330af47d98470ed6693dc1ea0.png)

可以进入看一下就是拼接字符串

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/d485cd83825e42cf990f23d2b9c4187e.png)

#### 4.3.4 发送http请求

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/5c477433be6649cc9e8d4d26d83419f8.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/33cccd85c49f4351aff45249102c7db0.png)

默认我们会调用最后HttpUrlConnection

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/bb4ff7d53f344fdf8cbfdd3735eb29b8.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660975217058/4635347275484a0abc006d2b9f782312.png)
