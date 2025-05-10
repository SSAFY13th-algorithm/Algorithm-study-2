import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main2806 {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int N;
	
	static char[] DNA;
	static int[] A;
	static int[] B;
	
	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		DNA = new char[N + 1];
		A = new int[N + 1];
		B = new int[N + 1];
	
		
		// 입력값을 1부터 N까지 배열에 저장
		String input = br.readLine();
		for (int i = 1; i <= input.length(); i++) {
			DNA[i] = input.charAt(i - 1);
		}
		
		// DNA의 처음이 A이면 
		if (DNA[1] == 'A') {
			A[1] = 0;
			B[1] = 1;
		}
		else {// DNA의 처음이 B이면
			A[1] = 1;
			B[0] = 0;
		}
		
		// 모두 A로 만드는 경우와 모두 B로 만드는 경우로 나뉜다.
		for (int i = 2; i <= N; i++) {
			if (DNA[i] == 'A') {
				A[i] = A[i - 1]; // A인 경우에는 이전과 동일하다
				B[i] = Math.min(B[i - 1] + 1, A[i - 1] + 1); //B인 경우에는 이전에 B까지 만들었을 때의 횟수에 현재 A를 B로 만드는 경우(1돌연변이)와 모두 A까지 만들었을 때 A를 모두 B로 바꾸는 경우(2돌연변이) 
			}
			else {
				A[i] = Math.min(A[i - 1] + 1, B[i - 1] + 1); //A인 경우에는 이전에 A까지 만들었을 때의 횟수에 모든 B를 A로 만드는 경우(2돌연변이)와 모두 B까지 말들었을 때 현재 B를 S로 만드는 경우(1돌연변이)
				B[i] = B[i - 1]; // B인 경우에는 이전과 동일하다
			}
		}
		System.out.println(Math.min(A[N],B[N] + 1));
	}
	
	
}
