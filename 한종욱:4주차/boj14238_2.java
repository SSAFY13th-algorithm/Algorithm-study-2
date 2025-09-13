import java.io.*;
public class boj14238_2 {
    // 입출력을 위한 BufferedReader와 BufferedWriter
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    // 결과 문자열을 만들기 위한 StringBuilder
    private static final StringBuilder sb = new StringBuilder();
    // dp[a][b][c][prev1][prev2]: A,B,C 각각 a,b,c개 남았고 이전 문자가 prev1, 그 이전이 prev2일 때 가능 여부
    private static int[][][][][] dp;
    // A,B,C 각각의 개수를 저장하는 배열
    private static int[] countWorkDay;
    // 전체 문자열의 길이
    private static int n = 0;

    public static void main(String[] args) throws IOException {
        // 초기 입력 처리
        init();
        // dp 함수 호출하여 결과 확인
        if (dp(countWorkDay[0], countWorkDay[1], countWorkDay[2], 0, 0)) {
            bw.write(sb.toString());
        } else {
            bw.write("-1");
        }
        bw.flush();
        bw.close();
        br.close();
    }

    // 초기 입력을 처리하는 메소드
    private static void init() throws IOException {
        String input = br.readLine();
        n = input.length();
        // A,B,C 개수 카운트
        countWorkDay = new int[3];
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == 'A') countWorkDay[0]++;
            else if (c == 'B') countWorkDay[1]++;
            else countWorkDay[2]++;
        }
        // DP 배열 초기화 (-1로 초기화하여 방문하지 않은 상태 표시)
        dp = new int[countWorkDay[0]+1][countWorkDay[1]+1][countWorkDay[2]+1][3][3];
        for (int i = 0; i <= countWorkDay[0]; i++)
            for (int j = 0; j <= countWorkDay[1]; j++)
                for (int k = 0; k <= countWorkDay[2]; k++)
                    for (int l = 0; l < 3; l++)
                        for (int m = 0; m < 3; m++)
                            dp[i][j][k][l][m] = -1;
    }

    // DP를 이용한 문자열 생성 메소드
    private static boolean dp(int a, int b, int c, int prev1, int prev2) {
        // 문자열이 완성된 경우
        if (sb.length() == n) return true;
        // 이미 계산된 경우 결과 반환
        if (dp[a][b][c][prev1][prev2] != -1)
            return dp[a][b][c][prev1][prev2] == 1;

        // A를 추가할 수 있는 경우 (제약 없음)
        if (a > 0) {
            sb.append('A');
            if (dp(a-1, b, c, 0, prev1)) {
                dp[a][b][c][prev1][prev2] = 1;
                return true;
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        // B를 추가할 수 있는 경우 (이전 문자가 B가 아니어야 함)
        if (b > 0 && prev1 != 1) {
            sb.append('B');
            if (dp(a, b-1, c, 1, prev1)) {
                dp[a][b][c][prev1][prev2] = 1;
                return true;
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        // C를 추가할 수 있는 경우 (이전 두 문자가 C가 아니어야 함)
        if (c > 0 && prev1 != 2 && prev2 != 2) {
            sb.append('C');
            if (dp(a, b, c-1, 2, prev1)) {
                dp[a][b][c][prev1][prev2] = 1;
                return true;
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        // 현재 상태에서 불가능한 경우
        dp[a][b][c][prev1][prev2] = 0;
        return false;
    }
}