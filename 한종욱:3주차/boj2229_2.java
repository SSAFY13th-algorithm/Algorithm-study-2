import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class boj2229_2 {
    // 입출력을 위한 BufferedReader, BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    private static int n;  // 학생 수
    private static int[] students;  // 학생들의 키를 저장하는 배열
    private static int[] dp;  // dp를 위한 배열

    public static void main(String[] args) throws IOException{
        setting();  // 초기 데이터 설정
        dp();  // dp로 최대 점수 계산

        // 결과 출력
        sb.append(dp[n]);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    // 초기 데이터를 설정하는 메소드
    private static void setting() throws IOException {
        n = Integer.parseInt(br.readLine());
        students = new int[n + 1];
        dp = new int[n + 1];

        // 학생들의 키 입력 받기
        String[] input = br.readLine().split(" ");
        for (int i = 1; i <= n; i++) {
            students[i] = Integer.parseInt(input[i - 1]);
        }
    }

    // dp로 최대 점수를 계산하는 메소드
    private static void dp() {
        for (int i = 1; i <= n; i++) {
            // 각 위치에서 가능한 모든 구간을 고려
            for (int j = 1; j <= i; j++) {
                // 현재 구간의 점수와 이전까지의 최대 점수를 합한 값 중 최대값 선택
                dp[i] = Math.max(dp[i - j] + degree(i, j), dp[i]);
            }
        }
    }

    // 특정 구간의 최대 키와 최소 키의 차이를 계산하는 메소드
    private static int degree(int i, int j) {
        int min = 10004;  // 최소값 초기화 (문제의 조건에 따른 최대값보다 큰 수)
        int max = -1;     // 최대값 초기화
        // i-j+1부터 i까지의 구간에서 최대값과 최소값 찾기
        for (int k = i-j+1; k <= i; k++) {
            min = Math.min(min, students[k]);
            max = Math.max(max, students[k]);
        }

        return max - min;  // 최대값과 최소값의 차이 반환
    }
}