import java.io.*;
import java.util.*;

public class boj20166 {
    // 입출력을 위한 BufferedReader, BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // 8방향 이동을 위한 방향 배열 (상, 하, 좌, 우 및 대각선)
    private static final int[] dx = {1, 1, 1, 0, -1, -1, -1, 0};
    private static final int[] dy = {1, 0, -1, 1, 1, 0, -1, -1};

    private static char[][] map;             // 알파벳 격자
    private static String[] strings;         // 찾을 문자열 목록
    private static Map<String, Integer> stringCount;  // 문자열별 발견 횟수
    private static Map<String, Integer> memo;  // 이미 처리한 상태 기록 (메모이제이션)
    private static int n;
    private static int m;
    private static int maxLen;      // 격자 크기, 찾을 문자열 개수, 최대 문자열 길이

    public static void main(String[] args) throws IOException {
        init();  // 초기 설정

        // 격자의 모든 위치에서 BFS 시작
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                BFS(i, j, map[i][j] + "");  // 각 위치의 문자로 시작하는 문자열 생성
            }
        }

        // 결과 출력
        for (String s : strings) {
            sb.append(stringCount.getOrDefault(s, 0));  // 각 문자열의 발견 횟수
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
        n = Integer.parseInt(st.nextToken());  // 격자 행 수
        m = Integer.parseInt(st.nextToken());  // 격자 열 수
        int k = Integer.parseInt(st.nextToken());  // 찾을 문자열 개수

        map = new char[n][m];             // 격자 초기화
        strings = new String[k];          // 찾을 문자열 배열 초기화
        stringCount = new HashMap<>();     // 문자열 발견 횟수 맵 초기화
        memo = new HashMap<>();            // 메모이제이션 맵 초기화

        // 격자 입력
        for (int i = 0; i < n; i++) {
            map[i] = br.readLine().toCharArray();
        }

        // 찾을 문자열 입력
        for (int i = 0; i < k; i++) {
            strings[i] = br.readLine();
            maxLen = Math.max(maxLen, strings[i].length());  // 최대 문자열 길이 갱신
        }
    }

    // BFS로 가능한 모든 문자열 생성
    private static void BFS(int x, int y, String str) {
        Queue<Node> q = new ArrayDeque<>();
        stringCount.put(str, stringCount.getOrDefault(str, 0) + 1);  // 현재 문자열 카운트 증가
        q.add(new Node(x, y, str));  // 시작 노드 큐에 추가

        while(!q.isEmpty()) {
            Node current = q.poll();

            // 최대 길이에 도달하면 더 이상 확장하지 않음
            if (current.str.length() == maxLen) continue;

            // 8방향으로 이동
            for (int i = 0; i < 8; i++) {
                // 토러스 형태로 경계를 넘어가면 반대편으로 이동
                int nx = (current.x + dx[i] + n) % n;
                int ny = (current.y + dy[i] + m) % m;

                // 새 문자열 생성
                String next = current.str + map[nx][ny];
                // 메모이제이션 키 생성 (위치와 문자열 조합)
                String memoKey = current.x + "," + current.y + "," + next;

                // 문자열 길이 초과하거나 이미 처리한 상태면 건너뜀
                if (next.length() == maxLen + 1 || memo.containsKey(next)) continue;

                // 새 문자열 카운트 증가
                stringCount.put(next, stringCount.getOrDefault(next, 0) + 1);
                // 처리한 상태 기록
                memo.put(memoKey, 1);
                // 새 노드 큐에 추가
                q.add(new Node(nx, ny, next));
            }
        }
    }

    // BFS 탐색을 위한 노드 클래스
    static class Node {
        int x;           // 현재 x 좌표
        int y;           // 현재 y 좌표
        String str;      // 현재까지 만든 문자열

        Node(int x, int y, String str) {
            this.x = x;
            this.y = y;
            this.str = str;
        }
    }
}