import java.io.*;
import java.util.*;

public class boj20166 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // 8방향 이동을 위한 방향 배열 (대각선 포함)
    private static final int[] dx = {1, 1, 1, 0, -1, -1, -1, 0};
    private static final int[] dy = {1, 0, -1, 1, 1, 0, -1, -1};

    private static char[][] map;  // 알파벳 격자
    private static String[] strings;  // 찾을 문자열들
    private static Map<String, Integer> stringCount;  // 각 문자열이 등장하는 횟수
    private static Map<String, Integer> memo;  // 이미 방문한 상태를 저장하는 메모이제이션
    private static int n, m, k, maxLen;  // n: 행 크기, m: 열 크기, k: 찾을 문자열 개수, maxLen: 찾을 문자열의 최대 길이

    public static void main(String[] args) throws IOException {
        init();  // 초기 데이터 설정

        // 격자의 모든 위치에서 BFS 시작
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                BFS(i, j, map[i][j] + "");  // 현재 위치의 문자로 시작하는 문자열 탐색
            }
        }

        // 결과 출력
        for (String s : strings) {
            sb.append(stringCount.getOrDefault(s, 0));  // 각 문자열이 등장하는 횟수 출력
            sb.append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    // 초기 데이터를 설정하는 메소드
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        map = new char[n][m];
        strings = new String[k];
        stringCount = new HashMap<>();  // 문자열 등장 횟수 저장 맵
        memo = new HashMap<>();  // 방문 상태 저장 맵

        // 격자 입력
        for (int i = 0; i < n; i++) {
            map[i] = br.readLine().toCharArray();
        }

        // 찾을 문자열 입력
        for (int i = 0; i < k; i++) {
            strings[i] = br.readLine();
            maxLen = Math.max(maxLen, strings[i].length());  // 최대 길이 갱신
        }
    }

    // 특정 위치에서 시작하는 모든 가능한 문자열을 탐색하는 BFS 메소드
    private static void BFS(int x, int y, String str) {
        Queue<Node> q = new ArrayDeque<>();
        stringCount.put(str, stringCount.getOrDefault(str, 0) + 1);  // 시작 문자열 카운트 증가
        q.add(new Node(x, y, str));

        while(!q.isEmpty()) {
            Node current = q.poll();

            // 최대 길이에 도달하면 더 이상 확장하지 않음
            if (current.str.length() == maxLen) continue;

            // 8방향으로 탐색
            for (int i = 0; i < 8; i++) {
                // 토러스 형태의 격자 (경계를 넘어가면 반대쪽으로 나옴)
                int nx = (current.x + dx[i] + n) % n;
                int ny = (current.y + dy[i] + m) % m;

                String next = current.str + map[nx][ny];  // 다음 문자열
                String memoKey = current.x + "," + current.y + "," + next;  // 메모이제이션 키

                // 길이 초과하거나 이미 방문한 상태면 스킵
                if (next.length() == maxLen + 1 || memo.containsKey(next)) continue;

                stringCount.put(next, stringCount.getOrDefault(next, 0) + 1);  // 새 문자열 카운트 증가
                memo.put(memoKey, 1);  // 방문 상태 저장
                q.add(new Node(nx, ny, next));  // 큐에 추가
            }
        }
    }

    // 탐색 상태를 저장하는 노드 클래스
    static class Node {
        int x;  // 현재 위치 x좌표
        int y;  // 현재 위치 y좌표
        String str;  // 현재까지 만든 문자열

        Node(int x, int y, String str) {
            this.x = x;
            this.y = y;
            this.str = str;
        }
    }
}