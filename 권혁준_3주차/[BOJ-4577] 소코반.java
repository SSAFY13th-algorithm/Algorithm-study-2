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
	static int N, M;
	static char[][] arr;
	static int[] dx = {1,0,-1,0};
	static int[] dy = {0,1,0,-1};
	static String command;
	static int x, y;

	public static void main(String[] args) throws Exception {
		
		ready();
		//solve();
		
		bwEnd();
	}
	
	static void ready() throws Exception{
		
		int caseNum = 0;
		while(true) {
			
			nextLine();
			N = nextInt();
			M = nextInt();
			if(N == 0) break;
			
			arr = new char[N][M];
			for(int i=0;i<N;i++) arr[i] = br.readLine().toCharArray();
			command = br.readLine();
			x = 0;
			y = 0;
			
			solve(++caseNum);
			
			
			
		}
		
		
	}
	
	static void solve(int caseNum) throws Exception{
		
		for(int i=0;i<N;i++) for(int j=0;j<M;j++) if(arr[i][j] == 'w' || arr[i][j] == 'W') {
			x = i;
			y = j;
			break;
		}
		
		String result = "incomplete\n";
		for(char direction : command.toCharArray()) {
			if(direction == 'U') move(2);
			if(direction == 'D') move(0);
			if(direction == 'L') move(3);
			if(direction == 'R') move(1);
			if(complete()) {
				result = "complete\n";
				break;
			}
		}
		
		bw.write("Game " + caseNum + ": " + result);
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) bw.write(arr[i][j]);
			bw.write("\n");
		}

	}
	
	static void move(int dir) {
		int nx = x+dx[dir], ny = y+dy[dir];
		
		char nextElement = arr[nx][ny];
		if(nextElement == '#') return;
		
		if(nextElement == '.' || nextElement == '+') {
			if(nextElement == '.') arr[nx][ny] = 'w';
			else arr[nx][ny] = 'W';
			
			if(arr[x][y] == 'W') arr[x][y] = '+';
			else arr[x][y] = '.';
			
			x = nx;
			y = ny;
			
			return;
		}
		
		if(nextElement == 'b' || nextElement == 'B') {
			
			int nnx = nx+dx[dir], nny = ny+dy[dir];
			char overElement = arr[nnx][nny];
			if(overElement == '#' || overElement == 'b' || overElement == 'B') return;
			
			if(overElement == '.') arr[nnx][nny] = 'b';
			else arr[nnx][nny] = 'B';
			
			if(nextElement == 'b') arr[nx][ny] = 'w';
			else arr[nx][ny] = 'W';
			
			if(arr[x][y] == 'w') arr[x][y] = '.';
			else arr[x][y] = '+';
			
			x = nx;
			y = ny;
			
		}
		
	}
	
	static boolean complete() {
		for(int i=0;i<N;i++) for(int j=0;j<M;j++) if(arr[i][j] == 'b') return false;
		return true;
	}

}
