import java.io.*;
import java.util.*;

public class Main {
	private static int n;
	private static int[][] map;
	private static int total;
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        n = Integer.parseInt(br.readLine());
        map = new int[n][n];
        for(int i=0; i<n; i++) {
        	StringTokenizer st = new StringTokenizer(br.readLine());
        	for(int j=0; j<n; j++) {
        		map[i][j] = Integer.parseInt(st.nextToken());
        		total += map[i][j];
        	}
        }
        
        int min = Integer.MAX_VALUE;
        for(int i=0; i<n-2; i++) {
        	for(int j=1; j<n-1; j++) {
        		min = Math.min(min, counting(i, j));
        	}
        }
        
        System.out.println(min);
    }
    
	private static int counting(int x, int y) {
		int result = Integer.MAX_VALUE;
		
		for(int d1=1; d1<n; d1++) {
			for(int d2=1; d2<n; d2++) {
				if(x+d1+d2>=n || y-d1<0 || y+d2>=n) continue;
				
				int[] arr = new int[5];
				arr[0] = count1(x, y, d1, d2);
				arr[1] = count2(x, y, d1, d2);
				arr[2] = count3(x, y, d1, d2);
				arr[3] = count4(x, y, d1, d2);
				arr[4] = total-(arr[0]+arr[1]+arr[2]+arr[3]);
				
				int max=0,min=Integer.MAX_VALUE;
        		for(int k=0; k<arr.length; k++) {
        			max = Math.max(max, arr[k]);
        			min = Math.min(min, arr[k]);
        		}
        		result = Math.min(result, max-min);
			}
		}
		
		return result;
	}
	
	private static int count1(int x, int y, int d1, int d2) {
		int count = 0;
		int dd1 = 0;
		int dd2 = 0;
		
		for(int r=0; r<x+d1; r++) {
			for(int c=0; c<=y; c++) {
				if(r==x+dd1 && c==y+dd2) {
					dd1++;
					dd2--;
					break;
				}
				count += map[r][c];
			}
		}
		
		return count;
	}
	
	private static int count2(int x, int y, int d1, int d2) {
		int count = 0;
		int dd1 = 1;
		int dd2 = 1;
		
		for(int r=0; r<=x+d2; r++) {
			for(int c=n-1; c>y; c--) {
				if(r==x+dd1 && c==y+dd2) {
					dd1++;
					dd2++;
					break;
				}
				count += map[r][c];
			}
		}
		
		return count;
	}
	
	private static int count3(int x, int y, int d1, int d2) {
		int count = 0;
		int dd1 = d1;
		int dd2 = -d1;
		
		for(int r=x+d1; r<n; r++) {
			for(int c=0; c<y-d1+d2; c++) {
				if(r==x+dd1 && c==y+dd2) {
					dd1++;
					dd2++;
					break;
				}
				count += map[r][c];
			}
		}
		
		return count;
	}
	
	private static int count4(int x, int y, int d1, int d2) {
		int count = 0;
		int dd1 = d2+1;
		int dd2 = d2-1;
		
		for(int r=x+d2+1; r<n; r++) {
			for(int c=n-1; c>=y-d1+d2; c--) {
				if(r==x+dd1 && c==y+dd2) {
					dd1++;
					dd2--;
					break;
				}
				count += map[r][c];
			}
		}
		
		return count;
	}
}
