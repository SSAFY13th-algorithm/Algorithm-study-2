import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // 입력을 빠르게 받기 위한 BufferedReader 사용

        StringTokenizer st = new StringTokenizer(br.readLine()); // 첫 번째 줄 입력 (N: 전자기기 개수, M: 콘센트 개수)
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine()); // 두 번째 줄 입력 (각 전자기기의 충전 시간)
        int[] check = new int[16]; // 2^0 ~ 2^15까지의 개수를 저장할 배열
        for (int i = 0; i < N; ++i) {
            int cur = Integer.parseInt(st.nextToken()); // 현재 입력된 충전 시간
            ++check[(int) (Math.log(cur) / Math.log(2))]; // log₂(cur)를 이용해 해당 k 값의 개수를 증가
        }

        int answer = 0; // 최소 충전 완료 시간
        int fill = 0;   // 현재 충전 가능한 슬롯 개수

        // 가장 긴 충전 시간(2^15)부터 작은 값으로 처리
        for (int i = 15; i >= 0; --i) {
            fill *= 2; // 상위 크기의 남은 충전 슬롯을 다음 크기로 넘겨줌

            if (check[i] == 0) { // 해당 충전 시간이 존재하지 않으면 패스
                continue;
            }

            // 기존 충전 슬롯이 남아 있다면 먼저 처리
            if (fill > 0) {
                if (check[i] <= fill) { // 남은 충전 슬롯으로 현재 크기 모두 처리 가능
                    fill -= check[i];
                    continue;
                }

                // 일부만 처리 가능하면 남은 기기 개수만큼 감소 후 fill을 초기화
                check[i] -= fill;
                fill = 0;
            }

            // M개의 콘센트로 그룹화하여 처리 가능한 부분을 먼저 충전
            answer += (check[i] / M) * Math.pow(2, i);
            check[i] = check[i] % M; // 나머지는 따로 처리

            if (check[i] == 0) { // 나머지가 없으면 패스
                continue;
            } else { // 남은 경우 새로운 충전 사이클이 필요함
                answer += Math.pow(2, i); // 해당 크기만큼의 시간을 추가
                fill = M - check[i]; // 새로운 충전 슬롯 설정
            }
        }

        System.out.println(answer); // 최소 충전 완료 시간 출력
    }
}
