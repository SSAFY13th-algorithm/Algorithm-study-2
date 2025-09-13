import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class boj2696 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // 두 개의 우선순위 큐를 사용하여 중앙값을 효율적으로 구함
    // low: 작은 값들을 저장하는 최대 힙 (큰 값이 우선순위가 높음)
    // high: 큰 값들을 저장하는 최소 힙 (작은 값이 우선순위가 높음)
    private static PriorityQueue<Integer> low, high;
    private static int m; // 수열의 길이

    public static void main(String[] args) throws IOException {
        int t = Integer.parseInt(br.readLine()); // 테스트 케이스 개수
        while (t-- > 0) {
            init(); // 각 테스트 케이스 처리
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    private static void init() throws IOException {
        m = Integer.parseInt(br.readLine()); // 수열의 길이 입력

        // 우선순위 큐 초기화
        // low: 최대 힙 (내림차순 정렬, 가장 큰 값이 맨 앞)
        low = new PriorityQueue<>((o1, o2) -> Integer.compare(o2, o1));
        // high: 최소 힙 (오름차순 정렬, 가장 작은 값이 맨 앞)
        high = new PriorityQueue<>();

        int row = m / 10 + 1; // 입력이 한 줄에 최대 10개씩 주어지므로 필요한 줄 수 계산
        int count = 0; // 현재까지 처리한 원소의 개수
        boolean flag = true; // 홀수 번째 원소인지 확인하는 플래그

        // 출력할 중앙값의 개수 (홀수 번째 원소들의 개수)
        sb.append(m / 2 + 1).append("\n");

        // 각 행을 처리
        while (row-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            // 현재 행의 모든 숫자 처리
            while (st.hasMoreTokens()) {
                int n = Integer.parseInt(st.nextToken());

                // 새로운 숫자를 적절한 힙에 삽입
                // 1. low가 비어있거나 low의 최댓값보다 작으면 low에 삽입
                if (low.isEmpty() || low.peek() > n) {
                    low.add(n);
                }
                // 2. high가 비어있거나 high의 최솟값보다 크면 high에 삽입
                else if (high.isEmpty() || high.peek() < n) {
                    high.add(n);
                }
                // 3. low의 최댓값과 high의 최솟값 사이에 있으면 low에 삽입
                else if (low.peek() <= n && n <= high.peek()) {
                    low.add(n);
                }

                // 두 힙의 크기 균형 맞추기
                // low의 크기가 high보다 2개 이상 크면 low에서 high로 이동
                if (low.size() == high.size() + 2) {
                    high.add(low.poll());
                }
                // low의 크기가 high보다 작으면 high에서 low로 이동
                else if (low.size() < high.size()) {
                    low.add(high.poll());
                }

                // 홀수 번째 원소일 때만 중앙값을 출력
                if (flag) {
                    sb.append(low.peek()); // 중앙값은 항상 low의 최댓값
                    count++;
                    // 10개마다 줄바꿈, 아니면 공백
                    if (count % 10 == 0) sb.append("\n");
                    else sb.append(" ");
                    flag = false; // 다음은 짝수 번째
                } else {
                    flag = true; // 다음은 홀수 번째
                }
            }
        }
        sb.append("\n"); // 각 테스트 케이스 끝에 줄바꿈
    }
}