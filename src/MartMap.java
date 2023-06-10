import java.util.ArrayList;
import java.util.List;

public class MartMap {
    private static final int INF = Integer.MAX_VALUE;
    public static void main(String[] args) {
        int N = 8;  // 정점의 수
        String[] places = { "마트입구", "생활 필수품", "가구점", "식료품점", "약국", "의류점", "전자제품점", "수산시장" };

        int[][] g = {
                {0, 7, INF, INF, INF, INF, INF, INF},
                {7, 0, 2, INF, INF, INF, INF, INF},
                {INF, 2, 0, 4, INF, INF, INF, INF},
                {INF, 2, 4, 0, 3, INF, INF, INF},
                {INF, INF, 5, 3, 0, 5, INF, INF},
                {INF, INF, INF, INF, 5, 0, 6, INF},
                {INF, INF, INF, INF, INF, 6, 0, 7},
                {INF, INF, INF, INF, INF, 10, 7, 0}
        };

        int[][] mst = {
                {0, 1, 7},
                {1, 2, 2},
                {1, 3, 7},
                {3, 4, 3},
                {4, 5, 5},
                {5, 6, 6},
                {6, 7, 7}
        };

        List<List<Integer>> a = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            a.add(new ArrayList<>());
        }

        for (int i = 0; i < mst.length; i++) {
            int u = mst[i][0];
            int v = mst[i][1];
            int w = mst[i][2];
            a.get(u).add(v);
            a.get(v).add(u);
            g[u][v] = w;
            g[v][u] = w;
        }

        List<Integer> visitSeq = new ArrayList<>();
        int current = 0; // 시작점을 마트입구(인덱스 0)로 변경
        visitSeq.add(0); // 마트입구(인덱스 0)를 방문 순서에 추가

        while (!a.get(0).isEmpty()) {
            boolean flag = false;
            int k = 0;
            while (k < a.get(current).size()) {
                int nextVisit = a.get(current).get(k);
                if (!visitSeq.contains(nextVisit)) {
                    flag = true;
                    visitSeq.add(nextVisit);
                    current = nextVisit;
                    break;
                }
                k++;
            }

            if (!flag) {
                int nextVisit = a.get(current).remove(0);
                visitSeq.add(nextVisit);
                current = nextVisit;
            }
        }

        visitSeq.add(0);

        System.out.print("mst를 따른 방문 순서:\t");
        for (int i = 0; i < visitSeq.size(); i++) {
            System.out.print(places[visitSeq.get(i)]);
            if (i != visitSeq.size() - 1) {
                System.out.print(" <- ");
            }
        }
        System.out.println();

        List<Integer> directSeq = new ArrayList<>();
        boolean[] mark = new boolean[N];
        for (int i = 0; i < visitSeq.size(); i++) {
            if (!mark[visitSeq.get(i)]) {
                directSeq.add(visitSeq.get(i));
                mark[visitSeq.get(i)] = true;
            }
        }

        System.out.print("지름길 방문 순서:\t");
        for (int i = directSeq.size() - 1; i >= 0; i--) {
            System.out.print(places[directSeq.get(i)]);
            if (i != 0) {
                System.out.print(" <- ");
            }
        }
        System.out.println();

    }

}