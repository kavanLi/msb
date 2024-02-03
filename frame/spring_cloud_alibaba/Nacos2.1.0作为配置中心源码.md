

# 四、Nacos作为配置中心源码分析

## 1、什么是Naocs配置中心

官方文档： [https://github.com/alibaba/spring-cloud-alibaba/wiki/Nacos-config](https://github.com/alibaba/spring-cloud-alibaba/wiki/Nacos-config)

Nacos 提供用于存储配置和其他元数据的 key/value 存储，为分布式系统中的外部化配置提供服务器端和客户端支持。使用 Spring Cloud Alibaba Nacos Config，您可以在 Nacos Server 集中管理你 Spring Cloud 应用的外部属性配置。

## 2、Nacos的使用

### 2.1 给Nacos2.1.0配置数据库

倒入数据

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/7b73603d4f4f4972bc8ebf1d2b69bae2.png)

修改内容

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/11fa6b963f3f4543bc43b1fe5b93c8f5.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/a2ce3b60bd1f451f87ae66e2691d329d.png)

### 2.2  版本推荐

https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/71b3c3f4b18a4b40b7d7ce8d44b72dec.png)

### 2.3 父工程指定版本

```
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.3.2.RELEASE</version>
</parent>
<properties>
    <maven.compiler.source>8</maven.compiler.source>
    <maven.compiler.target>8</maven.compiler.target>
    <spring-cloud-alibaba.version>2.2.9.RELEASE</spring-cloud-alibaba.version>
</properties>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-alibaba-dependencies</artifactId>
            <version>${spring-cloud-alibaba.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### 2.4 子工程引入依赖

```
 <dependencies>
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <!--  单元测试类 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

    </dependencies>
```

### 2.5 利用接口对配置进行操作

```

public class ConfigListenerTest {
    private static String serverAddr = "localhost";
    private static String dataId = "nacos-demo.yaml";
    private static String group = "DEFAULT_GROUP";
    private static ConfigService configService;

    @Test
    public void testListener() throws NacosException, InterruptedException {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        //获取配置服务
        configService = NacosFactory.createConfigService(properties);;
        //获取配置
        String content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);
        //注册监听器
        CountDownLatch countDownLatch = new CountDownLatch(5);
        configService.addListener(dataId, group, new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("配置发生变化:" + configInfo);
                countDownLatch.countDown();
            }

        });
        countDownLatch.await();
    }
    @Test
    public void publishConfig() throws NacosException {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        //获取配置服务
        configService = NacosFactory.createConfigService(properties);
        configService.publishConfig(dataId,group,"age: 30", ConfigType.PROPERTIES.getType());
    }


    @Test
    public void removeConfig() throws NacosException, InterruptedException {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        //获取配置服务
        configService = NacosFactory.createConfigService(properties);
        boolean isRemoveOk = configService.removeConfig(dataId, group);
        System.out.println(isRemoveOk);
    }

}

```

### 2.6 和Springboot整合

```
@RefreshScope
@RestController
public class NacosConfigController {

    @Value("${name}")
    private String name;

    @RequestMapping("/getName")
    public String getName(HttpServletRequest httpRequest){
        return name;
    }
}

```

### 2.7  里面放置定时任务

启动

```
@EnableScheduling
@SpringBootApplication
public class NacosConfigApplication {
    public static void main(String[] args)  {
         SpringApplication.run(NacosConfigApplication.class);
    }
}
```

增加定时任务

```
@RestController
public class NacosConfigController {

    @Value("${name}")
    private String name;

    @RequestMapping("/getName")
    public String getName(HttpServletRequest httpRequest){
        return name;
    }
    // 定时任务每5秒执行一次
    @Scheduled(cron = "*/2 * * * * ?")
    public void execute(){
        System.out.println("获取姓名：" + name);
    }


}

```

发现问题：如果我们更改配置，则对应的定时任务就失败

### 2.8 分析失败原因

#### 2.8.1 Schedule执行原理

当我们更改配置的时候，我们看日志会进行容器的刷新

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/6682a35bcb5c4f409ae2cfc2a6444a64.png)

此时容器中并没有对应的NacosConfigController对应的实例对象。所以我们的定时任务不会执行，我们可以调用一下我们controller对应的方法，然后容器中就有了NacosConfigController对应的实例，有了这个实例，我们的定时任务就会执行，因为这个定时任务是基于后置处理器进行执行的。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/30e0e523f678442bb8bf63281164413a.png)

#### 2.8.2 @RefreshScope对象被清理的原因

那为什么刷新容器后NacosConfigController这Bean都没有了呢？

因为在我们更新配置后，我们的容器会将@RefreshScope标注的对象清掉，好我们来分析一下

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/7399d98a20544789b7dbe1e8ad0fd46b.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/60d2a066ac4e4d3dbe0e352b85b29d3a.png)

Scope对应的有一个接口

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/ca6c9d17045c4b3f8eed72ba75685322.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/d173b37729f041529b20ba43f2e7249f.png)

```

```

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/d54c1c3c553a40248259705e0e4dbca7.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/e105bea3e0144e86be375f0a25a3c1cb.png)

真正实例创建 除了单例、多例、其他

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/5d23b5b790ae488aa1e3008370de304a.png)

我们分析这里从缓存中获取，

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/aa89bb4bb5a24baf89183da80019ca8b.png)

我们知道 单例获取是从单例对象池中，原型是重新构建Bean ,而我们Refresh是从BeanLifecycleWraperCache里面

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/c8f2dd7a356841ec94952fbbdf264858.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/bd00a539c5bb459b88743fecf3b14d71.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/be581d2c751d4d30b4bde88a840ec892.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/d4527f02753b4f009c7ff6de99877744.png)

也就是从缓存中获取对象，同时这里有个destroy

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/819053e658b746ba9ee430a0a304fe11.png)

总结一下：

1、@RefreshScope中有个@Scope里面值是Refresh,他创建对象是放到对应的缓存中，我们通过GenericScope#get方法从缓存中获取对应Bean对象

2、我们更新数据的时候，会发送一个RefreshEvent事件，容器会监听这个事件，然后将缓存中数据进行删除

3、而我们定时任务是在创建bean的后置处理器中执行的，此时bean都被清理了，所以定时任务也没有了

4、我们再次访问对应的NacosConfigController的时候，我们就会创建对应的对象放到缓存，此时定时任务也就执行了

解决问题方案：

我们缓存删除是监听RefreshEvent事件而处理的，我们现在也可以监听事件进行处理，监听事件，如果事件发生，它回调用对应监听器，然后就会实例化，这样定时任务也可以执行

```
@Slf4j
@RefreshScope
@RestController
public class NacosConfigController implements ApplicationListener<RefreshScopeRefreshedEvent> {

    @Value("${name}")
    private String name;

    @RequestMapping("/getName")
    public String getName(HttpServletRequest httpRequest){
        return name;
    }
    // 定时任务每5秒执行一次
    @Scheduled(cron = "*/2 * * * * ?")
    public void execute(){
        System.out.println("获取姓名：" + name);
    }


    @Override
    public void onApplicationEvent(RefreshScopeRefreshedEvent event) {
        log.info("监听刷新容器事件");
    }
}

```

## 3、源码分析

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/c0f4a18872d1495499c34f84c3a8f109.png)

### 3.1 服务启动加载bootstrap.propertis

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/fe82b99f7ff84d50bb76b17dbc752903.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/ef0054527c994102a82cba9f367320fb.png)

准备环境加载bootstrap.propertis，这里他会发送事件进行监听，我们可以直接进入	#load我们可以在load打上断点

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/a298fb0436234b7da9d37f500cad598f.png)

总结：我们可以根据堆栈信息来发现，它是在准备环境的时候发送一个事件，ConfigFileApplicationListener监听事件，最后调用PropertiesPropertySourceLoader 对资源进行加载

### 3.2 客户端拉取远程配置进行合并

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/ed0161730ff648d08cfd5529f2e4bbbf.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/1f1853e0884d49f7a165ab2c3462bae4.png)

这里最终会调用用到NacosPropertySourceLocator#  ，我们在locate上打上断点可以看一下堆栈信息

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/384d946df46647fab02089df6126ef08.png)

好，我们看一下locate方法

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/e864dc4b4a7c44919edd637a23dfd8b0.png)

记载配置文件后最终会用composite进行合并，那他们无论加载共享配置、扩展配置和当前应用配置最终会调到NacosPropertySourceLocator#loadNacosDatalfPresent

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/596fe539af5f4279b2b86d11a87ce9f4.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/a457e4f1ad0b4bbe884aa30036cbfcc8.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/9325e81bd43b4173a849082e7af6ca14.png)![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/c3f64f9672a94f6c85992665f13346f8.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/0dc5a76030444680a8f676deff2ee4a8.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/088bc1e5aebf457c97846120428b079e.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/5d2766cea280499f8a36b44bdf739f40.png)

重点我们看一下他的配置加载

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/bd4e2e0a1b3b46638d8f435446853514.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/36c124ab3bac48828c0a4229cffd44cf.png)

key1:从本地获取配置

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/db8b612e5a71414f98e7acceba4e458e.png)

key2:远程获取配置

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/51f79f57c5d84a549389ba0d8844ec72.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/2018a5c164794840b9b4cc18ebb1312f.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/b5a980095d5d49c5a6fa086c2249aab5.png)

总结：这里我们注意Spring的扩展点之一:在我们Bean构建之前加载一些数据，比如配置属性，我们就可以用这个扩展点，这里加载配置中心内容，这个内容用于后面bean对象创建。

例如：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/70f489aef9a445dd895ec8157ba28aa5.png)

进行配置

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/006d4cd787aa48ef9f7368c40843a33b.png)

### 3.3 服务端处理配置拉取

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/ebea61b6888746ceab2bbb1facd1a9f9.png)

客户端请求为ConfigQueryRequest，则从服务端进行搜索找到对应处理类

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/d1cc56dcffb74a7b833bb348f0155683.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/d300caea4cf2492893978b49d7462dc4.png)

下面对应的方法比较长，我们可以看一下关键的点

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/25104b534f6b4d5bb86f15e9a7683af3.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/58e57227695c496cb71813c48be66259.png)

总结：分析我们发送请求是从缓存文件中获取到的，这里带出两个问题，1、因为我们是从缓存中获取的，那我们直接修改数据库应该是不起作用的   2、缓存一定是从数据库中获取的，那什么时候设置进去的呢

### 3.4 服务启动进行数据库数据加载

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/e32f5ab8699d4971bc5c894bf5c4f120.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/3c244073b99247338fbdffa3a9d8b152.png)

服务端启动时就会依赖DumpService的init方法，从数据库中load 配置存储在本地磁盘上，并将一些重要的元信息例如MD5值缓存在内存中。服务端会根据心跳文件中保存的最后一次心跳时间，来判断到底是从数据库dump全量配置数据还是部分增量配置数据(如果机器上次心跳间隔是6h以内的话)。

全量dump当然先清空磁盘缓存，然后根据主键ID每次捞取一千条配置刷进磁盘和内存。增量dump就是捞取最近六小时的新增配置(包括更新的和删除的)，先按照这批数据刷新一遍内存和文件，再根据内存里所有的数据全量去比对一遍数据库，如果有改变的再同步一次，相比于全量dump的话会减少一定的数据库IO和磁盘I0次数。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/6d2cf38914074da0b06a53a46b92ed51.png)

构建bean时候一定初始化@PostConstruct 对应的方法

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/81a0749651994f43b2b567fd822de674.png)

判断全量和增量获取数据

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/267471c63ffb425880cfb3f86d288279.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/cea77ebace75435b85750651a2eb064b.png)

全量拉取

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/0547847b4bcc4655998d5164169e9214.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/7a51073bb3534a70a0fd13917c8d2ddb.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/a68e062228e04aafa24f40dad128df6f.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/b4ba6f8c486c40ceb364e67af3a9a6b7.png)

总结：这里的md5值的我们学习，我们md5算法获取对应文件的值，如果这个值变化说明文件发生了变化，利用这中方式我们可以通过文件md5值来判断其他是否有变化

### 3.5 加载数据发生变更，发送事件

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/5919423e085341b587fe85778e28560a.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/81fbd84468134f12b93956b57e2807ca.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/99952b33ee634fbb97f048dbc645224c.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/232b2fdc2dfa41259b98e1706703c0a1.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/05ad8d5c73c44ab3ab74ff0ac056088d.png)

如果队列写满则如下操作：但是我们上面是服务启动暂时没有新的服务进来，所以这里subscriber是空的也就无法调用

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/f7639306616a40fb87d782347579d84b.png)

如果正常情况下应该是放到队列里面，那就应该有取的地方,全文搜索对应的queue

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/93f4420dcd824d81a01100daf4239f39.png)

哪里调用到这里呢？

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/fd91d1d96536465c847733d578b54d20.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/cc743c7c05e54c6593375811e219a2ae.png)

从这里我们可以知道DefaultPublisher是一个线程，所有会调用run方法

**总结：我们应该学会发布监听事件**

### 3.6 监听配置变更

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/ed0e28dddd774c0783db95dda653fd2e.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/9b007bac8ca2450e9d6d5477267a67f7.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/e7e91a7be76b46328ebac131559c4152.png)

全文搜索ApplicationReadyEvent，查看NacosContextRefresher

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/6a269c1b889449c3a596cea17fb0fd3e.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/db9833f35ecb40aeb3e54dfb704c2a60.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/132881ed705c406587c47fec848345f1.png)

发送RefreshEvent事件后，就是对@RefreshScope标志的实例进行删除，这里可以参考**2.7分析失败原因**

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/633ef46fb65445699b19b0478da14977.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/9acdea99b10244ebbda51c4d6cbc3c38.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/aa115010d0cc48419efc50a49f89d5dc.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/ebb0d70e3ab24dbdb392d40f4f28dbe9.png)

key1:销毁对应实例

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/bd296f2d9cad4ca19d6698d734b705c4.png)

key2:发送事件RefreshScopeRefreshedEvent  这也是我们通过监听这个事件，来实例化对应的实例的

### 3.7 服务端端更改配置

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/b49d9b44ad72457e88702366d13959bc.png)

我们配置中心发布配置，一定是调用SpringMVC中的Controller方法 ，我们进行全文搜索，通过名称进行分析应该是ConfigController

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/f9ead1443b9b4189a9bf8897d27e39b1.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/3d521cce60724979b3e122b38486ee62.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/55480b65558248d79037cbfea686ef36.png)

key1: 更新数据库

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/0c2003665cea47b4807b301f33ed5fdf.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/c0f79a826b114888befc0ef1a23b7f47.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/7f173c61ce134b4c96ea8e8e9d624ff6.png)

key2:发布事件，进行客户端通知配置变更，以及集群同步

全文搜索ConfigDataChangeEvent

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/cbbd5720c6554311a039b206cb264f11.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/9e76d114e50740d2aefbf652e17ba2e9.png)

AsyncRpcTask是一个任务，并向里面传递了rpcQuene的一个任务队列，我看一下他是怎样处理的。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/4c6aaa502c174bb39449347aeeca3656.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/ef386c937c564dc89cd158fba3f2e853.png)

key1:我们看一下数据持久化， key2中集群数据同步就是发送一个rpc请求

我们重点key1：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/e3db3dd2a08546569e73ce4643a5becc.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/dd192e1ae4fb45f9a67618e5842ecfee.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/efa6d0f3642e455d9f2d985fd651feba.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/7c02bf2caab9455bb0249d504ffbb9fa.png)

上面我们解释过这里是异步处理一定有个地方处理他

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/009a61444f3e416c8bc63408066bc586.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/b34d63225d9042ac83352fac0edbd6fd.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/2dd54e31a44645259831e072d42341ed.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/90341c4288594ca08c924b6c0fcd8e74.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/b18991dac31a4572aedede6a735f5801.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/278697157d784a51bce20c2b0bb77f22.png)

发布LocalDataChangeEvent事件

全文搜索对应的事件

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/5a813af521dc47ce9b9801bf7dae5ac5.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/a6d2c20949874ea5b2337bc1b27bc553.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/7d626593a9694bdfb6eb116439381dc5.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/37af0434d32a47acbbd33c5d9bec34cd.png)

查看任务RpcPushTask对应的方法：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/47f4465bbbf84bbd88fb15f2d4a6eb6d.png)

向客户端发送请求，进行配置变更的通知

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/957d734b158d4008944332dd5fc62ae5.png)

### 3.8 客户端处理事件

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/e80cc3cf9d8440e4b129c6d592e03471.png)

我们知道发送请求的是ConfigChangeNotifyRequest，好，我们到客户端全文搜索一下，找对应的处理

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/974e09f0abab4b08bed8878e84ea0ba4.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/38e47f7bfce74c92b9267503f5e8ae02.png)

我们全文搜索listenExecutebell找对应的处理位置

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/2e65061ac3f9409dab4c185d10ecab70.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/3759971f18b24608a69107972a5b00e2.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/fbffe24abbfb43f792d6e1256a963e7f.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/8cce027d73344ebe9c5cca36cb4d434e.png)

### 3.9 客户端定时拉取配置

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/a7ed759e80e649edbf3d4090ad1d332c.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/a329a5d93898476db7f5a3443b16f32a.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/db693761e2754bdcbde164bcbb02202f.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/7bface5ad4a74c8097554e6b8d8b8973.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/b2e3ffdcdb49402189a2ec30ee102cd0.png)

3.10 于Nacos1.x长轮询做对比

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1670652088093/6767a4fb44fe49c99b9d5ea4d339d3d0.png)
