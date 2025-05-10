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

	static int[] A;
	static int N;
	
	public static void main(String[] args) throws Exception {
			
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		N = Integer.parseInt(br.readLine());
		A = new int[N];
		nextLine();
		for(int i=0;i<N;i++) A[i] = nextInt();
		
	}
	
	static void solve() throws Exception{
		
		int cnt = 0;
		while(true) {
			int gto = 0, one = 0;
			for(int i=0;i<N;i++) {
				gto += A[i] > 1 ? 1 : 0;
				one += A[i] == 1 ? 1 : 0;
			}
			if(gto == 0) {
				cnt += one;
				break;
			}
			
			for(int i=0;i<N;i++) {
				if(A[i] % 2 != 0) cnt++;
				A[i] >>= 1;
			}
			cnt++;
		}
		bw.write(cnt + "\n");
		
	}
	
}
