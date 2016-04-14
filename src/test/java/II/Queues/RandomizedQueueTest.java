package II.Queues;

import org.junit.Test;

import static org.junit.Assert.*;


public class RandomizedQueueTest {
    @Test
    public void addAndRemove() {
        RandomizedQueue<Integer> d = new RandomizedQueue<Integer>();
        int k=0;
        while (k<10000){
            d.enqueue(1);
            d.enqueue(2);
            d.enqueue(3);
            d.enqueue(4);
            d.enqueue(5);
            d.enqueue(1);
            d.enqueue(2);
            d.enqueue(3);
            d.enqueue(4);
            d.enqueue(5);
            d.enqueue(1);
            d.enqueue(2);
            d.enqueue(3);
            d.enqueue(4);
            d.enqueue(5);
            d.enqueue(1);
            d.enqueue(2);
            d.enqueue(3);
            d.enqueue(4);
            d.enqueue(5);
            d.enqueue(1);
            d.enqueue(2);
            d.enqueue(3);
            d.enqueue(4);
            d.enqueue(5);
            d.enqueue(1);
            d.enqueue(2);
            d.enqueue(3);
            d.enqueue(4);
            d.dequeue();
            d.enqueue(1);
            d.enqueue(2);
            d.enqueue(3);
            d.enqueue(4);
            d.enqueue(5);
            d.enqueue(1);
            d.enqueue(2);
            d.enqueue(3);
            d.dequeue();
            d.enqueue(5);
            k++;}

        System.out.println(d.size());
        for (int i=0;d.size()>0;i++){
            d.dequeue();
        }
    }

    @Test
    public void forEach() {
        RandomizedQueue<Integer> d = new RandomizedQueue<Integer>();

        d.enqueue(1);
        d.enqueue(2);
        d.enqueue(3);
        d.enqueue(4);
        d.enqueue(5);

        for (Integer i : d){
        }
    }
}