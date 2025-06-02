import java.io.*;
import java.util.*;

public class boj4386 {
    // 입출력을 위한 BufferedReader, BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // stars[i][0]: i번째 별의 x좌표, stars[i][1]: i번째 별의 y좌표
    private static double[][] stars;

    // 각 별까지의 최소 거리를 저장하는 배열
    private static double[] dist;

    // 방문 여부를 체크하는 배열
    private static boolean[] visited;

    // 별의 개수
    private static int n;

    public static void main(String[] args) throws IOException {
        init();  // 초기 데이터 설정

        // 프림 알고리즘으로 최소 신장 트리 구성
        double answer = prim(0);

        // 소수점 둘째 자리까지 출력 (반올림)
        bw.write(String.valueOf(Math.round(answer*100)/100.0));
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 초기 데이터를 설정하는 메소드
     * 별의 개수와 좌표를 입력받아 초기화합니다.
     */
    private static void init() throws IOException {
        n = Integer.parseInt(br.readLine());  // 별의 개수 입력

        // 배열 초기화
        stars = new double[n][2];  // 별 좌표 배열
        dist = new double[n];      // 최소 거리 배열
        visited = new boolean[n];  // 방문 체크 배열

        // 최소 거리를 매우 큰 값으로 초기화 (무한대 역할)
        Arrays.fill(dist, 1e6);

        // 각 별의 좌표 입력
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            double x = Double.parseDouble(st.nextToken());
            double y = Double.parseDouble(st.nextToken());
            stars[i][0] = x;
            stars[i][1] = y;
        }
    }

    /**
     * 프림 알고리즘을 사용하여 최소 신장 트리를 구성하는 메소드
     * @param start 시작 별의 인덱스
     * @return 최소 신장 트리의 가중치 합 (최소 비용)
     */
    private static double prim(int start) {
        // 우선순위 큐를 사용하여 거리가 가장 짧은 별부터 선택
        // [별 인덱스, 거리]를 저장
        PriorityQueue<double[]> pq = new PriorityQueue<>((o1, o2) -> Double.compare(o1[1], o2[1]));

        double sum = 0.0;  // 최소 신장 트리의 총 가중치

        dist[start] = 0.0;  // 시작 별까지의 거리는 0
        pq.add(new double[]{start, dist[start]});  // 시작 별을 큐에 추가

        // 모든 별을 연결할 때까지 반복
        while (!pq.isEmpty()) {
            double[] current = pq.poll();  // 거리가 가장 짧은 별 선택
            int currentStar = (int)current[0];  // 현재 별의 인덱스

            // 이미 방문한 별이면 스킵
            if (visited[currentStar]) continue;

            // 현재 별 방문 처리 및 가중치 합산
            visited[currentStar] = true;
            sum += current[1];  // 현재까지의 거리를 합산

            // 다른 모든 별과의 거리 계산
            for (int i = 0; i < n; i++) {
                // 자기 자신이거나 이미 방문한 별은 스킵
                if (i == currentStar || visited[i]) continue;

                // 현재 별에서 i번째 별까지의 거리 계산
                double newDist = distance(
                    stars[currentStar][0], stars[currentStar][1],
                    stars[i][0], stars[i][1]
                );

                // 기존 거리보다 더 짧으면 업데이트
                if (newDist < dist[i]) {
                    dist[i] = newDist;
                    pq.add(new double[]{i, dist[i]});  // 업데이트된 별을 큐에 추가
                }
            }
        }

        return sum;  // 최소 신장 트리의 총 가중치 반환
    }

    /**
     * 두 점 사이의 유클리드 거리를 계산하는 메소드
     * @param x1 첫 번째 점의 x좌표
     * @param y1 첫 번째 점의 y좌표
     * @param x2 두 번째 점의 x좌표
     * @param y2 두 번째 점의 y좌표
     * @return 두 점 사이의 거리
     */
    private static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
}