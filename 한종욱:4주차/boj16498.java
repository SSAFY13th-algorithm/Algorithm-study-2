import java.io.*;
import java.util.*;
public class boj16498 {
    // 입출력을 위한 BufferedReader와 BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();
    // 세 개의 배열을 저장할 전역 변수 선언
    private static int[] a;
    private static int[] b;
    private static int[] c;

    public static void main(String[] args) throws IOException {
        // 입력값 설정
        setting();

        // 세 배열에서 최소 차이를 계산
        int min = min(a, b, c);

        // 결과를 StringBuilder에 추가
        sb.append(min).append("\n");

        // 결과 출력 및 스트림 닫기
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    // 입력값을 받아 배열을 초기화하는 메소드
    private static void setting() throws IOException {
        // 첫 번째 줄에서 세 배열의 크기를 입력받음
        String[] input = br.readLine().split(" ");

        // 입력받은 크기로 배열 초기화
        a = new int[Integer.parseInt(input[0])];
        b = new int[Integer.parseInt(input[1])];
        c = new int[Integer.parseInt(input[2])];

        // a 배열의 원소들을 입력받음
        input = br.readLine().split(" ");
        for (int i = 0; i < a.length; i++) {
            a[i] = Integer.parseInt(input[i]);
        }

        // b 배열의 원소들을 입력받음
        input = br.readLine().split(" ");
        for (int i = 0; i < b.length; i++) {
            b[i] = Integer.parseInt(input[i]);
        }

        // c 배열의 원소들을 입력받음
        input = br.readLine().split(" ");
        for (int i = 0; i < c.length; i++) {
            c[i] = Integer.parseInt(input[i]);
        }
    }

    // 세 배열에서 최소 차이를 찾는 메소드
    private static int min(int[] a, int[] b, int[] c) {
        // c 배열을 정렬하여 이진 탐색 준비
        Arrays.sort(c);
        // 결과값을 저장할 변수를 최대값으로 초기화
        int result = Integer.MAX_VALUE;

        // a, b 배열의 모든 조합에 대해 반복
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                // 현재 a[i]와 b[j] 중 최대값과 최소값 계산
                int max = Math.max(a[i], b[j]);
                int min = Math.min(a[i], b[j]);

                // c 배열에서 min값 이상인 첫 번째 위치를 찾음
                int minIndex = Arrays.binarySearch(c, min);
                if (minIndex < 0) {
                    minIndex = -(minIndex + 1);  // min보다 큰 첫 번째 값의 index
                    if (minIndex >= c.length) {  // 그런 값이 없다면
                        minIndex = c.length - 1;  // min보다 작은 값들 중 가장 큰 값의 index
                    }
                }

                // c 배열에서 max값 이하인 마지막 위치를 찾음
                int maxIndex = Arrays.binarySearch(c, max);
                if (maxIndex < 0) {
                    maxIndex = -(maxIndex + 1);  // max보다 작은 마지막 값의 index
                    if (maxIndex < 0) {  // 그런 값이 없다면
                        maxIndex = 0;  // max보다 큰 값들 중 가장 작은 값의 index
                    }
                }

                // 찾은 범위의 전후 값까지 포함하여 최소 차이 계산
                for (int k = Math.max(0, minIndex-1); k <= Math.min(c.length-1, maxIndex+1); k++) {
                    // 현재 조합에서의 최대값과 최소값의 차이 계산
                    int temp = Math.max(max, c[k]) - Math.min(min, c[k]);
                    // 전체 최소값 갱신
                    result = Math.min(result, temp);
                }
            }
        }

        return result;
    }
}