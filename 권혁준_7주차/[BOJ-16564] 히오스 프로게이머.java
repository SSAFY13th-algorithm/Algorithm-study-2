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
	static long K;
	static long[] X;
	
	
	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{

		nextLine();
		N = nextInt();
		K = nextLong();
		X = new long[N];
		for(int i=0;i<N;i++) X[i] = Long.parseLong(br.readLine()); 
		
	}
	
	static void solve() throws Exception{
		
		Arrays.sort(X);
		for(int i=1;i<N;i++) {
			long diff = X[i] - X[i-1], cnt = i;
			if(K >= diff*cnt) {
				K -= diff*cnt;
				continue;
			}
			else {
				long can = K / cnt;
				bw.write((X[i-1] + can) + "\n");
				return;
			}
		}
		
		bw.write((X[N-1] + K/N) + "\n");
		
	}
	
}
