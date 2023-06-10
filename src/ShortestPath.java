import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShortestPath {
    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
        String[][] storeMap = {
                {"입구", "물건2", "물건3", "물건4"},
                {"물건5", "물건6", "물건7", "물건8"},
                {"물건9", "물건10", "물건11", "물건12"},
                {"물건13", "물건14", "물건15", "물건16"},
                {"물건17", "물건18", "물건19", "물건20"}
        };

        String[] items = {"입구", "물건20", "물건19", "물건13", "물건10"};
        double[][] graph = createGraph(storeMap, items);

        List<Integer> shortestPath = dijkstra(graph, 0);
        printShortestPath(items, shortestPath);
    }

    private static double[][] createGraph(String[][] storeMap, String[] items) {
        int numItems = items.length;
        double[][] graph = new double[numItems][numItems];

        for (int i = 0; i < numItems; i++) {
            for (int j = 0; j < numItems; j++) {
                String item1 = items[i];
                String item2 = items[j];
                double weight = calculateWeight(storeMap, item1, item2);

                graph[i][j] = weight;
            }
        }
        return graph;
    }

    private static double calculateWeight(String[][] storeMap, String item1, String item2) {
        int item1_row = 0;
        int item1_col = 0;
        int item2_row = 0;
        int item2_col = 0;

        if (item1.equals(item2))
            return 0;
        else {
            for (int i = 0; i < storeMap.length; i++) {
                for (int j = 0; j < storeMap[i].length; j++) {
                    if (storeMap[i][j].equals(item1)) {
                        item1_row = i;
                        item1_col = j;
                    } else if (storeMap[i][j].equals(item2)) {
                        item2_row = i;
                        item2_col = j;
                    }
                }
                if (item1_row != 0 && item2_row != 0)
                    break;
            }
            double distance = Math.sqrt(Math.pow(item1_row - item2_row, 2) + Math.pow(item1_col - item2_col, 2));
            return distance;
        }
    }

    private static List<Integer> dijkstra(double[][] graph, int start) {
        int numItems = graph.length;
        double[] distance = new double[numItems];
        boolean[] visited = new boolean[numItems];
        int[] previous = new int[numItems];

        Arrays.fill(distance, Double.MAX_VALUE);
        Arrays.fill(visited, false);
        Arrays.fill(previous, -1);

        distance[start] = 0;

        for (int i = 0; i < numItems - 1; i++) {
            int minIndex = findMinDistance(distance, visited);
            visited[minIndex] = true;

            for (int j = 0; j < numItems; j++) {
                if (!visited[j] && graph[minIndex][j] != INF && distance[minIndex] + graph[minIndex][j] < distance[j]) {
                    distance[j] = distance[minIndex] + graph[minIndex][j];
                    previous[j] = minIndex;
                }
            }
        }

        return buildShortestPath(previous, numItems - 1);
    }

    private static int findMinDistance(double[] distance, boolean[] visited) {
        double min = Double.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < distance.length; i++) {
            if (!visited[i] && distance[i] < min) {
                min = distance[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    private static List<Integer> buildShortestPath(int[] previous, int destination) {
        List<Integer> path = new ArrayList<>();
        int current = destination;

        while (current != -1) {
            path.add(0, current);
            current = previous[current];
        }

        return path;
    }

    private static void printShortestPath(String[] items, List<Integer> shortestPath) {
        System.out.println("최단 경로:");

        for (int i = 0; i < shortestPath.size(); i++) {
            int index = shortestPath.get(i);
            System.out.print(items[index]);

            if (i != shortestPath.size() - 1) {
                System.out.print(" -> ");
            }
        }
    }
}