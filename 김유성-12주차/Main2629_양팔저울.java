package study12week;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main2629_양팔저울 {

	static int N, M, sum = 0, base[]; // N: 추의 개수, M: 무게를 확인하고자 하는 구슬의 개수
	static boolean dp[][];
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		init();
	}

	static void init() throws IOException {
		N = Integer.parseInt(br.readLine().trim());
		base = new int[N + 1];
		st = new StringTokenizer(br.readLine().trim());
		
		for (int i = 1; i <= N; i++) {
			base[i] = Integer.parseInt(st.nextToken());
			sum += base[i];
		}
		dp = new boolean[N + 1][sum + 1];
		dp(0, 0);

		M = Integer.parseInt(br.readLine().trim());
		st = new StringTokenizer(br.readLine().trim());
		for (int i = 0; i < M; i++) {
			int ball = Integer.parseInt(st.nextToken());
			if (ball <= sum && dp[N][ball])
				System.out.print("Y ");
			else
				System.out.print("N ");
		}
	}

	static void dp(int index, int weight) {
		if (dp[index][weight]) return;
		
		dp[index][weight] = true;
		if (index == N) return;
		
		dp(index + 1, weight); // 현재 무게에 해당 추를 추가하지 않음
		dp(index + 1, weight + base[index + 1]); // 현재 무게에 해당 추를 추가한 경우
		dp(index + 1, Math.abs(weight - base[index + 1])); // 현재 무게에서 해당 추를 뺀 경우
	}
}
