import java.io.*;
import java.util.*;

public class Main {
	private static int r, c;
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        
        char[][] originMap = new char[r][c];
        for(int i=0; i<r; i++) {
        	String line = br.readLine();
        	for(int j=0; j<c; j++) {
        		originMap[i][j] = line.charAt(j);
        	}
        }
        
        char[][] fullMap = new char[r][c];
        char[][] map1 = new char[r][c];
        char[][] map2 = new char[r][c];
        for(int i=0; i<r; i++) {
        	Arrays.fill(fullMap[i], 'O');
        	Arrays.fill(map1[i], 'O');
        	Arrays.fill(map2[i], 'O');
        }
        
        int[][] d = {{-1,0},{0,1},{1,0},{0,-1}};
        for(int i=0; i<r; i++) {
        	for(int j=0; j<c; j++) {
        		if(originMap[i][j] == 'O') {
        			map1[i][j] = '.';
        			for(int index=0; index<4; index++) {
        				int nx = i+d[index][0];
        				int ny = j+d[index][1];
        				if(nx<0 || nx>=r || ny<0 || ny>=c) continue;
        				map1[nx][ny] = '.';
        			}
        		}
        	}
        }
        
        for(int i=0; i<r; i++) {
        	for(int j=0; j<c; j++) {
        		if(map1[i][j] == 'O') {
        			map2[i][j] = '.';
        			for(int index=0; index<4; index++) {
        				int nx = i+d[index][0];
        				int ny = j+d[index][1];
        				if(nx<0 || nx>=r || ny<0 || ny>=c) continue;
        				map2[nx][ny] = '.';
        			}
        		}
        	}
        }
        //1:그대로 2:꽉 3:반전 4:꽉 5:그대로 6:꽉 7:반전 8:꽉...
        if(n == 1) print(originMap); 
        else if(n%2 == 0) print(fullMap);
        else if(n%4 == 3) print(map1);
        else if(n%4 == 1) print(map2);
    }

	private static void print(char[][] map) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<r; i++) {
			for(int j=0; j<c; j++) {
				sb.append(map[i][j]+"");
			}
			sb.append("\n");
		}
		System.out.println(sb.toString().trim());
	}
}
