package com.msb;

import com.msb.entity.Book;
import com.msb.repository.BookSpringDataMongoRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.concurrent.CountDownLatch;

@SpringBootTest
class MsbWebfluxMongodbApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(MsbWebfluxMongodbApplicationTests.class);

    @Test
    void contextLoads(@Autowired BookSpringDataMongoRepository bookRepo) {
        log.info("开始创建数据");

        bookRepo.saveAll(Flux.just(
               new Book("平凡的世界",2001,"路遥"),
               new Book("人生",2001,"路遥"),
               new Book("在困难的日子",2001,"路遥"),
               new Book("人世间",2001,"梁晓声"),
               new Book("人间烟火",2001,"梁晓声")
        )).blockLast();
        log.info("数据存放到mongodb中");
        log.info("查询所有的数据");
        bookRepo.findAll()
                .doOnNext(book -> System.out.println(book))
                .blockLast();

        log.info("通过作者名称获取书的信息 开始");
        bookRepo.findByAuthorsOrderByPublishingYear("路遥")
                .doOnNext(book -> System.out.println(book))
                .blockLast();
        log.info("通过作者名称获取书的信息 结束");
    }


    @Test
    public void testInsert(@Autowired ReactiveMongoTemplate template) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);
        Book book = new Book("天若有情", 2001, "梁晓声");
        template.insert(book)
                .doOnNext(book1 -> System.out.println(book1))
                .flatMap(book1 -> template.findById(book1.getId(),Book.class))
                .doOnNext(book1 -> System.out.println("找到：" + book1))
                .doOnTerminate(() -> latch.countDown())
                .subscribe();
        latch.await();
    }

    @Test
    public void testDelete(@Autowired BookSpringDataMongoRepository repository){
        ObjectId id = new ObjectId("63748f677f632c6022e0b73c");
        repository.findById(id)
                .doOnNext(book -> System.out.println("删除之前：" + book))
                .then(repository.deleteById(id))
                .map(book -> repository.findById(id))
                .doOnNext(book -> System.out.println("删除之后：" + book))
                .doOnTerminate(() -> System.out.println("运行结束"))
                .block();
    }


}

















