import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class boj3108 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // Union-Find 자료구조를 위한 배열들
    private static int[] uf;   // 부모 노드를 저장하는 배열
    private static int[] size; // 각 트리의 크기를 저장하는 배열 (Union by Rank 최적화용)

    // 각 사각형의 좌표를 저장하는 배열
    // rect[i][0] = x1, rect[i][1] = y1, rect[i][2] = x2, rect[i][3] = y2
    private static int[][] rect;

    private static int n; // 사각형의 개수

    public static void main(String[] args) throws IOException {
        init();

        // 모든 사각형 쌍에 대해 연결 여부 확인 및 Union 수행
        // 인덱스 0은 원점(0,0)을 나타내는 가상의 노드
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                if (isConnected(i, j)) {
                    union(i, j); // 연결된 사각형들을 같은 그룹으로 합침
                }
            }
        }

        int answer = countGroups(); // 연결된 그룹의 개수 계산
        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // 입력 데이터 초기화 및 Union-Find 구조 설정
    private static void init() throws IOException {
        n = Integer.parseInt(br.readLine()); // 사각형의 개수

        // Union-Find 배열 초기화 (0번 인덱스는 원점을 위해 예약)
        uf = new int[n + 1];
        size = new int[n + 1];
        rect = new int[n + 1][4];

        // 각 노드를 자기 자신으로 초기화 (독립된 집합)
        for (int i = 1; i <= n; i++) {
            uf[i] = i;
            size[i] = 1;
        }

        // 각 사각형의 좌표 입력
        for (int i = 1; i <= n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            rect[i][0] = Integer.parseInt(st.nextToken()); // x1 (왼쪽 x 좌표)
            rect[i][1] = Integer.parseInt(st.nextToken()); // y1 (아래쪽 y 좌표)
            rect[i][2] = Integer.parseInt(st.nextToken()); // x2 (오른쪽 x 좌표)
            rect[i][3] = Integer.parseInt(st.nextToken()); // y2 (위쪽 y 좌표)
        }
    }

    // 두 사각형(또는 원점과 사각형)이 연결되어 있는지 확인하는 함수
    private static boolean isConnected(int i, int j) {
        // 각 사각형의 좌표 추출
        int x1_1 = rect[i][0], y1_1 = rect[i][1], x2_1 = rect[i][2], y2_1 = rect[i][3];
        int x1_2 = rect[j][0], y1_2 = rect[j][1], x2_2 = rect[j][2], y2_2 = rect[j][3];

        // 원점(0,0)과 사각형의 연결 여부 확인
        if (i == 0 || j == 0) {
            int rectIdx = (i == 0) ? j : i; // 사각형의 인덱스 결정
            int rx1 = rect[rectIdx][0], ry1 = rect[rectIdx][1],
                rx2 = rect[rectIdx][2], ry2 = rect[rectIdx][3];

            // 원점이 사각형의 경계에 있는지 확인
            // 1) 사각형의 왼쪽 또는 오른쪽 경계가 y축(x=0)에 있고, 원점이 y 범위 안에 있는 경우
            // 2) 사각형의 아래쪽 또는 위쪽 경계가 x축(y=0)에 있고, 원점이 x 범위 안에 있는 경우
            return ((rx1 == 0 || rx2 == 0) && ry1 <= 0 && 0 <= ry2) ||
                ((ry1 == 0 || ry2 == 0) && rx1 <= 0 && 0 <= rx2);
        }

        // 두 사각형이 완전히 분리된 경우들 확인
        // 사각형1이 사각형2 내부에 완전히 포함되는 경우 (접촉하지 않음)
        if (x1_2 < x1_1 && x2_1 < x2_2 && y1_2 < y1_1 && y2_1 < y2_2) return false;

        // 사각형2가 사각형1 내부에 완전히 포함되는 경우 (접촉하지 않음)
        if (x1_1 < x1_2 && x2_2 < x2_1 && y1_1 < y1_2 && y2_2 < y2_1) return false;

        // 두 사각형이 겹치거나 접촉하는지 확인
        // x 축과 y 축 모두에서 겹치는 구간이 있어야 함 (경계 포함)
        return x2_2 >= x1_1 && x2_1 >= x1_2 && y2_2 >= y1_1 && y2_1 >= y1_2;
    }

    // 연결된 그룹의 개수를 계산하는 함수
    private static int countGroups() {
        Set<Integer> groups = new HashSet<>();

        // 모든 노드의 루트를 찾아서 그룹 개수 계산
        for (int i = 0; i <= n; i++) {
            groups.add(find(i));
        }

        // 원점을 나타내는 0번 노드는 제외하고 실제 사각형 그룹만 카운트
        // 하지만 원점과 연결된 그룹이 있으면 그 그룹은 원점에서 시작 가능하므로
        // 전체 그룹에서 1을 빼서 펜을 들어야 하는 횟수를 계산
        return groups.size() - 1;
    }

    // Union-Find의 Union 연산 (Union by Rank 최적화 적용)
    private static void union(int x, int y) {
        int X = find(x); // x의 루트 찾기
        int Y = find(y); // y의 루트 찾기

        if (X == Y) return; // 이미 같은 그룹이면 종료

        // 크기가 작은 트리를 큰 트리에 연결 (Union by Rank)
        if (size[X] < size[Y]) {
            uf[X] = Y;
            size[Y] += size[X];
        } else {
            uf[Y] = X;
            size[X] += size[Y];
        }
    }

    // Union-Find의 Find 연산 (Path Compression 최적화 적용)
    private static int find(int x) {
        if (uf[x] == x) return x; // 루트 노드인 경우

        // 경로 압축: 재귀 호출하면서 모든 노드를 루트에 직접 연결
        return uf[x] = find(uf[x]);
    }
}