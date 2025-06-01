import java.io.*;
import java.util.*;

public class boj14867 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // 방문한 상태를 저장하기 위한 Set (중복 방문 방지)
    private static Set<String> visited;

    // a, b: 두 물통의 최대 용량
    // c, d: 목표로 하는 물의 양 (첫 번째 물통에 c, 두 번째 물통에 d)
    private static int a, b, c, d;

    public static void main(String[] args) throws IOException {
        init();
        int answer = BFS();
        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // 입력 데이터 초기화
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        a = Integer.parseInt(st.nextToken()); // 첫 번째 물통의 최대 용량
        b = Integer.parseInt(st.nextToken()); // 두 번째 물통의 최대 용량
        c = Integer.parseInt(st.nextToken()); // 첫 번째 물통의 목표 물의 양
        d = Integer.parseInt(st.nextToken()); // 두 번째 물통의 목표 물의 양
        visited = new HashSet<>();
    }

    // BFS를 이용한 최단 경로 탐색
    private static int BFS() {
        // 큐의 각 원소는 [첫 번째 물통의 물의 양, 두 번째 물통의 물의 양, 현재까지의 조작 횟수]
        Queue<int[]> q = new ArrayDeque<>();

        // 초기 상태: 두 물통 모두 비어있음 (0, 0, 0)
        visited.add("0,0");
        q.add(new int[]{0, 0, 0});

        int result = -1; // 불가능한 경우 -1 반환

        while (!q.isEmpty()) {
            int[] current = q.poll();

            // 목표 상태에 도달했는지 확인
            if (current[0] == c && current[1] == d) {
                result = current[2];
                break;
            }

            // 6가지 가능한 조작을 모두 시도
            for (int i = 0; i < 6; i++) {
                // 다음 상태를 저장할 배열 [물통1의 물의 양, 물통2의 물의 양, 조작 횟수]
                int[] next = new int[3];
                next[0] = current[0]; // 현재 첫 번째 물통의 물의 양
                next[1] = current[1]; // 현재 두 번째 물통의 물의 양
                next[2] = current[2]; // 현재 조작 횟수

                // 각 조작에 따른 다음 상태 계산
                if (i == 0) {
                    // 조작 1: 첫 번째 물통을 가득 채우기
                    next[0] = a;
                } else if (i == 1) {
                    // 조작 2: 두 번째 물통을 가득 채우기
                    next[1] = b;
                } else if (i == 2) {
                    // 조작 3: 첫 번째 물통을 비우기
                    next[0] = 0;
                } else if (i == 3) {
                    // 조작 4: 두 번째 물통을 비우기
                    next[1] = 0;
                } else if (i == 4) {
                    // 조작 5: 첫 번째 물통에서 두 번째 물통으로 물 옮기기
                    int remain = b - next[1]; // 두 번째 물통에 들어갈 수 있는 공간
                    if (remain < current[0]) {
                        // 두 번째 물통이 가득 찰 때까지만 옮길 수 있는 경우
                        next[1] = b;
                        next[0] = current[0] - remain;
                    } else {
                        // 첫 번째 물통의 모든 물을 옮길 수 있는 경우
                        next[1] = current[0] + current[1];
                        next[0] = 0;
                    }
                } else {
                    // 조작 6: 두 번째 물통에서 첫 번째 물통으로 물 옮기기
                    int remain = a - next[0]; // 첫 번째 물통에 들어갈 수 있는 공간
                    if (remain < current[1]) {
                        // 첫 번째 물통이 가득 찰 때까지만 옮길 수 있는 경우
                        next[0] = a;
                        next[1] = current[1] - remain;
                    } else {
                        // 두 번째 물통의 모든 물을 옮길 수 있는 경우
                        next[0] = current[0] + current[1];
                        next[1] = 0;
                    }
                }

                // 이미 방문한 상태인지 확인
                if (visited.contains(next[0] + "," + next[1])) continue;

                // 조작 횟수 증가 및 상태 저장
                next[2]++;
                visited.add(next[0] + "," + next[1]);
                q.add(next);
            }
        }

        return result;
    }
}