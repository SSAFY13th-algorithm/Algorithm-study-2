import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

public class boj16469 {
    // 입출력을 위한 객체들
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    // 4방향 탐색을 위한 방향 배열 (하,우,상,좌)
    private static final int[] dx = {1, 0, -1, 0};
    private static final int[] dy = {0, 1, 0, -1};
    private static final StringBuilder sb = new StringBuilder();

    // map[x][y][0~2]: 각 악당의 이동 거리, map[x][y][3]: 벽 여부
    private static int[][][] map;
    // visited[x][y][i]: i번째 악당의 방문 여부
    private static boolean[][][] visited;
    // BFS를 위한 큐
    private static Queue<int[]> queue = new LinkedList<>();
    private static int r, c;              // 격자의 크기
    private static int count = 0;         // 모든 악당이 만날 수 있는 지점의 개수
    private static int min = Integer.MAX_VALUE;  // 최소 시간

    public static void main(String[] args) throws IOException {
        setting();    // 입력 처리 및 초기 설정

        // 세 악당의 시작 위치 입력 받고 큐에 추가
        for (int i = 0; i < 3; i++) {
            String[] input = br.readLine().split(" ");
            int x = Integer.parseInt(input[0]);
            int y = Integer.parseInt(input[1]);
            visited[x][y][i] = true;  // 시작 위치 방문 체크
            queue.add(new int[]{x, y, i});  // 큐에 [x좌표, y좌표, 악당번호] 추가
        }

        bfs();  // BFS로 각 악당의 이동 경로 탐색

        // 결과 출력
        if (count == 0) {  // 만날 수 있는 지점이 없는 경우
            min = -1;
            sb.append(min).append("\n");
        }
        else {  // 만날 수 있는 지점이 있는 경우
            sb.append(min).append("\n").append(count).append("\n");
        }

        // 결과 출력 및 스트림 닫기
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    // 입력 처리 및 초기 설정
    private static void setting() throws IOException {
        String[] input = br.readLine().split(" ");
        r = Integer.parseInt(input[0]);
        c = Integer.parseInt(input[1]);

        // 3차원 배열 초기화 ([좌표][좌표][악당/벽])
        map = new int[r + 1][c + 1][4];
        visited = new boolean[r + 1][c + 1][3];

        // 격자 정보 입력 (벽 정보는 map[x][y][3]에 저장)
        for (int i = 1; i <= r; i++) {
            input = br.readLine().split("");
            for (int j = 1; j <= c; j++) {
                map[i][j][3] = Integer.parseInt(input[j - 1]);
            }
        }
    }

    // BFS로 각 악당의 이동 경로 탐색
    private static void bfs() {
        int time = 0;  // 현재 시간

        while (!queue.isEmpty()) {
            int length = queue.size();  // 현재 시간에 처리할 위치의 수
            time++;

            // 현재 시간에 있는 모든 위치에 대해 처리
            for (int i = 0; i < length; i++) {
                int[] current = queue.poll();

                // 4방향 탐색
                for (int j = 0; j < 4; j++) {
                    int nx = current[0] + dx[j];
                    int ny = current[1] + dy[j];

                    // 범위를 벗어나거나, 벽이거나, 이미 방문한 경우 스킵
                    if (OOB(nx, ny) || map[nx][ny][3] == 1 || visited[nx][ny][current[2]]) continue;

                    visited[nx][ny][current[2]] = true;  // 방문 체크
                    // 이동 거리 갱신
                    map[nx][ny][current[2]] = map[current[0]][current[1]][current[2]] + 1;
                    queue.add(new int[]{nx, ny, current[2]});  // 다음 위치 큐에 추가
                }
            }

            // 세 악당이 만날 수 있는 지점 찾기
            boolean found = false;
            for (int i = 1; i <= r; i++) {
                for (int j = 1; j <= c; j++) {
                    // 세 악당이 모두 도달할 수 있는 지점인지 확인
                    if (visited[i][j][0] && visited[i][j][1] && visited[i][j][2]) {
                        found = true;
                        count++;  // 만날 수 있는 지점 개수 증가
                    }
                }
            }

            // 만날 수 있는 지점을 찾았다면 최소 시간 갱신
            if (found) {
                min = Math.min(min, time);
                break;
            }
        }
    }

    // 주어진 좌표가 격자 범위를 벗어나는지 체크
    private static boolean OOB(int nx, int ny) {
        return nx < 1 || nx > r || ny < 1 || ny > c;
    }
}