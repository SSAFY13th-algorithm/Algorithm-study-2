import java.io.*;
import java.util.*;

public class Main {
	private static int count=0;
	private static int[][] d = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};
	private static int n,m;
	private static char[][] map;
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        map = new char[n][m];
        for(int i=0; i<n; i++) {
        	String line = br.readLine();
        	for(int j=0; j<m; j++) {
        		map[i][j] = line.charAt(j);
        	}
        }
        
        Map<String, Integer> hashmap = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for(int repeat=0; repeat<k; repeat++) {
        	String word = br.readLine();
        	count=0;
        	if(hashmap.containsKey(word)) {
        		sb.append(hashmap.get(word)).append("\n");
        		continue;
        	}
        	for(int i=0; i<n; i++) {
        		for(int j=0; j<m; j++) {
        			if(map[i][j] != word.charAt(0)) continue;
        			dfs(i,j,1,word);
        		}
        	}
        	hashmap.put(word, count);
        	sb.append(count).append("\n");
        }
        
        System.out.println(sb.toString().trim());
    }

	private static void dfs(int x, int y, int index, String word) {
		if(index == word.length()) {
			count++;
			return;
		}
		
		for(int i=0; i<d.length; i++) {
			int nx = x+d[i][0];
			int ny = y+d[i][1];
			if(nx<0) nx = n-1;
			if(nx>=n) nx=0;
			if(ny<0) ny=m-1;
			if(ny>=m) ny=0;
			
			if(word.charAt(index) != map[nx][ny]) continue;
			dfs(nx, ny, index+1, word);
		}
	}
}
