package org.pyx.reactive;

/**
 * Created by pyx on 2018/7/15.
 */
public class BookBusinessWrapper {
    private IBookService iBookService;
    public BookBusinessWrapper(){
        iBookService = new DefaultBookService();
    }

    public Task<Book> getBookById(int bookId){
        return new Task<Book>() {
            @Override
            public void doHandle(final CallBack<Book> callback) {
                iBookService.getBookById(bookId,callback);
            }
        };
    }


    public Task<Void> updateBook(Book book){
        return new Task<Void>() {
            @Override
            public void doHandle(CallBack<Void> callback) {
                iBookService.updateBook(book,callback);
            }
        };
    }
}
