package Services;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Sorting {
    private static final Random RANDOM = new Random();
    public static void main(String[] args) {
        int[] array = generateArray(100_000);
        double timeForBubbleSorting = sort(array, Sorting::sortBubble, 3);
        double timeForSelectionSorting = sort(array, Sorting::sortSelection, 3);
        double timeForInsertionSorting = sort(array, Sorting::sortInsertion, 3);
        System.out.println(timeForBubbleSorting);
        System.out.println(timeForSelectionSorting);
        System.out.println(timeForInsertionSorting);
    }

    private static int[] generateArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = RANDOM.nextInt();
        }
        return arr;
    }

    private static double sort(int[] arr,
                               Consumer<int[]> sortingAlgorithm,
                               int count) {
        return Stream.iterate(1, i -> i + 1)
                .limit(count)
                .map(i -> Arrays.copyOf(arr, arr.length))
                .mapToLong(array -> sort(sortingAlgorithm, array))
                .average()
                .getAsDouble();
    }
    private static long sort(Consumer<int[]> sortingAlgorithm,
                             int[]arr) {
        long start = System.currentTimeMillis();
        sortingAlgorithm.accept(arr);
        return System.currentTimeMillis() - start;
    }


    public static void sortBubble(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swapElements(arr, j, j + 1);
                }
            }
        }
    }

    private static void swapElements(int[] arr, int indexA, int indexB) {
        int tmp = arr[indexA];
        arr[indexA] = arr[indexB];
        arr[indexB] = tmp;
    }

    public static void sortSelection(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minElementIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minElementIndex]) {
                    minElementIndex = j;
                }
            }
            swapElements(arr, i, minElementIndex);
        }
    }

    public static void sortInsertion(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i;
            while (j > 0 && arr[j - 1] >= temp) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = temp;
        }
    }

}
