package test;

import org.junit.Test;

import java.util.Arrays;
import java.util.Scanner;

public class Sort {
    public static Scanner scanner = new Scanner(System.in);

    public static void insertSort(int[] array) {
        if (array == null) {
            return;
        }
        for (int i = 1; i < array.length; ++i) {
            int val = array[i];
            int j = i - 1;
            for (; j >= 0; --j) {
                if (array[j] > val) {
                    array[j + 1] = array[j];
                } else {
                    break;
                }
            }
            array[j + 1] = val;
        }
    }

    public static void selectSort(int[] array) {
        if (array == null) {
            return;
        }
        for (int i = 0; i < array.length; ++i) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; ++j) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            int tmp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = tmp;
        }
    }

    public static void merge(int[] array, int left, int mid, int right) {
        if (array == null || left >= right) {
            return;
        }
        int low = left;
        int high = mid + 1;
        int[] arr = new int[right - left + 1];
        int cur = 0;
        while (low <= mid && high <= right) {
            if (array[low] <= array[high]) {
                arr[cur++] = array[low++];
            } else {
                arr[cur++] = array[high++];
            }
        }

        while (low <= mid) {
            arr[cur++] = array[low++];
        }
        while (high <= right) {
            arr[cur++] = array[high++];
        }

        for (int i = 0; i < arr.length; ++i) {
            array[left + i] = arr[i];
        }
    }

    public static void mergeSort(int[] array, int left, int right) {
        if (array == null || left >= right) {
            return;
        }
        int mid = (left + right) / 2;
        mergeSort(array, left, mid);
        mergeSort(array, mid + 1, right);
        merge(array, left, mid, right);
    }

    public static void adjustHeap(int[] array, int parent, int end) {
        if (array == null || parent >= end) {
            return;
        }
        int val = array[parent];
        int child = 2 * parent + 1;
        while (child <= end) {
            if (child + 1 <= end && array[child + 1] > array[child]) {
                ++child;
            }
            if (array[child] <= val) {
                break;
            }
            array[parent] = array[child];
            parent = child;
            child = 2 * child + 1;
        }
        array[parent] = val;
    }

    public static void heapSort(int[] array) {
        if (array == null) {
            return;
        }

        for (int i = (array.length + 1) / 2; i >= 0; --i) {
            adjustHeap(array, i, array.length - 1);
        }

        for (int i = 0; i < array.length; ++i) {
            int tmp = array[0];
            array[0] = array[array.length - 1 - i];
            array[array.length - 1 - i] = tmp;
            adjustHeap(array, 0, array.length - 2 - i);
        }
    }

    public static void bubbleSort(int[] array) {
        if (array == null) {
            return;
        }
        for (int i = 0; i < array.length - 1; ++i) {
            for (int j = array.length - 1; j > i; --j) {
                if (array[j - 1] > array[j]) {
                    int tmp = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = tmp;
                }
            }
        }
    }


    public static void shellSort(int[] array) {
        if (array == null) {
            return;
        }
        for (int len = array.length / 2; len > 0; --len) {
            for (int i = len; i < array.length; ++i) {
                int j = i - len;
                int val = array[i];
                for (; j >= 0; j -= len) {
                    if (array[j] > val) {
                        array[j + len] = array[j];
                    } else {
                        break;
                    }
                }
                array[j + len] = val;
            }
        }
    }

    public static void shellSort2(int[] array) {
        if (array == null) {
            return;
        }
        for (int len = array.length / 2; len > 0; len /= 2) {
            for (int i = len; i < array.length; ++i) {
                int j = i - len;
                int val = array[i];
                for (; j >= 0; j -= len) {
                    if (array[j] > val) {
                        array[j + len] = array[j];
                    } else {
                        break;
                    }
                }
                array[j + len] = val;
            }
        }
    }

    public static int[] getNext(String pattern) {
        int[] ans = new int[pattern.length()];
        ans[0] = -1;
        int k = -1;
        for (int i = 1; i < pattern.length(); ++i) {
            while (k != -1 && pattern.charAt(k + 1) != pattern.charAt(i)) {
                k = ans[k];
            }
            if (pattern.charAt(k + 1) == pattern.charAt(i)) {
                ++k;
            }
            ans[i] = k;
        }
        return ans;
    }

    public static int kmp(String source, String pattern) {
        int[] next = getNext(pattern);
        int k = -1;
        for (int i = 0; i < source.length(); ++i) {
            while (k != -1 && pattern.charAt(k + 1) != source.charAt(i)) {
                k = next[k];
            }
            if (pattern.charAt(k + 1) == source.charAt(i)) {
                ++k;
            }
            if (k == next.length - 1) {
                return i - k;
            }
        }
        return -1;
    }

    @Test public void test() {
        String source = "abcdededbbdededjj";
        String pattern = "dedede";
        System.out.println(kmp(source, pattern));
    }

    public static int[] input() {
        int[] ans = new int[scanner.nextInt()];
        for (int i = 0; i < ans.length; ++i) {
            ans[i] = scanner.nextInt();
        }
        return ans;
    }

    public static void print(int[] array) {
        for (int i = 0; i < array.length; ++i) {
            System.out.print(" " + array[i]);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // 10 43 3241 1 0 541 3 4 90 3 9
        while (true) {
            int[] arr = input();
            int[] tmpArr = Arrays.copyOf(arr, arr.length);
            insertSort(tmpArr);
            print(tmpArr);
            tmpArr = Arrays.copyOf(arr, arr.length);
            selectSort(tmpArr);
            print(tmpArr);
            tmpArr = Arrays.copyOf(arr, arr.length);
            mergeSort(tmpArr, 0, tmpArr.length - 1);
            print(tmpArr);
            tmpArr = Arrays.copyOf(arr, arr.length);
            heapSort(tmpArr);
            print(tmpArr);
            tmpArr = Arrays.copyOf(arr, arr.length);
            bubbleSort(tmpArr);
            print(tmpArr);

            tmpArr = Arrays.copyOf(arr, arr.length);
            shellSort2(tmpArr);
            print(tmpArr);
        }
    }
}
