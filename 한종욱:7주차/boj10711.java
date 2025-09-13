import java.io.*;
import java.util.*;
import java.awt.Point;

/**
 * 백준 10711번 - 모래성
 *
 * 문제 개요:
 * - 모래성은 1~9 사이의 튼튼함을 가짐 (숫자가 클수록 튼튼함)
 * - 파도가 칠 때마다 주변 8방향의 빈 칸 수가 모래성의 튼튼함보다 크거나 같으면 무너짐
 * - 모든 모래성이 무너지지 않을 때까지 몇 번의 파도가 필요한지 계산
 */
public class boj10711 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // 8방향 이동을 위한 방향 배열 (상, 우상, 우, 우하, 하, 좌하, 좌, 좌상)
    private static final int[] dx = {1, 1, 1, 0, -1, -1, -1, 0};
    private static final int[] dy = {1, -1, 0, 1, 1, -1, 0, -1};

    private static List<Point> castles;        // 모든 모래성의 위치 저장
    private static Queue<Point> toRemove;      // 다음 파도에 무너질 모래성들을 저장하는 큐
    private static boolean[][] visited;        // 이미 무너질 것으로 판단된 모래성 표시
    private static int[][] map;                // 모래성 맵 (-1: 빈 칸, 1~9: 모래성 튼튼함)
    private static int h, w;                  // 맵의 높이와 너비

    /**
     * 메인 메소드
     * 초기화 후 모든 모래성이 무너질 때까지 파도를 시뮬레이션
     */
    public static void main(String[] args) throws IOException {
        // 입력 처리 및 초기 상태 설정
        init();

        // 파도 시뮬레이션
        int time = 0;
        while (!toRemove.isEmpty()) {
            delete();   // 파도에 의해 모래성 무너뜨리기
            time++;     // 파도 횟수 증가
        }

        // 결과 출력
        bw.write(time + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 초기화 메소드
     * 입력을 받아 맵을 생성하고 첫 파도에 무너질 모래성 식별
     */
    private static void init() throws IOException {
        // 맵 크기 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());

        // 데이터 구조 초기화
        map = new int[h][w];
        visited = new boolean[h][w];
        castles = new ArrayList<>();

        // 맵 입력 처리
        for (int i = 0; i < h; i++) {
            char[] input = br.readLine().toCharArray();
            for (int j = 0; j < w; j++) {
                if (input[j] == '.') {
                    map[i][j] = -1;  // 빈 칸은 -1로 표시
                } else {
                    map[i][j] = input[j] - '0';  // 모래성은 튼튼함 값 저장
                    castles.add(new Point(i, j)); // 모래성 위치 저장
                }
            }
        }

        // 첫 파도에 무너질 모래성 식별
        toRemove = new ArrayDeque<>();
        for (Point castle : castles) {
            int wave = 0;  // 주변 빈 칸 수
            for (int i = 0; i < 8; i++) {
                int nx = castle.x + dx[i];
                int ny = castle.y + dy[i];

                // 범위를 벗어나지 않고 빈 칸인 경우 파도 카운트 증가
                if (!OOB(nx, ny) && map[nx][ny] == -1) wave++;
            }

            // 파도 수가 모래성의 튼튼함 이상이면 무너질 예정
            if (wave >= map[castle.x][castle.y]) {
                toRemove.add(castle);
                visited[castle.x][castle.y] = true;  // 이미 처리 표시
            }
        }
    }

    /**
     * 파도가 치는 효과를 시뮬레이션하는 메소드
     * 현재 큐에 있는 모래성을 모두 무너뜨리고, 다음 파도에 무너질 모래성을 큐에 추가
     */
    private static void delete() {
        int size = toRemove.size();  // 현재 파도에 무너질 모래성 수

        // 현재 파도에 무너질 모든 모래성 처리
        for (int i = 0; i < size; i++) {
            Point current = toRemove.poll();
            map[current.x][current.y] = -1;  // 모래성을 빈 칸으로 변경

            // 무너진 모래성 주변 8방향 확인
            for (int d = 0; d < 8; d++) {
                int nx = current.x + dx[d];
                int ny = current.y + dy[d];

                // 유효하지 않은 위치, 이미 빈 칸이거나 처리 예정인 모래성은 건너뜀
                if (OOB(nx, ny) || map[nx][ny] == -1 || visited[nx][ny]) continue;

                int wave = 0;  // 주변 빈 칸 수 계산
                for (int j = 0; j < 8; j++) {
                    int nnx = nx + dx[j];
                    int nny = ny + dy[j];

                    if (OOB(nnx, nny)) continue;
                    if (map[nnx][nny] == -1) wave++;
                }

                // 파도 수가 모래성의 튼튼함 이상이면 다음 파도에 무너질 예정
                if (wave >= map[nx][ny]) {
                    visited[nx][ny] = true;  // 중복 처리 방지
                    toRemove.add(new Point(nx, ny));
                }
            }
        }
    }

    /**
     * 좌표가 맵 범위를 벗어나는지 확인하는 메소드
     * @param nx x 좌표
     * @param ny y 좌표
     * @return 범위를 벗어나면 true, 그렇지 않으면 false
     */
    private static boolean OOB(int nx, int ny) {
        return nx < 0 || nx > h - 1 || ny < 0 || ny > w - 1;
    }
}