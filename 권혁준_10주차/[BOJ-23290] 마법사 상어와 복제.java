import java.util.*;
import java.io.*;

class Fish{
	int dir, smell;
	Fish(int dir){
		this.dir = dir;
		this.smell = -1;
	}
	
	Fish(int dir, int smell){
		this.dir = dir;
		this.smell = smell;
	}
}

class Main {
	
	// IO field
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st = new StringTokenizer("");

	static void nextLine() throws Exception {st = new StringTokenizer(br.readLine());}
	static String nextToken() throws Exception {
		while(!st.hasMoreTokens()) nextLine();
		return st.nextToken();
	}
	static int nextInt() throws Exception { return Integer.parseInt(nextToken()); }
	static long nextLong() throws Exception { return Long.parseLong(nextToken()); }
	static double nextDouble() throws Exception { return Double.parseDouble(nextToken()); }
	static void bwEnd() throws Exception {bw.flush();bw.close();}
	
	// Additional field
	
	static int S;
	
	static List<Fish>[][] map;
	static int[] shark;
	
	static int[] prior = {2,0,6,4};
	static int[] dx = {0,-1,-1,-1,0,1,1,1};
	static int[] dy = {-1,-1,0,1,1,1,0,-1};
	
	public static void main(String[] args) throws Exception {
			
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		map = new List[4][4];
		for(int i=0;i<4;i++) for(int j=0;j<4;j++) map[i][j] = new ArrayList<>();
		
		int fishes = nextInt();
		S = nextInt();
		for(int i=0;i<fishes;i++) {
			int x = nextInt()-1, y = nextInt()-1, d = nextInt()-1;
			map[x][y].add(new Fish(d));
		}
		
		int x = nextInt()-1, y = nextInt()-1;
		shark = new int[] {x,y};
		
	}
	
	static void solve() throws Exception{

		while(S-- > 0) magic();
		
		int ans = 0;
		for(int i=0;i<4;i++) for(int j=0;j<4;j++) for(Fish fish : map[i][j]) if(fish.smell == -1) ans++;
		bw.write(ans + "\n");
		
	}
	
	static void magic() throws Exception {
		
		// STEP 1
		
		boolean[][] existSmell = new boolean[4][4];
		
		List<Fish>[][] copy = new List[4][4];
		for(int i=0;i<4;i++) for(int j=0;j<4;j++) copy[i][j] = new ArrayList<>();
		for(int i=0;i<4;i++) for(int j=0;j<4;j++) for(Fish fish : map[i][j]) {
			if(fish.smell == -1) copy[i][j].add(new Fish(fish.dir));
			else existSmell[i][j] = true;
		}
		
		// STEP 2
		
		List<Fish>[][] newMap = new List[4][4];
		for(int i=0;i<4;i++) for(int j=0;j<4;j++) newMap[i][j] = new ArrayList<>();
		
		for(int i=0;i<4;i++) for(int j=0;j<4;j++) for(Fish fish : map[i][j]) if(fish.smell == -1) {
			boolean canMove = false;
			for(int dir=fish.dir;dir>fish.dir-8;dir--) {
				int d = (dir + 16) % 8;
				int x = i+dx[d], y = j+dy[d];
				if(x<0 || x>=4 || y<0 || y>=4) continue;
				if((x==shark[0] && y==shark[1]) || existSmell[x][y]) continue;
				canMove = true;
				newMap[x][y].add(new Fish(d));
				break;
			}
			if(!canMove) {
				newMap[i][j].add(new Fish(fish.dir));
			}
		}
		else {
			newMap[i][j].add(new Fish(fish.dir, fish.smell));
		}
		
		// STEP 3
		
		int maxCount = -1;
		int[][][] C = new int[4][4][4];
		boolean[][] passed = new boolean[4][4];
		
		for(int i=0;i<4;i++) for(int j=0;j<4;j++) Arrays.fill(C[i][j], -1);
		for(int a=0;a<4;a++) {
			int x = shark[0] + dx[prior[a]];
			int y = shark[1] + dy[prior[a]];
			if(x<0 || x>=4 || y<0 || y>=4) continue;
			
			int countFirst = 0;
			for(Fish fish : newMap[x][y]) if(fish.smell == -1) countFirst++;
			
			passed[x][y] = true;
			for(int b=0;b<4;b++) {
				int xx = x+dx[prior[b]];
				int yy = y+dy[prior[b]];
				if(xx<0 || xx>=4 || yy<0 || yy>=4 || passed[xx][yy]) continue;
				
				int countSecond = 0;
				for(Fish fish : newMap[xx][yy]) if(fish.smell == -1) countSecond++;
				
				passed[xx][yy] = true;
				for(int c=0;c<4;c++) {
					int xxx = xx+dx[prior[c]];
					int yyy = yy+dy[prior[c]];
					if(xxx<0 || xxx>=4 || yyy<0 || yyy>=4 || passed[xxx][yyy]) continue;
					
					int countThird = 0;
					for(Fish fish : newMap[xxx][yyy]) if(fish.smell == -1) countThird++;
					
					maxCount = Math.max(maxCount, countFirst + countSecond + countThird);
					C[a][b][c] = countFirst + countSecond + countThird;
				}
				passed[xx][yy] = false;
			}
			passed[x][y] = false;
			
		}
		
		
		
		int first = 0, second = 0, third = 0;
		while(first < 4) {
			
			if(maxCount == C[first][second][third]) break;
			
			third++;
			if(third == 4) {
				third = 0;
				second++;
				if(second == 4) {
					second = 0;
					first++;
				}
			}
			
		}
		
		shark[0] += dx[prior[first]];
		shark[1] += dy[prior[first]];
		for(Fish fish : newMap[shark[0]][shark[1]]) if(fish.smell == -1) fish.smell = 2;
		
		shark[0] += dx[prior[second]];
		shark[1] += dy[prior[second]];
		for(Fish fish : newMap[shark[0]][shark[1]]) if(fish.smell == -1) fish.smell = 2;
		
		shark[0] += dx[prior[third]];
		shark[1] += dy[prior[third]];
		for(Fish fish : newMap[shark[0]][shark[1]]) if(fish.smell == -1) fish.smell = 2;
		
		// STEP 4, 5
		
		for(int i=0;i<4;i++) for(int j=0;j<4;j++) for(Fish fish : newMap[i][j]) if(fish.smell != 0) copy[i][j].add(new Fish(fish.dir, fish.smell));
		
		map = copy;
		for(int i=0;i<4;i++) for(int j=0;j<4;j++) for(Fish fish : map[i][j]) if(fish.smell != -1) fish.smell--;
		
	}
	
}
