import java.util.ArrayList;
import java.util.List;

public class MartMap {
    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
        int N = 8;

        // 아이템 이름 배열
        String[] items = {"마트입구", "전자레인지", "기저귀", "청소기", "돼지고기", "냉장고", "생수", "육개장 1박스", "의자"};
        // 아이템 볼륨 배열
        int[] volumes = {0, 10, 5, 25, 3, 40, 5, 15, 25};

        // 그래프의 인접 행렬
        int[][] g = {
                {0, 3, INF, 2, INF, INF, INF, INF},
                {3, 0, 7, INF, 4, INF, INF, INF},
                {INF, 7, 0, 5, INF, INF, INF, INF},
                {2, INF, 5, 0, 7, INF, INF, INF},
                {INF, 4, INF, 7, 0, 3, INF, INF},
                {INF, INF, INF, INF, 3, 0, 2, INF},
                {INF, INF, INF, INF, INF, 2, 0, 6},
                {INF, INF, INF, INF, INF, INF, 6, 0}
        };

        // 최소 신장 트리의 간선 정보
        int[][] mst = {
                {0, 1, 3},
                {1, 3, 7},
                {3, 4, 7},
                {4, 5, 3},
                {5, 6, 2},
                {6, 7, 6},
                {1, 2, 7}
        };

        int binSize = 40;
        // 근사적인 Bin Packing 알고리즘을 사용하여 필요한 카트의 개수 계산
        int binCount = approxBinPacking(volumes, binSize);

        System.out.println("필요한 카트의 개수: " + binCount);

        // 인접 리스트 생성
        List<List<Integer>> a = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            a.add(new ArrayList<>());
        }

        // 그래프의 인접 리스트 표현과 가중치 정보 설정
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
        int current = 0;
        visitSeq.add(0);

        // DFS를 사용하여 지름길 방문 순서 계산
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

        List<Integer> directSeq = new ArrayList<>();
        boolean[] mark = new boolean[N];
        for (int i = 0; i < visitSeq.size(); i++) {
            if (!mark[visitSeq.get(i)]) {
                directSeq.add(visitSeq.get(i));
                mark[visitSeq.get(i)] = true;
            }
        }

        System.out.print("지름길 방문 순서: ");
        for (int i = 0; i < directSeq.size(); i++) {
            System.out.print(items[directSeq.get(i)]);
            if (i != directSeq.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.print(" -> 마트입구");
        System.out.println();
    }

    // 근사적인 Bin Packing 알고리즘
    public static int approxBinPacking(int[] itemVolumes, int binSize) {
        List<Integer> bins = new ArrayList<>();
        bins.add(binSize);

        for (int i = 0; i < itemVolumes.length; i++) {
            int itemVolume = itemVolumes[i];
            int bestBinIndex = -1;
            int bestRemainingSpace = Integer.MAX_VALUE;

            for (int j = 0; j < bins.size(); j++) {
                int remainingSpace = bins.get(j) - itemVolume;

                if (remainingSpace >= 0 && remainingSpace < bestRemainingSpace) {
                    bestBinIndex = j;
                    bestRemainingSpace = remainingSpace;
                }
            }

            if (bestBinIndex != -1) {
                bins.set(bestBinIndex, bestRemainingSpace);
            } else {
                bins.add(binSize - itemVolume);
            }
        }

        return bins.size();
    }
}