package Aufgabe1;



public class MergeSort {


    static String arrayToString(int[] arr){
        String s = "";
        s = s + "[";
        for (int i = 0; i < arr.length; i++) {
            s = s + arr[i];
            if (i + 1 != arr.length)
                s = s + ", ";
        }
        s = s + "]";
        return s;
    }

    public static int[] merge(int[] leftArray, int[] rightArray){
        int leftLen = leftArray.length;
        int rightLen = rightArray.length;

        int[] result = new int[leftLen + rightLen]; //result array musst have a length sum of leftArray + rightArray

        int rightIndex = 0;
        int leftIndex = 0;
        int resultIndex = 0;

        //none array is empty!
        while(leftIndex < leftLen && rightIndex < rightLen){
            if (leftArray[leftIndex] < rightArray[rightIndex]){
                result[resultIndex] = leftArray[leftIndex];
                leftIndex++;
            }else{
                result[resultIndex] = rightArray[rightIndex];
                rightIndex++;
            }
            resultIndex++;
        }
        //right array is empty but left array not
        while(leftIndex < leftLen){
            result[resultIndex] = leftArray[leftIndex];
            leftIndex++;
            resultIndex++;
        }

        //left array is empty but right array not
        while(rightIndex < rightLen){
            result[resultIndex] = rightArray[rightIndex];
            rightIndex++;
            resultIndex++;
        }

        return result;
    }

    public static int[] mergeSort(int[] array){
        int arrLen = array.length;
        if (array.length < 2){
            return array;
        }else{
            //split array to two arrays
            int[] left = new int[((arrLen + 1) / 2)];
            int[] right = new int[(arrLen - left.length)];

            //left array
            for (int i = 0; i < left.length; i++){
                left[i] = array[i];
            }

            //right array
            int rightIndex = 0;
            for (int i = left.length; i < array.length; i++){
                right[rightIndex] = array[i];
                rightIndex++;
            }

            //recursive
            int[] leftSorted = mergeSort(left);
            int[] rightSorted = mergeSort(right);

            return merge(leftSorted, rightSorted);
        }
    }

    public static void main(String[] args){
        int[] bsp = {1,2,3,4,5};
        System.out.print("Aufgabe 1 a): ");
        System.out.println(arrayToString(bsp));

        System.out.println("\n");

        System.out.println("Aufgabe 1 b) : ");
        int[] leftList = {1,3,5,7};
        int[] rightList = {2,4,6,7};
        int[] result = merge(leftList , rightList);
        System.out.println(arrayToString(result));

        int[] leftList1 = {3, 7, 9, 15};
        int[] rightList1 = {2, 5, 12, 16 };
        int[] result1 = merge(leftList1 , rightList1);
        System.out.println(arrayToString(result1));

        int[] leftList2 = {1, 9, 11, 13};
        int[] rightList2 = {4, 8};
        int[] result2 = merge(leftList2 , rightList2);
        System.out.println(arrayToString(result2));

        int[] leftList3 = {1, 2, 3, 5, 7};
        int[] rightList3 = {4, 6};
        int[] result3 = merge(leftList3 , rightList3);
        System.out.println(arrayToString(result3));

    }
}
