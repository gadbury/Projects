import java.util.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class extends RedBlackTree into a tree that supports iterating over the values it
 * stores in sorted, ascending order.
 */
public class IterableRedBlackTree<T extends Comparable<T>>
                extends RedBlackTree<T> implements IterableSortedCollection<T> {

    private Comparable<T> max = null;
    private Comparable<T> min = null;


    /**
     * Allows setting the start (minimum) value of the iterator. When this method is called,
     * every iterator created after it will use the minimum set by this method until this method
     * is called again to set a new minimum value.
     * @param min the minimum for iterators created for this tree, or null for no minimum
     */
    public void setIteratorMin(Comparable<T> min) { this.min = min; }

    /**
     * Allows setting the stop (maximum) value of the iterator. When this method is called,
     * every iterator created after it will use the maximum set by this method until this method
     * is called again to set a new maximum value.
     * @param max the maximum for iterators created for this tree, or null for no maximum
     */
    public void setIteratorMax(Comparable<T> max) { this.max = max; }

    /**
     * Returns an iterator over the values stored in this tree. The iterator uses the
     * start (minimum) value set by a previous call to setIteratorMin, and the stop (maximum)
     * value set by a previous call to setIteratorMax. If setIteratorMin has not been called
     * before, or if it was called with a null argument, the iterator uses no minimum value
     * and starts with the lowest value that exists in the tree. If setIteratorMax has not been
     * called before, or if it was called with a null argument, the iterator uses no maximum
     * value and finishes with the highest value that exists in the tree.
     */
    public Iterator<T> iterator() {
        RBTIterator<T> iterator = new RBTIterator<>(this.root, this.min, this.max);
        return iterator;
        }

    /**
     * Nested class for Iterator objects created for this tree and returned by the iterator method.
     * This iterator follows an in-order traversal of the tree and returns the values in sorted,
     * ascending order.
     */
    protected static class RBTIterator<R> implements Iterator<R> {

         // stores the start point (minimum) for the iterator
         Comparable<R> min = null;
         // stores the stop point (maximum) for the iterator
         Comparable<R> max = null;
         // stores the stack that keeps track of the inorder traversal
         Stack<BSTNode<R>> stack = null;

        /**
         * Constructor for a new iterator if the tree with root as its root node, and
         * min as the start (minimum) value (or null if no start value) and max as the
         * stop (maximum) value (or null if no stop value) of the new iterator.
         * @param root root node of the tree to traverse
         * @param min the minimum value that the iterator will return
         * @param max the maximum value that the iterator will return 
         */
        public RBTIterator(BSTNode<R> root, Comparable<R> min, Comparable<R> max) {
          this.min = min;
          this.max = max;
          this.stack = new Stack<BSTNode<R>>();
          buildStackHelper(root);
        }

        /**
         * Helper method for initializing and updating the stack. This method both
         * - finds the next data value stored in the tree (or subtree) that is bigger
         *   than or equal to the specified start point (maximum), and
         * - builds up the stack of ancestor nodes that contain values larger than or
         *   equal to the start point so that those nodes can be visited in the future.
         * @param node the root node of the subtree to process
         */
        private void buildStackHelper(BSTNode<R> node) {
          //Ensure argument isn't null
          if(node == null){
            return;
          }
          //Calls itself recursively on node's right subtree if node is smaller than min
          if(min != null && min.compareTo(node.data) > 0){
            buildStackHelper(node.right);
          }
          //Pushes node onto stack and calls itself recursively on node's left subtree if node is
          //greater than min
          else{
            stack.push(node);
            buildStackHelper(node.left);
          }
        }

        /**
         * Returns true if the iterator has another value to return, and false otherwise.
         */
        public boolean hasNext() {
          //Continues looping while stack still has contents and invalid values are found
          while(!stack.isEmpty() && (max != null && max.compareTo(stack.peek().data) < 0)){
            stack.pop();
          }
          return !stack.isEmpty();
        }

        /**
         * Returns the next value of the iterator.
         * @throws NoSuchElementException if the iterator has no more values to return
         */
        public R next() {
          //Ensure there are more valid values
          if(!hasNext()){
            throw new NoSuchElementException("No more values in range");
          }

          //Get next valid value
          BSTNode<R> node = stack.pop();

          //Add values from right subtree to stack
          buildStackHelper(node.getRight());

          return node.data;
        }

    }


  /**
   * Tests iterating through a large tree with some duplicate integers with a specific min and max
   */

  @Test
    public void iteratorTest1(){
      IterableRedBlackTree<Integer> test1 = new IterableRedBlackTree<>();
      test1.insert(5); //
      test1.insert(7); //
      test1.insert(8); //
      test1.insert(14); //
      test1.insert(-500); //
      test1.insert(80);
      test1.insert(41);
      test1.insert(9); //
      test1.insert(24);
      test1.insert(7); //
      test1.insert(-4); //
      test1.insert(5); //

      //create iterator with range of 0-70
      RBTIterator<Integer> iterator1 = new RBTIterator<Integer>(test1.root,0,70);

      //Expected values, omitting -500,-4, and 80
      List<Integer> expected = List.of(5,5,7,7,8,9,14,24,41);

      //iterate through tree to create actual list
      List<Integer> actual = new ArrayList<Integer>();

      while(iterator1.hasNext()){
        actual.add(iterator1.next());
      }

      assertEquals(expected,actual);
      assertFalse(iterator1.hasNext());
    }

  /**
   * Tests iterating through a tree with string values with no specific min
   */

  @Test
  public void iteratorTest2(){
    IterableRedBlackTree<String> test2 = new IterableRedBlackTree<>();
    test2.insert("a");
    test2.insert("c");
    test2.insert("d");
    test2.insert("h");
    test2.insert("b");
    test2.insert("f");
    test2.insert("e");
    test2.insert("g");

    //create iterator with no lower range
    RBTIterator<String> iterator2 = new RBTIterator<String>(test2.root,null,"f");


    //Expected values, omitting h
    List<String> expected = List.of("a","b","c","d","e","f");

    //iterate through tree to create actual list
    List<String> actual = new ArrayList<String>() {
    };

    while(iterator2.hasNext()){
      actual.add(iterator2.next());
    }

    assertEquals(expected,actual);
    assertFalse(iterator2.hasNext());
  }

  /**
   * Tests iterating through a tree with integer values with no specific max
   */

  @Test
  public void iteratorTest3(){
    IterableRedBlackTree<Integer> test3 = new IterableRedBlackTree<>();
    test3.insert(5);
    test3.insert(6);
    test3.insert(7);
    test3.insert(8);
    test3.insert(900);
    test3.insert(10000);

    //create iterator with a lower range of 7
    RBTIterator<Integer> iterator3 = new RBTIterator<>(test3.root,7,null);

    //Expected values, omitting -500,-4, and 80
    List<Integer> expected = List.of(7,8,900,10000);

    //iterate through tree to create actual list
    List<Integer> actual = new ArrayList<Integer>();

    while(iterator3.hasNext()){
      actual.add(iterator3.next());
    }

    assertEquals(expected,actual);
    assertFalse(iterator3.hasNext());
  }

}
