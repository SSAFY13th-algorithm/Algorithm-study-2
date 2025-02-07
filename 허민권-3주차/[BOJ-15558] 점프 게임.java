import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

public class Main {
    static int N;  // 지도 칸의 개수
    static int k;  // 반대편 줄로 점프할 때 이동하는 칸 수
    static int[] left;  // 왼쪽 줄의 안전한 칸(1)과 위험한 칸(0)을 나타내는 배열
    static int[] right; // 오른쪽 줄의 안전한 칸(1)과 위험한 칸(0)을 나타내는 배열
    static int[] leftCheck;  // 왼쪽 줄에서 특정 칸에 도달할 수 있는 최소 시간 저장
    static int[] rightCheck; // 오른쪽 줄에서 특정 칸에 도달할 수 있는 최소 시간 저장
    static boolean solved = false; // 게임을 클리어할 수 있는지 여부를 저장하는 변수

    /**
     * 현재 위치가 이동 가능한지 확인하는 함수
     * @param cur 현재 위치
     * @param line 현재 줄 ('l' - 왼쪽 줄, 'r' - 오른쪽 줄)
     * @param count 현재 경과 시간(1초 단위)
     * @return 이동 가능 여부 (true: 가능, false: 불가능)
     */
    static boolean isOk(int cur, char line, int count) {
        // 범위를 벗어나면 이동 불가능
        if (cur < 0) return false;

        if (line == 'l') { // 현재 위치가 왼쪽 줄일 경우
            // 해당 칸이 위험한 칸이거나, 이미 최소 시간보다 오래 걸리는 경우, 또는 현재 시간보다 작은 경우 이동 불가능
            if (left[cur] == 0 || leftCheck[cur] <= count || cur < count)
                return false;
        }

        if (line == 'r') { // 현재 위치가 오른쪽 줄일 경우
            if (right[cur] == 0 || rightCheck[cur] <= count || cur < count)
                return false;
        }

        return true;
    }

    /**
     * DFS(깊이 우선 탐색)를 사용하여 가능한 모든 이동을 시도하는 함수
     * @param cur 현재 위치
     * @param line 현재 줄 ('l' - 왼쪽 줄, 'r' - 오른쪽 줄)
     * @param count 현재까지 경과한 시간(1초 단위)
     */
    static void go(int cur, char line, int count) {
        // 이미 게임을 클리어한 경우 더 이상 탐색하지 않음
        if (solved) return;

        // 현재 위치가 N번 칸을 넘어가면 게임 클리어
        if (cur + 1 >= N || cur + k >= N) {
            solved = true;
            return;
        }

        if (line == 'l') { // 현재 왼쪽 줄에 있을 경우
            // 오른쪽으로 한 칸 이동
            if (isOk(cur + 1, 'l', count + 1)) {
                leftCheck[cur + 1] = count + 1;
                go(cur + 1, 'l', count + 1);
            }
            // 왼쪽으로 한 칸 이동
            if (isOk(cur - 1, 'l', count + 1)) {
                leftCheck[cur - 1] = count + 1;
                go(cur - 1, 'l', count + 1);
            }
            // 반대편(오른쪽 줄)으로 k칸 점프
            if (isOk(cur + k, 'r', count + 1)) {
                rightCheck[cur + k] = count + 1;
                go(cur + k, 'r', count + 1);
            }
        } else { // 현재 오른쪽 줄에 있을 경우
            // 오른쪽으로 한 칸 이동
            if (isOk(cur + 1, 'r', count + 1)) {
                rightCheck[cur + 1] = count + 1;
                go(cur + 1, 'r', count + 1);
            }
            // 왼쪽으로 한 칸 이동
            if (isOk(cur - 1, 'r', count + 1)) {
                rightCheck[cur - 1] = count + 1;
                go(cur - 1, 'r', count + 1);
            }
            // 반대편(왼쪽 줄)으로 k칸 점프
            if (isOk(cur + k, 'l', count + 1)) {
                leftCheck[cur + k] = count + 1;
                go(cur + k, 'l', count + 1);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // 입력을 빠르게 받기 위한 BufferedReader 사용
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // N과 k 값 입력받기
        N = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        // 왼쪽 줄과 오른쪽 줄의 정보를 0과 1로 변환하여 배열에 저장
        left = br.readLine().chars().map((c) -> c - '0').toArray();
        right = br.readLine().chars().map((c) -> c - '0').toArray();

        // 방문 시간을 저장할 배열을 초기화 (매우 큰 값으로 설정하여 최소 시간을 구할 수 있도록 함)
        leftCheck = IntStream.generate(() -> 987654321).limit(N).toArray();
        rightCheck = IntStream.generate(() -> 987654321).limit(N).toArray();

        // 초기 시작 위치에서 DFS 탐색 시작
        go(0, 'l', 0);

        // 게임 클리어 여부 출력 (1: 클리어 가능, 0: 클리어 불가능)
        System.out.println(solved ? 1 : 0);
    }
}
