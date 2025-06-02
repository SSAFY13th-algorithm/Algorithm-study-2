import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class boj1477 {
    // 입출력을 위한 BufferedReader, BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int[] restArea;  // 휴게소 위치를 저장하는 배열
    private static int n;
    private static int m;
    private static int max;  // n: 현재 휴게소 개수, m: 추가할 휴게소 개수, l: 고속도로 길이, max: 휴게소 간 최대 거리

    public static void main(String[] args) throws IOException {
        init();  // 초기 데이터 설정
        int answer = binarySearch();  // 이진 탐색으로 최적 거리 찾기
        bw.write(String.valueOf(answer));  // 결과 출력
        bw.flush();
        bw.close();
        br.close();
    }

    // 초기 데이터를 설정하는 메소드
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());  // 현재 휴게소 개수
        m = Integer.parseInt(st.nextToken());  // 추가할 휴게소 개수
        int l = Integer.parseInt(st.nextToken());  // 고속도로 길이
        max = 0;  // 휴게소 간 최대 거리 초기화

        restArea = new int[n + 2];  // 시작점(0)과 끝점(l)을 포함한 배열

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            restArea[i] = Integer.parseInt(st.nextToken());  // 현재 휴게소 위치 입력
        }

        restArea[n] = 0;  // 시작점 추가
        restArea[n + 1] = l;  // 끝점 추가

        Arrays.sort(restArea);  // 위치 순으로 정렬

        // 휴게소 간 최대 거리 계산
        for (int i = 0; i < n + 1; i++) {
            max = Math.max(max, restArea[i + 1] - restArea[i]);
        }
    }

    // 이진 탐색으로 최적 거리를 찾는 메소드
    private static int binarySearch() {
        int start = 1;  // 최소 거리는 1
        int end = max;  // 최대 거리는 현재 휴게소 간 최대 거리
        int result = 0;  // 결과 저장 변수

        while (start <= end) {
            int mid = start + (end - start) / 2;  // 중간값 계산

            if (isPossible(mid)) {  // 현재 거리로 m개 이하의 휴게소를 추가할 수 있는지 확인
                result = mid;  // 가능한 결과 저장
                end = mid - 1;  // 더 작은 거리도 가능한지 확인
            } else {
                start = mid + 1;  // 더 큰 거리로 시도
            }
        }

        return result;  // 최종 결과 반환
    }

    // 주어진 거리로 m개 이하의 휴게소를 추가할 수 있는지 확인하는 메소드
    private static boolean isPossible(int distance) {
        int count = 0;  // 추가해야 할 휴게소 개수

        for (int i = 0; i < n + 1; i++) {
            int dist = restArea[i + 1] - restArea[i];  // 연속된 휴게소 간 거리

            // 필요한 휴게소 개수 계산
            // dist / distance는 해당 구간에 필요한 총 휴게소 수
            // dist % distance == 0인 경우 경계선에 정확히 위치하므로 1개 빼줌
            count += (dist / distance) - (dist % distance == 0 ? 1 : 0);
        }

        return count <= m;  // m개 이하로 추가 가능한지 반환
    }
}