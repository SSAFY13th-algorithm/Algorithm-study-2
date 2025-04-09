import java.io.*;
import java.util.*;

public class Main {
	private static long[] process;
	private static long result;
	private static long m;
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        long n = Long.parseLong(st.nextToken());
        m = Long.parseLong(st.nextToken());
        
        process = new long[(int) n];
        long max = 0;
        for(int i=0; i<n; i++) {
        	process[i] = Long.parseLong(br.readLine());
        	max = Math.max(max, process[i]);
        }
        result = Long.MAX_VALUE;
        
        long left = 0;
        long right = max*m;
        while(left <= right) {
        	long mid = left + (right-left)/2;
        	
        	long count = counting(mid);
        	if(count >= m) {
        		result = Math.min(result, mid);
        		right = mid-1;
        		continue;
        	}
        	left = mid+1;
        }
        
        System.out.println(result);
    }

	private static long counting(long mid) {
		long count = 0;
		for(int i=0; i<process.length; i++) {
			if(count > m) break;
			count += mid/process[i];
		}
		
		return count;
	}
}
