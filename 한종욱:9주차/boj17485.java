import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {
    // 입출력을 위한 BufferedReader, BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // dp[i][j][k]: i행 j열에서 k방향으로 이동했을 때의 최소 비용
    // k=0: 오른쪽 대각선 아래, k=1: 바로 아래, k=2: 왼쪽 대각선 아래
    private static int[][][] dp;

    // 광산의 각 위치별 크기 정보
    private static int[][] arr;

    // 광산의 크기
    private static int n, m;

    public static void main(String[] args) throws IOException {
        init();  // 초기 데이터 설정
        DP();    // 동적 프로그래밍으로 최소 비용 계산

        // 마지막 행의 모든 위치에서 최소 비용 찾기
        int answer = (int)1e9;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < 3; j++) {
                answer = Math.min(answer, dp[n - 1][i][j]);
            }
        }

        bw.write(String.valueOf(answer));
        bw.flush();
        bw.close();
        br.close();
    }

    // 초기 데이터를 설정하는 메소드
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());  // 행 개수
        m = Integer.parseInt(st.nextToken());  // 열 개수

        dp = new int[n][m][3];  // DP 배열 초기화
        arr = new int[n][m];    // 광산 정보 배열 초기화

        // 광산 정보 입력
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 첫 번째 행의 초기값 설정
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < 3; j++) {
                dp[0][i][j] = arr[0][i];
            }
        }
    }

    // 동적 프로그래밍으로 최소 비용을 계산하는 메소드
    private static void DP() {
        for (int i = 1; i < n; i++) {
            // 첫 번째 열의 경우
            dp[i][0][2] = (int)1e9;  // 왼쪽 대각선 아래로 이동 불가능
            dp[i][0][1] = arr[i][0] + dp[i - 1][0][0];  // 바로 아래로 이동
            dp[i][0][0] = arr[i][0] + Math.min(dp[i - 1][1][1], dp[i - 1][1][2]);  // 오른쪽 대각선 아래로 이동

            // 중간 열들의 경우
            for (int j = 1; j < m - 1; j++) {
                dp[i][j][0] = arr[i][j] + Math.min(dp[i - 1][j + 1][1], dp[i - 1][j + 1][2]);  // 오른쪽 대각선 아래
                dp[i][j][1] = arr[i][j] + Math.min(dp[i - 1][j][0], dp[i - 1][j][2]);  // 바로 아래
                dp[i][j][2] = arr[i][j] + Math.min(dp[i - 1][j - 1][0], dp[i - 1][j - 1][1]);  // 왼쪽 대각선 아래
            }

            // 마지막 열의 경우
            dp[i][m - 1][0] = (int)1e9;  // 오른쪽 대각선 아래로 이동 불가능
            dp[i][m - 1][1] = arr[i][m - 1] + dp[i - 1][m - 1][2];  // 바로 아래로 이동
            dp[i][m - 1][2] = arr[i][m - 1] + Math.min(dp[i - 1][m - 2][0], dp[i - 1][m - 2][1]);  // 왼쪽 대각선 아래로 이동
        }
    }
}