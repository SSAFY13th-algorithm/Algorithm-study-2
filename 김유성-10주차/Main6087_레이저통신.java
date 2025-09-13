package study10week;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main6087_레이저통신 {
	static class Node implements Comparable<Node> {
		int h, w, dir, m_num;
		
		public Node(int h, int w, int dir, int m_num) {
			this.h = h;
			this.w = w;
			this.dir = dir;
			this.m_num = m_num;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.m_num, o.m_num);
		}
	}
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	static int W, H, move[][] = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}}; // 상, 좌, 하, 우
	static int[] start = null;
	static char[][] map;
	static boolean[][][] visited;
	
	public static void main(String[] args) throws IOException {
		init();
		System.out.println(bfs());
	}
	
	static boolean isMove(int h, int w, int dir) {
		if (h >= 0 && h < H && w >= 0 && w < W && !visited[h][w][dir]) {
			if (map[h][w] != '*')
				return true;
		}
		return false;
	}
	
	static int bfs() {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		
		int nh, nw, ret = Integer.MAX_VALUE;
		
		for (int i = 0; i < 4; i++)
			visited[start[0]][start[1]][i] = true;
		
		for (int i = 0; i < 4; i++) {
			nh = start[0] + move[i][0];
			nw = start[1] + move[i][1];
			
			if (isMove(nh, nw, i)) {
				pq.add(new Node(nh, nw, i, 0));
			}
		}
		
		while (!pq.isEmpty()) {
			Node node = pq.poll();
			if (visited[node.h][node.w][node.dir])
				continue;
			
			if (map[node.h][node.w] == 'C') {
				ret = Math.min(node.m_num, ret);
			}
			
			visited[node.h][node.w][node.dir] = true;
			
			for (int i = 0; i < 4; i++) {
				nh = node.h + move[i][0];
				nw = node.w + move[i][1];
				
				if (isMove(nh, nw, i)) {
					if (node.dir == i)
						pq.add(new Node(nh, nw, i, node.m_num));
					else
						pq.add(new Node(nh, nw, i, node.m_num + 1));
						
				}
			}
		}
		return ret;
	}

	static void init() throws IOException {
		st = new StringTokenizer(br.readLine());
		W = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		
		map = new char [H][W];
		visited = new boolean[H][W][4];
		
		for (int h = 0; h < H; h++) {
			String input = br.readLine();
			for (int w = 0; w < W; w++) {
				map[h][w] = input.charAt(w);
				if (map[h][w] == 'C' && start == null) {
					start = new int[] {h, w};
				}
			}
		}
	}

}
