import java.io.*;
import java.util.*;

public class boj16237 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    // 상어의 이동 방향 (0: 정지, 1: 위, 2: 아래, 3: 왼쪽, 4: 오른쪽)
    private static final int[] dx = {0, -1, 1, 0, 0};
    private static final int[] dy = {0, 0, 0, -1, 1};
    private static Cell[][] map;  // 격자 맵
    private static List<Shark> sharks;  // 상어 목록
    private static int n, m, k, answer;  // n: 맵 크기, m: 상어 수, k: 냄새 지속 시간, answer: 정답

    public static void main(String[] args) throws IOException {
        init();  // 초기 설정

        // 최대 1000초까지 시뮬레이션
        for (int i = 0; i <= 1000; i++) {
            if (sharks.size() == 1) {  // 1번 상어만 남으면 종료
                answer = i;
                break;
            }
            chooseDir();  // 각 상어의 이동 방향 결정
            move();       // 상어 이동
            battle();     // 같은 칸에 있는 상어들 중 가장 작은 번호만 남김
            downSmell();  // 냄새 시간 감소
        }

        bw.write(answer + "\n");  // 결과 출력
        bw.flush();
        bw.close();
        br.close();
    }

    // 초기 설정을 처리하는 메소드
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        answer = -1;  // 1000초 이내에 1번 상어만 남지 않으면 -1 출력

        map = new Cell[n][n];
        sharks = new ArrayList<>();

        // 맵 정보 입력
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = new Cell();

                int num = Integer.parseInt(st.nextToken());
                if (num > 0) {  // 상어가 있는 위치
                    Shark shark = new Shark(i, j, num);
                    map[i][j].sharkNum.add(num);
                    sharks.add(shark);

                    map[i][j].smell = new Smell(num, k);  // 냄새 설정
                }
            }
        }

        // 상어를 번호 순으로 정렬
        sharks.sort((o1, o2) -> {
            return o1.num - o2.num;
        });

        // 상어의 초기 방향 입력
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            sharks.get(i).dir = Integer.parseInt(st.nextToken());
        }

        // 각 상어의 방향 우선순위 입력
        for (int i = 0; i < m; i++) {
            for (int j = 1; j <= 4; j++) {  // 1: 위, 2: 아래, 3: 왼쪽, 4: 오른쪽
                st = new StringTokenizer(br.readLine());
                for (int k = 0; k < 4; k++) {
                    sharks.get(i).priorityDir[j][k] = Integer.parseInt(st.nextToken());
                }
            }
        }
    }

    // 모든 상어의 이동 방향을 결정하는 메소드
    private static void chooseDir() {
        for (Shark shark : sharks) {
            shark.dir = chooseDir(shark);
        }
    }

    // 개별 상어의 이동 방향을 결정하는 메소드
    private static int chooseDir(Shark shark) {
        int[] priorityDir = shark.priorityDir[shark.dir];  // 현재 방향에 따른 우선순위

        // 1. 냄새가 없는 칸 찾기
        for (int i = 0; i < 4; i++) {
            int nx = shark.x + dx[priorityDir[i]];
            int ny = shark.y + dy[priorityDir[i]];

            if (!OOB(nx, ny) && map[nx][ny].smell.sharkNum == 0) {
                return priorityDir[i];
            }
        }

        // 2. 자신의 냄새가 있는 칸 찾기
        for (int i = 0; i < 4; i++) {
            int nx = shark.x + dx[priorityDir[i]];
            int ny = shark.y + dy[priorityDir[i]];

            if (!OOB(nx, ny) && map[nx][ny].smell.sharkNum == shark.num) {
                return priorityDir[i];
            }
        }

        return 0;  // 여기까지 오면 안됨
    }

    // 맵 범위를 벗어났는지 확인하는 메소드
    private static boolean OOB(int nx, int ny) {
        return nx < 0 || nx > n - 1 || ny < 0 || ny > n - 1;
    }

    // 상어 이동을 처리하는 메소드
    private static void move() {
        Cell[][] board = new Cell[n][n];  // 이동 후의 새로운 맵

        // 냄새 정보만 복사
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = new Cell();
                if (map[i][j].smell.sharkNum == 0) continue;
                board[i][j].smell = map[i][j].smell;
            }
        }

        // 각 상어 이동
        for (Shark shark : sharks) {
            int nx = shark.x + dx[shark.dir];
            int ny = shark.y + dy[shark.dir];

            shark.x += dx[shark.dir];  // 상어 위치 업데이트
            shark.y += dy[shark.dir];

            board[nx][ny].smell = new Smell(shark.num, k);  // 새 위치에 냄새 남김
            board[nx][ny].sharkNum.add(shark.num);  // 새 위치에 상어 정보 추가
        }

        map = board;  // 맵 업데이트
    }

    // 같은 칸에 여러 상어가 있을 때 번호가 가장 작은 상어만 남기는 메소드
    private static void battle() {
        boolean[][] visited = new boolean[n][n];
        List<Integer> toRemove = new ArrayList<>();  // 제거할 상어 번호

        for (Shark shark : sharks) {
            int x = shark.x;
            int y = shark.y;

            if (visited[x][y]) continue;  // 이미 처리한 칸은 스킵
            visited[x][y] = true;

            if (map[x][y].sharkNum.size() == 1) continue;  // 상어가 하나만 있으면 스킵
            Collections.sort(map[x][y].sharkNum);  // 번호 순 정렬

            // 가장 작은 번호를 제외한 모든 상어 제거
            for (int i = map[x][y].sharkNum.size() - 1; i > 0; i--) {
                int sharkNumToRemove = map[x][y].sharkNum.get(i);
                toRemove.add(sharkNumToRemove);
                map[x][y].sharkNum.remove(i);
            }
            map[x][y].smell.sharkNum = map[x][y].sharkNum.get(0);  // 냄새 업데이트
        }

        // 상어 목록에서 제거할 상어들 제거
        for (int i = sharks.size() - 1; i >= 0; i--) {
            if (toRemove.contains(sharks.get(i).num)) {
                sharks.remove(i);
            }
        }
    }

    // 냄새 시간을 감소시키는 메소드
    private static void downSmell() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // 냄새가 없거나 현재 상어가 있는 칸은 스킵
                if (map[i][j].smell.sharkNum == 0 || !map[i][j].sharkNum.isEmpty()) continue;

                // 냄새 시간 감소
                if (map[i][j].smell.time > 0) {
                    map[i][j].smell.time--;
                }

                // 냄새 시간이 0이 되면 냄새 제거
                if (map[i][j].smell.time == 0) {
                    map[i][j].smell.sharkNum = 0;
                }
            }
        }
    }

    // 맵의 각 칸을 표현하는 클래스
    static class Cell {
        Smell smell;  // 냄새 정보
        List<Integer> sharkNum;  // 해당 칸에 있는 상어 번호 목록

        Cell () {
            smell = new Smell();
            sharkNum = new ArrayList<>();
        }
    }

    // 냄새 정보를 표현하는 클래스
    static class Smell {
        int sharkNum;  // 냄새를 남긴 상어 번호
        int time;      // 냄새가 남아있는 시간

        Smell(int sharkNum, int time) {
            this.sharkNum = sharkNum;
            this.time = time;
        }

        Smell () {
            this.sharkNum = 0;
            this.time = 0;
        }
    }

    // 상어 정보를 표현하는 클래스
    static class Shark {
        int x, y;      // 상어의 위치
        int num;       // 상어 번호
        int dir;       // 현재 방향
        int[][] priorityDir;  // 방향별 우선순위

        Shark(int x, int y, int num) {
            this.x = x;
            this.y = y;
            this.num = num;
            this.dir = 0;
            priorityDir = new int[5][4];  // [현재방향][우선순위]
        }
    }
}