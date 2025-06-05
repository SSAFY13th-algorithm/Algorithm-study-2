import java.io.*;
import java.util.*;

public class Main {
	private static int[] numbers;
	private static int n,m;
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        
        numbers = new int[n+1];
        st = new StringTokenizer(br.readLine());
        int max = 0;
        int min = Integer.MAX_VALUE;
        for(int i=1; i<=n; i++) {
        	numbers[i] = Integer.parseInt(st.nextToken());
        	max = Math.max(max, numbers[i]);
        	min = Math.min(min, numbers[i]);
        }
        
        int result = Integer.MAX_VALUE;
        
        int left = 0;
        int right = max-min;
        while(left <= right) {
        	// mid = 구간 점수의 최댓값
        	int mid = left + (right-left)/2;
        	int count = counting(mid);
        	if(count > m) {
        		left = mid+1;
        	}else {
        		result = Math.min(result, mid);
        		right = mid-1;
        	}
        }

        System.out.println(result);
    }

	private static int counting(int mid) {
		int count = 0;
		int max = 0;
		int min = Integer.MAX_VALUE;
		
		for(int i=1; i<=n; i++) {
			max = Math.max(max, numbers[i]);
			min = Math.min(min, numbers[i]);
			if(mid >= max-min) continue;
		
			count++;
			max = numbers[i];
			min = numbers[i];
		}
		count++;
		return count;
	}
}
