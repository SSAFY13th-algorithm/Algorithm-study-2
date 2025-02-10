import java.io.*;
import java.util.*;
/*
 * 실력차이가 많이나도록 조 편성. 조 개수는 상관없음
 * 조가 잘짜여진 정도: 가장 높은 점수 - 가장 낮은 점수
 * 전체 정도: 각각의 조 점수 합
 * 즉, 전체 정도가 가장 클 때의 값 출력
 * */
public class Main {
	private static int[] list;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		
		list = new int[n+1];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=1; i<=n; i++) {
			list[i] = Integer.parseInt(st.nextToken());
		}
		
		int[] dp = new int[n+1];
		
		dp[1] = 0;
		for(int i=2; i<=n; i++) {
			int max = 0;
			for(int j=i-1; j>=0; j--) {
				max = Math.max(max, dp[j]+calcul(j+1, i));
			}
			dp[i] = max;
		}
		
		System.out.println(dp[n]);
	}

	private static int calcul(int start, int end) {
		int max = list[start];
		int min = list[start];
		for(int i=start; i<=end; i++) {
			max = Math.max(max, list[i]);
			min = Math.min(min, list[i]);
		}
		return max-min;
	}
}
