import java.util.Scanner;

public class Main11058 {
	
	static long dp[] = new long [100 + 1];
	static int N;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		
		dp[1] = 1;
		dp[2] = 2;
		dp[3] = 3;
		dp[4] = 4;
		dp[5] = 5;
		dp[6] = 6;
		
		
		for (int i = 7; i <= N; i++) {
			kriiboard(i);
		}
		System.out.println(dp[N]);
	}
	
	static void kriiboard(int num) {		
		long max = Math.max(case1(num), case2(num));
		max = Math.max(max, case3(num));
		dp[num] = max;
	}
	
	// a + c + v
	static long case1(int index) {
		return dp[index - 3] * 2;
	}
	
	// a + c + v + v
	static long case2(int index) {
		return dp[index - 4] * 3;
	}
	
	// a + c + v + v + v
	static long case3(int index) {
		return dp[index - 5] * 4;
	}
	
}

