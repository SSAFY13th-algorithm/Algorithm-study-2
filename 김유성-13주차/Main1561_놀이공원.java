package study13week;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main1561_놀이공원 {
  static class Ride implements Comparable<Ride> {
    int num, time, isRide; // isRide: 남은 시간. 0이면 탑승 가능

    public Ride(int num, int time, int isRide) {
      this.num = num;
      this.time = time;
      this.isRide = isRide;
    }

    @Override
    public int compareTo(Ride o) {
      int comp = Integer.compare(this.isRide, o.isRide);
      if (comp == 0)
        return Integer.compare(this.num, o.num);
      return comp;
    }
  }

  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static StringTokenizer st;
  static int N, M, play[];

  public static void main(String[] args) throws IOException {
    init();
    if (N < M) {
      for (int i = 0; i < M; i++) {
        if (--N == 0) {
          System.out.println(i + 1);
          return;
        }
      }
    } else if (N == M) {
      System.out.println(M);
    } else {
      long time = getTime();
      solve(time);
    }
  }

  static void solve(long time) {
    PriorityQueue<Ride> pq = new PriorityQueue<>();

    // 남은 아이들의 수를 구한다. 만약 딱 나누어 떨어지지 않는다면 아직 놀이기구에 탑승한 상태이다.
    for (int i = 0; i < M; i++) {
      N -= time / play[i];
      if (time % play[i] == 0) {
        pq.add(new Ride(i + 1, play[i], 0));
      } else {
        pq.add(new Ride(i + 1, play[i], play[i] - (int) time % play[i]));
        N--;
      }
    }

    Queue<Ride> temp = new LinkedList<>();

    while (true) {
      for (int i = 0; i < M; i++) {
        Ride ride = pq.poll();

        if (ride.isRide == 0) {
          ride.isRide = ride.time - 1;
          N--;
        } else 
          ride.isRide--;
        temp.add(ride);

        if (N == 0) {
          System.out.println(ride.num);
          return;
        }
      }

      while (!temp.isEmpty()) {
        pq.add(temp.poll());
      }
    }

  }

  static long getTime() {
    long start = 1;
    long end = 60_000_000_000L;
    long mid = 0; // 놀이기구를 탔을 때 걸리는 시간
    while (start < end - 1) {
      mid = (start + end) / 2;
      long child = getCount(mid);

      if (child >= N) {
        end = mid - 1;
      } else {
        start = mid;
      }
    }

    return start;
  }

  static long getCount(long time) {
    long ret = 0;
    for (int i = 0; i < M; i++) {
      ret += time / play[i];

      // 딱 나누어 떨어지지 않으면 놀이기구에 탑승한 아이가 있는상태. 그 아이의 인원수도 계산해준다.
      if (time / play[i] > 0 && time % play[i] != 0)
        ret++;
    }

    return ret;
  }

  static void init() throws IOException {
    st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());

    play = new int[M];

    st = new StringTokenizer(br.readLine());
    for (int i = 0; i < M; i++) {
      play[i] = Integer.parseInt(st.nextToken());
    }
  }
}
