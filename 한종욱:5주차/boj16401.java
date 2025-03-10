import java.io.*;
import java.util.StringTokenizer;

public class boj16401 {
    // 입출력을 위한 BufferedReader와 BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // 과자의 길이를 저장할 배열과 필요한 조각 수(m), 과자의 개수(n)
    private static int[] snacks;
    private static int m, n;

    public static void main(String[] args) throws IOException{
        init();  // 입력 받기
        int answer = binarySearch();  // 이진 탐색으로 최대 과자 길이 찾기

        // 결과 출력
        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // 입력을 받는 초기화 함수
    private static void init() throws IOException{
        StringTokenizer st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());  // 필요한 과자 조각의 수
        n = Integer.parseInt(st.nextToken());  // 과자의 개수

        snacks = new int[n];  // 과자 길이를 저장할 배열 초기화
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            snacks[i] = Integer.parseInt(st.nextToken());  // 각 과자의 길이 입력
        }
    }

    // 이진 탐색으로 가능한 최대 과자 길이를 찾는 함수
    private static int binarySearch() {
        int left = 1;  // 최소 길이
        int right = 1000000000;  // 최대 길이 (10^9)
        int result = 0;  // 결과값 저장

        while (left <= right) {
            int mid = left + (right - left) / 2;  // 중간값 계산 (오버플로우 방지를 위해 이렇게 계산)

            if (canDistribute(mid)) {  // mid 길이로 m개 이상의 조각을 만들 수 있는지 확인
                result = mid;  // 가능하다면 결과 갱신
                left = mid + 1;  // 더 큰 길이 탐색
            } else {
                right = mid - 1;  // 불가능하다면 더 작은 길이 탐색
            }
        }
        return result;
    }

    // 주어진 길이(length)로 m개 이상의 과자 조각을 만들 수 있는지 확인하는 함수
    private static boolean canDistribute(int length) {
        int count = 0;  // 만들 수 있는 과자 조각의 수
        for (int snack : snacks) {
            count += snack / length;  // 각 과자를 length 길이로 나눴을 때 나오는 조각의 수 더하기
            if (count >= m) return true;  // m개 이상의 조각을 만들 수 있다면 true 반환
        }
        return false;  // m개 이상의 조각을 만들 수 없다면 false 반환
    }
}