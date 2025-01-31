import java.util.*;
import java.io.*;

/*
 * X[0] = 0
 * X[1] = 01
 * X[2] = 0110
 * X[3] = 01101001
 * .
 * .
 * .
 */

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
	
	// X[n]의 k번째 글자를 구하는 함수
	static int solve(int n, long k) {
		if(n == 0) return 0;
		long half = 1L<<(n-1);
		if(k <= half) return solve(n-1,k);
		return solve(n-1,k-half) ^ 1;
	}

	public static void main(String[] args) throws Exception {

		nextLine();
		long k = nextLong();
		
		for(int i=0;i<64;i++) {
			if(k <= (1L<<i)) {
				bw.write(solve(i, k)+"\n");
				break;
			}
		}
		
		bwEnd();
	}

}
