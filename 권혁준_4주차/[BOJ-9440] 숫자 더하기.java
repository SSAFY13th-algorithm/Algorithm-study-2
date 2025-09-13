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
    static int N;
    static int[] C;

	public static void main(String[] args) throws Exception {
		
        nextLine();
        N = nextInt();
        while(N != 0){
            C = new int[10];
            for(int i=0;i<N;i++) C[nextInt()]++;

            String A = "", B = "";

            int id = 1;
            while(C[id] == 0) id++;
            A += (char)(id + '0');
            C[id]--;

            while(C[id] == 0) id++;
            B += (char)(id + '0');
            C[id]--;

            id = 0;
            int turn = 0;
            while(id < 10){
                while(id < 10 && C[id] == 0) id++;
                if(id == 10) break;
                if(turn == 0) A += (char)(id + '0');
                else B += (char)(id + '0');
                turn ^= 1;
                C[id]--;
            }
            bw.write((Integer.parseInt(A) + Integer.parseInt(B)) + "\n");
            
            nextLine();
            N = nextInt();
        }

		// ready();
		// solve();
	
		bwEnd();
		
	}
	
	// static void ready() throws Exception{
		


	// }
	
	// static void solve() throws Exception{

        

	// }
	
}
