import java.io.*;

public class boj2631 {
    // 입출력을 위한 BufferedReader, BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int[] numbers;  // 입력 받은 수열을 저장하는 배열
    private static int[] dp;       // 동적 프로그래밍을 위한 배열
    private static int n, max;     // n: 수열의 길이, max: LIS의 최대 길이

    public static void main(String[] args) throws IOException {
        init();  // 초기 데이터 설정
        dp();    // 동적 프로그래밍으로 LIS 계산

        // 결과 출력: 제거해야 할 최소 원소 개수 = 전체 원소 개수 - LIS 길이
        bw.write(n - max + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // 초기 데이터를 설정하는 메소드
    private static void init() throws IOException {
        n = Integer.parseInt(br.readLine());  // 수열의 길이 입력
        max = 0;  // 최대 LIS 길이 초기화

        numbers = new int[n];  // 수열 배열 초기화
        dp = new int[n];       // DP 배열 초기화

        // 수열 입력 받기
        for (int i = 0; i < n; i++) {
            numbers[i] = Integer.parseInt(br.readLine());
            dp[i] = 1;  // 모든 위치에서 LIS의 최소 길이는 1
        }
    }

    // 동적 프로그래밍으로 LIS(최장 증가 부분 수열)를 계산하는 메소드
    private static void dp() {
        for (int i = 1; i < n; i++) {
            for (int j = i - 1; j > -1; j--) {
                // 현재 위치(i)의 값이 이전 위치(j)의 값보다 크면
                if (numbers[i] > numbers[j]) {
                    // i 위치까지의 LIS 길이 업데이트
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                    // 전체 최대 LIS 길이 업데이트
                    max = Math.max(max, dp[i]);
                }
            }
        }
    }
}