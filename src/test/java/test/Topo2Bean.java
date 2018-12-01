package test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Topo2Bean {
    public static class Edge {
        public int val;
        public List<Edge> children = new LinkedList<>();
        public int indegree;
    }

    public int[][] source;
    private Edge[] edges;
    public int[] ans;
    public int count = 0;

    public void topoSort() {
        count = edges.length;
        for (int i = 0; i < edges.length; ++i) {

        }
    }
}
