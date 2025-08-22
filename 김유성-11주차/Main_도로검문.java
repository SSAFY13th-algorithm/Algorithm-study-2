package study11week;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_도로검문 {
	static class Node implements Comparable<Node> {
		int num;
		long len;

		public Node(int num, long len) {
			this.num = num;
			this.len = len;
		}

		@Override
		public int compareTo(Node o) {
			return Long.compare(this.len, o.len);
		}
	}

	static int N, M;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	static List<Node>[] list;
	static long[] dist;
	static boolean[] visited;
	static PriorityQueue<Node> pq = new PriorityQueue<>();

	public static void main(String[] args) throws IOException {
		init();
		long min = getMin(0, 0);

		if (min == Long.MAX_VALUE) {
			System.out.println(-1);
			return;
		}
		long ret = min;

		boolean[][] block = new boolean[N + 1][N + 1];
		for (int i = 1; i <= N; i++) {
			for (Node n : list[i]) {
				if (block[i][n.num]) continue;
				
				ret = Math.max(ret, getMin(i, n.num));
				block[i][n.num] = true;
				block[n.num][i] = true;

				if (ret == Long.MAX_VALUE) {
					System.out.println(-1);
					return;
				}
			}
		}
		System.out.println(ret - min);
	}

	static long getMin(int a, int b) {
		pq.clear();
		Arrays.fill(dist, Long.MAX_VALUE);
		dist[1] = 0;
		pq.add(new Node(1, 0));

		while (!pq.isEmpty()) {
			Node node = pq.poll();
			
			if (node.len > dist[node.num]) continue;
			if (node.num == N) return node.len;

			for (Node n : list[node.num]) {
				if (a == node.num && b == n.num)
					continue;

				if (dist[n.num] > node.len + n.len) {
					dist[n.num] = node.len + n.len;
					pq.add(new Node(n.num, dist[n.num]));
				}
			}
		}
		
		return Long.MAX_VALUE;
	}

	static void init() throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		list = new LinkedList[N + 1];
		for (int i = 1; i <= N; i++) {
			list[i] = new LinkedList<>();
		}
		dist = new long[N + 1];

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int t = Integer.parseInt(st.nextToken());
			list[a].add(new Node(b, t));
			list[b].add(new Node(a, t));
		}
	}
}
