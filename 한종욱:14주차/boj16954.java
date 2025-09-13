import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Queue;

public class boj16954 {
    // 입출력을 위한 객체 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // 8방향(제자리 포함 9가지 방향) 움직임을 위한 배열
    // dx[0], dy[0]은 제자리에 머무는 경우
    // 나머지는 시계 방향으로 8방향 이동 (동쪽부터 시작)
    private static int[] dx = {0, 1, 1, 1, 0, -1, -1, -1, 0};
    private static int[] dy = {0, 1, 0, -1, 1, 1, 0, -1, -1};

    // 체스판(미로)를 저장할 2차원 배열
    // '.'은 빈 칸, '#'은 벽
    private static char[][] map;

    // 체스판 크기 (8x8)
    private static int n;

    public static void main(String[] args) throws IOException {
        // 입력 처리 및 초기화
        init();

        // 좌표 (n-1, 0) = (7, 0)에서 시작하여 BFS 수행
        // 좌측 하단이 시작점
        int time = BFS(n - 1, 0);

        // 캐릭터가 맨 위(1초에 1칸씩 움직여 맨 위까지 7초 필요)까지 도달할 수 있으면 1, 아니면 0 출력
        int answer = time == 7 ? 1 : 0;
        bw.write(String.valueOf(answer));
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 초기화 메소드: 입력을 받아 미로 정보를 초기화
     */
    private static void init() throws IOException {
        n = 8; // 체스판 크기는 항상 8x8
        map = new char[n][n];

        // 각 행의 정보 입력 받기
        for (int i = 0; i < n; i++) {
            map[i] = br.readLine().toCharArray();
        }
    }

    /**
     * BFS를 통해 캐릭터가 움직일 수 있는 최대 시간을 계산
     * @param x 시작 x 좌표 (행)
     * @param y 시작 y 좌표 (열)
     * @return 캐릭터가 움직일 수 있는 최대 시간
     */
    private static int BFS(int x, int y) {
        Queue<int[]> q = new ArrayDeque<>();
        int max = 0; // 캐릭터가 살아남은 최대 시간

        // 큐에 시작 위치와 시간(0)을 추가
        // int[]{x좌표, y좌표, 현재 시간}
        q.add(new int[]{x, y, 0});

        while (!q.isEmpty()) {
            int[] current = q.poll();

            // 최대 생존 시간 갱신
            max = Math.max(max, current[2]);

            // 만약 최대 시간이 7이면 목표에 도달한 것이므로 루프 종료
            // 7초면 맨 위에 도달했다는 의미
            if (max == 7) break;

            // 9가지 방향(제자리 포함)으로 이동 시도
            for (int i = 0; i < 9; i++) {
                int nx = current[0] + dx[i];
                int ny = current[1] + dy[i];

                // 다음 위치가 범위를 벗어나거나,
                // 현재 시간+다음 위치가 체스판 밖으로 나가거나,
                // 다음 위치에 벽이 있거나,
                // 다음 위치 위에 벽이 있어서 다음 턴에 벽이 내려올 경우 이동 불가
                if (OOB(nx, ny) || nx + current[2] > n - 1 || map[nx][ny] == '#' || (nx > 0 && map[nx - 1][ny] == '#')) continue;

                // 벽이 1초마다 아래로 한 칸씩 내려오므로, 캐릭터도 함께 한 칸 위로 이동
                // (실제로는 벽이 내려오는 것과 동일한 효과)
                if (nx > 0) nx--;

                // 다음 위치와 시간+1을 큐에 추가
                q.add(new int[]{nx, ny, current[2] + 1});
            }
        }

        return max; // 최대 생존 시간 반환
    }

    /**
     * 주어진 좌표가 체스판 범위를 벗어나는지 확인
     * @param nx x 좌표
     * @param ny y 좌표
     * @return 범위를 벗어나면 true, 그렇지 않으면 false
     */
    private static boolean OOB(int nx, int ny) {
        return nx < 0 || nx > n - 1 || ny < 0 || ny > n - 1;
    }

    /**
     * 벽의 위치를 저장하는 클래스 (현재 코드에서는 사용되지 않음)
     */
    static class Wall {
        int x;
        int y;
        Wall(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}