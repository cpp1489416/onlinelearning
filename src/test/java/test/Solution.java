package test;
public class Solution {
    int ans = 0;

    public void plus(int count) {
        ans += count;
        ans %= 1000000007;
    }

    public void merge(int[] array, int left, int mid, int right) {
        if (left >= right || mid > right) {
            return;
        }
        int[] arr = new int[right - left + 1];
        int low = left;
        int high = mid + 1;
        int index = 0;
        while (low <= mid && high <= right) {
            if (array[low] <= array[high]) {
                arr[index++] = array[low++];
            } else {
                plus(high - left - index);
                arr[index++] = array[high++];
            }
        }
        while (low <= mid) {
            arr[index++] = array[low++];
        }
        while (high <= right) {
            arr[index++] = array[high++];
        }
        for (int i = 0; i < arr.length; ++i) {
            array[left + i] = arr[i];
        }
    }

    public void mergeSort(int[] array, int left, int right) {
        if (array == null || left >= right) {
            return ;
        }
        int mid = (left + right) / 2;
        mergeSort(array, left, mid);
        mergeSort(array, mid + 1, right);
        merge(array, left, mid, right);
    }

    public int InversePairs(int [] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        ans = 0;
        mergeSort(array, 0, array.length - 1);
        return ans;
    }
}

