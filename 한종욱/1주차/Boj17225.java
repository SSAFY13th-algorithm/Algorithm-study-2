import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main {
    // 기본 입출력 버퍼와 데이터 구조 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // 주문을 처리할 우선순위 큐 (시간순 정렬, 동일시간은 파란색 우선)
    private static PriorityQueue<Order> orders = new PriorityQueue<Order>((o1, o2) -> {
        if (o1.time == o2.time) return o1.isBlue ? -1 : 1;
        return o1.time - o2.time;
    });

    // 각 색상별 선물 번호 저장 큐
    private static Queue<Integer> blueQueue = new LinkedList<>();
    private static Queue<Integer> redQueue = new LinkedList<>();

    // 포장시간(a: 파란색, b: 빨간색), n: 주문수
    private static int a, b, n;

    public static void main(String[] args) throws IOException {
        // 기본 정보 입력
        String[] input = br.readLine().split(" ");
        a = Integer.parseInt(input[0]);
        b = Integer.parseInt(input[1]);
        n = Integer.parseInt(input[2]);

        int blueTime = 0;  // 파란색 다음 가능시간
        int redTime = 0;   // 빨간색 다음 가능시간
        int index = 1;     // 선물번호

        // 주문 정보 처리
        for (int i = 0; i < n; i++) {
            input = br.readLine().split(" ");
            int time = Integer.parseInt(input[0]);    // 주문시각
            int count = Integer.parseInt(input[2]);   // 주문개수

            // 색상별 주문처리 (B: 파란색, R: 빨간색)
            if (input[1].equals("B")) {
                int startTime = Math.max(time, blueTime);
                for (int j = 0; j < count; j++) {
                    orders.add(new Order(startTime + j * a, true));
                }
                blueTime = startTime + count * a;
            } else {
                int startTime = Math.max(time, redTime);
                for (int j = 0; j < count; j++) {
                    orders.add(new Order(startTime + j * b, false));
                }
                redTime = startTime + count * b;
            }
        }

        // 선물번호 할당
        while (!orders.isEmpty()) {
            Order current = orders.poll();
            if (current.isBlue) blueQueue.add(index++);
            else redQueue.add(index++);
        }

        // 결과 출력 형식 생성
        sb.append(blueQueue.size()).append("\n");
        while (!blueQueue.isEmpty()) sb.append(blueQueue.poll()).append(" ");
        sb.deleteCharAt(sb.length() - 1).append("\n");

        sb.append(redQueue.size()).append("\n");
        while (!redQueue.isEmpty()) sb.append(redQueue.poll()).append(" ");
        sb.deleteCharAt(sb.length() - 1).append("\n");

        // 결과 출력 및 버퍼 정리
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    // 주문 정보 클래스
    static class Order {
        private int time;      // 포장완료시간
        private boolean isBlue; // 색상구분

        public Order(int time, boolean isBlue) {
            this.time = time;
            this.isBlue = isBlue;
        }
    }
}