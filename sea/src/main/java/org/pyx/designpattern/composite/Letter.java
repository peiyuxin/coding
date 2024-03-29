package org.pyx.designpattern.composite;

/**
 * @author pyx
 * @date 2023/4/7
 */
public class Letter extends LetterComposite{

    private char c;

    public Letter(char c) {
        this.c = c;
    }

    @Override
    protected void printThisBefore() {
        System.out.print(c);
    }
    @Override
    protected void printThisAfter() {
        //nop
    }
}
