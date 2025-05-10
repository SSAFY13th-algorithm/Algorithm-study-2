import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class boj12761 {
    // 입출력을 위한 BufferedReader, BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    private static int[] dx;       // 이동 가능한 방향을 저장하는 배열
    private static boolean[] visited;  // 방문 여부를 체크하는 배열
    private static int n, m;       // n: 시작점, m: 도착점
    private static int a, b;       // a, b: 점프 가능한 거리

    public static void main(String[] args) throws IOException{
        init();  // 초기 데이터 설정
        int answer = bfs(n);  // BFS로 최단 이동 횟수 계산

        // 결과 출력
        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // 초기 데이터를 설정하는 메소드
    private static void init() throws IOException{
        StringTokenizer st = new StringTokenizer(br.readLine());
        a = Integer.parseInt(st.nextToken());  // 첫 번째 점프 거리
        b = Integer.parseInt(st.nextToken());  // 두 번째 점프 거리
        n = Integer.parseInt(st.nextToken());  // 시작점
        m = Integer.parseInt(st.nextToken());  // 도착점

        visited = new boolean[100001];  // 방문 배열 초기화 (문제의 범위가 0~100,000)

        // 이동 가능한 방향 배열 초기화:
        // 한 칸 앞으로, 한 칸 뒤로, a칸 앞으로, a칸 뒤로, b칸 앞으로, b칸 뒤로, a배 앞으로, b배 앞으로
        dx = new int[] {1, -1, a, -a, b, -b, a, b};
    }

    // BFS로 최단 이동 횟수를 계산하는 메소드
    private static int bfs(int start) {
        Queue<int[]> queue = new ArrayDeque<>();
        visited[start] = true;
        queue.add(new int[] {start, 0});  // {현재 위치, 이동 횟수}

        while (!queue.isEmpty()) {
            int[] current = queue.poll();

            // 도착점에 도달했다면 이동 횟수 반환
            if (current[0] == m) return current[1];

            // 8가지 이동 방향에 대해 탐색
            for (int i = 0; i < 8; i++) {
                int next = current[0];

                // 앞뒤 이동 또는 점프
                if (i < 6) next += dx[i];
                    // 순간이동 (곱하기)
                else next *= dx[i];

                // 범위를 벗어나거나 이미 방문한 위치면 스킵
                if (OOB(next) || visited[next]) continue;

                // 방문 처리 후 큐에 추가
                visited[next] = true;
                queue.add(new int[] {next, current[1] + 1});
            }
        }

        return -1;  // 도달할 수 없는 경우 (이 문제에서는 발생하지 않을 것)
    }

    // 범위를 벗어났는지 확인하는 메소드
    private static boolean OOB(int next) {
        return next < 0 || next > 100000;
    }
}