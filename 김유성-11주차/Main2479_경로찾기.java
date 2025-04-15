package study11week;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main2479_경로찾기 {
	
	static class Way {
		String before;
		int index;
		
		public Way(String before, int index) {
			this.before = before;
			this.index = index;
		}
	}

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	static int N, K, start, end;
	static Map<Integer, List<Integer>>[] binary; // String: 입력되는 이진코드, List : 비교하는 이진코드의 index번호
	static String[] str_binary;
	static boolean[] visited;

	public static void main(String[] args) throws IOException {
		init();
		bfs();
	}
	
	static boolean isHaming(int prev, int next) {
		int cnt = 0;
		for (int i = 0; i < K; i++) {
			if (str_binary[prev].charAt(i) != str_binary[next].charAt(i)) {
				cnt++;
			}
			if (cnt > 1)
				return false;
		}
		return true;
	}

	static void bfs() {
		Queue<Way> q = new LinkedList<>();
		q.add(new Way("" + (start + 1), start));
		
		while (!q.isEmpty()) {
			Way w = q.poll();
			if (w.index == end) {
				System.out.println(w.before);
				return;
			}
			
			if (visited[w.index]) continue;
			
			visited[w.index] = true;
			
			for (int i = 0; i < N; i++) {
				if (!visited[i] && isHaming(w.index, i)) {
					q.add(new Way(w.before + " " + (i + 1), i));
				}
			}
		}
		System.out.println(-1);
	}

	static void init() throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		str_binary = new String[N];
		visited = new boolean[N];
		
		for (int n = 0; n < N; n++) {
			str_binary[n] = br.readLine();
		}
		
		st = new StringTokenizer(br.readLine());
		start = Integer.parseInt(st.nextToken()) - 1;
		end= Integer.parseInt(st.nextToken()) - 1;
	}
}
