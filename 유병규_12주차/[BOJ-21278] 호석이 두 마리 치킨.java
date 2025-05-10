import java.io.*;
import java.util.*;

public class Main {
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        //1. dist 배열 준비
        //1-1. dist 배열 최댓값으로 초기화
        int[][] dist = new int[n][n];
        for(int i=0; i<n; i++) {
        	Arrays.fill(dist[i], (int) 1e9);
        }
        
        //1-2. 자기 자신 거리는 0으로 설정
        for(int i=0; i<n; i++) {
        	for(int j=0; j<n; j++) {
        		if(i==j) dist[i][j] = 0;
        	}
        }
        
        //1-3. 연결된 노드는 거리 값으로 설정
        for(int i=0; i<m; i++) {
        	st = new StringTokenizer(br.readLine());
        	int a = Integer.parseInt(st.nextToken())-1;
        	int b = Integer.parseInt(st.nextToken())-1;
        	
        	dist[a][b] = 1;
        	dist[b][a] = 1;
        }
        
        //2. 플로이드-워셜 알고리즘으로 모든 노드별 모든 노드까지 최단 거리로 설정
        for(int k=0; k<n; k++) {
        	for(int i=0; i<n; i++) {
        		for(int j=0; j<n; j++) {
        			dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
        		}
        	}
        }
        
        //3. 정답 찾기
        int min = Integer.MAX_VALUE;// 최단 거리 합
        int a = 0;// 치킨 집 1호점
        int b = 0;// 치킨 집 2호점
        
        for(int i=0; i<n; i++) {// 1호점 위치
        	for(int j=0; j<n; j++) {// 2호점 위치
        		int sum = 0;
        		for(int h=0; h<n; h++) {// 모든 집에서 가장 가까운 치킨 집까지의 접근성 계산
        			sum += Math.min(dist[h][i], dist[h][j]) * 2;
        		}
        		if(sum < min) {// 더 작다면 값 수정
        			min = sum;
        			a = i+1;
        			b = j+1;
        		}
        	}
        }
        
        System.out.println(a + " " + b + " " + min);
    }
}
