package II.Queues;

import org.junit.Test;

import static org.junit.Assert.*;


public class DequeTest {

    @Test
    public void addFirstElements() {
        Deque<Integer> d = new Deque<Integer>();

        d.addFirst(1);
        d.addFirst(2);
        d.addFirst(3);
        d.addFirst(4);
        d.addFirst(5);
        int n=0;
        for (Integer i : d){
            n++;
        }
    }

    @Test
    public void addLastElements() {
        Deque<Integer> d = new Deque<Integer>();

        d.addLast(1);
        d.addLast(2);
        d.addLast(3);
        d.addLast(4);
        d.addLast(5);
        int n=0;
        for (Integer i : d){
            n++;
        }

    }

    @Test
    public void addAndRemove() {
        Deque<Integer> d = new Deque<Integer>();
        d.addLast(1);
        d.removeFirst();
    }

    @Test
    public void checkIterator(){
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);


        deque.addFirst(2);
        deque.addLast(3);
        deque.addLast(4);
        deque.addFirst(5);
        deque.addLast(6);
        deque.addFirst(7);
        deque.addLast(8);
        deque.addFirst(9);
        deque.removeFirst();
        deque.addLast(11);
        deque.removeLast();
        for (Integer i : deque){

        }

    }

}