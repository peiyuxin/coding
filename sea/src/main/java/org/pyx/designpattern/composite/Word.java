package org.pyx.designpattern.composite;

import java.util.List;

/**
 * @author pyx
 * @date 2023/4/7
 */
public class Word extends LetterComposite{
    public Word(List<Letter> letters){
        for (Letter l: letters) {
            this.add(l);
        }
    }

    protected void printThisBefore(){
        System.out.print(" ");
    }

    protected void printThisAfter() {

    }
}
