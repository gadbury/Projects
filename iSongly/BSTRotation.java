public class BSTRotation <T extends Comparable<T>> extends BinarySearchTree<T> {
  /**
   * Performs the rotation operation on the provided nodes within this tree.
   * When the provided child is a left child of the provided parent, this
   * method will perform a right rotation. When the provided child is a right
   * child of the provided parent, this method will perform a left rotation.
   * When the provided nodes are not related in one of these ways, this
   * method will either throw a NullPointerException: when either reference is
   * null, or otherwise will throw an IllegalArgumentException.
   *
   * @param child is the node being rotated from child to parent position
   * @param parent is the node being rotated from parent to child position
   * @throws NullPointerException when either passed argument is null
   * @throws IllegalArgumentException when the provided child and parent
   *     nodes are not initially (pre-rotation) related that way
   */
  protected void rotate(BSTNode<T> child, BSTNode<T> parent)
      throws NullPointerException, IllegalArgumentException {
    //If either arguments are null, throw a NullPointerException
    if(child == null || parent == null){
      throw new NullPointerException("Can't pass null argument");
    }

    //Check if a left rotation is being performed and avoid NullPointerException
    if(parent.right != null) {
      if (parent.right.equals(child)) {
        //Create variables to hold floating right subtree and grandparent if applicable
        BSTNode<T> leftGrandchild = null;
        BSTNode<T> grandparent = null;
        //If parent node is not the root, save grandparent
        if (!root.equals(parent)) {
          grandparent = parent.getUp();
        }
        //If child has left child, save then it can become parent's right child
        if (child.left != null) {
          leftGrandchild = child.left;
        }
        //Make parent child's left child
        child.setLeft(parent);
        parent.setUp(child);
        //make left grandchild parent's right child if applicable
        if (leftGrandchild != null) {
          parent.setRight(leftGrandchild);
          leftGrandchild.setUp(parent);
        }
        //If parent node was not the root, set child's new parent as grandparent & grandparent's
        //child as child`
        if (grandparent != null) {
          child.setUp(grandparent);
          if (child.data.compareTo(grandparent.data) > 0) {
            grandparent.setRight(child);
          } else {
            grandparent.setLeft(child);
          }
        }
        //If child is now root, remove up and set child as root
        if (child.up.equals(parent)) {
          child.setUp(null);
          root = child;
        }
        //Separate parent and child if still connected by original relationship while avoiding
        // NullPointer
        try{
          if(parent.left.equals(child)){
            parent.setLeft(null);
          }
        }catch (NullPointerException e){
        }
        try{
          if(parent.right.equals(child)){
            parent.setRight(null);
          }
        }catch (NullPointerException e){
        }
        return;
      }
    }
    //Check if a right rotation is being performed while avoiding NullPointerException
    if(parent.left != null){
      if(parent.left.equals(child)){
        //Create variables to hold floating left subtree and grandparent if applicable
        BSTNode<T> rightGrandchild = null;
        BSTNode<T> grandparent = null;
        //If parent node is not the root, save grandparent
        if (!root.equals(parent)){
          grandparent = parent.getUp();
        }
        //If child has right child, save then it can become parent's left child
        if (child.right != null){
          rightGrandchild = child.right;
        }
        //Make parent child's right child
        child.setRight(parent);
        parent.setUp(child);
        //make right grandchild parent's left child if applicable
        if (rightGrandchild != null){
          parent.setLeft(rightGrandchild);
          rightGrandchild.setUp(parent);
        }
        //If parent node was not the root, set child's new parent as grandparent & grandparent's
        //child as child`
        if(grandparent != null){
          child.setUp(grandparent);
          if(child.data.compareTo(grandparent.data) >= 0){
            grandparent.setRight(child);
          }
          else{
            grandparent.setLeft(child);
          }
        }
        //If child is now root, remove up and set child as root
        if(child.up.equals(parent)){
          child.setUp(null);
          root = child;
        }
        //Separate parent and child if still connected by original relationship while avoiding
        // NullPointer
        try {
          if (parent.left.equals(child)) {
            parent.setLeft(null);
          }
        }catch (NullPointerException e) {
        }
        try{
          if(parent.right.equals(child)){
            parent.setRight(null);
          }
        }catch (NullPointerException e){
        }
        return;
      }
    }
    //If nodes are unrelated, throw an IllegalArgumentException
    else{
      throw new IllegalArgumentException("Arguments must be related");
    }
  }



  //Left rotation including root & 3 shared children (E&H)
  public boolean test1(){
    BSTNode<T> E = root;
    BSTNode<T> H = root.right;
    rotate(H,E);
    if(!root.toLevelOrderString().equals("[ H, E, J, B, F, I, K, A, D, G, C ]")){
      System.out.println("String was " + root.toLevelOrderString() + "rather than" + "[ H, E, J, B, F, I, K, A, D, G, C ]");
      return false;
    }
    return true;
  }


  //Right rotation not including root & 0 shared children (D&C)
  public boolean test2(){
    BSTNode<T> C = root.left.right.left;
    BSTNode<T> D = root.left.right;
    rotate(C,D);
    if(!root.toLevelOrderString().equals("[ E, B, H, A, C, F, J, D, G, I, K ]")){
      System.out.println("String was " + root.toLevelOrderString() + "rather than" + "[ E, B, H, A, C, F, J, D, G, I, K ]");
      return false;
    }
    return true;
  }

  //Left rotation with 1 shared child (H&F)
  public boolean test3(){
    BSTNode<T> H = root.right;
    BSTNode<T> F = root.right.left;
    rotate(F,H);
    if(!root.toLevelOrderString().equals("[ E, B, F, A, D, H, C, G, J, I, K ]")){
      System.out.println("String was " + root.toLevelOrderString() + "rather than" + "[ E, B, F, A, D, H, C, G, J, I, K ]");
      return false;
    }
    return true;
  }

  //Left rotation with 2 shared children (H&J)
  public boolean test4(){
    BSTNode<T> H = root.right;
    BSTNode<T> J = root.right.right;
    rotate(J,H);
    if(!root.toLevelOrderString().equals("[ E, B, J, A, D, H, K, C, F, I, G ]")){
      System.out.println("String was " + root.toLevelOrderString() + "rather than" + "[ E, B, J, A, D, H, K, C, F, I, G ]");
      return false;
    }
    return true;
  }



  public static void main(String args[]){
    BSTRotation<String> test1 = new BSTRotation<>();
    System.out.println("Test 1: " + test1.test1());

    BSTRotation<String> test2 = new BSTRotation<>();
    System.out.println("Test 2: " + test2.test2());

    BSTRotation<String>test3 = new BSTRotation<>();
    System.out.println("Test 3: "+ test3.test3());

    BSTRotation<String>test4 = new BSTRotation<>();
    System.out.println("Test 4: "+ test4.test4());

  }
}
