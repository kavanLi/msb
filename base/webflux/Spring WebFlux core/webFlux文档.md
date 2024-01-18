# 一、 **用操作符转换响应式流**

### 1 、 映射响应式流元素

​		转换序列的最自然方式是将每个元素映射到一个新值。

​		Flux 和 Mono 给出了 map 操作符，具有 map(Function<T，R>) 签名的方法可用于逐个处理元素。

​		当操作符将元素的类型从 T 转变为 R 时，整个序列的类型将改变。

​		Flux.map() 的弹珠图

![image-20221028202804712](D:\BaiduSyncdisk\课程升级\webFlux\img\image-20221028202804712.png)





​		

index 操作符可用于枚举序列中的元素。该方法具有以下签名： Flux<Tuple2<Long,T >> index() 。

![image-20221028204500342](D:\BaiduSyncdisk\课程升级\webFlux\img\image-20221028204500342.png)



timestamp 操作符的行为与 index 操作符类似，但会添加当前时间戳而不是索引。

![image-20221028204304603](D:\BaiduSyncdisk\课程升级\webFlux\img\image-20221028204304603.png)





### 2、 过滤响应式流



Project Reactor 包含用于过滤元素的各种操作符。

1. filter 操作符仅传递满足条件的元素。
2. take(n) 操作符限制所获取的元素，该方法忽略除前 *n* 个元素之外的所有元素。
3. takeLast 仅返回流的最后一个元素。
4. takeUntil(Predicate) 传递一个元素直到满足某个条件。

5. elementAt(n) 只可用于获取序列的第 *n* 个元素。
6. takeUntilOther(Publisher) 或 skipUntilOther(Publisher) 操作符，可以跳过或获取一个元素，直到某些消息从另一个流到达。

​		考虑如下工作流程，该工作流中，首先开始一个流的处理，然后从其他流收到特定事件之后，停止该流的处理。







![image-20221028210428159](D:\BaiduSyncdisk\课程升级\webFlux\img\image-20221028210428159.png)











### 3、  收集响应式流

- 收集到List
- 使用 collectMap 操作符的映射（ Map<K,T> ）；
- 使用 collectMultimap 操作符的多映射（ Map<K，Collection<T>> ）；
- Flux.collect(Collector) 操作符收集到任何实现了 java.util.stream.Collector 的数据结构。
- Flux 和 Mono 都有 repeat() 方法和 repeat(times) 方法，这两种方法可以针对传入序列进=行循环操作。
- defaultIfEmpty(T) 是另一个简洁的方法，它能为空的 Flux 或 Mono 提供默认值。
- Flux.distinct() 仅传递之前未在流中遇到过的元素。
- Flux.distinctUntilChanged() 操作符没有此限制，可用于无限流以删除出现在不间断行中的重复项。

#### 3.1 收集到List

```java
Flux.just(1,2,36,4,25,6,7)
        //CollectionSoredList 默认是升序
        .collectSortedList(Comparator.reverseOrder())
        .subscribe(System.out::println);
```

> 请注意，收集集合中的序列元素可能耗费资源，当序列具有许多元素时这种现象尤为突出。此外，尝试在无限流上收集数据可能消耗所有可用的内存。

#### 3.2 使用collectMap

```java
Flux.just(1,2,3,4,5,6)
        .collectMap(item -> "key:num" + item)
        .subscribe(System.out::println);
Flux.just(1,2,3,4,5,6)
        .collectMap(
                item -> "key:" + item,
                item -> "value:" + item
        ).subscribe(System.out::println);

Flux.just(1,2,3,4,5,6)
        .collectMap(
                integer -> "key:" + integer,
                integer -> "value:" + integer,
                ()->{
                    Map<String,String> map = new HashMap<>();
                    for(int i =7 ;i <10;i++){
                        map.put("key:" + i ,"value:" + i);
                    }
                    return map;

                }
        ).subscribe(System.out::println);
```

#### 3.3 CollectionMultimap 使用

```java
Flux.just(1,2,3,4,5)
                .collectMultimap(
                        item -> "key:" + item,
                        item ->{
                            List<String> values = new ArrayList<>();
                            for(int i =0 ;i < item ;i ++){
                                values.add("value:" + i);
                            }
                            return values;
                        }
                ).subscribe(System.out::println);
Flux.just(1,2,3,4,5)
    .collectMultimap(
    item -> "key:" + item,
    item ->{
        List<String> values = new ArrayList<>();
        for(int i =0 ;i < item ;i ++){
            values.add("value:" + i);
        }
        return values;
    },// 扩充
    () ->{
        Map map = new HashMap<String,List>();
        List<String> list = new ArrayList<>();
        for(int i = 0 ;i <3;i ++){
            list.clear();
            for(int j = 0 ;j <i;j++){
                list.add("ele:" +j);
            }
            map.put(i + ":key",list);
        }
        return map;
    }
).subscribe(System.out::println);
```

#### 3.4  repeat 操作符的使用：

```java
Flux.just(1,2,3)
        .repeat(3)// 实际上是打印四次，一次原始的，三次重复的
        .subscribe(System.out::println);
```

#### 3.5 defaultIfEmpty 操作符的使用：

```java
Flux.empty().defaultIfEmpty("hello msb").subscribe(System.out::println);
```

#### 3.6 distinct 操作符的使用：

```java
Flux.just(1,2,3)
        .repeat(3)
        .distinct()
        .subscribe(System.out::println);
```

#### 3.7 distinctUntilChanged 操作符的使用：

```java
 Flux.just(1,1,1,2,2,2,3,3,3,1,1,1,2,2,2)
                .distinctUntilChanged()
                .subscribe(System.out::print);
        System.out.println();
        System.out.println("===============");
        Flux.just(1,1,1,2,2,2,3,3,3,1,1,1,2,2,2)
                .distinct()
                .subscribe(System.out::print);
```









![image-20221028215408341](D:\BaiduSyncdisk\课程升级\webFlux\img\image-20221028215408341.png)



### 4、裁剪流中元素



Project Reactor可以：

1. 统计流中元素的数量；
2. 检查所有元素是否具有 Flux.all(Predicate) 所需的属性；
3. 使用 Flux.any(Predicate) 操作符检查是否至少有一个元素具有所需属性；
4. 使用 hasElements 操作符检查流中是否包含多个元素；
5. 使用 hasElement 操作符检查流中是否包含某个所需的元素。短路逻辑，在元素与值匹配时立即返回true。
6. any 操作符不仅可以检查元素的相等性，还可以通过提供自定义 Predicate 实例来检查任何其他属性。

检查序列中是否包含偶数：

```java
Flux.just(1,2,3,4,5,6)
        .any(item -> item % 2 == 0)
        .subscribe(System.out::println);
```

我们可以检查一下他的整个运行过程： 增加副作用

```java
Flux.just(1,2,3,4,5,6)
        .doOnNext(item -> System.out.println(item))
        .any(item -> item % 2 == 0)
        .subscribe(System.out::println);
```



​		Flux 类能使用自定义逻辑来裁剪序列（也称为折叠）。 reduce 操作符通常需要一个初始值和一个函数，而该函数会将前一步的结果与当前步的元素组合在一起。

将 1 到 5 之间的整数加起来：

```java
Flux.range(1,5)
        .reduce(0,(item1,item2)->{
            System.out.println("item1:" + item1);
            System.out.println("item2:" + item2);
            return item1 + item2;
        }).subscribe(System.out::println);
```



Flux.scan()操作符在进行聚合时，可以向下游发送中间结果。

scan 操作符对 1 到 5 之间的整数求和：

```java
Flux.range(1,5)
        .scan(0,(num1,num2) ->{
            System.out.println("num1:" + num1);
            System.out.println("num2:" + num2);
            return num1 + num2;
        })
        .subscribe(System.out::println);
```

运行结果如下：他是将中间结果输出出来

![image-20221030151955296](D:\BaiduSyncdisk\课程升级\webFlux\img\image-20221030151955296.png)

scan 操作符对于许多需要获取处理中事件的相关信息的应用程序有用

例如，我们可以计算流上的移动平均值：

```java
int arrLength = 5;
Flux.just(1,2,3,4,5,6)
        .index()
        .scan(new int[arrLength],(arr,entry) ->{
            arr[(int) (entry.getT1() % arrLength)] = entry.getT2();
            return arr;
}).skip(arrLength)// 当窗口数组背灌满之后，开始计算平均值，因此要跳过没有灌满的情况
        .map(array -> Arrays.stream(array).sum() * 1.0 /arrLength)
        .subscribe(System.out::println);
```

Mono 和 Flux 流有 then、thenMany 和 thenEmpty 操作符，它们在上游流完成时完成。

上游流完成处理后，这些操作符可用于触发新流，订阅是对于新流的。

```
Flux.just(1,2,3,4)
        .doOnNext(item -> System.out.println("副作用：" + item
        ))
        .thenMany(Flux.just(5,6,7))
        .subscribe(System.out::println);
```





即使 1、2 和 3 是由流生成和处理的，subscribe 方法中的 lambda 也只接收 4 和 5。

### 5、 组合响应式流

Project Reactor 可以将许多传入流组合成一个传出流。

指定的操作符虽然有许多重载方法，但是都会执行以下转换。

1. concat 操作符通过向下游转发接收的元素来连接所有数据源。当操作符连接两个流时，它首先消费并重新发送第一个流的所有元素，然后对第二个流执行相同的操作。

2. merge 操作符将来自上游序列的数据合并到一个下游序列中。与 concat 操作符不同，上游数据源是立即（**同时**）被订阅的。

3. zip 操作符订阅所有上游，等待所有数据源发出一个元素，然后将接收到的元素组合到一个输出元素中。

4. combineLatest 操作符与 zip 操作符的工作方式类似。但是，只要至少一个上游数据源发出一个值，它就会生成一个新值。

concat 操作符的使用：

```java
// 流是按顺序定于，按顺序处理
Flux.concat(
        Flux.range(10,5).delayElements(Duration.ofMillis(100))
        .doOnSubscribe(subscription -> System.out.println("订阅第一个流")),
        Flux.range(100,5).delayElements(Duration.ofMillis(100))
        .doOnSubscribe(subscription -> System.out.println("订阅第二流"))
).subscribe(System.out::println);
Thread.sleep(10*1000);
```

merge 操作符的使用：

```
Flux.merge(
        Flux.range(10,5).delayElements(Duration.ofMillis(100))
                .doOnSubscribe(subscription -> System.out.println("订阅第一个流")),
        Flux.range(100,5).delayElements(Duration.ofMillis(100))
                .doOnSubscribe(subscription -> System.out.println("订阅第二流"))
).subscribe(System.out::println);
Thread.sleep(10*1000);
```

zip 操作符的使用：  

```java
// zip
//这里最后形成的是一个二元组
// 这里面依照最慢的那个进行打印，如果我们两个流的打印个数不同，那就以最少的为准
Flux.zip(
        Flux.range(1,10)
        .delayElements(Duration.ofMillis(10)),
        Flux.range(100,10)
        .delayElements(Duration.ofMillis(10))
        ).subscribe(System.out::println);
        Thread.sleep(10*1000);
```

combineLatest 操作符的使用：

```java
Flux.combineLatest(
        Flux.range(1,10)
        .delayElements(Duration.ofMillis(1000)),
        Flux.range(100,10)
        .delayElements(Duration.ofMillis(2000)),
        ((integer1, integer2) -> integer1 + "==" +integer2)
).subscribe(System.out::println);
Thread.sleep(10*1000);
```

![image-20221029151301741](D:\BaiduSyncdisk\课程升级\webFlux\img\image-20221029151301741.png)

### 6、流元素批处理

Project Reactor 支持以以下几种方式对流元素（ Flux<T> ）执行批处理。

1. 将元素缓冲（buffering）到容器（如 List）中，结果流的类型为 Flux<List<T>> 。

2. 通过开窗（windowing）方式将元素加入诸如 Flux<Flux<T>> 等流中。请注意，现在的流信号不是值，而是可以处理的子流。

3. 通过某些键将元素分组（grouping）到具有 Flux<GroupedFlux<K, T>> 类型的流中。每个新键都会触发一个新的 GroupedFlux 实例，并且具有该键的所有元素都将被推送到GroupFlux 类的该实例中。

可以基于以下场景进行缓冲和开窗操作：

1. 处理元素的数量，比方说每 10 个元素；

2. 一段时间，比方说每 5 分钟一次；

3. 基于一些谓语，比方说在每个新的偶数之前切割；

4. 基于来自其他 Flux 的一个事件，该事件控制着执行过程。

如，为列表（大小为 5）中的整数元素执行缓冲操作：

```java
         Flux.range(1,100)
                .buffer(5)
                .subscribe(System.out::println);
```

​		buffer 操作符将许多事件收集到一个事件集合中。该集合本身成为下游操作符的事件。当需要使用元素集合来生成一些请求，而不是使用仅包含一个元素的集合来生成许多小请求时，用缓冲操作符来实现批处理会比较方便。

​		如，可以将数据项缓冲几秒钟然后批量插入，而不是逐个将元素插入数据库。

​		window 操作符：

​		如果需要根据数字序列中的元素是否为素数进行开窗拆分，可以使用 window 操作符的变体windowUntil。

​		它使用谓词来确定何时创建新切片。



```java
 Flux.range(101,20)
                .windowUntil(ReactorDemo12::isPrime,true)
                .subscribe(
                        window ->
                            window.collectList()
                                    .subscribe(
                                            item -> System.out.println("window :" + item)
                                    )

                )
        ;


private static boolean isPrime(Integer integer){
        double sqrt = Math.sqrt(integer);
        if(integer < 2){
            return false;
        }
        if(integer == 2 || integer == 3){
            return true;
        }
        if(integer % 2 == 0){
            return false;
        }
        for(int i = 3; i <= sqrt ;i++){
            if(integer % i == 0){
                return false;
            }
        }
        return true;

    }
```



请注意第一个窗口为空。这是因为一旦启动原始流，就会生成一个初始窗口。然后，第一个元素会到达（数字 101），它是素数，会触发一个新窗口。因此，已经打开的窗口会在没有任何元素的情况下通过 onComplete 信号关闭。





​		window操作符和buffer操作符类似，后者仅在缓冲区关闭时才会发出集合，而 window 操作符会在事件到达时立即对其进行传播，以更快地做出响应并实现更复杂的工作流程。

​		groupBy 操作符通过某些条件对响应式流中的元素进行分组。通过对每个元素打一个标签（key），按照标签将元素进行分组。

​		如：将整数序列按照奇数和偶数进行分组，并仅跟踪每组中的最后两个元素。

​		代码如下所示：

```java

```



运行结果：



![image-20221029195635085](D:\BaiduSyncdisk\课程升级\webFlux\img\image-20221029195635085.png)

此外，Project Reactor 库支持一些高级技术，例如在不同的时间窗口上对发出的元素进行分组。

### 7、**flatMap**、**concatMap** 和 **flatMapSequential** 操作符

​		flatMap 操作符在逻辑上由 map 和 flatten（就 Reactor 而言，flatten 类似于 merge 操作符）这两个操作组成。

​		flatMap 操作符的 map 部分将传入的**每个元素转换为响应式流**（T -> Flux<R>）;

​		flatten 部分将所有生成的响应式流**合并**为一个新的响应式流，通过该流可以传递 R 类型的元素。

​		Project Reactor 提供了 flatMap 操作符的一些不同变体。除了重载，该库还提供了flatMapSequential 操作符和 concatMap 操作符。

这 3 个操作符在以下几个方面有所不同。

1. 操作符是否立即订阅其内部流；flatMap 操作符和 flatMapSequential 操作符会立即订阅，而 concatMap 操作符则会在生成下一个子流并订阅它之前等待每个内部完成。

2. 操作符是否保留生成元素的顺序；concatMap 天生保留与源元素相同的顺序，flatMapSequential 操作符通过对所接收的元素进行排序来保留顺序，而 flatMap 操作符不一定保留原始排序。

3. 操作符是否允许对来自不同子流的元素进行交错；

   flatMap 操作符允许交错，而 concatMap和 flatMapSequential 不允许交错。

​		flatMap 操作符（及其变体）在函数式编程和响应式编程中都非常重要，因为它能使用一行代码实现复杂的工作流。

flatMap允许元素交错

flatMap 操作符

```java
Random random = new Random();
Flux.just(Arrays.asList(1,2,3),Arrays.asList("a","b","c","d"),Arrays.asList(7,8,9))
        .doOnNext(System.out::println)
        .flatMap(item -> Flux.fromIterable(item)
        .doOnSubscribe(subscription -> {
            System.out.println("已经订阅");
        })// 我们增加一个延时，订阅后延时一段时间再发送
        .delayElements(Duration.ofMillis(random.nextInt(100) + 100))
        ).subscribe(System.out::println);

Thread.sleep(10*1000);
```

​	flatMap 弹珠图：

![image-20221029200053070](D:\BaiduSyncdisk\课程升级\webFlux\img\image-20221029200053070.png)



​			concatMap不允许元素交错。

​			concatMap 操作符的使用：

```java
Random random = new Random();
Flux.just(Arrays.asList(1,2,3),Arrays.asList("a","b","c","d"),Arrays.asList(7,8,9))
    .doOnNext(System.out::println)
    .concatMap(item -> Flux.fromIterable(item)
               .doOnSubscribe(subscription -> {
                   System.out.println("已经订阅");
               })// 我们增加一个延时，订阅后延时一段时间再发送
               .delayElements(Duration.ofMillis(random.nextInt(100) + 100))
              ).subscribe(System.out::println);

Thread.sleep(10*1000);
```

**concatMap对每个上游的元素，在接收后都立即生成新的流，新流每个元素处理完之后，进行下一个新流的处理。**

![image-20221029200152289](D:\BaiduSyncdisk\课程升级\webFlux\img\image-20221029200152289.png)

flatMapSequential不允许元素交错。

flatMapSequential 操作符的使用：

```JAVA
 Random random = new Random();
        Flux.just(Arrays.asList(1,2,3),Arrays.asList("a","b","c","d"),Arrays.asList(7,8,9))
                .doOnNext(System.out::println)
                .flatMapSequential(item -> Flux.fromIterable(item)
                        .doOnSubscribe(subscription -> {
                            System.out.println("已经订阅");
                        })// 我们增加一个延时，订阅后延时一段时间再发送
                        .delayElements(Duration.ofMillis(random.nextInt(100) + 100))
                ).subscribe(System.out::println);

        Thread.sleep(10*1000);
```

![image-20221029200248720](D:\BaiduSyncdisk\课程升级\webFlux\img\image-20221029200248720.png)

### 8、元素采样

​		对于高吞吐量场景而言，通过应用采样技术处理一小部分事件是有意义的。

​		sample 操作符和 sampleTimeout 操作符可以让流周期性地发出与时间窗口内最近看到的值相对应的数据项。

​		我们假设使用以下代码：

```JAVA
 // 每个100ms从流中获取对应的元素
        Flux.range(1,100)
                .delayElements(Duration.ofMillis(10))
                .sample(Duration.ofMillis(200))
                .subscribe(System.out::println);
        Thread.sleep(10*1000);
```

​		使我们每10毫秒都顺序生成数据项，订阅者也只会收到所指定的约束条件内的一小部分事件。通过这种方法，我们可以在不需要所有传入事件就能成功操作的场景下使用被动限速。流控。

sample弹珠图：

![image-20221031142052503](D:\BaiduSyncdisk\课程升级\webFlux\img\image-20221031142052503.png)



sampleTimeout 操作符：

```java
Random random = new Random();
Flux.range(0,20)
    .delayElements(Duration.ofMillis(100))
    .sampleTimeout( item -> Mono.delay(Duration.ofMillis(random.nextInt(100) + 50)),20)
    .subscribe(System.out::println);
Thread.sleep(10 * 1000);
```

流控。

![image-20221029200527147](D:\BaiduSyncdisk\课程升级\webFlux\img\image-20221029200527147.png)



### 9、 将响应式流转化为阻塞结构

Project Reactor 库提供了一个 API，用于将响应式流转换为阻塞结构。

有以下选项来阻塞流并同步生成结果：

1. toIterable 方法将响应式 Flux 转换为阻塞 Iterable。

2. toStream 方法将响应式 Flux 转换为阻塞 Stream API。从 Reactor 3.2 开始，在底层使用toIterable 方法。

3. blockFirst 方法阻塞了当前线程，直到上游发出第一个值或完成流为止。

4. blockLast 方法阻塞当前线程，直到上游发出最后一个值或完成流为止。在 onError的情况下，它会在被阻塞的线程中抛出异常。

​		blockFirst 操作符和 blockLast 操作符具有方法重载，可用于设置线程阻塞的持续时间。这应该可以防止线程被无限阻塞。

​		

```java
 Flux.just(1,2,3).toIterable();
        Stream<Integer> stream = Flux.just(1,2,3,4).toStream();
        // 1、验证toIterable为阻塞
        Iterable<Integer> integers = Flux.just(1, 2, 3, 4)
                .delayElements(Duration.ofSeconds(1))
                .toIterable();
        System.out.println("===============");
        for(Integer num :integers){
            System.out.println(num);
        }
        System.out.println("===============");
        //  2、我么可以做一下改进
        Flux.just(1,2,3)
                .delayElements(Duration.ofSeconds(1))
                .subscribe(System.out::println);
        System.out.println("==========");
        System.out.println("==========");
        Thread.sleep(10*1000);

        // 3、toStream进行阻塞
        Stream<Integer> integerStream = Flux.just(1, 2, 3).delayElements(Duration.ofSeconds(1))
                .toStream();
        System.out.println("================");
        integerStream.forEach(System.out::println);
        System.out.println("================");
        // 4 、BlockFirst 只拿第一个，其他不处理
        Integer integer = Flux.just(1, 2, 3)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(item -> System.out.println("onNext:" + item))
                .blockFirst();
        System.out.println("==========");
        System.out.println(integer);
        System.out.println("==========");
        Thread.sleep(10*100);

        //  blocklast 直到流的最后一个元素
        Integer integer2 = Flux.just(1, 2, 3)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(item -> System.out.println("onNext:" + item))
                .blockLast();
        System.out.println("==========");
        System.out.println(integer2);
        System.out.println("==========");

        Flux<Integer> integerFlux = Flux.just(1, 2, 3).delayElements(Duration.ofSeconds(1));
        integerFlux.subscribe(item -> System.out.println("第一个订阅" + item));
        integerFlux.subscribe(item -> System.out.println("第二个订阅" + item));
        integerFlux.subscribe(item -> System.out.println("第三个订阅" + item));
        Integer integer2 = integerFlux.blockFirst();
        System.out.println("阻塞最后一个元素：" + integer2);
        System.out.println("=================");
        Thread.sleep(10*1000);
```

### 10、在序列处理时查看元素

​		有时，我们需要对处理管道中的每个元素或特定信号执行操作。为满足此类要求，Project Reactor提供了以下方法。

1. doOnNext(Consumer<T>) 使我们能对 Flux 或 Mono 上的每个元素执行一些操作。

2. doOnComplete 和 doOnError(Throwable) 可以应用在相应的事件上。

3. doOnSubscribe(Consumer<Subscription>) 和doOnCancel(Runnable) 使我们能对订阅生命周期事件做出响应。

4. 无论是什么原因导致的流终止， doOnTerminate(Runnable) 都会在流终止时被调用。

​		此外，Flux 和 Mono 提供了 doOnEach(Consumer<Signal>) 方法，该方法处理表示响应式流领域的所有信号，包括 onSubscribe、onNext、onError 和 onComplete

考虑以下代码：

```java
Flux.just(1,2,3)
    .concatWith(Flux.error(new RuntimeException("手动异常")))
    //  .doOnEach(item -> System.out.println(item))
    .subscribe(
    item -> System.out.println("onNext:" + item),
    ex -> System.err.println("onError:" + ex),
    () -> System.out.println("处理完毕")
);
```

在这个例子中，我们不仅收到了所有的 onNext 信号，还收到了 onError 信号。

### 11、物化和非物化信号

​		将流中的元素封装为Signal对象进行处理。

​		有时，采用信号进行流处理比采用数据进行流处理更有用。为了将数据流转换为信号流并再次返回，Flux 和 Mono 提供了 materialize 方法和 dematerialize 方法。示例如下：

```java
  Flux.just(1,2,3)
                .delayElements(Duration.ofMillis(1000))
                .publishOn(Schedulers.parallel())
                .concatWith(Flux.error(new Exception("手动异常")))
                .materialize()
                .doOnEach(item -> System.out.println(item.isOnComplete()))
                .log()
                .dematerialize()
                .subscribe(System.out::println);
        Thread.sleep(10*1000);
```

![image-20221029201200612](D:\BaiduSyncdisk\课程升级\webFlux\img\image-20221029201200612.png)

​		这里，在处理信号流时，doOnNext 方法不仅接收带有数据的 onNext 事件，还接收包含在Signal类中的 onComplete 事件。此方法能采用一个类型层次结构来处理 onNext、onError和 onCompete 事件。

​		如果我们只需要记录信号而不修改它们，那么 Reactor 提供了 log 方法，该方法使用可用的记录器记录所有处理过的信号。

# 二、**以编程方式创建流**

有时候需要一种更复杂的方法来在**流中生成信号**，或将对象的生命周期绑定到响应式流的生命周期。

## 1.1 push 和 create 工厂方法

​		push 工厂方法能通过适配**一个单线程生产者**来编程创建 Flux 实例。

​		此方法对于适配异步、**单线程**、多值 API 非常有用，而无须关注背压和取消， push 方法本身包含背压和取消。

```java
 // push
Flux<Integer> push = Flux.push(new Consumer<FluxSink<Integer>>() {
    @Override
    public void accept(FluxSink<Integer> fluxSink) {
        // 从数据库中获取数据
        // FluxSink 追加到响应式流中，这样将命令处理方式，转化为响应式处理方式
        IntStream.range(1, 10)
            .forEach(item -> fluxSink.next(item));
    }
});
push.subscribe(System.out::println);
Thread.sleep(5*1000);
```

我们可以转化为lambda表达式如下：

```java
Flux<Integer> push = Flux.push(fluxSink -> {
    // 从数据库中获取数据
    // FluxSink 追加到响应式流中，这样将命令处理方式，转化为响应式处理方式
    IntStream.range(1, 10)
            .forEach(item -> fluxSink.next(item));
});
push.subscribe(System.out::println);
Thread.sleep(5*1000);
```

 

push 工厂方法可以很方便地使用默认的背压和取消策略来适配异步 API。

create 工厂方法，与 push 工厂方法类似，起到桥接的作用。

该方法**能从不同的线程发送事件**。

如下代码所示：

```java
 MyEventProcessor myEventProcessor = new MyEventProcessor();
        Flux<String> bridage = Flux.create(sink -> {
            myEventProcessor.register(new MyEventListener<String>() {
                @Override
                public void onDataChunk(List<String> chunk) {
                    for (String s : chunk) {
                        sink.next(s);
                    }
                }

                @Override
                public void processComplete() {
                    sink.complete();
                }
            });
        });
        bridage.subscribe(
                System.out::println,
                ex -> System.err.println(ex),
                () ->System.out.println("处理完毕")
        );
        myEventProcessor.process();
        Thread.sleep(5*1000);
```

```java
static class MyEventProcessor{
    private MyEventListener listener;
    private Random random = new Random();
    void register(MyEventListener listener){
        this.listener = listener;
    }

    public void process(){
        while(random.nextInt(10) % 3 != 0){
            List<String> dataChunk = new ArrayList<>();
            for(int i = 0 ;i < 10;i++){
                dataChunk.add("data - " + i );
            }
            listener.onDataChunk(dataChunk);
        }
        listener.processComplete();;
    }
}
interface  MyEventListener<T>{
    void onDataChunk(List<T> chunk);

    void processComplete();
}
```

## 1.2 **generate** 工厂方法

​		generate 工厂方法旨在基于生成器的内部处理状态创建复杂序列。

​		它需要一个初始值和一个函数，该函数根据前一个内部状态计算下一个状态，并将 onNext 信号发送给下游订阅者。

​		例如，创建一个简单的响应式流来生成斐波那契（Fibonacci）数列（0,1，1，2，3，5，8，13，…）。

```java
Flux.generate(
        // 通过Callable提供初始状态实例
        new Callable<ArrayList<Long>>() {
            @Override
            public ArrayList<Long> call() throws Exception {
                final ArrayList<Long> longs = new ArrayList<>();
                longs.add(0L);
                longs.add(1L);
                return longs;
            }
        }, // 负责斐波拉契数列
        // 函数第一个参数数据、函数第二个参数类型 、返回值
        new BiFunction<ArrayList<Long>, SynchronousSink<Long>, ArrayList<Long>>() {
            @Override
            public ArrayList<Long> apply(ArrayList<Long> longs, SynchronousSink<Long> sink) {
                Long aLong = longs.get(longs.size() - 1);
                Long aLong1 = longs.get(longs.size() - 2);
                sink.next(aLong);
                longs.add(aLong + aLong1);
                return longs;
            }
        }).delayElements(Duration.ofMillis(500))
        .take(10)
        .subscribe(System.out::println);
Thread.sleep(5000);
```



我们使用lambda表达式处理：

```
Flux.generate(
        // 通过Callable提供初始状态实例
        () -> {
            final ArrayList<Long> longs = new ArrayList<>();
            longs.add(0L);
            longs.add(1L);
            return longs;
        }, // 负责斐波拉契数列
        // 函数第一个参数数据、函数第二个参数类型 、返回值
        (BiFunction<ArrayList<Long>, SynchronousSink<Long>, ArrayList<Long>>) (longs, sink) -> {
            Long aLong = longs.get(longs.size() - 1);
            Long aLong1 = longs.get(longs.size() - 2);
            sink.next(aLong);
            longs.add(aLong + aLong1);
            return longs;
        }).delayElements(Duration.ofMillis(500))
        .take(10)
        .subscribe(System.out::println);
Thread.sleep(5000);
```



状态还可以记录为二元组：

```java
Flux.generate(
        // 通过Callable提供初始状态实例
        () -> Tuples.of(0L,1L), // 负责斐波拉契数列
        // 函数第一个参数数据、函数第二个参数类型 、返回值
       (state, sink) -> {
           System.out.println("生成的数字：" + state.getT2());
           sink.next(state.getT1());
           long nextValue  = state.getT1() + state.getT2();
            return Tuples.of(state.getT2(),nextValue);
        }).delayElements(Duration.ofMillis(500))
        .take(10)
        .subscribe(System.out::println);
Thread.sleep(5000);
```

## 1.3 将 disposable 资源包装到响应式流中

using 工厂方法能根据一个 disposable 资源创建流。它在响应式编程中实现了 try-with-resources 方法。

假设我们需要包装一个阻塞 API，而该 API 使用以下有如下表示：

```java
static class Connection implements AutoCloseable {
    private final Random rnd = new Random();
    static Connection newConnection() {
        System.out.println("创建Connection对象");
        return new Connection();
    }
    public Iterable<String> getData() {
        if (rnd.nextInt(10) < 3) {
            throw new RuntimeException("通信异常");
        }
        return Arrays.asList("数据1", "数据2");
    }
    // close方法可以释放内部资源，并且应该始终被调用，即使在getData执行期间发生错误也是如此。
    @Override
    public void close() {
        System.out.println("关闭Connection连接");
    }
}
```

使用命令式方法，我们可以使用以下代码从连接接收数据：

```java
public static void main(String[] args) throws InterruptedException {
    try (Connection connection = Connection.newConnection()) {
        connection.getData().forEach(data -> System.out.println("接收的数据：" +
                data));
    } catch (Exception e) {
        System.err.println("错误信息：" + e);
    }
}
```



有相同作用的响应式代码如下所示

```java
Flux.using(
        Connection::newConnection,
        connection -> Flux.fromIterable(connection.getData()),
        Connection::close
).subscribe(
        data -> System.out.println("onNext接收到数据：" + data),
        ex -> System.err.println("onError接收到的异常信息：" +ex),
        () -> System.out.println("处理完毕")
);
```

连接的生命周期与流的生命周期绑定。

操作符还可以在通知订阅者流终止之前或之后选择是否应该进行清除动作。



#  三、**错误处理**

​		onError 信号是响应式流规范的一个组成部分，一种将异常传播给可以处理它的用户。但是，如果最终订阅者没有为 onError 信号定义处理程序，那么 onError 抛异常。

此外，响应式流的语义定义了 onError 是一个终止操作，该操作之后响应式流会停止执行。



​		此时，我们可能采取以下策略中的一种做出不同响应：

1. 为 subscribe 操作符中的 onError 信号定义处理程序。
2. 定义一个在发生错误时重新执行的响应式工作流。如果源响应序列发出错误信号，那么retry 操作符会重新订阅该序列。
3. 通过 onErrorResume 操作符捕获异常并执行备用工作流。
4. 通过 onErrorReturn 操作符捕获一个错误，并用一个默认静态值或一个从异常中计算出的值替换它。
5. 通过 onErrorMap 操作符捕获异常并将其转换为另一个异常来更好地表现当前场景。

## 1、onError

```java
//onError
Flux.from(new Publisher<String>() {
    @Override
    public void subscribe(Subscriber<? super String> s) {
        s.onError(new RuntimeException("手动异常"));
    }
    // }).subscribe(System.out::println);
}).subscribe(System.out::println,System.err::println);
```

## 2、retry

```java
  private static Random random = new Random();
    private static Flux<String> recommendedBooks(String userId){
        return Flux.defer(()->{
            if(random.nextInt(10) < 7){
                return Flux.<String>error(new RuntimeException("err"))
                        // 整体向后推移时间
                        .delaySequence(Duration.ofMillis(100));
            }else{
                return Flux.just("西游记","红楼梦")
                        .delayElements(Duration.ofMillis(10));
            }
        }).doOnSubscribe(
                item -> System.out.println("请求：" + userId)
        );
    }
```

```java
  private static CountDownLatch latch = new CountDownLatch(1);
    public static void main(String[] args) throws InterruptedException {

        Flux.just("user-1000")
                .flatMap(user -> recommendedBooks(user).retry(3))//这里最多会调用四次，第一次正常调用 后三次进行重试
                .subscribe(
                        System.out::println,
                        ex -> {
                            System.err.println(ex);
                            latch.countDown();
                        },
                        () ->{
                            System.out.println("处理完毕");
                            latch.countDown();
                        }
                );
        latch.await();
    }
```

## 3、onErrorResume 

```java
 public static void main(String[] args) throws InterruptedException {

        Flux.just("user-1000")
                .flatMap(user -> recommendedBooks(user))
                .onErrorResume(error -> Flux.just("三国演义"))// 异常捕获返回备用的流
                .subscribe(
                        System.out::println,
                        ex -> {
                            System.err.println(ex);
                            latch.countDown();
                        },
                        () ->{
                            System.out.println("处理完毕");
                            latch.countDown();
                        }
                );
        latch.await();
    }
```



## 4、onErrorReturn 

```java
 public static void main(String[] args) throws InterruptedException {

        Flux.just("user-1000")
                .flatMap(user -> recommendedBooks(user))
                .onErrorReturn("水浒传")// 异常捕获返回备用的流
                .subscribe(
                        System.out::println,
                        ex -> {
                            System.err.println(ex);
                            latch.countDown();
                        },
                        () ->{
                            System.out.println("处理完毕");
                            latch.countDown();
                        }
                );
        latch.await();
    }
```



## 5、onErrorMap 

```java
 public static void main(String[] args) throws InterruptedException {

        Flux.just("user-1000")
                .flatMap(user -> recommendedBooks(user))
                .onErrorMap(throwable -> {
                    if(throwable.getMessage().equals("err")){
                        return new Exception("业务异常");
                    }
                    return new Exception("未知异常");
                })
                .subscribe(
                        System.out::println,
                        ex -> {
                            System.err.println(ex);
                            latch.countDown();
                        },
                        () ->{
                            System.out.println("处理完毕");
                            latch.countDown();
                        }
                );
        latch.await();
    }
```

​		总而言之，Project Reactor 提供了丰富的工具集，可以帮助处理异常情况，从而提高应用程序的回弹性。**错误处理**

​		onError 信号是响应式流规范的一个组成部分，一种将异常传播给可以处理它的用户。但是，如果最终订阅者没有为 onError 信号定义处理程序，那么 onError 抛异常。

此外，响应式流的语义定义了 onError 是一个终止操作，该操作之后响应式流会停止执行。



​		此时，我们可能采取以下策略中的一种做出不同响应：

1. 为 subscribe 操作符中的 onError 信号定义处理程序。
2. 定义一个在发生错误时重新执行的响应式工作流。如果源响应序列发出错误信号，那么retry 操作符会重新订阅该序列。
3. 通过 onErrorResume 操作符捕获异常并执行备用工作流。
4. 通过 onErrorReturn 操作符捕获一个错误，并用一个默认静态值或一个从异常中计算出的值替换它。
5. 通过 onErrorMap 操作符捕获异常并将其转换为另一个异常来更好地表现当前场景。

## 1、onError

```java
//onError
Flux.from(new Publisher<String>() {
    @Override
    public void subscribe(Subscriber<? super String> s) {
        s.onError(new RuntimeException("手动异常"));
    }
    // }).subscribe(System.out::println);
}).subscribe(System.out::println,System.err::println);
```

## 2、retry

```java
  private static Random random = new Random();
    private static Flux<String> recommendedBooks(String userId){
        return Flux.defer(()->{
            if(random.nextInt(10) < 7){
                return Flux.<String>error(new RuntimeException("err"))
                        // 整体向后推移时间
                        .delaySequence(Duration.ofMillis(100));
            }else{
                return Flux.just("西游记","红楼梦")
                        .delayElements(Duration.ofMillis(10));
            }
        }).doOnSubscribe(
                item -> System.out.println("请求：" + userId)
        );
    }
```

```java
  private static CountDownLatch latch = new CountDownLatch(1);
    public static void main(String[] args) throws InterruptedException {

        Flux.just("user-1000")
                .flatMap(user -> recommendedBooks(user).retry(3))//这里最多会调用四次，第一次正常调用 后三次进行重试
                .subscribe(
                        System.out::println,
                        ex -> {
                            System.err.println(ex);
                            latch.countDown();
                        },
                        () ->{
                            System.out.println("处理完毕");
                            latch.countDown();
                        }
                );
        latch.await();
    }
```

## 3、onErrorResume 

```java
 public static void main(String[] args) throws InterruptedException {

        Flux.just("user-1000")
                .flatMap(user -> recommendedBooks(user))
                .onErrorResume(error -> Flux.just("三国演义"))// 异常捕获返回备用的流
                .subscribe(
                        System.out::println,
                        ex -> {
                            System.err.println(ex);
                            latch.countDown();
                        },
                        () ->{
                            System.out.println("处理完毕");
                            latch.countDown();
                        }
                );
        latch.await();
    }
```



## 4、onErrorReturn 

```java
 public static void main(String[] args) throws InterruptedException {

        Flux.just("user-1000")
                .flatMap(user -> recommendedBooks(user))
                .onErrorReturn("水浒传")// 异常捕获返回备用的流
                .subscribe(
                        System.out::println,
                        ex -> {
                            System.err.println(ex);
                            latch.countDown();
                        },
                        () ->{
                            System.out.println("处理完毕");
                            latch.countDown();
                        }
                );
        latch.await();
    }
```



## 5、onErrorMap 

```java
 public static void main(String[] args) throws InterruptedException {

        Flux.just("user-1000")
                .flatMap(user -> recommendedBooks(user))
                .onErrorMap(throwable -> {
                    if(throwable.getMessage().equals("err")){
                        return new Exception("业务异常");
                    }
                    return new Exception("未知异常");
                })
                .subscribe(
                        System.out::println,
                        ex -> {
                            System.err.println(ex);
                            latch.countDown();
                        },
                        () ->{
                            System.out.println("处理完毕");
                            latch.countDown();
                        }
                );
        latch.await();
    }
```

​		总而言之，Project Reactor 提供了丰富的工具集，可以帮助处理异常情况，从而提高应用程序的回弹性。



# 四、背压处理

​	尽管响应式流规范要求将背压构建到生产者和消费者之间的通信中，但这仍然可能使消费者溢出。

​		一些消费者可能无意识地请求无界需求，然后无法处理生成的负载。

​		另一些消费者则可能对传入消息的速率有严格的限制。例如，数据库客户端每秒不能插入超过 1000条记录。在这种情况下，事件批处理技术可能有所帮助。

​		可以通过以下方式配置流以处理背压情况：

1. onBackPressureBuffer 操作符会请求无界需求并将返回的元素推送到下游。如果下游消费者无法跟上，那么元素将缓冲在队列中。

2. onBackPressureDrop 操作符也请求无界需求（Integer.MAX_VALUE）并向下游推送数据。如果下游请求数量不足，那么元素会被丢弃。自定义处理程序可以用来处理已丢弃的元素。

3. onBackPressureLast 操作符与 onBackPressureDrop 的工作方式类似。只是会记住最近收到的元素，并在需求出现时立即将其推向下游。

4. onBackPressureError 操作符在尝试向下游推送数据时请求无界需求。如果下游消费者无法跟上，则操作符会引发错误。

​		管理背压的另一种方法是使用速率限制技术。

onBackPressureBuffer 

```java
public static void main(String[] args) throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(1);
    Flux.range(1,1000)
            .delayElements(Duration.ofMillis(10))
            .onBackpressureBuffer(600)
            .delayElements(Duration.ofMillis(100))
            .subscribe(
                    System.out::println,
                    ex ->{
                        System.out.println(ex);
                        latch.countDown();
                    },
                    () ->{
                        System.out.println("处理完毕");
                        latch.countDown();
                    }
            );
    latch.await();
    System.out.println("main结束");
}
```

onBackPressureDrop 



```java
Flux.range(1,1000)
        .delayElements(Duration.ofMillis(10))
        .onBackpressureDrop()
        .delayElements(Duration.ofMillis(100))
        .subscribe(
                System.out::println,
                ex ->{
                    System.out.println(ex);
                    latch.countDown();
                },
                () ->{
                    System.out.println("处理完毕");
                    latch.countDown();
                }
        );
latch.await();
System.out.println("main结束");
```

onBackPressureLast 



```java
CountDownLatch latch = new CountDownLatch(1);
Flux.range(1,1000)
        .delayElements(Duration.ofMillis(10))
        .onBackpressureLatest()
        .delayElements(Duration.ofMillis(100))
        .subscribe(
                System.out::println,
                ex ->{
                    System.out.println(ex);
                    latch.countDown();
                },
                () ->{
                    System.out.println("处理完毕");
                    latch.countDown();
                }
        );
latch.await();
System.out.println("main结束");
```

onBackPressureError 

```java**
CountDownLatch latch = new CountDownLatch(1);
Flux.range(1,1000)
        .delayElements(Duration.ofMillis(10))
        .onBackpressureError()
        .delayElements(Duration.ofMillis(100))
        .subscribe(
                System.out::println,
                ex ->{
                    System.out.println(ex);
                    latch.countDown();
                },
                () ->{
                    System.out.println("处理完毕");
                    latch.countDown();
                }
        );
latch.await();
System.out.println("main结束");
```







# 五、热数据流和冷数据流

​			冷发布者行为方式：无论订阅者何时出现，都为该订阅者生成所有序列数据，**没有订阅者就不会生成数据**。

```java
 Flux<String> coldPublisher = Flux.defer(()->{
            System.out.println("生成数据");
            return Flux.just(UUID.randomUUID().toString());
        });
        System.out.println("尚未生成数据");
        coldPublisher.subscribe(e -> System.out.println("onNext:" + e));
        coldPublisher.subscribe(e -> System.out.println("onNext:" + e));
        System.out.println("为两次订阅生成两次数据");
```





​			每当订阅者出现时都会有一个新序列生成，而这些语义可以代表 HTTP请求。

​			热发布者中的数据生成不依赖于订阅者而存在。因此，热发布者可能在第一个订阅者出现之前开始生成元素。

​			这种语义代表数据广播场景。例如，一旦股价发生变化，热发布者就可以向其订阅者广播有关当前股价的更新。

​			但是，当订阅者到达时，它仅接收未来的价格更新，而不接受先前价格历史。 

**多播流元素**

​		通过响应式转换将冷发布者转变为热发布者。

​		如，一旦所有订阅者都准备好生成数据，希望在几个订阅者之间共享冷处理器的结果。同时，我们又不希望为每个订阅者重新生成数据。 



​		如下案例：

```java
Flux<Integer> source = Flux.range(0,3)
    .doOnSubscribe(s -> System.out.println("对冷发布者的新订阅票据：" + s));
ConnectableFlux<Integer> conn = source.publish();
conn.subscribe(item -> System.out.println("[subscriber 1] onNext:" + item));
conn.subscribe(item -> System.out.println("[subscriber 2] onNext:" + item));
System.out.println("所有定于这都准备建立连接");
conn.connect();
```



可以看到，冷发布者收到了订阅，只生成了一次数据项。但是，两个订阅者都收到了整个事件集合。



**缓存流元素**

​			使用 ConnectableFlux 可以轻松实现不同的**数据缓存策略**。

​		     cache 操作符使用 ConnectableFlux ，因此它的主要附加值是它所提供的一个流式而直接的API。

​			可以调整缓存所能容纳的数据量以及每个缓存项的到期时间。

​		示例代码：

```java
Flux<Integer> source = Flux.range(0,5)
    .doOnSubscribe(s -> System.out.println("冷发布者的新订阅数据"));
Flux<Integer> cacheSource = source.cache(Duration.ofMillis(1000));
cacheSource.subscribe(item -> System.out.println("[subscribe 1] on Next:" +item));
cacheSource.subscribe(item -> System.out.println("[subscribe 2] on Next:" +item));
Thread.sleep(1200);
cacheSource.subscribe(item -> System.out.println("[subscribe 3] on Next:" +item));
```



​		前两个订阅者共享第一个订阅的同一份缓存数据。然后，在一定延迟之后，由于第三个订阅者无法获取缓存数据，因此一个针对冷发布者的新订阅被触发了。最后，即使该数据不来自缓存，第三个订阅者也接收到了所需的数据。

**共享流元素**

​		我们可以使用 ConnectableFlux 向几个订阅者多播事件。但是需要等待订阅者出现才能开始处理。

​		share 操作符可以将冷发布者转变为热发布者。该操作符会为每个新订阅者传播订阅者尚未错过的事件。

​		示例代码：

```java
     Flux<Integer> source = Flux.range(0, 5)
                .delayElements(Duration.ofMillis(100))
                .doOnSubscribe(s -> System.out.println("冷发布者新的订阅票据"));
        Flux<Integer> shareSource = source.share();
        shareSource.subscribe(item -> System.out.println("subscribe 1 onNext:" + item));
        Thread.sleep(400);
        shareSource.subscribe(item -> System.out.println("subscribe 2 onNext:" + item));
        Thread.sleep(10*1000);
```

​		在前面的代码中，共享了一个冷发布流，该流以每 100 毫秒为间隔生成事件。然后，经过一些延迟，一些订阅者订阅了共享发布者。

​		第一个订阅者从第一个事件开始接收，而第二个订阅者错过了在其出现之前所产生的事件（S2 仅接收到事件 3 和事件 4

# 







































 