import java.util.Arrays;

/**
 * 鸡尾酒排序
 */
public class CocktailSort {
    public static void main(String[] args) {
        int[] arr = new int[]{1,5,6,8,6,3,1,7,9};
        sort(arr);
    }

    public static void sort(int[] arr){
        int temp;
        for (int i = 0; i < arr.length / 2; i++) {
            boolean isSorted = true;
            //奇数论，从左向右排
            for (int j = i; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j+ 1]){
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    isSorted = false;
                }
            }
            if (isSorted){
                break;
            }
            isSorted = true;
            //偶数论，从右向左排
            for (int j = arr.length - 1 - i; j > i; j--) {
                if (arr[j] < arr[j-1]){
                    temp = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = temp;
                    isSorted = false;
                }
            }
            if (isSorted){
                break;
            }

        }
        System.out.println(Arrays.toString(arr));
    }
}
