package org.pyx.designpattern.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pyx
 * @date 2023/4/7
 */
public abstract class LetterComposite {

    private List<LetterComposite> children = new ArrayList<LetterComposite>();

    public void add(LetterComposite letter) {
        children.add(letter);
    }

    public int count() {
        return children.size();
    }
    protected abstract void printThisBefore();

    protected abstract void printThisAfter();

    public void print() {
        printThisBefore();
        for (LetterComposite letter : children) {
            letter.print();
        }
        printThisAfter();
    }
}
