package study11week;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Main20055_컨베이어벨트 {
	static class Belt {
		int index, durability;
		boolean isRobot;
		Belt prev;
		Belt next;
		
		public Belt(int index, int durability) {
			this.index = index;
			this.durability = durability;
			this.isRobot = false; // false면 올릴 수 있다.
		}
	}
	
	static class Robot {
		int index;

		public Robot(int index) {
			this.index = index;
		}
	}

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	static int N, K;
	static Belt[] belt;
	static int start, end;

	public static void main(String[] args) throws IOException {
		init();
		solve();
	}

	static void solve() {
		int k = 0;
		
		// 벨트의 번호를 저장.
		List<Robot> robot = new LinkedList<>();
		int step = 0;
		
		while (k < K) {
			// 로봇을 이동처리 할 때, 내리는 위치에 이동하고 바로 배열에서 삭제처리하면 꼬이기 때문에 시작할 때 한번 확인한다.
			if (belt[end].isRobot) {
				belt[end].isRobot = false;
				robot.remove(0);
			}
			
			start = belt[start].prev.index;
			end = belt[end].prev.index;
			
			Belt start_belt = belt[start];
			Belt end_belt = belt[end];
			
			// end의 로봇을 뺀다. 
			if (end_belt.isRobot) {
				end_belt.isRobot = false;
				// 무조건 robot 리스트의 맨 처음이 end에 있는 로봇이다.
				robot.remove(0);
			}
			
			//로봇 이동처리
			for (Robot b: robot) {
				Belt next_belt = belt[b.index].next;
				
				if (!next_belt.isRobot && next_belt.durability > 0) {
					belt[b.index].isRobot = false;
					next_belt.isRobot = true;
					next_belt.durability--;
					if (next_belt.durability == 0)
						k++;
					b.index = next_belt.index;
				}
			}
			
			// 로봇 추가 처리
			if (start_belt.durability > 0) {
				start_belt.durability--;
				if (start_belt.durability == 0)
					k++;
				start_belt.isRobot = true;
				robot.add(new Robot(start_belt.index));
			}
			step++;
		}
		System.out.println(step);
	}

	static void init() throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		belt = new Belt[N * 2 + 1];
		start = 1;
		end = N;

		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= N * 2; i++) {
			int durability = Integer.parseInt(st.nextToken()); 
			belt[i] = new Belt(i, durability);
		}
		
		belt[1].prev = belt[N * 2];
		belt[1].next = belt[2];
		
		belt[N * 2].prev = belt[N * 2 - 1];
		belt[N * 2].next = belt[1];
		
		for (int i = 2; i < N * 2; i++) {
			belt[i].prev = belt[i - 1];
			belt[i].next = belt[i + 1];
		}
	}

}
