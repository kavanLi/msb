### 一、数据库事务ACID特性

基础概念：事务ACID

* A（Atomic）：原子性，构成事务的所有操作，要么都执行完成，要么全部不执行，不可能出现部分成功部分失 败的情况。
* C（Consistency）：一致性，在事务执行前后，数据库的一致性约束没有被破坏。比如：张三向李四转100元， 转账前和转账后的数据是正确状态这叫一致性，如果出现张三转出100元，李四账户没有增加100元这就出现了数 据错误，就没有达到一致性。
* I（Isolation）：隔离性，数据库中的事务一般都是并发的，隔离性是指并发的两个事务的执行互不干扰，一个事 务不能看到其他事务运行过程的中间状态。通过配置事务隔离级别可以避脏读、重复读等问题。
* D（Durability）：持久性，事务完成之后，该事务对数据的更改会被持久化到数据库，且不会被回滚。

# 二、什么是分布式事务

分布式事务就是指事务的参与者、支持事务的服务器、资源服务器以及事务管理器分别位于不同的分布式系统的不同节点之上。

# 三、**分布式事务典型场景**

## **1、多服务**

随着互联网快速发展，微服务，SOA等服务架构模式正在被大规模的使用，会出现很多分布式事务问题如下：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1662350890004/0c78fa8b774e43a68d0cbf378a1881fc.png)

## 2、多数据源

### 2.1 跨库

跨库事务指的是，一个应用某个功能需要操作多个库，如下图：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1662350890004/571112c44d424804b597d198e9ef9823.png)

### **2.2 分库分表**

通常一个库数据量比较大或者预期未来的数据量比较大，都会进行水平拆分，也就是分库分表。如下图，将订单数据库拆分成了4个库：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1662350890004/13f2696d69ad41c88db7b45f7e85fbac.png)

小结：上述讨论的分布式事务场景中，无一例外的都直接或者间接的操作了多个数据库。如何保证事务的ACID特性，对于分布式事务实现方案而言，是非常大的挑战。同时，分布式事务实现方案还必须要考虑性能的问题，如果为了严格保证ACID特性，导致性能严重下降，那么对于一些要求快速响应的业务，是无法接受的。

# 四、分布式事务基础理论

随着互联化的蔓延，各种项目都逐渐向分布式服务做转换。如今微服务已经普遍存在，本地事务已经无法满足分布式的要求，由此分布式事务问题诞生。 分布式事务被称为世界性的难题，目前分布式存在两大理论依据：CAP定律 BASE理论。

## 1、CAP定律

这个定理的内容是指的是在一个分布式系统中、Consistency（一致性）、 Availability（可用性）、Partition tolerance（分区容错性），三者不可得兼。

- 一致性（C）

  对某个指定的客户端来说，读操作能返回最新的写操作。对于数据分布在不同节点上的数据上来说，如果在某个节点更新了数据，那么在其他节点如果都能读取到这个最新的数据，那么就称为强一致，如果有某个节点没有读取到，那就是分布式不一致。
- 可用性（A）

  非故障的节点在合理的时间内返回合理的响应(不是错误和超时的响应)。可用性的两个关键一个是合理的时间，一个是合理的响应。合理的时间指的是请求不能无限被阻塞，应该在合理的时间给出返回。合理的响应指的是系统应该明确返回结果并且结果是正确的，这里的正确指的是比如应该返回50，而不是返回40。
- 分区容错性（P）

  当出现网络分区后，系统能够继续工作。打个比方，这里个集群有多台机器，有台机器网络出现了问题，但是这个集群仍然可以正常工作。

在分布式系统中，网络无法100%可靠，分区其实是一个必然现象，如果我们选择了CA而放弃了P，那么当发生分区现象时，为了保证一致性，这个时候必须拒绝请求，但是A又不允许，所以分布式系统理论上不可能选择CA架构，只能选择CP或者AP架构。

对于CP来说，放弃可用性，追求一致性和分区容错性，我们的zookeeper其实就是追求的强一致。

对于AP来说，放弃一致性(这里说的一致性是强一致性)，追求分区容错性和可用性，这是很多分布式系统设计时的选择，后面的BASE也是根据AP来扩展。

## 2、BASE理论

BASE是Basically Available（基本可用）、Soft state（软状态）和 Eventually consistent（最终一致性）三个短语的缩写。BASE理论是对CAP中一致性和可用性权衡的结果，其来源于对大规模互联网系统分布式实践的总结， 是基于CAP定理逐步演化而来的。BASE理论的核心思想是：即使无法做到强一致性，但每个应用都可以根据自身业务特点，采用适当的方式来使系统达到最终一致性。

- 基本可用

  基本可用是指分布式系统在出现不可预知故障的时候，允许损失部分可用性—-注意，这绝不等价于系统不可用。比如：

  （1）响应时间上的损失。正常情况下，一个在线搜索引擎需要在0.5秒之内返回给用户相应的查询结果，但由于出现故障，查询结果的响应时间增加了1~2秒

  （2）系统功能上的损失：正常情况下，在一个电子商务网站上进行购物的时候，消费者几乎能够顺利完成每一笔订单，但是在一些节日大促购物高峰的时候，由于消费者的购物行为激增，为了保护购物系统的稳定性，部分消费者可能会被引导到一个降级页面
- 软状态

  软状态指允许系统中的数据存在中间状态，并认为该中间状态的存在不会影响系统的整体可用性，即允许系统在不同节点的数据副本之间进行数据同步的过程存在延时
- 最终一致性

  最终一致性强调的是所有的数据副本，在经过一段时间的同步之后，最终都能够达到一个一致的状态。因此，最终一致性的本质是需要系统保证最终数据能够达到一致，而不需要实时保证系统数据的强一致性。

## 3、酸碱平衡

ACID （酸）能够保证事务的强一致性，即数据是实时一致的。

BASE（碱） 要求的最终一致性

| 目标             | 说明                                                                                                                                                                                    | 场景                                                 |
| ---------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------- |
| 强一致（ACID）   | 参与事务的分支全部一起行动，单个子事务操作不完全提交，等待最终提交&#x3c;br />当事务完成后，后续其它的访问都会返回最新的更新过的值。                                                     | 比如下单扣优惠券，需要强一致性，不然优惠券可以用多次 |
| 最终一致（BASE） | 参与事务的分支可以单独行动，单个子事务操作可以先行提交不保证后续的访问，&#x3c;br />能够立刻返回最新的值，不确定多久之后可以访问到，但是需要保证过一段时间之后，最终能够访问到最新的值。 | 比如下单送积分，积分可以晚一点再送，不会影响主流程   |

强一致性

最终一致性

# 五、分布式事务协议

## 1、2PC

### 1.1 流程

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1662350890004/1c2fae0232d146b0902575f5ca92abea.png)

### 1.2 优缺点

优点： 尽量保证了数据的强一致，实现成本较低，在各大主流数据库都有自己实现，对于MySQL是从5.5开始支持(XA)。

缺点：

- **单点问题**:事务管理器在整个流程中扮演的角色很关键，如果其宕机，比如在第一阶段已经完成，在第二阶段正准备提交的时候事务管理器宕机，资源管理器就会一直阻塞，导致数据库无法使用。
- **同步阻塞**:在准备就绪之后，资源管理器中的资源一直处于阻塞，直到提交完成，释放资源。
- **数据不一致**:两阶段提交协议虽然为分布式数据强一致性所设计，但仍然存在数据不一致性的可能，比如在第二阶段中，假设协调者发出了事务commit的通知，但是因为网络问题该通知仅被一部分参与者所收到并执行了commit操作，其余的参与者则因为没有收到通知一直处于阻塞状态，这时候就产生了数据的不一致性。

## 2、3PC

三段提交（3PC）是对两段提交（2PC）的一种升级优化，`3PC`在 `2PC`的第一阶段和第二阶段中插入一个准备阶段。保证了在最后提交阶段之前，各参与者节点的状态都一致。同时在协调者和参与者中都引入超时机制，当 `参与者`各种原因未收到 `协调者`的commit请求后，会对本地事务进行abort，不会一直阻塞等待，解决了 `2PC`的单点故障问题，但 `3PC` 还是没能从根本上解决数据一致性的问题。

### 2.1 流程

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1662350890004/34881d50d44943e588689352f62f57b0.png)

**CanCommit**：协调者向所有参与者发送CanCommit命令，询问是否可以执行事务提交操作。如果全部响应YES则进入下一个阶段。

**PreCommit**：`协调者`向所有 `参与者`发送 `PreCommit`命令，询问是否可以进行事务的预提交操作，参与者接收到PreCommit请求后，如参与者成功的执行了事务操作，则返回 `Yes`响应，进入最终commit阶段。一旦参与者中有向协调者发送了 `No`响应，或因网络造成超时，协调者没有接到参与者的响应，协调者向所有参与者发送 `abort`请求，参与者接受abort命令执行事务的中断。

**DoCommit**： 在前两个阶段中所有参与者的响应反馈均是 `YES`后，协调者向参与者发送 `DoCommit`命令正式提交事务，如协调者没有接收到参与者发送的ACK响应，会向所有参与者发送 `abort`请求命令，执行事务的中断

### 2.2 优缺点

- 优点：

```
1、引入超时机制。解决了事务管理器突然宕机导致资源一直处于阻塞的问题
```

```
2、多了一次询问阶段。防止个别参与者不正常的情况下，其他参与者都执行了事务，锁定资源。
```

- 缺点：

1、 `3PC` 用超时机制，同步阻塞问题，但与此同时却多了一次网络通信，性能上反而变得更差，也不太推荐。

2、没有解决数据不一致的问题

# 六、Seata

## 1、Seata介绍

官网：https://seata.io/zh-cn/docs/overview/what-is-seata.html

概念：Seata 是一款开源的分布式事务解决方案，致力于提供高性能和简单易用的分布式事务服务。Seata 将为用户提供了 AT、TCC、SAGA 和 XA 事务模式，为用户打造一站式的分布式解决方案。

- **AT模式**:提供无侵入自动补偿的事务模式
- **XA模式**:支持已实现XA接口的数据库的XA模式
- **TCC模式**:TCC则可以理解为在应用层面的 `2PC`，是需要我们编写业务逻辑来实现。
- **SAGA模式**:为长事务提供有效的解决方案

## 2、Seata术语

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1662350890004/65897bc2e9bc468bba0d59ee8ab58dab.png)**TC (Transaction Coordinator) - 事务协调者**

维护全局和分支事务的状态，驱动全局事务提交或回滚，可以集群部署也可单独部署。

**TM (Transaction Manager) - 事务管理器**

定义全局事务的范围：开始全局事务、提交或回滚全局事务。

**RM (Resource Manager) - 资源管理器**

管理分支事务处理的资源，与TC交谈以注册分支事务和报告分支事务的状态，并驱动分支事务提交或回滚。

其中，TC 为单独部署的 Server 服务端，TM 和 RM 为嵌入到应用中的 Client 客户端。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1662350890004/017b528df799415ba3dc8d025b9e334a.png)

1.TM 请求 TC 开启一个全局事务。TC 会生成一个 XID 作为该全局事务的编号。XID，会在微服务的调用链路中传播，保证将多个微服务的子事务关联在一起。

2.RM 请求 TC 将本地事务注册为全局事务的分支事务，通过全局事务的 XID 进行关联。

3.TM 请求 TC 告诉 XID 对应的全局事务是进行提交还是回滚。

4.TC 驱动 RM 们将 XID 对应的自己的本地事务进行提交还是回滚。

## 3、基于Nacos的Seata搭建

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1676957237003/b16f36f37b7243e9a36a38b9ce733d2e.png)

### 3.1 下载对应jar包和源码

jar地址：https://github.com/seata/seata/releases

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1676957237003/81f6da34342940ee827dc06cc2a547a4.png)

源码：https://github.com/seata/seata/tags

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1676957237003/7f86e99dbe854d35a81606c5d1ae3014.png)

### 3.2  创建数据库

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1676957237003/d6c6751ec5894725abd0fbbb90c21056.png)

### 3.3 Seata中配置注册中心

将Seata Server注册到Nacos注册中心

```
  registry:
    # support: nacos, eureka, redis, zk, consul, etcd3, sofa
    type: nacos
    nacos:
      application: seata-server
      server-addr: 127.0.0.1:8848
      group: SEATA_GROUP
      namespace:
      cluster: default
      username:
      password:
```

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1676957237003/a2753b7f143f498bbdae6a90814cf19b.png)

注意：请确保client与server的注册处于同一个namespace和group，不然会找不到服务。

### 3.4 配置Nacos配置中心

#### 3.4.1 在nacos中创建命名空间seata

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1676957237003/6c490a389a0d465abea870628e5a0b3e.png)

#### 3.4.2 修改配置Nacos配置中心地址，修改conf/application.yml文件

修改配置

```
seata:
  config:
    # support: nacos, consul, apollo, zk, etcd3
    type: nacos
    nacos:
      server-addr: 127.0.0.1:8848
      namespace: seata
      group: SEATA_GROUP
      username:
      password:
      data-id: seataServer.properties
```

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1676957237003/d3be571ff0ea4a5f845687b113383c5a.png)

#### 3.4.3 上传Nacos配置中心

修改scritp/config-center/config.txt

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1676957237003/d96c6b0f678544ad9161926856430514.png)

获取/seata/script/config-center/config.txt，修改为db存储模式，并修改mysql连接配置

```
store.mode=db
store.lock.mode=db
store.session.mode=db
store.db.driverClassName=com.mysql.jdbc.Driver
store.db.url=jdbc:mysql://127.0.0.1:3306/seata?useUnicode=true&rewriteBatchedStatements=true
store.db.user=root
store.db.password=root
```

在store.mode=db，由于seata是通过jdbc的executeBatch来批量插入全局锁的，根据MySQL官网的说明，连接参数中的rewriteBatchedStatements为true时，在执行executeBatch，并且操作类型为insert时，jdbc驱动会把对应的SQL优化成 `insert into () values (), ()`的形式来提升批量插入的性能。
根据实际的测试，该参数设置为true后，对应的批量插入性能为原来的10倍多，因此在数据源为MySQL时，建议把该参数设置为true。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1676957237003/619bdd3c75794565b107edffbe89ad85.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1676957237003/f5b2fe718cca482789c88d698d7a16e8.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1676957237003/9443d08411cc4474b557a81cf0aa82f5.png)

在Nacos配置中心空间seata里面创建配置seataServer.properties，将config.txt内容设置进去

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1676957237003/ed36704cc4b8413e904091da6f87bdba.png)

### 3.5 启动SeataServer

启动命令

```
bin/seata-server.sh
```

正确启动两个端口

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1676957237003/3666065622fd473abe55d7d97238d5a5.png)

启动成功，查看控制台，账号密码都是seata。[http://localhost:7091/#/login](http://localhost:7091/#/login)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1676957237003/5f3ed6d9aaeb4ddd9647e32cbbb6dce0.png)

## 4、Seata AT模式的设计思路

### **4.1 前提**

* 基于支持本地 ACID 事务的关系型数据库。
* Java 应用，通过 JDBC 访问数据库。

### 4.2 整体机制

两阶段提交协议的演变：

* 一阶段：业务数据和回滚日志记录在同一个本地事务中提交，释放本地锁和连接资源。
* 二阶段：
  * 提交异步化，非常快速地完成。
  * 回滚通过一阶段的回滚日志进行反向补偿。

**一阶段**

Seata 的 JDBC 数据源代理通过对业务 SQL 的解析，把业务数据在更新前后的数据镜像组织成回滚日志，利用 **本地事务** 的 ACID 特性，将业务数据的更新和回滚日志的写入在同一个 **本地事务** 中提交。

这样，可以保证：任何提交的业务数据的更新一定有相应的回滚日志存在。

![Branch Transaction with UNDO LOG](https://img-blog.csdnimg.cn/6feac2bef42442a2b754d13c089769b8.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5aSn5qCR5YWI55SfLg==,size_20,color_FFFFFF,t_70,g_se,x_16)

基于这样的机制，分支的本地事务便可以在全局事务的 **执行阶段** 提交，马上释放本地事务锁定的资源。

**完成阶段：**

* 如果决议是全局提交，此时分支事务此时已经完成提交，不需要同步协调处理（只需要异步清理回滚日志），**完成阶段** 可以非常快速地结束。

![Global Commit](https://img-blog.csdnimg.cn/105bd07fd4454716a21959c6a1852a42.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5aSn5qCR5YWI55SfLg==,size_20,color_FFFFFF,t_70,g_se,x_16)

如果决议是全局回滚，RM 收到协调器发来的回滚请求，通过 XID 和 Branch ID 找到相应的回滚日志记录，通过回滚记录生成反向的更新 SQL 并执行，以完成分支的回滚。

![Global Rollback](https://img-blog.csdnimg.cn/1c3b43a5d5504f0097343ffe9505a114.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5aSn5qCR5YWI55SfLg==,size_20,color_FFFFFF,t_70,g_se,x_16)

## 5、多数据源分布式事务

### 5.1 操作多数据源

#### 5.1.1 创建多个数据源

这里配置了我们的所有数据库，并将数据库添加DynamicRoutingDataSource这个动态数据库中，并设置了目标数据源和配置的数据源，配置的数据源以map形式存放，然后将DynamicRoutingDataSource数据源传递给了mybatis，mybatis操作数据库就是通过他来操作

```java
@Configuration
@MapperScan("com.msb.seata.multiple.mapper")
public class DataSourceProxyConfig {

    @Bean("originOrder")
    @ConfigurationProperties(prefix = "spring.datasource.order")
    public DataSource dataSourceMaster() {
        return new DruidDataSource();
    }

    @Bean("originStorage")
    @ConfigurationProperties(prefix = "spring.datasource.storage")
    public DataSource dataSourceStorage() {
        return new DruidDataSource();
    }

    @Bean("originAccount")
    @ConfigurationProperties(prefix = "spring.datasource.account")
    public DataSource dataSourceAccount() {
        return new DruidDataSource();
    }


    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource(@Qualifier("originOrder") DataSource dataSourceOrder,
                                        @Qualifier("originStorage") DataSource dataSourceStorage,
                                        @Qualifier("originAccount") DataSource dataSourceAccount) {

        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();

        // 数据源的集合
        Map<Object, Object> dataSourceMap = new HashMap<>(3);
        dataSourceMap.put(DataSourceKey.ORDER.name(), dataSourceOrder);
        dataSourceMap.put(DataSourceKey.STORAGE.name(), dataSourceStorage);
        dataSourceMap.put(DataSourceKey.ACCOUNT.name(), dataSourceAccount);

        // 设置默认的数据源
        dynamicRoutingDataSource.setDefaultTargetDataSource(dataSourceOrder);
        // 设置目标数据源
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);

        DynamicDataSourceContextHolder.getDataSourceKeys().addAll(dataSourceMap.keySet());

        return dynamicRoutingDataSource;
    }

    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("dynamicDataSource") DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
  
        org.apache.ibatis.session.Configuration configuration=new org.apache.ibatis.session.Configuration();
        //使用jdbc的getGeneratedKeys获取数据库自增主键值
        configuration.setUseGeneratedKeys(true);
        //使用列别名替换列名
        configuration.setUseColumnLabel(true);
        //自动使用驼峰命名属性映射字段，如userId ---> user_id
        configuration.setMapUnderscoreToCamelCase(true);
        sqlSessionFactoryBean.setConfiguration(configuration);
  
        return sqlSessionFactoryBean;
    }

}
```

#### 5.1.2 创建DynamicRoutingDataSource

DynamicRoutingDataSource继承了AbstractRoutingDataSource，并且重写了determineCurrentLookupKey方法，这个方法，是在getConnection时候我们会调用这个方法活去对应的key，然后再对应我们的map数据库集合中获取对应的数据源。后面我们在源码讲解

```java
@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    /**
     * 该方法的返回值就是项目中所要用的DataSource的key值，
     * 拿到该key后就可以在resolvedDataSource中取出对应的DataSource，
     * 如果key找不到对应的DataSource就使用默认的数据源。
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        log.info("当前数据源 [{}]", DynamicDataSourceContextHolder.getDataSourceKey());
        return DynamicDataSourceContextHolder.getDataSourceKey();

    }

    public static void main(String[] args) {
        System.out.println(DynamicDataSourceContextHolder.getDataSourceKey());
    }
}
```

#### 5.1.3 创建切面

我们对应的service 上都是有@Datasource注解，这个注解DynamicDataSourceAspect会在方法调用的时候进行拦截，在方法之间会解析@Datasource中的值，会将其设置到ThreadLocal里面，这里面DynamicDataSourceContextHolder，然后调用mybatis时候获取链接的时候时候会带调用上面的类determineCurrentLookupKey获取对应的key，我们通过这个key可以获取对应的数据源

```java
@Aspect
@Component
public class DynamicDataSourceAspect {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    @Before("@annotation(ds)")
    public void changeDataSource(JoinPoint point, DataSource ds) throws Throwable {
        DataSourceKey dsId = ds.value();
        if (DynamicDataSourceContextHolder.isContainsDataSource(dsId.name())) {
            DynamicDataSourceContextHolder.setDataSourceKey(dsId);
            logger.debug("Use DataSource :{} >", dsId, point.getSignature());
        } else {
            logger.error("数据源[{}]不存在，使用默认数据源 >{}", dsId, point.getSignature());
        }
    }

    @After("@annotation(ds)")
    public void restoreDataSource(JoinPoint point, DataSource ds) {
        logger.debug("Revert DataSource : " + ds.value() + " > " + point.getSignature());
        DynamicDataSourceContextHolder.clearDataSourceKey();

    }
}
```

首先我们给DynamicRoutingDataSource设置数据源setTargetDataSources 是一个map数据源，设置完后afterPropertiesSet方法里面会将我们设置的map设置到我们的resovledDataSources中

当我调用getConnnection时候会调用DetermineTargetDataSources方法，这个方法会调用我们重写的determineCourrentLookupKey，从而获取数据源对应的ekey

最后 通过key从resolveDataSources中获取了对应的数据源。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1662350890004/d41a09fe208a4e40968791a278fb3e31.png)

## 6、接入微服务应用

**业务场景：**

用户下单，整个业务逻辑由三个微服务构成：

- 仓储服务：对给定的商品扣除库存数量。
- 订单服务：根据采购需求创建订单。
- 帐户服务：从用户帐户中扣除余额。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1662350890004/fc28bff020314043a4403393da94d4aa.png)

### 6.1 版本处理

| **Spring Cloud Alibaba Version** | **Spring Cloud Version** | **Spring Boot Version** | **Nacos Version** | **Seata Version** |
| -------------------------------------- | ------------------------------ | ----------------------------- | ----------------------- | ----------------------- |
| **2.2.9.RELEASE**                | **Hoxton.SR12**          | **2.3.12.RELEASE**      | **2.1.0**         | **1.5.2**         |

### 6.2 引入依赖

```
   <!--引入seata-->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-seata</artifactId>
        </dependency>
```

### 6.3 数据源设置为代理

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1676957237003/dac5417c60fe443bb9b02b16b6475f62.png)

### 6.4 排除数据源的自动配置方式循环依赖

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1676957237003/3a6e471950484a0caaa9fe19effd3e75.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1676957237003/3bd644262e09413dba5536d42b55c0f5.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1676957237003/31e55ded1c0147c188b70bfa81c3cf7f.png)

### 6.5 增加配置

```
seata:
  application-id: ${spring.application.name}
  # seata 服务分组，要与服务端配置service.vgroup_mapping的后缀对应
  tx-service-group: default_tx_group
  registry:
    # 指定nacos作为注册中心
    type: nacos
    nacos:
      application: seata-server
      server-addr: 127.0.0.1:8848
      namespace:
      group: SEATA_GROUP

  config:
    # 指定nacos作为配置中心
    type: nacos
    nacos:
      server-addr: 127.0.0.1:8848
      namespace: seata
      group: SEATA_GROUP
      data-id: seataServer.properties
```

### 6.6 微服务中创建undo_log

```
-- for AT mode you must to init this sql for you business database. the seata server not need it.
CREATE TABLE IF NOT EXISTS `undo_log`
(
    `branch_id`     BIGINT       NOT NULL COMMENT 'branch transaction id',
    `xid`           VARCHAR(128) NOT NULL COMMENT 'global transaction id',
    `context`       VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` LONGBLOB     NOT NULL COMMENT 'rollback info',
    `log_status`    INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   DATETIME(6)  NOT NULL COMMENT 'create datetime',
    `log_modified`  DATETIME(6)  NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='AT transaction mode undo table';
```

## 7、SeataXA 模式设计思路

### 7.1  前提

* 支持XA 事务的数据库。
* Java 应用，通过 JDBC 访问数据库。

### 7.2 整体机制

在 Seata 定义的分布式事务框架内，利用事务资源（数据库、消息服务等）对 XA 协议的支持，以 XA 协议的机制来管理分支事务的一种 事务模式。

![](https://img.alicdn.com/tfs/TB1hSpccIVl614jSZKPXXaGjpXa-1330-924.png)

* 执行阶段：
  * 可回滚：业务 SQL 操作放在 XA 分支中进行，由资源对 XA 协议的支持来保证 *可回滚*
  * 持久化：XA 分支完成后，执行 XA prepare，同样，由资源对 XA 协议的支持来保证  *持久化* （即，之后任何意外都不会造成无法回滚的情况）
* 完成阶段：
  * 分支提交：执行 XA 分支的 commit
  * 分支回滚：执行 XA 分支的 rollback

### 7.3 XA模式使用

```
    @Bean("dataSource")
    public DataSource dataSource(DruidDataSource druidDataSource) {
        // DataSourceProxy for AT mode
        // return new DataSourceProxy(druidDataSource);

        // DataSourceProxyXA for XA mode
        return new DataSourceProxyXA(druidDataSource);
    }
```

设置XA模式

```
seata:
  data-source-proxy-mode: XA
```

### 7.4 Seata XA模式优势确定

优势

- 业务无侵入：和 AT 一样，XA 模式将是业务无侵入的，不给应用设计和开发带来额外负担。
- 支持多种数据库 ：XA 协议被主流关系型数据库广泛支持，不需要额外的适配即可使用
- 支持多种语言

**缺点：**

会有阻塞模式 ，降低性能

## 8、Seata TCC 模式设计思路

### 8.1 TCC 命名由来

TCC 基于分布式事务中的二阶段提交协议实现，它的全称为 Try-Confirm-Cancel，即资源预留（Try）、确认操作（Confirm）、取消操作（Cancel），他们的具体含义如下：

1. Try：对业务资源的检查并预留；
2. Confirm：对业务处理进行提交，即 commit 操作，只要 Try 成功，那么该步骤一定成功；
3. Cancel：对业务处理进行取消，即回滚操作，该步骤回对 Try 预留的资源进行释放。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1662350890004/035778f7212d44e0bad25f344ede8b30.jpg)

TCC 是一种侵入式的分布式事务解决方案，以上三个操作都需要业务系统自行实现，对业务系统有着非常大的入侵性，设计相对复杂，但优点是 TCC 完全不依赖数据库，能够实现跨数据库、跨应用资源管理，对这些不同数据访问通过侵入式的编码方式实现一个原子操作，更好地解决了在各种复杂业务场景下的分布式事务问题。

### 8.2 执行流程

![Overview of a global transaction](https://seata.io/img/seata_tcc-1.png)

### 8.3 TCC和XA的区别

AT 模式基于 **支持本地 ACID 事务** 的  **关系型数据库** ：

* 一阶段 prepare 行为：在本地事务中，一并提交业务数据更新和相应回滚日志记录。
* 二阶段 commit 行为：马上成功结束，**自动** 异步批量清理回滚日志。
* 二阶段 rollback 行为：通过回滚日志，**自动** 生成补偿操作，完成数据回滚。

相应的，TCC 模式，不依赖于底层数据资源的事务支持：

* 一阶段 prepare 行为：调用 **自定义** 的 prepare 逻辑。
* 二阶段 commit 行为：调用 **自定义** 的 commit 逻辑。
* 二阶段 rollback 行为：调用 **自定义** 的 rollback 逻辑。

简单点概括，SEATA的TCC模式就是手工的AT模式，它允许你自定义两阶段的处理逻辑而不依赖AT模式的undo_log。

### 8.4  TCC存在的问题

#### 8.4.1 空回滚以及解决方案

空回滚指的是在一个分布式事务中，在没有调用参与方的 Try 方法的情况下，TM 驱动二阶段回滚调用了参与方的 Cancel 方法。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1676957237003/d9b870eda1e24eed80fbafd59a27a6f6.png)

如上图所示，全局事务开启后，参与者 A 分支注册完成之后会执行参与者一阶段 RPC 方法，如果此时参与者 A 所在的机器发生宕机，网络异常，都会造成 RPC 调用失败，即参与者 A 一阶段方法未成功执行，但是此时全局事务已经开启，Seata 必须要推进到终态，在全局事务回滚时会调用参与者 A 的 Cancel 方法，从而造成空回滚。

**要想防止空回滚，那么必须在 Cancel 方法中识别这是一个空回滚，Seata 是如何做的呢？**

Seata 的做法是新增一个 TCC 事务控制表，包含事务的 XID 和 BranchID 信息，在 Try 方法执行时插入一条记录，表示一阶段执行了，执行 Cancel 方法时读取这条记录，如果记录不存在，说明 Try 方法没有执行。

#### 8.4.2幂等问题以及解决方案

幂等问题指的是 TC 重复进行二阶段提交，因此 Confirm/Cancel 接口需要支持幂等处理，即不会产生资源重复提交或者重复释放。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1676957237003/5ec3862528494841b699df88204b28b1.png)

如上图所示，参与者 A 执行完二阶段之后，由于网络抖动或者宕机问题，会造成 TC 收不到参与者 A 执行二阶段的返回结果，TC 会重复发起调用，直到二阶段执行结果成功。

**Seata 是如何处理幂等问题的呢？**

同样的也是在 TCC 事务控制表中增加一个记录状态的字段 status，该字段有 3 个值，分别为：

1. tried：1
2. committed：2
3. rollbacked：3

二阶段 Confirm/Cancel 方法执行后，将状态改为 committed 或 rollbacked 状态。当重复调用二阶段 Confirm/Cancel 方法时，判断事务状态即可解决幂等问题。

#### 8.4.3 悬挂问题以及解决方案

悬挂指的是二阶段 Cancel 方法比 一阶段 Try 方法优先执行，由于允许空回滚的原因，在执行完二阶段 Cancel 方法之后直接空回滚返回成功，此时全局事务已结束，但是由于 Try 方法随后执行，这就会造成一阶段 Try 方法预留的资源永远无法提交和释放了

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1676957237003/772a25b3c796400aaacb774ff21ded8b.png)

如上图所示，在执行参与者 A 的一阶段 Try 方法时，出现网路拥堵，由于 Seata 全局事务有超时限制，执行 Try 方法超时后，TM 决议全局回滚，回滚完成后如果此时 RPC 请求才到达参与者 A，执行 Try 方法进行资源预留，从而造成悬挂。

**Seata 是怎么处理悬挂的呢？**

在 TCC 事务控制表记录状态的字段 status 中增加一个状态：

* suspended：4

当执行二阶段 Cancel 方法时，如果发现 TCC 事务控制表有相关记录，说明二阶段 Cancel 方法优先一阶段 Try 方法执行，因此插入一条 status=4 状态的记录，当一阶段 Try 方法后面执行时，判断 status=4 ，则说明有二阶段 Cancel 已执行，并返回 false 以阻止一阶段 Try 方法执行成功。

### 8.5 Seata TCC模式的接口改造

假设现有一个业务需要同时使用服务 A 和服务 B 完成一个事务操作，我们在服务 A 定义该服务的一个 TCC 接口：

```
 public interface TccActionOne {
    // 业务处理
    @TwoPhaseBusinessAction(name = "TccActionOne", commitMethod = "commit", rollbackMethod = "rollback")
    public boolean prepare(BusinessActionContext actionContext, int a);

   // 提交 
    public boolean commit(BusinessActionContext actionContext);

   // 回滚
    public boolean rollback(BusinessActionContext actionContext);
}

```

同样，在服务 B 定义该服务的一个 TCC 接口：

```
 public interface TccActionTwo {
    // 业务处理
    @TwoPhaseBusinessAction(name = "TccActionTwo ", commitMethod = "commit", rollbackMethod = "rollback")
    public boolean prepare(BusinessActionContext actionContext, int a);

   // 提交 
    public boolean commit(BusinessActionContext actionContext);

   // 回滚
    public boolean rollback(BusinessActionContext actionContext);
}
```

在业务所在系统中开启全局事务并执行服务 A 和服务 B 的 TCC 预留资源方法：

```
@GlobalTransactional
public String doTransactionCommit(){
    //服务A事务参与者
    tccActionOne.prepare(null,"one");
    //服务B事务参与者
    tccActionTwo.prepare(null,"two");
}
```

以上就是使用 Seata TCC 模式实现一个全局事务的例子，TCC 模式同样使用 @GlobalTransactional 注解开启全局事务，而服务 A 和服务 B 的 TCC 接口为事务参与者，Seata 会把一个 TCC 接口当成一个 Resource，也叫 TCC Resource。

### 8.6 TCC 模式使用

**业务场景：**

用户下单，整个业务逻辑由三个微服务构成：

- 仓储服务：对给定的商品扣除库存数量。
- 订单服务：根据采购需求创建订单。
- 帐户服务：从用户帐户中扣除余额。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1662350890004/fc28bff020314043a4403393da94d4aa.png)

#### 8.6.1 版本处理

| **Spring Cloud Alibaba Version** | **Spring Cloud Version** | **Spring Boot Version** | **Nacos Version** | **Seata Version** |
| -------------------------------------- | ------------------------------ | ----------------------------- | ----------------------- | ----------------------- |
| **2.2.9.RELEASE**                | **Hoxton.SR12**          | **2.3.12.RELEASE**      | **2.1.0**         | **1.5.2**         |

#### 8.6.2 引入依赖

```
   <!--引入seata-->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-seata</artifactId>
        </dependency>
```

#### 8.6.3 引入配置

```
seata:
  application-id: ${spring.application.name}
  # seata 服务分组，要与服务端配置service.vgroup_mapping的后缀对应
  tx-service-group: default_tx_group
  registry:
    # 指定nacos作为注册中心
    type: nacos
    nacos:
      application: seata-server
      server-addr: 127.0.0.1:8848
      namespace:
      group: SEATA_GROUP

  config:
    # 指定nacos作为配置中心
    type: nacos
    nacos:
      server-addr: 127.0.0.1:8848
      namespace: seata
      group: SEATA_GROUP
      data-id: seataServer.properties
```

#### 8.6.4 定义TCC接口

TCC相关注解如下：

* @LocalTCC 适用于SpringCloud+Feign模式下的TCC，@LocalTCC一定需要注解在接口上，此接口可以是寻常的业务接口，只要实现了TCC的两阶段提交对应方法便可
* @TwoPhaseBusinessAction 注解try方法，其中name为当前tcc方法的bean名称，写方法名便可（全局唯一），commitMethod指向提交方法，rollbackMethod指向事务回滚方法。指定好三个方法之后，seata会根据全局事务的成功或失败，去帮我们自动调用提交方法或者回滚方法。
* @BusinessActionContextParameter 注解可以将参数传递到二阶段（commitMethod/rollbackMethod）的方法。
* BusinessActionContext 便是指TCC事务上下文

```
@LocalTCC
public interface TccOrderService {

    /**
     * TCC的try方法：保存订单信息，状态为支付中
     *
     * 定义两阶段提交，在try阶段通过@TwoPhaseBusinessAction注解定义了分支事务的 resourceId，commit和 cancel 方法
     *  name = 该tcc的bean名称,全局唯一
     *  commitMethod = commit 为二阶段确认方法
     *  rollbackMethod = rollback 为二阶段取消方法
     *  BusinessActionContextParameter注解 传递参数到二阶段中
     *  useTCCFence seata1.5.1的新特性，用于解决TCC幂等，悬挂，空回滚问题，需增加日志表tcc_fence_log
     */
    @TwoPhaseBusinessAction(name = "prepareSaveOrder", commitMethod = "commit", rollbackMethod = "rollback", useTCCFence = true)
    Order prepareSaveOrder(OrderVo orderVo, @BusinessActionContextParameter(paramName = "orderId") Long orderId);

    /**
     *
     * TCC的confirm方法：订单状态改为支付成功
     *
     * 二阶段确认方法可以另命名，但要保证与commitMethod一致
     * context可以传递try方法的参数
     *
     * @param actionContext
     * @return
     */
    boolean commit(BusinessActionContext actionContext);

    /**
     * TCC的cancel方法：订单状态改为支付失败
     * 二阶段取消方法可以另命名，但要保证与rollbackMethod一致
     *
     * @param actionContext
     * @return
     */
    boolean rollback(BusinessActionContext actionContext);
}


@LocalTCC
public interface TccAccountService {

    /**
     * 用户账户扣款
     *
     * 定义两阶段提交，在try阶段通过@TwoPhaseBusinessAction注解定义了分支事务的 resourceId，commit和 cancel 方法
     *  name = 该tcc的bean名称,全局唯一
     *  commitMethod = commit 为二阶段确认方法
     *  rollbackMethod = rollback 为二阶段取消方法
     *
     * @param userId
     * @param money 从用户账户中扣除的金额
     * @return
     */
    @TwoPhaseBusinessAction(name = "debit", commitMethod = "commit", rollbackMethod = "rollback", useTCCFence = true)
    void tryDebit(@BusinessActionContextParameter(paramName = "userId") String userId,
                  @BusinessActionContextParameter(paramName = "money") int money);

    /**
     * 提交事务，二阶段确认方法可以另命名，但要保证与commitMethod一致
     * context可以传递try方法的参数
     *
     * @param actionContext
     * @return
     */
    boolean commit(BusinessActionContext actionContext);

    /**
     * 回滚事务，二阶段取消方法可以另命名，但要保证与rollbackMethod一致
     *
     * @param actionContext
     * @return
     */
    boolean rollback(BusinessActionContext actionContext);
}

@LocalTCC
public interface TccStorageService {
  
    /**
     * Try： 库存 - 扣减数量， 冻结库存 + 扣减数量
     */
    @TwoPhaseBusinessAction(name = "deduct" ,commitMethod = "commit" ,rollbackMethod = "rollback",useTCCFence = true)
    void tryDeduct(@BusinessActionContextParameter(paramName = "commodityCode") String commodityCode,
                @BusinessActionContextParameter(paramName = "count") int count);

    /**
     * Confirm:冻结库存-扣减数量
     */
    boolean commit(BusinessActionContext actionContext);


    /**
     *
     * CanceL:库存+扣减数量，冻结库存-扣减数量
     */
    boolean rollback(BusinessActionContext actionContext);


}
```

#### 8.6.5 微服务增加tcc_fence_log日志表

```
# tcc_fence_log 建表语句如下（MySQL 语法）
CREATE TABLE IF NOT EXISTS `tcc_fence_log`
(
    `xid`           VARCHAR(128)  NOT NULL COMMENT 'global id',
    `branch_id`     BIGINT        NOT NULL COMMENT 'branch id',
    `action_name`   VARCHAR(64)   NOT NULL COMMENT 'action name',
    `status`        TINYINT       NOT NULL COMMENT 'status(tried:1;committed:2;rollbacked:3;suspended:4)',
    `gmt_create`    DATETIME(3)   NOT NULL COMMENT 'create time',
    `gmt_modified`  DATETIME(3)   NOT NULL COMMENT 'update time',
    PRIMARY KEY (`xid`, `branch_id`),
    KEY `idx_gmt_modified` (`gmt_modified`),
    KEY `idx_status` (`status`)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4;
```

#### 8.6.6 在全局事务发起者中添加@GlobalTransactional注解

```
    //@Transactional
    @GlobalTransactional(name="createOrder")
    public Order saveOrder(OrderVo orderVo) {
        log.info("=============用户下单=================");
        log.info("当前 XID: {}", RootContext.getXID());

        //获取对应的唯一ID
        SnowFlake snowFlake = new SnowFlake(1, 2);
        Long orderId =snowFlake.nextId();
        Order order = tccOrderService.prepareSaveOrder(orderVo,orderId);
        //扣减余额   服务降级  throw
        Boolean debit= accountFeignService.debit(orderVo.getUserId(), orderVo.getMoney());
        //扣减库存
        storageFeignService.deduct(orderVo.getCommodityCode(), orderVo.getCount());
        return order;
  
    }
```

#### 8.6.7 测试

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1676957237003/a7a4d00b152d4a67b2077232f9aec722.png)

## 9、Saga模式

### 9.1 基本机制

Saga模式是SEATA提供的长事务解决方案，在Saga模式中，业务流程中每个参与者都提交本地事务，当出现某一个参与者失败则补偿前面已经成功的参与者，一阶段正向服务和二阶段补偿服务（执行处理时候出错了，给一个修复的机会）都由业务开发实现。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1662350890004/e9a0ee8aab874f5ab1c3c73a5cb198b1.png)

Saga 模式下分布式事务通常是由事件驱动的，各个参与者之间是异步执行的，Saga 模式是一种长事务解决方案。

### 9.2 为什么需要Saga

之前我们学习的Seata分布式三种操作模型中所使用的的微服务全部可以根据开发者的需求进行修改，但是在一些特殊环境下，比如老系统，封闭的系统（无法修改，同时没有任何分布式事务引入），那么AT、XA、TCC模型将全部不能使用，为了解决这样的问题，才引用了Saga模型。

比如：事务参与者可能是其他公司的服务或者是遗留系统，无法改造，可以使用Saga模式。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1662350890004/6c8eebd3477d475c90d90986d81c2fad.png)

Saga模式是Seata提供的长事务解决方案，提供了**异构系统的事务统一处理模型**。在Saga模式中，所有的子业务都不在直接参与整体事务的处理（只负责本地事务的处理），而是全部交由了最终调用端来负责实现，而在进行总业务逻辑处理时，在某一个子业务出现问题时，则自动补偿全面已经成功的其他参与者，这样一阶段的正向服务调用和二阶段的服务补偿处理全部由总业务开发实现。

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1662350890004/73cb2a0f2bf24a75ab547822a3a052f9.png)

### 9.3 Saga状态机

目前Seata提供的Saga模式只能通过状态机引擎来实现，需要开发者手工的进行Saga业务流程绘制，并且将其转换为Json配置文件，而后在程序运行时，将依据子配置文件实现业务处理以及服务补偿处理，而要想进行Saga状态图的绘制，一般需要通过Saga状态机来实现。

基本原理：

- 通过状态图来定义服务调用的流程并生成json定义文件
- 状态图中一个节点可以调用一个服务，节点可以配置它的补偿节点
- 状态图 json 由状态机引擎驱动执行，当出现异常时状态引擎反向执行已成功节点对应的补偿节点将事务回滚
- 可以实现服务编排需求，支持单项选择、并发、子流程、参数转换、参数映射、服务执行状态判断、异常捕获等功能

![](D:\BaiduSyncdisk\课程升级\springalibaba\seata\img\demo_statelang.png)![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1662350890004/37bb1b759407450caf5a564e354db8e1.png)

### 9.4 Saga状态机的应用

官方提供了一个状态机设计器

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1662350890004/42ea02863f9a4345964121a0a224e015.png)

官方文档地址：https://seata.io/zh-cn/docs/user/saga.html

Seata Safa状态机可视化图形设计器使用地址：https://github.com/seata/seata/blob/develop/saga/seata-saga-statemachine-designer/README.zh-CN.md
