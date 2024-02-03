package com.msb.repository;

import com.msb.entity.Book;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BookSpringDataMongoRepository extends ReactiveMongoRepository<Book, ObjectId> {

    Flux<Book> findByAuthorsOrderByPublishingYear(String ...authors);

}
