import java.util.*;

public class GPSNavigation {

    static class Edge {
        int target;
        int distance;

        Edge(int target, int distance) {
            this.target = target;
            this.distance = distance;
        }
    }

    static class Node implements Comparable<Node> {
        int vertex, dist;

        Node(int vertex, int dist) {
            this.vertex = vertex;
            this.dist = dist;
        }

        public int compareTo(Node other) {
            return Integer.compare(this.dist, other.dist);
        }
    }

    static void dijkstra(List<List<Edge>> graph, int source, int dest, String[] cities) {
        int n = graph.size();
        int[] dist = new int[n];
        int[] prev = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[source] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(source, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.vertex;

            for (Edge edge : graph.get(u)) {
                int v = edge.target;
                int weight = edge.distance;

                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    prev[v] = u;
                    pq.add(new Node(v, dist[v]));
                }
            }
        }

        if (dist[dest] == Integer.MAX_VALUE) {
            System.out.println("No path found!");
        } else {
            System.out.println("Shortest distance: " + dist[dest] + " km");
            System.out.print("Path: ");
            printPath(prev, dest, cities);
            System.out.println();
        }
    }

    static void printPath(int[] prev, int v, String[] cities) {
        if (v == -1) return;
        printPath(prev, prev[v], cities);
        System.out.print(cities[v] + " ");
    }

    public static void main(String[] args) {
        String[] cities = {"A", "B", "C", "D", "E"};
        int n = cities.length;

        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());

        // Undirected roads (two-way)
        graph.get(0).add(new Edge(1, 10)); // A-B
        graph.get(1).add(new Edge(0, 10));

        graph.get(0).add(new Edge(2, 3));  // A-C
        graph.get(2).add(new Edge(0, 3));

        graph.get(1).add(new Edge(2, 1));  // B-C
        graph.get(2).add(new Edge(1, 1));

        graph.get(1).add(new Edge(3, 2));  // B-D
        graph.get(3).add(new Edge(1, 2));

        graph.get(2).add(new Edge(3, 8));  // C-D
        graph.get(3).add(new Edge(2, 8));

        graph.get(2).add(new Edge(4, 2));  // C-E
        graph.get(4).add(new Edge(2, 2));

        graph.get(3).add(new Edge(4, 7));  // D-E
        graph.get(4).add(new Edge(3, 7));

        Scanner sc = new Scanner(System.in);

        System.out.println("Available cities:");
        for (int i = 0; i < cities.length; i++) {
            System.out.println(i + " - " + cities[i]);
        }

        System.out.print("Enter source city number: ");
        int source = sc.nextInt();

        System.out.print("Enter destination city number: ");
        int dest = sc.nextInt();

        System.out.println("\nFinding shortest path from " + cities[source] + " to " + cities[dest] + "...\n");
        dijkstra(graph, source, dest, cities);
    }
}