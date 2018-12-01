package test;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TopoBean {
    public static class Vertex {
        public List<Vertex> children = new LinkedList<>();
        public int val;
        public int indegree;

        @Override
        public String toString() {
            return "Vertex{" +
                    ", children=" + children +
                    ", val=" + val +
                    ", indegree=" + indegree +
                    '}';
        }
    }

    public static List<Vertex> topoSort(List<Vertex> vertices) {
        int countVertices = vertices.size();
        List<Vertex> ans = new LinkedList<>();
        Queue<Vertex> queue = new LinkedList<>();
        int count = 0;
        for (Vertex vertex : vertices) {
            for (Vertex child : vertex.children) {
                ++child.indegree;
            }
            if (vertex.indegree == 0) {
                queue.add(vertex);
            }
        }

        while (!queue.isEmpty()) {
            Vertex vertex = queue.remove();
            ans.add(vertex);
            ++count;
            for (Vertex v : vertex.children) {
                if (--v.indegree == 0) {
                    queue.add(v);
                }
            }
        }

        if (count != countVertices) {
            return null;
        }
        return ans;
    }


    public static Vertex findVertex(Map<Integer, Vertex> vertices, int val) {
        if (vertices.containsKey(val)) {
            return vertices.get(val);
        }
        Vertex v = new Vertex();
        v.val = val;
        vertices.put(val, v);
        return v;
    }

    public static void main(String[] args) {
        /*
        4
        1 3 2 3 4
        2 0
        3 1 2
        4 1 3
        3
        1 1 2
        2 1 3
        3 1 1
        7
        1 3 2 4 3
        2 2 4 5
        3 1 6
        4 3 3 6 7
        5 2 4 7
        6 0
        7 1 6
         */
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int n = scanner.nextInt();
            Map<Integer, Vertex> vertices = new HashMap<>();
            for (int i = 0; i < n; ++i) {
                int index = scanner.nextInt();
                Vertex curV = findVertex(vertices, index);
                int m = scanner.nextInt();
                for (int j = 0; j < m; ++j) {
                    int child = scanner.nextInt();
                    curV.children.add(findVertex(vertices, child));
                }
            }

            ArrayList<Vertex> all = new ArrayList(vertices.values());
            List<Vertex> ans = topoSort(all);
            if (ans == null) {
                System.out.println("这个图有圈");
                continue;
            }
            for (int i = 0; i < ans.size(); ++i) {
                System.out.print(" " + ans.get(i).val);
            }
            System.out.println();
        }
    }
}
