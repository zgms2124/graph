package exam;

import class06.Edge;

import java.util.*;

/**
 * 学习JAVA
 *
 * @项目名称：图的建立、BFS、DFS
 * @计通2204李志杨
 * @Date：2023/5/25 - 05 - 25 - 15:59
 * @version： 1.0
 * @功能：实现图的建立、遍历
 */
import java.util.*;

public class GraphBFSDFS {
    public static class Node {
        String value;
        List<Integer> nodes;

        public Node(String value) {
            this.value = value;
            this.nodes = new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        Node[] graph = null;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("请选择操作：");
            System.out.println("1. 建立图");
            System.out.println("2. 深度优先遍历");
            System.out.println("3. 广度优先遍历");
            System.out.println("0. 结束");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("请输入结点个数：");
                    int n = scanner.nextInt();
                    graph = createGraph(n);
                    break;
                case 2:
                    if (graph != null) {
                        System.out.println("请输入起始结点编号：");
                        int startNodeDFS = scanner.nextInt();
                        System.out.println("深度优先遍历结果：");
                        dfs(graph, startNodeDFS);
                    } else {
                        System.out.println("请先建立图！");
                    }
                    break;
                case 3:
                    if (graph != null) {
                        System.out.println("请输入起始结点编号：");
                        int startNodeBFS = scanner.nextInt();
                        System.out.println("广度优先遍历结果：");
                        bfs(graph, startNodeBFS);
                    } else {
                        System.out.println("请先建立图！");
                    }
                    break;
                case 0:
                    System.out.println("程序已结束。");
                    return;
                default:
                    System.out.println("无效的选择，请重新输入！");
                    break;
            }
        }
    }

    public static Node[] createGraph(int n) {
        Node[] graph = new Node[n];
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < n; i++) {
            System.out.println("请输入第 " + (i + 1) + " 个结点的值（字符串）：");
            String value = scanner.next();
            graph[i] = new Node(value);
        }

        for (int i = 0; i < n; i++) {
            System.out.println("结点 " + graph[i].value + " 的邻接结点个数为：");
            int num = scanner.nextInt();
            for (int j = 0; j < num; j++) {
                System.out.println("请输入第 " + (j + 1) + " 个邻接结点的编号：");
                int neighbor = scanner.nextInt();
                graph[i].nodes.add(neighbor);
            }
        }

        return graph;
    }

    public static void dfs(Node[] graph, int start) {
        boolean[] visited = new boolean[graph.length];
        List<String> visitedNodes = new ArrayList<>();
        List<String> treeEdges = new ArrayList<>();

        Stack<Integer> stack = new Stack<>();
        stack.push(start);
        visited[start] = true;
        visitedNodes.add(graph[start].value);
        while (!stack.isEmpty()) {
            int cur = stack.pop();

            for (int neighbor : graph[cur].nodes) {
                if (!visited[neighbor]) {
                    stack.push(cur);
                    stack.push(neighbor);
                    visited[neighbor] = true;
                    visitedNodes.add(graph[neighbor].value);
                    treeEdges.add("(" + graph[cur].value + ", " + graph[neighbor].value + ")");
                    break;
                }
            }
        }

        System.out.println("结点访问序列：" + visitedNodes);
        System.out.println("生成树的边集：" + treeEdges);
    }

    public static void bfs(Node[] graph, int start) {
        boolean[] visited = new boolean[graph.length];
        List<String> visitedNodes = new ArrayList<>();
        List<String> treeEdges = new ArrayList<>();

        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            visitedNodes.add(graph[cur].value);

            for (int neighbor : graph[cur].nodes) {
                if (!visited[neighbor]) {
                    queue.add(neighbor);
                    visited[neighbor] = true;
                    treeEdges.add("(" + graph[cur].value + ", " + graph[neighbor].value + ")");
                }
            }
        }

        System.out.println("结点访问序列：" + visitedNodes);
        System.out.println("生成树的边集：" + treeEdges);
    }
}

