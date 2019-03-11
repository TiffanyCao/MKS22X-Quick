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
  
}
