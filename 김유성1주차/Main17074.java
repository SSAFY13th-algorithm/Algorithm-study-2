import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main17074 {
	static boolean[] arr_dp = new boolean[100000 + 1];
	static int ordered_num = 0;
	static int N;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		
		int[] arr = new int [N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		// 0 번째 arr까지는 정렬이 되어 있기 때문에 true로
		arr_dp[0] = true;
		if (arr[1] >= arr[0])
			arr_dp[1] = true;
		
		removeElement(arr, 0);
		
		System.out.println(ordered_num);
	}
	
	static void removeElement(int[]arr, int remove_index) {
		// index가 넘어가거나, dp에서 remove_index 앞쪽이 정렬되어있지 않을 때 끝. 어차피 증가하면서 실행해도 소용없음
		if (remove_index == N || (remove_index > 1 && !arr_dp[remove_index - 1])) {
			return ;
		}
		// dp를 세팅 (다음에 사용. 현재는 remove_index앞쪽까지만 확인)
		if (remove_index > 1 && arr_dp[remove_index - 1]) {
			if (arr[remove_index - 1] <= arr[remove_index])
				arr_dp[remove_index] = true;
		}
		
		// remove_index 뒤쪽부터 끝까지 확인할 때까지 정렬이 되어있으면 true그대로, 아니면 false로
		boolean end = true;
		if (remove_index == 0) {
			int prev = arr[1];
			for (int i = 2; i < N; i++) {
				if (prev > arr[i]) {
					end = false;
					break;
				}
				prev = arr[i];
			}
		} else {
			int prev = arr[remove_index - 1];
			for (int i = remove_index + 1; i < N; i++) {
				if (prev > arr[i]) {
					end = false;
					break;
				}
					prev = arr[i];
			}
		}
		if (end)
			ordered_num++;
		removeElement(arr, remove_index + 1);
	}
}
