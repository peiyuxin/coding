package org.pyx.reactive;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pyx on 2018/7/14.
 */
public class DefaultBookService implements IBookService{

    private static final Book DEAFULT_BOOK = new Book();
    private ConcurrentHashMap<Integer,Book> cMap = new ConcurrentHashMap();
    static {
        DEAFULT_BOOK.id = 1;
        DEAFULT_BOOK.author = "pyx";
        DEAFULT_BOOK.name = "story";
    }
    @Override
    public Book getBookById(int bookId,CallBack<Book> callBack) {
        return cMap.getOrDefault(bookId,DEAFULT_BOOK);
    }

    @Override
    public void updateBook(Book book,CallBack<Void> callBack) {
        cMap.put(book.id,book);
    }
}