import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Boj17089 {
  // 입출력을 위한 BufferedReader와 BufferedWriter
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
  
  // 선택해야 하는 친구 수
  private static final int FRIEND_SIZE = 3;
  // 친구 관계가 없는 경우 반환할 값
  private static final int NOT_FOUND = -1;
  // 선택된 친구들의 번호를 저장하는 배열
  private static int[] selectedFriends = new int[FRIEND_SIZE];
  // 각 사람별 친구 수를 저장하는 배열
  private static int[] friendCount;
  // 친구 관계를 저장하는 2차원 배열
  private static boolean[][] relation;
  // n: 전체 사람 수, m: 친구 관계의 수
  private static int n, m;
  // 세 친구가 가지는 다른 친구 수의 최솟값
  private static int min = Integer.MAX_VALUE;

  public static void main(String[] args) throws IOException {
      // 입력 처리
      String[] input = br.readLine().split(" ");
      n = Integer.parseInt(input[0]);
      m = Integer.parseInt(input[1]);
      
      // 배열 초기화
      friendCount = new int[n + 1];
      relation = new boolean[n + 1][n + 1];
      
      // 친구 관계 입력 및 친구 수 계산
      for (int i = 0; i < m; i++) {
          input = br.readLine().split(" ");
          int x = Integer.parseInt(input[0]);
          int y = Integer.parseInt(input[1]);
              
          // 양방향 친구 관계 설정
          relation[x][y] = true;
          relation[y][x] = true;
          
          // 각 사람의 친구 수 증가
          friendCount[x]++;
          friendCount[y]++;
      }
      
      // 가능한 모든 3명 조합 탐색
      combine(1, 0);
      
      // 유효한 친구 관계를 찾지 못한 경우 NOT_FOUND로 설정
      if (min == Integer.MAX_VALUE) {
          min = NOT_FOUND;
      }
      
      // 결과 출력 및 자원 해제
      bw.write(min + "\n");
      bw.flush();
      bw.close();
      br.close();
  }
  
  /**
   * 가능한 모든 3명의 조합을 찾는 메소드
   * @param start 조합의 시작 인덱스
   * @param index 현재까지 선택한 친구의 수
   */
  private static void combine(int start, int index) {
      // 3명을 모두 선택한 경우
      if (index == 3) {
          // 선택된 3명의 전체 친구 수 계산
          int result = friendCount[selectedFriends[0]] + 
                      friendCount[selectedFriends[1]] + 
                      friendCount[selectedFriends[2]];
          // 서로 간의 친구 관계 6개 제외
          result -= 6;
          // 최솟값 갱신
          min = Math.min(result, min);
          return;
      }
      
      // start부터 n까지의 숫자 중에서 하나를 선택
      for (int i = start; i <= n ; i++) {
          selectedFriends[index] = i;
          // 첫 번째 선택이 아니고, 이전에 선택된 친구들과 모두 친구가 아닌 경우 스킵
          if (index > 0 && !areAllFriends(index)) continue;
          combine(i + 1, index + 1);
      }
  }
  
  /**
   * 현재 선택한 친구가 이전에 선택한 모든 친구들과 친구 관계인지 확인하는 메소드
   * @param index 현재 선택한 친구의 인덱스
   * @return 모든 이전 친구들과 친구인 경우 true, 아니면 false
   */
  private static boolean areAllFriends(int index) {
      for (int i = 0; i < index; i++) {
          if (!relation[selectedFriends[i]][selectedFriends[index]]) {
              return false;
          }
      }
      return true;
  }
}