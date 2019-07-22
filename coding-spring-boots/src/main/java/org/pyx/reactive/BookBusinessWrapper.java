package org.pyx.reactive;

/**
 * Created by pyx on 2018/7/15.
 */
public class BookBusinessWrapper {
    private IBookService iBookService;
    public BookBusinessWrapper(){
        iBookService = new DefaultBookService();
    }

    public <T extends Task<Book>> T  getBookById(int bookId){
        //T继承及强转非常安全 逆变,协变
        return (T)(Task<Book>)(CallBack<Book> callback) -> {
            iBookService.getBookById(bookId, callback);
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
