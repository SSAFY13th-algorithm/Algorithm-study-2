import java.io.*;
import java.util.*;

public class Main {
    private static int[][] fd = {{0,-1},{-1,-1},{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1}};
    private static int[][] sd = {{-1,0},{0,-1},{1,0},{0,1}};
    private static int max;
    private static int[][] map;
    private static Node[] path;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = 4;

        map = new int[n][n];
        int[][] smallMap = new int[n][n];

        StringTokenizer st = new StringTokenizer(br.readLine());
        int m = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());

        Set<Fish> fishes = new HashSet<>();
        for(int i=0; i<m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken())-1;
            int y = Integer.parseInt(st.nextToken())-1;
            int dir = Integer.parseInt(st.nextToken())-1;

            fishes.add(new Fish(x, y, dir));
            map[x][y]++;
        }

        st = new StringTokenizer(br.readLine());
        int x = Integer.parseInt(st.nextToken())-1;
        int y = Integer.parseInt(st.nextToken())-1;
        int[] shark = {x, y};

        //풀이 시작
        int time = 0;
        while(time < s) {
            //물고기 냄새 시간 증가
            for(int i=0; i<4; i++) {
                for(int j=0; j<4; j++) {
                    if(smallMap[i][j] > 0) smallMap[i][j]++;
                }
            }

            //기존 물고기 저장
            Queue<Fish> q = new LinkedList<>();

            //물고기 이동
            for(Fish fish : fishes) {
                q.add(new Fish(fish.x, fish.y, fish.dir));

                for(int i=0; i<fd.length; i++) {
                    int idx = (fish.dir-i) < 0 ? fish.dir-i+8 : fish.dir-i;
                    int nx = fish.x + fd[idx][0];
                    int ny = fish.y + fd[idx][1];

                    if(nx<0 || nx>=n || ny<0 || ny>=4 || smallMap[nx][ny] > 0) continue;
                    if(nx==shark[0] && ny==shark[1]) continue;

                    map[fish.x][fish.y]--;
                    map[nx][ny]++;
                    fish.x = nx;
                    fish.y = ny;
                    fish.dir = idx;
                    break;
                }

            }

            //상어 이동
            max = -1;
            path = new Node[3];
            dfs(0, shark[0], shark[1], 0, new Node(shark[0], shark[1], null));
            List<Fish> remove = new ArrayList<>();
            for(int i=0; i<3; i++) {
                Node node = path[i];
                if(map[node.x][node.y] == 0) continue;
                map[node.x][node.y] = 0;
                for(Fish fish : fishes) {
                    if(fish.x == node.x && fish.y == node.y) remove.add(fish);
                }
                smallMap[node.x][node.y] = 1;
            }
            fishes.removeAll(remove);
            shark[0] = path[2].x;
            shark[1] = path[2].y;

            //물고기 냄새 제거
            for(int i=0; i<n; i++) {
                for(int j=0; j<n; j++) {
                    if(smallMap[i][j] > 2) smallMap[i][j] = 0;
                }
            }

            //물고기 복사
            while(!q.isEmpty()) {
                Fish fish = q.poll();
                fishes.add(fish);
                map[fish.x][fish.y]++;
            }

            time++;
        }

        int count = 0;
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                count += map[i][j];
            }
        }

        System.out.println(count);
    }

    private static void dfs(int dept, int x, int y, int count, Node node) {
        if(dept == 3) {
            if(count <= max) return;
            max = count;
            Node current = node;
            for(int i=2; i>=0; i--) {
                path[i] = current;
                current = current.pre;
            }
            return;
        }

        for(int i=0; i<sd.length; i++) {
            int nx = x + sd[i][0];
            int ny = y + sd[i][1];

            if(nx<0 || nx>=4 || ny<0 || ny>=4) continue;
            int nCount = count+map[nx][ny];
            Node temp = node;
            while(temp.pre != null) {
                if(temp.x == nx && temp.y == ny) {
                    nCount = count;
                    break;
                }
                temp = temp.pre;
            }
            dfs(dept+1, nx, ny, nCount, new Node(nx, ny, node));
        }
    }

    private static class Fish{
        int x;
        int y;
        int dir;

        public Fish(int x, int y, int dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
    }

    private static class Node{
        int x;
        int y;
        Node pre;

        public Node(int x, int y, Node pre) {
            this.x = x;
            this.y = y;
            this.pre = pre;
        }
    }
}
