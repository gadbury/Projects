import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RedBlackTree<T extends Comparable<T>> extends BSTRotation<T>{

  /**
   *Method takes in data of generic type and inserts it into the Red Black Tree
   *
   * @param data data for the new node
   */
  @Override
  public void insert(T data) throws NullPointerException{
    //IF data is null, throw exception
    if(data == null){
      throw new NullPointerException("data can't be null");
    }
    //Create new node
    RBTNode<T> newNode = new RBTNode<T>(data);
    //If tree is empty, make node black and the root then terminate the method
    if(isEmpty()){
      newNode.flipColor();
      root = newNode;
      return;
    }
    //Use BinarySearchTree's insertHelper method to insert into tree
    insertHelper(newNode,root);
    //Check for a red property violation
    ensureRedProperty(newNode);
    //Ensure root is black
    if(((RBTNode<T>)this.root).isRed){
      ((RBTNode<T>)this.root).flipColor();
    }
  }

  /**
   * Checks if a new red node in the RedBlackTree causes a red property violation
   * by having a red parent. If this is not the case, the method terminates without
   * making any changes to the tree. If a red property violation is detected, then
   * the method repairs this violation and any additional red property violations
   * that are generated as a result of the applied repair operation.
   * @param newRedNode a newly inserted red node, or a node turned red by previous repair
   */
  protected void ensureRedProperty(RBTNode<T> newRedNode) {
    //Variables to make code more readable
    RBTNode<T> aunt = null;
    RBTNode<T> parent = null;
    RBTNode<T> grandparent = null;
    if(newRedNode.getUp() != null){
      parent = newRedNode.getUp();
    }
    if(parent.getUp() != null){
      grandparent = parent.getUp();
    }

    //If parent isn't red, do nothing
    if(!parent.isRed){
      return;
    }
    //If there is a violation, check color of aunt

    //get aunt
    try {
      if (grandparent.getLeft().equals(parent)) {
        aunt = grandparent.getRight();
      } else if (grandparent.getRight().equals(parent)) {
        aunt = grandparent.getLeft();
      }
    }catch (NullPointerException e){
    }
    //if aunt is black
    if(aunt == null || !aunt.isRed){
      //Left Left case

      //Ensure nodes being checked arent null
      if(grandparent.getLeft() != null && parent.getLeft() != null){
        if(grandparent.getLeft().equals(parent) && parent.getLeft().equals(newRedNode)){
          //Right rotation of parent and grandparent
          rotate(parent, grandparent);
          //flip both node's color
          parent.flipColor();
          grandparent.flipColor();
        }
      }

      //Left Right case

      //Ensure nodes being checked arent null
      if(grandparent.getLeft() != null && parent.getRight() != null){
        if(grandparent.getLeft().equals(parent) && parent.getRight().equals(newRedNode)){
          //Left rotation of child and parent
          rotate(newRedNode, parent);

          //Same action for Left Left case

          //Right rotation of new red node and grandparent
          rotate(newRedNode, grandparent);
          //flip both node's color
          newRedNode.flipColor();
          grandparent.flipColor();
        }
      }

      //Right Right case

      //Ensure nodes being checked arent null
      if(grandparent.getRight() != null && parent.getRight() != null){
        if(grandparent.getRight().equals(parent) && parent.getRight().equals(newRedNode)){
          //Left rotation of parent and grandparent
          rotate(parent, grandparent);
          //flip both node's color
          parent.flipColor();
          grandparent.flipColor();
        }
      }

      //Right Left case

      //Ensure nodes being checked arent null
      if(grandparent.getRight() != null && parent.getLeft() != null){
        if(grandparent.getRight().equals(parent) && parent.getLeft().equals(newRedNode)){
          //Right rotation of child and parent
          rotate(newRedNode, parent);

          //Same action for Right Right case

          //Left rotation of parent and grandparent
          rotate(newRedNode, grandparent);
          //flip both node's color
          newRedNode.flipColor();
          grandparent.flipColor();
        }
      }
    }
    //if aunt is red, recolor
    else{
      newRedNode.getUp().flipColor();
      aunt.flipColor();
      //if grandparent isn't the root and is black, make red
      if (!grandparent.equals(root) || !grandparent.isRed){
        grandparent.flipColor();
      }
    }
    //Check if any red violations have been created
    try {
      //Check if red parent has a red parent
      if (parent.getUp() != null && parent.getUp().isRed() && parent.isRed()) {
        ensureRedProperty(parent);
      }
      //Check if red grandparent has a red parent
      if (grandparent.getUp().isRed() && grandparent.isRed()){
        ensureRedProperty(grandparent);
      }
    }catch (NullPointerException e){
    }
    try {
      if (aunt.getUp() != null && aunt.getUp().isRed() && aunt.isRed()) {
        ensureRedProperty(aunt);
      }
    }catch (NullPointerException e){
    }
    try {
      if (grandparent.getUp() != null && grandparent.getUp().isRed() && grandparent.isRed()) {
        ensureRedProperty(grandparent);
      }
    }catch (NullPointerException e){
    }
  }


  //Tests the black aunt left right case with tree from Q103
  @Test
  public void RBTtest1(){
    //set up tree

    //Create nodes
    RBTNode<String> blackN = new RBTNode<>("N");
    blackN.flipColor();
    RBTNode<String> blackF = new RBTNode<>("F");
    blackF.flipColor();
    RBTNode<String> redT = new RBTNode<>("T");
    RBTNode<String> redB = new RBTNode<>("B");
    RBTNode<String> blackQ = new RBTNode<>("Q");
    blackQ.flipColor();
    RBTNode<String> blackX = new RBTNode<>("X");
    blackX.flipColor();
    //create tree and set node positions in level-order
    RedBlackTree<String> test1RBT = new RedBlackTree<>();

    //first level
    test1RBT.root = blackN;

    //second level
    blackN.setLeft(blackF);
    blackN.setRight(redT);
    blackF.setUp(blackN);
    redT.setUp(blackN);

    //Third level
    blackF.setLeft(redB);
    redB.setUp(blackF);
    redT.setLeft(blackQ);
    redT.setRight(blackX);
    blackQ.setUp(redT);
    blackX.setUp(redT);

    //Get expected and actual strings then compare
    String expected = "[ N(b), C(b), T(r), B(r), F(r), Q(b), X(b) ]";

    test1RBT.insert("C");

    String actual = test1RBT.root.toLevelOrderString();

    assertEquals(expected,actual);


   }

  //Tests red Aunt case
  @Test
  public void RBTtest2(){
    //set up tree

    //Create nodes
    RBTNode<String> blackN = new RBTNode<>("N");
    blackN.flipColor();
    RBTNode<String> blackC = new RBTNode<>("C");
    blackC.flipColor();
    RBTNode<String> redT = new RBTNode<>("T");
    RBTNode<String> redB = new RBTNode<>("B");
    RBTNode<String> redF = new RBTNode<>("F");
    RBTNode<String> blackQ = new RBTNode<>("Q");
    blackQ.flipColor();
    RBTNode<String> blackX = new RBTNode<>("X");
    blackX.flipColor();
    //create tree and set node positions in level-order
    RedBlackTree<String> test2RBT = new RedBlackTree<>();

    //first level
    test2RBT.root = blackN;

    //second level
    blackN.setLeft(blackC);
    blackN.setRight(redT);
    redT.setUp(blackN);
    blackC.setUp(blackN);

    //Third level
    blackC.setLeft(redB);
    blackC.setRight(redF);
    redB.setUp(blackC);
    redF.setUp(blackC);
    redT.setLeft(blackQ);
    redT.setRight(blackX);
    blackQ.setUp(redT);
    blackX.setUp(redT);

    //Get expected and actual strings then compare
    String expected = "[ N(b), C(r), T(r), B(b), F(b), Q(b), X(b), G(r) ]";

    test2RBT.insert("G");

    String actual = test2RBT.root.toLevelOrderString();

    assertEquals(expected,actual);

  }

  /**
   * Tests case where ensureRedProperty should make no changes
   */

  @Test
  public void RBTtest3(){
    //set up tree

    //Create nodes
    RBTNode<String> blackN = new RBTNode<>("N");
    blackN.flipColor();
    RBTNode<String> redC = new RBTNode<>("C");
    RBTNode<String> redT = new RBTNode<>("T");
    RBTNode<String> blackB = new RBTNode<>("B");
    blackB.flipColor();
    RBTNode<String> blackF = new RBTNode<>("F");
    blackF.flipColor();
    RBTNode<String> blackQ = new RBTNode<>("Q");
    blackQ.flipColor();
    RBTNode<String> blackX = new RBTNode<>("X");
    blackX.flipColor();
    RBTNode<String> redG = new RBTNode<>("G");
    //create tree and set node positions in level-order
    RedBlackTree<String> test3RBT = new RedBlackTree<>();

    //first level
    test3RBT.root = blackN;

    //second level
    blackN.setLeft(redC);
    blackN.setRight(redT);
    redT.setUp(blackN);
    redT.setUp(blackN);

    //Third level
    redC.setLeft(blackB);
    redC.setRight(blackF);
    blackB.setUp(redC);
    blackF.setUp(redC);
    redT.setLeft(blackQ);
    redT.setRight(blackX);
    blackQ.setUp(redT);
    blackX.setUp(redT);

    //Fourth Level
    blackF.setRight(redG);
    redG.setUp(blackF);

    //Get expected and actual strings then compare
    String expected = "[ N(b), C(r), T(r), B(b), F(b), Q(b), X(b), G(r), Z(r) ]";

    test3RBT.insert("Z");

    String actual = test3RBT.root.toLevelOrderString();

    assertEquals(expected,actual);
  }

  /**
   * Tests case where ensureRedProperty should make no changes
   */


  @Test
  public void RBTtest4(){
    //set up tree
    RedBlackTree<Integer> test4 = new RedBlackTree<>();
    //Insert nodes
    test4.insert(93);
    test4.insert(97);
    test4.insert(87);
    test4.insert(72);
    test4.insert(66);
    test4.insert(51);
    test4.insert(43);
    test4.insert(37);

    //Get expected and actual strings then compare
    String expected = "[ 72(b), 51(r), 93(r), 43(b), 66(b), 87(b), 97(b), 37(r) ]";



    String actual = test4.root.toLevelOrderString();

    assertEquals(expected,actual);
  }
}
