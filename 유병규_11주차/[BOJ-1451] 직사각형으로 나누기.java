import java.io.*;
import java.util.*;

public class Main {
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        int[][] map = new int[n][m];
        for(int i=0; i<n; i++) {
        	String line = br.readLine();
        	for(int j=0; j<m; j++) {
        		map[i][j] = line.charAt(j)-'0';
        	}
        }
        
        int[][] sumMap = new int[n][m];
        for(int i=0; i<n; i++) {
        	for(int j=0; j<m; j++) {
        		if(i==0 && j==0) sumMap[i][j] = map[i][j];
        		else if(i==0) sumMap[i][j] = sumMap[i][j-1]+map[i][j];
        		else if(j==0) sumMap[i][j] = sumMap[i-1][j]+map[i][j];
        		else sumMap[i][j] = sumMap[i-1][j]+sumMap[i][j-1]-sumMap[i-1][j-1]+map[i][j];
        	}
        }
        
        int total = sumMap[n-1][m-1];
        long max = 0;
        
        // 행이 1인 경우
        if(n==1) {
        	for(int i=0; i<m-2; i++) {
        		int one = sumMap[0][i];
        		for(int j=i+1; j<m-1; j++) {
        			int two = sumMap[0][j] - one;
        			int three = total-one-two;
        			max = Math.max(max, (long) one*two*three);
        		}
        	}
        	
        	System.out.println(max);
        	return;
        }
        
        // 열이 1인 경우
        if(m==1) {
        	for(int i=0; i<n-2; i++) {
        		int one = sumMap[i][0];
        		for(int j=i+1; j<n-1; j++) {
        			int two = sumMap[j][0] - one;
        			int three = total-one-two;
        			max = Math.max(max, (long) one*two*three);
        		}
        	}
        	System.out.println(max);
        	return;
        }
        
        // 행과 열이 모두 2 이상인 경우
        for(int i=0; i<n-1; i++) {
        	for(int j=0; j<m-1; j++) {        		
        		int one = sumMap[i][j];
        		//case 1: 첫번째 구역의 아래를 두번째 구역으로
        		int two = sumMap[n-1][j]-one;
        		int three = total-one-two;
        		max = Math.max(max, (long) one*two*three);
        		//case 2: 첫번째 구역의 오른쪽을 두번째 구역으로
        		two = sumMap[i][m-1]-one;
        		three = total-one-two;
        		max = Math.max(max, (long) one*two*three);
        	}
        }
        
        //첫번째 구역이 i행의 모든 열을 포함하는 경우
        for(int i=0; i<n-1; i++) {
        	int one = sumMap[i][m-1];
        	//case 3: 남은 구역을 세로로 나눠먹는 경우
        	for(int j=0; j<m-1; j++) {
        		int two = sumMap[n-1][j] - sumMap[i][j];
        		int three = total-one-two;
        		max = Math.max(max, (long) one*two*three);
        	}
        	//case 4: 남은 구역을 가로로 나눠먹는 경우
        	for(int j=i+1; j<n-1; j++) {
    			int two = sumMap[j][m-1] - one;
    			int three = total-one-two;
    			max = Math.max(max, (long) one*two*three);
    		}
        }
        
        //첫번째 구역이 i열의 모든 행을 포함하는 경우
        for(int j=0; j<m-1; j++) {
        	int one = sumMap[n-1][j];
        	//case 5: 남은 구역을 가로로 나눠먹는 경우
        	for(int i=0; i<n-1; i++) {
        		int two = sumMap[i][m-1] - sumMap[i][j];
        		int three = total-one-two;
        		max = Math.max(max, (long) one*two*three);
        	}
        	//case 6: 남은 구역을 세로로 나눠먹는 경우
        	for(int i=j+1; i<m-1; i++) {
    			int two = sumMap[n-1][i] - one;
    			int three = total-one-two;
    			max = Math.max(max, (long) one*two*three);
    		}
        }
        
        System.out.println(max);
    }
}
