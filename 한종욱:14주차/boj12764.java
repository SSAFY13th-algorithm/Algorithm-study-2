import java.io.*;
import java.util.*;

public class boj12764 {
    // 입출력을 위한 객체 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // 현재 사용 중인 컴퓨터를 관리하는 우선순위 큐 (종료 시간 기준 정렬)
    // int[0]: 종료 시간, int[1]: 컴퓨터 번호
    private static PriorityQueue<int[]> using;

    // 비어있는 컴퓨터 번호를 관리하는 우선순위 큐 (번호가 작은 순)
    private static PriorityQueue<Integer> blank;

    // 각 병사의 컴퓨터 사용 시간 (시작, 종료)
    private static int[][] times;

    // 각 컴퓨터별 사용 횟수를 저장하는 배열
    private static int[] computers;

    // 병사의 수
    private static int n;

    public static void main(String[] args) throws IOException {
        // 입력 처리 및 문제 해결
        init();

        // 결과 출력
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력을 처리하고 문제를 해결하는 메소드
     */
    private static void init() throws IOException {
        // 병사의 수 입력
        n = Integer.parseInt(br.readLine());

        // 우선순위 큐 초기화
        // using: 종료 시간이 빠른 순서대로 정렬
        using = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[0], o2[0]));
        // blank: 비어있는 컴퓨터 번호를 작은 순서대로 정렬
        blank = new PriorityQueue<>();

        // 각 컴퓨터별 사용 횟수 배열 초기화 (최대 10만 명)
        computers = new int[100001];

        // 각 병사의 컴퓨터 사용 시간 배열 초기화
        times = new int[n][2];

        // 필요한 최소 컴퓨터 수를 저장할 변수
        int computer = 0;

        // 병사들의 컴퓨터 사용 시간 입력
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken()); // 시작 시간
            int end = Integer.parseInt(st.nextToken());   // 종료 시간
            times[i][0] = start;
            times[i][1] = end;
        }

        // 시작 시간 기준으로 오름차순 정렬
        Arrays.sort(times, (o1, o2) -> Integer.compare(o1[0], o2[0]));

        // 병사들의 컴퓨터 사용 처리
        for (int[] time : times) {
            // 현재 병사의 시작 시간 이전에 사용이 끝난 컴퓨터들을 비워줌
            while (!using.isEmpty() && time[0] > using.peek()[0]) {
                int[] temp = using.poll();
                blank.add(temp[1]); // 비어있는 컴퓨터 목록에 추가
            }

            // 비어있는 컴퓨터가 없는 경우 (새 컴퓨터 할당 필요)
            if (blank.isEmpty()) {
                computer++; // 필요한 컴퓨터 수 증가
                using.add(new int[] {time[1], computer}); // 종료 시간과 컴퓨터 번호 저장
                computers[computer]++; // 해당 컴퓨터 사용 횟수 증가
            }
            // 비어있는 컴퓨터가 있는 경우 (재사용)
            else {
                int temp = blank.poll(); // 가장 번호가 작은 빈 컴퓨터 선택
                using.add(new int[] {time[1], temp}); // 종료 시간과 컴퓨터 번호 저장
                computers[temp]++; // 해당 컴퓨터 사용 횟수 증가
            }
        }

        // 결과 출력: 필요한 최소 컴퓨터 수
        sb.append(computer).append("\n");

        // 각 컴퓨터별 사용 횟수 출력
        for (int i = 1; i <= computer; i++) {
            sb.append(computers[i]).append(" ");
        }

        // 마지막 공백 제거
        sb.setLength(sb.length() - 1);
    }
}