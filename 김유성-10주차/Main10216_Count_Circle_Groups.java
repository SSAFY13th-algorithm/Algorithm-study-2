package Algo10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main10216_Count_Circle_Groups {
	
	static class Node {
		int x;
		int y;
		int r;
		
		public Node(int x, int y, int r) {
			this.x = x;
			this.y = y;
			this.r = r;
		}
	}
	
	static int N, parents[];
	static Node[] nodes;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	public static void main(String[] args) throws IOException{
		int T = Integer.parseInt(br.readLine());
		for (int t = 1; t <= T; t++) {
			init();
			int ret = area();
			System.out.println(ret);
		}
	}
	
	static boolean distance(Node n1, Node n2) {
		int x = Math.abs(n1.x - n2.x);
		int y = Math.abs(n1.y - n2.y);
		
		if (x * x + y * y <= Math.pow(n1.r + n2.r, 2))
			return true;
		return false;
	}
	
	static int area() {
		int ret = N;
		
		for (int i = 0; i < N - 1; i++) {
			for (int j = i + 1; j < N; j++) {
				
				if (!distance(nodes[i], nodes[j]))
					continue;
				
				if (union(i, j)) {
					ret--;
				}
			}
		}
		return ret;
	}
	
	static int find(int a) {
		if (a == parents[a])
			return a;
		return parents[a] = find(parents[a]);
	}
	
	static boolean union(int a, int b) {
		int root_a = find(a);
		int root_b = find(b);
		
		if (root_a == root_b)
			return false;
		
		if (root_a < root_b)
			parents[root_b] = root_a;
		else
			parents[root_a] = root_b;
		return true;
	}
	
	static void init() throws IOException {
		N = Integer.parseInt(br.readLine());
		
		parents = new int [N];
		nodes = new Node [N];
		
		for (int i = 0; i < N; i++)
			parents[i] = i;
		
		for (int n = 0; n < N; n++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int r = Integer.parseInt(st.nextToken());
			
			nodes[n] = new Node(x, y, r);
		}
	}
}
