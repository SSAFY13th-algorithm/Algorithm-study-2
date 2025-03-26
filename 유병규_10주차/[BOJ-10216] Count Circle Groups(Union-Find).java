import java.io.*;
import java.util.*;

public class Main {
	static class Info{
		int x;
		int y;
		int r;
		int num;
		
		public Info(int x, int y, int r, int num) {
			this.x = x;
			this.y = y;
			this.r = r;
			this.num = num;
		}
		
		public boolean isOther(Info info) {
			double dist = Math.sqrt(Math.pow(Math.abs(this.x-info.x), 2)+Math.pow(Math.abs(this.y-info.y), 2));
			if(dist <= this.r+info.r) return false;
			return true;
		}
	}
	
	private static int n;
	private static List<Info> infos;
	private static int[] parent;
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        
        for(int test=0; test<T; test++) {
        	n = Integer.parseInt(br.readLine());
        	
        	parent = new int[n];
        	for(int i=0; i<n; i++) {
        		parent[i] = i;
        	}
        	
        	infos = new ArrayList<>();
        	for(int i=0; i<n; i++) {
        		StringTokenizer st = new StringTokenizer(br.readLine());
        		int x = Integer.parseInt(st.nextToken());
        		int y = Integer.parseInt(st.nextToken());
        		int r = Integer.parseInt(st.nextToken());
        		Info nInfo = new Info(x, y, r, i);
        		
        		for(Info info : infos) {
        			if(info.isOther(nInfo)) continue;
        			
        			union(info, nInfo);
        		}
        		
        		infos.add(nInfo);
        	}
        	
        	int group = 0;
        	for(int i=0; i<n; i++) {
        		if(parent[i]==i) group++;
        	}
        	sb.append(group).append("\n");
        }
        
        System.out.println(sb.toString().trim());
    }

	private static void union(Info info, Info nInfo) {
		int a = findRoot(info.num);
		int b = findRoot(nInfo.num);
		
		if(a != b) parent[b] = a;
	}

	private static int findRoot(int x) {
		if(parent[x] == x) return x;
		return parent[x] = findRoot(parent[x]);
	}
}
