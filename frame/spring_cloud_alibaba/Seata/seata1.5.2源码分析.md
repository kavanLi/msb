#### 一 、Seata编译

## 1、进行编译

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/8ddc47b47f634a32b2b351d765dc9251.png)

# 二、启动源码

## 1、启动

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/025e939cce7a441a8bf30250da57a843.png)

更改 注册中心 和配置中心都是nacos

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1663843915058/b695fcf7cc7648bfb3deacd3e43ad6d4.png)

## 2、找入口

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/9b9c4bdde5464fde8c9b28da340c81fa.png)

从上面我们可以看出Seata1.5.2就是一个springboot项目，我们可以从自动装配的角度来看，我们看一下seata-spring-boot-starter.1.5.2.jar里面的spring.factories，里面有对应的自动装配类SeataAutoConfiguration

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1663843915058/c513ab214c514a9392ed67c47e1a4866.png)

在SeataAutoConfiguration我们找到对应注入的类GlobalTransactionScanner，通过名称我们应该推算出，他应该是对应@GlobalTransaction进行扫描，然后注入到容器

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/fbde787a5efa4b67a741bf64d882bf63.png)

我们看他的继承关系发现，他是一个后置处理器，并且他继承AbstractAutoProxyCreator 这里面应该是创建代理的，他创建代理类是不是在wrapIfNecessary这个方法里面创建的？，好那我们来看一下

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/2960b8ce35c84a1ea7f62a2222001d06.png)

### 2.1 创建代理类

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/56bb80cffca8485485c3e9ca29d6c02d.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/9c045b415f8d48778ba204514578cc9d.png)

### 2.2 初始化 关键组件 TM RM

实现接口InitializingBean我们需要看他的对应方法afterPropertiesSet，对TM RM进行初始化

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/d7f7c04f20234ec39948d19ce87aaaae.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/e9bbdc4c8e764162a65820c770a6706d.png)

TM 和RM的通信都是通过Netty通信

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/cbe41e09c733448aa2ac43c8711e0101.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/4bc6c32847ba4474b3ea9f733d2cfe55.png)

## 3、发送请求进如核心方法

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/5e5a355d27aa47258ce7620f0c7caa99.png)

调用的时候一定会调用到我们的拦截器GlobalTransactionalInterceptor.invoke方法

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/f31f7572c5cf4d6eac030067ea929b23.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/473b99924fe743359ce6671595241d9c.png)

处理全局事务

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/4eec2c3b41fd442f996435bdf54d3096.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/2342d4dc4faa41658df69aa05f916a4c.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/02ad80432b41419a9b44fb4622cbe9d1.png)

## 4、开启全局事务

### 4.1 客户端源码

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/09cc0d5c4cb645468060f824b611dc00.png)

开启事务这里的全局事务的默认超时时间是60s

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/2ebe94a85b3141aabc642409789b078a.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/39896bffb5d046f8a17b662db036a95e.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/211eb99027f9459ea3a48d7ef5321570.png)

**发送请求获取全局事务Id**

发送同步请求

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/a41b935c494a4118908b35777fc563e9.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/87b8f5492577430598b13dd5ea7b381f.png)

这里客户端是在GlobalTransactionScanner 实现了接口InitializingBean

### 4.2  服务端处理

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/f89de6f7fabe4a568292e0be0fa367ca.png)

服务端处理我们这里有个核心类DefaultCoordinator

协调者我们看一下接口就有事务的处理

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/d1ac3c3090a145a6b7e358abe0d5af0b.png)

开启全局事务：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/7211edb6c0004a5e9b6efe607d18d8c1.png)

持久话数据：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/e95f7f17bfb8431e941912883dcf523e.png)

- 获取全局事务Id

  ![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/ff69c09703db4712bd0fb6336a357f85.png)

  ![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/da9b95ee27994d09a76b30e9a26d2bc7.png)

  ![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/f0ce7b20159b476ba69eba45303766ba.png)
- 开启事务

  ![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/33faef326eb74e23bc4f00300d9399e2.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/0d6d6925e3ee487bb58d19e37a89b593.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/53888e730ff14bec9f94c52b74e75e90.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/68408a39dd8c429cb545e02eb425f320.png)

进行数据持久化   重要接口TransactionStoreManager

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/b6664aef03da4e228a3af7ecaa099259.png)

这里对应的持久化的策略

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/96ac9652074a4026a03b8325f5cec4c4.png)

核心新增更新等操作

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/44bf7be2af7e48619075ee5724e6b7f1.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/46507e9bf40842979d9fa84e10a934d4.png)

## 5、执行业务逻辑

### 5.1 客户端执行

本地事务提交：业务数据和undo.log落库

注册分支事务、增加对应的锁

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/5575f64461ac439cab387bced9f1d20a.png)

我们想我们在创建数据源的时候创建一个代理数据源    DataSourceProxy， 通过DataSourceProxy 我们可以获取代理链接ConnectionProxy，通过代理链接我们可以获取PreparedStatementProxy，那我们执行sql应该是在这里

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/948fdabf9e5b46b988a82901acaecd22.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/a314a139059b4c9d9a7b647b09bf3b62.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/91ba32b1e9174c71b8f5d2d9b2196461.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/83a486a345e54179bc7ff74c263fe15a.png)![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/01e2082cc4984c3e978414f158ca4da0.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/79276155fd5b42178e093eab3f3ee378.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/da19c3bbedbf4581bac6ffeb73d53e55.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/e3d61386de304f0bb9eb83222c18e4ba.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/cb66b36cdae2479d8eb8076d52f5ef38.png)

key1: 设置手动提交

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/dc73e53a71474c87ba22200d6cdff8f9.png)

key2: 形成前后置镜像，这里的镜像执行形成，并没有插入数据库

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/1494bcf3d6d24fd9b9579aad6849a16c.png)

key3:进行提交

这里面有commit和docommit，注册回滚等操作

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/1cfbec1e6e024e2b9ec0503511b2375d.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/e356d09d3dde4be4a381d0fdc5b500b4.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/f50701563754423ebc508883984a1d8a.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/7784d55f187c477fbd1ea0e8fcddefd0.png)

key1: 注册分支事务

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/41b334505ef0454787c34fe592732b57.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/6afe01402dd749d283d7abd8036a52f4.png)

key2:插入日志

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/5dbcf0942d4c4ea6a929030b02500a82.png)

插入日志

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/e67cd56c895b497087d89c1eabc3772f.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/424ed51bfeb24e9fb88cf6d353fe32f4.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/eacdb18a8f884135a17464b27c6a9a6e.png)

上述插入日志但是并没有提交

### 5.2 服务端源码

注册分支事务、和分支锁

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/e34d2d874cc7420d99ae9eb5feef799e.png)

注册分支事务入口DefaultCoordinator

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/2e025d8a99f44c80a5afc60c3f101ca2.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/24d19fd3d28f4efbbcace6e40f930d1f.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/e1080af40eaf4681ab01a322adbcaca9.png)

#### 5.2.1获取全局事务

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/2a30bcfaa6044bc98554f078e2d31800.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/31b0111189014e8ca80964a119b3132e.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/2cd9d4e3b77149ff970f1daf8d21cc52.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/5f1cbfd1f7774edda8e9736ef7a02ffe.png)

#### 5.2.2、创建BranchSession

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/62a2a8fac1614ad0b27e0fbf83bc04f9.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/eb3834b492204987a49a351fa610f229.png)

#### **5.2.3、增加分支事务锁**,防止数据被修改

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/69bfba8b04bd4d03a70314b24e8e91db.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/3668bce7b2f14efb9db648dfebf9a909.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/338147f6602e452197b2ba3dd40e5ebd.png)

行锁收集，为什么行锁是list，因为update的时候可能是多行

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/039968aab8e74330b86a9aefb098d954.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/db25864b8b32478e8e4e22c0a97bf6a2.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/d17794c235fe4033968fbef98365379f.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/8e199dc126c64976a40c7673fd7fad91.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/ed79518955834d5d8f92858e244d9eba.png)

```
img
```

#### **5.2.4、向全局session中添加分支session**

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/0d966f86ee974bb5879c3a693043feb8.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/658195094a2049cbbd9f888b0233a09f.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/0c07616187a443999b00c532e88b9869.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/263f39f6e1fc4eadb528be548397348f.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/5a30f33c86c94bf8b865ad1431cf4f4b.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/88c8c32d5c0347918b8a2594494d6b9b.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/a6f10986f4124dd6a2b0365fe99b3bbf.png)

#### **5.2.5、出现异常释放锁**

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/9a4b590104874897a0b76c0781da720d.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/d29e2b6b6cd6451f852af0997f1b946f.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/fc0bd10c208544ee9c4448fefba45ddc.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/384b59557e054cd39e6edb12552f905a.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/b8088564d53248e597ca0bd70d4e3df9.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/f281e8732cca4c2ebe56744b61e3bde3.png)

#### **5.2.6: 返回分支会话的分支ID**

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/ace9c9f719c64c7b8eae920f54d8fac7.png)

## 6、服务间传递xid

### 6.1 Feign 传递xid

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/6e6bc9792b6f4e43b00ef2ae6c29d47d.png)

**执行方法时候获取Request**

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/49ff9c8863074f02a8abeb6c8f9c1834.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/0c6e38d4002b4f6caeccadf6e622b452.png)

### 6.2 **RestTemplate进行调用**

**ClientHttpRequestInterceptor**

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/0837d4163ef64ebe9fd0cfe1458e4f74.png)

## 7、提交事务

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/c74e2aa8e5ef421e8ed959e6e1fc041a.png)

### 7.1 客户端

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/0844d95459a2469e98f71cf94fb57829.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/af6f642b1f6644ff8c6c5c2228b65ae7.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/61bd5edfd59c412e8bad9c25fc46fc70.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/dde0969201b04e3c8363888c4ab81595.png)

### 7.2 服务端

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/d40ef4756369456393ca995b60098355.png)

#### 7.2.1 事务提交将事务改为异步提交中状态

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/78ac19c94f5d457591ec51124dd67467.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/149d96bf21eb4dac93572689f87cc816.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/057f850ae8c4402489355a5e0aa2634d.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/614d14d6e68844eab72a5ec3c5e70c8e.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/afd775d4929d437dad390c92d21a8356.png)

#### 7.2.2 定时任务处理异步提交的全局事务

定时任务处理对应的提交数据，这里每一秒执行一次

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/dd6fb8aa56034518a27a867445c433a4.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/aafb8d8f3fbe4e2ea03fcd265c8046b1.png)

获取全局事务的分支事务，删除undolog

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/14b5fd526ed14bccb0e4048ac76da024.png)

根据删除的分支事务的undolog的状态来处理，处理分支事务的数据和分支事务锁

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/4475fa791b994f07856d7fe98eefc184.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/3821a38868f44e18a602416c27e299bb.png)

重点我们分析一下key4: 删除分支事务释放全局锁

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/da93eb4056874d46a3457f9a2cdf1681.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/d0ee10b31e244770937f29c59a2a955d.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/e9c4506064394ba49b54732ee0b2aa95.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/17dbb8b7dfd74698b6d7b788a8098d60.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/f3a15e83a63447c1babeb539908137e8.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/d67675f8fd7c446db1d0c7159fda0088.png)

删除全局事务

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/f9a24c4ee4ce466dbc5ff18f7d3416ae.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/0268bc0bf1d546e1bfd2f9d009b97754.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/4329b570d8bc4901bc39f4f0e97d8283.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/b679896334bb4c3789796e25f3d9f124.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/bf45f6aa511d436abc7496ef3840732b.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/b9608bdbbf0641178ea4d18650fdd83b.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/fb572d67e7d6477580f73ca31e057426.png)

#### 7.2.3  分支事务客户端删除undolog

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/5783086d392f472e83d6384d7432b584.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/336acbd562d84819a339e795248c73a8.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/a6d2ac3a909d4a9c89fd3d7e9cbddd2a.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/7a23b8888b9c44e0a7dfb0ba9cd997a5.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/f9973e36098f4d778d5b5463d16621a9.png)

异步处理

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/0ae3012bfa484788a75db4b03040749e.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/35daccd5db354cfeacdb7583e412fb30.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/1bfa55c052f24e7eb4155790934b032f.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/caa95cc6c56745aaacc14b4ab70f592e.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/241181c34b8a467c9483064946df9f83.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/bb48ab9e8f3941809e0f0c275b89ccfb.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/ccbc0a5694044d1aa4608bd68d6a6b63.png)

## 8、事务回滚

### 8.1 客户端

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/d2795d7b3fc048beb11ff333f666963b.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/19a001616e2a425eb34c1ab1732c795b.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/02cc02d0eadb408a87a0261f41db18a1.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/6fc81ce8e1eb4276a13b254c5e1f1661.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/75ed6d14c7ef4892a83e7ec3bb1d4ea5.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/bf4d0bc4930a4243a9017d4a2ce41d0e.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/992acc38062049b2ac6c2e7d2076d03a.png)

### 8.2 服务端

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/4e00a9ebb4d141acb4083cea09fe5620.png)

客户端发送的请求是GlobalRollbackReqeust,那我们再服务端进行全文搜索

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/c9145c61a54a41e1a1a1adaca3ad21af.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/66b3c60e3e2e4db5876085205da42755.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/c991770cb88c4c9ab68100cd2e5be898.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/1c431199d1c64c7d951d218776b542e2.png)

key1:事务回滚，先通过全局事xid找到全局事务和分支事务
1.先通过全局事务XID找到去global_table查询到全局事务对象
2.通过查询出来的xid对象去查询branch_talbe是否有分支事务，最后将这两个事务对象封装到GlobalSession中
这里传入的true代表全局事务和分支事务都查询出来，全局事务是一条数据，分支事务可能会多条是一个list

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/5740a3e3535b40de8710ef654057dbb1.png)![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/c8f2cf8c73dd48f98b63380d5dbc734a.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/6febae6f3f7d457eba72623a12fa53fd.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/2dea7718440f4925b648a4b800d21781.png)

key2：真正的回滚逻辑

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/8288d0022d364696837bc7c3ad1d9a90.png)

如果分支事务是在一阶段失败的，调用removeBranch，释放锁

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/00c5bf94188d442aabf95986690debc3.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/c0d606b17c2a4add8376822bb08afd3c.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/3559846e51ec4918bc09a6c8a4d63562.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/23c8a06c803a408d98b5ef8275f8552b.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/d12b4c9dbea2493e8786cc64671ce843.png)

branchRollback回滚分支事务，这里是server端，所以这里的分支事务的回滚是调用的远程进行回滚的而远程回滚就是使用的undo log日志表来回滚的

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/2059100c07cf4d2fa1fc4e443c48d634.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/bdbb56f34e9549dca4ecfb0df02bdd5b.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/241cde4bc0d347a2b3fe9a3818b29aef.png)、、

根据调用客户端删除undolog的返回状态，来判断怎样处理

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/1cc042a8ea394d6bbf6a90912eeebec6.png)

最后定时任务删除回滚对应的全局事务

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/f3b88e7e8d14480484d75592e8953d1b.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/b92b29a88c1543548ebeb1203e6b45b5.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/fdd957c7b9c54bb3b6439e5c87d29573.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/a03003cd416b44a9bab15e0b0c00022d.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/1f901c135d01460baca4648bd166fb6c.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/e2abcaa58b16487c902219c010102dba.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/2f7b050682924b3c9f2744c7436a23ab.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/f9d537c5ecd84d40b93bac23b00cf248.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/c07d98ad68804bf7a78507096857af00.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/1ee36be81bff4ffd9337e3707b0053bd.png)

### 8.3 客户端进行回滚

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/cdc8779c6bd54499bc6fac4a01e8ec78.png)

上面我们知道回滚我们发送请求为BranchRollbackRequest，那我们来到客户端端来全文搜索

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/14023be61f234c248ddb8d065ea0fcd7.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/7b54b3891c274e7589eb506f563aa4c3.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/57d3d2c472e241ac8992defbabeb4f0a.png)

在这里的undo方法中，会生成回滚sql，执行，然后删除undolog日志

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/27ba930a3f73434ab0f408f4ead46a47.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/82d87ac6922540c7b1dbd399849abf79.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/a3642af6823b4a92bf0149d70add9509.png)

## 9、查看回滚日志信息

navicat中查看中选择文本

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/9e6509f3678a40959155293fcb7f218c.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/15647/1677329743057/bae06f0441fa44a0860ef870881f569e.png)
