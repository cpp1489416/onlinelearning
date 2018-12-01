package test;

import org.junit.Test;

import java.util.Scanner;
import java.util.Stack;

public class Test1 {
    public static class Node {
        int val;
        Node next;

        Node() {
        }

        public Node(int val) {
            this.val = val;
        }

        public Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public void createRandomnLinkedList() {
        Node node = new Node();

    }

    public static Node reverse(Node head) {
        Node pre = null;
        Node cur = head;
        while (cur != null) {
            Node next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        return pre;
    }

    public static void remove(Node head, Node node) {
        if (head == null || node == null) {
            return;
        }

        if (node.next != null) {
            node.val = node.next.val;
            node.next = node.next.next;
        } else {
            Node cur = head;
            while (cur.next.next != null) {
                cur = cur.next;
            }
            cur.next = null;
        }
    }

    public static Node addNode(Node head, int val) {
        head.next = new Node(val);
        return head.next;
    }

    public static boolean isCross(String source) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < source.length(); ++i) {
            Character c = source.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                char o = stack.pop();
                if (o == '[' && c == ']' || o == '(' && c == ')' || o == '{' && c == '}') {
                    continue;
                } else {
                    return false;
                }
            }
        }

        if (!stack.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public static Node findReverseKthNode(Node head, int k) {
        Node ans = head;
        Node cur = head;
        int count = 0;
        for (int i = 0; cur != null; ++i) {
            if (i >= k) {
                ans = ans.next;
            }
            cur = cur.next;
            ++count;
        }
        if (k > count) {
            return null;
        }
        return ans;
    }

    public static void test1() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String s = scanner.next();
            System.out.println(isCross(s));
        }
    }

    @Test
    public void test2() {
        System.out.println("test2");
    }

    public static int getDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return Math.max(getDepth(root.left) + 1, getDepth(root.right) + 1);
    }

    public static int getDegree(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int count = getDegree(root.left) + getDegree(root.right);
        if (root.left != null) {
            ++count;
        }

        if (root.right != null) {
            ++count;
        }
        return count;
    }

    public static void merge(int[] array, int left, int mid, int right) {
        if (array == null || left >= right) {
            return;
        }
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

            if (array[child] > val) {
                array[parent] = array[child];
                parent = child;
            } else {
                break;
            }

            child = child * 2 + 1;
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
            System.out.print(" " + array[i]);
        }
        System.out.println();

        for (int i = 0; i < array.length; ++i) {
            int head = array[0];
            array[0] = array[array.length - 1 - i];
            array[array.length - 1 - i] = head;
            adjustHeap(array, 0, array.length - i - 2);
        }
    }

    @Test
    public void heapSortTest() {
        int array[] = {1, 2, 4, 5, 6, 7, 7, 8, 8, 10};
        heapSort(array);
        for (int i = 0; i < array.length; ++i) {
            System.out.print(" " + array[i]);
        }
    }

    public static class Exception1 extends Exception {

    }

    public static class Exception2 extends Exception1 {

    }

    public static class Exception3 extends Exception1 {

    }

    public static class Exception4 extends Exception {

    }

    public static class Father {
        void func() throws Exception1 {

        }
    }

    public static class Child extends Father {
        void func() throws Exception2, Exception3 {

        }
    }


}
