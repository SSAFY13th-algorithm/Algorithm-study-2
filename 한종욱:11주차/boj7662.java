import java.io.*;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * 백준 7662번 - 이중 우선순위 큐
 * 문제 요약: 최댓값과 최솟값을 모두 추출할 수 있는 이중 우선순위 큐를 구현하는 문제
 * 명령어 I n: 정수 n을 큐에 삽입
 * 명령어 D 1: 큐에서 최댓값 삭제
 * 명령어 D -1: 큐에서 최솟값 삭제
 */
public class boj7662 {
    // 입출력을 위한 객체들 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static StringBuilder sb = new StringBuilder();  // 결과를 모아서 한 번에 출력하기 위한 StringBuilder

    // TreeMap을 사용하여 이중 우선순위 큐 구현 (key: 정수값, value: 해당 정수의 개수)
    private static TreeMap<Integer, Integer> tMap;

    // 각 테스트 케이스의 연산 개수
    private static int k;

    /**
     * 메인 메소드: 프로그램의 진입점
     */
    public static void main(String[] args) throws IOException {
        int t = Integer.parseInt(br.readLine());  // 테스트 케이스의 개수

        // 각 테스트 케이스마다 처리
        for (int i = 0; i < t; i++) {
            init();  // 테스트 케이스 초기화 및 처리
        }

        // 모든 결과를 한 번에 출력
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 테스트 케이스를 초기화하고 처리하는 메소드
     */
    private static void init() throws IOException {
        k = Integer.parseInt(br.readLine());  // 연산의 개수
        tMap = new TreeMap<>();  // TreeMap 초기화 (자동으로 키 정렬)

        // k개의 연산 수행
        for (int i = 0; i < k; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            char command = st.nextToken().charAt(0);  // 명령어 (I 또는 D)
            int num = Integer.parseInt(st.nextToken());  // 정수 또는 삭제 위치 지정(-1 또는 1)

            if (command == 'I') {  // 삽입 연산
                // 이미 존재하는 값이면 개수 증가, 없으면 1로 설정
                tMap.put(num, tMap.getOrDefault(num, 0) + 1);
            } else {  // 삭제 연산
                // 큐가 비어있으면 무시
                if (tMap.isEmpty()) continue;

                if (num == 1) {  // 최댓값 삭제
                    int maxKey = tMap.lastKey();  // TreeMap의 마지막 키(최댓값)
                    tMap.put(maxKey, tMap.get(maxKey) - 1);  // 개수 감소

                    // 개수가 0이 되면 해당 키 제거
                    if (tMap.get(maxKey) == 0) tMap.remove(maxKey);
                } else {  // 최솟값 삭제
                    int minKey = tMap.firstKey();  // TreeMap의 첫번째 키(최솟값)
                    tMap.put(minKey, tMap.get(minKey) - 1);  // 개수 감소

                    // 개수가 0이 되면 해당 키 제거
                    if (tMap.get(minKey) == 0) tMap.remove(minKey);
                }
            }
        }

        // 결과를 StringBuilder에 추가
        if (tMap.isEmpty()) {
            sb.append("EMPTY");  // 큐가 비어있으면 "EMPTY" 출력
        } else {
            // 최댓값과 최솟값을 공백으로 구분하여 출력
            sb.append(tMap.lastKey()).append(" ").append(tMap.firstKey());
        }
        sb.append("\n");  // 테스트 케이스의 결과 구분을 위한 개행
    }
}