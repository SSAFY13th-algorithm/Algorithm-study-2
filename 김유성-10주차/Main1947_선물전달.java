package study10week;

import java.util.Scanner;

public class Main1947_선물전달 {
	
	static long[] dp;
	static int div = 1_000_000_000;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in); 
		int N = sc.nextInt();
		dp = new long [N + 3];
		dp[2] = 1;
		dp[3] = 2;
		getDp(N);
		System.out.println(dp[N]);
	}
	
	static void getDp(int n) {
		for (int i = 4; i <= n; i++) {
			dp[i] = ((i - 1) * (dp[i - 1] + dp[i - 2])) % div;
		}
	}
}