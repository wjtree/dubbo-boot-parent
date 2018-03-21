package com.app.data.repository;

import com.app.data.document.Book;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, ObjectId> {

}