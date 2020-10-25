import java.lang.reflect.Array;
import java.util.Arrays;

/**
 *冒泡排序
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = new int[]{1,5,6,8,6,3,1,7,9};
        sort(arr);
    }

    public static void sort(int[] arr){
        int temp;
        int lastExchange = 0;
        int sortBorder = arr.length - 1;
        for (int j = 0; j < arr.length - 1; j++) {
            boolean isSort = true;
            for (int i = 0; i < sortBorder; i++) {
                if (arr[i] > arr[i+1]){
                    temp = arr[i];
                    arr[i] = arr[i+1];
                    arr[i+1] = temp;
                    isSort = false;
                    lastExchange = i;
                }
            }
            sortBorder = lastExchange;
            if (isSort){
                break;
            }
        }
        System.out.println(Arrays.toString(arr));
    }
}
