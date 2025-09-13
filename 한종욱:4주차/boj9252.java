import java.io.*;

public class boj9252 {
    // 입출력을 위한 BufferedReader와 BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // 입력받은 두 문자열을 저장할 배열
    private static char[] input1;
    private static char[] input2;
    // LCS의 길이를 저장할 2차원 DP 배열
    private static int[][] dp;

    public static void main(String[] args) throws IOException {
        setting();    // 입력 받기
        dp();        // LCS 길이 계산

        // LCS의 길이 출력
        sb.append(dp[input1.length][input2.length]).append("\n");
        // LCS 문자열 출력
        sb.append(makeLCS());

        // 결과 출력 및 스트림 닫기
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    // 입력을 받아 초기 설정을 하는 메소드
    private static void setting() throws IOException {
        // 두 문자열을 입력받아 char 배열로 변환
        input1 = br.readLine().toCharArray();
        input2 = br.readLine().toCharArray();

        // DP 배열 초기화 (크기는 각 문자열 길이 + 1)
        dp = new int[input1.length + 1][input2.length + 1];
    }

    // LCS의 길이를 계산하는 메소드
    private static void dp() {
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                // 현재 비교하는 문자가 같은 경우
                if (input1[i-1] == input2[j-1])
                    dp[i][j] = dp[i-1][j-1] + 1;    // 대각선 위 값 + 1
                    // 다른 경우 위쪽과 왼쪽 값 중 큰 값 선택
                else
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
            }
        }
    }

    // 실제 LCS 문자열을 구성하는 메소드
    private static String makeLCS() {
        StringBuilder sb = new StringBuilder();
        // 배열의 가장 끝에서부터 시작
        int i = dp.length - 1;
        int j = dp[0].length - 1;

        // 배열의 처음까지 역추적
        while (i != 0 && j != 0) {
            // 두 문자가 같은 경우
            if (input1[i-1] == input2[j-1]) {
                sb.append(input1[i-1]);    // LCS에 문자 추가
                i--;
                j--;
            }
            // 다른 경우 위쪽과 왼쪽 중 큰 값으로 이동
            else {
                if (dp[i-1][j] > dp[i][j-1]) {
                    i--;
                } else {
                    j--;
                }
            }
        }
        // 역순으로 구한 문자열을 뒤집어서 반환
        return sb.reverse().toString();
    }
}