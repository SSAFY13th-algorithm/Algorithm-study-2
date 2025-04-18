package study13week;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main19236_청소년상어 {
  static class Fish implements Comparable<Fish> {
    int h, w, dir, num;

    public Fish(int num, int h, int w, int dir) {
      this.num = num;
      this.h = h;
      this.w = w;
      this.dir = dir;
    }
    
    public Fish(Fish f) {
      this.num = f.num;
      this.h = f.h;
      this.w = f.w;
      this.dir = f.dir;
    }
    
    @Override
    public int compareTo(Fish o) {
      return Integer.compare(this.num, o.num);
    }
  }

  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static StringTokenizer st;
  
  // ↑, ↖, ←, ↙, ↓, ↘, →, ↗ 
  static int move[][] = {{-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}}; 
  static Fish start[][] = new Fish[4][4];
  static int shark[] = new int[3];
  static int max;
  static PriorityQueue<Fish> fishes = new PriorityQueue<>();

  public static void main(String[] args) throws IOException {
    init();
    dfs(shark[0], shark[1], shark[2], max, start);
    System.out.println(max);
  }
  
  
  static void dfs(int h, int w, int dir, int sum, Fish[][] map) {
    // 항상 갱신해도 상관이 없다.
    max = Math.max(max, sum);
    
    // 물고기 PQ에 넣기
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        if (map[i][j] == null) continue;
        fishes.add(map[i][j]);
      }
    }
    
    // 물고기 이동
    moveFish(map, h , w);
    
    
    // 상어 이동에 따른 dfs
    int nh = h, nw = w;
    nh += move[dir][0];
    nw += move[dir][1];
    
    while (nh >= 0 && nh < 4 && nw >= 0 && nw < 4) {
      if (map[nh][nw] != null) {
        Fish temp = map[nh][nw];
        map[nh][nw] = null;
        dfs(nh, nw, temp.dir, sum + temp.num, cloneMap(map));
        map[nh][nw] = temp;
      }
      
      nh += move[dir][0];
      nw += move[dir][1];
    }
  }
  
  static Fish[][] cloneMap(Fish[][] before) {
    Fish[][] ret = new Fish[4][4];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        if (before[i][j] == null) continue;
        ret[i][j] = new Fish(before[i][j]);
      }
    }
    return ret;
  }
  
  static void moveFish(Fish[][] map, int shark_h, int shark_w) {
    while (!fishes.isEmpty()) {
      Fish f = fishes.poll();
      int h = f.h, w = f.w;
      
      int nh, nw, d;
      for (int i = 0; i < 8; i++) {
        d = (f.dir + i) % 8;
        nh = h + move[d][0];
        nw = w + move[d][1];
        
        if (nh >= 0 && nh < 4 && nw >= 0 && nw < 4 && !(nh == shark_h && nw == shark_w)) {
          f.dir = d;
          f.h = nh;
          f.w = nw;
          if (map[nh][nw] == null) { // 빈 칸
            map[nh][nw] = f;
            map[h][w] = null;
          } else { // 물고기가 있는 칸
            map[nh][nw].h = h;
            map[nh][nw].w = w;
            
            map[h][w] = map[nh][nw];
            map[nh][nw] = f;
          }
          break;
        }
      }
    }
  }

  static void init() throws IOException {
    
    for (int i = 0; i < 4; i++) {
      st = new StringTokenizer(br.readLine().trim());
      for (int j = 0; j < 4; j++) {
        int num = Integer.parseInt(st.nextToken());
        int dir = Integer.parseInt(st.nextToken()) - 1;
        
        if (i == 0 && j == 0) {
          shark[2] = dir;// 상어 방향
          max = num;
          continue;
        }
        start[i][j] = new Fish(num, i, j, dir);
      }
    }
  }
}
