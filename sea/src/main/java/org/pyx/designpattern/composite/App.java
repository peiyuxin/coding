package org.pyx.designpattern.composite;

/**
 * @author pyx
 * @date 2023/4/7
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Message from the orcs:");

        LetterComposite orcMessage = new Messenger().messageFromOrcs();
        orcMessage.print();

        System.out.println("\n");

        System.out.println("Message from the elves: ");

        LetterComposite elfMessage = new Messenger().messageFromElves();
        elfMessage.print();
    }
}
