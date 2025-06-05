import java.io.*;
import java.util.*;

/**
 * 백준 11085번: 군사 이동
 * 문제 난이도: Gold 3
 * 문제 유형: Kruskal 알고리즘, 그래프 이론, 최대 경로 너비
 *
 * 문제 요약:
 * - 백준왕국(p개의 지점, w개의 길)에서 두 수도(c와 v) 사이의 이동 경로 중
 *   가장 좁은 길의 너비를 최대화하는 문제
 * - 각 길은 양방향이며 너비(w)가 있음
 *
 * 접근 방식:
 * - 일반적인 Kruskal 알고리즘을 변형하여 너비가 넓은 길부터 선택하는 방식 사용
 * - 경로의 병목 구간(가장 좁은 길)의 너비를 최대화하기 위해 내림차순 정렬
 */
public class boj11085 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static PriorityQueue<Edge> edges;  // 간선을 너비의 내림차순으로 저장하는 우선순위 큐
    private static int[] uf, size;            // 유니온-파인드를 위한 배열
    private static int p, w, c, v;            // p: 지점 개수, w: 길 개수, c: 시작 수도, v: 도착 수도

    public static void main(String[] args) throws IOException {
        init();  // 입력 데이터 초기화
        int answer = kruskal();  // 크루스칼 알고리즘으로 최대 경로 너비 계산

        // 결과 출력
        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력 데이터를 초기화하는 함수
     * - 지점 수(p), 길 수(w), 출발지(c), 도착지(v) 입력
     * - 유니온-파인드 배열 초기화
     * - 간선 정보 입력 및 내림차순 우선순위 큐에 저장
     */
    private static void init() throws IOException {
        // 지점 수와 길 수 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        p = Integer.parseInt(st.nextToken());  // 지점 개수
        w = Integer.parseInt(st.nextToken());  // 길 개수

        // 출발 수도와 도착 수도 입력
        st = new StringTokenizer(br.readLine());
        c = Integer.parseInt(st.nextToken());  // 시작 수도
        v = Integer.parseInt(st.nextToken());  // 도착 수도

        // 간선 우선순위 큐 초기화 - 너비(w) 기준 내림차순 정렬
        // 가장 너비가 넓은 길부터 사용하기 위함
        edges = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.w, o1.w));

        // 유니온-파인드 배열 초기화
        uf = new int[p + 1];    // 부모 노드 저장 배열
        size = new int[p + 1];  // 각 집합의 크기 저장 배열 (union 최적화)

        // 초기에 각 노드는 자신이 집합의 대표(부모)
        for (int i = 1; i <= p; i++) {
            uf[i] = i;       // 초기 부모는 자기 자신
            size[i] = 1;     // 초기 집합 크기는 1
        }

        // 길 정보 입력
        while (w-- > 0) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());  // 출발 지점
            int v = Integer.parseInt(st.nextToken());  // 도착 지점
            int w = Integer.parseInt(st.nextToken());  // 길의 너비

            // 간선 정보를 우선순위 큐에 추가
            edges.add(new Edge(u, v, w));
        }
    }

    /**
     * 변형된 크루스칼 알고리즘을 사용하여 최대 경로 너비를 계산하는 함수
     * - 너비가 넓은 길부터 선택하여 출발지와 도착지가 연결될 때까지 진행
     * - 마지막으로 선택한 간선의 너비가 경로의 병목 구간(최소 너비)
     *
     * @return 출발지(c)에서 도착지(v)까지 가는 경로에서 가장 좁은 길의 최대 너비
     */
    private static int kruskal() {
        int min = 1005;  // 최소 너비를 저장할 변수 (문제 제약: 최대 너비는 1000)

        // 출발지(c)와 도착지(v)가 연결될 때까지 반복
        while (find(c) != find(v)) {
            // 너비가 가장 넓은 간선부터 선택
            Edge edge = edges.poll();

            // 두 지점의 집합 대표자(root) 확인
            int root1 = find(edge.u);
            int root2 = find(edge.v);

            // 이미 같은 집합에 속한 경우 사이클이 생기므로 건너뜀
            if (root1 == root2) continue;

            // 두 집합 병합
            union(edge.u, edge.v);

            // 선택한 간선의 너비가 현재까지의 최소 너비
            // (내림차순으로 선택하므로, 이전에 선택한 간선들은 모두 이 간선보다 너비가 넓음)
            min = Math.min(min, edge.w);
        }

        // 최종 경로의 최소 너비 반환
        return min;
    }

    /**
     * 유니온-파인드의 union 연산 - 두 집합을 병합
     * - 집합의 크기를 기준으로 최적화 (작은 집합을 큰 집합에 병합)
     *
     * @param x 첫 번째 원소
     * @param y 두 번째 원소
     */
    private static void union(int x, int y) {
        // 각 원소가 속한 집합의 대표자 찾기
        int X = find(x);
        int Y = find(y);

        // 이미 같은 집합인 경우 무시
        if (X == Y) return;

        // 작은 집합을 큰 집합에 병합 (유니온-파인드 최적화)
        if (size[X] < size[Y]) {
            uf[X] = Y;         // X의 대표자를 Y로 변경
            size[Y] += size[X]; // Y 집합의 크기 증가
        } else {
            uf[Y] = X;         // Y의 대표자를 X로 변경
            size[X] += size[Y]; // X 집합의 크기 증가
        }
    }

    /**
     * 유니온-파인드의 find 연산 - 원소가 속한 집합의 대표자 찾기
     * - 경로 압축 최적화 적용 (재귀 호출 결과를 배열에 저장)
     *
     * @param x 찾을 원소
     * @return x가 속한 집합의 대표자(루트)
     */
    private static int find(int x) {
        // 자기 자신이 대표자인 경우 바로 반환
        if (uf[x] == x) return x;

        // 경로 압축: x의 부모를 집합의 대표자로 바로 연결
        return uf[x] = find(uf[x]);
    }

    /**
     * 간선 정보를 저장하는 클래스
     */
    static class Edge {
        int u;  // 출발 지점
        int v;  // 도착 지점
        int w;  // 길의 너비

        public Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }
}