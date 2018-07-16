package org.pyx.reactive;

/**
 * Created by pyx on 2018/7/15.
 */
public class BookBusiness {
    private BookBusinessWrapper businessWrapper;
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

    public AbstractTask<Void> modifyBook(int bookId){
        AbstractTask<Book> bookTask = (AbstractTask<Book>)businessWrapper.getBookById(bookId);
        AbstractTask<Void> baseTask = bookTask.map(new Func<Book, AbstractTask<Void>>() {
            @Override
            public AbstractTask<Void> call(Book param) {
                return (AbstractTask<Void>)businessWrapper.updateBook(param);
            }
        });
        return baseTask;
    }
}
