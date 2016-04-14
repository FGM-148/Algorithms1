package II.Queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
   
   private Node first;
   private Node last;
   private int numberOfItems;
   
   private class Node {
       Item value;
       Node next;
       Node prev;
       Node() {}
       Node(Item item){
           value = item;
       }
   }
   
   private class DequeIterator implements Iterator<Item> {
       private Node current = first;

       public boolean hasNext() { return current != null; }

       public void remove() { throw new UnsupportedOperationException(); }

       public Item next() {
           if (!hasNext())
               throw new NoSuchElementException();
           Item item = current.value;
           current = current.next; 
           return item;
       }
   }
   
   public Deque() {
	   first = last = null;
	   numberOfItems = 0;
   }

   public boolean isEmpty(){
	   return numberOfItems ==0;
   }

   public int size(){
	   return numberOfItems;
   }

   public void addFirst(Item item) {
       if (item==null)
           throw new NullPointerException();
       Node node = new Node(item);
       if (first!=null) {
           node.next = first;
           first.prev = node;
           first = node;
       }
       else
           first=node;
       if (last==null){
           last = node;
           }
       numberOfItems++;
   }

   public void addLast(Item item){
       if (item==null)
           throw new NullPointerException();
	   Node node = new Node(item);
	   if (last!=null) {
	       node.prev = last;
	       last.next = node;
	       last = node;
	   }
	   else
	       first = last = node;
	   numberOfItems++;
   }

   public Item removeFirst(){
       if (numberOfItems ==0)
           throw new NoSuchElementException();
       Item result = first.value;
       first.value = null;
       if (numberOfItems ==1)
           first = last = null;
       else{
	       first.prev = null;
           first = first.next;
	   }
       numberOfItems--;
       return result;    
   }

   public Item removeLast(){
       if (numberOfItems ==0)
           throw new NoSuchElementException();
	   Item result = last.value;
	   last.value = null;
	   if (numberOfItems ==1){
	       last = first = null;
	   }
	   else{
       last = last.prev;
       last.next = null;
	   }
	   numberOfItems--;
	   return result;
   }

   public Iterator<Item> iterator(){
	   return new DequeIterator();
   }

   public static void main(String[] args){
	   // unit testing
   }

}