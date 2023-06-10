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

        String[] items = {"입구", "물건8", "물건19", "물건13", "물건10"};
        double[][] graph = createGraph(storeMap, items);

        dijkstra(graph, 0);
    }

    private static double[][] createGraph(String[][] storeMap, String[] items) {
        int numItems = items.length;
        double[][] graph = new double[numItems][numItems]; // 물건들과 입구의 위치를 포함한 그래프 배열 생성

        // 물건들과 입구의 위치 간의 거리를 계산하여 그래프에 가중치로 설정
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

    static double calculateWeight(String[][] storeMap, String item1, String item2) {
        int item1_row = 0;
        int item1_col = 0;
        int item2_row = 0;
        int item2_col = 0;

        if(item1.equals(item2))
            return 0;
        else{
            for(int i = 0; i < storeMap.length; i++){
                for(int j = 0; j < storeMap[i].length; j++){
                    if(storeMap[i][j].equals(item1)){
                        item1_row = i;
                        item1_col = j;
                    }
                    else if(storeMap[i][j].equals(item2)){
                        item2_row = i;
                        item2_col = j;
                    }

                }
                if(item1_row != 0 && item2_row != 0)
                    break;
            }
            double distanse = Math.sqrt((int)Math.pow(item1_row - item2_row,2)
                    + (int)Math.pow(item1_col - item2_col,2));

            return distanse;
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
            int current = getMinDistance(distance, visited, numItems);
            visited[current] = true;

            for (int j = 0; j < numItems; j++) {
                if (!visited[j] && graph[current][j] != 0 && distance[current] != Double.MAX_VALUE
                        && distance[current] + graph[current][j] < distance[j]) {
                    distance[j] = distance[current] + graph[current][j];
                    previous[j] = current;
                }
            }
        }

        return getShortestPath(previous, numItems - 1);
    }

    private static int getMinDistance(double[] distance, boolean[] visited, int numItems) {
        double minDistance = Double.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < numItems; i++) {
            if (!visited[i] && distance[i] <= minDistance) {
                minDistance = distance[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    private static List<Integer> getShortestPath(int[] previous, int end) {
        List<Integer> path = new ArrayList<>();
        int current = end;

        while (current != -1) {
            path.add(current);
            current = previous[current];
        }

        List<Integer> shortestPath = new ArrayList<>();

        for (int i = path.size() - 1; i >= 0; i--) {
            shortestPath.add(path.get(i));
        }

        return shortestPath;
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