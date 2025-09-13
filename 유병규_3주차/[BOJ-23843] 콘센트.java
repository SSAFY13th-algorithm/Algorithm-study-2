import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n = 전자기기 개수, m = 콘센트 개수
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // times = 전자기기별 충전시간
        int[] times = new int[n];
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
            times[i] = Integer.parseInt(st.nextToken());
        }

        // times 오름차순 정렬(int[]은 오름차순만 가능)
        Arrays.sort(times);

        // 우선순위큐에 콘센트 개수만큼 0(=초기값) 삽입
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for(int i=0; i<m; i++) {
            priorityQueue.offer(0);
        }

        // times를 오름차순으로 정렬했기에 가장 큰 값부터 넣기 위해 뒤에서 부터 반복
        for(int i=n-1; i>=0; i--) {
            int number = priorityQueue.poll();
            priorityQueue.offer(number+times[i]);
        }

        // 제일 큰 값을 얻기 위해 1개 빼고 전부 제거
        while(priorityQueue.size() > 1) {
            priorityQueue.poll();
        }

        // 마지막 남은 거(제일 큰 값) 출력
        System.out.println(priorityQueue.poll());
    }
}