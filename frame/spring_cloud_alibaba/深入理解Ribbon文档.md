## 1、负载均衡的两种方式

- 服务器端负载均衡

  传统的方式前端发送请求会到我们的的nginx上去，nginx作为反向代理，然后路由给后端的服务器，由于负载均衡算法是nginx提供的，而nginx是部署到服务器端的，所以这种方式又被称为服务器端负载均衡。

  ![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/dc81b37551a24364b179126ea2227cee.png)

- 客户端侧负载均衡

现在有三个实例，内容中心可以通过discoveryClient 获取到用户中心的实例信息，如果我们再订单中心写一个负载均衡  的规则计算请求那个实例，交给restTemplate进行请求，这样也可以实现负载均衡，这个算法里面，负载均衡是有订单中心提供的，而订单中心相对于用户中心是一个客户端，所以这种方式又称为客户端负负载均衡。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/e378273029ef4931967dcf528a8ade10.png)

## 2、手写一个客户端侧负载均衡器

◆随机选择实例

```java
@Autowired
private DiscoveryClient discoveryClient;
@GetMapping("/order/create")
public String createOrder(Integer productId,Integer userId){
    List<ServiceInstance> instances = discoveryClient.getInstances("msb-stock");
    List<String> targetUrls = instances.stream()
        // 数据变换
        .map(instance -> instance.getUri().toString() + "/stock/reduce")
        .collect(Collectors.toList());
    int i = ThreadLocalRandom.current().nextInt(targetUrls.size());
    String targetUrl = targetUrls.get(i);
    log.info("请求求目标地址：{}",targetUrl);
    String result = restTemplate.getForObject(targetUrl +"/"+ productId, String.class);
    log.info("进行减库存:{}",result);
    return "下单成功";
}
```

## 3、使用Ribbon实现负载均衡

Ribbon是什么?
Netflix开源的客户端侧负载均衡器

更加直观说就是ribbon就是简化我们这段代码的小组件，不过他比我们的代码要强大一些，他给他们提供了丰富的负载均衡算法。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/0101314c4f4648afaad2af46bb44e213.jpg)

引入ribbon :三步骤： 加依赖，启动注解，写配置

不需要加，nacosdiscovery，已经给添加了依赖，

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/08e8e41cae584691bd8f1b22eec9fee0.png)

写注解，需要写到RestTemplate上面。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/fddeaec92a1143899ed62b27e5d9163f.png)

第三步：写配置

没有配置。

改造我们的请求：

url:改为 下面 当请求发送的发送的时候ribbon会将msb-stock进行转化为我们nacos里面中的地址。并且进行负载均衡算法，进行请求，

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/b2ef91dca69241f59cc8cbbc96d12180.png)

## 4、Ribbon的重要接口 以及内置负载均衡规则

### 4.1、Ribbon重要接口

| 接口                    | 作用                       | 默认值                                                       |
| ----------------------- | -------------------------- | ------------------------------------------------------------ |
| IClientConfig           | 读取配置                   | DefaultclientConfigImpl                                      |
| IRule                   | 负载均衡规则，选择实例     | ZoneAvoidanceRule                                            |
| IPing                   | 筛选掉ping不通的实例       | 默认采用DummyPing实现，该检查策略是一个特殊的实现，&#x3c;br />实际上它并不会检查实例是否可用，而是始终返回true，默认认为所&#x3c;br />有服务实例都是可用的. |
| ServerList&#x3c;Server> | 交给Ribbon的实例列表       | **Ribbon**: ConfigurationBasedServerList&#x3c;br /> **Spring Cloud Alibaba**: NacosServerList |
| ServerListFilter        | 过滤掉不符合条件的实例     | ZonePreferenceServerListFilter                               |
| ILoadBalancer           | Ribbon的入口               | ZoneAwareLoadBalancer                                        |
| ServerListUpdater       | 更新交给Ribbon的List的策略 | PollingServerListUpdater                                     |

### 4.2、Ribbon负载均衡规则

| 规则名称                 | 特点                                                         |
| ------------------------ | ------------------------------------------------------------ |
| RandomRule               | 随机选择一个Server                                           |
|                          |                                                              |
| RetryRule                | 对选定的负责均衡策略机上充值机制，在一个配置时间段内当选择Server不成功，则一直尝试使用subRule的方式选择一个可用的Server |
| RoundRobinRule           | 轮询选择，轮询index，选择index对应位置Server                 |
| WeightedResponseTimeRule | 根据相应时间加权，相应时间越长，权重越小，被选中的可能性越低 |
| ZoneAvoidanceRule        | （默认是这个）该策略能够在多区域环境下选出最佳区域的实例进行访问。在没有Zone的环境下，类似于轮询（RoundRobinRule） |

## 5、Ribbon的配置

### 5.1 类配置方式

```java
public class RibbonConfiguration {
    @Bean
    public IRule ribbonRule(){
        //随机选择
        return new RandomRule();
    }
}
```

```java
/**
* 指定配置
**/
@Configuration
@RibbonClient(name = "msb-stock",configuration = RibbonConfiguration.class)
public class UserRibbonConfiguration {
}
```

### 5.2 属性配置

将前面的配置注释掉：

如下进行配置：

```java
msb-stock:
  ribbon:
    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule
```

### 5.3 优先级高低

java配置的要高， 我们可以类配置的路由规则为随机（RandomRule），然后属性配置为轮训（RoundRobinRule）;

测试是随机则java配置高于属性配置

### 5.4 全局配置

@RibbonClients(defaultConfiguration=xxx.class)

就是将RibbonClient改为RibbonClients  将configuration改为defaultConfiguration

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/d036a2e9c84d4371a17c2e01aeded70d.png)

## 6、饥饿加载

ribbon默认是懒加载的，只有第一层调用的时候才会生成userCenter的ribbonClient，这就会导致首次调用的会很慢的问题。

```yaml
ribbon:
  eager-load:
    enabled: true
      clients: msb-stock
```

# 

# 8、源码分析

## 1、猜测源码的实现

我们在看源码的时候我们可以根据功能先想一下，他是怎样实现的，如果让我们来实现我们会怎么做，我们想ribbon不过就是替换nx-stock，为ip+端口我们会怎样做，大家想一下 ？  是不是我们可以增加加一个拦截器， 如下，你这样有这样一个思维再去看源码就应该容易一点：我们RestTemplate有一个扩展点是

ClientHttpRequestInterceptor 我们Ribbon通过LoadBalancerInterceptor实现了这个扩展早点，将nx-stock替换为 192.168.0.3:8003

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/91b6468f682c429288e08628ea7d0f7a.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/3e2f6a5d34444086ab9d80a83b7830d8.png)

## 2、初始化的过程

我们首先进入我们的注解@LoadBalanced，我们学spring源码的时候一般是注解中增加一个@Import,引入一个对象此时他没有，所以我们想它是和springboot整合的，所以我们可以找到同包下的spring.factories中，看一下，自动装配类。

我们先进入spring-cloud-starter-netflix-ribbon.2.2.6

.Release 里面乜有对应spring.factories，他是空的，这和我们讲springboot时候说mybatis一样，starter是空的但是他能引用一些自动配置的jar,我们进去看

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/85a057d811d148d7b3c188d324083e80.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/17ba99da87174cb5a13337e119de87ee.png)

导入了RibbonAutoConfiguration

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/333f298c4b7b43969f8bf0d1a9edea29.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/50dbdf85af9a4d069526cd92a311fa1b.png)

我们可以全文搜索一下哪里加载了LoadBalancerAutoConfiguration，

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/cefb4bb1ad544809805c7d515cd70832.png)

这样应该和我们的LoadBalanced注解有关

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/0289e4c818cf4ece99c470c2c303af9d.png)

AsyncLoadBalancerAutoConfiguration和LoadBalancerAutoConfiguration应该和我们对应的注解有关系，那么我们想Async是应该和异步有关系应该是更高级的作用，所以我们进入LoadBalancerAutoConfiguration这个类。 我们进入配置类中发现

好这里面就应该是我们找的类，这里应该是获取容器中所有标注@LoadBalanced注解的所有类。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/4b7de65581604ae09b83b765c3d0f27a.png)

我们进入LoadBalancerAutoConfiguration 里面初始化一些对象，

我们这里初始化一个对象是LoadBalanceInterceptor，这就是一个拦截器，然后后面是一个RestTemplateCustomizer，我们从名字可以看出他就是一个自定义的RestTemplate，我们可以看一下里面内容就有一个customize方法，我们这里用哪个lambda表达式来处理，就是穿进去一个restTemplate然后给里面增加一个拦截器。这个拦截器里面就是上面弄的LoadBalancerInterceptor。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/811e03983e5f46e6a21fbdfd5c9ea066.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/c4d52b16dfcb4d2fb330571be423a98c.png)

接着还有一个对象就是：SmartInitializingSingleton

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/0b5eeb42004141a29dad4110099873b3.png)

这一部分就是给我们的restTemplate增加了拦截器。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/e3443e0dba1a4685a35a04f6aa56d1a4.png)

接下来我们就可以进入拦截器查看一下，进入拦截器是不是就查看intercept,写过拦截器一个知道他最重要的方法就是intercept

## 3、负载均衡的过程

request.getURI()， 这个不用我多解释了吧，restTemplate就是发送http请求，这里获取他的请求链接，然后获取他的host，他的host是什么就是nx-stock，就是serviceName，然后将这serviceName传递到我们的execute里面，我们推想这里很可能对我们serviceName进行解析，从我们的nacos注册中心获取注册列表，然后通过负均衡选择一个合适的地址，进行调用。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/2924644b86c34b639b2c3b2ec60b9cc0.png)

这里我么可以一个看到第一个应该是获取负载均衡器，第一个是根据负载均衡器来获取对应的一个服务

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/8df60ae89b00498d81d26a95a0297667.png)

那我们简单看一下怎样获取负载均衡器：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/83455a15034b446d900d8bfc457de966.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/ff1b67236a914d7597372b26f0002657.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/a13290dbff9c4205895c981db46f6d71.png)

AnnotationConfigApplicationContext 就是我们注解配置的上下文，我们则是从容器中获取对应的对象，ILoadBalancer对应的负载均衡器。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/772e6f82acae4d22ae3d415a9589e4af.png)

我们可以看出是从一个Map里获得，如果没有我们需要创建他createContext

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/fbcca9bcf074464b9932378922ac2978.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/95590468428a4a8c83ca36a5fe864388.png)

获取配置然后注册刷新容器，这里和我们的spring容器一样。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/c801ccc1aed4430d9ea22c45a5ad822d.png)

所以我们一定有一个地方是创建这个对象，然后注入容器的，那一定是一个配置类，其实他是在**RibbonClientConfiguration**这个类中。在这里其实就是我们对应ribbon可配置类的默认配置是在这里配置，看这里每个注入类对应的注解@ConditionalOnMissingBean，从这里我们能知道我们配置了我们自己的类就用我们自己的类，如果没有配置我们自己的类，就会用到默认的配置类。

从这里我们能验证一个事情，@ConditionOnMissingBean中是查看容器中是否有对应的ILoadBalancer如果有则使用，如果没有则调用这个方法，然后我们看一下这个方法propertiesFactory是查看property中是否设置了我们的配置，如果有则获取到，没有则是获取默认的，

所以这里证明一个前面的结论： java配置高于属性配置

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/d29032aa6f4a43bf914f2631fc04d23c.png)

好，看到这里就可以，当然我们也可以查看一下这个负载均衡器，但里面对应的内容很复杂，我们知道我们获取一个负载均衡器就可以了，后面可以不看。等我们后面用的时候再看。

好我们回到刚才的位置：

通过这个名称getServer我们就应该知道，这个应该是通过负载均衡器中的算法获取对应一个服务

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/7972609685fb4369bf01645e3f80670c.png)

这里是调用这个负载均衡器的chooseServer，通过名称我们就知道这里是选择一个服务，这里肯定是从nacos中获取对应的服务列表，然后选择一个进行调用。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/2001f7cbf46442828869f2cc98d8ddf1.png)

在这里我们可以看到我们应该调用ZoneAvoidanceRule

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/3006514c52d14b708ad105ed5bdf7ed6.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/2f9ac62a3a7b45cba1bf437de677541e.png)

进入后我们看到一个关键方法就是role.choose，这里面我们发现他有很多实现，刚才我们说过RibbonClientConfiguration初始化了我们一些默认的对应的类，

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/55d4959717de48e5bf5884a1bd15c250.png)

我们可以发现这里创建了一个默认的规则ZoneAvoidanceRule，所以就会调用他方法，同时我们也要看一下他的集成关系，因为我们调用的方法可能是他的父类中的方法，

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/20227ba3d3024c97baaac0bac2ae3320.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/edb2a576cb6d44589a2a1e336515b244.png)

这里没有对应的ZoneAvoidanceRule 但是有PredicateBasedRule，所以会调用这个方法。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/4de2b92470854e339574a80c590ab5a2.png)

首先获取一个负载均衡器，然后这里chooseRoundRobinAfterFiltering 从这个方法我们就知道，这里使用轮训方法，ZoneAvoidanceRule如果没有设置时钟就会才用轮训算法，接着这里通过负载均衡器获取对应所有的server,我们可以推算这里是应该是从nacos中获取对应的服务列表，当然我们先不考虑他是怎样获取的，我们先知道他这里获取对应的服务列表就可以。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/1e3ebc65c33c48ce85bb5df93d5f5b17.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/666f7d266a324804a4537aa7269519dc.png)

下面就轮训机制获取对应有效的服务，首先看一下 nextIndex他是AtomicInteger类型，我们首先获取对应的值，然后加一求余，得到next值，然后

nextIndex.compareAndSet方法，判断是否是current这个值，如果是则返回，并且将next值设置进去，方便下一次的获取，这就是轮训机制，大家能不能明白，

好，那我们看这里用掉了cas方式，这样大大提高了他的性能，如果不用cas的话就需要用到lock，这样性能就会降低，当然如果设置返回false,他还会进入下一次循环处理是吧， 这就是并发编程中的应用，我们可能在工作中做业务用不到，但是你写一些中间件或者上大厂这些就用到的很多了，所以并发编程的基本功一定要搞好。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/c30e8b9e14f642689fd00e6ead22ef50.png)

好，负载均衡我们说完了，我们看一下我们的server是怎么样获取的。

## 4、获取服务列表

我们要从我们的负载均衡器中看起，因为我们前面就是从负载均衡器中获取对应的server列表

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/b80f49e58a8a4c47b162f5eba91090f8.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/6de4799b71f644bdab5dfc4b8ddbcc5f.png)

我们可以进入我们的配置类中RibbonClientConfiguration中查看对应的创建。从这里构造方法我们可以看到对应的serverList，所以说他是在创建构造方法的时候就已经获取到对应的服务列表，好我们看他的服务列表是怎么获取的。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/f9c823cd9ed84eec9919f6f16aa9e4b5.png)

好，我们来全文搜索一下	， 这里是从配置文件中获取对应的配置server,因为我们的ribbon可以独立使用的，所以我们这里获取的serverlist应该是空的。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/97ad17c727544b50a460fb4bff973585.png)

好，这里我们进入负载均衡器的构造方法里面。

这里面有个restofInit方法，好这里的init方法我们可以进去看看，看到这个init或者start方式都是重要方法，我们可以进去看一下

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/420906cf6c3a4e6da93f17891fea9bba.png)

看这个方法，我们可以翻译一下 这里 了开启并初始化学习新服务的特点， 这是什么意思我们可以看一下

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/2f18c7f0a7f446578db5834118b266a4.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/853cad79baa44c03a4e053fff8eb4714.png)

他们最后会调用这个updateListOfServers方法，这个是重点后面我们会看到。

=![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/a8e8d1e87fba446281211ca1fc8761ee.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/835ec7cc10844617b83f1a0cec4d50a1.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/5c884f9e91224de1861417f44492ff8c.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/29f4cec1c56b4aa1b8202689e349e9a0.png)

获取实例

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/534e06e1d2644461ba1b6a15f58b05f5.png)

这里就和nacos中获取数据

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/96a32caaa7864e1d93c8171f62e13939.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/cdacfc7e9a8443aa994de6d7345f28d9.png)

## 5、更新服务列表

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/642cb994c53048aba24958136e8ebb06.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/797cc4bfb03c4fe7a0012680347e84db.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/16520b08e06d4c1e95252092256f61d7.png)

进行服务赋值，后面就可以使用了

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/2dba436c13a74986baca6b286ad6a756.png)

## 6、重构请求的URL

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/8bc1166bdb634301acc61855226b7161.png)

此时我们需要debug进入这个容器中：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/bad90f4c98ea46b1b2f5eb275355de88.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/fc64746261694f34be60d801d3892042.png)

路跟下来发现在⼀个匿名内部类中，发现了很可疑的地点：ServiceRequestWrapper，服务请求的⼀个

包装类，难不成在这⾥重构请求，有点接近了，进去看下：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/914aed7d9ba6442581d463e9f73ad9c4.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/8c63ac8b108647ccade5bada086e7d26.png)

debug进入就会发现里面对host的替换

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1660734141024/ffba42fefa3b44d8a9899f6104f139b3.png)
