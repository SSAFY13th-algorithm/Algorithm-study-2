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
	static boolean[] hasParent;
	static int[] min, max;
	static int N;
	static int[] left, right;
	static int cnt = 0, ans = 0, dep = 0;
	
	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{

		min = new int[10001];
		max = new int[10001];
		Arrays.fill(min, 100000);
		Arrays.fill(max, -1);
		
		N = Integer.parseInt(br.readLine());
		hasParent = new boolean[N+1];
		left = new int[N+1];
		right = new int[N+1];
		for(int i=1;i<=N;i++) {
			nextLine();
			int n = nextInt(), l = nextInt(), r = nextInt();
			left[n] = l;
			right[n] = r;
			if(l != -1) hasParent[l] = true;
			if(r != -1) hasParent[r] = true;
		}
		
	}
	
	static void solve() throws Exception{

		for(int i=1;i<=N;i++) if(!hasParent[i]) {
			dfs(i,1);
			break;
		}
		for(int i=1;i<=10000;i++) {
			int diff = max[i] - min[i] + 1;
			if(diff > ans) {
				ans = diff;
				dep = i;
			}
		}
		bw.write(dep+" "+ans);
		
	}
	
	static void dfs(int n, int d) {
		if(left[n] != -1) dfs(left[n], d+1);
		cnt++;
		min[d] = Math.min(cnt, min[d]);
		max[d] = Math.max(cnt, max[d]);
		if(right[n] != -1) dfs(right[n], d+1);
	}
	
}
