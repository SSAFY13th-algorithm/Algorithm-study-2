package Algo10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main4386_별자리만들기 {
	
	static class Node implements Comparable<Node> {
		int from;
		int to;
		double len;
		
		public Node(int from, int to, double len) {
			this.from = from;
			this.to = to;
			this.len = len;
		}
		
		@Override
		public int compareTo(Node o) {
			return Double.compare(this.len, o.len);
		}
	}
	
	static int N, parents[];
	static double star[][];
	static PriorityQueue<Node> pq = new PriorityQueue<>();
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	public static void main(String[] args) throws IOException{
		init();
		double min = getMin();
		System.out.println(Math.round(min * 100) * 0.01);
	}
	
	static double getMin() {
		double ret = 0;
		int cnt = 0;
		
		while (!pq.isEmpty()) {
			Node node = pq.poll();
			if (union(node.from, node.to)) {
				cnt++;
				ret += node.len;
			}
			if (cnt == N - 1)
				break;
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
	
	static double distance(double first[], double second[]) {
		double x = Math.abs(first[0] - second[0]);
		double y = Math.abs(first[1] - second[1]);
		
		return Math.sqrt(x * x + y * y);
	}
	
	static void init() throws IOException {
		N = Integer.parseInt(br.readLine());
		
		parents = new int [N];
		star = new double [N][2];
		
		for (int i = 0; i < N; i++) {
			parents[i] = i;
		}
		
		for (int n = 0; n < N; n++) {
			st = new StringTokenizer(br.readLine());
			star[n][0] = Double.parseDouble(st.nextToken());
			star[n][1] = Double.parseDouble(st.nextToken());
		}
		
		for (int i = 0; i < N - 1; i++) {
			for (int j = i + 1; j < N; j++) {
				pq.add(new Node(i, j, distance(star[i], star[j])));
			}
		}
	}
}
