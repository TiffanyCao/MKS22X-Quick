import java.util.*;

public class Quick{

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
     //at the start, setup
    Random choose = new Random(); //choose random index for pivot
    //int pivotIndex = choose.nextInt((ending - starting) + 1) + starting;
    int pivotIndex = (((ending-starting) / 2) + starting);
    if((data[starting] >= data[ending] && data[starting] <= data[pivotIndex]) || (data[starting] >= data[pivotIndex] && data[starting] <= data[ending])){
      pivotIndex = starting; //cases where the value at the start is the median
    }else if((data[ending] >= data[starting] && data[ending] <= data[pivotIndex]) || (data[ending] >= data[pivotIndex] && data[ending] <= data[starting])){
      pivotIndex = ending; //cases where the value at the end is the median
    } //otherwise, the value at the middle is the median
    int pivot = data[pivotIndex]; //store pivot
    data[pivotIndex] = data[starting]; //swap pivot with value at index zero
    data[starting] = pivot;
    pivotIndex = starting;
    int start = starting+1; //set the start to one greater than the start
    int end = ending; //end stays the same
    while(start != end){ //start = end is the base case
      if(data[start] >= pivot){ //if the value is greater than or equal to the pivot
        int chance = choose.nextInt() % 2; //fifty-fifty chance of swapping it to the other side
        if((chance == 0 && data[start] == pivot) || data[start] > pivot){ //if the value is equal to the pivot or greater than the pivot
          int temp = data[start];
          data[start] = data[end];
          data[end] = temp;
          end--; //end decreases because the value is now in the right place
        }
      }else start++; //start moves right if the value is in its correct side
    }
    //System.out.println(start + " " + end);
    if(data[start] < pivot){ //if the value at the middle index is less than the pivot, switch with pivot
      int temp = data[start];
      data[start] = pivot;
      data[pivotIndex] = temp;
      pivotIndex = start;
      return start; //return pivot's new index
    }else{ //if the value at the middle index is greater than the pivot, switch pivot with value at middle index - 1
      int temp = data[start-1];
      data[start-1] = pivot;
      data[pivotIndex] = temp;
      pivotIndex = start - 1;
      return start-1; //return pivot's new index
    }
    //if(starting >= ending) return starting-1;
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
    quicksortInsertion(data, 0, data.length - 1); //helper function
  }

  /**A recursive helper method that partitions and sorts repeatedly until a certain length, during which insertion sort is used
  *@param int[] data
  *@param int start is the start of the partition
  *@param int end is the end of the partition
  */
  public static void quicksortInsertion(int[] data, int start, int end){
    if(start < end && end != start && (end - start) > 10){ //if the length of the partition is greater than 10
      //as long as start and end are not equal, the partition range will be greater than 1, which means more can be partitioned
      int pivot = partition(data, start, end); //partition once to get a pivot
      quicksortInsertion(data, start, pivot - 1); //partition from the start to the pivot
      quicksortInsertion(data, pivot + 1, end); //partition from the pivot + 1 to the end;
    }else{ //otherwise, use insertion
      insertionsort(data, start, end); //use insertion sort on the array within the given range
    }
  }

  /**A recursive helper method that partitions and sorts repeatedly until all values are sorted
  *@param int[] data
  *@param int start is the start of the partition
  *@param int end is the end of the partition
  */
  public static void quicksortH(int[] data, int start, int end){
    if(start < end && end != start){ //eventually the start and end will be the same when the length of the partition range becomes one
      //as long as start and end are not equal, the partition range will be greater than 1, which means more can be partitioned
      int pivot = partition(data, start, end); //partition once to get a pivot
      quicksortH(data, start, pivot - 1); //partition from the start to the pivot
      quicksortH(data, pivot + 1, end); //partition from the pivot + 1 to the end;
    }
  }

  /**A method of insertion sort that only sorts within the given range of an array
  *@param int[] data
  *@param int lo the lower index of the range to be sorted, inclusive
  *@param int hi the upper index of the range to be sorted, inclusive
  */
  public static void insertionsort(int[] data, int lo, int hi){
    for(int i = lo + 1; i <= hi; i++){ //insertion within the bounds of lo and hi
      //System.out.println(i);
      //System.out.println(lo);
      int x;
      int temp = data[i]; //store the value to be compared
      for(x = i - 1; (x >= lo) && (data[x] > temp); x--){ //if the values before it are greater than the value being compared...
        //System.out.println(x);
        data[x+1] = data[x]; //shift the values to the right
      }
      data[x+1] = temp; //when the value is at the correct index, set it down
    }
  }

  public static void main(String[] args){
    int[] test1 = {999, 999, 999, 4, 1, 0, 3, 2, 999, 999, 999};
    //System.out.println(quickselect(test1, 0)); //0
    //System.out.println(quickselect(test1, 1)); //1
    //System.out.println(quickselect(test1, 2)); //2
    //System.out.println(quickselect(test1, 3)); //3
    //System.out.println(quickselect(test1, 4)); //4
    System.out.println(quickselect(test1, 5) + "\n"); //999
    System.out.println(print(test1) + "\n");
    quicksort(test1);
    System.out.println(print(test1) + "\n"); //[0, 1, 2, 3, 4, 999, 999, 999, 999, 999, 999]


    Random num = new Random();
    int[] test2 = new int[100];
    for(int i = 0; i < test2.length; i++){
      test2[i] = num.nextInt() % 1000;
    }

    System.out.println(print(test2) + "\n");
    System.out.println(quickselect(test2, 0) + "\n");
    System.out.println(print(test2) + "\n");
    quicksort(test2);
    System.out.println(print(test2) + "\n");

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
      int[] corr = new int[i];
      int[] test = new int[i];
      int temp = i-1;
      for(int y = 0; y < test.length; y++){
        test[y] = temp;
        corr[y] = temp;
        temp--;
      }
      //if(i == 9999) System.out.println(print(test));
      if(quickselect(test, 0) == 0 && quickselect(test, test.length-1) == i - 1){
        quicksort(test);
        Arrays.sort(corr);
        boolean order = true;
        for(int y = 0; y < test.length; y++){
          if(test[y] != corr[y]) order = false;
        }
        if(order){
          System.out.println("success " + i);
        }else{
          System.out.println("fail " + i);
        }
      }else{
        System.out.println("fail " + i);
      }
    }


    int[] test4 = new int[100];
    for(int i = 0; i < test4.length; i++){
      test4[i] = 6;
    }
    System.out.println(quickselect(test4, 0));
    System.out.println(quickselect(test4, test4.length - 1));

    int[] corr = new int[1000000];
    int[] ary = new int[1000000];
    Random number = new Random();
    int n = number.nextInt() % 1000;
    for(int i = 0; i < 1000000; i++){
      ary[i] = n;
      corr[i] = n;
      n = number.nextInt() % 1000;
    }
    Arrays.sort(corr); //0m0.282s for 1,000,000; 0m1.364s for 10,000,000
    quicksort(ary); //0m0.324s for 1,000.000; 0m1.871s for 10,000,000

    boolean order = true;
    for(int i = 0; i < corr.length; i++){
      if(ary[i] != corr[i] && order){
        order = false;
        System.out.println(i);
        System.out.println(ary[i]);
        System.out.println(corr[i]);
      }
    }
    System.out.println(order);


    /*int[] array = new int[1000000];
    Random numbers = new Random();
    for(int i = 0; i < array.length; i++){
      array[i] = numbers.nextInt();
    }
    */

    //Arrays.sort(array); //0m0.424s
    //quicksort(array); //0m0.520s

    /*int[] array = new int[10000000];
    Random numbers = new Random();
    for(int i = 0; i < array.length; i++){
      array[i] = numbers.nextInt();
    }
    */

    //Arrays.sort(array); //0m1.720s
    //quicksort(array); //0m3.238s

    int[] array = new int[100000000];
    Random numbers = new Random();
    for(int i = 0; i < array.length; i++){
      array[i] = numbers.nextInt();
    }

    //Arrays.sort(array); //0m12.223s
    //quicksort(array); //0m32.393s

  }
}
