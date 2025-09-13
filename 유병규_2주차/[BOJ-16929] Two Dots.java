import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	private static int n, m;
	private static char[][] board;
	private static boolean[][] visited;
	private static int[][] d = {{-1,0}, {0,1}, {1,0}, {0,-1}};
	private static String result = "No";

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		board = new char[n][m];

		for(int i=0; i<n; i++) {
			String line = br.readLine();
			for(int j=0; j<m; j++) {
				board[i][j] = line.charAt(j);
			}
		}

		visited = new boolean[n][m];
		for(int i=0; i<n; i++) {
			for(int j=0; j<m; j++) {
				if(visited[i][j]) continue;

				dfs(board[i][j], i, j, i, j, 0);
				if(result.equals("Yes")) {
					System.out.println(result);
					return;
				}
			}
		}

		System.out.println(result);
	}

	private static void dfs(char start, int startX, int startY, int x, int y, int depth) {
		if(x==startX && y==startY && depth >= 4) {
			result = "Yes";
			return;
		}

		if(visited[x][y]) return;
		visited[x][y] = true;

		for(int k=0; k<d.length; k++) {
			int nx = x+d[k][0];
			int ny = y+d[k][1];
			if(nx<0 || nx>=n || ny<0 || ny>=m || board[nx][ny] != start) {
				continue;
			}
			dfs(start, startX, startY, nx, ny, depth+1);
		}
		visited[x][y] = false;
	}
}
