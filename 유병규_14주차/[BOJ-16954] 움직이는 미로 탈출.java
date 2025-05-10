import java.io.*;
import java.util.*;

public class Main {
    private static int n = 8;
    private static int[][] d = {{0,0},{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //총 벽 개수
        int wallCount = 0;
        //맵
        char[][] map = new char[n][n];
        for(int i=0; i<n; i++) {
            String line = br.readLine();
            for(int j=0; j<n; j++) {
                map[i][j] = line.charAt(j);
                if(map[i][j] != '#') continue;
                wallCount++;
            }
        }

        Queue<int[]> q = new LinkedList<>();
        //시작 점
        q.add(new int[] {7,0});
        while(true) {
            //만약 큐가 비었다면 갈 수 없음을 의미
            if(q.isEmpty()) {
                System.out.println("0");
                return;
            }
            //벽이 없다면 무조건 갈 수 있음
            if(wallCount == 0) {
                System.out.println("1");
                return;
            }
            //현재 시점에 위치할 수 있는 지점을 기준으로만 계산
            int size = q.size();
            //다음 위치가 중복으로 큐에 들어가는 걸 방지하기 위한 방문처리
            boolean[][] visited = new boolean[n][n];
            for(int i=0; i<size; i++) {
                int[] info = q.poll();
                int x = info[0];
                int y = info[1];
                //도착했다면 종료
                if(x == 0 && y == 7) {
                    System.out.println("1");
                    return;
                }
                //제자리 포함 9방향으로 갈 수 있는 곳 저장
                for(int idx=0; idx<d.length; idx++) {
                    int nx = x + d[idx][0];
                    int ny = y + d[idx][1];
                    if(nx<0 || nx>=n || ny<0 || ny>=n || visited[nx][ny]) continue;
                    if(map[nx][ny] == '#' || (nx-1>=0 && map[nx-1][ny] == '#')) continue;
                    q.offer(new int[] {nx, ny});
                    visited[nx][ny] = true;
                }
            }
            //가장 아래 있는 벽은 사라짐
            for(int j=0; j<n; j++) {
                if(map[n-1][j] != '#') continue;
                map[n-1][j] = '.';
                wallCount--;
            }
            //나머지 벽은 아래로 이동
            for(int i=n-2; i>=0; i--) {
                for(int j=0; j<n; j++) {
                    if(map[i][j] != '#') continue;
                    map[i][j] = '.';
                    map[i+1][j] = '#';
                }
            }
        }
    }
}
