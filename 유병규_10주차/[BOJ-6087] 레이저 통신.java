import java.io.*;
import java.util.*;

public class Main {
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        int w = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());
        
        
        int[] start = null;
        int[] end = null;
        char[][] map = new char[h][w];
        for(int i=0; i<h; i++) {
        	String line = br.readLine();
        	for(int j=0; j<w; j++) {
        		map[i][j] = line.charAt(j);
        		if(map[i][j] == 'C') {
        			if(start == null) start = new int[] {i,j};
        			else end = new int[] {i,j};
        		}
        	}
        }
        
        int[][] d = {{-1,0},{1,0},{0,-1},{0,1}};
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1,o2) -> o1[3]-o2[3]);
        boolean[][][] visited = new boolean[h][w][4];
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
        	if(x==end[0] && y==end[1]) {
        		System.out.println(count);
        		return;
        	}
        	
        	int nx = x+d[dir][0];
        	int ny = y+d[dir][1];
        	
        	if(nx<0 || nx>=h || ny<0 || ny>=w || visited[nx][ny][dir] || map[nx][ny] == '*') continue;
        	//거울 설치 x
        	pq.offer(new int[] {nx, ny, dir, count});
        	//거울 설치 '/': 0->3, 1->2, 2->1, 3->0
        	int ndir = 3-dir;
        	pq.offer(new int[] {nx, ny, ndir, count+1});
        	//거울 설치 '\': 0->2, 1->3, 2->0, 3->1
        	if(dir==0) ndir = 2;
        	else if(dir==1) ndir = 3;
        	else if(dir==2) ndir = 0;
        	else ndir = 1;
        	pq.offer(new int[] {nx, ny, ndir, count+1});
        }
    }
}
