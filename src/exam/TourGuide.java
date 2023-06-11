package exam;


/**
 * 学习JAVA
 *
 * @项目名称：
 * @计通2204李志杨
 * @Date：2023/5/29 - 05 - 29 - 19:02
 * @version： 1.0
 * @功能：
 */
import java.util.*;

class Node {
    private String name;
    private int ticketPrice;

    public Node(String name, int ticketPrice) {
        this.name = name;
        this.ticketPrice = ticketPrice;
    }

    public String getName() {
        return name;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }
}

class Edge {
    private Node source;
    private Node destination;
    private int weight;

    public Edge(Node source, Node destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public Node getSource() {
        return source;
    }

    public Node getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }
}

class Path {
    private int value;
    private List<Node> nodes;

    public Path(int value, List<Node> nodes) {
        this.value = value;
        this.nodes = nodes;
    }

    public int getValue() {
        return value;
    }

    public List<Node> getNodes() {
        return nodes;
    }
}

class Graph {
    Map<Node, List<Edge>> travelList;

    public Graph() {
        travelList = new HashMap<>();
    }

    public void addNode(Node node) {
        travelList.put(node, new ArrayList<>());
    }

    public void addEdge(Node source, Node destination, int weight) {
        List<Edge> edges = travelList.get(source);
        edges.add(new Edge(source, destination, weight));
        travelList.put(source, edges);
        edges =travelList.get(destination);
        edges.add(new Edge(destination, source, weight));
        travelList.put(destination, edges);
    }

    public Path findShortestPath(Node source, Node destination) {
        // 创建距离和前驱节点的映射
        Map<Node, Integer> distance = new HashMap<>();
        Map<Node, Node> previous = new HashMap<>();

        // 创建优先队列，并根据距离进行比较
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(distance::get));

        // 初始化距离和前驱节点映射，并将所有节点加入优先队列
        for (Node node : travelList.keySet()) {
            if (node == source) {
                distance.put(node, 0);
            } else {
                distance.put(node, Integer.MAX_VALUE);
            }
            previous.put(node, null);
            queue.add(node);
        }

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current == destination) {
                break;
            }

            for (Edge edge : travelList.get(current)) {
                // 计算新的距离
                int newDistance = distance.get(current) + edge.getWeight();

                // 如果新的距离比之前的更短，则更新距离和前驱节点
                if (newDistance < distance.get(edge.getDestination())) {
                    distance.put(edge.getDestination(), newDistance);
                    previous.put(edge.getDestination(), current);

                    // 由于距离发生了变化，需要重新调整优先队列中节点的顺序
                    queue.remove(edge.getDestination());
                    queue.add(edge.getDestination());
                }
            }
        }

        // 从前驱节点构建路径
        List<Node> pathNodes = new ArrayList<>();
        Node current = previous.get(destination);
        while (current != null) {
            pathNodes.add(0, current);
            current = previous.get(current);
        }

        // 检查是否存在路径，并返回路径及其距离
        if (pathNodes.size() > 0 && pathNodes.get(0) == source) {
            return new Path(distance.get(destination), pathNodes);
        } else {
            return null;
        }
    }


    public List<Node> findBestTour(Node startNode) {
        List<Node> tour = new ArrayList<>();  // 存储游览路径的列表
        Set<Node> visited = new HashSet<>();  // 存储已访问过的节点的集合
        tour.add(startNode);  // 将起始节点添加到游览路径中
        visited.add(startNode);  // 将起始节点标记为已访问

        // 当访问的节点数小于图中节点总数时循环继续
        while (visited.size() < travelList.keySet().size()) {
            Node current = tour.get(tour.size() - 1);  // 获取当前游览路径中最后一个节点
            Node nextNode = null;  // 存储下一个要访问的节点
            int shortestDistance = Integer.MAX_VALUE;  // 存储最短距离的初始值

            // 遍历当前节点的邻接边
            for (Edge edge : travelList.get(current)) {
                // 如果邻接节点未被访问且边的权重小于最短距离，则更新下一个节点和最短距离
                if (!visited.contains(edge.getDestination()) && edge.getWeight() < shortestDistance) {
                    nextNode = edge.getDestination();
                    shortestDistance = edge.getWeight();
                }
            }

            // 如果存在下一个节点，则将其添加到游览路径中，并标记为已访问
            if (nextNode != null) {
                tour.add(nextNode);
                visited.add(nextNode);
            }
        }

        return tour;  // 返回最佳游览路径
    }

}

public class TourGuide {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 建图
        Graph graph = new Graph();

        // 加点
        Node entrance = new Node("大门", 0);
        Node a = new Node("音乐喷泉", 10);
        Node b = new Node("湖中小屋", 15);
        Node c = new Node("青青草坪", 12);
        Node d = new Node("休憩小地", 25);

        graph.addNode(entrance);
        graph.addNode(a);
        graph.addNode(b);
        graph.addNode(c);
        graph.addNode(d);

        // 加边
        graph.addEdge(entrance, a, 10);
        graph.addEdge(entrance, b, 15);
        graph.addEdge(entrance, c, 20);
        graph.addEdge(entrance, d, 16);
        graph.addEdge(a, b, 2);
        graph.addEdge(a, c, 6);
        graph.addEdge(a, b, 2);
        graph.addEdge(b, c, 4);
        graph.addEdge(b, d, 2);
        graph.addEdge(c, d, 3);

        // 打印菜单
        System.out.println("欢迎来到动次打次公园!");
        System.out.println("1. 如果你想找到任意景点间的距离，请敲1。");
        System.out.println("2. 如果你想找到一条最佳游览路径，请敲2。");
        System.out.println("3. 如果你想知道每个景点的信息，请敲3。");
        while(true){

            System.out.println();
            System.out.println("选择吧！ (1-3):");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("你想从哪出发:");
                    String sourceAttraction = scanner.next();
                    System.out.println("去哪:");
                    String destinationAttraction = scanner.next();

                    Node sourceNode = findNodeByName(graph, sourceAttraction);
                    Node destinationNode = findNodeByName(graph, destinationAttraction);

                    if (sourceNode != null && destinationNode != null) {
                        Path shortestPath = graph.findShortestPath(sourceNode, destinationNode);

                        if (shortestPath != null) {
                            System.out.print("从" + sourceNode.getName() + "到" + destinationNode.getName() +
                                    ": 最短距离为 = " + shortestPath.getValue() + ", 最短路径为 ： ");
                            for (Node node : shortestPath.getNodes()) {
                                System.out.print(node.getName()+"->");
                            }
                            System.out.print(destinationNode.getName());
                        } else {
                            System.out.println("无法从" + sourceNode.getName() + "到达" + destinationNode.getName());
                        }

                    } else {
                        System.out.println("你去不了那个地方。");
                    }
                    break;

                case 2:
                    Node startNode = entrance;
                    List<Node> bestTour = graph.findBestTour(startNode);

                    System.out.print("从大门开始处最好的游览路径为：");
                    for (Node node : bestTour) {
                        System.out.print(node.getName() +"->");
                    }
                    System.out.print("大门");
                    break;
                case 3:
                    // 列景点
                    System.out.println("景点信息如下:");
                    for (Node node : graph.travelList.keySet()) {
                        if(node.getName()!="大门")
                            System.out.println(node.getName()+"的门票为："+node.getTicketPrice());
                    }
                    break;
                default:
                    System.out.println("你在干什么？");
                    break;
            }
        }
    }

    private static Node findNodeByName(Graph graph, String name) {
        for (Node node : graph.travelList.keySet()) {
            if (node.getName().equalsIgnoreCase(name)) {
                return node;
            }
        }
        return null;
    }
}
