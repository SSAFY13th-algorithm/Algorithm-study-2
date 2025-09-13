import java.util.*;
import java.io.*;

class Main {
	
	// IO field
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;

	static void nextLine() throws Exception {st = new StringTokenizer(br.readLine());}
	static int nextInt() {return Integer.parseInt(st.nextToken());}
	static long nextLong() {return Long.parseLong(st.nextToken());}
	static void bwEnd() throws Exception {bw.flush();bw.close();}
	
	// Additional field

    static int[][] dp;
    static int N, M;
    static String A, B;

	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
        A = br.readLine();
        B = br.readLine();

        N = A.length();
        M = B.length();
        dp = new int[N+1][M+1];

	}
	
	static void solve() throws Exception{

        for(int i=1;i<=N;i++) for(int j=1;j<=M;j++){
            dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
            if(A.charAt(i-1) == B.charAt(j-1)){
                dp[i][j] = Math.max(dp[i][j], dp[i-1][j-1] + 1);
            }
        }

        List<Character> ans = new ArrayList<>();
        int i = N, j = M;
        while(i>0 && j>0){
            while(j>0 && dp[i][j] == dp[i][j-1]) j--;
            while(i>0 && dp[i][j] == dp[i-1][j]) i--;
            if(j==0) break;
            ans.add(B.charAt(j-1));
            j--;
            i--;
        }
        
        bw.write(ans.size() + "\n");
        for(int k=ans.size()-1;k>=0;k--) bw.write(ans.get(k));

	}
	
}
