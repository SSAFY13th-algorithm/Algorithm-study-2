import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        int[] men = new int[n + 1];
        int[] women = new int[m + 1];
        
        st = new StringTokenizer(br.readLine());
        for (int i=1; i<=n; i++) {
            men[i] = Integer.parseInt(st.nextToken());
        }
        
        st = new StringTokenizer(br.readLine());
        for (int i=1; i<=m; i++) {
            women[i] = Integer.parseInt(st.nextToken());
        }
        
        // 정렬
        Arrays.sort(men);
        Arrays.sort(women);
        
        // 작은 그룹과 큰 그룹 결정
        int[] small, large;
        int smallSize, largeSize;
        
        if (n <= m) {
            small = men;
            large = women;
            smallSize = n;
            largeSize = m;
        } else {
            small = women;
            large = men;
            smallSize = m;
            largeSize = n;
        }
        
        // DP 배열 초기화
        long[][] dp = new long[smallSize+1][largeSize+1];
        
        // 초기값 설정
        for (int i=0; i<=smallSize; i++) {
            for (int j=0; j<i; j++) {
                dp[i][j] = Long.MAX_VALUE;
            }
        }
        
        // DP 계산
        for (int i=1; i<=smallSize; i++) {
            for (int j=i; j<=largeSize; j++) {
                if (j == i) {
                    dp[i][j] = dp[i-1][j-1] + Math.abs(small[i] - large[j]);
                } else {
                    dp[i][j] = Math.min(
                        dp[i][j-1],  // j번째 사람을 선택하지 않는 경우
                        dp[i-1][j-1] + Math.abs(small[i] - large[j])  // j번째 사람을 선택하는 경우
                    );
                }
            }
        }
        
        System.out.println(dp[smallSize][largeSize]);
    }
}
