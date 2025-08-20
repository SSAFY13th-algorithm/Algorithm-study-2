import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class boj18222 {
    // 입출력을 위한 객체들
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static long k;    // 찾고자 하는 위치

    public static void main(String[] args) throws IOException {
        k = Long.parseLong(br.readLine());    // 위치 k 입력
        int answer = function(k);             // k번째 위치의 값 계산

        // 결과 출력 및 스트림 닫기
        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // k번째 위치의 값을 재귀적으로 계산하는 함수
    private static int function(long k) {
        // 기저 사례: k가 1이면 0 반환
        if (k == 1) {
            return 0;
        }

        // k보다 작은 가장 큰 2의 거듭제곱수 찾기
        long len = 1;
        while (len * 2 < k) {
            len *= 2;
        }

        // 문자열의 앞부분과 뒷부분이 반전된 관계이므로
        // k에서 len을 뺀 위치의 값을 반전시켜 반환
        if (function(k - len) == 0) {
            return 1;
        }
        else {
            return 0;
        }
    }
}