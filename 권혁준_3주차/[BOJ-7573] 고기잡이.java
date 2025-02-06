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
	static int[] x;
	static int[] y;
	static int N, K, M;
	static int ans = 0;

	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
		
		bwEnd();
	}
	
	static void ready() throws Exception{
		
		nextLine();
		N = nextInt();
		K = nextInt();
		M = nextInt();
		x = new int[M];
		y = new int[M];
		for(int i=0;i<M;i++) {
			nextLine();
			x[i] = nextInt();
			y[i] = nextInt();
		}
		K >>= 1;
		
	}
	
	static void solve() throws Exception{
		
		for(int i:x) for(int j:y) {
			// (i,j)에서 
			for(int c=1;c<=K/2;c++) {
				// c * (K-c)크기
				int[] res = new int[8];
				for(int a=0;a<M;a++) {
					int xx = x[a], yy = y[a];
					if(i <= xx && xx <= i+c && j <= yy && yy <= j+K-c) res[0]++;
					if(i <= xx && xx <= i+K-c && j <= yy && yy <= j+c) res[1]++;
					if(i-c <= xx && xx <= i && j <= yy && yy <= j+K-c) res[2]++;
					if(i-K+c <= xx && xx <= i && j <= yy && yy <= j+c) res[3]++;
					if(i <= xx && xx <= i+c && j-K+c <= yy && yy <= j) res[4]++;
					if(i <= xx && xx <= i+K-c && j-c <= yy && yy <= j) res[5]++;
					if(i-c <= xx && xx <= i && j-K+c <= yy && yy <= j) res[6]++;
					if(i-K+c <= xx && xx <= i && j-c <= yy && yy <= j) res[7]++;
				}
				for(int a=0;a<8;a++) ans = Math.max(ans, res[a]);
			}
		}
		bw.write(ans+"\n");
		
	}

	
}
