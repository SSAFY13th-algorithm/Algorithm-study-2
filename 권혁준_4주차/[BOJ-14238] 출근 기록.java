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
	static boolean[][][][][][] D;
	static String[][][][][][] R;
	static String S;
	static int N, A, B, C;
	
	
	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		S = br.readLine();
		N = S.length();
		A = 0;
		B = 0;
		C = 0;
		for(int i=0;i<N;i++) {
			char g = S.charAt(i);
			if(g == 'A') A++;
			else if(g == 'B') B++;
			else C++;
		}
		
		D = new boolean[A+1][B+1][C+1][3][3][3];
		R = new String[A+1][B+1][C+1][3][3][3];
		
	}
	
	static void solve() throws Exception{
		
		if(A > 0) {
			D[1][0][0][0][0][0] = true;
			R[1][0][0][0][0][0] = "A";			
		}
		if(B > 0) {
			D[0][1][0][0][0][1] = true;
			R[0][1][0][0][0][1] = "B";			
		}
		if(C > 0) {			
			D[0][0][1][0][0][2] = true;
			R[0][0][1][0][0][2] = "C";
		}
		
		for(int i=2;i<=N;i++) {
			boolean[][][][][][] ND = new boolean[A+1][B+1][C+1][3][3][3];
			String[][][][][][] NR = new String[A+1][B+1][C+1][3][3][3];
			for(int a=0;a<=A;a++) for(int b=0;b<=B;b++) for(int c=0;c<=C;c++) {
				// A 놓기
				if(a > 0) {
					for(int x=0;x<3;x++) for(int y=0;y<3;y++) for(int z=0;z<3;z++) {
						if(D[a-1][b][c][z][x][y]) {
							ND[a][b][c][x][y][0] = true;
							NR[a][b][c][x][y][0] = R[a-1][b][c][z][x][y] + 'A';
						}
					}
				}
				if(b > 0) {
					for(int x=0;x<3;x++) for(int y=0;y<3;y++) {
						if(y == 1) continue;
						for(int z=0;z<3;z++) {
							if(D[a][b-1][c][z][x][y]) {
								ND[a][b][c][x][y][1] = true;
								NR[a][b][c][x][y][1] = R[a][b-1][c][z][x][y] + 'B';
							}
						}
					}
				}
				if(c > 0) {
					for(int x=0;x<3;x++) for(int y=0;y<3;y++) {
						if(x == 2 || y == 2) continue;
						for(int z=0;z<3;z++) {
							if(D[a][b][c-1][z][x][y]) {
								ND[a][b][c][x][y][2] = true;
								NR[a][b][c][x][y][2] = R[a][b][c-1][z][x][y] + 'C';
							}
						}
					}
				}
			}
			D = ND;
			R = NR;
		}
		
		for(int i=0;i<3;i++) for(int j=0;j<3;j++) for(int k=0;k<3;k++) {
			if(D[A][B][C][i][j][k]) {
				bw.write(R[A][B][C][i][j][k]);
				return;
			}
		}
		bw.write("-1");
		
		
	}
	
}
