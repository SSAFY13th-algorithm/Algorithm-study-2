import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 2479번 - 경로 찾기
 * 문제 요약: N개의 이진코드가 주어지고, 시작 코드에서 끝 코드까지
 * 해밍 거리가 1인 코드들만 거쳐서 갈 수 있는 최단 경로를 찾는 문제
 */
public class boj2479 {
    // 입출력을 위한 객체들 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    private static int[] arr;     // 각 코드 번호의 이진값을 정수로 저장하는 배열
    private static boolean[] visited;  // BFS 탐색 시 방문 여부를 체크하는 배열
    private static int n, k, start, end;  // n: 코드 개수, k: 코드 길이, start: 시작 코드 번호, end: 도착 코드 번호

    /**
     * 메인 메소드: 프로그램의 진입점
     */
    public static void main(String[] args) throws IOException {
        init();  // 입력 및 초기화
        String answer = BFS();  // BFS로 경로 탐색
        bw.write(answer + "\n");  // 결과 출력
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력을 받고 변수를 초기화하는 메소드
     */
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());  // 코드 개수
        k = Integer.parseInt(st.nextToken());  // 코드 길이

        arr = new int[n + 1];  // 1번부터 n번까지의 코드를 저장 (인덱스 1부터 시작)
        visited = new boolean[n + 1];  // 방문 여부 체크 배열

        // 각 코드를 입력받아 2진수에서 10진수로 변환하여 저장
        for (int i = 1; i <= n; i++) {
            arr[i] = Integer.parseInt(br.readLine(), 2);  // 2진수 문자열을 10진수 정수로 변환
        }

        // 시작 코드와 도착 코드 번호 입력
        st = new StringTokenizer(br.readLine());
        start = Integer.parseInt(st.nextToken());  // 시작 코드 번호
        end = Integer.parseInt(st.nextToken());    // 도착 코드 번호
    }

    /**
     * BFS를 이용하여 시작 코드에서 도착 코드까지의 경로를 찾는 메소드
     * @return 경로를 공백으로 구분한 문자열, 경로가 없으면 "-1" 반환
     */
    private static String BFS() {
        Queue<Element> q = new ArrayDeque<>();  // BFS를 위한 큐
        visited[start] = true;  // 시작 코드 방문 체크

        // 시작 코드의 값과 경로 문자열을 가진 Element 객체 생성하여 큐에 추가
        Element e = new Element(arr[start], "" + start);
        q.add(e);

        // BFS 탐색 시작
        while(!q.isEmpty()) {
            Element current = q.poll();  // 큐에서 하나 꺼냄

            // 현재 코드가 도착 코드와 같다면 경로 반환
            if (current.num == arr[end]) return current.s;

            // 모든 코드에 대해 검사
            for (int i = 1; i <= n; i++) {
                // 이미 방문했거나, 해밍 거리가 1이 아니면 스킵
                if (visited[i] || !IsHammingDistanceOne(current.num, arr[i])) continue;

                visited[i] = true;  // 방문 체크
                // 새로운 코드와 업데이트된 경로를 큐에 추가
                q.add(new Element(arr[i], current.s + " " + i));
            }
        }

        return "-1";  // 경로를 찾지 못했을 경우
    }

    /**
     * 두 이진 코드 간의 해밍 거리가 1인지 확인하는 메소드
     * 해밍 거리: 같은 길이의 두 이진 코드에서 서로 다른 비트의 개수
     * @param x 첫 번째 코드 (정수 표현)
     * @param y 두 번째 코드 (정수 표현)
     * @return 해밍 거리가 1이면 true, 아니면 false
     */
    public static boolean IsHammingDistanceOne(int x, int y) {
        // XOR 연산으로 서로 다른 비트만 1이 되고, bitCount로 1의 개수(=다른 비트 수)를 계산
        return Integer.bitCount(x ^ y) == 1;
    }

    /**
     * BFS 탐색 시 사용되는 Element 클래스
     * 현재 코드 값과 시작점부터 현재까지의 경로를 저장
     */
    static class Element {
        int num;     // 코드의 정수 값
        String s;    // 경로 문자열 (코드 번호들을 공백으로 구분)

        Element(int num, String s) {
            this.num = num;
            this.s = s;
        }
    }
}