package org.pyx.designpattern.chain;

/**
 * @author pyx
 * @date 2023/4/7
 */
public class app {

    public static void main(String[] args) {
        OrcKing king = new OrcKing();
        king.makeRequest(new Request(RequestType.DEFEND_CASTLE,"defend castle"));
        king.makeRequest(new Request(RequestType.TORTURE_PRIONER,"torture prioner"));
        king.makeRequest(new Request(RequestType.COLLECT_TAX,"collect tax"));
    }
}
