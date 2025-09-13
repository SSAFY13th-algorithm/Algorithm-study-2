import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class boj2229_1 {
    // 입출력을 위한 BufferedReader, BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    private static int n;  // 학생 수
    private static int[] students;  // 학생들의 키를 저장하는 배열
    private static int[] dp;  // dp를 위한 배열
    private static int[][] maxValues;  // 구간별 최대값을 저장하는 2차원 배열
    private static int[][] minValues;  // 구간별 최소값을 저장하는 2차원 배열

    public static void main(String[] args) throws IOException {
        setting();  // 초기 데이터 설정
        preprocess();  // 구간별 최대/최소값 미리 계산
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
        maxValues = new int[n + 1][n + 1];
        minValues = new int[n + 1][n + 1];

        // 학생들의 키 입력 받기
        String[] input = br.readLine().split(" ");
        for (int i = 1; i <= n; i++) {
            students[i] = Integer.parseInt(input[i - 1]);
        }
    }

    // 구간별 최대값과 최소값을 미리 계산하는 메소드
    private static void preprocess() {
        for (int i = 1; i <= n; i++) {
            // 구간의 길이가 1인 경우 초기화
            maxValues[i][i] = students[i];
            minValues[i][i] = students[i];
            // 구간의 길이를 늘려가며 최대/최소값 계산
            for (int j = i + 1; j <= n; j++) {
                maxValues[i][j] = Math.max(maxValues[i][j-1], students[j]);
                minValues[i][j] = Math.min(minValues[i][j-1], students[j]);
            }
        }
    }

    // dp로 최대 점수를 계산하는 메소드
    private static void dp() {
        for (int i = 1; i <= n; i++) {
            // 각 위치에서 가능한 모든 구간을 고려
            for (int j = 1; j <= i; j++) {
                // 현재 구간의 점수와 이전까지의 최대 점수를 합한 값 중 최대값 선택
                dp[i] = Math.max(dp[i - j] + (maxValues[i-j+1][i] - minValues[i-j+1][i]), dp[i]);
            }
        }
    }
}