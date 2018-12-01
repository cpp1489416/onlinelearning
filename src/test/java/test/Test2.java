package test;

import cpp.user.po.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class Test2 {
    public ArrayList<Integer> maxInWindows(int[] num, int size) {
        if (num == null || num.length == 0 || size > num.length) {
            return new ArrayList<>();
        }
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < size; ++i) {
            while (!queue.isEmpty() && num[queue.getFirst()] <= num[i]) {
                queue.removeFirst();
            }
            queue.addLast(i);
        }
        ArrayList<Integer> ans = new ArrayList<>();
        ans.add(num[queue.getFirst()]);
        for (int i = size; i < num.length; ++i) {
            queue.addLast(i);
            if (i - queue.getFirst() > size - 1) {
                queue.removeFirst();
            }
            while (queue.size() != 1 && (num[queue.getFirst()] <= num[i])) {
                queue.removeFirst();
            }
            for (int j = 0; j < queue.size(); ++j) {
                System.out.print(" " + num[queue.get(j)]);
            }
            System.out.println();
            ans.add(num[queue.getFirst()]);
        }
        return ans;
    }

    final User user = new User();

    public void add() {
        user.setAge(3);
    }

    public static class Node {
        public int val;
        public Node left;
        public Node right;
    }

    public static void visit(Node node) {
        System.out.print(" " + node.val);
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

    public static void postOrder(Node root) {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        Node pre = null;
        Node cur = root;
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

    public static class GraphNode {
        public int val;
        public GraphNode next;
    }
    
    public static void topoSort(Node[] arr) {
        
    }

    public static void main(String[] args) {
        new Test2().maxInWindows(new int[]{2, 3, 4, 2, 6, 2, 5, 1}, 3);
    }
}