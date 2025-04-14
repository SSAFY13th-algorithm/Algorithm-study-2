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
    static int K;
    static int[] C;
    static int ans = 0, mx = 0;
    
    static int dfs(int n){
        if(n >= (1<<K)) return mx-C[n];
        int left = dfs(n<<1), right = dfs((n<<1) + 1);
        ans += Math.abs(left-right);
        return Math.min(left, right);
    }

	public static void main(String[] args) throws Exception {

		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
        K = Integer.parseInt(br.readLine());
        C = new int[1<<(K+1)];
        nextLine();
        for(int i=2;i<=(1<<(K+1))-1;i++) {
            C[i] = nextInt();
            ans += C[i];
            C[i] += C[i>>1];
            mx = Math.max(mx, C[i]);
        }

	}
	
	static void solve() throws Exception{

        dfs(1);
        bw.write(ans+"\n");

	}
	
}
