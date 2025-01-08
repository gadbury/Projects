public class BinarySearchTree<T extends Comparable<T>> implements SortedCollection<T>{

  protected BSTNode<T> root;

  public BinarySearchTree(){
    root = null;
  }

  public void insert(T data) throws NullPointerException {
    //Throws exception if data is null
    if(data == null){
      throw new NullPointerException("data cannot be null");
    }
    //If BST is empty, new node becomes the root
    if (isEmpty()){
      root.setData(data);
    }
    //Otherwise, new node is created and insert helper is used
    else{
      BSTNode<T> newNode = new BSTNode(data);
      insertHelper(newNode, root);
    }
  }

  public boolean contains(Comparable<T> data) {
    //create variable to hold data of current node being checked
    BSTNode<T> currentNode = root;
    //Checks if root is data being searched for before traversing BST
    if(data.compareTo(root.data) == 0){
      return true;
    }
    //While loop loops until end of BST or match is found
    while(data.compareTo(currentNode.data) != 0){
      //If end of BST is reached without a match, return false
      if(currentNode == null){
        return false;
      }

      //if data being searched for is less than currentNode, check left child
      if(data.compareTo(currentNode.data) < 0){
        //If match is found, return true
        if(data.compareTo(currentNode.left.data) == 0){
          return true;
        }
        //If match isn't found, make child new current node
        else{
          currentNode = currentNode.left;
        }
      }
      //if data being searched for is greater than or equal to currentNode, check right child
      else{
        //If match is found, return true
        if(data.compareTo(currentNode.right.data) == 0){
          return true;
        }
        //If match isn't found, make child new current node
        else{
          currentNode = currentNode.right;
        }

      }
    }
    return false;
  }

  public int size(){
    int size = 0;
    
    return size;
  }

  public boolean isEmpty(){
    //if root is null, tree is empty
    return root == null;
  }

  public void clear(){
    //Root and both children are set to null, clearing the BST
    root.left.setData(null);
    root.right.setData(null);
    root = null;
  }

  /**
   * Performs the naive binary search tree insert algorithm to recursively
   * insert the provided newNode (which has already been initialized with a
   * data value) into the provided tree/subtree.  When the provided subtree
   * is null, this method does nothing.
   */
  protected void insertHelper(BSTNode<T> newNode, BSTNode<T> subtree) {
    //base case to end recursion when end of BST is reached
    if(subtree == null){
      return;
    }
    //create variables for node data
    T newNodeData = newNode.getData();
    T subtreeData = subtree.getData();
    //If newNode is less than the subtree root, it goes to the left
    if(newNodeData.compareTo(subtreeData) < 0){
      //If node has no left child, new node is inserted
      if(subtree.left == null){
        subtree.setLeft(newNode);
        newNode.setUp(subtree);
      }
      //otherwise, recursive call to continue traversing the tree
      else{
        insertHelper(newNode, subtree.left);
      }
    }
    //If newNode is greater than or equal to the subtree root, it goes to the right
    else{
      //If node has no right child, new node is inserted
      if(subtree.right == null){
        subtree.setRight(newNode);
        newNode.setUp(subtree);
      }
      //otherwise, recursive call to continue traversing the tree
      else{
        insertHelper(newNode, subtree.right);
      }
    }
  }
}
