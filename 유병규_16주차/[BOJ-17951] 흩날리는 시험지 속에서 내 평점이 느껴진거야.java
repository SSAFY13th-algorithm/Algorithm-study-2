import java.io.*;
import java.util.*;

public class Main {
	private static int n, k;
	private static int[] num;
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        
        st = new StringTokenizer(br.readLine());
        num = new int[n];
        int sum = 0;
        for(int i=0; i<n; i++) {
        	num[i] = Integer.parseInt(st.nextToken());
        	sum += num[i];
        }

        if(k == 1) {
        	System.out.println(sum);
        	return;
        }
        
        int left = 0;
        int right = sum;
        int mid = 0;
        while(left <= right) {
        	mid = left + (right-left)/2;
        	
        	int count = counting(mid);
        	
        	if(count < k) {
        		right = mid-1;
        		continue;
        	}

        	left = mid+1;
        }
        
        System.out.println(right);
    }

	private static int counting(int min) {
		int count = 0;
		
		int sum = 0;
		for(int i=0; i<n; i++) {
			sum += num[i];
			if(sum < min) continue;
			count++;
			sum = 0;
		}

		return count;
	}
}
