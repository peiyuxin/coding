package org.pyx.designpattern.chain;

/**
 * @author pyx
 * @date 2023/4/7
 */
public class OrcCommander extends RequestHandler{

    public OrcCommander(RequestHandler handler) {
        super(handler);
    }

    public void handleRequest(Request req) {
        if(req.getRequestType().equals(RequestType.DEFEND_CASTLE)){
            printHandling(req);
        } else {
            super.handleRequest(req);
        }
    }

    public String toString(){
        return "Orc commander";
    }
}
