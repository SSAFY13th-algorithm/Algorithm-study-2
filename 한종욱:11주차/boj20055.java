import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 백준 20055번 - 컨베이어 벨트 위의 로봇
 * 문제 요약: N칸의 컨베이어 벨트가 있고, 각 칸은 내구도가 있다.
 * 로봇이 올라가고 내리면서 내구도가 감소하고,
 * 내구도가 0인 칸이 K개 이상이 되면 종료되는 시뮬레이션 문제
 */
public class boj20055 {
    // 입출력을 위한 객체들 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // 컨베이어 벨트를 양방향 큐(덱)로 표현
    private static Deque<Cell> deque;

    // n: 컨베이어 벨트의 한 쪽 길이, k: 종료 조건인 내구도 0인 칸의 개수
    private static int n, k;

    /**
     * 메인 메소드: 프로그램의 진입점
     */
    public static void main(String[] args) throws IOException {
        init();  // 입력 및 초기화

        int answer = 0;  // 단계 수를 저장할 변수
        int count = 0;   // 내구도가 0인 칸의 개수

        // 내구도가 0인 칸이 k개 미만인 동안 반복
        while (count < k) {
            answer++;  // 단계 증가

            // 1. 벨트가 각 칸 위에 있는 로봇과 함께 한 칸 회전
            // pollLast()로 마지막 요소를 빼서 addFirst()로 맨 앞에 추가
            deque.addFirst(deque.pollLast());

            // 2. 로봇 이동 및 내구도 체크, 새로운 내구도 0인 칸의 수 누적
            count += checkNAndMoveRobot();
        }

        // 결과 출력
        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력을 받고 변수를 초기화하는 메소드
     */
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());  // 컨베이어 벨트의 한 쪽 길이
        k = Integer.parseInt(st.nextToken());  // 종료 조건(내구도 0인 칸의 개수)

        deque = new ArrayDeque<>();  // 컨베이어 벨트를 표현할 덱 초기화

        // 각 칸의 내구도 입력 받기
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 2*n; i++) {
            // 내구도 값으로 Cell 객체 생성 후 덱에 추가
            deque.addLast(new Cell(Integer.parseInt(st.nextToken())));
        }
    }

    /**
     * 로봇 이동 규칙에 따라 로봇을 이동시키고 내구도를 체크하는 메소드
     * @return 새롭게 내구도가 0이 된 칸의 개수
     */
    private static int checkNAndMoveRobot() {
        // 덱을 리스트로 변환하여 인덱스로 접근 가능하게 함
        List<Cell> list = new ArrayList<>(deque);
        int count = 0;  // 새롭게 내구도가 0이 된 칸의 개수

        // 내리는 위치(n-1)에 도달한, 로봇을 내림
        list.get(n - 1).onRobot = false;

        // 벨트 위 로봇들을 뒤에서부터 앞으로 이동시킴 (뒤에서부터 확인해야 중복 이동 방지)
        for (int i = n - 2; i > 0; i--) {
            // 현재 칸에 로봇이 있고, 다음 칸의 내구도가 0보다 크며 로봇이 없으면
            if (list.get(i).onRobot &&
                (list.get(i + 1).duration > 0 && !list.get(i + 1).onRobot)) {

                list.get(i + 1).duration--;  // 다음 칸 내구도 감소

                // 내구도가 0이 되었으면 카운트 증가
                if (list.get(i + 1).duration == 0) count++;

                list.get(i + 1).onRobot = true;  // 다음 칸에 로봇 이동
                list.get(i).onRobot = false;     // 현재 칸 로봇 제거
            }
        }

        // 다시 한번 내리는 위치의 로봇 제거 (이동 후 내리는 위치에 도달한 로봇도 처리)
        list.get(n - 1).onRobot = false;

        // 올리는 위치(0)에 로봇 올리기
        if (list.get(0).duration > 0) {
            list.get(0).duration--;  // 올리는 위치 내구도 감소

            // 내구도가 0이 되었으면 카운트 증가
            if (list.get(0).duration == 0) count++;

            list.get(0).onRobot = true;  // 올리는 위치에 로봇 올림
        }

        // 변경된 리스트를 다시 덱으로 변환
        deque = new ArrayDeque<>(list);

        return count;  // 새롭게 내구도가 0이 된 칸의 개수 반환
    }

    /**
     * 컨베이어 벨트의 각 칸을 표현하는 내부 클래스
     */
    static class Cell {
        int duration;     // 칸의 내구도
        boolean onRobot;  // 로봇 존재 여부

        Cell(int duration) {
            this.duration = duration;
            this.onRobot = false;  // 초기에는 로봇이 없음
        }
    }
}