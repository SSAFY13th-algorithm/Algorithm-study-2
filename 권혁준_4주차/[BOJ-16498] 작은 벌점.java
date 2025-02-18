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
	static int NA, NB, NC;
	static int[] A, B, C;
	
	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{

		nextLine();
		NA = nextInt();
		NB = nextInt();
		NC = nextInt();
		
		A = new int[NA];
		B = new int[NB];
		C = new int[NC];
		
		nextLine();
		for(int i=0;i<NA;i++) A[i] = nextInt();
		nextLine();
		for(int i=0;i<NB;i++) B[i] = nextInt();
		nextLine();
		for(int i=0;i<NC;i++) C[i] = nextInt();
		
	}
	
	static void solve() throws Exception{
		
		
		Arrays.sort(A);
		Arrays.sort(B);
		Arrays.sort(C);
		
		int ans = Integer.MAX_VALUE;
		for(int i:A) {
			int s = 0, e = 0;
			while(s<NB && e<NC) {
				ans = Math.min(ans, max(i,B[s],C[e]) - min(i,B[s],C[e]));
				if(B[s] < C[e]) s++;
				else e++;
			}
		}
		bw.write(ans+"\n");
		
	}

	static int max(int a, int b, int c) {
		return Math.max(a, Math.max(b, c));
	}
	
	static int min(int a, int b, int c) {
		return Math.min(a, Math.min(b, c));
	}
	
}
