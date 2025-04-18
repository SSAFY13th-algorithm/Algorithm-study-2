import java.io.*;
import java.util.*;

public class Main {
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    
        //입력 값
        int n = Integer.parseInt(br.readLine());
        int[] a = new int[n];
        int[] b = new int[n];
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
        	a[i] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
        	b[i] = Integer.parseInt(st.nextToken());
        }
        
        //바늘의 각도가 특정 순서대로 주어지지 않아, 정렬
        Arrays.sort(a);
        Arrays.sort(b);
        
        //a 배열의 각 바늘 간 사잇각을 저장하는 배열
        int[] da = new int[n];
        int pre = 0;
        for(int i=1; i<n; i++) {
        	da[i-1] = a[i]-a[pre++];
        }
        //첫 바늘과 마지막 바늘의 사잇각
        da[n-1] = a[0]+360000 - a[n-1];
        
        //b 배열의 각 바늘 간 사잇각을 저장하는 배열
        int[] db = new int[n];
        pre = 0;
        for(int i=1; i<n; i++) {
        	db[i-1] = b[i]-b[pre++];
        }
        //첫 바늘과 마지막 바늘의 사잇각
        db[n-1] = b[0]+360000 - b[n-1];
        
        //실패 함수: pi배열
        //pi[i]: 0~i까지의 부분 문자열에서 접두사와 접미사의 최대 길이
        int[] pi = new int[n];
        
        int j=0;
        for(int i=1; i<n; i++) {
        	while(j>0 && da[i] != da[j]) {
        		j = pi[j-1];
        	}
        	if(da[i] == da[j]) {
        		pi[i] = ++j;
        	}
        }
        
        //모든 점을 시작점으로 비교하기 위한 배열
        int[] target = new int[2*n];
        for(int i=0; i<2*n; i++){
        	target[i] = db[i%n];
        }
        
        //kmp
        j=0;
        for(int i=0; i<2*n; i++) {
        	while(j>0 && target[i] != da[j]) {
        		j = pi[j-1];
        	}
        	
        	if(target[i] == da[j]) {
        		//찾았다면 종료
        		if(j == n-1) {
        			System.out.println("possible");
        			return;
        		}
        		j++;
        	}
        }
        
        System.out.println("impossible");
    }
}
