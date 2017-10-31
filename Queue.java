/*******************************************************************************
/
/      filename:  Queue.java
/
/   description:  Implementation of a generic Queue that was used for the
/                 Queues.
/
/        author:  Krishnaraja, Vignesh
/      login id:  FA_17_CPS356_14
/
/         class:  CPS 356
/    instructor:  Perugini
/    assignment:  Midterm Project
/
/      assigned:  October 12, 2017
/           due:  October 26, 2017
/
/******************************************************************************/
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<Item> implements Iterable<Item> {
   private Node<Item> first;   
   private Node<Item> last;    
   private int n;               

   private static class Node<Item> {
      private Item item;
      private Node<Item> next;
   }

   public Queue() {
      first = null;
      last  = null;
      n = 0;
   }

   public boolean isEmpty() {
      return first == null;
   }

   public int size() {
      return n;
   }

   public Item peek() {
      if (isEmpty()) throw new NoSuchElementException("Queue underflow");
      return first.item;
   }

   public void enqueue(Item item) {
      Node<Item> oldlast = last;
      last = new Node<Item>();
      last.item = item;
      last.next = null;
      if (isEmpty()) first = last;
      else           oldlast.next = last;
      n++;
   }

   public Item dequeue() {
      if (isEmpty()) throw new NoSuchElementException("Queue underflow");
      Item item = first.item;
      first = first.next;
      n--;
      if (isEmpty()) last = null; 
      return item;
   }

   public Iterator<Item> iterator()  {
      return new ListIterator<Item>(first);  
   }

   private class ListIterator<Item> implements Iterator<Item> {
      private Node<Item> current;

      public ListIterator(Node<Item> first) {
         current = first;
      }

      public boolean hasNext(){ return current != null;}
      public void remove(){ throw new UnsupportedOperationException();}

      public Item next() {
         if (!hasNext()) throw new NoSuchElementException();
         Item item = current.item;
         current = current.next; 
         return item;
      }
   }
}