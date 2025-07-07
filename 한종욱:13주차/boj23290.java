import java.io.*;
import java.util.*;

public class boj23290 {
    // 입출력을 위한 버퍼 객체 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // 상어 이동을 위한 방향 배열 (상하좌우: 1234)
    private static final int[] sdx = {0, -1, 0, 1, 0};  // 상어 x축 이동 방향
    private static final int[] sdy = {0, 0, -1, 0, 1};  // 상어 y축 이동 방향

    // 물고기 이동을 위한 방향 배열 (←, ↖, ↑, ↗, →, ↘, ↓, ↙: 1~8)
    private static final int[] dx = {0, 0, -1, -1, -1, 0, 1, 1, 1};  // 물고기 x축 이동 방향
    private static final int[] dy = {0, -1, -1, 0, 1, 1, 1, 0, -1};  // 물고기 y축 이동 방향

    // 게임판과 관련된 변수들
    private static Cell[][] map;       // 현재 게임판
    private static Cell[][] copy;      // 복제용 게임판
    private static List<Status> list;  // 상어가 이동할 수 있는 경로 저장 리스트
    private static boolean[][] visited; // 상어 이동 시 방문 여부 확인
    private static Shark shark;        // 상어 객체
    private static int m, s;           // m: 물고기 수, s: 마법 시전 횟수

    public static void main(String[] args) throws IOException {
        // 초기 설정 및 입력 처리
        init();

        // s번 마법 시전
        while (s-- > 0) {
            copyMap();     // 1. 물고기 복제 마법 준비
            moveFishes();  // 2. 물고기 이동
            moveShark();   // 3. 상어 이동 (물고기 냠냠)
            removeSmell(); // 4. 물고기 냄새 제거
            copyFish();    // 5. 복제 마법 완료
        }

        // 물고기 수 세기
        int answer = countFishes();
        bw.write(String.valueOf(answer));
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 초기 설정 및 입력을 처리하는 함수
     */
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());  // 물고기 수
        s = Integer.parseInt(st.nextToken());  // 마법 시전 횟수

        // 게임판 초기화 (1-indexed, 4x4 크기)
        map = new Cell[5][5];

        // 셀 객체 생성
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                map[i][j] = new Cell();
            }
        }

        // 물고기 정보 입력
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());  // 물고기 행 위치
            int y = Integer.parseInt(st.nextToken());  // 물고기 열 위치
            int dir = Integer.parseInt(st.nextToken()); // 물고기 방향
            map[x][y].fishes.add(dir);  // 해당 위치에 물고기 추가
        }

        // 상어 위치 입력
        st = new StringTokenizer(br.readLine());
        shark = new Shark(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
    }

    /**
     * 현재 게임판을 복사하는 함수 (물고기 복제 마법 준비 단계)
     */
    private static void copyMap() {
        copy = new Cell[5][5];
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                copy[i][j] = new Cell();

                // 현재 맵의 물고기들을 복사 맵에 복제
                for (int fish : map[i][j].fishes) {
                    copy[i][j].fishes.add(fish);
                }
            }
        }
    }

    /**
     * 물고기들을 이동시키는 함수
     */
    private static void moveFishes() {
        // 이동 후 상태를 저장할 임시 게임판
        Cell[][] temp = new Cell[5][5];

        // 임시 게임판 초기화 (냄새 정보 복사)
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                temp[i][j] = new Cell();
                temp[i][j].smell = map[i][j].smell;
            }
        }

        // 모든 위치의 물고기들을 확인하며 이동
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                if (!map[i][j].fishes.isEmpty()) {
                    boolean canMove = false;
                    // 각 물고기의 방향에 대해
                    for (int dir : map[i][j].fishes) {
                        int count = 0;
                        // 최대 8방향을 시도 (반시계 방향으로 45도씩 회전)
                        while (count < 8) {
                            int nx = i + dx[dir];
                            int ny = j + dy[dir];

                            // 이동할 수 있는 조건: 맵 안이고, 상어가 없고, 냄새가 없어야 함
                            if (!OOB(nx, ny) && !gotoShark(nx, ny) && !leftSmell(nx, ny)){
                                temp[nx][ny].fishes.add(dir);
                                canMove = true;
                                break;
                            }

                            // 이동할 수 없으면 45도 반시계 회전
                            dir--;
                            if (dir == 0) dir = 8;
                            count++;
                        }

                        // 8방향 모두 이동할 수 없으면 제자리에 남음
                        if (!canMove) {
                            for (int fdir : map[i][j].fishes) {
                                temp[i][j].fishes.add(fdir);
                            }
                        }
                    }
                }
            }
        }

        // 이동 완료된 임시 게임판을 현재 게임판으로 갱신
        map = temp;
    }

    /**
     * 상어를 이동시키는 함수
     */
    private static void moveShark() {
        visited = new boolean[5][5];
        list = new ArrayList<>();
        int eatFishes = 0;
        List<int[]> eatenFishesPos = new ArrayList<>();

        // DFS를 통해 상어의 모든 가능한 3칸 이동 경로 탐색
        dfs(0, shark.x, shark.y, eatFishes, eatenFishesPos, "");

        // 가장 많은 물고기를 먹을 수 있는 경로 선택
        // 같은 수의 물고기를 먹을 수 있다면 사전순으로 가장 앞선 경로 선택
        Collections.sort(list, (o1, o2) -> {
            if (o1.eatFishes == o2.eatFishes) return o1.dirs.compareTo(o2.dirs);
            return Integer.compare(o2.eatFishes, o1.eatFishes);
        });

        // 선택된 경로로 상어 이동 및 물고기 제거
        Status nextPos = list.get(0);
        shark.x = nextPos.x;
        shark.y = nextPos.y;
        for (int[] pos : nextPos.eatenFishesPos) {
            map[pos[0]][pos[1]].smell = 3;  // 냄새 남기기 (3턴 지속)
            map[pos[0]][pos[1]].fishes.clear();  // 물고기 제거
        }
    }

    /**
     * 상어가 이동할 수 있는 모든 경로를 탐색하는 DFS 함수
     * @param index 이동 횟수 (0~2)
     * @param x 현재 x 좌표
     * @param y 현재 y 좌표
     * @param eatFishes 현재까지 먹은 물고기 수
     * @param eatenFishesPos 물고기를 먹은 위치 목록
     * @param dirs 이동 방향 문자열 (예: "123")
     */
    private static void dfs(int index, int x, int y, int eatFishes, List<int[]> eatenFishesPos, String dirs) {
        // 3칸 이동 완료 시 결과 저장
        if (index == 3) {
            List<int[]> temp = new ArrayList<>();
            for (int[] pos : eatenFishesPos) {
                temp.add(pos);
            }
            list.add(new Status(x, y, eatFishes, temp, dirs));
            return;
        }

        // 4방향(상하좌우) 탐색
        for (int i = 1; i <= 4; i++) {
            int nx = x + sdx[i];
            int ny = y + sdy[i];
            boolean flag = false;

            // 맵을 벗어나면 건너뛰기
            if (OOB(nx, ny)) continue;

            // 물고기가 있고 아직 방문하지 않은 위치라면
            if (!map[nx][ny].fishes.isEmpty() && !visited[nx][ny]) {
                eatFishes += map[nx][ny].fishes.size();  // 물고기 먹기
                eatenFishesPos.add(new int[]{nx, ny});  // 위치 기록
                visited[nx][ny] = true;  // 방문 처리
                flag = true;  // 상태 변경 표시
            }

            // 다음 이동 진행
            dfs(index + 1, nx, ny, eatFishes, eatenFishesPos, dirs + "" + i);

            // 상태를 변경했다면 백트래킹
            if (flag) {
                eatFishes -= map[nx][ny].fishes.size();  // 물고기 수 복원
                eatenFishesPos.remove(eatenFishesPos.size() - 1);  // 위치 기록 삭제
                visited[nx][ny] = false;  // 방문 처리 복원
            }
        }
    }

    /**
     * 해당 위치가 상어가 있는 위치인지 확인하는 함수
     */
    private static boolean gotoShark(int nx, int ny) {
        return nx == shark.x && ny == shark.y;
    }

    /**
     * 해당 위치에 물고기 냄새가 있는지 확인하는 함수
     */
    private static boolean leftSmell(int nx, int ny) {
        return map[nx][ny].smell > 0;
    }

    /**
     * 해당 위치가 맵을 벗어나는지 확인하는 함수 (Out Of Bounds)
     */
    private static boolean OOB(int nx, int ny) {
        return nx < 1 || nx > 4 || ny < 1 || ny > 4;
    }

    /**
     * 물고기 냄새를 감소시키는 함수 (매 턴마다 1씩 감소)
     */
    private static void removeSmell() {
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                if (map[i][j].smell > 0) map[i][j].smell--;
            }
        }
    }

    /**
     * 복제 마법을 완료하는 함수 (복사해둔 물고기를 현재 맵에 추가)
     */
    private static void copyFish() {
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                for (int fish : copy[i][j].fishes) {
                    map[i][j].fishes.add(fish);
                }
            }
        }
    }

    /**
     * 현재 맵에 있는 총 물고기 수를 세는 함수
     */
    private static int countFishes() {
        int cnt = 0;
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                cnt += map[i][j].fishes.size();
            }
        }

        return cnt;
    }

    /**
     * 상어의 이동 상태를 저장하는 클래스
     */
    static class Status {
        int x;                     // 최종 x 위치
        int y;                     // 최종 y 위치
        int eatFishes;             // 먹은 물고기 수
        List<int[]> eatenFishesPos; // 물고기를 먹은 위치들
        String dirs;               // 이동 방향 문자열

        public Status(int x, int y, int eatFishes, List<int[]> eatenFishesPos, String dirs) {
            this.x = x;
            this.y = y;
            this.eatFishes = eatFishes;
            this.eatenFishesPos = eatenFishesPos;
            this.dirs = dirs;
        }
    }

    /**
     * 게임판의 각 셀을 나타내는 클래스
     */
    static class Cell {
        List<Integer> fishes; // 현재 위치의 물고기 방향 목록
        int smell;           // 물고기 냄새 지속 시간

        Cell() {
            this.fishes = new ArrayList<>();
            this.smell = 0;
        }
    }

    /**
     * 상어 객체를 나타내는 클래스
     */
    static class Shark {
        int x; // 상어의 x 좌표
        int y; // 상어의 y 좌표

        Shark(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}