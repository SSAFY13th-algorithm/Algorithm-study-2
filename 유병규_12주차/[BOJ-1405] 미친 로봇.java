import java.io.*;
import java.util.*;
import java.math.BigDecimal;

public class Main {
	private static boolean[][] visited;
	private static int n;
	private static double[] percent;
	private static int[][] d = {{0,1},{0,-1},{1,0},{-1,0}};
	private static double total;
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        n = Integer.parseInt(st.nextToken());
        percent = new double[4];
        for(int i=0; i<4; i++) {
        	percent[i] = Integer.parseInt(st.nextToken()) / 100.0;
        }
        
        if(n == 1) {
        	System.out.println(1.0);
        	return;
        }
        
        visited = new boolean[2*n+1][2*n+1];
        visited[n][n] = true;
        dfs(1, n, n, 0, "");
        
        BigDecimal bd = new BigDecimal(Double.toString(total));
        System.out.println(bd.toPlainString());
    }
    private static String[] p = {"E","W","S","N"};
    private static void dfs(double current, int x, int y, int dept, String path) {
    	if(dept == n) {
    		total += current;
    		return;
    	}
    	
    	for(int i=0; i<d.length; i++) {
    		if(percent[i] == 0) continue;
    		int nx = x+d[i][0];
    		int ny = y+d[i][1];
    		if(visited[nx][ny]) continue;
    		visited[nx][ny] = true;
    		dfs(current * percent[i], nx, ny, dept+1, path+p[i]);
    		visited[nx][ny] = false;
    	}
    	
    }
}
