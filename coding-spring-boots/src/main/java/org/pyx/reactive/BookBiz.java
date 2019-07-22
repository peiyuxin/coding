package org.pyx.reactive;

/**
 * Created by pyx on 2018/7/14.
 */
public class BookBiz {
    private IBookService bookService;
    public BookBiz(){
        bookService = new DefaultBookService();
    }
    public void updateBook(int bookId,CallBack<Void> callback){
        bookService.getBookById(1, new CallBack<Book>() {
                @Override
                public void onResult(Book book) {
                    if(book != null){
                        book.author = "pyx";
                        book.id = bookId;
                        bookService.updateBook(book,callback);
                    }
                }

                @Override
                public void onError(Exception e) {
                    callback.onError(e);
                }
            });
        /*Book book = bookService.getBookById(bookId);
        if(book!=null){
            book.author = "pyx";
            book.id = bookId;
            bookService.updateBook(book);
        }*/
    }
}
