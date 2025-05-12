import java.io.*;
import java.util.*;

public class Main {
	private static int n,m;
	private static int[] red,blue;
	private static char[][] map;
	private static int[][] d = {{-1,0},{1,0},{0,-1},{0,1}};
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        
        map = new char[n][m];
        for(int i=0; i<n; i++) {
        	String line = br.readLine();
        	for(int j=0; j<m; j++) {
        		map[i][j] = line.charAt(j);
        		if(map[i][j] == 'R') {
        			red = new int[] {i,j};
        			map[i][j] = '.';
        		}
        		if(map[i][j] == 'B') {
        			blue = new int[] {i,j};
        			map[i][j] = '.';
        		}
        	}
        }
        
        int count = bfs();
        System.out.println(count);
    }
    
    private static int bfs() {
    	Queue<int[]> q = new LinkedList<>();
    	boolean[][][][] visited = new boolean[n][m][n][m];
    	
    	q.offer(new int[] {red[0], red[1], blue[0], blue[1], 0, 0, 4});
    	visited[red[0]][red[1]][blue[0]][blue[1]] = true;
    	
    	while(!q.isEmpty()) {
    		int[] info = q.poll();
    		int rx = info[0];
    		int ry = info[1];
    		int bx = info[2];
    		int by = info[3];
    		int count = info[4];
    		int dStart = info[5];
    		int dCount = info[6];
    		
    		int dx = (bx-rx == 0) ? 0 : (bx-rx)/Math.abs(bx-rx);
    		int dy = (by-ry == 0) ? 0 : (by-ry)/Math.abs(by-ry);
    		To: for(int i=0; i<dCount; i++) {
    			int idx = dStart+i;
    			
    			int nbx = bx + d[idx][0];
    			int nby = by + d[idx][1];
    			int nrx = rx + d[idx][0];
    			int nry = ry + d[idx][1];
    			
    			if(OOB(nbx, nby) || OOB(nrx, nry)) continue;
    			
    			//블루 먼저
    			if(dx == d[idx][0] && dy == d[idx][1]) {
    				while(true) {
    					if(map[nbx][nby] == 'O') continue To;
    					if(map[nbx][nby] == '#') break;
    					nbx += d[idx][0];
    					nby += d[idx][1];
    				}
    				
    				nbx -= d[idx][0];
    				nby -= d[idx][1];
    				
    				while(true) {
    					if(map[nrx][nry] == 'O') break;
    					if(map[nrx][nry] == '#' || (nbx == nrx && nby == nry)) break;
    					nrx += d[idx][0];
    					nry += d[idx][1];
    				}
    				
    				if(map[nrx][nry] == 'O') return count+1;
    				
    				nrx -= d[idx][0];
    				nry -= d[idx][1];
    				
    				if(visited[nrx][nry][nbx][nby]) continue To;
    				int ndStart = idx < 2 ? 2 : 0;
    				q.offer(new int[] {nrx, nry, nbx, nby, count+1, ndStart, 2});
    				visited[nrx][nry][nbx][nby] = true;
    			}
    			//레드 먼저
    			else {
    				while(true) {
    					if(map[nrx][nry] == 'O') break;
    					if(map[nrx][nry] == '#') break;
    					nrx += d[idx][0];
    					nry += d[idx][1];
    				}
    				
    				if(map[nrx][nry] != 'O') {
    					nrx -= d[idx][0];
    					nry -= d[idx][1];    					
    				}
    				
    				
    				while(true) {
    					if(map[nbx][nby] == 'O') continue To;
    					if(map[nbx][nby] == '#' || (nbx == nrx && nby == nry)) break;
    					nbx += d[idx][0];
    					nby += d[idx][1];
    				}
    				
    				nbx -= d[idx][0];
    				nby -= d[idx][1];
    				
    				if(map[nrx][nry] == 'O') return count+1;
    				
    				if(visited[nrx][nry][nbx][nby]) continue To;
    				int ndStart = idx < 2 ? 2 : 0;
    				q.offer(new int[] {nrx, nry, nbx, nby, count+1, ndStart, 2});
    				visited[nrx][nry][nbx][nby] = true;
    			}
    		}
    	}
    	
    	return -1;
    }

	private static boolean OOB(int x, int y) {
		if(x<0 || x>=n || y<0 || y>=m) return true;
		return false;
	}
}
