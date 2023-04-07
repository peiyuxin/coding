package org.pyx.designpattern.chain;

/**
 * @author pyx
 * @date 2023/4/7
 */
public abstract class RequestHandler {

    private RequestHandler next;

    public RequestHandler(RequestHandler next){
        this.next = next;
    }

    public void handleRequest(Request req){
        if(next != null){
            next.handleRequest(req);
        }
    }

    protected void printHandling(Request req){
        System.out.println(this + " handling request \""+ req +"\"");
    }

}
