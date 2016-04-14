package II.Queues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {

    public static void main(String[] args) {
        int i =  Integer.parseInt(args[0]);
        RandomizedQueue<String> deck = new RandomizedQueue<String>();
        for (int j=0;!StdIn.isEmpty();j++)
            deck.enqueue(StdIn.readString());
        for (int j=0;j<i;j++)
            StdOut.println(deck.dequeue());

    }

}
