import java.io.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 봄버맨 게임 시뮬레이션 클래스
 * 매 초마다 봄버맨의 행동을 시뮬레이션하고 결과를 출력합니다.
 */
public class boj16918 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final char EMPTY = '.';  // 빈 칸을 나타내는 문자
    private static final char BOMB = 'O';   // 폭탄을 나타내는 문자
    private static final List<Point> POINTS = new ArrayList<>();  // 폭탄의 위치를 저장하는 리스트
    private static final StringBuilder sb = new StringBuilder();  // 결과 문자열 생성을 위한 StringBuilder
    private static final int[] dx = {1, 0, -1, 0};  // 상하좌우 이동을 위한 x축 변화량
    private static final int[] dy = {0, 1, 0, -1};  // 상하좌우 이동을 위한 y축 변화량
    private static char[][] zone;  // 격자판(게임 맵)
    private static int r, c, n;  // r: 행 수, c: 열 수, n: 시간(초)

    /**
     * 메인 메소드
     * 초기화 후 n초 동안의 봄버맨 게임을 시뮬레이션합니다.
     */
    public static void main(String[] args) throws IOException{
        // 입력을 받아 초기 상태 설정
        init();

        // n초 동안의 게임 시뮬레이션
        // 1초: 초기 상태 그대로 유지
        // 짝수 초: 모든 빈 칸에 폭탄 설치
        // 홀수 초(3초 이상): 폭탄 폭발
        for (int time = 2; time <= n; time++) {
            if (time % 2 == 0) fillBomb();  // 짝수 초에는 빈 칸에 폭탄 설치
            else boom();  // 홀수 초에는 폭탄 폭발
        }

        // 최종 상태 출력
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                sb.append(zone[i][j]);
            }
            sb.append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 초기 상태를 설정하는 메소드
     * 입력을 받아 격자판과 초기 폭탄 위치를 설정합니다.
     */
    private static void init() throws IOException{
        // 행, 열, 시간 입력 받기
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());

        // 격자판 초기화
        zone = new char[r][c];

        // 격자판 상태 입력 받기
        for (int i = 0; i < r; i++) {
            char[] input = br.readLine().toCharArray();
            for (int j = 0; j < c; j++) {
                zone[i][j] = input[j];
                // 폭탄 위치 저장
                if (zone[i][j] == BOMB) POINTS.add(new Point(i, j));
            }
        }
    }

    /**
     * 빈 칸에 폭탄을 설치하는 메소드
     * 격자판의 모든 빈 칸에 폭탄을 설치합니다.
     */
    private static void fillBomb() {
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (zone[i][j] == EMPTY) zone[i][j] = BOMB;
            }
        }
    }

    /**
     * 폭탄을 폭발시키는 메소드
     * 현재 존재하는 모든 폭탄을 폭발시키고, 폭발 범위(상하좌우)도 함께 제거합니다.
     */
    private static void boom() {
        boolean[][] visited = new boolean[r][c];  // 이미 처리한 칸 표시

        // 저장된 모든 폭탄 위치에 대해 폭발 처리
        for (Point point : POINTS) {
            // 폭탄 위치를 빈 칸으로 변경
            zone[point.x][point.y] = EMPTY;
            visited[point.x][point.y] = true;

            // 폭탄의 상하좌우 처리
            for (int i = 0; i < 4; i++) {
                int nx = point.x + dx[i];
                int ny = point.y + dy[i];

                // 격자판 범위를 벗어나거나 이미 처리한 칸이면 건너뜀
                if (OOB(nx, ny) || visited[nx][ny]) continue;

                // 해당 칸을 처리했음을 표시하고 빈 칸으로 변경
                visited[nx][ny] = true;
                zone[nx][ny] = EMPTY;
            }
        }

        // 폭발 후 남아있는 폭탄 위치 갱신
        POINTS.clear();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (zone[i][j] == BOMB) POINTS.add(new Point(i, j));
            }
        }
    }

    /**
     * 좌표가 격자판 범위를 벗어나는지 확인하는 메소드
     * @param nx x 좌표
     * @param ny y 좌표
     * @return 범위를 벗어나면 true, 그렇지 않으면 false
     */
    private static boolean OOB(int nx, int ny) {
        return nx < 0 || nx > r - 1 || ny < 0 || ny > c - 1;
    }
}