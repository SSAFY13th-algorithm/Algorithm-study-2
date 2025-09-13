import java.io.*;
import java.util.*;

public class Main {
    private static int n = 4;
    private static int[][] d = {{-1,0},{-1,-1},{0,-1},{1,-1},{1,0},{1,1},{0,1},{-1,1}};
    private static Fish[][] map;
    private static List<Fish> fishes;
    private static int max;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        map = new Fish[n][n];
        fishes = new ArrayList<>();
        for(int i=0; i<n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j=0; j<n; j++) {
                int num = Integer.parseInt(st.nextToken());
                int dir = Integer.parseInt(st.nextToken())-1;

                Fish fish = new Fish(i, j, num, dir, true);
                map[i][j] = fish;
                fishes.add(fish);
            }
        }

        max = map[0][0].num;

        int[] shark = {0, 0, map[0][0].die()};
        dfs(shark, map[0][0].num);

        System.out.println(max);
    }

    private static void dfs(int[] shark, int current) {
        // 물고기 상태 저장 (깊은 복사)
        Fish[][] tempMap = new Fish[4][4];
        List<Fish> tempFishes = new ArrayList<>();
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                Fish newFish = new Fish(i, j, map[i][j].num, map[i][j].dir, map[i][j].alive);
                tempFishes.add(newFish);
                tempMap[i][j] = newFish;
            }
        }

        // 물고기 이동
        Collections.sort(fishes);
        for(Fish fish : fishes) {
            if(!fish.isAlive()) continue;

            for(int i=0; i<d.length; i++) {
                int dir = (fish.dir+i)%8;
                int nx = fish.x + d[dir][0];
                int ny = fish.y + d[dir][1];

                if(nx<0 || nx>=n || ny<0 || ny>=n) continue;
                if(nx==shark[0] && ny==shark[1]) continue;

                fish.dir = dir;
                Fish temp = map[nx][ny];
                map[nx][ny] = fish;
                map[fish.x][fish.y] = temp;
                temp.move(fish.x, fish.y);
                fish.move(nx, ny);
                break;
            }
        }

        // 상어 먹방
        boolean moved = false;
        for(int idx=1; idx<=3; idx++) {
            int nx = shark[0] + d[shark[2]][0] * idx;
            int ny = shark[1] + d[shark[2]][1] * idx;
            if(nx<0 || nx>=n || ny<0 || ny>=n) break;

            if(!map[nx][ny].isAlive()) continue;

            // 물고기를 잡아먹음
            int fishDir = map[nx][ny].die();
            moved = true;

            // 다음 단계로 진행
            dfs(new int[] {nx, ny, fishDir}, current + map[nx][ny].num);

            // 물고기 부활
            map[nx][ny].alive = true;
        }

        // 상어가 더 이상 이동할 곳이 없을 때
        if(!moved) {
            max = Math.max(max, current);
        }

        // 맵 상태 복원
        map = tempMap;
        fishes = tempFishes;
    }

    private static class Fish implements Comparable<Fish>{
        int x, y;
        int num;
        int dir;
        boolean alive;

        public Fish(int x, int y, int num, int dir, boolean alive) {
            this.x = x;
            this.y = y;
            this.num = num;
            this.dir = dir;
            this.alive = alive;
        }

        public int die() {
            this.alive = false;
            return this.dir;
        }

        public boolean isAlive() {
            return this.alive;
        }

        public void move(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Fish o) {
            return Integer.compare(this.num, o.num);
        }

        @Override
        public String toString() {
            String t = "";
            switch(dir) {
                case 0: t = "↑"; break;
                case 1: t = "↖"; break;
                case 2: t = "←"; break;
                case 3: t = "↙"; break;
                case 4: t = "↓"; break;
                case 5: t = "↘"; break;
                case 6: t = "→"; break;
                case 7: t = "↗"; break;
            }
            return this.alive ? "["+this.num+","+t+"]" : "[ X ]";
        }
    }
}
