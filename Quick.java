import java.util.*;

public class Quick{

    private static int pivotIndex;
    private static int pivot;
    private static int originalStart;
    private static int originalEnd;
    private static int start;
    private static int end;
    private static boolean toStart = true;

  /**A method that recursively calls to sort the array so all values greater than the randomly chosen pivot are to its right
  *and all values less than the randomly chosen pivot are to its left
  *
  *Modify the array such that:
  *1. Only the indices from start to end inclusive are considered in range
  *2. A random index from start to end inclusive is chosen, the corresponding
  *   element is designated the pivot element.
  *3. all elements in range that are smaller than the pivot element are placed before the pivot element.
  *4. all elements in range that are larger than the pivot element are placed after the pivot element.
  *@param int[] data
  *@param int starting
  *@param int ending
  *@return int the new index of the pivot
  *@return the index of the final position of the pivot element.
  */
  public static int partition(int[] data, int starting, int ending){
    if(data.length == 1) return 0; //special case
    if(toStart){ //at the start, setup
      Random choose = new Random(); //choose random index for pivot
      pivotIndex = choose.nextInt((ending - starting) + 1) + starting;
      //System.out.println(pivotIndex);
      pivot = data[pivotIndex]; //store pivot
      //System.out.println(pivot);
      data[pivotIndex] = data[starting]; //swap pivot with value at index zero
      data[starting] = pivot;
      pivotIndex = starting;
      start = starting+1; //set the start to one greater than the start
      end = ending; //end stays the same
      originalStart = starting;
      originalEnd = ending;
      toStart = false; //no need for setting up anymore
      //System.out.println(pivotIndex);
      //System.out.println(print(data));
      return partition(data, start, end); //recursive call
    }
    if(starting != ending && starting < ending){ //if more swaps can be tested and starting doesn't become more than ending
      //System.out.println("starting " + starting);
      //System.out.println("ending " + ending);
      //System.out.println("start " + start);
      //System.out.println("end " + end);
      while(data[start] > pivot && start <= originalEnd && end >= originalStart && start != end){
        //if the value at the start index is greater than the pivot and the start and end are within range and not equal to each other
        int temp = data[start]; //swap with value at end index
        data[start] = data[end];
        data[end] = temp;
        end--; //end index decreases
      }
      //System.out.println(print(data));
      if(start != end) start++; //start index increases after the value at current start index is less than the pivot, unless start is equal to end
      return partition(data, start, end); //recursive call with the new start and end
    }
    if(starting == ending){ //if no more swaps should be tested...
      //System.out.println("final starting " + starting);
      //System.out.println("final ending " + ending);
      //System.out.println("final start " + start);
      //System.out.println("final end " + end);
      if(data[start] < pivot){ //if the value at the middle index is less than the pivot, switch with pivot
        int temp = data[start];
        data[start] = pivot;
        data[pivotIndex] = temp;
        toStart = true; //set up becomes true so next partition can be done
        //System.out.println(print(data));
        //System.out.println(start);
        return start; //return pivot's new index
      }else{ //if the value at the middle index is greater than the pivot, switch pivot with value at middle index - 1
        int temp = data[start-1];
        data[start-1] = pivot;
        data[pivotIndex] = temp;
        toStart = true; //set up becomes true so next partition can be done
        //System.out.println(print(data));
        //System.out.println(start-1);
        return start-1; //return pivot's new index
      }
    }
    //if(starting >= ending) return starting-1;
    return -1; //so it compiles
  }

  /**A method that prints out the array
  *@return String
  */
  public static String print(int[] data){
    String result = "[";
    for(int i = 0; i < data.length; i++){
      if(i != data.length-1){
        result += data[i] + ", ";
      }else{
        result += data[i] + "]";
      }
    }
    return result;
  }

  /**A method that finds the kth smallest value of the array, using partition
  *@param int[] data
  *@param int k the index given
  *@return int the value at index k
  */
  public static int quickselect(int[] data, int k){
    quickselectH(data, 0, data.length-1, k); //helper call
    return data[k]; //return the value at index k
  }

  /**A method that finds the kth smallest value of the array, using partition
  *@param int[] data
  *@param int start is the starting range of partition
  *@param int end is the ending range of partition
  *@param int k is the index given
  */
  public static void quickselectH(int[] data, int start, int end, int k){
    if(start < end && end != start){ //if the partition range is still greater than 1
      //eventually partition range should just be the value at k
      int pivot = partition(data, start, end); //partition once and get a pivot
      if(k < pivot) quickselectH(data, start, pivot-1, k); //if k is smaller than the pivot, partition the array values before the pivot
      if(k > pivot) quickselectH(data, pivot+1, end, k); //if k is greater than the pivot, partition the array values after the pivot
    }
  }

  /**A method that sorts an array using partition
  *@param int[] data
  */
  public static void quicksort(int[] data){
    quicksortH(data, 0, data.length - 1); //helper function
  }

  /**A recursive helper method that partitions and sorts repeatedly until all values are sorted
  *@param int[] data
  *@param int start is the start of the partition
  *@param int end is the end of the partition
  */
  public static void quicksortH(int[] data, int start, int end){
    if(start < end && start != end){ //eventually the start and end will be the same when the length of the partition range becomes one
      //as long as start and end are not equal, the partition range will be greater than 1, which means more can be partitioned
      int pivot = partition(data, start, end); //partition once to get a pivot
      quicksortH(data, start, pivot); //partition from the start to the pivot
      quicksortH(data, pivot + 1, end); //partition from the pivot + 1 to the end;
    }
  }

  public static void main(String[] args){
    int[] test1 = {999, 999, 999, 4, 1, 0, 3, 2, 999, 999, 999};
    //System.out.println(quickselect(test1, 0)); //0
    //System.out.println(quickselect(test1, 1)); //1
    //System.out.println(quickselect(test1, 2)); //2
    //System.out.println(quickselect(test1, 3)); //3
    //System.out.println(quickselect(test1, 4)); //4
    //System.out.println(quickselect(test1, 5)); //999
    //System.out.println(print(test1));
    quicksort(test1);
    System.out.println(print(test1)); //[0, 1, 2, 3, 4, 999, 999, 999, 999, 999, 999]


    Random num = new Random();
    int[] test2 = new int[100];
    for(int i = 0; i < test2.length; i++){
      test2[i] = num.nextInt() % 1000;
    }

    System.out.println(print(test2));
    System.out.println(quickselect(test2, 0));
    System.out.println(print(test2));
    quicksort(test2);
    System.out.println(print(test2));


    int[] test3 = new int[1000];
    int tempTest3 = 999;
    for(int i = 0; i < test3.length; i++){
      test3[i] = tempTest3;
      tempTest3--;
    }
    System.out.println(quickselect(test3, 500));
    System.out.println(print(test3));
    quicksort(test3);
    System.out.println(print(test3));


    for(int i = 1; i < 10000; i++){
      int[] test = new int[i];
      int temp = i-1;
      for(int y = 0; y < test.length; y++){
        test[y] = temp;
        temp--;
      }
      if(i == 9999) System.out.println(print(test));
      if(quickselect(test, 0) == 0 && quickselect(test, test.length-1) == i - 1){
        System.out.println("success " + i);
      }else{
        System.out.println("fail " + i);
      }
      if(i == 9999){
        System.out.println(print(test));
        quicksort(test);
        System.out.println(print(test));
      }
    }

    int[] test4 = new int[100];
    for(int i = 0; i < test4.length; i++){
      test4[i] = 6;
    }
    System.out.println(quickselect(test4, 0));
    System.out.println(quickselect(test4, test4.length - 1));
  }

}
