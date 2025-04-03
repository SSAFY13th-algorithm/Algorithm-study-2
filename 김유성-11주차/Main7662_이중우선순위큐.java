package algostudy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Main7662_이중우선순위큐 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static TreeSet<Integer> set;
	static HashMap<Integer, Integer> map;

	public static void main(String[] args) throws IOException {
		int T = Integer.parseInt(br.readLine());
		for (int t = 1; t <= T; t++) {
			init();
			if (set.isEmpty())
				System.out.println("EMPTY");
			else
				System.out.println(set.last() + " " + set.first());
		}
	}

	static void init() throws IOException {
		int N = Integer.parseInt(br.readLine());
		set = new TreeSet<>((a, b) -> Integer.compare(a, b));
		map = new HashMap<>();
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			String flag = st.nextToken();
			int num = Integer.parseInt(st.nextToken());
			int number;
			
			if (flag.equals("I")) {
				number = map.getOrDefault(num, 0);
				map.put(num, number + 1);
				set.add(num);
			} else {
				// 최댓값 삭제
				if (set.isEmpty())
					continue;
				
				if (num == 1) {
					num = set.last();
					number = map.get(num);
				} else {
					num = set.first();
					number = map.get(num);
				}
				
				if (number == 1) {
					map.remove(num);
					set.remove(num);
				} else {
					map.put(num, number - 1);
				}
			}
		}
	}
}
