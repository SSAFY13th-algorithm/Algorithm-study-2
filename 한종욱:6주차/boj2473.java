import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class boj2473 {
    // 입출력을 위한 BufferedReader, BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static long[] arr;     // 입력받은 수열을 저장하는 배열
    private static long[] answer;   // 결과로 선택된 세 수를 저장하는 배열
    private static int n;          // 수열의 길이
    private static long min;       // 세 수의 합의 최소 절대값

    public static void main(String[] args) throws IOException {
        init();      // 초기 데이터 설정
        solution();  // 문제 해결

        // 결과 출력
        bw.write(answer[0] + " " + answer[1] + " " + answer[2] + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // 초기 데이터를 설정하는 메소드
    private static void init() throws IOException {
        n = Integer.parseInt(br.readLine());
        min = (long) (3e9 + 1);    // 최대 가능한 값보다 큰 값으로 초기화

        arr = new long[n];
        answer = new long[3];

        // 수열 입력 받기
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Long.parseLong(st.nextToken());
        }

        Arrays.sort(arr);  // 이진 탐색을 위한 정렬
    }

    // 세 수의 합이 0에 가장 가까운 조합을 찾는 메소드
    private static void solution() {
        // 첫 번째 수를 고정
        for (int i = 0; i < n - 2; i++) {
            // 세 번째 수를 고정 (뒤에서부터)
            for (int j = n - 1; j > i + 1; j--) {
                int left = i + 1;  // 두 번째 수의 왼쪽 범위
                int right = j - 1; // 두 번째 수의 오른쪽 범위

                // 이진 탐색으로 두 번째 수 찾기
                while (left <= right) {
                    int mid = left + (right - left) / 2;

                    long sum = arr[i] + arr[mid] + arr[j];

                    // 현재까지의 최소값보다 절대값이 작으면 정답 갱신
                    if (min > Math.abs(sum)) {
                        answer[0] = arr[i];
                        answer[1] = arr[mid];
                        answer[2] = arr[j];
                        min = Math.abs(sum);
                    }

                    // 합이 0이면 더 이상 찾을 필요 없음
                    if (sum == 0) break;
                        // 합이 양수면 더 작은 수를 선택해야 함
                    else if (sum > 0) {
                        right = mid - 1;
                    }
                    // 합이 음수면 더 큰 수를 선택해야 함
                    else {
                        left = mid + 1;
                    }
                }
            }
        }
    }
}