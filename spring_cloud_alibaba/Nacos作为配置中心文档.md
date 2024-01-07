# 一、配置中心存的意义

### 1、微服务中配置文件的问题

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/a62828ea15bc43c18dfec1f0c6acd789.png)

配置文件的问题：

- 配置文件的数量会随着服务的增加持续递增
- 单个配置文件无法区分多个运行环境
- 配置文件内容无法动态更新，需要重启服务

引入配置文件：刚才架构就会成为这样。是由配置中心统一管理

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/1225e370d7624aba864a7a0c888f2d51.png)

- 统一配置文件管理
- 提供统一标准接口，服务根据标准接口自行拉取配置
- 支持动态更新的到所有服务

### 2、业界常用的配置中心

- Appllo

  1. 统一管理不同环境、不同集群的配置
  2. 配置修改实时生效（热发布）
  3. 版本发布管理
  4. 灰度发布
  5. 权限管理、发布审核、操作审计
  6. 提供开放平台 API
- Disconf

  是百度开源的框架，他是基于zk来实现配置变更后来实时通知和生效的。
- SpringCloud Config

  他是springcloud自带的配置组件，他可以和spring进行无缝集成，spring自家研发的，使用起来很方便，配置存储是支持git ,不过他缺少可视化界面，并且配置的生效也不是实时的。需要重启，或者手动刷新的功能。
- Nacos

# 二、Nacos安装以及编译

## 1、下载源码

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/df9a78f600d54a2cadef69e5f7381aa4.png)

解压进入目录中进行maven编译

```pom
mvn clean install -DskipTests -Drat.skip=true -f pom.xml
```

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/c887d6acd41f45dc983238548bb3d9ae.png)

> 注意：编译的时候可能需要你自己指定jdk版本，可以修改maven配置文件conf/settings.xml
>
> ![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/6846c7f9c42c4061b8b749eacb0a76fa.png)
>
> ```xml
> <profile>  
>      <id>jdk-1.8</id>  
>      <activation>  
>          <activeByDefault>true</activeByDefault>  
>          <jdk>1.8</jdk>  
>      </activation>  
>      <properties>  
>          <maven.compiler.source>1.8</maven.compiler.source>  
>          <maven.compiler.target>1.8</maven.compiler.target>  
>          <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>  
>      </properties>  
>  </profile> 
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

  ![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/65a0cbbd8e24460b96359fcabce05b35.png)
- 解压进入bin目录
- 执行命令

  ```shell
  startup.cmd -m standalone
  ```

## 5、修改startup.cmd

将MODE模式改为standalone，这样下次直接双击startup.cmd就可以了

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/4a0627a4d5834846b72a8b68701ff5a2.png)

# 三、Nacos Config数据模型

Nacos Config数据模型

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/a1b0862ce32f4e23924f1f0713823fef.jpg)

数据模型最佳实践

|           |                                            |
| --------- | ------------------------------------------ |
| Namespace | 代表不同的运行环境:Dev/Test/Prod           |
| Group     | 代表某一类配置，比如中间件配置、数据库配置 |
| Datald    | 某个项目中具体的配置文                     |

# 四、Nacos集成springboot实现统一配置管理

## 1、集成过程

1. 增加依赖

   ```pom
   <dependency>
       <groupId>com.alibaba.cloud</groupId>
       <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
   </dependency>
   ```
2. 开启注解

   没有注解
3. 增加配置

   这里的配置是bootstrap.yaml(bootstrap.properties)

   bootstrap.yml（bootstrap.properties）用来程序引导时执行，应用于更加早期配置信息读取，如可以使用来配置application.yml中使用到参数等

   application.yml（application.properties) 应用程序特有配置信息，可以用来配置后续各个模块中需使用的公共参数等。

   > 加载顺序bootstrap.yml > application.yml > application-dev(prod).yml
   >

```properties
   spring.application.name=nacos-config
#nacos地址
spring.cloud.nacos.config.server-addr=localhost:8848
```

4、获取对应的属性

```java
@RestController
public class NacosConfigController {

    @Value("${name}")
    private String name;

    @RequestMapping("/getName")
    public String getName(){
        return name;
    }
}
```

此时是可以获取到数据配置中心的数据的，但是他不能动态更新，为此我们可以加注解@RefreshScope

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/1413a8e488b345e58631f9caafcf1e41.png)

## 2、各种配置加载顺序

```properties
spring.application.name=nacos-config

server.port=8081
#nacos地址
spring.cloud.nacos.config.server-addr=localhost:8848
#1、只有上面的配置的时候他默认加载文件为：${application.name}
#2、指定文件后缀名称
#加载文件为：${application.name}.${file-extension}
#nacos-config.yaml
spring.cloud.nacos.config.file-extension=yaml
##3、profile： 指定环境  文件名：${application.name}-${profile}.${file-extension}
##nacos-config-prod.yaml
spring.profiles.active=prod
#4、nacos自己提供的环境隔离 ，这里是开发环境下的
spring.cloud.nacos.config.namespace=ff02931a-6fdb-4681-ac37-2f6d9a0596f8

#5、 自定义 group 配置，这里也可以设置为数据库配置组，中间件配置组，但是一般不用，
# 配置中心淡化了组的概念，使用默认值DEFAULT_GROUP
spring.cloud.nacos.config.group=DEFAULT_GROUP
#
#6、自定义Data Id的配置 共享配置（sharedConfigs）0
spring.cloud.nacos.config.shared-configs[0].data-id= common.yaml
#可以不配置，使用默认
spring.cloud.nacos.config.shared-configs[0].group=DEFAULT_GROUP
# 这里需要设置为true，动态可以刷新，默认为false
spring.cloud.nacos.config.shared-configs[0].refresh=true

# 7、扩展配置(extensionConfigs)
# 支持一个应用有多个DataId配置，mybatis.yaml datasource.yaml
spring.cloud.nacos.config.extension-configs[0].data-id=datasource.yaml
spring.cloud.nacos.config.extension-configs[0].group=DEFAULT_GROUP
spring.cloud.nacos.config.extension-configs[0].refresh=true


#作用：顺序
#${application.name}-${profile}.${file- extension}   msb-edu-prod.yaml
#${application.name}.${file-extension}   nacos-config.yaml
#${application.name}   nacos-config
#extensionConfigs  扩展配置文件
#sharedConfigs  多个微服务公共配置 redis

```

# 五、Nacos config动态刷新实现原理解析

## 1、动态监听

- **Push表示服务端主动将数据变更信息推送给客户端**

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/a07a838c88e546e7af1c003e44b78af9.png)

```
推送的模式服务器必须保持客户端的长连接，这样服务端会耗费大量的内存，并且还要检测链接的有效性。需要一些心跳机制来维护
```

- **Pull表示客户端主动去服务端拉取数据**

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/58e6478600cf455486d4f855353f1687.png)

```
这样客户端缺少了时效性，客户端不可能实时的从服务端拉取的，他要有时间间隔的。这个时间间隔不好控制，时间长了就实时性不高，时间短了，如果配			置没有变化时候他会有需要无效的拉取。
```

## 2、动态刷新流程图（长轮询机制）

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/50762971682d466bad0ced4ec7d9acf4.png)

客户端会轮询向服务端发出一个长连接请求，这个长连接最多30s就会超时，服务端收到客户端的请求会先判断当前是否有配置更新，有则立即返回如果没有服务端会将这个请求拿住“hold”29.5s加入队列，最后0.5s再检测配置文件无论有没有更新都进行正常返回，但等待的29.5s期间有配置更新可以提前结束并返回。

## 3、长轮训机制源码分析

### 3.1 源码方式打包

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

### 3.2 入口

我们想Nacos和SpringBoot整合一定是自动装配，那么我们需要找自动装配类。如下：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/c5a44e5f6b1244bf98075bebd045814d.png)

### 3.3 NacosConfigManager源码分析

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/a2dd9192c8034b5f9de4bed424435287.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/1fcd06eb94854a5ebc8c002d663873e7.png)

这里是用一个单例的方式来处理，synchronized来处理并发。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/e6e1e03313ca4931b61ed525ec1b2f81.png)

我们ConfigService默认实现类就是NacosConfigService，这个service的初始化就比较关键了，我们需要关注两个属性agent 是一个HttpAgent类型，用于发起http的一个类，这里也是用了一个非常经典的装饰器模式，

第二个是ClientWorker类，他是客户端的工作类，这里可以猜测，他会基于agent 做一些http请求，这个clientwork也是nacos配置中心，客户端中比较关键的一个类，

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/c37432d32e784404b5adb5eb0617b957.png)

### 3.4 ClientWork分析

我们进入clientWork里面：

init初始化几个比价关键的bean

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/95ae66572d69467a9b3a5fa4fa807844.png)

timeout :初始时间是30000ms，咱们讲过nacos长轮询的默认值就是30秒，这个值就是从这里来的。这个值会放到http头当中。然后发给服务端，服务端会根据这个时间进行长轮询，

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/5d1c34ed9c89436faa2f95edcbab3bf3.png)

意思就是这个线程池里面就有一个线程进行执行

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/0dbccf1a0f7842338afd8656402695ec.png)

接着是一样，这里用一个newScheduleThreadPool，这里是一个定时任务的线程池

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/cdb688c85dac4a63ad13f8d20d5ac700.png)

接着执行第一个线程池。延迟1毫秒进行执行，每过10毫秒然后执行一个，然后我们看一下里面的执行内容

> 注意：这里每10毫秒执行一次，会不会很频繁对性能有损耗？
>
> ```
> 这里定时任务是在上个任务执行完毕后和现在任务开始之间相差10ms，也就是在上个任务没有执行完毕时候，他不会开启第二个任务，而每个任务是长轮询机制。所以不会有每10ms执行一次的问题。
> ```

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/386d77ab57fb4afb86382c47ee53940a.png)

cacheMap就是我们启动的时候，加载的需要监听的nacos配置文件的类，他的key是什么？ 就是dataId+groupID来表示一个key,value就是配置的内容。我们可以看一下他的类型里面用的ConcurrentHashMap，里面可以避免并发产生的线程问题。 ，这里第一步就是获取监听配置文件的数量。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/0ad197e7f1994d6cbbeb618ad2460300.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/67d8eab127b141118ac53bd209f06ec5.png)

一个LongPollingRunnable可以监听3000个文件的变化。如果超过3000文件就会有额外的类来监听

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/8d7a205e0600418f8a4dd48832dc55ea.png)

我们看一下LongPollingRunnable的run方法：

第一：遍历我们的cacheMap的集合，去看他的本地的配置进行检查，

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/1d96a1e1a6cb4cdab0ca3508e20dfa39.png)

第二部：这个就是之前讲到的比较关键的流程，调用服务端长轮询的流程，

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/e31eb94abaee4d6587d39a2e8beb6d8b.png)

首先拼写dataId和group组，

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/d7190385e23d4c6cb5f01498b6b42d67.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/8d6924b7fcb8479e9f1c70ac59d1a225.png)

这里就是做一个http请求调用，来调用服务端，来请求我要关注的数据，那些数据发生了变化，他会将我们本地需要监听的配置文件，以dataId和group进行组合，然后调用到服务端， 首先拼一个头，他里面有一个关键的参数就是timeout，他的默认值就是30秒，前面说过，

### 3.5 发送HTTP请求获取配置

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/91c0024bba784ba9ab0c5e1ad2ba204a.png)

接着他会发送一个请求，调用他们的listen接口。好，客户端的请求就可能在这里hang住了，客户端会发送一个链接，服务端会阻塞30秒，我们来看一下服务端做了什么？

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/c7b4ca57304e41a3b4ca64e33d75bb7c.png)

### 3.6 服务端处理长轮询

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/8b1d06c6eb1a4f109f2b0fd23ac81835.png)

找到对应listener请求的controller类。这个接口就是接收客户端发送长轮询的http请求的接口。

首先他会获取客户端发送的需要监听的可能发生变化的配置文件的列表，并且计算他们的md5值，然后他会调用inner.doPollingConfig,

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/cfdbaad236a64afe8983b8f12f95351c.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/51531abcd2194adab170e6a1f607e9e7.png)

这个方法里面就会调用所谓的长轮询，他的长轮询 是怎么实现的，我们进来看一下：

首先判断是否支持长轮询，isSupportLongPolling

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/57ae9e8f61da4093859b5554ef3ab891.png)

他会从header里面获取LONG_POLLING_HEADER，如果没有就不支持长轮询，服务端发送是一个短轮询的请求默认是支持长轮询的，他就会调用addLongPollingClient

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/467939b2072d4c96a2c9aac8463758bd.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/01b10e9d56474db384497cab81747db9.png)

将客户端传过来的值都传递进来。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/e61838a12a344d92aa7f7cb2cdccb0db.png)

进入方法里面我们会发现，有中文注释他会提前500ms进行返回。避免客户端超时。前面说的500毫秒就是在这里来的。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/001d8af3c86b4ce796557d2ce4366058.png)

首先判断是否是一个IsFixedPolling，如果是就是30秒，如果不是就是29.5秒，执行完毕，我们这里要不就是30秒，要不就是29.5s，下面的代码可能不是很重要关键就是这个scheduler执行这样一个线程，

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/65b317844f8a4bb98cf531e3129b60de.png)

我们可以看一下这个scheduler初始化：

两个关键的变量一个是allSubs：他是一个支持并发链接的队列。这个也是在之前的图中讲到，他是将客户端每次请求都会放到这个quene里面，放到这里面就是，当页面或者通过api调用修改了配置，修改配置之后会发送一个消息，订阅消息一端就会遍历我们刚刚这个allSubs队列当中，看看这个队列里面请求的配置发生了变化，如果发生变化的配置和请求的客户端监听的配置匹配上之后，就会立即将变更的内容发挥给客户端。这就是队列的作用。

初始化的第二个参数：他有是一个schedule的线程池，nacos里面大量使用这样的线程池，在初始化的后，他就会执行每10ms就会执行一下这个任务。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/99a70f246cc2468eba6ddb43dd4228e8.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/21db75d9aa0a499fad061504e093aa83.png)

返回到最初：

将客户端的长轮询请求封装我 CliengLongPolling，交给定时任务去处理，

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/a38ffac3bbd04a818659f6d3e650d791.png)

我们来看来看一下ClientLongPolling的run里面执行了些什么事情：

里面是一个schedule，所有的逻辑在里面的run方法里面。他也是在timeoutTime之后才会执行。他会再29.5秒或者30秒之后执行这段代码。这里面的代码就是将客户端请求，进行个返回，那返回之前他会干什么，他会进行比较， 他会比较我们监听的配置和我本身的配置是否发生了变化，

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/6b703c54527a49028d05f13585350459.png)

如果发生变化，changedGroups.size()就会大于0了，就会changeGroup以及里面内容返回给客户端，如果没有变化就返回一个空。所以他在发送请求之前也会放到刚才说的队列当中，当然我们源码分析中会直接返回并不会检查。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/df21b3552df6400e883875a3bc99f5ac.png)

这个队列的作用我们刚才也说过，就是在29.5秒之内，如果有人再服务端页面或者调用相关api，更改了配置，立刻就会有一个消息发送过来，消息发送监听之后就会操作这个队列，

从这段代码我们可以看出，其实服务端收到长轮询之后，不会立即返回，而是在延长29.5秒才会将请求返回给客户端。这就使得客户端和服务端在30秒之内数据没有发生变化的情况下，是一直处于链接状态，到这里服务端配置的疑惑基本已经跟同学们讲明白了。 目前讲到这里我们还有另一个疑惑，我们通过api或者控制台怎样实时的显示。目前来看我们这个定时任务是延迟timeoutTime，去执行的，根本没有达到一个实时的目的，那我们可以看一下本类（LongPollingService）他继承了AbstractEventListener，这个listener就是一个消息监听机制，他有一个对应的onEvent的，

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/a7a5587f7f27499d8131e3fa2a20f97d.png)

### 3.7 控制台更新配置

我们看一下这个onEvent做了什么？

他就是监听了本地变更的消息，他机会执行一个DataChangeTask，这里面就会传递这个groupKey，我们查看一下这个DataChangeTask里面的内容。 这个task也是一个线程。他的执行的代码在他的run当中，

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/ed763ee0f3ed406188dcd65df4bfdd00.png)

他就是遍历我们刚才的队列，他在遍历过程中，就那我们发生变更的groupKey，和队列中的groupKey是否是匹配的。如果是匹配的他就会知道这个客户端的请求。将他响应给客户端，这就达到实时通信的目的。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/36ef52f330924461a9b324086b3b5a3b.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/e158e8ea9aa14131aa22b7409fcdc8b6.png)

发布时间之后，他会传递到DataChangeTask中进行数据更改事件的处理。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1556929612271845376/58ab7c472f114456a47f90fd3583056c.jpg)
