import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

public class Boj14716 {
    // 입출력을 위한 버퍼 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    
    // 8방향 이동을 위한 dx, dy 배열 (오른쪽위부터 시계방향)
    private static int[] dx = {1, 1, 1, 0, -1, -1, -1, 0};
    private static int[] dy = {1, 0, -1, 1, 1, 0, -1, -1};
    
    // 지도의 크기를 저장할 변수
    private static int m, n;
    // 현수막의 정보를 저장할 2차원 배열
    private static int[][] map;
    // 방문 여부를 체크할 2차원 배열
    private static boolean[][] visited;
    // 글자의 개수를 카운트할 변수
    private static int count = 0;
    
    public static void main(String[] args) throws IOException{
        // 지도의 크기 입력 받기
        String[] input = br.readLine().split(" ");
        m = Integer.parseInt(input[0]);  // 세로 크기
        n = Integer.parseInt(input[1]);  // 가로 크기
        
        // 지도와 방문 배열 초기화
        map = new int[m][n];
        visited = new boolean[m][n];
        
        // 지도 정보 입력 받기 (1: 글자, 0: 글자 아님)
        for (int i = 0; i < m; i++) {
            input = br.readLine().split(" ");
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(input[j]);
            }
        }
        
        // 전체 지도를 탐색하며 글자 찾기
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 방문하지 않은 글자(1)를 발견하면 BFS 수행
                if (map[i][j] == 1 && !visited[i][j]) {
                    count++;  // 글자 개수 증가
                    bfs(i, j);  // BFS로 연결된 글자 부분 탐색
                }
            }
        }
        
        // 결과 출력 및 입출력 스트림 닫기
        bw.write(count + "\n");
        bw.flush();
        bw.close();
        br.close();
    }
    
    // BFS로 연결된 글자 부분 탐색하는 메소드
    private static void bfs(int x, int y) {
        Queue<int[]> queue = new LinkedList<>();  // BFS를 위한 큐 생성
        visited[x][y] = true;  // 시작 지점 방문 처리
        queue.add(new int[]{x, y});  // 시작 지점을 큐에 삽입
        
        while (!queue.isEmpty()) {
            int[] current = queue.poll();  // 현재 위치 꺼내기
            
            // 8방향 탐색
            for (int i = 0; i < 8; i++) {
                int nx = current[0] + dx[i];  // 다음 x좌표
                int ny = current[1] + dy[i];  // 다음 y좌표
                
                // 범위를 벗어나거나, 글자가 아니거나, 이미 방문한 경우 스킵
                if (OOB(nx, ny) || map[nx][ny] == 0 || visited[nx][ny]) continue;
                
                visited[nx][ny] = true;  // 방문 처리
                queue.add(new int[]{nx, ny});  // 큐에 새로운 위치 추가
            }
        }
    }
    
    // 좌표가 지도 범위를 벗어나는지 체크하는 메소드
    private static boolean OOB(int nx, int ny) {
        return nx < 0 || nx > m - 1 || ny < 0 || ny > n - 1;
    }
}