package exam;

import java.util.*;

/**
 * 学习JAVA
 *
 * @项目名称：利用最小生成树解决通信网的总造价最低问题
 * @计通2204李志杨
 * @Date：2023/5/25 - 05 - 25 - 16:37
 * @version： 1.0
 * @功能：利用P算法实现最小生成树
 */
public class SmallestValue {
    public static class Node{
        String value;
        int num;
        List<Edge> edges=new ArrayList<>();
        public Node(String value,int num){
            this.value=value;
            this.num=num;
            this.edges=new ArrayList<>();
        }
    }
    public static void main(String[] args) {
        Node[] graph;
        System.out.println("结点个数为：");
        Scanner sc=new Scanner(System.in);
        int size=sc.nextInt();
        if(size<=0){
            System.out.println("结点个数必须大于0！");
            System.exit(0);
        }
        graph=createGraph(size);
        Set <Edge>set=new HashSet<>();
        set=Prim(graph,0);
        System.out.println("边的条数为："+set.size());
        for(Edge cur:set){
            System.out.println(graph[cur.from].value+"与"+graph[cur.to].value+"，权值为："+cur.weight);
        }
    }
    public static class Edge{
        int from;
        int to;
        int weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }
    public static Node[] createGraph(int n) {
        System.out.println("需要输入" + n + "个结点");
        Node[] ans = new Node[n];
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < n; i++) {
            System.out.println("输入第" + (i + 1) + "个结点的值（字符串）");
            String str = sc.next();
            ans[i] = new Node(str, i);
        }
        for (int i = 0; i < n; i++) {
            System.out.println("结点" + ans[i].value + "的邻接结点的个数为：");
            int num = sc.nextInt();
            for (int j = 0; j < num; j++) {
                System.out.println("第" + (j + 1) + "个邻接结点的编号为");
                int to = sc.nextInt();
                System.out.println("第" + (j + 1) + "个邻接结点的权值为");
                int weight = sc.nextInt();
                ans[i].edges.add(new Edge(i, to, weight));
                // 添加双向连接
                ans[to].edges.add(new Edge(to, i, weight));
            }
        }
        return ans;
    }
    public static class myCmp implements Comparator<Edge> {
        @Override
        public int compare(Edge o1,Edge o2) {
            return o1.weight - o2.weight;
        }

    }
    public static Set Prim(Node[] graph,int start){
        PriorityQueue <Edge>q=new PriorityQueue<>(new myCmp());
        for(Edge cur:graph[start].edges){
            q.add(cur);
        }
        boolean [] seen=new boolean[graph.length];
        Set<Edge> result=new HashSet<>();
        seen[start]=true;
        while(!q.isEmpty()){
            Edge edge=q.poll();
            if(!seen[edge.to]){
                result.add(edge);
                seen[edge.to]=true;
                for(Edge cur:graph[edge.to].edges){
                        q.add(cur);
                }
            }
        }
        return result;
    }


}
