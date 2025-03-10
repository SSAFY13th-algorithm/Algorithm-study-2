import java.io.*;
import java.util.*;

public class Main {
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		
		
		int[] numbers = new int[n];
		for(int i=0; i<n; i++) {
			numbers[i] = Integer.parseInt(br.readLine());
		}
		
		if(n == 1) {
			System.out.println(numbers[0]+k);
			return;
		}
		int result = 0;
		Arrays.sort(numbers);
		int left = numbers[0];
		int right = numbers[0]+k;
		while(left <= right) {
			int mid = (left+right)/2;
			
			long need = 0;
			for(int i=0; i<numbers.length; i++) {
				if(mid <= numbers[i]) break;
				need += mid-numbers[i];
			}
			
			if(need <= k) {
				left = mid+1;
				result = Math.max(result, mid);
			}
			else right = mid-1;
		}
		System.out.println(result);
	}
}
