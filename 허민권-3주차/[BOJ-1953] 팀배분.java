import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    // 학생들이 속한 팀을 저장하는 배열 (-1: 미배정, 0: 청팀, 1: 백팀)
    static int[] check = IntStream.generate(() -> -1).limit(101).toArray();
    
    // 학생 간의 싫어하는 관계를 저장하는 인접 행렬
    static int[][] students = new int[101][101];
    
    // 학생 수
    static int n;

    /**
     * DFS(깊이 우선 탐색) 방식으로 학생들을 두 팀으로 배정하는 함수
     * @param num 현재 팀을 배정할 학생 번호
     * @param kind 현재 학생이 배정될 팀 ('w'는 청팀, 'b'는 백팀)
     */
    public static void go(int num, char kind){
        // 'w'면 청팀(0), 'b'면 백팀(1)으로 배정
        check[num] = kind == 'w' ? 0 : 1;

        // 모든 학생을 탐색하면서 싫어하는 관계 확인
        for(int i=1 ;i<=n; ++i){
            if(num == i) continue;  // 자기 자신은 고려할 필요 없음
            if(check[i] != -1) continue;  // 이미 배정된 학생은 건너뜀

            // num 학생과 i 학생이 서로 싫어하는 관계라면 반대 팀으로 배정
            if(students[num][i] == 1){
                go(i, kind == 'w'? 'b' : 'w'); // 청팀이면 백팀으로, 백팀이면 청팀으로
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // 입력을 위한 BufferedReader 선언
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 첫 번째 줄: 학생 수 입력
        n = Integer.parseInt(br.readLine());

        // 학생별 싫어하는 사람의 정보를 인접 행렬에 저장
        for(int i=1; i<=n; ++i){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(st.nextToken()); // i번째 학생이 싫어하는 사람의 수
            for(int j=0; j<p; ++j){
                int target = Integer.parseInt(st.nextToken()); // 싫어하는 사람의 번호
                students[i][target] = 1; // 싫어하는 관계 표시
            }
        }

        // 모든 학생을 탐색하면서 아직 팀이 정해지지 않은 경우 DFS 수행
        for(int i=1; i<=n; ++i){
            if(check[i] == -1){
                go(i, 'w'); // DFS 탐색 시작 (기본적으로 'w'팀으로 배정 시작)
            }
        }

        // 청팀과 백팀을 저장할 리스트
        List<Integer> w = new ArrayList<>();
        List<Integer> b = new ArrayList<>();

        // 학생들을 팀별로 분류
        for(int i=1; i<=n; ++i){
            if(check[i] == 0) w.add(i); // 청팀
            else b.add(i); // 백팀
        }
        
        // 특정 경우(한 팀에 모든 학생이 속하는 경우)를 예외 처리
        if(w.size() == 100 || w.size() == 0){
            System.out.println(99);
            System.out.println(IntStream.range(1, n).mapToObj(String::valueOf).collect(Collectors.joining(" ")));
            System.out.println(1);
            System.out.println(n);
        }else{
            // 일반적인 경우: 팀의 크기와 팀원 목록 출력
            System.out.println(w.size());
            System.out.println(w.stream().map(String::valueOf).collect(Collectors.joining(" ")));
            System.out.println(b.size());
            System.out.println(b.stream().map(String::valueOf).collect(Collectors.joining(" ")));
        }
    }
}
