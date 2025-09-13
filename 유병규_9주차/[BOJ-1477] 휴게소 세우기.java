import java.io.*;
import java.util.*;

public class Main {
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());
        
        String[] line = n == 0 ? new String[] {} : br.readLine().split(" ");
        int[] nums = Arrays.stream(line)
        		.mapToInt(Integer::parseInt)
        		.toArray();
        Arrays.sort(nums);
       
        int min = Integer.MAX_VALUE;
        int left = 1;
        int right = 1000;
        while(left <= right) {
        	int mid = left+(right-left)/2;
        	int count = counting(nums, mid, l);
        	if(count <= m) {
        		min = Math.min(min, mid);
        		right = mid-1;
        	}
        	else {
        		left = mid+1;
        	}
        }
        System.out.println(min);
    }
    
    private static int counting(int[] nums, int dir, int end) {
    	int count = 0;
    	int pre = 0;
    	for(int i=0; i<nums.length; i++) {
    		int a = nums[i]-pre;
    		if(nums[i]-pre > dir) {
    			count += (nums[i]-pre-1)/dir;
    		}
    		pre = nums[i];
    	}
    	if(end-pre > dir) {
			count += (end-pre-1)/dir;
		}
    	return count;
    }
}
