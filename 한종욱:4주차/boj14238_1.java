import java.io.*;
public class boj14238_1 {
    // 입출력을 위한 BufferedReader와 BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    // 최종 결과를 저장할 StringBuilder
    private static final StringBuilder answer = new StringBuilder();
    // A, B, C 각각의 개수를 저장할 배열
    private static int[] countWorkDay;
    // 전체 문자열의 길이
    private static int count = 0;
    // 유효한 답을 찾았는지 확인하는 플래그
    private static boolean flag = false;

    public static void main(String[] args) throws IOException {
        // 초기 입력 처리
        init();
        // DFS를 위한 StringBuilder 생성
        StringBuilder sb = new StringBuilder();
        // DFS 수행
        dfs(sb);
        // 유효한 답이 없는 경우 -1 출력
        if (answer.length() == 0) {
            answer.append("-1");
        }
        // 결과 출력 및 스트림 닫기
        bw.write(answer.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    // 초기 입력을 처리하는 메소드
    private static void init() throws IOException {
        String input = br.readLine();
        // A, B, C의 개수를 저장할 배열 초기화
        countWorkDay = new int[3];
        // 입력된 문자열을 순회하며 각 문자의 개수 카운트
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == 'A') countWorkDay[0]++;
            else if (c == 'B') countWorkDay[1]++;
            else countWorkDay[2]++;
            count++;
        }
    }

    // DFS로 가능한 문자열을 만드는 메소드
    private static void dfs(StringBuilder sb) {
        // 이미 답을 찾은 경우 종료
        if (flag) return;
        // 문자열이 완성된 경우
        if (sb.length() == count) {
            answer.append(sb.toString());
            flag = true;
            return;
        }

        int len = sb.length();
        // A는 제약 없이 추가 가능
        if (countWorkDay[0] > 0) {
            sb.append('A');
            countWorkDay[0]--;
            dfs(sb);
            countWorkDay[0]++;
            sb.deleteCharAt(len);
        }
        // B는 이전 문자가 B가 아닌 경우에만 추가 가능
        if (countWorkDay[1] > 0 && (len == 0 || sb.charAt(len - 1) != 'B')) {
            sb.append('B');
            countWorkDay[1]--;
            dfs(sb);
            countWorkDay[1]++;
            sb.deleteCharAt(len);
        }
        // C는 이전 문자와 그 이전 문자가 C가 아닌 경우에만 추가 가능
        if (countWorkDay[2] > 0 &&
            (len == 0 || sb.charAt(len - 1) != 'C') &&
            (len <= 1 || sb.charAt(len - 2) != 'C')) {
            sb.append('C');
            countWorkDay[2]--;
            dfs(sb);
            countWorkDay[2]++;
            sb.deleteCharAt(len);
        }
    }
}