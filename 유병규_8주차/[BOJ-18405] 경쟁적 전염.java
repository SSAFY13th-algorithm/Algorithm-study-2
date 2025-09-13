import java.io.*;
import java.util.*;

public class Main {
	
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        int[][] map = new int[n+1][n+1];
        Queue<int[]>[] virus = new LinkedList[k+1];
        for(int i=1; i<=k; i++) {
        	virus[i] = new LinkedList<int[]>();
        }
        
        for(int i=1; i<=n; i++) {
        	st = new StringTokenizer(br.readLine());
        	for(int j=1; j<=n; j++) {
        		map[i][j] = Integer.parseInt(st.nextToken());
        		if(map[i][j] == 0) continue;
        		virus[map[i][j]].offer(new int[] {i,j});
        	}
        }
        
        st = new StringTokenizer(br.readLine());
        int s = Integer.parseInt(st.nextToken());
        int[] target = {Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};
        
        if(map[target[0]][target[1]] != 0) {
        	System.out.println(map[target[0]][target[1]]);
        	return;
        }
        
        int[][] d = {{-1,0},{0,1},{1,0},{0,-1}};
        int time = 0;
        while(time < s) {
        	for(int i=1; i<=k; i++) {
        		int size = virus[i].size();
        		for(int j=0; j<size; j++) {
        			int[] point = virus[i].poll();
        			for(int index=0; index<d.length; index++) {
        				int nx = point[0] + d[index][0];
        				int ny = point[1] + d[index][1];
        				if(nx<=0 || nx>n || ny<=0 || ny>n || map[nx][ny] != 0) continue;
        				if(nx == target[0] && ny == target[1]) {
        					System.out.println(i);
        					return;
        				}
        				map[nx][ny] = i;
        				virus[i].offer(new int[] {nx,ny});
        			}
        		}
        	}
        	time++;
        }
        
        System.out.println(0);
    }
}
