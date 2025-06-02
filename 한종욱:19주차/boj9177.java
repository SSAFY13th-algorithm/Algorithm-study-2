import java.io.*;
import java.util.StringTokenizer;

public class boj9177 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // dp[i][j]: word1의 첫 i개 문자와 word2의 첫 j개 문자를 섞어서
    //          word3의 첫 (i+j)개 문자를 만들 수 있는지 여부
    private static boolean[][] dp;

    // 입력으로 받는 세 개의 단어를 문자 배열로 저장
    private static char[] word1, word2, word3;
    private static int n; // 사용되지 않는 변수 (제거 가능)

    public static void main(String[] args) throws IOException {
        int t = Integer.parseInt(br.readLine()); // 테스트 케이스 개수

        // 각 테스트 케이스에 대해 처리
        for (int i = 1; i <= t; i++) {
            init(); // 데이터 초기화 및 DP 수행

            // 결과 출력: word1 전체와 word2 전체를 섞어서 word3 전체를 만들 수 있는지 확인
            bw.write("Data set " + i + ": " + (dp[word1.length][word2.length] ? "yes" : "no") + "\n");
        }

        bw.flush();
        bw.close();
        br.close();
    }

    // 입력 데이터 초기화 및 동적 계획법 수행
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        word1 = st.nextToken().toCharArray(); // 첫 번째 단어
        word2 = st.nextToken().toCharArray(); // 두 번째 단어
        word3 = st.nextToken().toCharArray(); // 목표 단어 (섞은 결과)

        // DP 테이블 초기화
        // dp[i][j]는 word1의 첫 i개 문자와 word2의 첫 j개 문자로 word3의 첫 (i+j)개 문자를 만들 수 있는지
        dp = new boolean[word1.length + 1][word2.length + 1];

        // 기저 사례: 빈 문자열들로 빈 문자열을 만드는 것은 항상 가능
        dp[0][0] = true;

        // 첫 번째 행 초기화: word1만 사용해서 word3의 앞부분을 만들 수 있는지
        for (int i = 1; i <= word1.length; i++) {
            // word1의 첫 i개 문자로 word3의 첫 i개 문자를 만들 수 있는지
            // 이전 상태가 가능하고, 현재 문자가 일치해야 함
            dp[i][0] = dp[i - 1][0] && word1[i - 1] == word3[i - 1];
        }

        // 첫 번째 열 초기화: word2만 사용해서 word3의 앞부분을 만들 수 있는지
        for (int j = 1; j <= word2.length; j++) {
            // word2의 첫 j개 문자로 word3의 첫 j개 문자를 만들 수 있는지
            // 이전 상태가 가능하고, 현재 문자가 일치해야 함
            dp[0][j] = dp[0][j - 1] && word2[j - 1] == word3[j - 1];
        }

        // DP 테이블 채우기
        for (int i = 1; i <= word1.length; i++) {
            for (int j = 1; j <= word2.length; j++) {
                // dp[i][j]를 구하는 두 가지 방법:

                // 방법 1: word1의 i번째 문자를 사용하는 경우
                // - 이전 상태 dp[i-1][j]가 가능해야 함
                // - word1의 (i-1)번째 문자가 word3의 (i+j-1)번째 문자와 일치해야 함
                if (dp[i - 1][j] && word1[i - 1] == word3[i + j - 1]) {
                    dp[i][j] = true;
                }

                // 방법 2: word2의 j번째 문자를 사용하는 경우
                // - 이전 상태 dp[i][j-1]가 가능해야 함
                // - word2의 (j-1)번째 문자가 word3의 (i+j-1)번째 문자와 일치해야 함
                if (dp[i][j - 1] && word2[j - 1] == word3[i + j - 1]) {
                    dp[i][j] = true;
                }
            }
        }
    }
}