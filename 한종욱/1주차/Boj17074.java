import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Boj17074 {
   // 입출력을 위한 BufferedReader와 BufferedWriter
   private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
   
   // 패딩값으로 사용할 최소, 최대값 상수 선언
   private static final int MIN_VALUE = -1000000005;
   private static final int MAX_VALUE = 1000000005;
   
   // 수열을 저장할 배열
   private static int[] sequence;
   // 비내림차순이 깨지는 지점의 인덱스
   private static int index = 0;
   // 비내림차순이 깨지는 횟수
   private static int breakCount = 0;
   // 수열의 길이
   private static int n;
   
   public static void main(String[] args) throws IOException {
       // 수열의 길이 입력받기
       n = Integer.parseInt(br.readLine());
       // 하나의 원소를 제거했을 때 비내림차순이 되는 경우의 수
       int count = 0;
       
       // 공백으로 구분된 수열 입력받기
       String[] input = br.readLine().split(" ");
       
       // 양끝에 패딩을 추가하기 위해 n+2 크기의 배열 생성
       sequence = new int[n + 2];
       // 맨 앞에 최소값 패딩 추가
       sequence[0] = MIN_VALUE;
       // 맨 뒤에 최대값 패딩 추가
       sequence[sequence.length - 1] = MAX_VALUE;
       
       // 입력받은 수열을 배열에 저장
       for (int i = 1; i <= n; i++) {
           sequence[i] = Integer.parseInt(input[i - 1]);
       }
       
       // 수열 분석하여 비내림차순이 깨지는 횟수와 위치 파악
       analyzeSequence();
       
       // 비내림차순이 깨지지 않는 경우 
       // -> 어떤 수를 제거해도 비내림차순이 됨
       if (breakCount == 0) {
           count = n;
       }
       
       // 비내림차순이 한 번 깨지는 경우
       if (breakCount == 1) {
           // 깨지는 지점의 왼쪽 수를 제거했을 때 비내림차순이 되는지 확인
           if (sequence[index - 1] <= sequence[index + 1]) count++;
           // 깨지는 지점의 오른쪽 수를 제거했을 때 비내림차순이 되는지 확인
           if (sequence[index] <= sequence[index + 2]) count++;
       }
       
       // 결과 출력 및 리소스 해제
       bw.write(count + "\n");
       bw.flush();
       bw.close();
       br.close();
   }
   
   // 수열을 분석하여 비내림차순이 깨지는 횟수와 위치를 찾는 메소드
   private static void analyzeSequence() {
       for (int i = 0; i < sequence.length - 1; i++) {
           // 현재 수가 다음 수보다 크면 비내림차순이 깨짐
           if (sequence[i] > sequence[i + 1]) {
               index = i;     // 깨지는 지점의 인덱스 저장
               breakCount++;  // 깨지는 횟수 증가
           }
       }
   }
}