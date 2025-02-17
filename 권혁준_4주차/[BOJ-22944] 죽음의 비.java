import java.util.*;
import java.io.*;


class Main {
	
	// IO field
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;

	static void nextLine() throws Exception {st = new StringTokenizer(br.readLine());}
	static int nextInt() {return Integer.parseInt(st.nextToken());}
	static long nextLong() {return Long.parseLong(st.nextToken());}
	static void bwEnd() throws Exception {bw.flush();bw.close();}
	
	// Additional field
	static int N, H, D, K;
	static int sx, sy, ex, ey;
	static List<int[]> U;
	static int ans = Integer.MAX_VALUE;
	
	
	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		nextLine();
		N = nextInt();
		H = nextInt();
		D = nextInt();
		U = new ArrayList<>();
		for(int i=0;i<N;i++) {
			char[] temp = br.readLine().toCharArray();
			for(int j=0;j<N;j++) {
				if(temp[j] == 'S') {
					sx = i;
					sy = j;
				}
				if(temp[j] == 'E') {
					ex = i;
					ey = j;
				}
				if(temp[j] == 'U') {
					U.add(new int[] {i,j});
				}
			}
		}
		K = U.size();
		
	}
	
	static void solve() throws Exception{

		bck(0, new ArrayList<Integer>());
		bw.write((ans == Integer.MAX_VALUE ? -1 : ans) + "\n");
		
	}
	
	static void bck(int state, List<Integer> L) {
		int val = 0, hp = H, dhp = D;
		int px = sx, py = sy;
		boolean um = false;
		for(int i:L) {
			int dist = Math.abs(px-U.get(i)[0]) + Math.abs(py-U.get(i)[1]) - 1;
			val += dist+1;
			if(um) {
				if(dist < dhp + hp) hp -= Math.max(0, dist-dhp);
				else return;
			}
			else {
				if(dist < hp) hp -= dist;
				else return;
			}
			dhp = D-1;
			if(dhp > 0) um = true;
			else um = false;
			px = U.get(i)[0];
			py = U.get(i)[1];
		}
		int dist = Math.abs(ex-px) + Math.abs(ey-py) - 1;
		val += dist+1;
		
		if(um) {
			if(dist < dhp + hp) {
				hp -= Math.max(0, dist-dhp);
				ans = Math.min(ans, val);
			}
		}
		else {
			if(dist < hp) {
				hp -= dist;
				ans = Math.min(ans, val);
			}
		}
		
		
		if(state == (1<<K)-1) return;
		for(int i=0;i<K;i++) if((state & (1<<i)) == 0) {
			L.add(i);
			bck(state | (1<<i), L);
			L.remove(L.size()-1);
		}
		
	}
	
}
