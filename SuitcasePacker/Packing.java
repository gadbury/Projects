import java.util.ArrayList;

/**
 * Class used for packing a 2D suitcase with items using various strategies.
 */
public class Packing {
  /**
   * Tries to pack each item in the items list in-order.
   * If an item can fit, it must be packed. Otherwise, it should be skipped.
   * Must be a recursive method.
   *
   * @param suitcase current Suitcase object
   * @return a Suitcase representing the outcome of a strategy in which the items
   * were attempted to be packed in-order.
   */
  public static Suitcase rushedPacking(Suitcase suitcase) {
    //Base case if all items have been packed
    if(suitcase.numItemsUnpacked() == 0){
      return suitcase;
    }

    //Iterates through unpacked objects in order
    for(int i = 0; i < suitcase.numItemsUnpacked(); i++){
      Item currItem = suitcase.getUnpackedItems().get(i);
      //If next item can be packed
      if(suitcase.canPackItem(currItem)){
        //Item is packed into suitcase and rushedPacking is recursively called to add addtional
        //items in order
        suitcase = suitcase.packItem(currItem);
        suitcase = rushedPacking(suitcase);
      }
    }

    //Base case if no more items fit
    return suitcase;
  }

  /**
   * Packs items by greedily packing the largest item which can currently be packed.
   * Must be a recursive method.
   *
   * @param suitcase current Suitcase object
   * @return a Suitcase representing the outcome of a greedy strategy in which at each
   * point the largest item that can fit is packed.
   */
  public static Suitcase greedyPacking(Suitcase suitcase) {
    //Base case if all items have been packed
    if(suitcase.numItemsUnpacked() == 0){
      return suitcase;
    }

    //Make an arrayList of all objects from largest to smallest
    ArrayList<Item> copyOfUnpacked = new ArrayList<Item>(suitcase.getUnpackedItems());
    ArrayList<Item> orderedUnpacked = new ArrayList<Item>();

    while(copyOfUnpacked.size() > 0){
      int largestIndex = 0;
      //finds the largest index in copy of unpacked
      for(int j = 0; j < copyOfUnpacked.size(); j++){
        if(copyOfUnpacked.get(j).height*copyOfUnpacked.get(j).width >
            copyOfUnpacked.get(largestIndex).height*copyOfUnpacked.get(largestIndex).width){
          largestIndex = j;
        }
      }
      //Adds largest item in the list at the end, making the list go in descending size
      orderedUnpacked.add(copyOfUnpacked.get(largestIndex));
      copyOfUnpacked.remove(largestIndex);
    }

    //Iterates through unpacked items from largest to smallest using orderedUnpacked
    for(int i = 0; i < orderedUnpacked.size(); i++){
      //If the next largest item in the list is still in the original unpacked list and is able to
      //get packed into the suitcase
      if(suitcase.getUnpackedItems().contains(orderedUnpacked.get(i)) &&
      suitcase.canPackItem(orderedUnpacked.get(i))){
        //largest available item gets added to list
        suitcase = suitcase.packItem(orderedUnpacked.get(i));
        //item is removed from orderedUnpacked
        orderedUnpacked.remove(i);
        //greedyPacking is recursively called to add the next largest item that can possibly
        //be added to list
        suitcase = greedyPacking(suitcase);
      }
    }

    //once all possible items are added, suitcase is returned
    return suitcase;
  }

  /**
   * Finds the optimal packing of items by trying all packing orders.
   * Must be a recursive method.
   *
   * @param suitcase current Suitcase
   * @return a Suitcase representing the optimal outcome.
   */
  public static Suitcase optimalPacking(Suitcase suitcase) {
    //Base case to return if all items have been added
    if(suitcase.numItemsUnpacked() == 0){
      return suitcase;
    }
    //Creates list of suitcases to be filled with all possible suitcases for current call
    ArrayList<Suitcase> suitcases = new ArrayList<Suitcase>();
    //For loop iterates through all possible items to be packed in the current state of
    //suitcase
    for(int i = 0; i < suitcase.numItemsUnpacked(); i++){
      //creates new suitcase object to represent one possible combination
      Suitcase suitcaseIterate = suitcase;

      //Checks if suitcase can add item
      if(suitcaseIterate.canPackItem(suitcaseIterate.getUnpackedItems().get(i))){
        //item is added to suitcase
        suitcaseIterate = suitcaseIterate.packItem(suitcaseIterate.getUnpackedItems().get(i));

        //optimalPacking is recursively called to build on all possibillities stemming from
        //suitcaseIterate and best possible combination is returned and added to suitcases
        Suitcase suitcaseIteratePacked = optimalPacking(suitcaseIterate);
        suitcases.add(suitcaseIteratePacked);
      }
    }
    //original suitcase is added to account for the case where nothing is added on to suitcase
    //in previous loop
    suitcases.add(suitcase);

    //Loop iterates through suitcases to find most optimal combination present
    int optimalPackedIndex = 0;
    for(int i = 0; i < suitcases.size(); i++){
      if(suitcases.get(i).areaPacked() > suitcases.get(optimalPackedIndex).areaPacked()){
        optimalPackedIndex = i;
      }
    }

    //most optimal combination is returned
    return suitcases.get(optimalPackedIndex);
  }
}
