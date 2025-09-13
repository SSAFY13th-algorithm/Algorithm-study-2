package study10week;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main5972_택배배송 {
	static class House implements Comparable<House> {
		int cow;
		int now;
		
		public House(int cow, int now) {
			this.cow = cow;
			this.now = now;
		}


		@Override
		public int compareTo(House o) {
			return this.cow - o.cow;
		}
	}
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	static int N, M;
	static List<House>[] houses;

	public static void main(String[] args) throws IOException {
		init();
		solve();
	}
	
	static void solve() {
		PriorityQueue<House> q = new PriorityQueue<>();
		boolean visited[] = new boolean[N + 1];
		
		visited[1] = true;
		for (House h : houses[1]) {
			q.add(new House(h.cow, h.now));
		}
		
		while (!q.isEmpty()) {
			House h = q.poll();
			
			if (h.now == N) {
				System.out.println(h.cow);
				return;
			}
			
			visited[h.now] = true;
			for (House n : houses[h.now]) {
				if (!visited[n.now]) {
					q.add(new House(n.cow + h.cow, n.now));
				}
			}
		}
		
	}

	static void init() throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		houses = new LinkedList[N + 1];
		for (int i = 1; i <= N; i++) {
			houses[i] = new LinkedList<House>();
		}
		
		for (int m = 0; m < M; m++) {
			st = new StringTokenizer(br.readLine());
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			int C = Integer.parseInt(st.nextToken());
			houses[A].add(new House(C, B));
			houses[B].add(new House(C, A));	
		}
	}
}
