package org.pyx.reactive;

/**
 * Created by pyx on 2018/7/15.
 */
public class BookBusiness {
    private BookBusinessWrapper businessWrapper;

    private IBookService bookService;
    public BookBusiness(){
        businessWrapper = new BookBusinessWrapper();
    }
    public Task<Void> updateBook(int bookId){
        return new Task<Void>() {
            @Override
            public void doHandle(CallBack<Void> callback) {
                businessWrapper.getBookById(bookId).doHandle(new CallBack<Book>() {
                    @Override
                    public void onResult(Book result) {
                        if(result!=null){
                            result.setAuthor("pyx");
                            businessWrapper.updateBook(result).doHandle(callback);
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        callback.onError(e);
                    }
                });
            }
        };
    }

    public Task<Void> modifyBook(int bookId){
        AbstractTask<Book> bookTask = businessWrapper.getBookById(bookId);
        //AbstractTask<Void> baseTask = //func.call(bookTask)
        Task<Void> task=bookTask.map(new Func<Book, Task<Void>>() {
            @Override
            public Task<Void> call(Book param) {
                return businessWrapper.updateBook(param);
            }
        });
        /*bookTask.map(new Func<Book, Task<Void>>() {
            @Override
            public Task<Void> call(Book param) {
                return businessWrapper.updateBook(param);
            }
        });*/
        return task;
    }

}
