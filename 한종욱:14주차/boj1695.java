import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class boj1695 {
    // 입출력을 위한 객체 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // dp[i][j]: i부터 j까지의 부분 수열을 팰린드롬으로 만들기 위해 추가해야 하는 최소 숫자 개수
    private static int[][] dp;

    // 입력받은 수열을 저장하는 배열
    private static int[] arr;

    // 수열의 길이
    private static int n;

    public static void main(String[] args) throws IOException {
        // 입력 처리 및 초기화
        init();

        // 동적 프로그래밍을 통한 문제 해결
        DP();

        // 결과 출력: dp[n][1]은 전체 수열(1부터 n까지)을 팰린드롬으로 만들기 위해 필요한 최소 추가 숫자 개수
        bw.write(String.valueOf(dp[n][1]));
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력을 처리하고 초기화하는 메소드
     */
    private static void init() throws IOException {
        // 수열의 길이 입력
        n = Integer.parseInt(br.readLine());

        // 배열 초기화 (1-indexed 사용)
        arr = new int[n + 1];
        dp = new int[n + 1][n + 1];

        // 수열 입력 받기
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
    }

    /**
     * 동적 프로그래밍을 통해 팰린드롬 만들기 위한 최소 추가 숫자 개수 계산
     * dp[i][j]: i부터 j까지의 부분 수열을 팰린드롬으로 만들기 위해 추가해야 하는 최소 숫자 개수
     */
    private static void DP() {
        // i는 수열의 오른쪽 끝 인덱스
        for (int i = 2; i <= n; i++) {
            // j는 수열의 왼쪽 끝 인덱스 (i보다 작은 값부터 1까지 역순으로)
            for (int j = i - 1; j >= 1; j--) {
                // 양 끝의 숫자가 같은 경우
                if (arr[i] == arr[j]) {
                    // 양 끝을 제외한 부분 수열에 대한 최소 추가 개수와 동일
                    dp[i][j] = dp[i - 1][j + 1];
                }
                // 양 끝의 숫자가 다른 경우
                else {
                    // 두 가지 경우 중 최소값 선택:
                    // 1. 왼쪽에 숫자 추가: dp[i-1][j] + 1
                    // 2. 오른쪽에 숫자 추가: dp[i][j+1] + 1
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i][j + 1]) + 1;
                }
            }
        }
    }
}