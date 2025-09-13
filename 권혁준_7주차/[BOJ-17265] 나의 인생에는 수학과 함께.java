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
	static char[][] A;
	static int low = Integer.MAX_VALUE;
	static int high = Integer.MIN_VALUE;
	
	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		N = Integer.parseInt(br.readLine());
		A = new char[N][N];
		for(int i=0;i<N;i++) {
			String temp = br.readLine();
			for(int j=0;j<2*N;j+=2)A[i][j>>1] = temp.charAt(j);
		}
		
	}
	
	static void solve() throws Exception{
		
		bck(0,0,A[0][0]-'0');
		bw.write(high + " " + low);
		
	}
	
	static void bck(int x, int y, int res) {
		
		if(x == N-1 && y == N-1) {
			low = Math.min(low, res);
			high = Math.max(high, res);
			return;
		}
		
		if(x < N-1) {
			if(x < N-2) bck(x+2,y,cal(res,A[x+1][y],A[x+2][y]-'0'));
			if(y < N-1) bck(x+1,y+1,cal(res,A[x+1][y],A[x+1][y+1]-'0'));
		}
		
		if(y < N-1) {
			if(y < N-2) bck(x,y+2,cal(res,A[x][y+1],A[x][y+2]-'0'));
			if(x < N-1) bck(x+1,y+1,cal(res,A[x][y+1],A[x+1][y+1]-'0'));
		}
	}
	
	static int cal(int a, char op, int b) {
		if(op == '+') return a+b;
		if(op == '-') return a-b;
		return a*b;
	}
	
}
