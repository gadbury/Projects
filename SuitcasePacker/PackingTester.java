import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.fail;

/**
 * Class used for testing the methods in the Packing class.
 */
public class PackingTester {
  /**
   * Tester method for the Packing.rushedPacking() method base cases.
   * It should test at least the following scenarios:
   * - There are no items left to pack in the suitcase
   * - There are items left to pack, but none of them fit
   * @return true if all tests pass, false otherwise
   */
  @Test
  public void rushedPackingBaseTest() {
    //Tests adding 1 item to full suitcase
    ArrayList<Item> rpbItems1 = new ArrayList<Item>();
    Item box1 = new Item("box",3,3);
    Item wontFit = new Item("wontfit",1,1);
    rpbItems1.add(box1);
    rpbItems1.add(wontFit);
    Suitcase rpbSuitcase1 = new Suitcase(3,3,rpbItems1);

    Suitcase rpbSuitcase1Correct = new Suitcase(3,3,rpbItems1);
    rpbSuitcase1Correct = rpbSuitcase1Correct.packItem(box1);

    Suitcase rpbSuitcase1Packed = Packing.rushedPacking(rpbSuitcase1);
    if(!rpbSuitcase1Packed.equals(rpbSuitcase1Correct)){
      System.out.println("RPB1");
      fail();
    }

    //Tests when there's nothing left to add
    ArrayList<Item> rpbItems2 = new ArrayList<Item>();
    Item box2 = new Item("box",3,3);
    rpbItems2.add(box2);
    Suitcase rpbSuitcase2 = new Suitcase(3,3,rpbItems2);

    Suitcase rpbSuitcase2Correct = new Suitcase(3,3,rpbItems2);
    rpbSuitcase2Correct = rpbSuitcase2Correct.packItem(box2);

    Suitcase rpbSuitcase2Packed = Packing.rushedPacking(rpbSuitcase2);
    if(!rpbSuitcase2Packed.equals(rpbSuitcase2Correct)){
      System.out.println("RPB2");
      fail();
    }

    //Tests adding multiple items to full suitcase
    ArrayList<Item> rpbItems3 = new ArrayList<Item>();
    Item box3 = new Item("box",3,3);
    rpbItems3.add(box3);
    Item wontFit2 = new Item("wontfit",1,9);
    Item wontFit3 = new Item("wontfit",2,6);
    Item wontFit4 = new Item("wontfit",4,3);
    Item wontFit5 = new Item("wontfit",3,1);
    rpbItems3.add(wontFit2);
    rpbItems3.add(wontFit3);
    rpbItems3.add(wontFit4);
    rpbItems3.add(wontFit5);
    Suitcase rpbSuitcase3 = new Suitcase(3,3,rpbItems3);
    Suitcase rpbSuitcase3Packed = Packing.rushedPacking(rpbSuitcase3);

    Suitcase rpbSuitcase3Correct = new Suitcase(3,3,rpbItems3);
    rpbSuitcase3Correct = rpbSuitcase3Correct.packItem(box3);


    if(!rpbSuitcase3Packed.equals(rpbSuitcase3Correct)){
      System.out.println("RPB3");
      fail();
    }
  }

  /**
   * Tester method for the Packing.rushedPacking() method recursive cases.
   * It should test at least the following scenarios:
   * - All the items remaining can fit in the suitcase
   * - At least one item remaining cannot fit in the suitcase
   * @return true if all tests pass, false otherwise
   */
  @Test
  public void rushedPackingRecursiveTest() {

    //Tests if items fit perfectly
    ArrayList<Item> rprItems1 = new ArrayList<Item>();

    Item perfectFit1 = new Item("perfectFit",2,2);
    Item perfectFit2 = new Item("perfectFit",4,2);
    Item perfectFit3 = new Item("perfectFit",1,2);
    Item perfectFit4 = new Item("perfectFit",1,2);
    rprItems1.add(perfectFit1);
    rprItems1.add(perfectFit2);
    rprItems1.add(perfectFit3);
    rprItems1.add(perfectFit4);

    Suitcase rprSuitcase1 = new Suitcase(4,4,rprItems1);

    Suitcase rprSuitcase1Packed = Packing.rushedPacking(rprSuitcase1);

    ArrayList<Item> rprCorrectItems1 = new ArrayList<Item>();
    Suitcase rprSuitcase1Correct = new Suitcase(4,4,rprItems1);
    rprSuitcase1Correct = rprSuitcase1Correct.packItem(perfectFit1);
    rprSuitcase1Correct = rprSuitcase1Correct.packItem(perfectFit2);
    rprSuitcase1Correct = rprSuitcase1Correct.packItem(perfectFit3);
    rprSuitcase1Correct = rprSuitcase1Correct.packItem(perfectFit4);


    if(!rprSuitcase1Packed.equals(rprSuitcase1Correct)){
      System.out.println("RPR1");
      fail();
    }


    //Tests if one item doesn't fit
    ArrayList<Item> rprItems2 = new ArrayList<Item>();
    Item Fit1 = new Item("Fit",2,1);
    Item Fit2 = new Item("Fit",4,2);
    Item Fit3 = new Item("Fit",1,1);
    Item Fit4 = new Item("Fit",1,1);
    Item noFit1 = new Item("noFit",3,3);
    rprItems2.add(Fit1);
    rprItems2.add(Fit2);
    rprItems2.add(Fit3);
    rprItems2.add(Fit4);
    rprItems2.add(noFit1);

    Suitcase rprSuitcase2 = new Suitcase(4,4,rprItems2);
    Suitcase rprSuitcase2Packed = Packing.rushedPacking(rprSuitcase2);

    ArrayList<Item> rprCorrectItems2 = new ArrayList<Item>();
    Suitcase rprSuitcase2Correct = new Suitcase(4,4,rprItems2);
    rprSuitcase2Correct = rprSuitcase2Correct.packItem(Fit1);
    rprSuitcase2Correct = rprSuitcase2Correct.packItem(Fit2);
    rprSuitcase2Correct = rprSuitcase2Correct.packItem(Fit3);
    rprSuitcase2Correct = rprSuitcase2Correct.packItem(Fit4);

    if(!rprSuitcase2Packed.equals(rprSuitcase2Correct)){
      System.out.println("RPR2");
      fail();
    }


    //Tests if multiple items don't fit
    ArrayList<Item> rprItems4 = new ArrayList<Item>();
    Item Fit5 = new Item("Fit",2,1);
    Item Fit6 = new Item("Fit",4,2);
    Item Fit7 = new Item("Fit",1,1);
    Item Fit8 = new Item("Fit",1,1);
    Item noFit2 = new Item("noFit",3,3);
    Item noFit3 = new Item("noFit",3,3);
    Item noFit4 = new Item("noFit",3,3);
    rprItems4.add(Fit5);
    rprItems4.add(Fit6);
    rprItems4.add(Fit7);
    rprItems4.add(Fit8);
    rprItems4.add(noFit2);
    rprItems4.add(noFit3);
    rprItems4.add(noFit4);
    Suitcase rprSuitcase4 = new Suitcase(4,4,rprItems4);
    Suitcase rprSuitcase4Packed = Packing.rushedPacking(rprSuitcase4);



    Suitcase rprSuitcase4Correct = new Suitcase(4,4,rprItems4);
    rprSuitcase4Correct = rprSuitcase4Correct.packItem(Fit5);
    rprSuitcase4Correct = rprSuitcase4Correct.packItem(Fit6);
    rprSuitcase4Correct = rprSuitcase4Correct.packItem(Fit7);
    rprSuitcase4Correct = rprSuitcase4Correct.packItem(Fit8);

    if(!rprSuitcase4Packed.equals(rprSuitcase4Correct)){
      System.out.println("RPR4");
      fail();
    }

    //Write Up Scenario
    ArrayList<Item> rprItems5 = new ArrayList<Item>();
    Item boxA = new Item("box",4,2);
    Item boxB = new Item("box",6,3);
    Item boxC = new Item("box",7,4);
    Item boxD = new Item("box",4,5);
    Item boxE = new Item("box",4,5);
    Item boxF = new Item("box",5,4);
    Item boxG = new Item("box",2,6);
    rprItems5.add(boxA);
    rprItems5.add(boxB);
    rprItems5.add(boxC);
    rprItems5.add(boxD);
    rprItems5.add(boxE);
    rprItems5.add(boxF);
    rprItems5.add(boxG);

    Suitcase rprSuitcase5 = new Suitcase(10,10,rprItems5);
    Suitcase rprSuitcase5Packed = Packing.rushedPacking(rprSuitcase5);

    Suitcase rprSuitcase5Correct = new Suitcase(10,10,rprItems5);
    rprSuitcase5Correct = rprSuitcase5Correct.packItem(boxA);
    rprSuitcase5Correct = rprSuitcase5Correct.packItem(boxB);
    rprSuitcase5Correct = rprSuitcase5Correct.packItem(boxC);
    rprSuitcase5Correct = rprSuitcase5Correct.packItem(boxD);

    if(!rprSuitcase5Packed.equals(rprSuitcase5Correct)){
      System.out.println("RPR5");
      fail();
    }
  }

  /**
   * Tester method for the Packing.greedyPacking() method base cases.
   * It should test at least the following scenarios:
   * - There are no items left to pack in the suitcase
   * - There are items left to pack, but none of them fit
   * @return true if all tests pass, false otherwise
   */
  @Test
  public void greedyPackingBaseTest() {
    //Tests adding 1 item to full suitcase
    ArrayList<Item> gpbItems1 = new ArrayList<Item>();
    Item box1 = new Item("box",3,3);
    Item wontFit = new Item("wontfit",1,1);
    gpbItems1.add(box1);
    gpbItems1.add(wontFit);
    Suitcase gpbSuitcase1 = new Suitcase(3,3,gpbItems1);

    Suitcase gpbSuitcase1Correct = new Suitcase(3,3,gpbItems1);
    gpbSuitcase1Correct = gpbSuitcase1Correct.packItem(box1);

    Suitcase gpbSuitcase1Packed = Packing.greedyPacking(gpbSuitcase1);
    if(!gpbSuitcase1Packed.equals(gpbSuitcase1Correct)){
      System.out.println("RPB1");
      fail();
    }

    //Tests when there's nothing left to add
    ArrayList<Item> gpbItems2 = new ArrayList<Item>();
    Item box2 = new Item("box",3,3);
    gpbItems2.add(box2);
    Suitcase gpbSuitcase2 = new Suitcase(3,3,gpbItems2);

    Suitcase gpbSuitcase2Correct = new Suitcase(3,3,gpbItems2);
    gpbSuitcase2Correct = gpbSuitcase2Correct.packItem(box2);

    Suitcase gpbSuitcase2Packed = Packing.greedyPacking(gpbSuitcase2);
    if(!gpbSuitcase2Packed.equals(gpbSuitcase2Correct)){
      System.out.println("RPB2");
      fail();
    }

    //Tests adding multiple items to full suitcase
    ArrayList<Item> gpbItems3 = new ArrayList<Item>();
    Item box3 = new Item("box",3,3);
    gpbItems3.add(box3);
    Item wontFit2 = new Item("wontfit",1,9);
    Item wontFit3 = new Item("wontfit",2,6);
    Item wontFit4 = new Item("wontfit",4,3);
    Item wontFit5 = new Item("wontfit",3,1);
    gpbItems3.add(wontFit2);
    gpbItems3.add(wontFit3);
    gpbItems3.add(wontFit4);
    gpbItems3.add(wontFit5);
    Suitcase gpbSuitcase3 = new Suitcase(3,3,gpbItems3);
    Suitcase gpbSuitcase3Packed = Packing.greedyPacking(gpbSuitcase3);

    Suitcase gpbSuitcase3Correct = new Suitcase(3,3,gpbItems3);
    gpbSuitcase3Correct = gpbSuitcase3Correct.packItem(box3);


    if(!gpbSuitcase3Packed.equals(gpbSuitcase3Correct)){
      System.out.println("RPB3");
      fail();
    }
  }

  /**
   * Tester method for the Packing.greedyPacking() method recursive cases.
   * It should test at least the following scenarios:
   * - At least one item is packed out of order (an item with a higher index
   *   is packed before an item with a lower index)
   * - A scenario where the greedy packing method packs more of the suitcase
   *   than the rushed packing (you can use the example given in the writeup)
   * @return true if all tests pass, false otherwise
   */
  @Test
  public void greedyPackingRecursiveTest() {
    //Write Up Scenario
    ArrayList<Item> gprItems1 = new ArrayList<Item>();
    Item boxA = new Item("box",4,2);
    Item boxB = new Item("box",6,3);
    Item boxC = new Item("box",7,4);
    Item boxD = new Item("box",4,5);
    Item boxE = new Item("box",4,5);
    Item boxF = new Item("box",5,4);
    Item boxG = new Item("box",2,6);
    gprItems1.add(boxA);
    gprItems1.add(boxB);
    gprItems1.add(boxC);
    gprItems1.add(boxD);
    gprItems1.add(boxE);
    gprItems1.add(boxF);
    gprItems1.add(boxG);

    Suitcase gprSuitcase1 = new Suitcase(10,10,gprItems1);
    Suitcase gprSuitcase1Packed = Packing.greedyPacking(gprSuitcase1);

    Suitcase gprSuitcase1Correct = new Suitcase(10,10,gprItems1);
    gprSuitcase1Correct = gprSuitcase1Correct.packItem(boxC);
    gprSuitcase1Correct = gprSuitcase1Correct.packItem(boxD);
    gprSuitcase1Correct = gprSuitcase1Correct.packItem(boxE);
    gprSuitcase1Correct = gprSuitcase1Correct.packItem(boxG);

    if(!gprSuitcase1Packed.equals(gprSuitcase1Correct)){
      System.out.println("GPR1");
      fail();
    }

    //Higher index scenario

    ArrayList<Item> gprItems2 = new ArrayList<Item>();
    Item box1 = new Item("box",4,2);
    Item box2 = new Item("box",6,3);
    Item box3 = new Item("box",7,4);
    gprItems2.add(box1);
    gprItems2.add(box2);
    gprItems2.add(box3);


    Suitcase gprSuitcase2 = new Suitcase(10,10,gprItems2);
    Suitcase gprSuitcase2Packed = Packing.greedyPacking(gprSuitcase2);

    Suitcase gprSuitcase2Correct = new Suitcase(10,10,gprItems2);
    gprSuitcase2Correct = gprSuitcase2Correct.packItem(box3);
    gprSuitcase2Correct = gprSuitcase2Correct.packItem(box2);
    gprSuitcase2Correct = gprSuitcase2Correct.packItem(box1);

    if(!gprSuitcase2Packed.equals(gprSuitcase2Correct)){
      System.out.println("GPR2");
      fail();
    }
  }


  /**
   *Private helper method that takes in a random suitcase and the result of putting that random
   * suitcase into optimalPacking and compares the result to 100 combinations of the random
   * suitcase and compares the result from optimalPacking to each combination
   *
   * @param randomSuitcase The suitcase object being used for testing
   * @param packedSuitcase Result of running randomSuitcase through optimalPacking
   * @return Whether optimalPacking was better than all 100 random combinations
   */



  /**
   * Tester method for the Packing.optimalPacking() method.
   *
   * This tester should test the optimalPacking() method by
   * randomly generating at least TEN (10) different scenarios,
   *
   * and randomly generating at least ONE-HUNDRED (100)
   * different packing solutions for EACH of the scenarios.
   *
   * Each scenario should have at least FIVE (5) random items,
   * and the suitcases should be of size at least 5x5.
   *
   * If any random solution is better than the optimal packing then
   * it is not actually optimal, so the method does not pass the test.
   * You should use the Utilities method to generate random lists of
   * items, and to randomly pack the suitcases.
   * @return true if all tests pass, false otherwise
   */
  public void optimalPackingRandomTest() {
    //Write Up Scenario
    ArrayList<Item> oprItems1 = new ArrayList<Item>();
    Item boxA = new Item("box",4,2);
    Item boxB = new Item("box",6,3);
    Item boxC = new Item("box",7,4);
    Item boxD = new Item("box",4,5);
    Item boxE = new Item("box",4,5);
    Item boxF = new Item("box",5,4);
    Item boxG = new Item("box",2,6);
    oprItems1.add(boxA);
    oprItems1.add(boxB);
    oprItems1.add(boxC);
    oprItems1.add(boxD);
    oprItems1.add(boxE);
    oprItems1.add(boxF);
    oprItems1.add(boxG);

    Suitcase oprSuitcase1 = new Suitcase(10,10,oprItems1);
    Suitcase oprSuitcase1Packed = Packing.optimalPacking(oprSuitcase1);

    Suitcase oprSuitcase1Correct = new Suitcase(10,10,oprItems1);
    oprSuitcase1Correct = oprSuitcase1Correct.packItem(boxC);
    oprSuitcase1Correct = oprSuitcase1Correct.packItem(boxD);
    oprSuitcase1Correct = oprSuitcase1Correct.packItem(boxA);
    oprSuitcase1Correct = oprSuitcase1Correct.packItem(boxF);
    oprSuitcase1Correct = oprSuitcase1Correct.packItem(boxG);

    if(!oprSuitcase1Packed.equals(oprSuitcase1Correct)){
      System.out.println("opr1");
      fail();
    }


    //Scenario 1 - 8 Items (9x9)
    ArrayList<Item> randomItems1 = Utilities.randomItems(8);
    Suitcase randomSuitcase1 = new Suitcase(9,9, randomItems1);
    ArrayList<Suitcase> randomSuitcases1 = new ArrayList<Suitcase>();

    Suitcase randomSuitcase1Packed = Packing.optimalPacking(randomSuitcase1);

    while(randomSuitcases1.size() < 100){
      Suitcase suitcase1Iteration = Utilities.randomlyPack(randomSuitcase1);
      if(!randomSuitcases1.contains(suitcase1Iteration)){
        randomSuitcases1.add(suitcase1Iteration);
        if(randomSuitcase1Packed.areaPacked() < suitcase1Iteration.areaPacked()){
          fail();
        }
      }
    }


    //Scenario 2 - 5 Items (5x5)

    ArrayList<Item> randomItems2 = Utilities.randomItems(5);
    Suitcase randomSuitcase2 = new Suitcase(5,5, randomItems2);
    ArrayList<Suitcase> randomSuitcases2 = new ArrayList<Suitcase>();

    Suitcase randomSuitcase2Packed = Packing.optimalPacking(randomSuitcase2);

    while(randomSuitcases2.size() < 100){
      Suitcase suitcase2Iteration = Utilities.randomlyPack(randomSuitcase2);
      if(!randomSuitcases2.contains(suitcase2Iteration)){
        randomSuitcases2.add(suitcase2Iteration);
        if(randomSuitcase2Packed.areaPacked() < suitcase2Iteration.areaPacked()){
          fail();
        }
      }
    }



    //Scenario 3 - 5 Items (20x20)

    ArrayList<Item> randomItems3 = Utilities.randomItems(5);
    Suitcase randomSuitcase3 = new Suitcase(20,20, randomItems3);
    ArrayList<Suitcase> randomSuitcases3 = new ArrayList<Suitcase>();

    Suitcase randomSuitcase3Packed = Packing.optimalPacking(randomSuitcase3);

    while(randomSuitcases3.size() < 100){
      Suitcase suitcase3Iteration = Utilities.randomlyPack(randomSuitcase3);
      if(!randomSuitcases3.contains(suitcase3Iteration)){
        randomSuitcases3.add(suitcase3Iteration);
        if(randomSuitcase3Packed.areaPacked() < suitcase3Iteration.areaPacked()){
          fail();
        }
      }
    }



    //Scenario 4 - 14 Items (5x5)

    ArrayList<Item> randomItems4 = Utilities.randomItems(14);
    Suitcase randomSuitcase4 = new Suitcase(5,5, randomItems4);
    ArrayList<Suitcase> randomSuitcases4 = new ArrayList<Suitcase>();

    Suitcase randomSuitcase4Packed = Packing.optimalPacking(randomSuitcase4);

    while(randomSuitcases4.size() < 100){
      Suitcase suitcase4Iteration = Utilities.randomlyPack(randomSuitcase4);
      if(!randomSuitcases4.contains(suitcase4Iteration)){
        randomSuitcases4.add(suitcase4Iteration);
        if(randomSuitcase4Packed.areaPacked() < suitcase4Iteration.areaPacked()){
          fail();
        }
      }
    }



    //Scenario 5 - 8 Items (20x5)

    ArrayList<Item> randomItems5 = Utilities.randomItems(8);
    Suitcase randomSuitcase5 = new Suitcase(20,5, randomItems5);
    ArrayList<Suitcase> randomSuitcases5 = new ArrayList<Suitcase>();

    Suitcase randomSuitcase5Packed = Packing.optimalPacking(randomSuitcase5);

    while(randomSuitcases5.size() < 100){
      Suitcase suitcase5Iteration = Utilities.randomlyPack(randomSuitcase5);
      if(!randomSuitcases5.contains(suitcase5Iteration)){
        randomSuitcases5.add(suitcase5Iteration);
        if(randomSuitcase5Packed.areaPacked() < suitcase5Iteration.areaPacked()){
          fail();
        }
      }
    }



    //Scenario 6 - 8 Items (5x20)

    ArrayList<Item> randomItems6 = Utilities.randomItems(8);
    Suitcase randomSuitcase6 = new Suitcase(5,20, randomItems6);
    ArrayList<Suitcase> randomSuitcases6 = new ArrayList<Suitcase>();

    Suitcase randomSuitcase6Packed = Packing.optimalPacking(randomSuitcase6);

    while(randomSuitcases6.size() < 100){
      Suitcase suitcase6Iteration = Utilities.randomlyPack(randomSuitcase6);
      if(!randomSuitcases6.contains(suitcase6Iteration)){
        randomSuitcases6.add(suitcase6Iteration);
        if(randomSuitcase6Packed.areaPacked() < suitcase6Iteration.areaPacked()){
          fail();
        }
      }
    }



    //Scenario 7 - 5 Items (9x9)

    ArrayList<Item> randomItems7 = Utilities.randomItems(5);
    Suitcase randomSuitcase7 = new Suitcase(9,9, randomItems7);
    ArrayList<Suitcase> randomSuitcases7 = new ArrayList<Suitcase>();

    Suitcase randomSuitcase7Packed = Packing.optimalPacking(randomSuitcase7);

    while(randomSuitcases7.size() < 100){
      Suitcase suitcase7Iteration = Utilities.randomlyPack(randomSuitcase7);
      if(!randomSuitcases7.contains(suitcase7Iteration)){
        randomSuitcases7.add(suitcase7Iteration);
        if(randomSuitcase7Packed.areaPacked() < suitcase7Iteration.areaPacked()){
          fail();
        }
      }
    }



    // Scenario 8 - 9 Items (10x10)

    ArrayList<Item> randomItems8 = Utilities.randomItems(9);
    Suitcase randomSuitcase8 = new Suitcase(10,10, randomItems8);
    ArrayList<Suitcase> randomSuitcases8 = new ArrayList<Suitcase>();

    Suitcase randomSuitcase8Packed = Packing.optimalPacking(randomSuitcase8);

    while(randomSuitcases8.size() < 100){
      Suitcase suitcase8Iteration = Utilities.randomlyPack(randomSuitcase8);
      if(!randomSuitcases8.contains(suitcase8Iteration)){
        randomSuitcases8.add(suitcase8Iteration);
        if(randomSuitcase8Packed.areaPacked() < suitcase8Iteration.areaPacked()){
          fail();
        }
      }
    }





    //Scenario 9 - 5 Items (7x9)

    ArrayList<Item> randomItems9 = Utilities.randomItems(5);
    Suitcase randomSuitcase9 = new Suitcase(7,9, randomItems9);
    ArrayList<Suitcase> randomSuitcases9 = new ArrayList<Suitcase>();

    Suitcase randomSuitcase9Packed = Packing.optimalPacking(randomSuitcase9);

    while(randomSuitcases9.size() < 100){
      Suitcase suitcase9Iteration = Utilities.randomlyPack(randomSuitcase9);
      if(!randomSuitcases9.contains(suitcase9Iteration)){
        randomSuitcases9.add(suitcase9Iteration);
        if(randomSuitcase9Packed.areaPacked() < suitcase9Iteration.areaPacked()){
          fail();
        }
      }
    }

    //Scenario 10 - 9 Items (5x9)

    ArrayList<Item> randomItems10 = Utilities.randomItems(7);
    Suitcase randomSuitcase10 = new Suitcase(5,9, randomItems10);
    ArrayList<Suitcase> randomSuitcases10 = new ArrayList<Suitcase>();

    Suitcase randomSuitcase10Packed = Packing.optimalPacking(randomSuitcase10);

    while(randomSuitcases10.size() < 100){
      Suitcase suitcase10Iteration = Utilities.randomlyPack(randomSuitcase10);
      if(!randomSuitcases10.contains(suitcase10Iteration)){
        randomSuitcases10.add(suitcase10Iteration);
        if(randomSuitcase10Packed.areaPacked() < suitcase10Iteration.areaPacked()){
          fail();
        }
      }
    }
  }
}
