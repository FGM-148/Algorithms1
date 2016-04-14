package II.Queues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] itemsArray;
    private int numberOfItems = 0;
    private int randomId;
    
    public RandomizedQueue() {
       numberOfItems = 0;
       randomId = 0;
       itemsArray = (Item[]) new Object[2];
    }
    
    public boolean isEmpty(){
       return numberOfItems >0 ? false : true;
    }

    public int size(){
       return numberOfItems;
    }

    public void enqueue(Item item) {
       if (item==null)
           throw new NullPointerException();
       if (numberOfItems == itemsArray.length) {
           resize(itemsArray.length*2);
           itemsArray[numberOfItems] = item;
       }
       else {
           if(itemsArray[numberOfItems]==null)
               itemsArray[numberOfItems] = item;
       }
       numberOfItems++;
    }

    public Item dequeue(){
       if (isEmpty())
            throw new NoSuchElementException();
       randomId = StdRandom.uniform(numberOfItems);
       Item result = itemsArray[randomId];
       itemsArray[randomId] = itemsArray[numberOfItems-1];
       itemsArray[numberOfItems-1] = null;
       numberOfItems--;
       if (numberOfItems >0 && numberOfItems ==0.25* itemsArray.length)
           resize(itemsArray.length/2);
       return result;
    }
    
    public Item sample(){
        if (isEmpty())
            throw new NoSuchElementException();
        return itemsArray[StdRandom.uniform(numberOfItems)];
    }

    public Iterator<Item> iterator(){
       return new RandomIterator();
    }

    private class RandomIterator implements Iterator{
        
        private Item[] result = (Item[]) new Object[numberOfItems];
        private Item[] temp = (Item[]) new Object[itemsArray.length];
        private int last = 0;
        
        RandomIterator(){
            final int count = numberOfItems;
            for (int i =0; i < count; i++){
                result[i] = dequeue();
                temp [i] = result[i];
            }
            itemsArray = temp;
            numberOfItems = count;
        }

        public boolean hasNext() {
            return last < numberOfItems;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return result[last++];
        }

        public void remove(){
            throw new UnsupportedOperationException();
        }

    }
    
    private void resize(int capacity) {
        assert capacity >= numberOfItems;
        Item[] temp = (Item[]) new Object[capacity];
        int j = 0;
        for (int i = 0; i < itemsArray.length; i++) {
            if (itemsArray[i]!=null){
                temp[j] = itemsArray[i];
                j++;
            }
        }
        itemsArray = temp;
    }

    public static void main(String[] args){
       // unit testing
    }
}