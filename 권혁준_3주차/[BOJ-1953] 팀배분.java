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
	static int N;
	static boolean[][] edge;
	static List<Integer> red = new ArrayList<>();
	static List<Integer> blue = new ArrayList<>();
	static int[] num;

	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
		
		bwEnd();
	}
	
	static void ready() throws Exception{
		
		nextLine();
		N = nextInt();
		edge = new boolean[N+1][N+1];
		num = new int[N+1];
		
		for(int i=1;i<=N;i++) {
			nextLine();
			int p = nextInt();
			while(p-- > 0) {
				int j = nextInt();
				edge[i][j] = true;
				edge[j][i] = true;
			}
		}
		
	}
	
	static void solve() throws Exception{
		
		for(int i=1;i<=N;i++) if(num[i] == 0) {
			num[i] = 2;
			dfs(i,2);
		}
		
		printResult(red);
		printResult(blue);

	}
	
	static void dfs(int n, int x) {
		if(x == 2) red.add(n);
		else blue.add(n);
		for(int i=1;i<=N;i++) {
			if(!edge[n][i]) continue;
			if(num[i] != 0) continue;
			num[i] = x^1;
			dfs(i, x^1);
		}
	}
	
	static void printResult(List<Integer> A) throws Exception{
		Collections.sort(A);
		bw.write(A.size() + "\n");
		for(int i:A) bw.write(i + " ");
		bw.write("\n");
	}
	
}
