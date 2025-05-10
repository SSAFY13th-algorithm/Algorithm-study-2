import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class boj1953 {
    // 입출력을 위한 BufferedReader, BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // 적대 관계를 저장하는 인접 리스트
    private static List<List<Integer>> table = new ArrayList<>();
    // 각 팀의 구성원을 저장하는 리스트
    private static List<Integer> team1 = new ArrayList<>();
    private static List<Integer> team2 = new ArrayList<>();
    // 방문 여부를 체크하는 배열
    private static boolean[] visited;
    // 전체 인원 수
    private static int n;

    public static void main(String[] args) throws IOException{
        setting();  // 초기 데이터 설정

        // 모든 사람에 대해 팀 배정
        for (int i = 1; i <= n; i++) {
            if (!visited[i]) {
                dfs(i, 1);  // 방문하지 않은 사람부터 DFS 시작
            }
        }

        // team1 결과 출력 준비
        sb.append(team1.size()).append("\n");
        Collections.sort(team1);
        Collections.sort(team2);
        for (int person : team1) {
            sb.append(person).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("\n");

        // team2 결과 출력 준비
        sb.append(team2.size()).append("\n");
        for (int person : team2) {
            sb.append(person).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("\n");

        // 결과 출력
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    // 초기 데이터를 설정하는 메소드
    private static void setting() throws IOException {
        n = Integer.parseInt(br.readLine());
        // 인접 리스트 초기화
        for (int i = 0; i <= n; i++) {
            table.add(new ArrayList<>());
        }
        visited = new boolean[n + 1];

        // 적대 관계 입력 받기
        for (int i = 0; i < n; i++) {
            String[] input = br.readLine().split(" ");
            int count = Integer.parseInt(input[0]);
            for (int j = 1 ; j <= count; j++) {
                // 양방향 그래프로 저장
                table.get(i + 1).add(Integer.parseInt(input[j]));
                table.get(Integer.parseInt(input[j])).add(i + 1);
            }
        }
    }

    // DFS로 팀을 나누는 메소드
    private static void dfs(int person, int teamNum) {
        visited[person] = true;  // 방문 처리

        // 팀 배정
        if (teamNum == 1) {
            team1.add(person);
        } else {
            team2.add(person);
        }

        // 적대 관계에 있는 사람들을 반대 팀으로 배정
        for (int enemy : table.get(person)) {
            if (!visited[enemy]) {
                dfs(enemy, teamNum * -1);  // 팀 번호를 반대로 바꿔서 재귀 호출
            }
        }
    }
}