package test;

import org.jetbrains.annotations.Nullable;

import java.util.Scanner;
import java.util.Stack;

public class BitTree extends Object {
    public static Scanner scanner = new Scanner(System.in);

    public static class Node {
        public int val;
        public Node left;
        public Node right;
    }

    public static void visit(Node node) {
        System.out.print(" " + node.val);
    }

    public static void preOrder(Node root) {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        Node cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                visit(cur);
                stack.push(cur);
                cur = cur.left;
            }
            if (!stack.isEmpty()) {
                cur = stack.pop();
                cur = cur.right;
            }
        }
    }

    public static void preOrder2(Node root) {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            visit(node);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
    }

    public static void inOrder(Node root) {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        Node cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            if (!stack.isEmpty()) {
                cur = stack.pop();
                visit(cur);
                cur = cur.right;
            }
        }
    }

    public static void inOrder2(Node root) {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        Node pre = null;
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            if (cur.left == null && cur.right == null ||
                    pre != null && (cur.left == pre || cur.right == pre) ||
                    !stack.isEmpty() && (cur.left == stack.peek() || cur.right == stack.peek())) {
                pre = cur;
                visit(cur);
                continue;
            }
            if (cur.right != null) {
                stack.push(cur.right);
            }
            stack.push(cur);
            if (cur.left != null) {
                stack.push(cur.left);
            }
        }
    }

    public static void postOrder(Node root) {
        if (root == null) {
            return;
        }
        Node cur = root;
        Node pre = null;
        Stack<Node> stack = new Stack<>();
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            if (!stack.isEmpty()) {
                Node node = stack.peek();
                if (node.right == null || node.right == pre) {
                    visit(node);
                    pre = node;
                    stack.pop();
                } else {
                    cur = node.right;
                }
            }
        }
    }

    public static void postOrder2(Node root) {
        if (root == null) {
            return;
        }
        Node pre =null;
        Stack<Node> stack  = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.peek();
            if (node.left == null && node.right == null ||
                    pre != null && (node.left == pre || node.right == pre)) {
                visit(node);
                stack.pop();
                pre = node;
                continue;
            }
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
    }

    public static Node create() {
        int val = scanner.nextInt();
        if (val <= 0) {
            return null;
        }
        Node node = new Node();
        node.val = val;
        node.left = create();
        node.right = create();
        return node;
    }


    public static void print(int[] array) {
        for (int i = 0; i < array.length; ++i) {
            System.out.print(" " + array[i]);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int x = 20;
        int y = 5;
        System.out.println(x + y + "" + (x + y) + y);
        // 1 2 0 0 3 4 0 0 5 0 0 5  10 43  3 4 1 6 2 4 2 6 5
        while (true) {
            Node root = create();
            preOrder(root);
            System.out.println();
            preOrder2(root);
            System.out.println();
            inOrder(root);
            System.out.println();
            inOrder2(root);
            System.out.println();
            postOrder(root);
            System.out.println();
            postOrder2(root);
            System.out.println();
        }
    }

}
