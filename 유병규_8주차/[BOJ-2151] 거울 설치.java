import java.io.*;
import java.util.*;

public class Main {
	
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        int[] start = null, end = null;
        
        char[][] map = new char[n][n];
        for(int i=0; i<n; i++) {
        	String line = br.readLine();
        	for(int j=0; j<n; j++) {
        		map[i][j] = line.charAt(j);
        		if(map[i][j] == '#') {
        			if(start == null) start = new int[] {i,j};
        			else end = new int[] {i,j};
        		}
        	}
        }
        
        int[][] d = {{-1,0},{0,1},{1,0},{0,-1}};
        boolean[][][] visited = new boolean[n][n][4];
        
        //int [x좌표, y좌표, 방향, 거울개수]
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1,o2) -> o1[3] - o2[3]);
        
        //어느 방향이 시작인지 모르니까 일단 다 넣기
        for(int i=0; i<4; i++) {
        	pq.offer(new int[] {start[0], start[1], i, 0});
        }
        
        while(!pq.isEmpty()) {
        	int[] current = pq.poll();
        	
        	int x = current[0];
        	int y = current[1];
        	int dir = current[2];
        	int count = current[3];
        	
        	visited[x][y][dir] = true;
        	
        	if(x == end[0] && y == end[1]) {
        		System.out.println(count);
        		return;
        	}
        	
        	int nx = x + d[dir][0];
        	int ny = y + d[dir][1];
        	
        	if(nx<0 || nx>=n || ny<0 || ny>=n || visited[nx][ny][dir] || map[nx][ny] == '*') continue;
        	
        	if(map[nx][ny] == '!') {
        		// '/' : 0->1, 1->0, 2->3, 3->2
        		int nd = 0;
        		if(dir == 0) nd = 1;
        		else if(dir == 1) nd = 0;
        		else if(dir == 2) nd = 3;
        		else nd = 2;
        		pq.offer(new int[] {nx, ny, nd, count+1});
        		
        		// '\' : 0->3, 1->2, 2->1, 3->0
        		if(dir == 0) nd = 3;
        		else if(dir == 1) nd = 2;
        		else if(dir == 2) nd = 1;
        		else nd = 0;
        		pq.offer(new int[] {nx, ny, nd, count+1});
        	}
        	
        	pq.offer(new int[] {nx, ny, dir, count});
        }
    }
}
