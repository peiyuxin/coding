package org.pyx.designpattern.composite;

import java.util.List;

/**
 * @author pyx
 * @date 2023/4/7
 */
public class Sentence extends LetterComposite{
    public Sentence(List<Word> words){
        for (Word w : words) {
            this.add(w);
        }
    }
    @Override
    protected void printThisBefore(){
        //nop
    }
    @Override
    protected void printThisAfter(){
        System.out.print(".");
    }
}
