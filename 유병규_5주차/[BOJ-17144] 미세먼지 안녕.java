import java.io.*;
import java.util.*;

public class Main {
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static int[][] map;
	private static int[] machineTop, machineBottom;
	private static int r,c,t;
	
	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		t = Integer.parseInt(st.nextToken());
		
		map = new int[r][c];
		machineTop = new int[2];
		machineBottom = new int[2];
		for(int i=0; i<r; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<c; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == -1 && machineTop[0] == 0) {
					machineTop = new int[] {i,j};
					continue;
				}
				if(map[i][j] == -1) {
					machineBottom = new int[] {i,j};
				}
			}
		}
		
		int time = 0;
		while(time < t) {
			//1. 확산
			int[][] dust = new int[r][c];
			for(int i=0; i<r; i++) {
				for(int j=0; j<c; j++) {
					if(map[i][j] == 0 || map[i][j] == -1) continue;
					spreadDust(dust, i, j);
				}
			}
			
			for(int i=0; i<r; i++) {
				for(int j=0; j<c; j++) {
					map[i][j] += dust[i][j];
				}
			}
			//2. 공청 가동
			playMachine();
			
			time++;
		}
		
		//출력
		int total = 0;
		for(int i=0; i<r; i++) {
			for(int j=0; j<c; j++) {
				if(map[i][j] == 0 || map[i][j] == -1) continue;
				total += map[i][j];
			}
		}
		System.out.print(total);
	}

	private static void spreadDust(int[][] dust, int x, int y) {
		int[][] d = {{-1,0},{0,1},{1,0},{0,-1}};
		int count = 0;
		for(int i=0; i<d.length; i++) {
			int nx = x+d[i][0];
			int ny = y+d[i][1];
			
			if(nx<0 || nx>=r || ny<0 || ny>=c) continue;
			if(map[nx][ny] == -1) continue;
			
			dust[nx][ny] += map[x][y]/5;
			count++;
		}
		map[x][y] -= (map[x][y]/5)*count;
	}

	private static void playMachine() {
		playTop();
		playBottom();
	}
	
	private static void playTop() {
		int[][] d = {{-1,0},{0,1},{1,0},{0,-1}};
		int index = 0;
		int x = machineTop[0] + d[index][0];
		int y = machineTop[1] + d[index][1];
		while(index < 4) {
			int nx = x+d[index][0];
			int ny = y+d[index][1];
			
			if(nx<0 || nx>machineTop[0] || ny<0 || ny>=c) {
				index++;
				continue;
			}
			if(map[nx][ny] == -1) {
				map[x][y] = 0;
				break;
			}
			
			map[x][y] = map[nx][ny];
			x=nx;
			y=ny;
		}
	}
	
	private static void playBottom() {
		int[][] d = {{1,0},{0,1},{-1,0},{0,-1}};
		int index = 0;
		int x = machineBottom[0] + d[index][0];
		int y = machineBottom[1] + d[index][1];
		while(index < 4) {
			int nx = x+d[index][0];
			int ny = y+d[index][1];
			
			if(nx<machineBottom[0] || nx>=r || ny<0 || ny>=c) {
				index++;
				continue;
			}
			if(map[nx][ny] == -1) {
				map[x][y] = 0;
				break;
			}
			
			map[x][y] = map[nx][ny];
			x=nx;
			y=ny;
		}
	}
}
