package org.pyx.reactive;

/**
 * Created by pyx on 2018/7/14.
 */
public interface IBookService {
    /**
     * 根据bookId获取book
     * @param bookId
     * @return book
     */
    public Book getBookById(int bookId,CallBack<Book> callBack);

    /**
     * 更新book
     * @param book
     */
    public void updateBook(Book book,CallBack<Void> callBack);
}
