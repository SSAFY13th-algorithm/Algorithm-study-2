import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class boj1114 {
    // 입출력을 위한 객체 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // 자를 수 있는 위치를 저장하는 TreeSet (정렬된 상태로 유지, 중복 방지)
    private static TreeSet<Integer> pointCut;

    // l: 통나무의 길이, k: 자를 수 있는 위치의 개수, c: 최대 자를 수 있는 횟수
    private static int l, k, c;

    public static void main(String[] args) throws IOException {
        // 입력 처리 및 초기화
        init();

        // 이분 탐색을 통해 최소 통나무 길이 계산
        int min = binarySearch();

        // 가장 왼쪽 위치를 찾기 위한 처리
        int pos = l; // 처음에는 통나무 끝에서 시작
        int count = 0; // 자른 횟수 카운트

        // 자를 수 있는 최대 횟수만큼 자르기 시도
        while (pos > 0 && count < c) {
            // pos - min 이상의 가장 작은 자를 수 있는 위치 찾기
            Integer point = pointCut.ceiling(pos - min);
            if (point == null) break; // 적절한 위치가 없으면 중단

            // point보다 크거나 같은 모든 자를 위치를 제거 (이미 처리한 부분)
            pointCut.tailSet(point, true).clear();
            pos = point; // 현재 위치 업데이트
            count++; // 자른 횟수 증가
        }

        // 결과 출력: 최소 통나무 길이와 가장 왼쪽에서 자르는 위치
        sb.append(min).append(" ").append(pos);
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력을 처리하고 초기화하는 메소드
     */
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        l = Integer.parseInt(st.nextToken()); // 통나무 길이
        k = Integer.parseInt(st.nextToken()); // 자를 수 있는 위치의 개수
        c = Integer.parseInt(st.nextToken()); // 최대 자를 수 있는 횟수

        pointCut = new TreeSet<>(); // 자를 수 있는 위치를 저장할 TreeSet 초기화

        st = new StringTokenizer(br.readLine());
        // 자를 수 있는 위치들을 입력받아 TreeSet에 저장
        for (int i = 0; i < k; i++) {
            pointCut.add(Integer.parseInt(st.nextToken()));
        }
    }

    /**
     * 이분 탐색을 통해 최소 통나무 길이를 찾는 메소드
     * @return 가능한 최소 통나무 길이
     */
    private static int binarySearch() {
        int left = 1; // 최소 가능한 길이
        int right = l; // 최대 가능한 길이 (통나무 전체 길이)
        int result = 0; // 결과값 저장 변수

        // 이분 탐색 수행
        while (left <= right) {
            int mid = left + (right - left) / 2; // 중간값 계산 (오버플로우 방지)

            // mid 길이로 통나무를 자를 수 있는지 확인
            if (valid(mid)) {
                result = mid; // 가능하면 결과 업데이트
                right = mid - 1; // 더 작은 값으로 탐색 범위 줄임 (최소값을 찾기 위해)
            } else {
                left = mid + 1; // 불가능하면 더 큰 값으로 탐색 범위 늘림
            }
        }
        return result;
    }

    /**
     * 주어진 길이(target)로 통나무를 자를 수 있는지 확인하는 메소드
     * @param target 확인할 최대 통나무 길이
     * @return target 길이로 통나무를 자를 수 있으면 true, 아니면 false
     */
    private static boolean valid(int target) {
        int count = 0; // 자른 횟수
        int length = 0; // 현재 위치

        // 원본 pointCut을 변경하지 않기 위해 복사본 생성
        TreeSet<Integer> set = new TreeSet<>(pointCut);

        // 최대 c번 자르기 시도
        while (length + target < l * 2 && count < c) {
            // length + target 이하의 가장 큰 자를 수 있는 위치 찾기
            Integer point = set.floor(length + target);
            if (point == null) break; // 적절한 위치가 없으면 중단

            // point보다 작거나 같은 모든 자를 위치를 제거 (이미 처리한 부분)
            set.headSet(point, true).clear();
            length = point; // 현재 위치 업데이트
            count++; // 자른 횟수 증가
        }

        // 남은 통나무 조각(l - length)의 길이가 target보다 크면 불가능
        if (l - length > target) return false;
        return true;
    }
}