package com.app.data;

import com.alibaba.fastjson.JSON;
import com.app.core.util.MemCachedUtil;
import com.app.data.document.Book;
import com.app.data.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MouldDataApplicationTests {
//    @Autowired
//    private UserMapper userMapper;
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void test(){
//        System.out.println("=====================");
//        List<User> users = userMapper.selectAll();
//        System.out.println(JSON.toJSONString(users));
//        System.out.println("=====================");
    }

    @Test
    public void testMongoDB(){
        System.out.println("=====================");
        // bookDao.deleteAll();

        Book book1 = new Book("以收到比分", "110", "shenzhen");
        bookRepository.insert(book1);

//        Book book2 = new Book("jack", "123", "beijing");
//        Book book3 = new Book("andy", "123", "hubei");

        // bookRepository.insert(book2);
        // bookRepository.insert(book3);

//        List<Book> books = new ArrayList<>();
//        books.add(book1);
//        books.add(book2);
//        books.add(book3);
//
//        bookRepository.insert(books);

        // bookDao.insert();

        List<Book> books = bookRepository.findAll();
        System.out.println(JSON.toJSONString(books));

        // Example<Book> example = Example.of(book1);
        // Book bookTemp = bookDao.findOne(example);

        // System.out.println("================================================");
        // System.out.println(JSON.toJSONString(bookTemp));
        // System.out.println("================================================");
        //
        // bookTemp.setPwd("918293819283");
        // bookTemp.setName("ggdj");
        //
        // bookTemp = bookDao.save(bookTemp);
        // ObjectId objectId = new ObjectId();


        // System.out.println("================================================");
        // System.out.println(JSON.toJSONString(bookTemp));
        // System.out.println(objectId.toString());
        // System.out.println(objectId.toHexString());
        // System.out.println("================================================");

        // bookDao.save();
        System.out.println("=====================");
    }

    @Test
    public void test2() {
        System.out.println("=====================");
        Object date = MemCachedUtil.get("date");
        System.out.println(date);

        MemCachedUtil.set("date","hello 101");

        date = MemCachedUtil.get("date");
        System.out.println(date);

        MemCachedUtil.flashAll();

        date = MemCachedUtil.get("date");
        System.out.println(date);
        System.out.println("=====================");
    }
}
