import java.io.*;
import java.util.*;

public class Main {
	private static long[] lengths;
	private static long[] pattys;
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = Integer.parseInt(st.nextToken());
        long x = Long.parseLong(st.nextToken());
        
        lengths = new long[n+1];
        pattys = new long[n+1];
        lengths[0] = 1;
        pattys[0] = 1;
        for(int i=1; i<=n; i++) {
        	lengths[i] = (lengths[i-1]*2) + 3;
        	pattys[i] = (pattys[i-1]*2) + 1;
        }
        
        long result = counting(n, x);
        
        System.out.println(result);
    }

	private static long counting(int level, long x) {
		if(level == 0) return 1;
		if(x == 1) return 0;
		if(x == lengths[level]) return pattys[level];
		
		long mid = (lengths[level]/2) + 1;
		if(x == mid) return pattys[level-1] + 1;
		if(x < mid) return counting(level-1, x-1);
		return pattys[level-1] + 1 + counting(level-1, x-mid);
	}
}
