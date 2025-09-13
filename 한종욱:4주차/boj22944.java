import java.io.*;
import java.util.*;
public class boj22944 {
    // 입출력을 위한 BufferedReader와 BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // 상하좌우 이동을 위한 방향 배열
    private static final int[] dx = { 1, 0, -1, 0 };
    private static final int[] dy = { 0, 1, 0, -1 };

    // 맵 정보와 방문 체크를 위한 배열들
    private static char[][] map;              // 맵 정보
    private static boolean[][][] visited;      // 방문 체크 (x, y, 우산 번호)
    private static int[] start = new int[2];   // 시작 위치
    private static int[] end = new int[2];     // 도착 위치
    private static int n, h, d;               // 맵 크기, 체력, 우산 내구도

    public static void main(String[] args) throws IOException {
        init();    // 초기 설정
        sb.append(bfs(start[0], start[1]));    // BFS 실행 및 결과 저장

        // 결과 출력 및 스트림 닫기
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    // 초기 설정을 위한 메소드
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());    // 맵 크기
        h = Integer.parseInt(st.nextToken());    // 초기 체력
        d = Integer.parseInt(st.nextToken());    // 우산 내구도

        map = new char[n][n];
        int umbrellaCount = 0;    // 우산 개수

        // 맵 정보 입력
        for (int i = 0; i < n; i++) {
            String line = br.readLine();
            for (int j = 0; j < n; j++) {
                char c = line.charAt(j);
                if (c == 'U') {    // 우산인 경우 번호 부여
                    umbrellaCount++;
                    map[i][j] = (char) (umbrellaCount + '0');
                } else {
                    map[i][j] = c;
                    if (c == 'S') {        // 시작 위치 저장
                        start[0] = i;
                        start[1] = j;
                    } else if (c == 'E') {    // 도착 위치 저장
                        end[0] = i;
                        end[1] = j;
                    }
                }
            }
        }
        visited = new boolean[n][n][umbrellaCount + 1];
    }

    // BFS 탐색을 수행하는 메소드
    private static int bfs(int x, int y) {
        Queue<Node> queue = new ArrayDeque<>();
        visited[x][y][0] = true;
        map[x][y] = '.';
        queue.add(new Node(x, y, 0, h, 0, 0));    // 초기 위치 큐에 추가

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.hp == 0) continue;    // 체력이 0이면 죽음

            // 도착지점에 도달한 경우
            if (current.x == end[0] && current.y == end[1]) {
                return current.time;
            }

            // 4방향 탐색
            for (int i = 0; i < 4; i++) {
                int nx = current.x + dx[i];
                int ny = current.y + dy[i];

                // 맵을 벗어나거나 이미 방문한 경우
                if (OOB(nx, ny) || visited[nx][ny][current.umbrellaIndex]) continue;

                // 빈 공간인 경우
                if (map[nx][ny] == '.'){
                    if (current.durability > 0) {    // 우산 내구도가 남아있는 경우
                        visited[nx][ny][current.umbrellaIndex] = true;
                        queue.add(new Node(nx, ny, current.time+1, current.hp, current.umbrellaIndex, current.durability-1));
                    } else {    // 우산이 없는 경우 체력 감소
                        visited[nx][ny][current.umbrellaIndex] = true;
                        queue.add(new Node(nx, ny, current.time+1, current.hp-1, current.umbrellaIndex, 0));
                    }
                }
                // 도착지점인 경우
                else if (map[nx][ny] == 'E') {
                    visited[nx][ny][current.umbrellaIndex] = true;
                    queue.add(new Node(nx, ny, current.time+1, current.hp, current.umbrellaIndex, current.durability));
                }
                // 우산이 있는 경우
                else {
                    queue.add(new Node(nx, ny, current.time+1, current.hp, map[nx][ny] - '0', d-1));
                    visited[nx][ny][map[nx][ny] - '0'] = true;
                    map[nx][ny] = '.';    // 우산 사용 후 제거
                }
            }
        }
        return -1;    // 도착지점에 도달할 수 없는 경우
    }

    // 맵 범위를 벗어났는지 체크하는 메소드
    private static boolean OOB(int nx, int ny) {
        return nx < 0 || nx > n-1 || ny < 0 || ny > n-1;
    }

    // 현재 상태를 저장하는 Node 클래스
    static class Node {
        private int x, y;            // 현재 위치
        private int time;            // 이동 시간
        private int hp;              // 현재 체력
        private int umbrellaIndex;   // 현재 우산 번호
        private int durability;      // 우산 내구도

        public Node(int x, int y, int time, int hp, int umbrellaIndex, int durability) {
            this.x = x;
            this.y = y;
            this.time = time;
            this.hp = hp;
            this.umbrellaIndex = umbrellaIndex;
            this.durability = durability;
        }
    }
}