import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class boj12101 {
    // 입출력을 위한 버퍼 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    // 결과 문자열을 만들기 위한 StringBuilder
    private static final StringBuilder sb = new StringBuilder();

    // 현재까지의 수열을 저장할 ArrayList
    private static List<Integer> list = new ArrayList<>();

    // n: 만들어야 할 합, k: 찾아야 할 k번째 수열
    private static int n, k;
    // 현재까지 찾은 수열의 개수
    private static int totalCount = 0;
    // k번째 수열을 찾았는지 표시하는 플래그
    private static boolean flag = false;

    public static void main(String[] args) throws IOException {
        // 입력값 처리
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());  // 목표 합 n
        k = Integer.parseInt(st.nextToken());  // 찾을 순서 k

        // DFS 시작 (초기 합은 0)
        dfs(0);

        // k번째 수열을 찾지 못한 경우
        if (!flag) {
            sb.append("-1");
        }

        // 결과 출력 및 버퍼 정리
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * DFS를 통해 1, 2, 3의 합으로 n을 만드는 모든 경우를 탐색
     * @param sum 현재까지의 합계
     */
    private static void dfs(int sum) {
        // k번째 수열을 이미 찾았거나 현재 합이 n을 초과한 경우 탐색 중단(가지치기)
        if (flag || sum > n) return;

        // 현재 합이 목표값 n과 같은 경우
        if (sum == n) {
            totalCount++;  // 찾은 수열 개수 증가

            // 현재 수열이 k번째인 경우
            if (totalCount == k) {
                // 현재 수열을 문자열로 변환
                for (int element : list) {
                    sb.append(element).append("+");
                }
                sb.setLength(sb.length()-1);  // 마지막 + 기호 제거
                sb.append("\n");
                flag = true;  // k번째 수열 찾음을 표시
            }
            return;
        }

        // 1, 2, 3을 각각 시도
        for (int i = 1; i < 4; i++) {
            list.add(i);  // 현재 숫자 추가
            dfs(sum + i);  // 다음 숫자 탐색 (재귀 호출)
            list.remove(list.size()-1);  // 백트래킹 (현재 숫자 제거)
        }
    }
}