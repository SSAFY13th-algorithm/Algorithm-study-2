import java.util.*;
import java.io.*;

class Gift{
	int time;
	char color;
	Gift(int time, char color){
		this.time = time;
		this.color = color;
	}
}

public class Main {
	static StringTokenizer st;
	static int nextInt() { return Integer.parseInt(st.nextToken()); }
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		st = new StringTokenizer(br.readLine());
		
		ArrayList<Gift> arr = new ArrayList<>();
		int A = nextInt(), B = nextInt(), N = nextInt();
		int ta = 0, tb = 0;
		int sizeA = 0, sizeB = 0;
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			int t = nextInt();
			char c = st.nextToken().charAt(0);
			int k = nextInt();
			
			if(c == 'B') sizeA += k;
			else sizeB += k;
			
			for(int j=0;j<k;j++) {
				if(c == 'B') {
					if(ta < t) ta = t;
					arr.add(new Gift(ta,'B'));
					ta += A;
				}
				else {
					if(tb < t) tb = t;
					arr.add(new Gift(tb,'R'));
					tb += B;
				}
			}
			
		}
		Collections.sort(arr, (a,b) -> (a.time==b.time)?a.color-b.color:a.time-b.time);
		
		int[] AA = new int[sizeA];
		int[] BB = new int[sizeB];
		int pa = 0, pb = 0;
		int g = 1;
		for(int i=0;i<arr.size();i++) {
			if(arr.get(i).color == 'B') AA[pa++] = g++;
			else BB[pb++] = g++;
		}
		
		bw.write(sizeA+"\n");
		for(int i:AA) bw.write(i+" ");
		bw.write("\n"+sizeB+"\n");
		for(int i:BB) bw.write(i+" ");
		
		bw.close();
	}

}
