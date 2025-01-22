import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main17089 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		int[][] friend = new int [N + 1][N + 1];
		
		for (int i = 0; i < M; i++) {
			StringTokenizer token = new StringTokenizer(br.readLine());
			int people1 = Integer.parseInt(token.nextToken());
			int people2 = Integer.parseInt(token.nextToken());
			// 친구인 사람끼리 표시하기
			friend[people1][people2]++;
			friend[people2][people1]++;
			
			// 총 친구의 수
			friend[people1][0]++;
			friend[people2][0]++;
		}
		
		int friend_num = Integer.MAX_VALUE;
		for (int a = 1; a <= N - 2; a++) {
			for (int b = a + 1; b <= N - 1; b++) {
					// 셋이 친구인 경우에만 총 합 계산
					if (friend[a][b] > 0) {
						for (int c = b + 1; c <= N; c++) {
							if (friend[b][c] > 0 && friend[c][a] > 0)
								friend_num = Math.min(friend_num, sum_friends(a, b, c, friend));
					}
				}
			}
		}
		
		if (friend_num != Integer.MAX_VALUE) {
			System.out.println(friend_num);
		} else {
			System.out.println(-1);
		}
	}
	
	static int sum_friends(int a, int b, int c, int[][] friend) {
		int sum = 0;
		sum += friend[a][0] - 2;
		sum += friend[b][0] - 2;
		sum += friend[c][0] - 2;
		return sum;
	}

}
