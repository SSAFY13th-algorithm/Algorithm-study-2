import java.io.*;
import java.util.*;
public class boj30804 {
    // 입출력을 위한 BufferedReader와 BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // 과일의 개수 n
    private static int n;
    // 과일의 종류를 저장할 배열
    private static int[] fruits;

    public static void main(String[] args) throws IOException {
        setting();    // 입력 받기

        // 최대 길이 계산 및 출력
        sb.append(function());

        // 결과 출력 및 스트림 닫기
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    // 입력을 받아 초기 설정을 하는 메소드
    private static void setting() throws IOException {
        // 과일의 개수 입력
        n = Integer.parseInt(br.readLine());
        fruits = new int[n];

        // 각 과일의 종류 입력
        String[] input = br.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            fruits[i] = Integer.parseInt(input[i]);
        }
    }

    // 최대 길이를 계산하는 메소드 (투 포인터 알고리즘)
    private static int function() {
        int max = 0;        // 최대 길이를 저장할 변수
        int start = 0;      // 시작 포인터
        int end = 0;        // 끝 포인터

        // 각 과일 종류의 개수를 저장할 HashMap
        Map<Integer, Integer> map = new HashMap<>();

        // 끝 포인터가 배열 끝에 도달할 때까지 반복
        while (end < n) {
            // 현재 과일 추가
            map.put(fruits[end], map.getOrDefault(fruits[end], 0) + 1);

            // 과일 종류가 2개를 초과하는 경우
            if (map.size() > 2) {
                // 시작 포인터의 과일 개수 감소
                map.put(fruits[start], map.get(fruits[start])-1);
                // 과일 개수가 0이 되면 맵에서 제거
                if (map.get(fruits[start]) == 0) {
                    map.remove(fruits[start]);
                }
                start++;    // 시작 포인터 이동
            }
            // 현재 구간의 길이와 최대 길이 비교
            max = Math.max(max, end-start+1);
            end++;    // 끝 포인터 이동
        }
        return max;
    }
}