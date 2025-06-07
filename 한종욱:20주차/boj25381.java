import java.io.*;
import java.util.*;

public class boj25381 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // 각 문자('A', 'B', 'C')의 위치를 저장하는 스택들
    // 스택을 사용하는 이유: 뒤에서부터 처리하여 패턴을 찾기 위함
    private static Stack<Integer> A, B, C;

    // 제거된 패턴의 개수를 저장하는 변수
    private static int count;

    public static void main(String[] args) throws IOException {
        init();
        bw.write(count + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // 입력 처리 및 패턴 제거 알고리즘 수행
    private static void init() throws IOException {
        char[] input = br.readLine().toCharArray(); // 입력 문자열을 문자 배열로 변환
        count = 0;

        // 각 문자별 스택 초기화
        A = new Stack<>();
        B = new Stack<>();
        C = new Stack<>();

        // 입력 문자열을 순회하면서 각 문자의 위치를 해당 스택에 저장
        for (int i = 0; i < input.length; i++) {
            if (input[i] == 'A') {
                A.push(i); // A의 위치를 A 스택에 저장
            } else if (input[i] == 'B') {
                B.push(i); // B의 위치를 B 스택에 저장
            } else {
                C.push(i); // C의 위치를 C 스택에 저장
            }
        }

        // 첫 번째 단계: "AB" 패턴 제거
        // A 스택이 비어있지 않을 때까지 반복
        while (!A.isEmpty()) {
            // B 스택이 비어있지 않고, 현재 A의 위치가 B의 위치보다 앞에 있는 경우
            // 즉, "AB" 패턴을 발견한 경우
            if (!B.isEmpty() && A.peek() < B.peek()) {
                B.pop(); // B를 제거 (AB 패턴에서 B 부분)
                count++; // 제거된 패턴 개수 증가
            }
            A.pop(); // 현재 A를 제거 (처리 완료)
        }

        // 두 번째 단계: "BC" 패턴 제거
        // 첫 번째 단계에서 일부 B가 제거되었으므로, 남은 B들에 대해 C와의 패턴 확인
        while (!B.isEmpty()) {
            // C 스택이 비어있지 않고, 현재 B의 위치가 C의 위치보다 앞에 있는 경우
            // 즉, "BC" 패턴을 발견한 경우
            if (!C.isEmpty() && B.peek() < C.peek()) {
                C.pop(); // C를 제거 (BC 패턴에서 C 부분)
                count++; // 제거된 패턴 개수 증가
            }
            B.pop(); // 현재 B를 제거 (처리 완료)
        }
    }
}