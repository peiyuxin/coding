package org.pyx.designpattern.chain;

/**
 * @author pyx
 * @date 2023/4/7
 */
public class OrcOfficer extends RequestHandler{

    public OrcOfficer(RequestHandler next) {
        super(next);
    }

    public void handleRequest(Request req) {
        if(req.getRequestType().equals(RequestType.TORTURE_PRIONER)){
            printHandling(req);
        } else {
            super.handleRequest(req);
        }
    }

    @Override
    public String toString() {
        return "Orc officer";
    }
}
