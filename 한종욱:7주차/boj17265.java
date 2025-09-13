import java.io.*;
import java.util.*;

/**
 * 백준 17265번 - 나의 인생에는 수학과 함께
 *
 * 문제 개요:
 * - N×N 크기의 격자에 숫자(0-5)와 연산자(+,-,*)가 교대로 채워져 있음
 * - 좌상단(0,0)에서 우하단(N-1,N-1)까지 이동
 * - 이동 방향은 오른쪽과 아래쪽만 가능
 * - 경로에서 만난 숫자와 연산자를 순서대로 계산
 * - 가능한 모든 경로 중에서 최댓값과 최솟값을 구하는 문제
 */
public class boj17265 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    // 이동 방향 (오른쪽, 아래쪽)
    private static final int[] dx = {1, 0};
    private static final int[] dy = {0, 1};

    private static char[][] map;    // 격자 맵 (숫자와 연산자)
    private static int n;           // 격자 크기
    private static int max, min;    // 최댓값, 최솟값

    /**
     * 메인 메소드
     * 입력 처리 후 DFS로 모든 경로 탐색
     */
    public static void main(String[] args) throws IOException {
        // 입력 처리 및 초기화
        init();

        // DFS로 모든 가능한 경로 탐색
        // 시작점(0,0)의 값을 초기 결과로 설정, 연산자는 아직 없으므로 'a'로 임시 설정
        DFS(0, 0, map[0][0] - '0', 'a');

        // 결과 출력
        bw.write(max + " " + min + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력을 처리하고 변수들을 초기화하는 메소드
     */
    private static void init() throws IOException {
        // 격자 크기 입력
        n = Integer.parseInt(br.readLine());

        // 최댓값과 최솟값 초기화 (가능한 범위를 고려하여 설정)
        // 최대 5^5 = 3125, 최소 -3125
        max = -3125;
        min = 3125;

        // 격자 맵 초기화 및 입력
        map = new char[n][n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = st.nextToken().charAt(0);
            }
        }
    }

    /**
     * DFS로 모든 가능한 경로를 탐색하는 메소드
     * @param x 현재 x좌표
     * @param y 현재 y좌표
     * @param result 현재까지의 계산 결과
     * @param op 마지막으로 만난 연산자 (초기값 'a'는 연산자가 없음을 의미)
     */
    private static void DFS(int x, int y, int result, char op) {
        // 목적지에 도달했을 때 최댓값, 최솟값 갱신
        if (x == n - 1 && y == n - 1) {
            max = Math.max(max, result);
            min = Math.min(min, result);
            return;
        }

        // 가능한 두 방향(오른쪽, 아래쪽)으로 탐색
        for (int i = 0; i < 2; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            // 격자 범위를 벗어나면 건너뜀
            if (OOB(nx, ny)) continue;

            // 숫자인 경우 (0-5)
            if (map[nx][ny] >= '0' && map[nx][ny] <= '5') {
                int num = map[nx][ny] - '0';

                // 이전 연산자에 따라 계산 후 다음 DFS 호출
                // 연산자가 없는 경우('a')는 첫 번째 숫자이므로 계산하지 않음
                if (op == '+') {
                    DFS(nx, ny, result + num, 'a'); // 덧셈 수행
                } else if (op == '-') {
                    DFS(nx, ny, result - num, 'a'); // 뺄셈 수행
                } else if (op == '*') {
                    DFS(nx, ny, result * num, 'a'); // 곱셈 수행
                } else {
                    // 첫 번째 숫자인 경우 (op가 'a'인 경우)
                    DFS(nx, ny, num, 'a');
                }
            }
            // 연산자인 경우 (+, -, *)
            else {
                // 현재 연산자를 저장하고 결과는 변경하지 않음
                DFS(nx, ny, result, map[nx][ny]);
            }
        }
    }

    /**
     * 좌표가 격자 범위를 벗어나는지 확인하는 메소드
     * @param nx x 좌표
     * @param ny y 좌표
     * @return 범위를 벗어나면 true, 그렇지 않으면 false
     */
    private static boolean OOB(int nx, int ny) {
        return nx < 0 || nx > n - 1 || ny < 0 || ny > n - 1;
    }
}