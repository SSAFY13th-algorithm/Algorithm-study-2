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

	static int N, D, K, C;
	static int[] cnt, A;
	
	public static void main(String[] args) throws Exception {
			
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		nextLine();
		N = nextInt();
		D = nextInt();
		K = nextInt();
		C = nextInt();
		cnt = new int[D+1];
		cnt[C]++;
		
		A = new int[N];
		for(int i=0;i<N;i++) A[i] = Integer.parseInt(br.readLine());
		
	}
	
	static void solve() throws Exception{
		
		int res = 1;
		for(int e=0;e<K;e++) {
			if(cnt[A[e]] == 0) res++;
			cnt[A[e]]++;
		}
		
		int ans = res, s = 0;
		for(int e=K;e<N+K;e++) {
			if(cnt[A[e%N]] == 0) res++;
			cnt[A[e%N]]++;
			if(cnt[A[s]] == 1) res--;
			cnt[A[s++]]--;
			ans = Math.max(ans, res);
		}
		
		bw.write(ans + "\n");
		
	}
	
}
