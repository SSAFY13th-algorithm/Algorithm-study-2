import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

public class boj15558 {
    // 입출력을 위한 BufferedReader, BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // 이동 방향 배열 (아래, 위, 제자리)
    private static final int[] dx = {1, -1, 0};
    private static final int[] dy = {0, 0, 0};

    // 게임 맵과 방문 여부 체크 배열
    private static int[][] map;
    private static boolean[][] visited;

    // 맵의 길이와 점프 거리
    private static int n, k;

    public static void main(String args[]) throws IOException {
        setting();  // 초기 설정

        // BFS 탐색 결과에 따라 도달 가능 여부 출력
        if (bfs(0, 0)) {
            sb.append(1);
        } else {
            sb.append(0);
        }

        // 결과 출력
        bw.write(sb.toString() + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // 초기 설정을 처리하는 메소드
    private static void setting() throws IOException {
        // 맵의 길이와 점프 거리 입력
        String[] input = br.readLine().split(" ");
        n = Integer.parseInt(input[0]);
        k = Integer.parseInt(input[1]);

        // 맵과 방문 배열 초기화
        map = new int[n][2];
        visited = new boolean[n][2];

        // 첫 번째 줄 입력
        input = br.readLine().split("");
        for (int i = 0; i < n; i++) {
            map[i][0] = Integer.parseInt(input[i]);
        }

        // 두 번째 줄 입력
        input = br.readLine().split("");
        for (int i = 0; i < n; i++) {
            map[i][1] = Integer.parseInt(input[i]);
        }
    }

    // BFS 탐색을 수행하는 메소드
    private static boolean bfs(int x, int y) {
        Queue<int[]> queue = new LinkedList<>();
        visited[x][y] = true;
        queue.add(new int[] {x, y, 0});  // x좌표, y좌표, 시간

        while (!queue.isEmpty()) {
            int[] current = queue.poll();

            // 세 가지 이동 방식에 대해 탐색
            for (int i = 0; i < 3; i++) {
                int nx = current[0];
                int ny = current[1];

                // 일반 이동
                if (i != 2) {
                    nx += dx[i];
                    ny += dy[i];
                }
                // k만큼 점프하며 라인 변경
                else {
                    nx += k;
                    ny = ny == 0 ? 1 : 0;
                }

                // 끝에 도달했다면 성공
                if (nx >= n) {
                    return true;
                }

                // 유효하지 않은 이동이면 스킵
                if (nx < 0 || nx < current[2] + 1 || map[nx][ny] == 0 || visited[nx][ny]) continue;

                // 방문 처리 후 큐에 추가
                visited[nx][ny] = true;
                queue.add(new int[]{nx, ny, current[2] + 1});
            }
        }

        return false;  // 도달 불가능
    }
}