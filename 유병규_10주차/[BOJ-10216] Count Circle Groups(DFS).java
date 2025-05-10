import java.io.*;
import java.util.*;

public class Main {
	static class Info{
		int x;
		int y;
		int r;
		int num;
		
		public Info(int x, int y, int r) {
			this.x = x;
			this.y = y;
			this.r = r;
			this.num = -1;
		}
		
		public boolean isOther(Info info) {
			double dist = Math.sqrt(Math.pow(Math.abs(this.x-info.x), 2)+Math.pow(Math.abs(this.y-info.y), 2));
			if(dist <= this.r+info.r) return false;
			return true;
		}
	}
	
	private static int n;
	private static Info[] infos;
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        
        for(int test=0; test<T; test++) {
        	n = Integer.parseInt(br.readLine());
        	
        	infos = new Info[n];
        	for(int i=0; i<n; i++) {
        		StringTokenizer st = new StringTokenizer(br.readLine());
        		int x = Integer.parseInt(st.nextToken());
        		int y = Integer.parseInt(st.nextToken());
        		int r = Integer.parseInt(st.nextToken());
        		infos[i] = new Info(x, y, r);
        	}
        	
        	int group = 0;
        	for(int i=0; i<n; i++) {
        		if(infos[i].num != -1) continue;
        		infos[i].num = group;
        		recur(infos[i], group);
        		group++;
        	}
        	
        	sb.append(group).append("\n");
        }
        
        System.out.println(sb.toString().trim());
    }
    
    private static void recur(Info info, int gNum) {
    	for(int i=0; i<n; i++) {
    		if(infos[i].num != -1) continue;
    		if(info.isOther(infos[i])) continue;
    		infos[i].num = gNum;
    		recur(infos[i], gNum);
    	}
    }
}
