import java.io.*;

public class boj9440 {
    // 입출력을 위한 BufferedReader와 BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // 남은 숫자의 개수를 저장하는 변수
    private static int t;
    // 각 숫자(0-9)의 등장 횟수를 저장하는 배열
    private static int[] trie;

    public static void main(String[] args) throws IOException {
        // 입력이 0이 들어올 때까지 반복
        while (true) {
            setting();    // 입력 받기
            if (t == 0) break;    // 0이 입력되면 종료
            sb.append(greedy()).append("\n");    // 결과 계산 및 저장
        }

        // 결과 출력 및 스트림 닫기
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    // 입력을 받아 초기 설정을 하는 메소드
    private static void setting() throws IOException {
        String[] input = br.readLine().split(" ");
        int n = Integer.parseInt(input[0]);    // 숫자의 개수
        t = n;    // 남은 숫자 개수 초기화

        // 각 숫자의 등장 횟수를 저장할 배열 초기화
        trie = new int[10];

        if (t == 0) return;    // 0이 입력되면 종료

        // 입력받은 숫자들의 등장 횟수 카운트
        for (int i = 1; i <=n; i++) {
            int num = Integer.parseInt(input[i]);
            trie[num]++;
        }
    }

    // 가능한 가장 작은 합을 구하는 메소드 (그리디 알고리즘)
    private static int greedy() throws IOException{
        // 두 수를 저장할 문자열
        String a = "";
        String b = "";

        // 첫 번째 자리수 설정 (0이 아닌 수로)
        for (int i = 1; i < 10;) {
            if (a.length() > 0 && b.length() > 0) break;

            if (trie[i] > 0) {
                // 첫 번째 수의 첫 자리
                if (a.length() == 0) {
                    a += i;
                }
                // 두 번째 수의 첫 자리
                else {
                    b += i;
                }
                trie[i]--;
                t--;
            } else {
                i++;
            }
        }

        // 남은 숫자들을 두 수에 번갈아가며 추가
        int index = 0;
        while (t > 0) {
            if (trie[index] > 0) {
                // 두 수의 길이가 같으면 첫 번째 수에 추가
                if (a.length() == b.length()) {
                    a += index;
                }
                // 다르면 두 번째 수에 추가
                else {
                    b += index;
                }
                trie[index]--;
                t--;
            } else {
                index++;
            }
        }

        // 두 수의 합을 반환
        return Integer.parseInt(a) + Integer.parseInt(b);
    }
}